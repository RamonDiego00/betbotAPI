#!/bin/bash
set -e

echo "➜ Deploying infrastructure with Terraform..."
cd cloud/
terraform init
terraform apply -auto-approve

echo "✅ Infrastructure deployed successfully!"
echo "🔗 ALB Endpoint: $(terraform output -raw alb_dns_name)"