#/bin/bash

echo "########### Creating profile ###########"

aws configure set aws_access_key_id ${AWS_ACCESS_KEY_ID} --profile=localstack
aws configure set aws_secret_access_key ${AWS_SECRET_ACCESS_KEY} --profile=localstack
aws configure set region ${AWS_DEFAULT_REGION} --profile=localstack

echo "########### Listing profile ###########"
aws configure list --profile=localstack

echo "########## Creating table ##########"

aws dynamodb create-table --cli-input-json file:///user-table.json --endpoint-url=http://localhost:4566 --profile=localstack