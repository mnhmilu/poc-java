package com.mnhmilu.poc.localstack.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mnhmilu.poc.localstack.util.ProfileConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Client {

    @Value("${s3.endpointUrl}")
    private String endpointUrl;

    @Value("${s3.endpointRegion}")
    private String endpointRegion;

    @Value("${s3.access.key}")
    private String accessKey;

    @Value("${s3.secret.key}")
    private String secretKey;

    @Value("${resource.prefix}")
    private String profile;

    @Bean
    public AmazonS3 createClient() {
        if (ProfileConstants.PROFILE_DEV.equals(profile)) {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AwsClientBuilder.EndpointConfiguration endpoint =
                    new AwsClientBuilder.EndpointConfiguration(endpointUrl, endpointRegion);
            return AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(endpoint)
                    .withPathStyleAccessEnabled(true).build();
        } else {
            return AmazonS3ClientBuilder.standard().build();
        }
    }
}
