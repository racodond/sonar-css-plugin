package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class CommentTest extends TestBase {

  private LexerlessGrammar b = CssGrammarImpl.createGrammar();

  @Test
  public void css() {
    assertThat(b.rule(CssGrammarImpl.ruleset))
        .matches(code("p {color:red;text-align:center;} /* comment */"))
        ;
  }

}
