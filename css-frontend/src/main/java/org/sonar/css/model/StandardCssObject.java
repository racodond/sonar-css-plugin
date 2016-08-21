/*
 * SonarQube CSS Plugin
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

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class StandardCssObject {

  private String name;
  private boolean obsolete;
  private boolean experimental;

  /*
   * See:
   * - Mozilla: https://developer.mozilla.org/en-US/docs/Web/CSS/Reference
   * - Mozilla: https://developer.mozilla.org/en-US/docs/Web/CSS/Mozilla_Extensions
   * - Microsoft: https://developer.microsoft.com/en-us/microsoft-edge/platform/documentation/apireference/cssstyles/
   * - Webkit: https://developer.mozilla.org/en-US/docs/Web/CSS/Webkit_Extensions
   */
  private final Set<Vendor> vendors;

  private final List<String> links;

  public StandardCssObject() {
    name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this.getClass().getSimpleName());
    obsolete = false;
    experimental = false;
    vendors = new HashSet<>();
    links = new ArrayList<>();
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setObsolete(boolean obsolete) {
    this.obsolete = obsolete;
  }

  public void setExperimental(boolean experimental) {
    this.experimental = experimental;
  }

  public void addVendors(Vendor... allVendors) {
    vendors.addAll(Lists.newArrayList(allVendors));
  }

  public void addLinks(String... allLinks) {
    links.addAll(Lists.newArrayList(allLinks));
  }

  public String getName() {
    return name;
  }

  public boolean isObsolete() {
    return obsolete;
  }

  public boolean isExperimental() {
    return experimental;
  }

  public boolean hasVendors() {
    return !vendors.isEmpty();
  }

  public Set<Vendor> getVendors() {
    return vendors;
  }

  public List<String> getLinks() {
    return links;
  }

}
