#!/bin/bash
sudo pip install apache-libcloud
sudo pip install ansible
sudo pip install PyCrypto
export GCE_INI_PATH=./gce.ini
export ANSIBLE_INVENTORY=./host