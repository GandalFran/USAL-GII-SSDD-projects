#!/bin/bash

# src: https://gist.github.com/drmalex07/e6e99dad070a78d5dab24ff3ae032ed1
cat << EOF | sudo tee /etc/systemd/system/tomcat.service
[Unit]
Description=Tomcat - instance %i
After=syslog.target network.target

[Service]
Type=forking

ExecStart=/opt/tomcat7/bin/startup.sh
ExecStop=/opt/tomcat7/bin/shutdown.sh

#RestartSec=5
#Restart=always

[Install]
WantedBy=multi-user.target
EOF

systemctl enable tomcat.service
systemctl start tomcat.service
systemctl status tomcat.service