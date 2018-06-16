#!/bin/bash
sudo pip install apache-libcloud
sudo pip install ansible
sudo pip install PyCrypto
export GCE_INI_PATH='~/gcp-retail-workshop-2018/airflow/ansible/airflow/gce.ini'
export ANSIBLE_INVENTORY='~/gcp-retail-workshop-2018/airflow/ansible/airflow/hosts'