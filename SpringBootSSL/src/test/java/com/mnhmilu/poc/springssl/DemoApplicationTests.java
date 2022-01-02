package com.mnhmilu.poc.springssl;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

    @Value("${http.client.ssl.trust-store}")
    private Resource keyStore;   // inject keystore specified in config

    @Value("${http.client.ssl.trust-store-password}")
    private String keyStorePassword;  // inject password from config

    @Value("${local.server.port}")
    private int port;

    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(
                        keyStore.getURL(),
                        keyStorePassword.toCharArray()
                ).build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        RestTemplateBuilder rtb = new RestTemplateBuilder()
                .requestFactory(() -> factory)
                .rootUri("https://localhost:" + port);
        this.restTemplate = new TestRestTemplate(rtb, null, null, TestRestTemplate.HttpClientOption.SSL);
    }

    @Test
    public void shouldPing() {
        ResponseEntity<String> result = restTemplate.getForEntity("/SrpingBootSSL-0.0.1-SNAPSHOT/test/echo", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Ok", result.getBody());
    }

//    @Test
//    void checkingHttpsConnection() {
//        TestRestTemplate restTemplate = new TestRestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/SrpingBootSSL-0.0.1-SNAPSHOT/test/echo", HttpMethod.GET, null, String.class);
//        System.out.println(response);
//        assertEquals(200, response.getStatusCodeValue());
//    }

}
