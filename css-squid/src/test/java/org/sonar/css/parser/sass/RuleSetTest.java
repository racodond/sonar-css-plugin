package org.sonar.css.parser.sass;

import org.sonar.css.parser.CssGrammar;

import org.junit.Test;
import org.sonar.css.parser.SassGrammar;
import org.sonar.css.parser.TestBase;
import org.sonar.sslr.parser.LexerlessGrammar;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class RuleSetTest extends TestBase {

  private LexerlessGrammar b = SassGrammar.createGrammar();

  @Test
  public void variable() {
    assertThat(b.rule(CssGrammar.ruleset))
        .matches(
            code(
            "#main p {" +
              "  color: #00ff00;" +
              "  width: 97%;" +
              "" +
              "  .redbox {" +
              "    background-color: #ff0000;" +
              "    color: #000000;" +
              "  }" +
              "}"
            ))
        .matches(
            code(
            "a {" +
              "  font-weight: bold;" +
              "  text-decoration: none;" +
              "  body.firefox & { font-weight: normal; };" +
              "  &:hover { text-decoration: underline; };" +
              "}"
            )
        );
  }

  @Test
  public void nestedProperties() {
    assertThat(b.rule(CssGrammar.ruleset))
        .matches(
            code(
            ".funky {" +
              "  font: {" +
              "  family: fantasy;" +
              "    size: 30em;" +
              "    weight: bold;" +
              "  }" +
              "}"
            )
        )
        .matches(
            code(
            ".funky {" +
              "  font: 2px/3px {" +
              "  family: fantasy;" +
              "    size: 30em;" +
              "    weight: bold;" +
              "  }" +
              "}"
            )
        );
  }

  @Test
  public void nestedImport() {
    assertThat(b.rule(CssGrammar.ruleset))
        .matches(
            code(
            "#main {" +
              "  @import \"example\";" +
              "}"
            )
        ).matches(
            code(
            ".sidebar {" +
              "  width: 300px;" +
              "  @media screen and (orientation: landscape) {" +
              "    width: 500px;" +
              "  }" +
              "}"
            )
        );
  }
}
