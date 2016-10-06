/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.css.visitors;

import java.io.File;

import org.sonar.css.tree.symbol.SymbolModelImpl;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.symbol.SymbolModel;
import org.sonar.plugins.css.api.visitors.TreeVisitorContext;

public class CssTreeVisitorContext implements TreeVisitorContext {

  private final TreeImpl tree;
  private final File file;
  private final SymbolModel symbolModel;

  public CssTreeVisitorContext(TreeImpl tree, File file) {
    this.tree = tree;
    this.file = file;

    this.symbolModel = new SymbolModelImpl();
    SymbolModelImpl.build(this);
  }

  @Override
  public TreeImpl getTopTree() {
    return tree;
  }

  @Override
  public File getFile() {
    return file;
  }

  @Override
  public SymbolModel getSymbolModel() {
    return symbolModel;
  }

}
