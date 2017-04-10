/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
package org.sonar.css.parser.scss;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.typed.ActionParser;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.Tree;

import static org.junit.Assert.fail;

abstract class ScssTreeTest {

  private final ActionParser<Tree> parser;

  ScssTreeTest(LexicalGrammar ruleKey) {
    parser = ScssParser.createTestParser(Charsets.UTF_8, ruleKey);
  }

  public ActionParser<Tree> parser() {
    return parser;
  }

  public void checkNotParsed(String toParse) {
    try {
      parser.parse(toParse);
    } catch (Exception e) {
      return;
    }
    fail("Did not throw a RecognitionException as expected.");
  }

}
