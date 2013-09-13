package org.sonar.css.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Don%27t-use-too-many-web-fonts
 * @author tkende
 *
 */
@Rule(key = "font-faces", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class TooManyWebFonts extends SquidCheck<LexerlessGrammar> {

  private static final int DEFAULT_THRESHOLD = 5;

  @RuleProperty(key = "fontFaceThreshold", defaultValue = ""
    + DEFAULT_THRESHOLD)
  private int fontFaceThreshold = DEFAULT_THRESHOLD;

  private int currentFontFace;

  @Override
  public void init() {
    subscribeTo(CssGrammar.atRule);
  }

  @Override
  public void visitFile(AstNode astNode) {
    currentFontFace = 0;
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.getFirstChild(CssGrammar.atkeyword)
        .getFirstChild(CssGrammar.ident).getTokenValue().equals("font-face")) {
      currentFontFace++;
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    if (currentFontFace > fontFaceThreshold) {
      getContext().createLineViolation(this, "Do not use too many web fonts, the number of font-faces is {0} greater than {1} authorized.", astNode, currentFontFace,
          fontFaceThreshold);
    }
  }

  public void setFontFaceThreshold(int fontFaceThreshold) {
    this.fontFaceThreshold = fontFaceThreshold;
  }

}
