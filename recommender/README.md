# Recommendations on GCP with TensorFlow and WALS

This project deploys a solution for a recommendation service on GCP, using the WALS
algorithm in TensorFlow.  Components include:

- Recommendation model code, and scripts to train and tune the model on ML Engine
- A REST endpoint using [Google Cloud Endpoints](https://cloud.google.com/endpoints/) for serving recommendations
- An Airflow server running on GKE for orchestrating scheduled model training


## Before you begin

1. Create a new [Cloud Platform project](https://console.cloud.google.com/projectcreate).

2. [Enable billing](https://support.google.com/cloud/answer/6293499#enable-billing)
   for your project.

3. [Enable APIs](https://console.cloud.google.com/apis/dashboard) for
  * BigQuery API
  * Cloud Resource Manager
  * ML Engine
  * App Engine Admin
  * Container Engine
  * Cloud SQL API


## Initial Setup

### Use Google Cloud Shell

1. Open the [Google Cloud Platform
   Console](https://console.cloud.google.com/?_ga=1.38191587.1500870598.1489443487).

2. Click the Cloud Shell icon at the top of the screen.
![Cloud Shell](https://cloud.google.com/shell/docs/images/shell_icon.png)

3. Git clone this repo

		git clone https://github.com/precocity/gcp-retail-workshop-2018.git 


## Extract Data from BigQuery

This tutorial leverages a recently releasted Google Analytics data set from Google Merchandise Store...

1. Make a GCS bucket with the name recommender_[YOUR-PROJECT-ID]:

		BUCKET=gs://recommender_$(gcloud config get-value project 2> /dev/null)
		gsutil mb ${BUCKET}

2. Create a dataset :

		cd gcp-retail-workshop-2018/recommender
		bq mk GA360_MerchStore
		bq query --replace=true --destination_table=GA360_MerchStore.implicit_feedback "`cat implicit_feedback_query.sql`"
3. Extract newly created table into CSV into GCS BUCKET

		TRAIN_FILE=$BUCKET/data/recommendation_events.csv
		bq extract GA360_MerchStore.implicit_feedback $TRAIN_FILE
		
## Train Recommender using Cloud ML Engine
I'll need to add a lot more here

1. Setup some ENV variables & change directory

		JOBNAME=wals_$(date -u +%y%m%d_%H%M%S)
		REGION=us-east1
		ARGS="--data-type web_views --train-files ${TRAIN_FILE} --verbose-logging $@"
		cd wals_ml_engine
2. Execute Cloud MLE command

		gcloud ml-engine jobs submit training ${JOBNAME} \
		 --region $REGION \
		 --scale-tier=CUSTOM \
		 --job-dir=$BUCKET \
		 --module-name trainer.task \
		 --package-path trainer \
		 --config trainer/config/config_train.json \
		 -- \
		 ${ARGS}
			

