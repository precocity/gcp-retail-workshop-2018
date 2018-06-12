#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"
APPS_DIR=$SCRIPT_DIR/../apps
cd $APPS_DIR && mvn clean package
