#!/bin/bash

set -euo pipefail

mvn -B clean install -Pits -Dsonar.runtimeVersion=$SQ_VERSION

if [ "$SQ_VERSION" == "LTS" ]; then

    mvnCommand = "mvn -B sonar:sonar"
    commonArgs = "-Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_TOKEN -Dsonar.organization=racodond-github"
    githubAgs = "-Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST -Dsonar.github.repository=$TRAVIS_REPO_SLUG -Dsonar.github.oauth=$GITHUB_TOKEN"
    branchArgs = ''

    if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
        if [ "$TRAVIS_BRANCH" != "master" ]; then
           branchArgs = "-Dsonar.branch.name=$TRAVIS_BRANCH -Dsonar.branch.target=master"
        fi
        $mvnCommand $commonArgs $branchArgs
    else
        $mvnCommand $commonArgs $githubArgs

    fi

fi
