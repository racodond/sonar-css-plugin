#!/bin/bash

set -euo pipefail

mvn -B clean install

cd its
mvn -B clean install -Dsonar.runtimeVersion=$SQ_VERSION

