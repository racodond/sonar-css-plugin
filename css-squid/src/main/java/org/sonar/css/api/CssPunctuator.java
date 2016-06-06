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
package org.sonar.css.api;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;

public enum CssPunctuator implements TokenType {
  CDO("<!--"), CDC("-->"), INCLUDES("~="), DASHMATCH("|="), PREFIXMATCH("^="), SUFFIXMATCH("$="),
  SUBSTRINGMATCH("*="), LCURLYBRACE("{"), RCURLYBRACE("}"), LPARENTHESIS("("), RPARENTHESIS(")"),
  LBRACKET("["), RBRACKET("]"), DOT("."), SEMI(";"), COMMA(","), COLON(":"), EQUALS("=");

  private final String value;

  CssPunctuator(String word) {
    this.value = word;
  }

  @Override
  public String getName() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public boolean hasToBeSkippedFromAst(AstNode node) {
    return false;
  }

}
