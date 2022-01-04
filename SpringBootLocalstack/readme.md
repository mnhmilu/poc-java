## POC List
- [ ] Localstack 
- [ ] AWS DynamoDB
- [ ] AWS SQS
- [ ] AWS SSM 
- [ ] AWS EC2
- [ ] AWS SNS

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

> aws ssm --endpoint-url http://localhost:4566 put-parameter --name "/dev/someparam/param1" --type SecureString --value "param1value" --overwrite

> aws ssm --endpoint-url http://localhost:4566 get-parameter --name dev/someparam/param1


### SQS
> aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name dev-queue


### DynamoDB

> aws dynamodb --endpoint-url http://localhost:4566 create-table --table-name dev-product --attribute-definitions AttributeName=organizationCode,AttributeType=S AttributeName=productId,AttributeType=S --key-schema AttributeName=organizationCode,KeyType=HASH AttributeName=productId,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

- Add local index (ref)
- Add global index (ref)

### S3

> aws --endpoint-url=http://localhost:4566 s3 mb s3://bucket
> aws --endpoint-url=http://localhost:4566 s3 cp ./config/s3/file.txt s3://bucket