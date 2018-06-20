#!/bin/bash

BUCKET_NAME="${GOOGLE_CLOUD_PROJECT}-dataflow"

gsutil cp -r dataflow/schemas gs://$BUCKET_NAME/
gsutil cp -r dataflow/udfs gs://$BUCKET_NAME/
