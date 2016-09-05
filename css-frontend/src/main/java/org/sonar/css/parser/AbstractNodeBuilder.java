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
package org.sonar.css.parser;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.*;
import com.sonar.sslr.api.typed.Input;
import com.sonar.sslr.api.typed.NodeBuilder;

import java.util.List;

import org.sonar.css.tree.impl.css.InternalSyntaxSpacing;
import org.sonar.css.tree.impl.css.InternalSyntaxToken;
import org.sonar.css.tree.impl.css.InternalSyntaxTrivia;
import org.sonar.plugins.css.api.tree.css.SyntaxTrivia;
import org.sonar.sslr.grammar.GrammarRuleKey;

public abstract class AbstractNodeBuilder implements NodeBuilder {

  private static final char BYTE_ORDER_MARK = '\uFEFF';

  @Override
  public Object createNonTerminal(GrammarRuleKey ruleKey, Rule rule, List<Object> children, int startIndex, int endIndex) {
    for (Object child : children) {
      if (child instanceof InternalSyntaxToken) {
        return child;
      }
    }
    return new InternalSyntaxSpacing(startIndex, endIndex);
  }

  @Override
  public Object createTerminal(Input input, int startIndex, int endIndex, List<Trivia> trivias, TokenType type) {
    char[] fileChars = input.input();
    boolean hasByteOrderMark = fileChars.length > 0 && fileChars[0] == BYTE_ORDER_MARK;
    boolean isEof = GenericTokenType.EOF.equals(type);
    LineColumnValue lineColumnValue = tokenPosition(input, startIndex, endIndex);
    return new InternalSyntaxToken(
      lineColumnValue.line,
      column(hasByteOrderMark, lineColumnValue.line, lineColumnValue.column),
      lineColumnValue.value,
      createTrivias(trivias, hasByteOrderMark),
      isEof,
      isByteOrderMark(input, startIndex, endIndex));
  }

  private static List<SyntaxTrivia> createTrivias(List<Trivia> trivias, boolean hasByteOrderMark) {
    List<SyntaxTrivia> result = Lists.newArrayList();
    for (Trivia trivia : trivias) {
      Token trivialToken = trivia.getToken();
      int column = column(hasByteOrderMark, trivialToken.getLine(), trivialToken.getColumn());
      result.add(InternalSyntaxTrivia.create(trivialToken.getValue(), trivialToken.getLine(), column));
    }
    return result;
  }

  private static int column(boolean hasByteOrderMark, int line, int column) {
    if (hasByteOrderMark && line == 1) {
      return column - 1;
    }
    return column;
  }

  private static LineColumnValue tokenPosition(Input input, int startIndex, int endIndex) {
    int[] lineAndColumn = input.lineAndColumnAt(startIndex);
    String value = input.substring(startIndex, endIndex);
    return new LineColumnValue(lineAndColumn[0], lineAndColumn[1] - 1, value);
  }

  private static boolean isByteOrderMark(Input input, int startIndex, int endIndex) {
    return (Character.toString(BYTE_ORDER_MARK)).equals(input.substring(startIndex, endIndex));
  }

  private static class LineColumnValue {
    final int line;
    final int column;
    final String value;

    private LineColumnValue(int line, int column, String value) {
      this.line = line;
      this.column = column;
      this.value = value;
    }
  }

}
