package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class RuleSetTest extends TestBase {

  private LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void atRuleTest() {
    assertThat(b.rule(CssGrammar.ruleset))
      .matches("p{color:red;}")
      .matches(code(
        "h1 {",
        "font-weight: bold;",
        "font-size: 12px;",
        "font-family: Helvetica;",
        "font-variant: normal;",
        "}"))
      .matches(code(
        "div" +
          "{" +
          "transform:rotate(30deg);" +
          "}"
        ));
  }

}
