#!/bin/bash

bq mk --table retail_demo_warehouse.channel bigquery/schemas/channel-bq.json
bq mk --table retail_demo_warehouse.classification bigquery/schemas/classification-bq.json
bq mk --table retail_demo_warehouse.customer_address bigquery/schemas/customer-address-bq.json
bq mk --table retail_demo_warehouse.customer_employee_relationship bigquery/schemas/customer-employee-relationship-bq.json
bq mk --table retail_demo_warehouse.customer_favorites bigquery/schemas/customer-favorites-bq.json
bq mk --table retail_demo_warehouse.customer_loyalty bigquery/schemas/customer-loyalty-bq.json
bq mk --table retail_demo_warehouse.customer_preferences bigquery/schemas/customer-preferences-bq.json
bq mk --table retail_demo_warehouse.customer_relationship bigquery/schemas/customer-relationship-bq.json
bq mk --table retail_demo_warehouse.customer_segment bigquery/schemas/customer-segment-bq.json
bq mk --table retail_demo_warehouse.department bigquery/schemas/department-bq.json
bq mk --table retail_demo_warehouse.division bigquery/schemas/division-bq.json
bq mk --table retail_demo_warehouse.employee bigquery/schemas/employee-bq.json
bq mk --table retail_demo_warehouse.item bigquery/schemas/item-bq.json
bq mk --table retail_demo_warehouse.sales_events bigquery/schemas/sales-events-bq.json
bq mk --table retail_demo_warehouse.store bigquery/schemas/store-bq.json
bq mk --table retail_demo_warehouse.store_sales bigquery/schemas/store-sales-bq.json
bq mk --table retail_demo_warehouse.style bigquery/schemas/style-bq.json
bq mk --table retail_demo_warehouse.sub_classification bigquery/schemas/sub-classification-bq.json
bq mk --table retail_demo_warehouse.vendor bigquery/schemas/vendor-bq.json

## if not created manually, then uncomment below
## bq mk --table retail_demo_warehouse.customer bigquery/schemas/customer-bq.json
