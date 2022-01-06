package com.mnhmilu.poc.localstack.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnhmilu.poc.localstack.util.ProfileConstants;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

import java.util.Collections;

@Configuration
public class SQSConfig {

    @Value("${sqs.endpointUrl}")
    private String url;

    @Value("${sqs.endpointRegion}")
    private String region;

    @Value("${sqs.access.key}")
    private String awsAccessKey;

    @Value("${sqs.secret.key}")
    private String awsSecretKey;

    @Value("${resource.prefix}")
    private String profile;

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQS(AWSCredentialsProvider credentials) {
        return (ProfileConstants.PROFILE_DEV.equals(profile))? AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(credentials)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(url, region))
                .build() : AmazonSQSAsyncClientBuilder.standard().build();
    }

    @Bean
    @Primary
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSCredentialsProviderChain(
                new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsAccessKey, awsSecretKey)));
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQS){
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQS);
        factory.setMaxNumberOfMessages(10);
        return factory;
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(final ObjectMapper mapper,
                                                                 final AmazonSQSAsync amazonSQSAsync) {
        final QueueMessageHandlerFactory queueHandlerFactory = new QueueMessageHandlerFactory();
        queueHandlerFactory.setAmazonSqs(amazonSQSAsync);
        queueHandlerFactory.setArgumentResolvers(
                Collections.singletonList(new PayloadMethodArgumentResolver(jackson2MessageConverter(mapper))));
        return queueHandlerFactory;
    }
    private MessageConverter jackson2MessageConverter(final ObjectMapper mapper) {
        final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        // set strict content type match to false to enable the listener to handle AWS events
        converter.setStrictContentTypeMatch(false);
        converter.setObjectMapper(mapper);
        return converter;
    }

}
