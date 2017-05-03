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
package org.sonar.css.parser.embedded;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;

public class ClosingHtmlStyleTagTreeTest extends EmbeddedCssTreeTest {

  public ClosingHtmlStyleTagTreeTest() {
    super(LexicalGrammar.CLOSING_HTML_STYLE_TAG);
  }

  @Test
  public void closingHtmlStyleTag() {
    checkParsed("</style>");
    checkParsed("</style >");
  }

  @Test
  public void notClosingHtmlStyleTag() {
    checkNotParsed("</blabla>");
    checkNotParsed("</style blabla>");
    checkNotParsed("</style ");
  }

  private void checkParsed(String toParse) {
    parser().parse(toParse);
  }

}
