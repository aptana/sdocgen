#!/bin/bash

set -e

rm -rf ${1}_docs
mkdir ${1}_docs
cp $1 ${1}_docs
cd ${1}_docs
java -jar ../../libs/saxon8.jar \
    $1 \
    ../../html/generate_documentation.xslt \
    ReferenceName=SDocGen \
    ReferenceDisplayName="SDocGen Test"
cp ../../html/css/*.* .
cp ../../html/images/*.* .
cp ../../html/js/*.* .
cd ..
