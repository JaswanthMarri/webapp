#!/bin/bash

ls -l
sudo cp /tmp/csye6225.service /etc/systemd/system/csye6225.service
sudo mkdir /opt/app
sudo mkdir /var/log/myapp
#sudo touch /var/log/myapp/my-app.log
sudo chown -R csye6225:csye6225 /var/log/myapp
sudo cp /tmp/webapp-0.0.1-SNAPSHOT.jar /opt/app/webapp-0.0.1-SNAPSHOT.jar
#sudo cp /tmp/application.properties /opt/application.properties
cd /tmp
curl -O https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.85/bin/apache-tomcat-9.0.85.tar.gz
sudo mkdir /opt/tomcat
sudo chown -R csye6225:csye6225 /opt/
sudo -u csye6225 tar xzvf apache-tomcat-9*tar.gz -C /opt/tomcat --strip-components=1
sudo chmod +x /opt/tomcat/bin/*.sh
sudo sh -c 'chmod +x /opt/tomcat/bin/*.sh'
sudo cp /opt/tomcat/conf/server.xml /opt/tomcat/conf/server.xml.bak
sudo sed -i 's/8080/80/g' /opt/tomcat/conf/server.xml
sudo systemctl daemon-reload
sudo systemctl enable csye6225
sudo systemctl start csye6225
sudo firewall-cmd --add-port=8080/tcp --permanent
sudo firewall-cmd --reload
#cd ..
