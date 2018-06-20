#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"

nohup $SCRIPT_DIR/submit-batch-jobs-execution.sh &
