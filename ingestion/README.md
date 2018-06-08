## Data Ingestion

## Overview
#### Architecture
![Architecture Diagram](assets/RetailWorkshop-DataIngestion-Arch.png)
#### Data Model
[To Be Added]
#### Dataflow Templates
* [Overview](https://cloud.google.com/dataflow/docs/templates/overview)
* [Source code](https://github.com/GoogleCloudPlatform/DataflowTemplates)
  - GCS Text to BigQuery
  - Pub/Sub to BigQuery
  - Pub/Sub to Datastore

## Hands-On
#### Pre-Requisites
* Completed the [setup](../setup/README.md) process.
* `cd gcp-retail-workshop-2018/ingestion`

#### Creating BigQuery Tables
Let's create one BigQuery table using the `bq` command and create the rest in the same manner using a helper script.
1. Create a dataset using the command below, be sure to replace the project-name:<br/>
```
bq mk [project-name]:retail_demo_warehouse
```
Following the execution of this command, you should see a success message like this: Dataset '[project-name]:retail_demo_warehouse' successfully created.

2. Create a BigQuery table, customer, using the schema definition in the codebase:<br/>
```
bq mk --table [project-name]:retail_demo_warehouse.customer bigquery/schemas/customer-bq.json
```
Following the execution of this command, you should see a success message like this: Table '[project-name]:retail_demo_warehouse.customer' successfully created.
3. You can verify whether the dataset and the table are appropriately created by navigating to BigQuery on the left nav of Google Cloud home page and then selecting the dataset and table on the BigQuery home page. <br/><br/>
4. Let's go ahead and create the rest of the tables required using a helper script that's provided in the codebase which essentially executes step #2 for all the remaining tables:
```
sh bigquery/scripts/create-bq-tables.sh
```
At this point all the tables will be created in the dataset in BigQuery. You can verify in the BigQuery home page.

#### Batch Load
