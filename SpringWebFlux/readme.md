# Technology Stack
- Spring Web Flux

## Prerequisite 

> aws dynamodb --endpoint-url http://localhost:4566 create-table --table-name customer-sync --attribute-definitions AttributeName=customerId,AttributeType=S --key-schema AttributeName=customerId,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

> DYNAMO_ENDPOINT=http://localhost:4566 dynamodb-admin --open

## Instruction:

- for web flux run SpringWebfluxDemoApplication
- for non web flux run SpringWithoutWebfluxApplication

## Resources:
- What is Spring Webflux and when to use it ? [link](https://www.youtube.com/watch?v=M3jNn3HMeWg)
- About Reactive and Non Reactive Application performance [link](https://blog.devgenius.io/is-spring-webflux-a-myth-4526c2f92413)
 
 