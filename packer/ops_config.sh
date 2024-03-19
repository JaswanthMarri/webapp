#!/bin/bash

sudo cp /tmp/config.yml /etc/google-cloud-ops-agent/config.yaml

sudo systemctl enable google-cloud-ops-agent
sudo systemctl start google-cloud-ops-agent

