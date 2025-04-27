#!/bin/bash

# Create results directory if it doesn't exist
mkdir -p ~/jmeter/results

# Run JMeter in non-GUI mode
jmeter -n \
  -t ~/jmeter/US_East_test.jmx \
  -l ~/jmeter/results/results.jtl \
  -j ~/jmeter/results/jmeter.log

# Optional: upload results to central location (e.g., your local PC or S3)
# scp ~/jmeter/results/results.jtl youruser@yourmachine:/path/to/store/results/
