stylelint / SonarQube Rule Mapping
==================================

Note that there is not always a strict one-to-one relationship between SonarQube and stylelint rules.

## SonarQube => stylelint


### Common

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| "!important" flag should be placed at the end of the declaration | None | |
| "!important" flag should not be used | |
| "@font-face" rule should be made compatible with the required browsers | None | |
| "@import" rule should not be used | None | |
| "FIXME" tags should be handled | None | |
| "NOSONAR" tags should not be used to switch off issues | None | |
| "stylelint-disable" tags should be removed | None | |
| "stylelint-enable" tags should be removed | None | |
| "text-transform" properties should not be set to "uppercase" or "capitalize" for some languages | None | |
| "TODO" tags should be handled | None | |
| @charset should be the first element in the style sheet and not be preceded by any character | None | |
| @import rules should precede all other at-rules and style rules | None | |
| Box model size should be carefully reviewed | None | |
| Byte Order Mark (BOM) should not be used for UTF-8 files | None | |
| Case-insensitive flag should not be used | None | |
| Class selectors should follow a naming convention | |
| CSS should be written in lower case | |
| CSS variables should follow a naming convention | |
| Deprecated system colors should not be used | None | |
| Duplicated background images should be removed | None | |
| Duplicated properties should be removed | |
| Each declaration should end with a semicolon | |
| Empty declarations should be removed | |
| Empty rules should be removed | |
| Empty stylesheets should be removed | |
| Experimental @-rules should not be used | |
| Experimental functions should not be used | None | |
| Experimental identifiers should not be used | |
| Experimental properties should not be used | stylelint is limited to vendor-prefix properties|
| Experimental pseudo-elements and pseudo-classes should not be used | None | |
| Experimental selector combinators should not be used | None | |
| Files should contain an empty new line at the end | |
| Files should not have too many lines | None | |
| font-family properties should end with a generic font family | None | |
| Font family names should be quoted | |
| font-family should not contain duplicated font family names | |
| Font files inlining should not be used | None | |
| Generic family names should not be quoted | |
| Gradient definitions should be set for all vendors | None | |
| ID selectors should follow a naming convention | |
| IDs in selectors should be removed | |
| Leading zeros should be removed | |
| Lines should not be too long | |
| Lines should not end with trailing whitespaces | |
| Missing vendor prefixes should be added to experimental properties | None | |
| Name of overqualified element should be removed | None | |
| Named colors should not be used | |
| Number precision should not be too high | |
| Obsolete functions should not be used | None | |
| Obsolete properties should not be used | None | |
| Obsolete pseudo-elements and pseudo-classes should not be used | None | |
| Obsolete selector combinators should not be used | None | |
| Over-specified selectors should be simplified | |
| Properties that do not work with the "display" property should be removed. |
| Property values should be valid | |
| Protocol-relative URL should not be used | |
| Regular expression like selectors should not be used | None | |
| Regular expression on comment | |
| Regular expression on @-rule | This SonarQube rule template can be used to create a rule similar to stylelint at-rule-blacklist and at-rule-whitelist. |
| Regular expression on function | This SonarQube rule template can be used to create a rule similar to stylelint function-blacklist and function-whitelist. |
| Regular expression on property | This SonarQube rule template can be used to create a rule similar to stylelint property-blacklist and property-whitelist. |
| Regular expression on unit | This SonarQube rule template can be used to create a rule similar to stylelint unit-blacklist and unit-whitelist. |
| Rule properties should be alphabetically ordered | |
| Shorthand properties should be used whenever possible | |
| Shorthand properties should not be used | None | |
| Single quotes should be used instead of double quotes for strings | |
| Source code should comply with formatting standards | |
| Source files should not have any duplicated blocks | None | |
| Standard properties should be specified along with vendor-prefixed properties | None | |
| Star hack should not be used | |
| Stylesheets should not "@import" too many other sheets | None | |
| Stylesheets should not contain too many rules | None | |
| Stylesheets should not contain too many selectors | None | |
| Tabulation characters should not be used | None | |
| The number of web fonts should be reduced | None | |
| There should be one single declaration per line | |
| Trailing zeros for numeric values should be removed | |
| Underscore hack should not be used | |
| Units for zero length values should be removed | |
| Universal selector should not be used as key part | |
| Unknown @-rules should be removed | |
| Unknown CSS functions should be removed | None | |
| Unknown properties should be removed | |
| Unknown pseudo-elements and pseudo-classes should be removed | |
| Unknown type selectors should be removed | |
| URL should be quoted | |


