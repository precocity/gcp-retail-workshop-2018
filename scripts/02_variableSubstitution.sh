#!/usr/bin/env bash

find . -type f -exec sed -i "s/{USER}/$USER/g" {} +
find . -type f -exec sed -i "s/{DEVSHELL_PROJECT_ID}/$DEVSHELL_PROJECT_ID/g" {} +
chmod +x ~/gcp-retail-workshop-2018/airflow/ansible/airflow/ansible_install.sh

