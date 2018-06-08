# Data Ingestion Hands-On Lab
## Overview
### Architecture
![Architecture Diagram](assets/RetailWorkshop-DataIngestion-Arch.png)
### Data Model
[To Be Added]
### Dataflow Templates
* [Overview](https://cloud.google.com/dataflow/docs/templates/provided-templates)
* [Source code](https://github.com/GoogleCloudPlatform/DataflowTemplates)
  - GCS Text to BigQuery
  - Pub/Sub to BigQuery
  - Pub/Sub to GCS Text

## Hands-On
### Pre-Requisites
* Complete the [setup](../setup/README.md) process.
* `cd gcp-retail-workshop-2018/ingestion`

_Tip: For the following exercises and the commands that need to be executed as part of them, it is recommended to copy the commands to a text editor first and replace the placeholders with the appropriate values and then copy-paste the updated command at the prompt._

### Exercise 1: Creating BigQuery Tables

Expected Time: TBD

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

3. You can verify whether the dataset and the table are appropriately created by navigating to BigQuery on the left nav of Google Cloud home page and then selecting the dataset and table on the BigQuery home page.

4. Let's go ahead and create the rest of the tables required using a helper script that's provided in the codebase which essentially executes step #2 for all the remaining tables: (the required arg to this script is the project-name)
```
sh bigquery/scripts/create-bq-tables.sh [project-name]
```
At this point all the tables will be created in the dataset in BigQuery. You can verify in the BigQuery home page.

### Exercise 2: Deploy Dataflow Jobs

Expected Time: TBD

Let's manually deploy both batch and streaming dataflow jobs using the Google provided templates to understand how they readily provide capabilities to address most common ingestion scenarios.
1. Clone the Google provided Dataflow templates project:
```
cd ~/
git clone https://github.com/GoogleCloudPlatform/DataflowTemplates.git
cd DataflowTemplates
```
2. Create a bucket in GCS to deploy the Dataflow jobs:<br/>
_Note: The bucket that you will be creating in this step will be used in rest of the steps_
```
gsutil mb gs://[unique-dataflow-bucket-name]
```
3. Build & deploy the GCS Text -> BigQuery Dataflow job:
```
mvn compile exec:java \
-Dexec.mainClass=com.google.cloud.teleport.templates.TextIOToBigQuery \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args=" \
--project=[project-name] \
--stagingLocation=gs://[unique-dataflow-bucket-name]/gcs-to-bigquery/staging \
--tempLocation=gs://[unique-dataflow-bucket-name]/gcs-to-bigquery/tmp \
--templateLocation=gs://[unique-dataflow-bucket-name]/gcs-to-bigquery/templates/FileToBigQuery.json \
--runner=DataflowRunner"
```
You should see a "Build Successful" message upon successful deployment of the Dataflow job.

4. Build & deploy the Pub/Sub -> BigQuery Dataflow job:
```
mvn compile exec:java \
-Dexec.mainClass=com.google.cloud.teleport.templates.PubSubToBigQuery \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args=" \
--project=[project-name] \
--stagingLocation=gs://[unique-dataflow-bucket-name]/pubsub-to-bigquery/staging \
--tempLocation=gs://[unique-dataflow-bucket-name]/pubsub-to-bigquery/tmp \
--templateLocation=gs://[unique-dataflow-bucket-name]/pubsub-to-bigquery/templates/PubSubToBigQuery.json \
--runner=DataflowRunner"
```
You should see a "Build Successful" message upon successful deployment of the Dataflow job.

5. Build & deploy Pub/Sub -> GCS Text Dataflow job:
```
mvn compile exec:java \
-Dexec.mainClass=com.google.cloud.teleport.templates.PubsubToText \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args=" \
--project=[project-name] \
--stagingLocation=gs://[unique-dataflow-bucket-name]/pubsub-to-gcs/staging \
--tempLocation=gs://[unique-dataflow-bucket-name]/pubsub-to-gcs/tmp \
--templateLocation=gs://[unique-dataflow-bucket-name]/pubsub-to-gcs/templates/PubSubToFile.json \
--runner=DataflowRunner"
```
You should see a "Build Successful" message upon successful deployment of the Dataflow job.

6. At this point all the required Dataflow jobs are deployed to GCS and you can verify by navigating to your bucket and the respective folders.

7. Next step is to deploy application specific logic (parsing & transformations) that is required by the Dataflow jobs. There is helper script that copies the application specific code to GCS. Execute the following:
```
cd ~/gcp-retail-workshop-2018/ingestion
sh dataflow/scripts/deploy-app-code.sh [unique-dataflow-bucket-name]
```

### Exercise 3: Batch Load

Expected Time: TBD

### Exercise 4: StackDriver Logs, Monitoring

Expected Time: TBD

### Exercise 5: Streaming

Expected Time: TBD
