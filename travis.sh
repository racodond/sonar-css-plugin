#!/bin/bash

mvn clean install

if [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
  mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN
fi
