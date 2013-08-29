/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
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
package org.sonar.css.parser;


import org.sonar.css.CssConfiguration;

import com.google.common.annotations.VisibleForTesting;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.events.ParsingEventListener;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;

import java.nio.charset.Charset;

public final class CssParser {
  private CssParser() {
  }

  @VisibleForTesting
  public static Parser<LexerlessGrammar> create(ParsingEventListener... parsingEventListeners) {
    return create(new CssConfiguration(Charset.forName("UTF-8")), parsingEventListeners);
  }

  public static Parser<LexerlessGrammar> create(CssConfiguration conf, ParsingEventListener... parsingEventListeners) {
    return new ParserAdapter<LexerlessGrammar>(conf.getCharset(), CssGrammarImpl.createGrammar());
  }

}
