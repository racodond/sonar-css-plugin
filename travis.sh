#!/bin/bash

set -euo pipefail

mvn -B clean install -Pits -Dsonar.runtimeVersion=$SQ_VERSION

if [ "$TRAVIS_PULL_REQUEST" != "false" ] && [ "$SQ_VERSION" == "LTS" ]; then
  mvn -B sonar:sonar \
      -Dsonar.host.url=https://sonarcloud.io \
      -Dsonar.analysis.mode=preview \
      -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST \
      -Dsonar.github.repository=$TRAVIS_REPO_SLUG \
      -Dsonar.github.oauth=$GITHUB_TOKEN \
      -Dsonar.login=$SONAR_TOKEN \
      -Dsonar.organization=racodond-github

elif [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$SQ_VERSION" == "LTS" ]; then
  mvn -B sonar:sonar \
      -Dsonar.host.url=https://sonarcloud.io \
      -Dsonar.login=$SONAR_TOKEN \
      -Dsonar.organization=racodond-github
fi
