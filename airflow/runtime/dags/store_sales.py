import os
from datetime import datetime, timedelta

from airflow import DAG
from airflow.contrib.operators.bigquery_operator import BigQueryOperator
from airflow.operators.bash_operator import BashOperator

airflow_home = os.environ.get('AIRFLOW_HOME')

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2018, 6, 16),
    'depends_on_past': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5)
}

dag = DAG('bq_stores_sales_processing',
          schedule_interval='*/10 * * * *',
          catchup=False,
          default_args=default_args)

#bq_store_sales_task = BashOperator(task_id='bq_store_sales_bash',
#                                   bash_command='bq query --use_legacy_sql=False < ' + airflow_home + '/scripts/store_sales.sql ',
#                                   dag=dag)

bq_store_sales_task = BigQueryOperator(
     task_id='bq_store_sales',
     bql="INSERT `retail_demo_warehouse.store_sales` (store_id, store_name, lat_long, transaction_date, total_sales, updated_timestamp) SELECT store.store_id, store.store_name, CONCAT(CAST(store.latitude AS STRING), CONCAT(',', CAST(store.longitude AS STRING))) AS lat_long, sales.transaction_date, sales.store_sales AS total_sales, CURRENT_TIMESTAMP() as updated_timestamp FROM ( SELECT transaction_date, CAST(store_id AS INT64) AS store_id, ROUND(SUM((SELECT SUM(item_price_each * quantity) FROM UNNEST(lineitems))),2) AS store_sales FROM `retail_demo_warehouse.sales_events` GROUP BY transaction_date, store_id ) sales JOIN `retail_demo_warehouse.store` store ON store.store_id = sales.store_id",
     sql=None,
     destination_dataset_table=False,
     bigquery_conn_id='google_cloud_default',
     use_legacy_sql=False,
     udf_config=False,
     dag=dag)
