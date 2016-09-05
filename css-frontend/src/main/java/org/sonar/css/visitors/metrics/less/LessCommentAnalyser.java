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
package org.sonar.css.visitors.metrics.less;

import org.sonar.css.visitors.metrics.css.CssCommentAnalyser;

public class LessCommentAnalyser extends CssCommentAnalyser {

  @Override
  public String getContents(String comment) {
    if (comment.startsWith("/*")) {
      return comment.substring(2, comment.length() - 2);
    } else if (comment.startsWith("<!--")) {
      return comment.substring(4, comment.length() - 3);
    } else if (comment.startsWith("//")) {
      return comment.substring(2, comment.length());
    } else {
      throw new IllegalArgumentException();
    }
  }

}
