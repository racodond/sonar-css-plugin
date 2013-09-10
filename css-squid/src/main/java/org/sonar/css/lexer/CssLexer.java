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
package org.sonar.css.lexer;

import org.sonar.css.parser.CssGrammar;

import com.sonar.sslr.api.GenericTokenType;

import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import com.sonar.sslr.impl.channel.UnknownCharacterChannel;
import org.sonar.css.api.CssPunctuator;

import java.nio.charset.Charset;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;

public final class CssLexer {

  public static final String COMMENT = "(?:/\\*[\\s\\S]*?\\*/)";
  public static final String COMMENT2 = "(?:\\<\\!--[\\s\\S]*?--\\>)";

  private CssLexer() {
  }

  /**
   * TODO It is ugly, why we cannot use the CssGrammar rules directly?
   * @param charset
   * @return
   */
  public static Lexer create(Charset charset) {
    return Lexer
        .builder()
        .withCharset(charset)
        .withChannel(commentRegexp(COMMENT))
        .withChannel(commentRegexp(COMMENT2))
        .withChannel(regexp(GenericTokenType.IDENTIFIER, CssGrammar.IDENTIFIER))
        .withChannel(regexp(GenericTokenType.LITERAL, CssGrammar.LITERAL))
        .withChannel(new PunctuatorChannel(CssPunctuator.values()))
        .withChannel(new BlackHoleChannel("[ \\t\\r\\n\\f]+"))
        .withChannel(new UnknownCharacterChannel(true))
        .withFailIfNoChannelToConsumeOneCharacter(false)
        .build();
  }

}
