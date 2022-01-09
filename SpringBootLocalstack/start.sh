#!/bin/bash
clear
echo "starting docker for localstack poc"
docker-compose up -d

echo "creating sample ssm parameter"
aws ssm --endpoint-url http://localhost:4566 put-parameter --name "/dev/poc/param1" --type SecureString --value "param1value" --overwrite

echo "creating sample poc queue"
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name dev-poc-queue

echo "creating sample dynamodb schema"

aws dynamodb --endpoint-url http://localhost:4566 create-table --table-name dev-poc-subscription --attribute-definitions AttributeName=walletNo,AttributeType=S AttributeName=externalRequestId,AttributeType=S AttributeName=accountNo,AttributeType=S --key-schema AttributeName=walletNo,KeyType=HASH AttributeName=externalRequestId,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --local-secondary-indexes \
 		"[{\"IndexName\": \"index-accountNo\",
        \"KeySchema\":[{\"AttributeName\":\"walletNo\",\"KeyType\":\"HASH\"},
                      {\"AttributeName\":\"accountNo\",\"KeyType\":\"RANGE\"}],
        \"Projection\":{\"ProjectionType\":\"ALL\"}}]"

echo "creating sample dynamodb global index"

aws dynamodb --endpoint-url http://localhost:4566 update-table --table-name dev-poc-subscription --attribute-definitions AttributeName=externalRequestId,AttributeType=S --global-secondary-index-updates \
  "[{\"Create\":{\"IndexName\": \"index-externalRequestId\",\"KeySchema\":[{\"AttributeName\":\"externalRequestId\",\"KeyType\":\"HASH\"}], \
  \"ProvisionedThroughput\": {\"ReadCapacityUnits\": 10, \"WriteCapacityUnits\": 5      },\"Projection\":{\"ProjectionType\":\"ALL\"}}}]"

DYNAMO_ENDPOINT=http://localhost:4566 dynamodb-admin --open