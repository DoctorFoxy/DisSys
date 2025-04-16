#!/bin/bash

echo "make sure this service doesn't run already!"
echo "starting in background and printing output to soap-service.log"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
java -jar "$SCRIPT_DIR/soap-service.jar" >> "$SCRIPT_DIR/soap-service.log" 2>&1 &

echo "done, use 'cat $SCRIPT_DIR/soap-service.log' to see output"

