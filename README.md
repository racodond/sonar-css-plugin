SonarQube CSS Plugin
====================
[![Build Status](https://api.travis-ci.org/SonarQubeCommunity/sonar-css.svg)](https://travis-ci.org/SonarQubeCommunity/sonar-css) [![Nemo Quality Gate status](https://nemo.sonarqube.org/api/badges/gate?key=org.codehaus.sonar-plugins.css%3Acss)](https://nemo.sonarqube.org/overview?id=org.codehaus.sonar-plugins.css%3Acss)

Plugin versions and compatibility with SonarQube versions:
http://docs.sonarqube.org/display/PLUG/Plugin+Version+Matrix

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
2. Install the CSS plugin either by a [direct download](https://github.com/SonarQubeCommunity/sonar-css/releases) or through the [update center](http://docs.sonarqube.org/display/SONAR/Update+Center).
3. Install your [favorite analyzer](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis) (SonarQube Scanner, Maven, Ant, etc.) and analyze your code.

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
