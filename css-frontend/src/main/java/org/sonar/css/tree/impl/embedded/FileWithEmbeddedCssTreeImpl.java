/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
 * mailto: david.racodon@gmail.com
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
package org.sonar.css.tree.impl.embedded;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.css.tree.impl.TreeListUtils;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.embedded.CssInStyleTagTree;
import org.sonar.plugins.css.api.tree.embedded.FileWithEmbeddedCssTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileWithEmbeddedCssTreeImpl extends TreeImpl implements FileWithEmbeddedCssTree {

  private final SyntaxToken eof;
  private final List<Tree> trees;
  private final List<CssInStyleTagTree> cssBetweenTagsList;

  public FileWithEmbeddedCssTreeImpl(@Nullable List<Tree> trees, SyntaxToken eof) {
    this.trees = trees;
    this.eof = eof;
    this.cssBetweenTagsList = TreeListUtils.allElementsOfType(trees, CssInStyleTagTree.class);
  }

  @Override
  public Kind getKind() {
    return Kind.FILE_WITH_EMBEDDED_CSS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      trees != null ? trees.iterator() : new ArrayList<Tree>().iterator(),
      Iterators.singletonIterator(eof));
  }

  @Override
  public List<CssInStyleTagTree> cssBetweenTagsList() {
    return cssBetweenTagsList;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitFileWithEmbeddedCss(this);
  }

}
