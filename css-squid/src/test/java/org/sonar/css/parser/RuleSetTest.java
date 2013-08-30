package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class RuleSetTest extends TestBase {

  private LexerlessGrammar b = CssGrammarImpl.createGrammar();

  @Test
  public void atRuleTest() {
    assertThat(b.rule(CssGrammarImpl.ruleset))
        .matches("p{color:red;}")
        .matches(code(
            "h1 {",
            "font-weight: bold;",
            "font-size: 12px;",
            "font-family: Helvetica;",
            "font-variant: normal;",
            "}"));
  }

}