### Specific to CSS embedded in HTML/XHTML

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| CSS should not be embedded in HTML files | None | |


### Specific to SCSS

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| @debug directives should not be used in production code | None | |
| @extend directives should not be used | None | |
| @if ... @else if ... constructs should end with @else directive | None | |
| Always use 'through' instead of 'to' in @for directives | None | |
| Conditions should not be too complex | None | |
| Control flow directives @if, @else if, @else, @for, @while, and @each should not be nested too deeply | None | |
| Custom functions should follow a naming convention | |
| Declarations and directives should be properly sorted | None | |
| Deprecated unescaped multiline strings should not be used | None | |
| Empty control flow directive should be removed | None | |
| Empty mixins should be removed | |
| Mixins should follow a naming convention | |
| Nested properties should define at least two properties | None | |
| Placeholder selectors should follow a naming convention | |
| Related @if / @else if directives should not have the same condition | None | |
| Rulesets should not be nested too deeply | |
| SCSS variables should follow a naming convention | |
| Single-line comments (//) should be preferred over multi-line comments (/* ... */) | None | |
| Two branches in the same conditional structure should not have exactly the same implementation | None | |
| Useless parentheses following @include and @mixin with no parameter should be removed | |


### Specific to Less

| SonarQube Rule | stylelint Related Rules | Comments |
| -------------- |------------------------ | -------- |
| Deprecated "e" escaping function should be replaced with ~"value" syntax | None | |
| Less variables should follow a naming convention | None | |
| Rulesets should not be nested too deeply | |
| Same variable should not be declared multiple times within the same scope | None | |
| Single-line comments (//) should be preferred over multi-line comments (/* ... */) | None | |
| Unknown CSS / Less functions should be removed | None | |
| Variables should be declared at the beginning of the block | None | |


## stylelint => SonarQube

### Color

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [color-hex-case](https://stylelint.io/user-guide/rules/color-hex-case/) | CSS should be written in lower case | |
| [color-hex-length](https://stylelint.io/user-guide/rules/color-hex-length/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [color-named](https://stylelint.io/user-guide/rules/color-named/) | Named colors should not be used | |
| [color-no-hex](https://stylelint.io/user-guide/rules/color-no-hex/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [color-no-invalid-hex](https://stylelint.io/user-guide/rules/color-no-invalid-hex/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Font family

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [font-family-name-quotes](https://stylelint.io/user-guide/rules/font-family-name-quotes/) | Generic family names should not be quoted | |
| [font-family-no-duplicate-names](https://stylelint.io/user-guide/rules/font-family-no-duplicate-names/) | font-family should not contain duplicated font family names | |


### Font weight

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [font-weight-notation](https://stylelint.io/user-guide/rules/font-weight-notation/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Function

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [function-blacklist](https://stylelint.io/user-guide/rules/function-blacklist/) | Regular expression on function | |
| [function-calc-no-unspaced-operator](https://stylelint.io/user-guide/rules/function-calc-no-unspaced-operator/) | None | |
| [function-comma-newline-after](https://stylelint.io/user-guide/rules/function-comma-newline-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-comma-newline-before](https://stylelint.io/user-guide/rules/function-comma-newline-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-comma-space-after](https://stylelint.io/user-guide/rules/function-comma-space-after/) | None | |
| [function-comma-space-before](https://stylelint.io/user-guide/rules/function-comma-space-before/) | None | |
| [function-linear-gradient-no-nonstandard-direction](https://stylelint.io/user-guide/rules/function-linear-gradient-no-nonstandard-direction/) | None | |
| [function-max-empty-lines](https://stylelint.io/user-guide/rules/function-max-empty-lines/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-name-case](https://stylelint.io/user-guide/rules/function-name-case/) | CSS should be written in lower case | |
| [function-parentheses-newline-inside](https://stylelint.io/user-guide/rules/function-parentheses-newline-inside/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-parentheses-space-inside](https://stylelint.io/user-guide/rules/function-parentheses-space-inside/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-url-data-uris](https://stylelint.io/user-guide/rules/function-url-data-uris/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-url-no-scheme-relative](https://stylelint.io/user-guide/rules/function-url-no-scheme-relative/) | Protocol-relative URL should not be used | |
| [function-url-quotes](https://stylelint.io/user-guide/rules/function-url-quotes/) | URL should be quoted | |
| [function-url-scheme-whitelist](https://stylelint.io/user-guide/rules/function-url-scheme-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [function-whitelist](https://stylelint.io/user-guide/rules/function-whitelist/) | Regular expression on function | |
| [function-whitespace-after](https://stylelint.io/user-guide/rules/function-whitespace-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Number

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [number-leading-zero](https://stylelint.io/user-guide/rules/number-leading-zero/) | Leading zeros should be removed | |
| [number-max-precision](https://stylelint.io/user-guide/rules/number-max-precision/) | Number precision should not be too high | |
| [number-no-trailing-zeros](https://stylelint.io/user-guide/rules/number-no-trailing-zeros/) | Trailing zeros for numeric values should be removed | |


### String

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [string-no-newline](https://stylelint.io/user-guide/rules/string-no-newline/) | None | |
| [string-quotes](https://stylelint.io/user-guide/rules/string-quotes/) | Single quotes should be used instead of double quotes for strings | |


### Length

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [length-zero-no-unit](https://stylelint.io/user-guide/rules/length-zero-no-unit/) | Units for zero length values should be removed | |


### Time

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [time-min-milliseconds](https://stylelint.io/user-guide/rules/time-min-milliseconds/) | None | |
| [time-no-imperceptible](https://stylelint.io/user-guide/rules/time-no-imperceptible/) (deprecated) | None | |


### Unit

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [unit-blacklist](https://stylelint.io/user-guide/rules/unit-blacklist/) | Regular expression on unit | |
| [unit-case](https://stylelint.io/user-guide/rules/unit-case/) | CSS should be written in lower case | |
| [unit-no-unknown](https://stylelint.io/user-guide/rules/unit-no-unknown/) | None | For instance, 10zc will be considered as two value elements in SonarQube: number '10' and identifier 'zc'. |
| [unit-whitelist](https://stylelint.io/user-guide/rules/unit-whitelist/) | Regular expression on unit | |


### Value

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [value-keyword-case](https://stylelint.io/user-guide/rules/value-keyword-case/) | None | |
| [value-no-vendor-prefix](https://stylelint.io/user-guide/rules/value-no-vendor-prefix/) | Experimental identifiers should not be used | |


### Value list

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [value-list-comma-newline-after](https://stylelint.io/user-guide/rules/value-list-comma-newline-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [value-list-comma-newline-before](https://stylelint.io/user-guide/rules/value-list-comma-newline-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [value-list-comma-space-after](https://stylelint.io/user-guide/rules/value-list-comma-space-after/) | Source code should comply with formatting standards | |
| [value-list-comma-space-before](https://stylelint.io/user-guide/rules/value-list-comma-space-before/) | Source code should comply with formatting standards | |
| [value-list-max-empty-lines](https://stylelint.io/user-guide/rules/value-list-max-empty-lines/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Custom property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [custom-property-empty-line-before](https://stylelint.io/user-guide/rules/custom-property-empty-line-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [custom-property-no-outside-root](https://stylelint.io/user-guide/rules/custom-property-no-outside-root/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [custom-property-pattern](https://stylelint.io/user-guide/rules/custom-property-pattern/) | CSS variables should follow a naming convention | |


### Shorthand property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [shorthand-property-no-redundant-values](https://stylelint.io/user-guide/rules/shorthand-property-no-redundant-values/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Property

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [property-blacklist](https://stylelint.io/user-guide/rules/property-blacklist/) | Regular expression on property | |
| [property-case](https://stylelint.io/user-guide/rules/property-case/) | CSS should be written in lower case | |
| [property-no-unknown](https://stylelint.io/user-guide/rules/property-no-unknown/) | Unknown properties should be removed | |
| [property-no-vendor-prefix](https://stylelint.io/user-guide/rules/property-no-vendor-prefix/) | Experimental properties should not be used | |
| [property-whitelist](https://stylelint.io/user-guide/rules/property-whitelist/) | Regular expression on property | |


### Keyframe declaration

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [keyframe-declaration-no-important](https://stylelint.io/user-guide/rules/keyframe-declaration-no-important/) | "!important" flag should not be used | |


### Declaration

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [declaration-bang-space-after](https://stylelint.io/user-guide/rules/declaration-bang-space-after/) | Source code should comply with formatting standards | |
| [declaration-bang-space-before](https://stylelint.io/user-guide/rules/declaration-bang-space-before/) | None | |
| [declaration-colon-newline-after](https://stylelint.io/user-guide/rules/declaration-colon-newline-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-colon-space-after](https://stylelint.io/user-guide/rules/declaration-colon-space-after/) | Source code should comply with formatting standards | |
| [declaration-colon-space-before](https://stylelint.io/user-guide/rules/declaration-colon-space-before/) | Source code should comply with formatting standards | |
| [declaration-empty-line-before](https://stylelint.io/user-guide/rules/declaration-empty-line-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-no-important](https://stylelint.io/user-guide/rules/declaration-no-important/) | "!important" flag should not be used | |
| [declaration-property-unit-blacklist](https://stylelint.io/user-guide/rules/declaration-property-unit-blacklist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-property-unit-whitelist](https://stylelint.io/user-guide/rules/declaration-property-unit-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-property-value-blacklist](https://stylelint.io/user-guide/rules/declaration-property-value-blacklist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [declaration-property-value-whitelist](https://stylelint.io/user-guide/rules/declaration-property-value-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |


### Declaration block

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [declaration-block-no-duplicate-properties](https://stylelint.io/user-guide/rules/declaration-block-no-duplicate-properties/) | Duplicated properties should be removed | |
| [declaration-block-no-ignored-properties](https://stylelint.io/user-guide/rules/declaration-block-no-ignored-properties/) (deprecated) | Properties that do not work with the "display" property should be removed | |
| [declaration-block-no-redundant-longhand-properties](https://stylelint.io/user-guide/rules/declaration-block-no-redundant-longhand-properties/) | Shorthand properties should be used whenever possible | |
| [declaration-block-no-shorthand-property-overrides](https://stylelint.io/user-guide/rules/declaration-block-no-shorthand-property-overrides/) | None | |
| [declaration-block-properties-order](https://stylelint.io/user-guide/rules/declaration-block-properties-order/) (deprecated) | Rule properties should be alphabetically ordered | |
| [declaration-block-semicolon-newline-after](https://stylelint.io/user-guide/rules/declaration-block-semicolon-newline-after/) | There should be one single declaration per line | |
| [declaration-block-semicolon-newline-before](https://stylelint.io/user-guide/rules/declaration-block-semicolon-newline-before/) | There should be one single declaration per line | |
| [declaration-block-semicolon-space-after](https://stylelint.io/user-guide/rules/declaration-block-semicolon-space-after/) | There should be one single declaration per line | |
| [declaration-block-semicolon-space-before](https://stylelint.io/user-guide/rules/declaration-block-semicolon-space-before/) | None | |
| [declaration-block-single-line-max-declarations](https://stylelint.io/user-guide/rules/declaration-block-single-line-max-declarations/) | There should be one single declaration per line | |
| [declaration-block-trailing-semicolon](https://stylelint.io/user-guide/rules/declaration-block-trailing-semicolon/) | Each declaration should end with a semicolon | |


### Block

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [block-closing-brace-empty-line-before](https://stylelint.io/user-guide/rules/block-closing-brace-empty-line-before/) | None | |
| [block-closing-brace-newline-after](https://stylelint.io/user-guide/rules/block-closing-brace-newline-after/) | None | |
| [block-closing-brace-newline-before](https://stylelint.io/user-guide/rules/block-closing-brace-newline-before/) | None | |
| [block-closing-brace-space-after](https://stylelint.io/user-guide/rules/block-closing-brace-space-after/) | None | |
| [block-closing-brace-space-before](https://stylelint.io/user-guide/rules/block-closing-brace-space-before/) | None | |
| [block-no-empty](https://stylelint.io/user-guide/rules/block-no-empty/) | Empty mixins should be removed | |
| [block-no-single-line](https://stylelint.io/user-guide/rules/block-no-single-line/) (deprecated) | There should be one single declaration per line | |
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
| [selector-class-pattern](https://stylelint.io/user-guide/rules/selector-class-pattern/) | Class selectors should follow a naming convention | |
| [selector-combinator-space-after](https://stylelint.io/user-guide/rules/selector-combinator-space-after/) | None | |
| [selector-combinator-space-before](https://stylelint.io/user-guide/rules/selector-combinator-space-before/) | None | |
| [selector-descendant-combinator-no-non-space](https://stylelint.io/user-guide/rules/selector-descendant-combinator-no-non-space/) | None | |
| [selector-id-pattern](https://stylelint.io/user-guide/rules/selector-id-pattern/) | ID selectors should follow a naming convention | |
| [selector-max-compound-selectors](https://stylelint.io/user-guide/rules/selector-max-compound-selectors/) | Over-specified selectors should be simplified | |
| [selector-max-specificity](https://stylelint.io/user-guide/rules/selector-max-specificity/) | None | Not convinced by the added value of such a rule. Use Over-specified selectors should be simplified instead. No plan to implement it in SonarQube. |
| [selector-nested-pattern](https://stylelint.io/user-guide/rules/selector-nested-pattern/) | None | |
| [selector-no-attribute](https://stylelint.io/user-guide/rules/selector-no-attribute/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-combinator](https://stylelint.io/user-guide/rules/selector-no-combinator/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-empty](https://stylelint.io/user-guide/rules/selector-no-empty/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-id](https://stylelint.io/user-guide/rules/selector-no-id/) | IDs in selectors should be removed | |
| [selector-no-qualifying-type](https://stylelint.io/user-guide/rules/selector-no-qualifying-type/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-type](https://stylelint.io/user-guide/rules/selector-no-type/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-no-universal](https://stylelint.io/user-guide/rules/selector-no-universal/) | Universal selector should not be used as key part | |
| [selector-no-vendor-prefix](https://stylelint.io/user-guide/rules/selector-no-vendor-prefix/) | None | |
| [selector-pseudo-class-blacklist](https://stylelint.io/user-guide/rules/selector-pseudo-class-blacklist/) | None | |
| [selector-pseudo-class-case](https://stylelint.io/user-guide/rules/selector-pseudo-class-case/) | CSS should be written in lower case | |
| [selector-pseudo-class-no-unknown](https://stylelint.io/user-guide/rules/selector-pseudo-class-no-unknown/) | Unknown pseudo-elements and pseudo-classes should be removed | |
| [selector-pseudo-class-parentheses-space-inside](https://stylelint.io/user-guide/rules/selector-pseudo-class-parentheses-space-inside/) | None | |
| [selector-pseudo-class-whitelist](https://stylelint.io/user-guide/rules/selector-pseudo-class-whitelist/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-pseudo-element-case](https://stylelint.io/user-guide/rules/selector-pseudo-element-case/) | CSS should be written in lower case | |
| [selector-pseudo-element-colon-notation](https://stylelint.io/user-guide/rules/selector-pseudo-element-colon-notation/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-pseudo-element-no-unknown](https://stylelint.io/user-guide/rules/selector-pseudo-element-no-unknown/) | Unknown pseudo-elements and pseudo-classes should be removed | |
| [selector-root-no-composition](https://stylelint.io/user-guide/rules/selector-root-no-composition/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [selector-type-case](https://stylelint.io/user-guide/rules/selector-type-case/) | CSS should be written in lower case | |
| [selector-type-no-unknown](https://stylelint.io/user-guide/rules/selector-type-no-unknown/) | Unknown type selectors should be removed | |
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
| [at-rule-blacklist](https://stylelint.io/user-guide/rules/at-rule-blacklist/) | Regular expression on @-rule | |
| [at-rule-empty-line-before](https://stylelint.io/user-guide/rules/at-rule-empty-line-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [at-rule-name-case](https://stylelint.io/user-guide/rules/at-rule-name-case/) | CSS should be written in lower case | |
| [at-rule-name-newline-after](https://stylelint.io/user-guide/rules/at-rule-name-newline-after/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [at-rule-name-space-after](https://stylelint.io/user-guide/rules/at-rule-name-space-after/) | None | |
| [at-rule-no-unknown](https://stylelint.io/user-guide/rules/at-rule-no-unknown/) | Unknown @-rules should be removed | |
| [at-rule-no-vendor-prefix](https://stylelint.io/user-guide/rules/at-rule-no-vendor-prefix/) | Experimental @-rules should not be used | |
| [at-rule-semicolon-newline-after](https://stylelint.io/user-guide/rules/at-rule-semicolon-newline-after/) | None | |
| [at-rule-semicolon-space-before](https://stylelint.io/user-guide/rules/at-rule-semicolon-space-before/) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [at-rule-whitelist](https://stylelint.io/user-guide/rules/at-rule-whitelist/) | Regular expression on @-rule | |


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
| [comment-word-blacklist](https://stylelint.io/user-guide/rules/comment-word-blacklist/) | Regular expression on comment | |


### General / Sheet

| stylelint Rule | SonarQube Related Rules | Comments |
| -------------- |------------------------ | -------- |
| [indentation](https://stylelint.io/user-guide/rules/indentation/) | None | |
| [max-empty-lines](https://stylelint.io/user-guide/rules/max-empty-lines/) | None | |
| [max-line-length](https://stylelint.io/user-guide/rules/max-line-length/) | Lines should not be too long | |
| [max-nesting-depth](https://stylelint.io/user-guide/rules/max-nesting-depth/) | Rulesets should not be nested too deeply | |
| [no-browser-hacks](https://stylelint.io/user-guide/rules/no-browser-hacks/) (deprecated) | Underscore hack should not be used | |
| [no-descending-specificity](https://stylelint.io/user-guide/rules/no-descending-specificity/) | None | |
| [no-duplicate-selectors](https://stylelint.io/user-guide/rules/no-duplicate-selectors/) | None | |
| [no-empty-source](https://stylelint.io/user-guide/rules/no-empty-source/) | Empty stylesheets should be removed | |
| [no-eol-whitespace](https://stylelint.io/user-guide/rules/no-eol-whitespace/) | Lines should not end with trailing whitespaces | |
| [no-extra-semicolons](https://stylelint.io/user-guide/rules/no-extra-semicolons/) | Empty declarations should be removed | |
| [no-indistinguishable-colors](https://stylelint.io/user-guide/rules/no-indistinguishable-colors/) (deprecated) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [no-invalid-double-slash-comments](https://stylelint.io/user-guide/rules/no-invalid-double-slash-comments/) | None | |
| [no-missing-end-of-source-newline](https://stylelint.io/user-guide/rules/no-missing-end-of-source-newline/) | Files should contain an empty new line at the end | |
| [no-unknown-animations](https://stylelint.io/user-guide/rules/no-unknown-animations/) | None | |
| [no-unsupported-browser-features](https://stylelint.io/user-guide/rules/no-unsupported-browser-features/) (deprecated) | None | |


### stylelint-scss

| stylelint-css Rule | SonarQube Related Rules | Comments |
| ------------------ |------------------------ | -------- |
| [at-else-closing-brace-newline-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-else-closing-brace-newline-after/README.md) | None | |
| [at-else-closing-brace-space-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-else-closing-brace-space-after/README.md) | None | |
| [at-else-empty-line-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-else-empty-line-before/README.md) | None | |
| [at-extend-no-missing-placeholder](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-extend-no-missing-placeholder/README.md) | None | |
| [at-function-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-function-pattern/README.md) | Custom functions should follow a naming convention | |
| [at-if-closing-brace-newline-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-if-closing-brace-newline-after/README.md) | None | |
| [at-if-closing-brace-space-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-if-closing-brace-space-after/README.md) | None | |
| [at-import-no-partial-leading-underscore](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-import-no-partial-leading-underscore/README.md) | None | |
| [at-import-partial-extension-blacklist](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-import-partial-extension-blacklist/README.md) | None | |
| [at-import-partial-extension-whitelist](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-import-partial-extension-whitelist/README.md) | None | |
| [at-mixin-argumentless-call-parentheses](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-mixin-argumentless-call-parentheses/README.md) | Useless parentheses following @include and @mixin with no parameter should be removed | |
| [at-mixin-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/at-mixin-pattern/README.md) | Mixins should follow a naming convention | |
| [dollar-variable-colon-newline-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-colon-newline-after/README.md) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [dollar-variable-colon-space-after](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-colon-space-after/README.md) | Source code should comply with formatting standards | |
| [dollar-variable-colon-space-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-colon-space-before/README.md) | Source code should comply with formatting standards | |
| [dollar-variable-empty-line-before](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-empty-line-before/README.md) | None | Not convinced by the added value of such a rule. No plan to implement it in SonarQube. |
| [dollar-variable-no-missing-interpolation](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-no-missing-interpolation/README.md) | None | |
| [dollar-variable-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/dollar-variable-pattern/README.md) | SCSS variables should follow a naming convention |
| [percent-placeholder-pattern](https://github.com/kristerkari/stylelint-scss/blob/master/src/rules/percent-placeholder-pattern/README.md) | Placeholder selectors should follow a naming convention |
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
