package org.sonar.css.checks;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/stubbornella/csslint/wiki/Don%27t-use-too-many-web-fonts
 * @author tkende
 *
 */
@Rule(key = "font-faces", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class ShordhandProperties extends SquidCheck<LexerlessGrammar> {

  private static List<String> margin = ImmutableList.<String> of("margin-left", "margin-right", "margin-top", "margin-bottom");
  private static List<String> padding = ImmutableList.<String> of("padding-left", "padding-right", "padding-top", "padding-bottom");

  List<String> properties = new ArrayList<String>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset, CssGrammar.declaration);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.getType().equals(CssGrammar.ruleset)) {
      properties.clear();
    } else if (astNode.getType().equals(CssGrammar.declaration)) {
      String property = astNode.getFirstChild(CssGrammar.property).getTokenValue();
      if (margin.contains(property) || padding.contains(property)) {
        properties.add(property);
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.getType().equals(CssGrammar.ruleset)) {
      if (properties.containsAll(margin)) {
        getContext().createLineViolation(this, "Margin shorthand can be applied", astNode);
      }
      if (properties.containsAll(padding)) {
        getContext().createLineViolation(this, "Padding shorthand can be applied", astNode);
      }
    }
  }

}
