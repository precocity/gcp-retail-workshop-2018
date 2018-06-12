#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"
APPS_DIR=$SCRIPT_DIR/../../apps
PUBLISHER_JAR_DIR=$APPS_DIR/Sales-Data-Publisher/target
PUBLISHER_JAR=$PUBLISHER_JAR_DIR/Sales-Data-Publisher.jar

echo "Starting publisher $PUBLISHER_JAR"

