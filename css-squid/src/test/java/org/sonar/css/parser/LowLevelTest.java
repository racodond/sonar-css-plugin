package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class LowLevelTest {

  private LexerlessGrammar b = CssGrammarImpl.createGrammar();

  @Test
  public void strings() {
    assertThat(b.rule(CssGrammarImpl.string))
        .matches("\"\"")
        .matches("\"subs.css\"")
        .matches("'asdawddawd'")
        .matches("''")
        ;
  }

  @Test
  public void idents() {
    assertThat(b.rule(CssGrammarImpl.ident))
        .matches("p")
        .matches("b\\&W\\?")
        .matches("B\\&W\\?");
  }

  @Test
  public void declaration() {
    assertThat(b.rule(CssGrammarImpl.declaration))
        .matches("color: blue")
        .notMatches("color: blue;");
  }

  @Test
  public void selector() {
    assertThat(b.rule(CssGrammarImpl.selector))
        .matches("h6")
        .matches("h1, h2")
        .matches("h3, h4 & h5")
        .notMatches("h3{")
        ;
  }

}
