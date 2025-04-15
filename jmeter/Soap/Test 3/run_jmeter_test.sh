#!/bin/bash

# Define paths
RESULTS_DIR=~/jmeter/soap/results
JTL_FILE="$RESULTS_DIR/results.jtl"
LOG_FILE="$RESULTS_DIR/jmeter.log"
JMX_FILE=us_west_soap_test3.jmx
JMETER_BIN=../../apache-jmeter-5.6.3/bin/jmeter

# Ensure results directory exists
mkdir -p "$RESULTS_DIR"

# Clear old results
echo "Clearing old result and log files..."
> "$JTL_FILE"   # truncate file if exists, create if not
> "$LOG_FILE"

# Set heap size for JMeter (optional tuning)
export HEAP="-Xms256m -Xmx512m"

# Run JMeter test
echo "Starting JMeter test with plan: $JMX_FILE"
"$JMETER_BIN" -n \
  -t "$JMX_FILE" \
  -l "$JTL_FILE" \
  -j "$LOG_FILE"

# Optional: upload results
# scp "$JTL_FILE" youruser@yourmachine:/path/to/store/results/
