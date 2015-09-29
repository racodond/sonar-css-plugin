SonarQube CSS Plugin
====================

[![Build Status](https://api.travis-ci.org/SonarCommunity/sonar-css.svg)](https://travis-ci.org/SonarCommunity/sonar-css)

Project homepage:
https://github.com/SonarCommunity/sonar-css

Issue tracking:
https://github.com/SonarCommunity/sonar-css/issues

CI builds:
https://travis-ci.org/SonarCommunity/sonar-css

Download:
https://github.com/SonarCommunity/sonar-css/releases

## Description
This plugin enables code QA analysis of CSS source code within [SonarQube](http://www.sonarqube.org):

 * Computes metrics: lines of code, number of CSS rules, complexity, etc.
 * Validates your CSS code
 * Performs more than 50 checks such as: Stylesheets should not contain too many selectors, Empty declarations should be removed, Box model size should be carefully reviewed, etc.

## Usage
### Getting started in two minutes
Download the [package](https://github.com/racodond/package-test-sonarqube-css/archive/master.zip) and follow the instructions of the [README file](https://github.com/racodond/package-test-sonarqube-css/blob/master/README.md).
This package contains the SonarQube server with the CSS plugin, the analyzer (SonarQube Runner) and a sample project.

### Installing and configuring your SonarQube platform from scratch
1. [Install](http://docs.sonarqube.org/display/SONAR/Setup+and+Upgrade) SonarQube
2. Install the CSS plugin either by a [direct download](https://github.com/SonarCommunity/sonar-css/releases) or through the [update center](http://docs.sonarqube.org/display/SONAR/Update+Center).
3. Install your [favorite analyzer](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis) (SonarQube Runner, Maven, etc.) and analyze your code.

## Metrics
### Functions
Number of rules.

### Complexity
The following elements increment the complexity by one:

* Class selector
* ID selector
* Attribute selector
* Type selector
* Pseudo-class selector
* At-rule

### Complexity/function
It computes the complexity/rule, meaning the average number of selectors per rule.

It gives a measurement on how specific the selectors are.

As the computation of complexity in the CSS plugin is still in its early stage, it can be turned off if necessary at global and project levels.

