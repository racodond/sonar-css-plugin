package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class CommentTest extends TestBase {

  private LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void css() {
    assertThat(b.rule(CssGrammar.ruleset))
        .matches(code("p {color:red;text-align:center;} /* comment */"))
        .matches(code("/* comment */p {color:red;text-align:center;} /* comment */"))
        ;
  }

}
