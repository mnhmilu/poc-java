FROM tomcat:9-jdk11
ADD ./target/demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

COPY server.xml /usr/local/tomcat/conf/

COPY keystore.jks /usr/local/tomcat/temp/
EXPOSE 8443
#CMD chmod +x /usr/local/tomcat/bin/catalina.sh
#CMD ["catalina.sh", "run"]


