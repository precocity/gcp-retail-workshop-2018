#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"

#source the library
. $SCRIPT_DIR/common.sh

#VARS
DATA_DIR=$SCRIPT_DIR/../../data

#Action
echo "Copying sales data to $DATA_DIR"
mkdir $DATA_DIR
gsutil cp gs://precocity-retail-workshop-2018-bucket/raw/combined_sales_data/json.txt $DATA_DIR

echo "Creating topic $PUBSUB_TOPIC"
gcloud pubsub topics create $PUBSUB_TOPIC

