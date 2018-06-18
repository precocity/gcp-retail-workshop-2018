import os
from datetime import datetime, timedelta

from airflow import DAG
from airflow.operators.bash_operator import BashOperator

from gcs_handler import list_gcs_folder

airflow_home = os.environ.get('AIRFLOW_HOME')

channel_dataflow_bash_command = airflow_home + '/scripts/bash_loads.sh '

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
                                   bash_command=channel_dataflow_bash_command,
                                   dag=dag)
