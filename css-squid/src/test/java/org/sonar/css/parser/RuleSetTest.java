package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class RuleSetTest {

  private LexerlessGrammar b = CssGrammarImpl.createGrammar();

  @Test
  public void atRuleTest() {
    assertThat(b.rule(CssGrammarImpl.ruleset))
        .matches("p{color:red;}");
  }

}
