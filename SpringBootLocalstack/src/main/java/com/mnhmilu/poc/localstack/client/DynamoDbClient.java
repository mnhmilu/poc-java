package com.mnhmilu.poc.localstack.client;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.mnhmilu.poc.localstack.util.ProfileConstants;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DynamoDbClient {
    private AmazonDynamoDB client;
    
    public DynamoDbClient(String region, String profile, String endpointUrl) {
        log.info("Region: {} and Active profile: {}", region, profile);

        if (ProfileConstants.PROFILE_DEV.equals(profile)) {

            client = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, region))
                    .build();
        } else {
            client = AmazonDynamoDBClientBuilder.standard().build();
        }
    }

    public DynamoDBMapper getDynamoDBMapper(String tableName) {

        var mapperConfig = new DynamoDBMapperConfig.Builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
                .build();

        return new DynamoDBMapper(this.client, mapperConfig);
    }

	public AmazonDynamoDB getClient() {
		return client;
	}
    
}
