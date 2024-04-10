#!/bin/bash

sudo yum update -y

if ! grep -q "^csye6225:" /etc/group; then
    sudo groupadd csye6225
fi

# Add the csye6225 user to the csye6225 group
sudo useradd -r -m -s /usr/sbin/nologin -g csye6225 csye6225

sudo chown -R csye6225:csye6225 /etc/systemd/system/

sudo systemctl daemon-reload

sudo yum install -y maven
java -version

sudo yum install -y java-17-openjdk-devel

# Set the path to Java 17
export JAVA_HOME="/usr/lib/jvm/java-17-openjdk"

# Update PATH to include Java 17
export PATH="$JAVA_HOME/bin:$PATH"

# Update PATH to include Java 17
export PATH="$JAVA_HOME/bin:$PATH"

source /etc/profile

# Verify Java version
java -version