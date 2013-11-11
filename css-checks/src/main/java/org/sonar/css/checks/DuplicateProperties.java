/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/stubbornella/csslint/wiki/Disallow-duplicate-properties
 * @author tkende
 *
 */
@Rule(key = "duplicate-properties", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class DuplicateProperties extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset, CssGrammar.atRule);
  }

  //TODO refactor this to not use getDescendants
  @Override
  public void visitNode(AstNode astNode) {
    List<AstNode> declarations = astNode.getDescendants(CssGrammar.declaration);
    findDuplicates(declarations);
  }

  //TODO refactor this
  private void findDuplicates(List<AstNode> declarations) {
    List<Declarations> decs = new ArrayList<Declarations>();
    for (AstNode astNode : declarations) {
      String property = astNode.getFirstChild(CssGrammar.property).getToken().getValue().trim();
      String value = StringUtils.join(astNode.getFirstChild(CssGrammar.value).getTokens(), ' ');
      Declarations previouslyAdded = getWithProperty(decs, property);
      if (previouslyAdded!=null){
        if(previouslyAdded.getValue().equals(value.trim())){
          getContext().createLineViolation(this, "Duplicated property in the declarations", astNode);
        } else {
          decs.add(new Declarations(property, value, astNode.getTokenLine()));
          if(previouslyAdded.getLine()<astNode.getTokenLine()-1){
            getContext().createLineViolation(this, "Duplicated property in the declarations", astNode);
          }
        }
      } else {
        decs.add(new Declarations(property, value, astNode.getTokenLine()));
      }
    }
  }

  private Declarations getWithProperty(List<Declarations> decs, String property) {
    for (Declarations declaration : decs) {
     if(declaration.getProperty().equals(property)){
       return declaration;
     }
    }
    return null;
  }

  private class Declarations {
    String property;
    String value;
    int line;

    public Declarations(String property, String value, int line){
      this.property = property.trim();
      this.value = value.trim();
      this.line = line;
    }

    public String getProperty() {
      return property;
    }

    public String getValue() {
      return value;
    }

    public int getLine() {
      return line;
    }


  }


}
