/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
package org.sonar.css.parser;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.css.InternalSyntaxToken;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;

import java.util.Arrays;
import java.util.Collections;

import static org.fest.assertions.Assertions.assertThat;

public class SeparatedListTest {

  @Test
  public void validSeparatorList() {
    String[] elements = {"a", "b", "c"};
    InternalSyntaxToken[] separators = {
      new InternalSyntaxToken(1, 1, ",", Collections.EMPTY_LIST, false, false),
      new InternalSyntaxToken(2, 1, ",", Collections.EMPTY_LIST, false, false)
    };
    SeparatedList<String, SyntaxToken> separatedList = new SeparatedList<>(Arrays.asList(elements), Arrays.asList(separators));

    assertThat(separatedList).isNotEmpty();
    assertThat(separatedList.get(0)).isEqualTo("a");
    assertThat(separatedList.get(1)).isEqualTo("b");
    assertThat(separatedList.get(2)).isEqualTo("c");

    assertThat(separatedList).contains("a");
    assertThat(separatedList).containsOnly("a", "b", "c");
    assertThat(separatedList.containsAll(ImmutableList.of("a", "b", "c"))).isTrue();

    assertThat(separatedList.separators()).hasSize(2);
    assertThat(separatedList.separators().get(0).text()).isEqualTo(",");
    assertThat(separatedList.separators().get(1).text()).isEqualTo(",");
  }

  @Test
  public void emptySeparatedList() {
    SeparatedList<String, SyntaxToken> separatedList = new SeparatedList<>(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
    assertThat(separatedList).isEmpty();
    assertThat(separatedList.separators()).isEmpty();
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidSeparatedList() {
    String[] elements = {"a", "b", "c"};
    InternalSyntaxToken[] separators = {
      new InternalSyntaxToken(1, 1, ",", Collections.EMPTY_LIST, false, false)
    };
    new SeparatedList<>(Arrays.asList(elements), Arrays.asList(separators));
  }

}
