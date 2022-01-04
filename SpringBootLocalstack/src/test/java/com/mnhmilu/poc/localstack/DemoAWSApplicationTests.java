package com.mnhmilu.poc.localstack;

import com.mnhmilu.poc.localstack.client.SSMClient;
import org.junit.jupiter.api.Test;
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
}
