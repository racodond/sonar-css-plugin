package org.sonar.css.parser;


import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class AtRulesTest {

  private LexerlessGrammar b = CssGrammarImpl.createGrammar();


  @Test
  public void atRuleTest() {
    assertThat(b.rule(CssGrammarImpl.atRule))
        .matches("@import \"subs.css\";");
  }

}
