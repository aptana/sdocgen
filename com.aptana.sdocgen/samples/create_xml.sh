#!/bin/bash

set -e

java -jar ../libs/com.aptana.sdocgen.jar \
    --browser "Test Browser" --browser-version 1.0 \
    $1
