package com.mnhmilu.poc.springssl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void checkingHttpsConnection() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/SrpingBootSSL-0.0.1-SNAPSHOT/test/echo", HttpMethod.GET, null, String.class);
        System.out.println(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

}
