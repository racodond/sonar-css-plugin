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
package org.sonar.css;

import com.google.common.annotations.VisibleForTesting;
import com.sonar.sslr.api.AstNode;

import java.nio.charset.Charset;
import java.util.Set;

import org.sonar.css.issue.Issue;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

public class CssCheck extends SquidCheck<LexerlessGrammar> implements CharsetAwareVisitor {

  private Charset charset;

  @Override
  public void setCharset(Charset charset) {
    this.charset = charset;
  }

  public Charset getCharset() {
    return charset;
  }

  public PreciseIssue addIssue(SquidCheck check, String message, AstNode astNode) {
    PreciseIssue issue = new PreciseIssue(check, getContext().getFile(), message, astNode, charset);
    addIssue(issue);
    return issue;
  }

  public PreciseIssue addFileIssue(SquidCheck check, String message) {
    PreciseIssue issue = new PreciseIssue(check, getContext().getFile(), message);
    addIssue(issue);
    return issue;
  }

  public PreciseIssue addLineIssue(SquidCheck check, String message, int line) {
    PreciseIssue issue = new PreciseIssue(check, getContext().getFile(), message, line);
    addIssue(issue);
    return issue;
  }

  @VisibleForTesting
  public Set<Issue> getIssues() {
    return ((CssSquidContext) getContext()).getIssues();
  }

  @VisibleForTesting
  public void addIssue(Issue issue) {
    ((CssSquidContext) getContext()).getIssues().add(issue);
  }

}
