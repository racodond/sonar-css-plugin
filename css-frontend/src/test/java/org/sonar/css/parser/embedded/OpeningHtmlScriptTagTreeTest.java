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
package org.sonar.css.parser.embedded;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;

public class OpeningHtmlScriptTagTreeTest extends EmbeddedCssTreeTest {

  public OpeningHtmlScriptTagTreeTest() {
    super(LexicalGrammar.OPENING_HTML_SCRIPT_TAG);
  }

  @Test
  public void openingHtmlScriptTag() {
    checkParsed("<script type=\"text/css\">");
    checkParsed("<script type = \"text/css\" >");
  }

  @Test
  public void notOpeningHtmlScriptTag() {
    checkNotParsed("<script>");
    checkNotParsed("< script>");
    checkNotParsed("< script type=\"text/css\">");
    checkNotParsed("<script type=\"text/javascript\">");
    checkNotParsed("</script>");
  }

  private void checkParsed(String toParse) {
    parser().parse(toParse);
  }

}
