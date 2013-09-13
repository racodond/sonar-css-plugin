package org.sonar.css.checks;

import com.sonar.sslr.api.AstNode;
import org.sonar.css.parser.CssGrammar;

import java.util.List;

import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

/**
 * https://github.com/stubbornella/csslint/wiki/Bulletproof-font-face
 * @author tkende
 *
 */
@Rule(key = "bulletproof-font-face", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class BulletproofFontFace extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(CssGrammar.atRule);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.getFirstChild(CssGrammar.atkeyword)
        .getFirstChild(CssGrammar.ident).getTokenValue().equals("font-face")) {
      List<AstNode> declarations = astNode.getChildren(CssGrammar.declaration);
      for (AstNode declaration : declarations) {
        if(declaration.getFirstChild(CssGrammar.property)
            .getTokenValue().equals("src")){
          String firstAnyFunciontValue = declaration.getFirstChild(CssGrammar.value).getFirstChild(CssGrammar.any)
              .getFirstChild(CssGrammar.string).getTokenValue();
          if(!firstAnyFunciontValue.matches(".*\\.eot\\?.*?['\"]$")){
            getContext().createLineViolation(this, "First web font has missing query string or it is not eot", astNode);
          }
        }
      }
    }
  }

}
