#!/bin/sh

# server shut down
cd /home1/irteam/apps/tomcat/bin
sudo ./shutdown.sh

# file delete
cd ../webapps
sudo rm -r board-1.0.0-BUILD-SNAPSHOT.war
sudo rm -r board
sudo rm -r board-1.0.0-BUILD-SNAPSHOT

# git pull
cd /home1/irteam/repositories/HyunHye
sudo git pull
#maven build
sudo mvn clean package

# war file copy
cd target
sudo cp board-1.0.0-BUILD-SNAPSHOT.war /home1/irteam/apps/tomcat/webapps

#tomcat restart
cd /home1/irteam/apps/tomcat/bin
sudo ./startup.sh

