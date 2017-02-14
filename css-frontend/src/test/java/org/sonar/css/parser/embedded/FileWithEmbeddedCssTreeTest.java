/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.parser.embedded;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.embedded.FileWithEmbeddedCssTree;

import static org.fest.assertions.Assertions.assertThat;

public class FileWithEmbeddedCssTreeTest extends EmbeddedCssTreeTest {

  public FileWithEmbeddedCssTreeTest() {
    super(LexicalGrammar.FILE_WITH_EMBEDDED_CSS);
  }

  @Test
  public void fileWithEmbeddedCss() throws Exception {
    FileWithEmbeddedCssTree tree;

    tree = checkParsed(new File("src/test/resources/embedded/empty.html"));
    assertThat(tree.cssBetweenTagsList()).isEmpty();

    tree = checkParsed(new File("src/test/resources/embedded/noEmbeddedCss.html"));
    assertThat(tree.cssBetweenTagsList()).isEmpty();

    tree = checkParsed(new File("src/test/resources/embedded/embeddedCss.html"));
    assertThat(tree.cssBetweenTagsList()).hasSize(2);
  }

  private FileWithEmbeddedCssTree checkParsed(File file) throws Exception {
    FileWithEmbeddedCssTree tree = (FileWithEmbeddedCssTree) parser().parse(Files.toString(file, Charsets.UTF_8));
    assertThat(tree.cssBetweenTagsList()).isNotNull();
    return tree;
  }

}
