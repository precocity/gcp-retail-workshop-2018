#!/bin/bash

if [ $# -ne 1 ]; then
  echo "Expected argument: unique-dataflow-bucket-name"
  exit 1
fi

BUCKET_NAME=$1

gsutil cp -r dataflow/schemas gs://$BUCKET_NAME/
gsutil cp -r dataflow/udfs gs://$BUCKET_NAME/
