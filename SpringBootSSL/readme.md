
## Step 1: Pre-pare Backend

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

### Docker based tomcat 9 configuration

server.xml example

```<Connector port="8443" maxThreads="150" scheme="https" secure="true" SSLEnabled="true" keystoreFile="/usr/local/tomcat/temp/keystore.jks" keystorePass="password" clientAuth="false" keyAlias="selfsigned" sslProtocol="TLS"/>```

### Tomcat Docker run with Spring boot API with SSL Configuration by loading custom server.xml and context.xml file

> IDE->Maven-> Install (will generate war file)

```
sudo docker build -t mywebapp .

sudo docker run -p 8443:8443 mywebapp

sudo docker ps

sudo docker exec -it 024af579e420 bash
```
##Step 2: To Run Test as a client 

- Clean -> Build -> Install
- Make sure truststore copied into test->resource folder
- Mind test report at target -> surefire-reports -> *.txt file


