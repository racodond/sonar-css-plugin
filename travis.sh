#!/bin/bash

mvn -B clean install

if [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "SQ_VERSION" == "LTS" ]; then
  mvn -B sonar:sonar -Dsonar.login=$SONAR_TOKEN
fi

cd its
mvn -B clean install -Dsonar.runtimeVersion=$SQ_VERSION
