stylelint / SonarQube Rule Mapping
==================================

Note that there is not always a strict one-to-one relationship between [SonarQube rules](http://sonarqube.racodond.com/coding_rules#languages=scss%2Ccss%2Cless) and [stylelint rules](https://stylelint.io/user-guide/rules/).

## SonarQube => stylelint


### Common

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| ["!important" flag should be placed at the end of the declaration](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimportant-position) | None | |
| ["!important" flag should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimportant) | [declaration-no-important](https://stylelint.io/user-guide/rules/declaration-no-important/)<br/>[keyframe-declaration-no-important](https://stylelint.io/user-guide/rules/keyframe-declaration-no-important/) | |
| ["@font-face" rule should be made compatible with the required browsers](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afont-face-browser-compatibility) | None | |
| ["@import" rule should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimport) | None | |
| ["FIXME" tags should be handled](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS1134) | None | |
| ["NOSONAR" tags should not be used to switch off issues](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Anosonar) | None | |
| ["stylelint-disable" tags should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Astylelint-disable) | None | |
| ["stylelint-enable" tags should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Astylelint-enable) | None | |
| ["text-transform" properties should not be set to "uppercase" or "capitalize" for some languages](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Atext-transform-uppercase) | None | |
| ["TODO" tags should be handled](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS1135) | None | |
| [@charset should be the first element in the style sheet and not be preceded by any character](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acharset-first) | None | |
| [@import rules should precede all other at-rules and style rules](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimport-first) | None | |
| [Box model size should be carefully reviewed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Abox-model) | None | |
| [Byte Order Mark (BOM) should not be used for UTF-8 files](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Abom-utf8-files) | None | |
| [Case-insensitive flag should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase-insensitive-flag) | None | |
| [Class selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aclass-selector-naming-convention) | [selector-class-pattern](https://stylelint.io/user-guide/rules/selector-class-pattern/) | |
| [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | [property-case](https://stylelint.io/user-guide/rules/property-case/)<br/>[function-name-case](https://stylelint.io/user-guide/rules/function-name-case/)<br/>[at-rule-name-case](https://stylelint.io/user-guide/rules/at-rule-name-case/)<br/>[selector-pseudo-class-case](https://stylelint.io/user-guide/rules/selector-pseudo-class-case/)<br/>[selector-pseudo-element-case](https://stylelint.io/user-guide/rules/selector-pseudo-element-case/)<br/>[selector-type-case](https://stylelint.io/user-guide/rules/selector-type-case/)<br/>[unit-case](https://stylelint.io/user-guide/rules/unit-case/)<br/>[color-hex-case](https://stylelint.io/user-guide/rules/color-hex-case/) | |
| [CSS variables should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acss-variable-naming-convention) | [custom-property-pattern](https://stylelint.io/user-guide/rules/custom-property-pattern/) | |
| [Deprecated system colors should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Adeprecated-system-colors) | None | |
| [Duplicated background images should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aduplicate-background-images) | None | |
| [Duplicated properties should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aduplicate-properties) | [declaration-block-no-duplicate-properties](https://stylelint.io/user-guide/rules/declaration-block-no-duplicate-properties/) | |
| [Each declaration should end with a semicolon](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asemicolon-declaration) | [declaration-block-trailing-semicolon](https://stylelint.io/user-guide/rules/declaration-block-trailing-semicolon/) | |
| [Empty declarations should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-declaration) | [no-extra-semicolons](https://stylelint.io/user-guide/rules/no-extra-semicolons/) | |
| [Empty rules should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-rules) | [block-no-empty](https://stylelint.io/user-guide/rules/block-no-empty/) | |
| [Empty stylesheets should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-stylesheet) | [no-empty-source](https://stylelint.io/user-guide/rules/no-empty-source/) | |
| [Experimental @-rules should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-atrule-usage) | [at-rule-no-vendor-prefix](https://stylelint.io/user-guide/rules/at-rule-no-vendor-prefix/) | |
| [Experimental functions should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-function-usage) | None | |
| [Experimental identifiers should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-identifier-usage) | [value-no-vendor-prefix](https://stylelint.io/user-guide/rules/value-no-vendor-prefix/) | |
| [Experimental properties should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-property-usage) | [property-no-vendor-prefix](https://stylelint.io/user-guide/rules/property-no-vendor-prefix/) | stylelint is limited to vendor-prefix properties|
| [Experimental pseudo-elements and pseudo-classes should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-pseudo-usage) | None | |
| [Experimental selector combinators should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-selector-combinator-usage) | None | |
| [Files should contain an empty new line at the end](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-line-end-of-file) | [no-missing-end-of-source-newline](https://stylelint.io/user-guide/rules/no-missing-end-of-source-newline/) | |
| [Files should not have too many lines](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afile-too-many-lines) | None | |
| [font-family properties should end with a generic font family](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afont-family-not-ending-with-generic-font-family) | None | |
| [Font family names should be quoted](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunquoted-font-family-names) | [font-family-name-quotes](https://stylelint.io/user-guide/rules/font-family-name-quotes/) | |
| [font-family should not contain duplicated font family names](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aduplicate-properties) | [font-family-no-duplicate-names](https://stylelint.io/user-guide/rules/font-family-no-duplicate-names/) | |
| [Font files inlining should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Ainlining-font-files) | None | |
| [Generic family names should not be quoted](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aquoted-generic-family-names) | [font-family-name-quotes](https://stylelint.io/user-guide/rules/font-family-name-quotes/) | |
| [Gradient definitions should be set for all vendors](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Agradients) | None | |
| [ID selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aid-selector-naming-convention) | [selector-id-pattern](https://stylelint.io/user-guide/rules/selector-id-pattern/) | |
| [IDs in selectors should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aids) | [selector-no-id](https://stylelint.io/user-guide/rules/selector-no-id/) | |
| [Leading zeros should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aleading-zeros) | [number-leading-zero](https://stylelint.io/user-guide/rules/number-leading-zero/) | |
| [Lines should not be too long](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aline-length) | [max-line-length](https://stylelint.io/user-guide/rules/max-line-length/) | |
| [Lines should not end with trailing whitespaces](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS1131) | [no-eol-whitespace](https://stylelint.io/user-guide/rules/no-eol-whitespace/) | |
| [Missing vendor prefixes should be added to experimental properties](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acompatible-vendor-prefixes) | None | |
| [Name of overqualified element should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aoverqualified-elements) | None | |
| [Named colors should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Anamed-color) | [color-named](https://stylelint.io/user-guide/rules/color-named/) | |
| [Number precision should not be too high](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Anumber-precision) | [number-max-precision](https://stylelint.io/user-guide/rules/number-max-precision/) | |
| [Obsolete functions should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aobsolete-functions) | None | |
| [Obsolete properties should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aobsolete-properties) | None | |
| [Obsolete pseudo-elements and pseudo-classes should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aobsolete-pseudos) | None | |
| [Over-specified selectors should be simplified](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aoverspecific-selectors) | [selector-max-compound-selectors](https://stylelint.io/user-guide/rules/selector-max-compound-selectors/) | |
| [Properties that do not work with the "display" property should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Adisplay-property-grouping) | [declaration-block-no-ignored-properties](https://stylelint.io/user-guide/rules/declaration-block-no-ignored-properties/) | Rule should be improved on SonarQube side. See [issue #105](https://github.com/racodond/sonar-css-plugin/issues/105). |
| [Property values should be valid](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Avalidate-property-value) | [csstree/stylelint-validator plugin](https://github.com/csstree/stylelint-validator) | |
| [Protocol-relative URL should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aprotocol-relative-url) | [function-url-no-scheme-relative](https://stylelint.io/user-guide/rules/function-url-no-scheme-relative/) | |
| [Regular expression like selectors should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aregex-selectors) | None | |
| [Regular expression on comment](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acomment-regular-expression) | [comment-word-blacklist](https://stylelint.io/user-guide/rules/comment-word-blacklist/) | |
| [Regular expression on @-rule](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aat-rule-regular-expression) | [at-rule-blacklist](https://stylelint.io/user-guide/rules/at-rule-blacklist/)<br/>[at-rule-whitelist](https://stylelint.io/user-guide/rules/at-rule-whitelist/) | This SonarQube rule template can be used to create a rule similar to stylelint at-rule-blacklist and at-rule-whitelist. |
| [Regular expression on function](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afunction-regular-expression) | [function-blacklist](https://stylelint.io/user-guide/rules/function-blacklist/)<br/>[function-whitelist](https://stylelint.io/user-guide/rules/function-whitelist/) | This SonarQube rule template can be used to create a rule similar to stylelint function-blacklist and function-whitelist. |
| [Regular expression on property](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aproperty-regular-expression) | [property-blacklist](https://stylelint.io/user-guide/rules/property-blacklist/)<br/>[property-whitelist](https://stylelint.io/user-guide/rules/property-whitelist/) | This SonarQube rule template can be used to create a rule similar to stylelint property-blacklist and property-whitelist. |
| [Rule properties should be alphabetically ordered](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aalphabetize-declarations) | [declaration-block-properties-order](declaration-block-properties-order) | |
| [Shorthand properties should be used whenever possible](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Ashorthand) | [declaration-block-no-redundant-longhand-properties](https://stylelint.io/user-guide/rules/declaration-block-no-redundant-longhand-properties/) | |
| [Shorthand properties should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Ado-not-use-shorthand-properties) | None | |
| [Single quotes should be used instead of double quotes for strings](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asingle-quotes) | [string-quotes](https://stylelint.io/user-guide/rules/string-quotes/) | |
| [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | [block-opening-brace-newline-after](https://stylelint.io/user-guide/rules/block-opening-brace-newline-after/)<br/>[declaration-colon-space-after](https://stylelint.io/user-guide/rules/declaration-colon-space-after/)<br/>[declaration-colon-space-before](https://stylelint.io/user-guide/rules/declaration-colon-space-before/)<br/>[declaration-bang-space-after](https://stylelint.io/user-guide/rules/declaration-bang-space-after/)<br/>[dollar-variable-colon-space-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-colon-space-after/README.md)<br/>[dollar-variable-colon-space-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-colon-space-before/README.md) | |
| [Source files should not have any duplicated blocks](http://sonarqube.racodond.com/coding_rules#rule_key=common-css%3ADuplicatedBlocks) | None | |
| [Standard properties should be specified along with vendor-prefixed properties](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Avendor-prefix) | None | |
| [Star hack should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Astar-property-hack) | [no-browser-hacks](https://stylelint.io/user-guide/rules/no-browser-hacks/) | |
| [Stylesheets should not "@import" too many other sheets](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS2735) | None | |
| [Stylesheets should not contain too many rules](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asheet-too-many-rules) | None | |
| [Stylesheets should not contain too many selectors](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS2732) | None | |
| [Tabulation characters should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Atab-character) | None | |
| [The number of web fonts should be reduced](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afont-faces) | None | |
| [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | [block-no-single-line](https://stylelint.io/user-guide/rules/block-no-single-line/)<br/>[declaration-block-single-line-max-declarations](https://stylelint.io/user-guide/rules/declaration-block-single-line-max-declarations/)<br/>[declaration-block-semicolon-newline-after](https://stylelint.io/user-guide/rules/declaration-block-semicolon-newline-after/) | |
| [Trailing zeros for numeric values should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Atrailing-zeros) | [number-no-trailing-zeros](https://stylelint.io/user-guide/rules/number-no-trailing-zeros/) | |
| [Underscore hack should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunderscore-property-hack) | [no-browser-hacks](https://stylelint.io/user-guide/rules/no-browser-hacks/) | |
| [Units for zero length values should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Azero-units) | [length-zero-no-unit](https://stylelint.io/user-guide/rules/length-zero-no-unit/) | |
| [Universal selector should not be used as key part](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Auniversal-selector) | [selector-no-universal](https://stylelint.io/user-guide/rules/selector-no-universal/) | |
| [Unknown @-rules should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-at-rules) | [at-rule-no-unknown](https://stylelint.io/user-guide/rules/at-rule-no-unknown/) | |
| [Unknown CSS functions should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-functions) | None | |
| [Unknown properties should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aknown-properties) | [property-no-unknown](https://stylelint.io/user-guide/rules/property-no-unknown/) | |
| [Unknown pseudo-elements and pseudo-classes should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-pseudo) | [selector-pseudo-class-no-unknown](https://stylelint.io/user-guide/rules/selector-pseudo-class-no-unknown/)<br/>[selector-pseudo-element-no-unknown](https://stylelint.io/user-guide/rules/selector-pseudo-element-no-unknown/) | |
| [Unknown type selectors should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-type-selector) | [selector-type-no-unknown](https://stylelint.io/user-guide/rules/selector-type-no-unknown/) | |
| [URL should be quoted](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aquoted-url) | [function-url-quotes](https://stylelint.io/user-guide/rules/function-url-quotes/) | |


### Specific to CSS embedded in HTML/XHTML

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [CSS should not be embedded in HTML files](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aembedded-css) | None | |


### Specific to SCSS

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [@debug directives should not be used in production code](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Adebug) | None | |
| [@extend directives should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aextend) | None | |
| [@if ... @else if ... constructs should end with @else directive](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aif-elseif-without-else) | None | |
| [Always use 'through' instead of 'to' in @for directives](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Athrough-instead-of-to) | None | |
| [Conditions should not be too complex](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Atoo-complex-condition) | None | |
| [Control flow directives @if, @else if, @else, @for, @while, and @each should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Anested-control-flow-directives) | None | |
| [Custom functions should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Acustom-function-naming-convention) | [at-function-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-function-pattern/README.md) | |
| [Declarations and directives should be properly sorted](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Asort-declarations-and-directives) | None | |
| [Deprecated unescaped multiline strings should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Adeprecated-unescaped-multiline-string) | None | |
| [Empty control flow directive should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aempty-control-flow-directive) | None | |
| [Empty mixins should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aempty-mixin) | [block-no-empty](https://stylelint.io/user-guide/rules/block-no-empty/) | |
| [Mixins should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Amixin-naming-convention) | [at-mixin-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-mixin-pattern/README.md) | |
| [Nested properties should define at least two properties](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aat-least-two-nested-properties) | None | |
| [Placeholder selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aplaceholder-selector-naming-convention) | [percent-placeholder-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/percent-placeholder-pattern/README.md) | |
| [Related @if / @else if directives should not have the same condition](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aif-elseif-same-condition) | None | |
| [Rulesets should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Anested-rulesets) | [max-nesting-depth](https://stylelint.io/user-guide/rules/max-nesting-depth/) | |
| [SCSS variables should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Ascss-variable-naming-convention) | [dollar-variable-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-pattern/README.md) | |
| [Single-line comments (//) should be preferred over multi-line comments (/* ... */)](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aprefer-single-line-comments) | None | |
| [Two branches in the same conditional structure should not have exactly the same implementation](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Abranch-same-implementation) | None | |
| [Useless parentheses following @include and @mixin with no parameter should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Auseless-parentheses-mixin-no-parameter) | [at-mixin-argumentless-call-parentheses](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-mixin-argumentless-call-parentheses/README.md) | |


### Specific to Less

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [Deprecated "e" escaping function should be replaced with ~"value" syntax](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Aless-deprecated-escaping-function) | None | |
| [Less variables should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Aless-variable-naming-convention) | None | |
| [Rulesets should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Anested-rulesets) | [max-nesting-depth](https://stylelint.io/user-guide/rules/max-nesting-depth/) | |
| [Same variable should not be declared multiple times within the same scope](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Amultiple-variable-declarations-same-scope) | None | |
| [Single-line comments (//) should be preferred over multi-line comments (/* ... */)](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Aprefer-single-line-comments) | None | |
| [Unknown CSS / Less functions should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Aunknown-less-functions) | None | |
| [Variables should be declared at the beginning of the block](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Avariable-declaration-first) | None | |


## stylelint => SonarQube

### Color

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [color-hex-case](https://stylelint.io/user-guide/rules/color-hex-case/) | [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| [color-hex-length](https://stylelint.io/user-guide/rules/color-hex-length/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [color-named](https://stylelint.io/user-guide/rules/color-named/) | [Named colors should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Anamed-color) | |
| [color-no-hex](https://stylelint.io/user-guide/rules/color-no-hex/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [color-no-invalid-hex](https://stylelint.io/user-guide/rules/color-no-invalid-hex/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Font family

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [font-family-name-quotes](https://stylelint.io/user-guide/rules/font-family-name-quotes/) | [Generic family names should not be quoted](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aquoted-generic-family-names)<br/>[Font family names should be quoted](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunquoted-font-family-names) | |
| [font-family-no-duplicate-names](https://stylelint.io/user-guide/rules/font-family-no-duplicate-names/) | [font-family should not contain duplicated font family names](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aduplicated-font-families) | |


### Font weight

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [font-weight-notation](https://stylelint.io/user-guide/rules/font-weight-notation/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Function

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [function-blacklist](https://stylelint.io/user-guide/rules/function-blacklist/) | [Regular expression on function](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afunction-regular-expression) | |
| [function-calc-no-unspaced-operator](https://stylelint.io/user-guide/rules/function-calc-no-unspaced-operator/) | None | |
| [function-comma-newline-after](https://stylelint.io/user-guide/rules/function-comma-newline-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-comma-newline-before](https://stylelint.io/user-guide/rules/function-comma-newline-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-comma-space-after](https://stylelint.io/user-guide/rules/function-comma-space-after/) | None | |
| [function-comma-space-before](https://stylelint.io/user-guide/rules/function-comma-space-before/) | None | |
| [function-linear-gradient-no-nonstandard-direction](https://stylelint.io/user-guide/rules/function-linear-gradient-no-nonstandard-direction/) | None | |
| [function-max-empty-lines](https://stylelint.io/user-guide/rules/function-max-empty-lines/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-name-case](https://stylelint.io/user-guide/rules/function-name-case/) | [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| [function-parentheses-newline-inside](https://stylelint.io/user-guide/rules/function-parentheses-newline-inside/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-parentheses-space-inside](https://stylelint.io/user-guide/rules/function-parentheses-space-inside/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-url-data-uris](https://stylelint.io/user-guide/rules/function-url-data-uris/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-url-no-scheme-relative](https://stylelint.io/user-guide/rules/function-url-no-scheme-relative/) | [Protocol-relative URL should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aprotocol-relative-url) | |
| [function-url-quotes](https://stylelint.io/user-guide/rules/function-url-quotes/) | [URL should be quoted](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aquoted-url) | |
| [function-url-scheme-whitelist](https://stylelint.io/user-guide/rules/function-url-scheme-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-whitelist](https://stylelint.io/user-guide/rules/function-whitelist/) | [Regular expression on function](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afunction-regular-expression) | |
| [function-whitespace-after](https://stylelint.io/user-guide/rules/function-whitespace-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Number

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [number-leading-zero](https://stylelint.io/user-guide/rules/number-leading-zero/) | [Leading zeros should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aleading-zeros) | |
| [number-max-precision](https://stylelint.io/user-guide/rules/number-max-precision/) | [Number precision should not be too high](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Anumber-precision) | |
| [number-no-trailing-zeros](https://stylelint.io/user-guide/rules/number-no-trailing-zeros/) | [Trailing zeros for numeric values should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Atrailing-zeros) | |


### String

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [string-no-newline](https://stylelint.io/user-guide/rules/string-no-newline/) | None | |
| [string-quotes](https://stylelint.io/user-guide/rules/string-quotes/) | [Single quotes should be used instead of double quotes for strings](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asingle-quotes) | |


### Length

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [length-zero-no-unit](https://stylelint.io/user-guide/rules/length-zero-no-unit/) | [Units for zero length values should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Azero-units) | |


### Time

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [time-min-milliseconds](https://stylelint.io/user-guide/rules/time-min-milliseconds/) | None | |
| [time-no-imperceptible](https://stylelint.io/user-guide/rules/time-no-imperceptible/) (deprecated) | None | |


### Unit

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [unit-blacklist](https://stylelint.io/user-guide/rules/unit-blacklist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [unit-case](https://stylelint.io/user-guide/rules/unit-case/) | [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| [unit-no-unknown](https://stylelint.io/user-guide/rules/unit-no-unknown/) | None | For instance, 10zc will be considered as two value elements in SonarQube: number '10' and identifier 'zc'. |
| [unit-whitelist](https://stylelint.io/user-guide/rules/unit-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Value

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [value-keyword-case](https://stylelint.io/user-guide/rules/value-keyword-case/) | None | |
| [value-no-vendor-prefix](https://stylelint.io/user-guide/rules/value-no-vendor-prefix/) | [Experimental identifiers should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-identifier-usage) | |


### Value list

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [value-list-comma-newline-after](https://stylelint.io/user-guide/rules/value-list-comma-newline-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [value-list-comma-newline-before](https://stylelint.io/user-guide/rules/value-list-comma-newline-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [value-list-comma-space-after](https://stylelint.io/user-guide/rules/value-list-comma-space-after/) | None | |
| [value-list-comma-space-before](https://stylelint.io/user-guide/rules/value-list-comma-space-before/) | None | |
| [value-list-max-empty-lines](https://stylelint.io/user-guide/rules/value-list-max-empty-lines/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Custom property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [custom-property-empty-line-before](https://stylelint.io/user-guide/rules/custom-property-empty-line-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [custom-property-no-outside-root](https://stylelint.io/user-guide/rules/custom-property-no-outside-root/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [custom-property-pattern](https://stylelint.io/user-guide/rules/custom-property-pattern/) | [CSS variables should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acss-variable-naming-convention) | |


### Shorthand property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [shorthand-property-no-redundant-values](https://stylelint.io/user-guide/rules/shorthand-property-no-redundant-values/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [property-blacklist](https://stylelint.io/user-guide/rules/property-blacklist/) | [Regular expression on property](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aproperty-regular-expression) | |
| [property-case](https://stylelint.io/user-guide/rules/property-case/) | [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| [property-no-unknown](https://stylelint.io/user-guide/rules/property-no-unknown/) | [Unknown properties should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aknown-properties) | |
| [property-no-vendor-prefix](https://stylelint.io/user-guide/rules/property-no-vendor-prefix/) | [Experimental properties should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-property-usage) | |
| [property-whitelist](https://stylelint.io/user-guide/rules/property-whitelist/) | [Regular expression on property](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aproperty-regular-expression) | |


### Keyframe declaration

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [keyframe-declaration-no-important](https://stylelint.io/user-guide/rules/keyframe-declaration-no-important/) | ["!important" flag should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimportant) | |


### Declaration

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [declaration-bang-space-after](https://stylelint.io/user-guide/rules/declaration-bang-space-after/) | [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | |
| [declaration-bang-space-before](https://stylelint.io/user-guide/rules/declaration-bang-space-before/) | None | |
| [declaration-colon-newline-after](https://stylelint.io/user-guide/rules/declaration-colon-newline-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-colon-space-after](https://stylelint.io/user-guide/rules/declaration-colon-space-after/) | [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | |
| [declaration-colon-space-before](https://stylelint.io/user-guide/rules/declaration-colon-space-before/) | [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | |
| [declaration-empty-line-before](https://stylelint.io/user-guide/rules/declaration-empty-line-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-no-important](https://stylelint.io/user-guide/rules/declaration-no-important/) | ["!important" flag should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimportant) | |
| [declaration-property-unit-blacklist](https://stylelint.io/user-guide/rules/declaration-property-unit-blacklist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-property-unit-whitelist](https://stylelint.io/user-guide/rules/declaration-property-unit-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-property-value-blacklist](https://stylelint.io/user-guide/rules/declaration-property-value-blacklist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-property-value-whitelist](https://stylelint.io/user-guide/rules/declaration-property-value-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Declaration block

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [declaration-block-no-duplicate-properties](https://stylelint.io/user-guide/rules/declaration-block-no-duplicate-properties/) | [Duplicated properties should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aduplicate-properties) | |
| [declaration-block-no-ignored-properties](https://stylelint.io/user-guide/rules/declaration-block-no-ignored-properties/) (deprecated) | [Properties that do not work with the "display" property should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Adisplay-property-grouping) | |
| [declaration-block-no-redundant-longhand-properties](https://stylelint.io/user-guide/rules/declaration-block-no-redundant-longhand-properties/) | [Shorthand properties should be used whenever possible](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Ashorthand) | |
| [declaration-block-no-shorthand-property-overrides](https://stylelint.io/user-guide/rules/declaration-block-no-shorthand-property-overrides/) | None | |
| [declaration-block-properties-order](https://stylelint.io/user-guide/rules/declaration-block-properties-order/) (deprecated) | [Rule properties should be alphabetically ordered](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aalphabetize-declarations) | |
| [declaration-block-semicolon-newline-after](https://stylelint.io/user-guide/rules/declaration-block-semicolon-newline-after/) | [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | |
| [declaration-block-semicolon-newline-before](https://stylelint.io/user-guide/rules/declaration-block-semicolon-newline-before/) | [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | |
| [declaration-block-semicolon-space-after](https://stylelint.io/user-guide/rules/declaration-block-semicolon-space-after/) | [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | |
| [declaration-block-semicolon-space-before](https://stylelint.io/user-guide/rules/declaration-block-semicolon-space-before/) | None | |
| [declaration-block-single-line-max-declarations](https://stylelint.io/user-guide/rules/declaration-block-single-line-max-declarations/) | [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | |
| [declaration-block-trailing-semicolon](https://stylelint.io/user-guide/rules/declaration-block-trailing-semicolon/) | [Each declaration should end with a semicolon](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asemicolon-declaration) | |


### Block

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [block-closing-brace-empty-line-before](https://stylelint.io/user-guide/rules/block-closing-brace-empty-line-before/) | None | |
| [block-closing-brace-newline-after](https://stylelint.io/user-guide/rules/block-closing-brace-newline-after/) | None | |
| [block-closing-brace-newline-before](https://stylelint.io/user-guide/rules/block-closing-brace-newline-before/) | None | |
| [block-closing-brace-space-after](https://stylelint.io/user-guide/rules/block-closing-brace-space-after/) | None | |
| [block-closing-brace-space-before](https://stylelint.io/user-guide/rules/block-closing-brace-space-before/) | None | |
| [block-no-empty](https://stylelint.io/user-guide/rules/block-no-empty/) | [Empty mixins should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aempty-mixin)<br/>[Empty rules should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-rules) | |
| [block-no-single-line](https://stylelint.io/user-guide/rules/block-no-single-line/) (deprecated) | [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | |
| [block-opening-brace-newline-after](https://stylelint.io/user-guide/rules/block-opening-brace-newline-after/) | None | |
| [block-opening-brace-newline-before](https://stylelint.io/user-guide/rules/block-opening-brace-newline-before/) | None | |
| [block-opening-brace-space-after](https://stylelint.io/user-guide/rules/block-opening-brace-space-after/) | None | |
| [block-opening-brace-space-before](https://stylelint.io/user-guide/rules/block-opening-brace-space-before/) | None | |


### Selector

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [selector-attribute-brackets-space-inside](https://stylelint.io/user-guide/rules/selector-attribute-brackets-space-inside/) | None | |
| [selector-attribute-operator-blacklist](https://stylelint.io/user-guide/rules/selector-attribute-operator-blacklist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-attribute-operator-space-after](https://stylelint.io/user-guide/rules/selector-attribute-operator-space-after/) | None | |
| [selector-attribute-operator-space-before](https://stylelint.io/user-guide/rules/selector-attribute-operator-space-before/) | None | |
| [selector-attribute-operator-whitelist](https://stylelint.io/user-guide/rules/selector-attribute-operator-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-attribute-quotes](https://stylelint.io/user-guide/rules/selector-attribute-quotes/) | None | |
| [selector-class-pattern](https://stylelint.io/user-guide/rules/selector-class-pattern/) | [Class selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aclass-selector-naming-convention) | |
| [selector-combinator-space-after](https://stylelint.io/user-guide/rules/selector-combinator-space-after/) | None | |
| [selector-combinator-space-before](https://stylelint.io/user-guide/rules/selector-combinator-space-before/) | None | |
| [selector-descendant-combinator-no-non-space](https://stylelint.io/user-guide/rules/selector-descendant-combinator-no-non-space/) | None | |
| [selector-id-pattern](https://stylelint.io/user-guide/rules/selector-id-pattern/) | [ID selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aid-selector-naming-convention) | |
| [selector-max-compound-selectors](https://stylelint.io/user-guide/rules/selector-max-compound-selectors/) | [Over-specified selectors should be simplified](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aoverspecific-selectors) | |
| [selector-max-specificity](https://stylelint.io/user-guide/rules/selector-max-specificity/) | None | Not convinced by the added value of such a rule. Use [Over-specified selectors should be simplified](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aoverspecific-selectors) instead. No plan to implement it in SonarQube. |
| [selector-nested-pattern](https://stylelint.io/user-guide/rules/selector-nested-pattern/) | None | |
| [selector-no-attribute](https://stylelint.io/user-guide/rules/selector-no-attribute/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-combinator](https://stylelint.io/user-guide/rules/selector-no-combinator/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-empty](https://stylelint.io/user-guide/rules/selector-no-empty/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-id](https://stylelint.io/user-guide/rules/selector-no-id/) | [IDs in selectors should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aids) | |
| [selector-no-qualifying-type](https://stylelint.io/user-guide/rules/selector-no-qualifying-type/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-type](https://stylelint.io/user-guide/rules/selector-no-type/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-universal](https://stylelint.io/user-guide/rules/selector-no-universal/) | [Universal selector should not be used as key part](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Auniversal-selector) | |
| [selector-no-vendor-prefix](https://stylelint.io/user-guide/rules/selector-no-vendor-prefix/) | None | |
| [selector-pseudo-class-blacklist](https://stylelint.io/user-guide/rules/selector-pseudo-class-blacklist/) | None | |
| [selector-pseudo-class-case](https://stylelint.io/user-guide/rules/selector-pseudo-class-case/) | [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| [selector-pseudo-class-no-unknown](https://stylelint.io/user-guide/rules/selector-pseudo-class-no-unknown/) | [Unknown pseudo-elements and pseudo-classes should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-pseudo) | |
| [selector-pseudo-class-parentheses-space-inside](https://stylelint.io/user-guide/rules/selector-pseudo-class-parentheses-space-inside/) | None | |
| [selector-pseudo-class-whitelist](https://stylelint.io/user-guide/rules/selector-pseudo-class-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-pseudo-element-case](https://stylelint.io/user-guide/rules/selector-pseudo-element-case/) | [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| [selector-pseudo-element-colon-notation](https://stylelint.io/user-guide/rules/selector-pseudo-element-colon-notation/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-pseudo-element-no-unknown](https://stylelint.io/user-guide/rules/selector-pseudo-element-no-unknown/) | [Unknown pseudo-elements and pseudo-classes should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-pseudo) | |
| [selector-root-no-composition](https://stylelint.io/user-guide/rules/selector-root-no-composition/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-type-case](https://stylelint.io/user-guide/rules/selector-type-case/) | [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| [selector-type-no-unknown](https://stylelint.io/user-guide/rules/selector-type-no-unknown/) | [Unknown type selectors should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-type-selector) | |
| [selector-max-empty-lines](https://stylelint.io/user-guide/rules/selector-max-empty-lines/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Selector list

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [selector-list-comma-newline-after](https://stylelint.io/user-guide/rules/selector-list-comma-newline-after/) | None | |
| [selector-list-comma-newline-before](https://stylelint.io/user-guide/rules/selector-list-comma-newline-before/) | None | |
| [selector-list-comma-space-after](https://stylelint.io/user-guide/rules/selector-list-comma-space-after/) | None | |
| [selector-list-comma-space-before](https://stylelint.io/user-guide/rules/selector-list-comma-space-before/) | None | |


### Root rule

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [root-no-standard-properties](https://stylelint.io/user-guide/rules/root-no-standard-properties/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Rule

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [rule-empty-line-before](https://stylelint.io/user-guide/rules/rule-empty-line-before/) | None | |
| [rule-nested-empty-line-before](https://stylelint.io/user-guide/rules/rule-nested-empty-line-before/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [rule-non-nested-empty-line-before](https://stylelint.io/user-guide/rules/rule-non-nested-empty-line-before/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Media feature

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [media-feature-colon-space-after](https://stylelint.io/user-guide/rules/media-feature-colon-space-after/) | None | |
| [media-feature-colon-space-before](https://stylelint.io/user-guide/rules/media-feature-colon-space-before/) | None | |
| [media-feature-name-blacklist](https://stylelint.io/user-guide/rules/media-feature-name-blacklist/) | None | |
| [media-feature-name-case](https://stylelint.io/user-guide/rules/media-feature-name-case/) | None | |
| [media-feature-name-no-unknown](https://stylelint.io/user-guide/rules/media-feature-name-no-unknown/) | None | |
| [media-feature-name-no-vendor-prefix](https://stylelint.io/user-guide/rules/media-feature-name-no-vendor-prefix/) | None | |
| [media-feature-name-whitelist](https://stylelint.io/user-guide/rules/media-feature-name-whitelist/) | None | |
| [media-feature-no-missing-punctuation](https://stylelint.io/user-guide/rules/media-feature-no-missing-punctuation/) (deprecated) | None | |
| [media-feature-parentheses-space-inside](https://stylelint.io/user-guide/rules/media-feature-parentheses-space-inside/) | None | |
| [media-feature-range-operator-space-after](https://stylelint.io/user-guide/rules/media-feature-range-operator-space-after/) | None | |
| [media-feature-range-operator-space-before](https://stylelint.io/user-guide/rules/media-feature-range-operator-space-before/) | None | |


### Custom media

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [custom-media-pattern](https://stylelint.io/user-guide/rules/custom-media-pattern/) | None | |


### Media query list

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [media-query-list-comma-newline-after](https://stylelint.io/user-guide/rules/media-query-list-comma-newline-after/) | None | |
| [media-query-list-comma-newline-before](https://stylelint.io/user-guide/rules/media-query-list-comma-newline-before/) | None | |
| [media-query-list-comma-space-after](https://stylelint.io/user-guide/rules/media-query-list-comma-space-after/) | None | |
| [media-query-list-comma-space-before](https://stylelint.io/user-guide/rules/media-query-list-comma-space-before/) | None | |


### At-rule

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [at-rule-blacklist](https://stylelint.io/user-guide/rules/at-rule-blacklist/) | [Regular expression on @-rule](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aat-rule-regular-expression) | |
| [at-rule-empty-line-before](https://stylelint.io/user-guide/rules/at-rule-empty-line-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [at-rule-name-case](https://stylelint.io/user-guide/rules/at-rule-name-case/) | [CSS should be written in lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| [at-rule-name-newline-after](https://stylelint.io/user-guide/rules/at-rule-name-newline-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [at-rule-name-space-after](https://stylelint.io/user-guide/rules/at-rule-name-space-after/) | None | |
| [at-rule-no-unknown](https://stylelint.io/user-guide/rules/at-rule-no-unknown/) | [Unknown @-rules should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-at-rules) | |
| [at-rule-no-vendor-prefix](https://stylelint.io/user-guide/rules/at-rule-no-vendor-prefix/) | [Experimental @-rules should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-atrule-usage) | |
| [at-rule-semicolon-newline-after](https://stylelint.io/user-guide/rules/at-rule-semicolon-newline-after/) | None | |
| [at-rule-whitelist](https://stylelint.io/user-guide/rules/at-rule-whitelist/) | [Regular expression on @-rule](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aat-rule-regular-expression) | |


### stylelint-disable comment

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [stylelint-disable-reason](https://stylelint.io/user-guide/rules/stylelint-disable-reason/) (deprecated) | None | Does not make sense in SonarQube |


### Comment

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [comment-empty-line-before](https://stylelint.io/user-guide/rules/comment-empty-line-before/) | None | |
| [comment-no-empty](https://stylelint.io/user-guide/rules/comment-no-empty/) | None | |
| [comment-whitespace-inside](https://stylelint.io/user-guide/rules/comment-whitespace-inside/) | None | |
| [comment-word-blacklist](https://stylelint.io/user-guide/rules/comment-word-blacklist/) | [Regular expression on comment](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acomment-regular-expression) | |


### General / Sheet

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [indentation](https://stylelint.io/user-guide/rules/indentation/) | None | |
| [max-empty-lines](https://stylelint.io/user-guide/rules/max-empty-lines/) | None | |
| [max-line-length](https://stylelint.io/user-guide/rules/max-line-length/) | [Lines should not be too long](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aline-length) | |
| [max-nesting-depth](https://stylelint.io/user-guide/rules/max-nesting-depth/) | [Rulesets should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Anested-rulesets)<br/>[Rulesets should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Anested-rulesets) | |
| [no-browser-hacks](https://stylelint.io/user-guide/rules/no-browser-hacks/) (deprecated) | [Underscore hack should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunderscore-property-hack)<br/>[Star hack should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Astar-property-hack) | |
| [no-descending-specificity](https://stylelint.io/user-guide/rules/no-descending-specificity/) | None | |
| [no-duplicate-selectors](https://stylelint.io/user-guide/rules/no-duplicate-selectors/) | None | |
| [no-empty-source](https://stylelint.io/user-guide/rules/no-empty-source/) | [Empty stylesheets should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-stylesheet) | |
| [no-eol-whitespace](https://stylelint.io/user-guide/rules/no-eol-whitespace/) | [Lines should not end with trailing whitespaces](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS1131) | |
| [no-extra-semicolons](https://stylelint.io/user-guide/rules/no-extra-semicolons/) | [Empty declarations should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-declaration) | |
| [no-indistinguishable-colors](https://stylelint.io/user-guide/rules/no-indistinguishable-colors/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [no-invalid-double-slash-comments](https://stylelint.io/user-guide/rules/no-invalid-double-slash-comments/) | None | |
| [no-missing-end-of-source-newline](https://stylelint.io/user-guide/rules/no-missing-end-of-source-newline/) | [Files should contain an empty new line at the end](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-line-end-of-file) | |
| [no-unknown-animations](https://stylelint.io/user-guide/rules/no-unknown-animations/) | None | |
| [no-unsupported-browser-features](https://stylelint.io/user-guide/rules/no-unsupported-browser-features/) (deprecated) | None | |


### stylelint-scss

| stylelint-css Rule | SonarQube Related Rules | Comments |
| ------------------ |------------------------ | -------- |
| [at-else-closing-brace-newline-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-else-closing-brace-newline-after/README.md) | None | |
| [at-else-closing-brace-space-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-else-closing-brace-space-after/README.md) | None | |
| [at-else-empty-line-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-else-empty-line-before/README.md) | None | |
| [at-extend-no-missing-placeholder](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-extend-no-missing-placeholder/README.md) | None | |
| [at-function-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-function-pattern/README.md) | [Custom functions should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Acustom-function-naming-convention) | |
| [at-if-closing-brace-newline-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-if-closing-brace-newline-after/README.md) | None | |
| [at-if-closing-brace-space-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-if-closing-brace-space-after/README.md) | None | |
| [at-import-no-partial-leading-underscore](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-import-no-partial-leading-underscore/README.md) | None | |
| [at-import-partial-extension-blacklist](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-import-partial-extension-blacklist/README.md) | None | |
| [at-import-partial-extension-whitelist](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-import-partial-extension-whitelist/README.md) | None | |
| [at-mixin-argumentless-call-parentheses](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-mixin-argumentless-call-parentheses/README.md) | [Useless parentheses following @include and @mixin with no parameter should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Auseless-parentheses-mixin-no-parameter) | |
| [at-mixin-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-mixin-pattern/README.md) | [Mixins should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Amixin-naming-convention) | |
| [dollar-variable-colon-newline-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-colon-newline-after/README.md) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [dollar-variable-colon-space-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-colon-space-after/README.md) | [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | |
| [dollar-variable-colon-space-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-colon-space-before/README.md) | [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | |
| [dollar-variable-empty-line-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-empty-line-before/README.md) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [dollar-variable-no-missing-interpolation](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-no-missing-interpolation/README.md) | None | |
| [dollar-variable-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-pattern/README.md) | [SCSS variables should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Ascss-variable-naming-convention) |
| [percent-placeholder-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/percent-placeholder-pattern/README.md) | [Placeholder selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aplaceholder-selector-naming-convention) |
| [double-slash-comment-empty-line-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/double-slash-comment-empty-line-before/README.md) | None |
| [double-slash-comment-inline](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/double-slash-comment-inline/README.md) | None |
| [double-slash-comment-whitespace-inside](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/double-slash-comment-whitespace-inside/README.md) | None |
| [declaration-nested-properties](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/declaration-nested-properties/README.md) | None |
| [media-feature-value-dollar-variable](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/media-feature-value-dollar-variable/README.md) | None |
| [operator-no-newline-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/operator-no-newline-after/README.md) | None |
| [operator-no-newline-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/operator-no-newline-before/README.md) | None |
| [operator-no-unspaced](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/operator-no-unspaced/README.md) | None |
| [partial-no-import](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/partial-no-import/README.md) | None |
| [selector-no-redundant-nesting-selector](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/selector-no-redundant-nesting-selector/README.md) | None |
