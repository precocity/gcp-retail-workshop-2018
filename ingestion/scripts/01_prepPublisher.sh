#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"

#source the library
. $SCRIPT_DIR/common.sh

#VARS
DATA_DIR=$SCRIPT_DIR/../../data
DATA_FILE=$DATA_DIR/json.txt

#Action
echo "Copying sales data to $DATA_DIR"
if [ ! -d $DATA_DIR ]; then
  mkdir $DATA_DIR
fi
if [ ! -f $DATA_FILE ]; then
    gsutil cp gs://precocity-retail-workshop-2018-bucket/raw/combined_sales_data/json.txt $DATA_DIR
else
    echo "$DATA_FILE exists. Not downloading."
fi

echo "Creating topic $PUBSUB_TOPIC"
gcloud pubsub topics create $PUBSUB_TOPIC

