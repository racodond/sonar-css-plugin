SonarQube CSS Plugin
====================
[![Build Status](https://api.travis-ci.org/SonarQubeCommunity/sonar-css.svg?branch=master)](https://travis-ci.org/SonarQubeCommunity/sonar-css)
[![AppVeyor Build Status](https://ci.appveyor.com/api/projects/status/tc9bonqa7v0x4scb/branch/master?svg=true)](https://ci.appveyor.com/project/racodond/sonar-css/branch/master)
[![Quality Gate status](https://sonarqube.com/api/badges/gate?key=org.codehaus.sonar-plugins.css%3Acss)](https://sonarqube.com/overview?id=org.codehaus.sonar-plugins.css%3Acss)


## Description
This plugin enables code QA analysis of CSS files within [SonarQube](http://www.sonarqube.org):

 * Computes metrics: lines of code, number of rules, complexity, etc.
 * Validates your CSS code
 * Performs more than [50 checks](#available-checks)
 * Provides the ability to write your own checks

 
## Usage
1. [Download and install](http://docs.sonarqube.org/display/SONAR/Setup+and+Upgrade) SonarQube
2. Install the CSS plugin either by a [direct download](https://github.com/SonarQubeCommunity/sonar-css/releases) or through the [update center](http://docs.sonarqube.org/display/SONAR/Update+Center).
3. Install your [favorite analyzer](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis) (SonarQube Scanner, Maven, Ant, etc.) and analyze your code. Note that starting at version 2.0, Java 8 is required to run an analysis.

Plugin versions and compatibility with SonarQube versions: http://docs.sonarqube.org/display/PLUG/Plugin+Version+Matrix


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


## Available Checks

### Checks
* "!important" annotation should be placed at the end of the declaration
* "!important" annotation should not be used
* "@font-face" rule should be made compatible with the required browsers
* "@import" rule should not be used
* "FIXME" tags should be handled
* "NOSONAR" tags should not be used to switch off issues
* "TODO" tags should be handled
* @charset should be the first element in the style sheet and not be preceded by any character
* @import rules should precede all other at-rules and style rules
* Box model size should be carefully reviewed
* Branches should have sufficient coverage by unit tests
* Byte Order Mark (BOM) should not be used for UTF-8 files
* Case-sensitive flag should not be used
* Deprecated Internet Explorer static filters should be removed
* Deprecated system colors should not be used
* Duplicated background images should be removed
* Duplicated properties should be removed
* Each declaration should end with a semicolon
* Empty declarations should be removed
* Empty rules should be removed
* Experimental @-rules should not be used
* Experimental functions should not be used
* Experimental identifiers should not be used
* Experimental properties should not be used
* Experimental pseudo-elements and pseudo-classes should not be used
* Font files inlining should not be used
* Gradient definitions should be set for all vendors
* IDs in selectors should be removed
* Leading zeros should be removed
* Lines should not be too long
* Lines should not end with trailing whitespaces
* Missing vendor prefixes should be added to experimental properties
* Name of overqualified element should be removed
* Obsolete functions should not be used
* Obsolete properties should not be used
* Obsolete pseudo-elements and pseudo-classes should not be used
* Over-specified selectors should be simplified
* Properties that do not work with the "display" property should be removed
* Properties, functions and variables should be lower case
* Property values should be valid
* Regular expression like selectors should not be used
* Rule properties should be alphabetically ordered
* Selectors should follow a naming convention
* Shorthand properties should be used whenever possible
* Shorthand properties should not be used
* Source code should comply with formatting standards
* Standard properties should be specified along with vendor-prefixed properties
* Star hack should not be used
* Stylesheets should not "@import" too many other sheets
* Stylesheets should not contain too many rules
* Stylesheets should not contain too many selectors
* Tabulation characters should not be used
* The number of web fonts should be reduced
* There should be one single declaration per line
* Underscore hack should not be used
* Units for zero length values should be removed
* Universal selector should not be used as key part
* Unknown @-rules should be removed
* Unknown functions should be removed
* Unknown properties should be removed
* Unknown pseudo-functions should be removed

### Templates
Regular expression on comment
Regular expression on property


## Custom Checks

You're thinking of new valuable rules? Version 2.1 or greater provides an API to write your own custom checks.
A sample plugin with detailed explanations is available [here](https://github.com/racodond/sonar-css-custom-rules-plugin).
If your custom rules may benefit the community, feel free to create a pull request in order to make the rule available in the CSS plugin.

You're thinking of new rules that may benefit the community but don't have the time or the skills to write them? Feel free to create an [issue](https://github.com/SonarQubeCommunity/sonar-css/issues) for your rules to be taken under consideration.
