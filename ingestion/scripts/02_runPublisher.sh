#!/usr/bin/env bash

PROJECT_NAME=${GOOGLE_CLOUD_PROJECT}
TOPIC_NAME=${GOOGLE_CLOUD_PROJECT}-sales-events

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"

#source the library
. $SCRIPT_DIR/common.sh

#VARS
JAVA=`which java`
APPS_DIR=$SCRIPT_DIR/../../apps
DATA_DIR=$SCRIPT_DIR/../../data
PUBLISHER_JAR_DIR=$APPS_DIR/Sales-Data-Publisher/target
PUBLISHER_JAR=$PUBLISHER_JAR_DIR/Sales-Data-Publisher.jar


#APP VARS
BATCH_DELAY_MS=1000
MAX_BATCH_SIZE=100
INPUT_FILE=$DATA_DIR/json.txt
FULL_PUBSUB_TOPIC="projects/$PROJECT_NAME/topics/$TOPIC_NAME"
OFFSET_FILE=$DATA_DIR/publisher_offset.txt

echo "Using topic: $FULL_PUBSUB_TOPIC"

#Action
echo "Starting publisher $PUBLISHER_JAR"
java -jar $PUBLISHER_JAR -d $BATCH_DELAY_MS -s $MAX_BATCH_SIZE -i $INPUT_FILE -t $FULL_PUBSUB_TOPIC -o $OFFSET_FILE
