stylelint / SonarQube Rule Mapping
==================================

Note that there is not always a strict one-to-one relationship between [SonarQube rules](http://sonarqube.racodond.com/coding_rules#languages=scss%2Ccss%2Cless) and [stylelint rules](https://stylelint.io/user-guide/rules/).

## SonarQube => stylelint


### Common

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| ["!important" annotation should be placed at the end of the declaration](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimportant-position) | None | |
| ["!important" annotation should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimportant) | [declaration-no-important](https://stylelint.io/user-guide/rules/declaration-no-important/)<br/>[keyframe-declaration-no-important](https://stylelint.io/user-guide/rules/keyframe-declaration-no-important/) | |
| ["@font-face" rule should be made compatible with the required browsers](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afont-face-browser-compatibility) | None | |
| ["@import" rule should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimport) | None | |
| ["FIXME" tags should be handled](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS1134) | None | |
| ["NOSONAR" tags should not be used to switch off issues](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Anosonar) | None | |
| ["TODO" tags should be handled](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS1135) | None | |
| [@charset should be the first element in the style sheet and not be preceded by any character](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acharset-first) | None | |
| [@import rules should precede all other at-rules and style rules](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimport-first) | None | |
| [Box model size should be carefully reviewed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Abox-model) | None | |
| [Byte Order Mark (BOM) should not be used for UTF-8 files](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Abom-utf8-files) | None | |
| [Case-insensitive flag should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase-insensitive-flag) | None | |
| [Class selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aclass-selector-naming-convention) | [selector-class-pattern](https://stylelint.io/user-guide/rules/selector-class-pattern/) | |
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
| [Font files inlining should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Ainlining-font-files) | None | |
| [Gradient definitions should be set for all vendors](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Agradients) | None | |
| [ID selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aid-selector-naming-convention) | [selector-id-pattern](https://stylelint.io/user-guide/rules/selector-id-pattern/) | |
| [IDs in selectors should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aids) | [selector-no-id](https://stylelint.io/user-guide/rules/selector-no-id/) | |
| [Leading zeros should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aleading-zeros) | [number-leading-zero](https://stylelint.io/user-guide/rules/number-leading-zero/) | |
| [Lines should not be too long](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aline-length) | [max-line-length](https://stylelint.io/user-guide/rules/max-line-length/) | |
| [Lines should not end with trailing whitespaces](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS1131) | [no-eol-whitespace](https://stylelint.io/user-guide/rules/no-eol-whitespace/) | |
| [Missing vendor prefixes should be added to experimental properties](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acompatible-vendor-prefixes) | None | |
| [Name of overqualified element should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aoverqualified-elements) | None | |
| [Obsolete functions should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aobsolete-functions) | None | |
| [Obsolete properties should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aobsolete-properties) | None | |
| [Obsolete pseudo-elements and pseudo-classes should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aobsolete-pseudos) | None | |
| [Over-specified selectors should be simplified](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aoverspecific-selectors) | [selector-max-compound-selectors](https://stylelint.io/user-guide/rules/selector-max-compound-selectors/) | |
| [Properties that do not work with the "display" property should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Adisplay-property-grouping) | [declaration-block-no-ignored-properties](https://stylelint.io/user-guide/rules/declaration-block-no-ignored-properties/) | Rule should be improved on SonarQube side. See [issue #105](https://github.com/racodond/sonar-css-plugin/issues/105). |
| [Properties, functions, @-rule keywords, pseudo functions and pseudo identifiers should be lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | [property-case](https://stylelint.io/user-guide/rules/property-case/)<br/>[function-name-case](https://stylelint.io/user-guide/rules/function-name-case/)<br/>[at-rule-name-case](https://stylelint.io/user-guide/rules/at-rule-name-case/)<br/>[selector-pseudo-class-case](https://stylelint.io/user-guide/rules/selector-pseudo-class-case/)<br/>[selector-pseudo-element-case](https://stylelint.io/user-guide/rules/selector-pseudo-element-case/)<br/> | |
| [Property values should be valid](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Avalidate-property-value) | None | |
| [Protocol-relative URL should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aprotocol-relative-url) | [function-url-no-scheme-relative](https://stylelint.io/user-guide/rules/function-url-no-scheme-relative/) | |
| [Regular expression like selectors should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aregex-selectors) | None | |
| [Regular expression on comment](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acomment-regular-expression) | [comment-word-blacklist](https://stylelint.io/user-guide/rules/comment-word-blacklist/) | |
| [Regular expression on property](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aproperty-regular-expression) | [property-blacklist](https://stylelint.io/user-guide/rules/property-blacklist/) | This SonarQube rule template can be used to create stylelint property-blacklist rule. |
| [Rule properties should be alphabetically ordered](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aalphabetize-declarations) | [declaration-block-properties-order](declaration-block-properties-order) | |
| [Shorthand properties should be used whenever possible](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Ashorthand) | [declaration-block-no-redundant-longhand-properties](https://stylelint.io/user-guide/rules/declaration-block-no-redundant-longhand-properties/) | |
| [Shorthand properties should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Ado-not-use-shorthand-properties) | None | |
| [Single quotes should be used instead of double quotes for strings](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asingle-quotes) | [string-quotes](https://stylelint.io/user-guide/rules/string-quotes/) | |
| [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | [block-opening-brace-newline-after](https://stylelint.io/user-guide/rules/block-opening-brace-newline-after/)<br/>[declaration-colon-space-after](https://stylelint.io/user-guide/rules/declaration-colon-space-after/)<br/>[declaration-colon-space-before](https://stylelint.io/user-guide/rules/declaration-colon-space-before/)<br/>[declaration-bang-space-after](https://stylelint.io/user-guide/rules/declaration-bang-space-after/)<br/> | |
| [Source files should not have any duplicated blocks](http://sonarqube.racodond.com/coding_rules#rule_key=common-css%3ADuplicatedBlocks) | None | |
| [Standard properties should be specified along with vendor-prefixed properties](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Avendor-prefix) | None | |
| [Star hack should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Astar-property-hack) | [no-browser-hacks](https://stylelint.io/user-guide/rules/no-browser-hacks/) | |
| [Stylesheets should not "@import" too many other sheets](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS2735) | None | |
| [Stylesheets should not contain too many rules](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asheet-too-many-rules) | None | |
| [Stylesheets should not contain too many selectors](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS2732) | None | |
| [Tabulation characters should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Atab-character) | None | |
| [The number of web fonts should be reduced](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Afont-faces) | None | |
| [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | [block-no-single-line](https://stylelint.io/user-guide/rules/block-no-single-line/)<br/>[declaration-block-single-line-max-declarations](https://stylelint.io/user-guide/rules/declaration-block-single-line-max-declarations/) | |
| [Trailing zeros for numeric values should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Atrailing-zeros) | [number-no-trailing-zeros](https://stylelint.io/user-guide/rules/number-no-trailing-zeros/) | |
| [Underscore hack should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunderscore-property-hack) | [no-browser-hacks](https://stylelint.io/user-guide/rules/no-browser-hacks/) | |
| [Units for zero length values should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Azero-units) | [length-zero-no-unit](https://stylelint.io/user-guide/rules/length-zero-no-unit/) | |
| [Universal selector should not be used as key part](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Auniversal-selector) | [selector-no-universal](https://stylelint.io/user-guide/rules/selector-no-universal/) | |
| [Unknown @-rules should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-at-rules) | [at-rule-no-unknown](https://stylelint.io/user-guide/rules/at-rule-no-unknown/) | |
| [Unknown CSS functions should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-functions) | None | |
| [Unknown properties should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aknown-properties) | [property-no-unknown](https://stylelint.io/user-guide/rules/property-no-unknown/) | |
| [Unknown pseudo-elements and pseudo-classes should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-pseudo) | [selector-pseudo-class-no-unknown](https://stylelint.io/user-guide/rules/selector-pseudo-class-no-unknown/)<br/>[selector-pseudo-element-no-unknown](https://stylelint.io/user-guide/rules/selector-pseudo-element-no-unknown/) | |
| [URL should be quoted](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aquoted-url) | [function-url-quotes](https://stylelint.io/user-guide/rules/function-url-quotes/) | |


### Specific to CSS embedded in HTML/XHTML

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [CSS should not be embedded in HTML files](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aembedded-css) | None | |


### Specific to SCSS

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [@debug directives should not be used in production code](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Adebug) | None | |
| [@if ... @else if ... constructs should end with @else directive](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aif-elseif-without-else) | None | |
| [Always use 'through' instead of 'to' in @for directives](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Athrough-instead-of-to) | None | |
| [Conditions should not be too complex](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Atoo-complex-condition) | None | |
| [Control flow directives @if, @else if, @else, @for, @while, and @each should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Anested-control-flow-directives) | None | |
| [Custom functions should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Acustom-function-naming-convention) | None | |
| [Declarations and directives should be properly sorted](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Asort-declarations-and-directives) | None | |
| [Deprecated unescaped multiline strings should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Adeprecated-unescaped-multiline-string) | None | |
| [Empty control flow directive should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aempty-control-flow-directive) | None | |
| [Empty mixins should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aempty-mixin) | [block-no-empty](https://stylelint.io/user-guide/rules/block-no-empty/) | |
| [Mixins should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Amixin-naming-convention) | None | |
| [Nested properties should define at least two properties](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aat-least-two-nested-properties) | None | |
| [Placeholder selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aplaceholder-selector-naming-convention) | None | |
| [Related @if / @else if directives should not have the same condition](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aif-elseif-same-condition) | None | |
| [Rulesets should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Anested-rulesets) | [max-nesting-depth](https://stylelint.io/user-guide/rules/max-nesting-depth/) | |
| [SCSS variables should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Ascss-variable-naming-convention) | None | |
| [Single-line comments (//) should be preferred over multi-line comments (/* ... */)](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aprefer-single-line-comments) | None | |
| [Two branches in the same conditional structure should not have exactly the same implementation](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Abranch-same-implementation) | None | |
| [Useless parentheses following @include and @mixin with no parameter should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Auseless-parentheses-mixin-no-parameter) | None | |


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
| color-hex-case | None | |
| color-hex-length | None | |
| color-named | None | |
| color-no-hex | None | |
| color-no-invalid-hex | None | |


### Font family

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| font-family-name-quotes | None | Planned for SonarQube 3.1. See [issue #101](https://github.com/racodond/sonar-css-plugin/issues/101) |
| font-family-no-duplicate-names | None | Planned for SonarQube 3.1. See [issue #102](https://github.com/racodond/sonar-css-plugin/issues/102) |


### Font weight

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| font-weight-notation | None | |


### Function

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| function-blacklist | None | |
| function-calc-no-unspaced-operator | None | |
| function-comma-newline-after | None | |
| function-comma-newline-before | None | |
| function-comma-space-after | None | |
| function-comma-space-before | None | |
| function-linear-gradient-no-nonstandard-direction | None | |
| function-max-empty-lines | None | |
| function-name-case | [Properties, functions, @-rule keywords, pseudo functions and pseudo identifiers should be lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| function-parentheses-newline-inside | None | |
| function-parentheses-space-inside | None | |
| function-url-data-uris | None | |
| function-url-no-scheme-relative | [Protocol-relative URL should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aprotocol-relative-url) | |
| function-url-quotes | [URL should be quoted](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aquoted-url) | |
| function-url-scheme-whitelist | None | |
| function-whitelist | None | |
| function-whitelist | None | |


### Number

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| number-leading-zero | [Leading zeros should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aleading-zeros) | |
| number-max-precision | None | |
| number-no-trailing-zeros | [Trailing zeros for numeric values should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Atrailing-zeros) | |


### String

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| string-no-newline | None | |
| string-quotes | [Single quotes should be used instead of double quotes for strings](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asingle-quotes) | |


### Length

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| length-zero-no-unit | [Units for zero length values should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Azero-units) | |


### Time

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| time-no-imperceptible | None | |


### Unit

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| unit-blacklist | None | |
| unit-case | None | |
| unit-no-unknown | None | |
| unit-whitelist | None | |


### Value

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| value-keyword-case | None | |
| value-no-vendor-prefix | [Experimental identifiers should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-identifier-usage) | |


### Value list

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| value-list-comma-newline-after | None | |
| value-list-comma-newline-before | None | |
| value-list-comma-space-after | None | |
| value-list-comma-space-before | None | |


### Custom property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| custom-property-empty-line-before | None | |
| custom-property-no-outside-root | None | |
| custom-property-pattern | [CSS variables should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acss-variable-naming-convention) | |


### Shorthand property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| shorthand-property-no-redundant-values | None | |


### Property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| property-blacklist | [Regular expression on property](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aproperty-regular-expression) | |
| property-case | [Properties, functions, @-rule keywords, pseudo functions and pseudo identifiers should be lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| property-no-unknown | [Unknown properties should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aknown-properties) | |
| property-no-vendor-prefix | [Experimental properties should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-property-usage) | |
| property-whitelist | None | |


### Keyframe declaration

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| keyframe-declaration-no-important | ["!important" annotation should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimportant) | |


### Declaration

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| declaration-bang-space-after | [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | |
| declaration-bang-space-before | None | |
| declaration-colon-newline-after | None | |
| declaration-colon-space-after | [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | |
| declaration-colon-space-before | [Source code should comply with formatting standards](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aformatting) | |
| declaration-empty-line-before | None | |
| declaration-no-important | ["!important" annotation should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aimportant) | |
| declaration-property-unit-blacklist | None | |
| declaration-property-unit-whitelist | None | |
| declaration-property-value-blacklistt | None | |
| declaration-property-value-whitelist | None | |


### Declaration block

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| declaration-block-no-duplicate-properties | [Duplicated properties should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aduplicate-properties) | |
| declaration-block-no-ignored-properties | [Properties that do not work with the "display" property should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Adisplay-property-grouping) | |
| declaration-block-no-redundant-longhand-properties | [Shorthand properties should be used whenever possible](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Ashorthand) | |
| declaration-block-no-shorthand-property-overrides | None | |
| declaration-block-properties-order | [Rule properties should be alphabetically ordered](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aalphabetize-declarations) | |
| declaration-block-semicolon-newline-after | None | |
| declaration-block-semicolon-newline-before | None | |
| declaration-block-semicolon-space-after | None | |
| declaration-block-semicolon-space-before | None | |
| declaration-block-single-line-max-declarations | [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | |
| declaration-block-trailing-semicolon | [Each declaration should end with a semicolon](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Asemicolon-declaration) | |


### Block

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| block-closing-brace-empty-line-before | None | |
| block-closing-brace-newline-after | None | |
| block-closing-brace-newline-before | None | |
| block-closing-brace-space-after | None | |
| block-closing-brace-space-before | None | |
| block-no-empty | [Empty mixins should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Aempty-mixin)<br/>[Empty rules should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-rules) | |
| block-no-single-line | [There should be one single declaration per line](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aone-declaration-per-line) | |
| block-opening-brace-newline-after | None | |
| block-opening-brace-newline-before | None | |
| block-opening-brace-space-after | None | |
| block-opening-brace-space-before | None | |


### Selector

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| selector-attribute-brackets-space-inside | None | |
| selector-attribute-operator-blacklist | None | |
| selector-attribute-operator-space-after | None | |
| selector-attribute-operator-space-before | None | |
| selector-attribute-operator-whitelist | None | |
| selector-attribute-quotes | None | |
| selector-class-pattern | [Class selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aclass-selector-naming-convention) | |
| selector-combinator-space-after | None | |
| selector-combinator-space-before | None | |
| selector-descendant-combinator-no-non-space | None | |
| selector-id-pattern | [ID selectors should follow a naming convention](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aid-selector-naming-convention) | |
| selector-max-compound-selectors | [Over-specified selectors should be simplified](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aoverspecific-selectors) | |
| selector-max-specificity | None | |
| selector-nested-pattern | None | |
| selector-no-attribute | None | |
| selector-no-combinator | None | |
| selector-no-empty | None | |
| selector-no-id | [IDs in selectors should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aids) | |
| selector-no-qualifying-type | None | |
| selector-no-type | None | |
| selector-no-universal | [Universal selector should not be used as key part](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Auniversal-selector) | |
| selector-no-vendor-prefix | None | |
| selector-pseudo-class-blacklist | None | |
| selector-pseudo-class-case | [Properties, functions, @-rule keywords, pseudo functions and pseudo identifiers should be lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| selector-pseudo-class-no-unknown | [Unknown pseudo-elements and pseudo-classes should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-pseudo) | |
| selector-pseudo-class-parentheses-space-inside | None | |
| selector-pseudo-class-whitelist | None | |
| selector-pseudo-element-case | [Properties, functions, @-rule keywords, pseudo functions and pseudo identifiers should be lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| selector-pseudo-element-colon-notation | None | |
| selector-pseudo-element-no-unknown | [Unknown pseudo-elements and pseudo-classes should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-pseudo) | |
| selector-root-no-composition | None | |
| selector-type-case | None | |
| selector-type-no-unknown | None | |
| selector-max-empty-lines | None | |


### Selector list

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| selector-list-comma-newline-after | None | |
| selector-list-comma-newline-before | None | |
| selector-list-comma-space-after | None | |
| selector-list-comma-space-before | None | |


### Root rule

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| root-no-standard-properties | None | |


### Rule

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| rule-nested-empty-line-before | None | |
| rule-non-nested-empty-line-before | None | |


### Media feature

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| media-feature-colon-space-after | None | |
| media-feature-colon-space-before | None | |
| media-feature-name-blacklist | None | |
| media-feature-name-case | None | |
| media-feature-name-no-unknown | None | |
| media-feature-name-no-vendor-prefix | None | |
| media-feature-name-whitelist | None | |
| media-feature-no-missing-punctuation | None | |
| media-feature-parentheses-space-inside | None | |
| media-feature-range-operator-space-after | None | |
| media-feature-range-operator-space-before | None | |


### Custom media

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| custom-media-pattern | None | |


### Media query list

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| media-query-list-comma-newline-after | None | |
| media-query-list-comma-newline-before | None | |
| media-query-list-comma-space-after | None | |
| media-query-list-comma-space-before | None | |


### At-rule

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| at-rule-blacklist | None | |
| at-rule-empty-line-before | None | |
| at-rule-name-case | [Properties, functions, @-rule keywords, pseudo functions and pseudo identifiers should be lower case](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acase) | |
| at-rule-name-newline-after | None | |
| at-rule-name-space-after | None | |
| at-rule-no-unknown | [Unknown @-rules should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunknown-at-rules) | |
| at-rule-no-vendor-prefix | [Experimental @-rules should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aexperimental-atrule-usage) | |
| at-rule-semicolon-newline-after | None | |
| at-rule-whitelist | None | |


### stylelint-disable comment

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| stylelint-disable-reason | None | |


### Comment

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| comment-empty-line-before | None | |
| comment-no-empty | None | |
| comment-whitespace-inside | None | |
| comment-word-blacklist | [Regular expression on comment](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Acomment-regular-expression) | |


### General / Sheet

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| indentation | None | |
| max-empty-lines | None | |
| max-line-length | [Lines should not be too long](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aline-length) | |
| max-nesting-depth | [Rulesets should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=less%3Anested-rulesets)<br/>[Rulesets should not be nested too deeply](http://sonarqube.racodond.com/coding_rules#rule_key=scss%3Anested-rulesets) | |
| no-browser-hacks | [Underscore hack should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aunderscore-property-hack)<br/>[Star hack should not be used](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Astar-property-hack) | |
| no-descending-specificity | None | |
| no-duplicate-selectors | None | |
| no-empty-source | [Empty stylesheets should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-stylesheet) | |
| no-eol-whitespace | [Lines should not end with trailing whitespaces](http://sonarqube.racodond.com/coding_rules#rule_key=css%3AS1131) | |
| no-extra-semicolons | [Empty declarations should be removed](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-declaration) | |
| no-indistinguishable-colors | None | |
| no-invalid-double-slash-comments | None | |
| no-missing-end-of-source-newline | [Files should contain an empty new line at the end](http://sonarqube.racodond.com/coding_rules#rule_key=css%3Aempty-line-end-of-file) | |
| no-unknown-animations | None | |
| no-unsupported-browser-features | None | |
