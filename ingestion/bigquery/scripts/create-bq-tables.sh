#!/bin/bash

if [ $# -ne 1 ]; then
        echo "Exepected 1 argument: project-name"
        exit 1
fi

PROJECT_NAME=$1

bq mk --table $PROJECT_NAME:retail_demo_warehouse.channel bigquery/schemas/channel-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.classification bigquery/schemas/classification-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.customer-address bigquery/schemas/customer-address-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.customer-employee-relationship bigquery/schemas/customer-employee-relationship-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.customer-favorites bigquery/schemas/customer-favorites-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.customer-loyalty bigquery/schemas/customer-loyalty-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.customer-preferences bigquery/schemas/customer-preferences-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.customer-relationship bigquery/schemas/customer-relationship-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.customer-segment bigquery/schemas/customer-segment-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.department bigquery/schemas/department-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.division bigquery/schemas/division-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.employee bigquery/schemas/employee-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.item bigquery/schemas/item-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.sales-header bigquery/schemas/sales-header-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.sales-line-item bigquery/schemas/sales-line-item-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.store bigquery/schemas/store-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.style bigquery/schemas/style-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.sub-classification bigquery/schemas/sub-classification-bq.json
bq mk --table $PROJECT_NAME:retail_demo_warehouse.vendor bigquery/schemas/vendor-bq.json

## if not created manually, then uncomment below
## bq mk --table $PROJECT_NAME:retail_demo_warehouse.customer bigquery/schemas/customer-bq.json
