## POC List
- [X] Localstack 
- [X] AWS DynamoDB
- [X] AWS SQS
- [X] AWS SSM 
- [ ] AWS S3
- [ ] AWS SNS
- [ ] AWS ElasticCace

### Localstack Readiness
#### Prerequisite:

1. AWS-CLI (V1)
2. Docker-Compose util
3. Docker

###### Create mock aws crdentials file (method 1):

```
$ sudo touch ~/.aws/credentials
$ sudo nano ~/.aws/credentials
```

Put below lines in the file ~/.aws/credentials and save it.
```
[default]
aws_access_key_id = fakeMyKeyId
aws_secret_access_key = fakeSecretAccessKey
```
To view aws configuration
```
$sudo aws configure list
```
Create mock aws config file:
```
$ sudo touch  ~/.aws/config
$ sudo nano  ~/.aws/config
```

Put below lines in the file ~/.aws/config and save it.

```
[default]
region = ap-southeast-1
output_format = json
```

###### Create mock aws crdentials file (method 2):
```
$aws configure

AWS Access Key ID : fakeMyKeyId
AWS Secret Access Key: fakeSecretAccessKey
Default region name: ap-southeast-1
Default output format: json
```
cd to project's docker compose file. Run :

```
$ sudo docker-compose up
```
#### To install DynamoDB Admin UI

https://github.com/aaronshaf/dynamodb-admin

#### To configure DynamoDB Admin UI
```
AWS_REGION=ap-southeast-1 AWS_ACCESS_KEY_ID=fakeMyKeyId AWS_SECRET_ACCESS_KEY=fakeSecretAccessKey dynamodb-admin
```

### execute cmd before run test 

> sh start.sh


## Or

#### To run DynamoDB Admin UI
```
DYNAMO_ENDPOINT=http://localhost:4566 dynamodb-admin --open
```

#### To create initial infra ( table, data etc )
For Linux user, you have to execute this command from the project root directory
```
$ sh config/dynamodb/dynamodb.sh
```
#### To create ssm parameters
For linux user, you have to execute this command from the project root directory

```
$ sh config/ssm-parameters.sh
```
### SSM

> aws ssm --endpoint-url http://localhost:4566 put-parameter --name "/dev/poc/param1" --type SecureString --value "param1value" --overwrite

> aws ssm --endpoint-url http://localhost:4566 get-parameter --name dev/poc/param1

### SQS
> aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name dev-poc-queue

> aws sqs --endpoint-url http://localhost:4566 list-queues

> aws sqs --endpoint-url http://localhost:4566 receive-message --queue-url http://localhost:4566/000000000000/dev-poc-queue

> Critical POM Dependency (use maven dependency analyzer if needed)
```
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk-core</artifactId>
            <version>1.12.37</version>
        </dependency>
```


### DynamoDB

> aws dynamodb --endpoint-url http://localhost:4566 create-table \
--table-name dev-subscription \
--attribute-definitions AttributeName=walletNo,AttributeType=S AttributeName=externalRequestId,AttributeType=S AttributeName=accountNo,AttributeType=S \
--key-schema AttributeName=walletNo,KeyType=HASH AttributeName=externalRequestId,KeyType=RANGE \
--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
--local-secondary-indexes \
"[{\"IndexName\": \"index-accountNo\",
\"KeySchema\":[{\"AttributeName\":\"walletNo\",\"KeyType\":\"HASH\"}, \
{\"AttributeName\":\"accountNo\",\"KeyType\":\"RANGE\"}],
\"Projection\":{\"ProjectionType\":\"ALL\"}}]"



> aws dynamodb --endpoint-url http://localhost:4566 update-table
--table-name dev-subscription
--attribute-definitions AttributeName=externalRequestId,AttributeType=S
--global-secondary-index-updates \
"[{\"Create\":{\"IndexName\": \"index-externalRequestId\",\"KeySchema\":[{\"AttributeName\":\"externalRequestId\",\"KeyType\":\"HASH\"}], \
\"ProvisionedThroughput\": {\"ReadCapacityUnits\": 10, \"WriteCapacityUnits\": 5 },\"Projection\":{\"ProjectionType\":\"ALL\"}}}]"

### S3

> aws --endpoint-url=http://localhost:4566 s3 mb s3://bucket

> aws --endpoint-url=http://localhost:4566 s3 cp ./config/s3/file.txt s3://bucket

View all existing buckets
> aws --endpoint-url=http://localhost:4566 s3 ls

List all item in a specific bucket 
> aws --endpoint-url=http://localhost:4566 s3 ls bucket --recursive
