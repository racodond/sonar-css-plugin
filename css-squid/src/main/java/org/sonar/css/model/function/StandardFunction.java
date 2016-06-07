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
package org.sonar.css.model.function;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class StandardFunction {

  private String name;
  private boolean obsolete;
  private boolean experimental;
  private boolean ieStaticFilter;
  private final List<String> links;

  public StandardFunction() {
    name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this.getClass().getSimpleName());
    obsolete = false;
    experimental = false;
    ieStaticFilter = false;
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

  public void setIeStaticFilter(boolean ieStaticFilter) {
    this.ieStaticFilter = ieStaticFilter;
  }

  public void addLinks(String... allLinks) {
    links.addAll(Lists.newArrayList(allLinks));
  }

  @Nonnull
  public String getName() {
    return name;
  }

  public boolean isObsolete() {
    return obsolete;
  }

  public boolean isIeStaticFilter() {
    return ieStaticFilter;
  }

  public boolean isExperimental() {
    return experimental;
  }

  @Nonnull
  public List<String> getLinks() {
    return links;
  }

}
