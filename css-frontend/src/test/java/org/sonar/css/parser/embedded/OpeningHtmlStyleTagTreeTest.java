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

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;

public class OpeningHtmlStyleTagTreeTest extends EmbeddedCssTreeTest {

  public OpeningHtmlStyleTagTreeTest() {
    super(LexicalGrammar.OPENING_HTML_STYLE_TAG);
  }

  @Test
  public void openingHtmlStyleTag() {
    checkParsed("<style type=\"text/css\">");
    checkParsed("<style type = \"text/css\" >");
    checkParsed("<style media=\"screen\" type = \"text/css\" >");
  }

  @Test
  public void notOpeningHtmlStyleTag() {
    checkNotParsed("<style>");
    checkNotParsed("< style>");
    checkNotParsed("< style type=\"text/css\">");
    checkNotParsed("<style type=\"text/javastyle\">");
    checkNotParsed("</style>");
  }

  private void checkParsed(String toParse) {
    parser().parse(toParse);
  }

}
