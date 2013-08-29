package org.sonar.css.parser;

import com.google.common.base.Joiner;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class CssStylesheetTest {

  private LexerlessGrammar b = CssGrammarImpl.createGrammar();

  @Test
  public void css() {
    assertThat(b.getRootRule())
        .matches(code("p {color:red;text-align:center;}"))
        .matches(code("@import \"subs.css\";","p {color:red;text-align:center;}"));
  }


  private static String code(String... lines) {
    return Joiner.on("\n").join(lines);
  }
}
