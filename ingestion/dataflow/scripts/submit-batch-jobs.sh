#!/bin/bash

export PROJECT_NAME=${GOOGLE_CLOUD_PROJECT}
export BUCKET_NAME=${GOOGLE_CLOUD_PROJECT}-dataflow

submitBatchJob () {
  FEED=$1
  BQ_TABLE=`echo $FEED | sed 's/-/_/g'`
  ID_SUFFIX=`date +%Y-%m-%d_%H:%M:%S`
  JOB_ID=${FEED}Load_${ID_SUFFIX}

  echo "Submitting $FEED batch load Dataflow job to load the $BQ_TABLE table using job id: $JOB_ID"

  gcloud dataflow jobs run $JOB_ID \
  --gcs-location=gs://$BUCKET_NAME/gcs-to-bigquery/templates/FileToBigQuery.json \
  --parameters javascriptTextTransformFunctionName=transform,JSONPath=gs://$BUCKET_NAME/schemas/${FEED}.json,javascriptTextTransformGcsPath=gs://$BUCKET_NAME/udfs/${FEED}.js,inputFilePattern=gs://precocity-retail-workshop-2018-bucket/staged/${BQ_TABLE}/${BQ_TABLE}.*.csv,outputTable=$PROJECT_NAME:retail_demo_warehouse.${BQ_TABLE},bigQueryLoadingTemporaryDirectory=gs://$BUCKET_NAME/gcs-to-bigquery/tmp
}

echo "Using PROJECT_NAME: ${PROJECT_NAME}, BUCKET_NAME: ${BUCKET_NAME}"

submitBatchJob channel
submitBatchJob classification
submitBatchJob customer-address
submitBatchJob customer-employee-relationship
submitBatchJob customer-favorites
submitBatchJob customer-loyalty
submitBatchJob customer-preferences
submitBatchJob customer-relationship
submitBatchJob customer-segment

echo ""
echo ""
echo "Going to sleep for a bit to account for default quota. In production you should set quotas in advance so that you have enough headroom to run your expected number of simultaneous jobs."
sleep 120

submitBatchJob department
submitBatchJob division
submitBatchJob employee
submitBatchJob item
submitBatchJob store
submitBatchJob style
submitBatchJob sub-classification
submitBatchJob vendor
