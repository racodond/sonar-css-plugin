#!/bin/bash

set -euo pipefail

function installTravisTools {
  mkdir ~/.local
  curl -sSL https://github.com/SonarSource/travis-utils/tarball/v21 | tar zx --strip-components 1 -C ~/.local
  source ~/.local/bin/install
}

case "$TESTS" in

CI)
  mvn verify -B -e -V
  ;;

IT-DEV)
  installTravisTools

  mvn package -Dsource.skip=true -Denforcer.skip=true -Danimal.sniffer.skip=true -Dmaven.test.skip=true

  build_snapshot "SonarSource/sonarqube"

  cd its/plugin
  mvn -Dsonar.runtimeVersion="DEV" -Dmaven.test.redirectTestOutputToFile=false install
  ;;

IT-LTS)
  installTravisTools

  mvn package -Dsource.skip=true -Denforcer.skip=true -Danimal.sniffer.skip=true -Dmaven.test.skip=true

  cd its/plugin
  mvn -Dsonar.runtimeVersion="LTS" -Dmaven.test.redirectTestOutputToFile=false install
  ;;

esac
