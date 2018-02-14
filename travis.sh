#!/bin/bash

set -euo pipefail

mvn -B clean install -Pits -Dsonar.runtimeVersion=$SQ_VERSION

if [ "$SQ_VERSION" == "LTS" ]; then

    mvnCommand='mvn -B sonar:sonar'
    commonArgs="-Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_TOKEN -Dsonar.organization=racodond-github"
    githubArgs="-Dsonar.analysis.mode=preview -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST -Dsonar.github.repository=$TRAVIS_REPO_SLUG -Dsonar.github.oauth=$GITHUB_TOKEN"
    branchArgs=''

    if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
        if [ "$TRAVIS_BRANCH" != "master" ]; then
           branchArgs="-Dsonar.branch.name=$TRAVIS_BRANCH -Dsonar.branch.target=master"
        fi
        eval $mvnCommand $commonArgs $branchArgs
    else
        eval $mvnCommand $commonArgs $githubArgs
    fi

fi
