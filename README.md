SonarQube CSS / SCSS / Less Analyzer
====================================
[![Build Status](https://api.travis-ci.org/racodond/sonar-css-plugin.svg?branch=master)](https://travis-ci.org/racodond/sonar-css-plugin)
[![Build status](https://ci.appveyor.com/api/projects/status/36tt33b2wrfjgmo2/branch/master?svg=true)](https://ci.appveyor.com/project/racodond/sonar-css-plugin/branch/master)
[![Quality Gate status](https://sonarqube.com/api/badges/gate?key=org.codehaus.sonar-plugins.css%3Acss)](https://sonarqube.com/overview?id=org.codehaus.sonar-plugins.css%3Acss)


## Description
This [SonarQube](http://www.sonarqube.org) plugin analyzes:

* [CSS](https://www.w3.org/Style/CSS/) files
* [CSS](https://www.w3.org/Style/CSS/) code embedded in HTML/XHTML files
* [SCSS](http://sass-lang.com/) files
* [Less](http://lesscss.org/) files
 
and:

* Computes metrics: lines of code, complexity, number of rules, etc.
* Validates your CSS code
* Checks for duplicated code
* Checks various guidelines to find out code smells and potential bugs through more than:
  * [80 checks](http://sonarqube.racodond.com/coding_rules#languages=css%2Ccss) on CSS code
  * [90 checks](http://sonarqube.racodond.com/coding_rules#languages=scss%2Ccss) on SCSS code
  * [80 checks](http://sonarqube.racodond.com/coding_rules#languages=less%2Ccss) on Less code
* Provides the ability to write your own checks


## SonarQube CSS / SCSS / Less Analyzer in Action
* [Demo project](http://sonarqube.racodond.com/dashboard/index?id=css-sample-project)


## Usage

### Installation Guide
1. [Download and install](http://docs.sonarqube.org/display/SONAR/Setup+and+Upgrade) SonarQube
2. Install the CSS / SCSS /Less analyzer either by a [direct download](https://github.com/racodond/sonar-css-plugin/releases) or through the [update center](http://docs.sonarqube.org/display/SONAR/Update+Center).
3. Install your [favorite analyzer](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis) (SonarQube Scanner, Maven, Ant, etc.) and analyze your code. Note that starting at version 2.0, Java 8 is required to run an analysis.

Plugin versions and compatibility with SonarQube versions: http://docs.sonarqube.org/display/PLUG/Plugin+Version+Matrix

### Getting Started in Two Minutes
For a quick try, a simple package and procedure is available at https://github.com/racodond/package-test-sonarqube-css

### Analyzing CSS code embedded in HTML/XHTML files
The plugin analyzes CSS code embedded in `<style type="text/css">...</style>` tags in HTML/XHTML files.
To do so, as a prerequisite, SonarQube has to import those files. Either:

* Install a plugin importing those files ([Web plugin](http://docs.sonarqube.org/display/PLUG/Web+Plugin) for instance)
* Or turn on the [import of unknown files](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-Unrecognizedfiles) by setting property `sonar.import_unknown_files` to `true` 

The list of files containing embedded CSS to analyze can be customized through the `sonar.css.embedded.file.suffixes` property.


## stylelint / SonarQube Rule Mapping
If you are already using [stylelint](https://stylelint.io/), adding SonarQube to your stack will help you bring code quality to another level.
The [stylelint / SonarQube rule mapping](doc/stylelint-sonarqube-rule-mapping.md) may be of great help to define your [SonarQube quality profile](https://docs.sonarqube.org/display/SONAR/Quality+Profiles).


## Custom Checks
You're thinking of new valuable checks? Version 2.1 or greater provides an API to write your own custom checks.
A sample plugin with detailed explanations is available [here](https://github.com/racodond/sonar-css-custom-rules-plugin).
If your custom checks may benefit the community, feel free to create a pull request in order to make the check available in the CSS / SCSS / Less analyzer.

You're thinking of new checks that may benefit the community but don't have the time or the skills to write them? Feel free to create an [issue](https://github.com/racodond/sonar-css-plugin/issues) for your checks to be taken under consideration.


## Metrics

### Functions
Number of rules.

### Complexity
The following elements increment the complexity by one:

* Class selector
* ID selector
* Attribute selector
* Type selector
* Pseudo selector
* Keyframes selector
* At-rule

### Complexity/function
It computes the complexity/rule, meaning the average number of selectors per rule. It gives a measurement on how specific the selectors are.


## Contributing
Any contribution is more than welcome!
 
You feel like:
* Adding a new check? Just [open an issue](https://github.com/racodond/sonar-css-plugin/issues/new) to discuss the value of your check. Once validated, code, don't forget to add a lot of unit tests and open a PR.
* Maintaining the [stylelint / SonarQube rule mapping](https://github.com/racodond/sonar-css-plugin/blob/master/doc/stylelint-sonarqube-rule-mapping.md)? Just open a PR 
* Fixing some bugs or improving existing checks? Just open a PR.
