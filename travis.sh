#!/bin/bash

set -euo pipefail

function configureTravis {
  mkdir ~/.local
  curl -sSL https://github.com/SonarSource/travis-utils/tarball/v25 | tar zx --strip-components 1 -C ~/.local
  source ~/.local/bin/install
}
configureTravis

case "$TESTS" in

CI)
  mvn verify -B -e -V
  ;;

IT-DEV)
  mvn package -Dsource.skip=true -Denforcer.skip=true -Danimal.sniffer.skip=true -Dmaven.test.skip=true -e -B

  cd its/plugin
  mvn -Dsonar.runtimeVersion="DEV" -Dmaven.test.redirectTestOutputToFile=false verify -e -B -V
  ;;

IT-LTS)
  mvn package -Dsource.skip=true -Denforcer.skip=true -Danimal.sniffer.skip=true -Dmaven.test.skip=true -e -B

  cd its/plugin
  mvn -Dsonar.runtimeVersion="LTS" -Dmaven.test.redirectTestOutputToFile=false verify -e -B
  ;;

esac
