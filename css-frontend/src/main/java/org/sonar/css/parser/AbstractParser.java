/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.parser;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.ActionParser;
import org.sonar.css.parser.css.CssGrammar;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Objects;

public abstract class AbstractParser extends ActionParser<Tree> {

  public AbstractParser(Charset charset, LexerlessGrammarBuilder grammarBuilder, Class<? extends CssGrammar> grammarClass,
                        TreeFactory treeFactory, AbstractNodeBuilder nodeBuilder, GrammarRuleKey rootRule) {
    super(charset, grammarBuilder, grammarClass, treeFactory, nodeBuilder, rootRule);
  }

  @Override
  public Tree parse(File file) {
    return createParentLink(super.parse(file));
  }

  private static Tree createParentLink(Tree parent) {
    if (!parent.isLeaf()) {
      Lists.newArrayList(parent.childrenIterator())
        .stream()
        .filter(Objects::nonNull)
        .forEach(t -> {
          t.setParent(parent);
          createParentLink(t);
        });
    }
    return parent;
  }

}
