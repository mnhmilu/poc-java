package com.mnhmilu.poc.localstack.config;

import com.mnhmilu.poc.localstack.repository.SubscriptionRepository;
import com.mnhmilu.poc.localstack.repository.SubscriptionRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Value("${resource.prefix}")
    private String appProfile;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${dynamodb.endpointUrl}")
    private String endpointUrl;

    @Value("${dynamodb.endpointRegion}")
    private String endpointRegion;

    @Bean
    SubscriptionRepository subscriptionRepository() {
        return new SubscriptionRepositoryImpl(appProfile, appName, endpointUrl, endpointRegion);
    }

}