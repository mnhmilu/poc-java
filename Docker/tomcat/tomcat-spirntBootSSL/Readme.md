### Docker file 

```FROM tomcat:9-jdk11
ADD ./target/demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

COPY server.xml /usr/local/tomcat/conf/

COPY keystore.jks /usr/local/tomcat/temp/
EXPOSE 8443
```



### Keystore and TrustStore Generation (Selfsigned )


Create Keystore 

```sudo keytool -genkey -keyalg RSA -alias selfsigned -dname "CN=stackoverflow,OU=Hakan,O=Hakan,C=NL" -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore keystore.jks -storepass password -validity 360 -keysize 2048```

Export the public key

```sudo keytool -v -export -file keystore.cer -keystore keystore.jks -alias selfsigned```

Import and create truststore

```sudo keytool -import -alias keystore -file keystore.cer -keystore test.truststore```

### Tomcat Docker run with Spring boot API with SSL Configuration by loading custom server.xml and context.xml file 

```sudo docker build -t mywebapp .```

```sudo docker run -p 8443:8443 mywebapp```

//inspect docker container 

```sudo docker ps```

```sudo docker exec -it 024af579e420 bash```


### Docker based tocmat 9 configuration

server.xml example

```<Connector port="8443" maxThreads="150" scheme="https" secure="true" SSLEnabled="true" keystoreFile="/usr/local/tomcat/temp/keystore.jks" keystorePass="password" clientAuth="false" keyAlias="selfsigned" sslProtocol="TLS"/>```

### Client Applicaiton Setup

```@Test
void checkingHttpsConnectionForMSFReport() {
    ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/demo-0.0.1-SNAPSHOT/test/echo", HttpMethod.GET, null, String.class);
    System.out.println(response);
    Assertions.assertEquals(200, response.getStatusCodeValue());
}

http.client.ssl.trust-store=classpath:truststore/truststore.jks
http.client.ssl.trust-store-password=password

@Value("${http.client.ssl.trust-store}")
    private Resource keyStore;
    @Value("${http.client.ssl.trust-store-password}")
    private String keyStorePassword;

    @Bean
    RestTemplate restTemplate() throws Exception {
         File keyStoreFilePath = new File(keyStore.getURI());
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(
                        keyStoreFilePath,
                        keyStorePassword.toCharArray()
                ).build();

        SSLConnectionSocketFactory socketFactory =
                new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory).build();

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
```


