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
package org.sonar.css.model.property.standard;

import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;

public class Display extends StandardProperty {

  public Display() {
    addLinks(
      "https://www.w3.org/TR/CSS22/visuren.html#propdef-display",
      "https://drafts.csswg.org/css-display/#propdef-display",
      "https://drafts.csswg.org/css-flexbox-1/#flex-containers",
      "https://drafts.csswg.org/css-grid-1/#grid-containers",
      "https://drafts.csswg.org/css-lists-3/#ref-for-valdef-display-inline-list-item-2",
      "https://drafts.csswg.org/css-ruby-1/#propdef-display");
    addValidators(new IdentifierValidator(
      "inline", "block", "list-item", "inline-block", "table",
      "inline-table", "table-row-group", "table-header-group", "table-footer-group", "table-row",
      "table-column-group", "table-column", "table-cell", "table-caption", "none", "flex", "inline-flex", "grid",
      "inline-grid", "run-in", "contents", "ruby", "ruby-base", "ruby-text", "ruby-base-container",
      "ruby-text-container", "compact", "marker"));
  }

}
