#!/bin/bash

sudo cp /tmp/config.yml /etc/google-cloud-ops-agent/config.yaml
sudo systemctl restart google-cloud-ops-agent

