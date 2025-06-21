#!/bin/bash
set -e

# Configurações
AWS_REGION="us-east-1"
ECR_REPO_NAME="kognity-betbot-hexagonal-api"

# Build e push só quando necessário
echo "➜ Building application..."
./gradlew build

echo "➜ Building Docker image..."
docker build -t $ECR_REPO_NAME .

echo "➜ Authenticating to ECR..."
AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
ECR_URI="$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_URI

echo "➜ Pushing image to ECR..."
docker tag $ECR_REPO_NAME:latest $ECR_URI/$ECR_REPO_NAME:latest
docker push $ECR_URI/$ECR_REPO_NAME:latest

echo "✅ Image updated successfully!"