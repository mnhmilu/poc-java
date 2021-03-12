//Create Selfsigned keystore
### test
keytool -genkey -keyalg RSA -alias selfsigned -dname "CN=stackoverflow,OU=Hakan,O=Hakan,C=NL" -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore keystore.jks -storepass password -validity 360 -keysize 2048

//Export the public key
keytool -v -export -file keystore.cer -keystore keystore.jks -alias selfsigned

//Import and create truststore
keytool -import -alias keystore -file keystore.cer -keystore test.truststore



sudo docker build -t mywebapp .
sudo docker run -p 8443:8443 mywebapp

//inspect docker container 

sudo docker ps

sudo docker exec -it 024af579e420 bash
