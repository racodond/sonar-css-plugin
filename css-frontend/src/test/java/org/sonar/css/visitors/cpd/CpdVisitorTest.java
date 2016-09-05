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
package org.sonar.css.visitors.cpd;

import com.google.common.base.Charsets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.css.parser.css.CssParserBuilder;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.duplications.internal.pmd.TokensLine;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.TreeVisitorContext;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CpdVisitorTest {

  private CpdVisitor cpdVisitor;
  private TreeVisitorContext visitorContext;
  private SensorContextTester sensorContext;
  private DefaultInputFile inputFile;
  private File file;

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Before
  public void setUp() throws IOException {
    DefaultFileSystem fileSystem = new DefaultFileSystem(tempFolder.getRoot());
    fileSystem.setEncoding(Charsets.UTF_8);
    file = tempFolder.newFile();
    inputFile = new DefaultInputFile("moduleKey", file.getName())
      .setLanguage("css")
      .setType(InputFile.Type.MAIN);
    fileSystem.add(inputFile);

    sensorContext = SensorContextTester.create(tempFolder.getRoot());
    sensorContext.setFileSystem(fileSystem);

    visitorContext = mock(TreeVisitorContext.class);
    cpdVisitor = new CpdVisitor(sensorContext);
    when(visitorContext.getFile()).thenReturn(file);
  }

  @Test
  public void test() throws Exception {
    String toParse = ".mybox {\n  color: green;\n  background-color: blue;\n}";

    inputFile.initMetadata(toParse);
    Tree tree = CssParserBuilder.createParser(Charsets.UTF_8).parse(toParse);

    when(visitorContext.getTopTree()).thenReturn((TreeImpl) tree);

    com.google.common.io.Files.write(toParse, file, Charsets.UTF_8);
    cpdVisitor.scanTree(visitorContext);

    List<TokensLine> cpdTokenLines = sensorContext.cpdTokens("moduleKey:" + inputFile.getFile().getName());
    assertThat(cpdTokenLines).hasSize(4);

    TokensLine tokensline;

    tokensline = cpdTokenLines.get(0);
    assertThat(tokensline.getValue()).isEqualTo(".mybox{");
    assertThat(tokensline.getStartLine()).isEqualTo(1);
    assertThat(tokensline.getStartUnit()).isEqualTo(1);
    assertThat(tokensline.getEndLine()).isEqualTo(1);
    assertThat(tokensline.getEndUnit()).isEqualTo(3);

    tokensline = cpdTokenLines.get(1);
    assertThat(tokensline.getValue()).isEqualTo("color:green;");
    assertThat(tokensline.getStartLine()).isEqualTo(2);
    assertThat(tokensline.getStartUnit()).isEqualTo(4);
    assertThat(tokensline.getEndLine()).isEqualTo(2);
    assertThat(tokensline.getEndUnit()).isEqualTo(7);

    tokensline = cpdTokenLines.get(2);
    assertThat(tokensline.getValue()).isEqualTo("background-color:blue;");
    assertThat(tokensline.getStartLine()).isEqualTo(3);
    assertThat(tokensline.getStartUnit()).isEqualTo(8);
    assertThat(tokensline.getEndLine()).isEqualTo(3);
    assertThat(tokensline.getEndUnit()).isEqualTo(11);

    tokensline = cpdTokenLines.get(3);
    assertThat(tokensline.getValue()).isEqualTo("}");
    assertThat(tokensline.getStartLine()).isEqualTo(4);
    assertThat(tokensline.getStartUnit()).isEqualTo(12);
    assertThat(tokensline.getEndLine()).isEqualTo(4);
    assertThat(tokensline.getEndUnit()).isEqualTo(12);
  }

}
