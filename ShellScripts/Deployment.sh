#!/usr/bin/env bash

# Script for deployment of SpringBootProject to EC2 Tomcat instance using EC2 keys
# to give execute permission 
# chmod +x Deployment.sh


ARTIFACT_NAME="/home/nahid/workspace/merchantapp/source/bkash-merchant-app-mw/merchantapp-notification/target/merchantapp-notification-0.0.1-SNAPSHOT.war"
SERVER_NAME="ec2-user@ip-10-10-3-12.ap-southeast-1.compute.internal"
AWS_KEY="/home/nahid/workspace/merchantapp/aws/merchantapp.pem"

UPLOADED_ARTIFACT_NAME="/home/ec2-user/merchantapp-notification-0.0.1-SNAPSHOT.war"
RENAMED_ARTIFACT_NAME="/home/ec2-user/ROOT.war"



scp -i $AWS_KEY $ARTIFACT_NAME $SERVER_NAME:/home/ec2-user

echo "###################### Upload completed ########################################"

ssh -i $AWS_KEY $SERVER_NAME << EOF

sudo -i

echo "###################### Deployment Started #######################################"

mv $UPLOADED_ARTIFACT_NAME $RENAMED_ARTIFACT_NAME

rm -rf /opt/tomcat/webapps/ROOT*

cp -R $RENAMED_ARTIFACT_NAME /opt/tomcat/webapps/

chown -R tomcat:tomcat /opt/tomcat/webapps

echo "###################### Deployment End ########################################"

EOF
