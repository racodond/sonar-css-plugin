[![Release](https://img.shields.io/github/release/racodond/sonar-css-plugin.svg)](https://github.com/racodond/sonar-css-plugin/releases/latest)
[![Build Status](https://api.travis-ci.org/racodond/sonar-css-plugin.svg?branch=master)](https://travis-ci.org/racodond/sonar-css-plugin)
[![Build status](https://ci.appveyor.com/api/projects/status/jkl46pwunl60soio/branch/master?svg=true)](https://ci.appveyor.com/project/racodond/sonar-css-plugin-oy7u9/branch/master)

[![Quality Gate status](https://sonarcloud.io/api/project_badges/measure?project=org.codehaus.sonar-plugins.css%3Acss&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.codehaus.sonar-plugins.css%3Acss)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=org.codehaus.sonar-plugins.css%3Acss&metric=ncloc)](https://sonarcloud.io/dashboard?id=org.codehaus.sonar-plugins.css%3Acss)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.codehaus.sonar-plugins.css%3Acss&metric=coverage)](https://sonarcloud.io/dashboard?id=org.codehaus.sonar-plugins.css%3Acss)


# SonarQube CSS / SCSS / Less Analyzer

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
* Checks various guidelines to find out potential bugs, vulnerabilities and code smells through more than:
  * [80 checks](#available-rules) on CSS code
  * [90 checks](#available-rules) on SCSS code
  * [80 checks](#available-rules) on Less code
* Provides the ability to write your own checks


## Usage

### Installation Guide
1. [Download and install](http://docs.sonarqube.org/display/SONAR/Setup+and+Upgrade) SonarQube
1. Install the CSS / SCSS / Less plugin by a [direct download](https://github.com/racodond/sonar-css-plugin/releases). The latest version is compatible with SonarQube 5.6+.
1. Install your [favorite scanner](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis) (SonarQube Scanner, Maven, Ant, etc.)
1. [Analyze your code](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis)


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


## Available Rules

### Common to CSS and SCSS and Less

* "!important" flag should be placed at the end of the declaration
* "!important" flag should not be used
* "@font-face" rule should be made compatible with the required browsers
* "FIXME" tags should be handled
* "NOSONAR" tags should not be used to switch off issues
* "stylelint-disable" tags should be removed
* "stylelint-enable" tags should be removed
* "text-transform" properties should not be set to "uppercase" or "capitalize" for some languages
* "TODO" tags should be handled
* @charset should be the first element in the style sheet and not be preceded by any character
* Box model size should be carefully reviewed
* Byte Order Mark (BOM) should not be used for UTF-8 files
* Case-insensitive flag should not be used
* Class selectors should follow a naming convention
* CSS should be written in lower case
* Deprecated system colors should not be used
* Duplicated background images should be removed
* Duplicated properties should be removed
* Each declaration should end with a semicolon
* Empty declarations should be removed
* Empty rules should be removed
* Empty stylesheets should be removed
* Experimental @-rules should not be used
* Experimental identifiers should not be used
* Experimental properties should not be used
* Experimental pseudo-elements and pseudo-classes should not be used
* Experimental selector combinators should not be used
* Files should contain an empty new line at the end
* Files should not have too many lines
* Font family names should be quoted
* Font files inlining should not be used
* font-family properties should end with a generic font family
* font-family should not contain duplicated font family names
* Forbidden properties should not be used
* Generic font family names should not be quoted
* Gradient definitions should be set for all vendors
* ID selectors should follow a naming convention
* IDs in selectors should be removed
* Leading zeros should be removed
* Lines should not be too long
* Lines should not end with trailing whitespaces
* Missing vendor prefixes should be added to experimental properties
* Name of overqualified element should be removed
* Named colors should not be used
* Number precision should not be too high
* Obsolete properties should not be used
* Obsolete pseudo-elements and pseudo-classes should not be used
* Obsolete selector combinators should not be used
* Over-specified selectors should be simplified
* Properties that do not work with the "display" property should be removed
* Property values should be valid
* Protocol-relative URL should not be used
* Regular expression like selectors should not be used
* Regular expression on @-rule
* Regular expression on comment
* Regular expression on function
* Regular expression on property
* Regular expression on unit
* Rule properties should be alphabetically ordered
* Shorthand properties should be used whenever possible
* Shorthand properties should not be used
* Single quotes should be used instead of double quotes for strings
* Source code should comply with formatting standards
* Standard properties should be specified along with vendor-prefixed properties
* Star hack should not be used
* Stylesheets should not contain too many rules
* Stylesheets should not contain too many selectors
* Tabulation characters should not be used
* The number of web fonts should be reduced
* There should be one single declaration per line
* Trailing zeros for numeric values should be removed
* Types in selectors should be removed
* Underscore hack should not be used
* Units for zero length values should be removed
* Universal selector should not be used as key part
* Unknown @-rules should be removed
* Unknown properties should be removed
* Unknown pseudo-elements and pseudo-classes should be removed
* Unknown type selectors should be removed
* URL 'paper.gif' should never be used
* URL should be quoted


### Specific to CSS

* "@import" rule should not be used
* @import rules should precede all other at-rules and style rules
* CSS variables should follow a naming convention
* Experimental functions should not be used
* Obsolete functions should not be used
* Stylesheets should not "@import" too many other sheets
* Unknown CSS functions should be removed


### Specific to CSS embedded in HTML/XHTML

* CSS should not be embedded in HTML files


### Specific to SCSS

* @debug directives should not be used in production code
* @extend directives should not be used
* @if ... @else if ... constructs should end with @else directive
* Always use 'through' instead of 'to' in @for directives
* Conditions should not be too complex
* Control flow directives @if, @else if, @else, @for, @while, and @each should not be nested too deeply
* Custom functions should follow a naming convention
* Declarations and directives should be properly sorted
* Deprecated unescaped multiline strings should not be used
* Empty control flow directive should be removed
* Empty mixins should be removed
* Mixins should follow a naming convention
* Nested properties should define at least two properties
* Placeholder selectors should follow a naming convention
* Related @if / @else if directives should not have the same condition
* Rulesets should not be nested too deeply
* SCSS variables should follow a naming convention
* Single-line comments (//) should be preferred over multi-line comments (/* ... */)
* Two branches in the same conditional structure should not have exactly the same implementation
* Useless parentheses following @include and @mixin with no parameter should be removed


### Specific to Less

* Deprecated "e" escaping function should be replaced with ~"value" syntax
* Less variables should follow a naming convention
* Rulesets should not be nested too deeply
* Same variable should not be declared multiple times within the same scope
* Single-line comments (//) should be preferred over multi-line comments (/* ... */)
* Unknown CSS / Less functions should be removed
* Variables should be declared at the beginning of the block
