import os
from datetime import datetime, timedelta

from airflow import DAG
from airflow.operators.bash_operator import BashOperator

from gcs_handler import list_gcs_folder

airflow_home = os.environ.get('AIRFLOW_HOME')

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2018, 5, 18),
    'depends_on_past': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5)
}

dag = DAG('channel_processing',
          schedule_interval=None,
          catchup=False,
          default_args=default_args)

# Add the terminating forward slash to stick to the single folder in the bucket
file_list = list_gcs_folder('staged/channel/', 'precocity-retail-workshop-2018-bucket')
for file_name in file_list:
    channel_df_task = BashOperator(task_id='channel_df.' + file_name,
                                   bash_command='gcloud dataflow jobs run channel-load \
    --gcs-location=gs://rajiv-dataflow/gcs-to-bigquery/templates/FileToBigQuery.json \
    --zone=us-central1-c \
    --parameters javascriptTextTransformFunctionName=transform,JSONPath=gs://rajiv-dataflow/gcs-to-bigquery/schemas/channel.json,javascriptTextTransformGcsPath=gs://rajiv-dataflow/udfs/channel.js,inputFilePattern=gs://precocity-retail-workshop-2018-bucket/staged/channel/{channel_file_name},outputTable=ziptie-ulta-demo:retail_demo_warehouse.channel,bigQueryLoadingTemporaryDirectory=gs://rajiv-dataflow/gcs-to-bigquery/tmpn'.format(
                                       channel_file_name=file_name),
                                   dag=dag)
