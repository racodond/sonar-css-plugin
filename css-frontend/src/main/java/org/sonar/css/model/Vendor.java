/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.model;

/**
 * See:
 * https://www.w3.org/TR/CSS22/syndata.html#vendor-keyword-history
 * http://stackoverflow.com/questions/5411026/list-of-css-vendor-prefixes
 */
public enum Vendor {

  ANTENNA_HOUSE("-ah-", "Antenna House"),
  APPLE("-apple-", "Webkit supports properties using the -apple- prefixes as well"),
  ADVANCED_TELEVISION_STANDARDS_COMMITTEE("-atsc-", "Advanced Television Standards Committee"),
  EPUB("-epub-", "EPUB"),
  JAVA_FX("-fx-", "JavaFX"),
  HP("-hp-", "Hewlett Packard"),
  KDE("-khtml-", "Konqueror browser"),
  MOZILLA("-moz-", "Mozilla Foundation (Gecko-based browsers)"),
  MICROSOFT("-ms-", "Microsoft"),
  MICROSOFT_OFFICE("mso-", "Microsoft Office"),
  OPERA("-o-", "Opera Software"),
  YES_LOGIC("prince-", "YesLogic"),
  RESEARCH_IN_MOTION("-rim-", "Research In Motion"),
  REAL_OBJECTS("-ro-", "Real Objects"),
  TALL_COMPONENTS("-tc-", "Tall Components"),
  WAP("-wap-", "The WAP Forum"),
  WEBKIT("-webkit-", "Safari, Chrome (and other WebKit-based browsers)"),
  OPERA2("-xv-", "Opera Software");

  private final String prefix;
  private final String description;

  Vendor(String prefix, String description) {
    this.prefix = prefix;
    this.description = description;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getDescription() {
    return description;
  }
}
