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
package org.sonar.css.checks;

import com.google.common.io.Files;
import com.sonar.sslr.api.AstNode;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CharsetAwareVisitor;
import org.sonar.css.CssCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(
  key = "S1131",
  name = "Lines should not end with trailing whitespaces",
  priority = Priority.MINOR,
  tags = {Tags.FORMAT})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("1min")
public class TrailingWhitespaceCheck extends CssCheck implements CharsetAwareVisitor {

  private static final String WHITESPACE = "\\t\\u000B\\f\\u0020\\u00A0\\uFEFF\\p{Zs}";
  private Charset charset;

  @Override
  public void setCharset(Charset charset) {
    this.charset = charset;
  }

  @Override
  public void visitFile(AstNode astNode) {
    List<String> lines;
    try {
      lines = Files.readLines(getContext().getFile(), charset);
    } catch (IOException e) {
      throw new IllegalStateException("Rule S1131 - cannot read file", e);
    }
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      if (line.length() > 0 && Pattern.matches("[" + WHITESPACE + "]", line.subSequence(line.length() - 1, line.length()))) {
        addLineIssue(this, "Remove the useless trailing whitespaces at the end of this line.", i + 1);
      }
    }
  }
}
