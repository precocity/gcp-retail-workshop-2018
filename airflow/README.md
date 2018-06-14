# Airflow Hands-On Lab
## Overview
This lab is designed to demonstrate how to use Terraform to create a compute instance and then configure an Airflow instance using Ansible.

Precocity uses Terraform for overall infrastrucutre deployment and Ansible for the installation and configuration management of resources.

## Hands-On
### Pre-Requisites
* Cloud Shell
* `$ git clone https://github.com/precocity/gcp-retail-workshop-2018.git`

>Note: Unless otherwise explicitly stated, all the commands below are to be executed in Cloud Shell as-is. If you have already run the `git clone` command, it is not necessary to do it again.

>Note: Once you are done with all the exercises, please go through the last Cleanup exercise to review and make sure any running resources are terminated.

---
### Exercise 1: Creating the Airflow Instance with Terraform

Expected Time: 5 mins

In this section you will configure Terraform and create a service account key for use with Ansible.

***Step 1***
From Google Cloud Shell run the following commands:

* `$ gcloud config set project [PROJECT_ID]`

Replace [PROJECT_ID] with your project name. From this point on we can reference your project name using the $DEVSHELL_PROJECT_ID environment variable.

* `cd gcp-retail-workshop-2018/airflow/terraform/airflow`

This is the folder from which we'll install and configure Terraform. Run the following commands

* `$ wget https://releases.hashicorp.com/terraform/0.11.7/terraform_0.11.7_linux_amd64.zip`
* `$ unzip terraform_0.11.7_linux_amd64.zip -d .`
* `$ ./terraform -v`

The output from the last command should display `Terraform v0.11.7`.



