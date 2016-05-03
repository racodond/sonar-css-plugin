/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.model;

/**
 * See:
 * https://www.w3.org/TR/CSS22/syndata.html#vendor-keyword-history
 * http://stackoverflow.com/questions/5411026/list-of-css-vendor-prefixes
 */
public enum Vendor {

  ANTENNA_HOUSE("-ah-"),
  APPLE("-apple-"),
  ADVANCED_TELEVISION_STANDARDS_COMMITTEE("-atsc-"),
  EPUB("-epub-"),
  JAVA_FX("-fx-"),
  HP("-hp-"),
  KDE("-khtml-"),
  MOZILLA("-moz-"),
  MICROSOFT("-ms-"),
  MICROSOFT_OFFICE("mso-"),
  OPERA("-o-"),
  YES_LOGIC("prince-"),
  RESEARCH_IN_MOTION("-rim-"),
  REAL_OBJECTS("-ro-"),
  TALL_COMPONENTS("-tc-"),
  WAP("-wap-"),
  WEBKIT("-webkit-"),
  OPERA2("-xv-");

  private String prefix;

  Vendor(String prefix) {
    this.prefix = prefix;
  }

  public String getPrefix() {
    return prefix;
  }

}
