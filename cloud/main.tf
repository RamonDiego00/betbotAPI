provider "aws" {
  region = "us-east-1"
}

# ECR Repository
resource "aws_ecr_repository" "api" {
  name = "kognity-bet-hexagonal-api"
}

# ECS Cluster
resource "aws_ecs_cluster" "api" {
  name = "kognity-bet-hexagonal-api-cluster"
}

# IAM Role para ECS
resource "aws_iam_role" "ecs_execution" {
  name = "ecs_execution_role_kognity"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_execution" {
  role       = aws_iam_role.ecs_execution.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

# Security Group para o ALB
resource "aws_security_group" "alb" {
  name        = "kognity-alb-sg"
  description = "Allow HTTP traffic"

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Security Group para ECS
resource "aws_security_group" "ecs" {
  name        = "kognity-ecs-sg"
  description = "Allow traffic from ALB"

  ingress {
    from_port       = 8080
    to_port         = 8080
    protocol        = "tcp"
    security_groups = [aws_security_group.alb.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# ECS Task Definition
resource "aws_ecs_task_definition" "api" {
  family                   = "kognity-bet-hexagonal-api"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = 256
  memory                   = 512
  execution_role_arn       = aws_iam_role.ecs_execution.arn
  container_definitions    = jsonencode([{
    name      = "kognity-bet-hexagonal-api",
    image     = "${aws_ecr_repository.api.repository_url}:latest",
    essential = true,
    portMappings = [
      {
        containerPort = 8080,
        hostPort      = 8080,
        protocol      = "tcp"
      }
    ],
    environment = [
      {
        name  = "SPRING_PROFILES_ACTIVE",
        value = "prod"
      }
    ],
    logConfiguration = {
      logDriver = "awslogs",
      options = {
        awslogs-group         = "/ecs/kognity-api",
        awslogs-region        = "us-east-1",
        awslogs-stream-prefix = "ecs"
      }
    }
  }])
}

# CloudWatch Log Group
resource "aws_cloudwatch_log_group" "ecs" {
  name = "/ecs/kognity-api"
  retention_in_days = 3
}

# ECS Service
resource "aws_ecs_service" "api" {
  name            = "kognity-bet-hexagonal-api-service"
  cluster         = aws_ecs_cluster.api.id
  task_definition = aws_ecs_task_definition.api.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = data.aws_subnets.default.ids
    security_groups = [aws_security_group.ecs.id]
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.api.arn
    container_name   = "kognity-bet-hexagonal-api"
    container_port   = 8080
  }

  depends_on = [aws_lb_listener.api]
}

# Data sources para usar a VPC default
data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

# Application Load Balancer
resource "aws_lb" "api" {
  name               = "kognity-hexagonal-api-lb"
  internal           = false
  load_balancer_type = "application"
  subnets            = data.aws_subnets.default.ids
  security_groups    = [aws_security_group.alb.id]
}

# ALB Target Group
resource "aws_lb_target_group" "api" {
  name        = "kognity-hexagonal-api-tg"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = data.aws_vpc.default.id
  target_type = "ip"

  health_check {
    path                = "/actuator/health"
    healthy_threshold   = 2
    unhealthy_threshold = 2
    timeout             = 5
    interval            = 30
    matcher             = "200"
  }
}

# ALB Listener
resource "aws_lb_listener" "api" {
  load_balancer_arn = aws_lb.api.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.api.arn
  }
}