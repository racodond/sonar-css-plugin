package org.sonar.css.parser;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

public class CssStylesheetTest extends TestBase {

  private LexerlessGrammar b = CssGrammarImpl.createGrammar();

  @Test
  public void css() {
    assertThat(b.getRootRule())
        .matches(code("p {color:red;text-align:center;}"))
        .matches(code(
            "@import \"subs.css\";",
            "p {color:red;text-align:center;}"))
        .matches(code(
            "@import \"subs.css\";",
            "@import \"print-main.css\" print;",
            "@media print {",
            "body { font-size: 10pt }",
            "}",
            "h1 {color: blue }"));
  }
}
