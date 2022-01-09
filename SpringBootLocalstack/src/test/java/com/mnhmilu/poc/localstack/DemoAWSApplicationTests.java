package com.mnhmilu.poc.localstack;

import com.mnhmilu.poc.localstack.client.SSMClient;
import com.mnhmilu.poc.localstack.entity.SubscriptionEntity;
import com.mnhmilu.poc.localstack.repository.SubscriptionRepository;
import com.mnhmilu.poc.localstack.service.MessageSenderWithTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Slf4j
class DemoAWSApplicationTests {

    @Value("${ssm.endpointUrl}")
    private String ssmEndPointUrl;

    @Value("${ssm.endpointRegion}")
    private String ssmEndPointRegion;

    @Value("${resource.prefix}")
    private String appProfile;

    @Value("${spring.application.name}")
    private String appName;
    private SSMClient ssmClient;
    @Autowired
    private MessageSenderWithTemplate messageSenderWithTemplate;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Test
    public void testSSMConfig() {
      //todo: manually put ssm param via command , now checking if value is available or not
      SSMClient ssmClient = new SSMClient(appName,appProfile,ssmEndPointUrl,ssmEndPointRegion);
      assertEquals(ssmClient.getSSMParameterValue("param1"),"param1value");
      Map<String, String> apiKeys=ssmClient.getSsmParams();
      assertEquals(apiKeys.get("param1"),"param1value");
    }
    @Test
    public void testDynamoDbConfig()
    {
        log.info("Inserting sample dynamodb item");
        assertEquals("test","test");
        SubscriptionEntity subscriptionEntity=new SubscriptionEntity();
        subscriptionEntity.setSubscriptionRequestId("sub123");
        subscriptionEntity.setWalletNo("01733400896");
        subscriptionEntity.setAccountNo("123");
        subscriptionEntity.setExternalRequestId("ex123");
        subscriptionEntity.setStatus("active");
        subscriptionRepository.save(subscriptionEntity);
    }
    @Test
    public void testSQSConfig() {
        try {
            log.info("Sending message to dev-poc-queue");
            messageSenderWithTemplate.send("test-message-new", "dev-poc-queue");

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
