#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"
DATA_DIR=$SCRIPT_DIR/../../data
echo "Copying sales data to $DATA_DIR"
mkdir $DATA_DIR
gsutil cp gs://precocity-retail-workshop-2018-bucket/raw/combined_sales_data/json.txt $DATA_DIR
