package com.mnhmilu.poc.localstack;

import com.mnhmilu.poc.localstack.client.SSMClient;
import com.mnhmilu.poc.localstack.entity.SubscriptionEntity;
import com.mnhmilu.poc.localstack.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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

    @Test
    public void testSSMConfig() {
      //todo: manually put ssm param via command , now checking if value is available or not
      SSMClient ssmClient = new SSMClient(appName,appProfile,ssmEndPointUrl,ssmEndPointRegion);
      assertEquals(ssmClient.getSSMParameterValue("param1"),"param1value");
      Map<String, String> apiKeys=ssmClient.getSsmParams();
      assertEquals(apiKeys.get("param1"),"param1value");
    }

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Test
    public void testDynamoDbConfig()
    {
        assertEquals("test","test");
        SubscriptionEntity subscriptionEntity=new SubscriptionEntity();
        subscriptionEntity.setSubscriptionRequestId("sub123");
        subscriptionEntity.setWalletNo("01733400896");
        subscriptionEntity.setAccountNo("123");
        subscriptionEntity.setExternalRequestId("ex123");
        subscriptionEntity.setStatus("active");
        subscriptionRepository.save(subscriptionEntity);
    }



}
