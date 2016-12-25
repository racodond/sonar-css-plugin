/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.its;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.locator.FileLocation;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.sonar.wsclient.internal.HttpRequestFactory;
import org.sonar.wsclient.jsonsimple.JSONValue;

public class ProfileGenerator {

  private static Multimap<String, Parameter> parameters = ImmutableListMultimap.<String, Parameter>builder()
    .build();

  public static void generateProfile(Orchestrator orchestrator, String language) {
    try {
      StringBuilder sb = new StringBuilder()
        .append("<profile>")
        .append("<name>rules</name>")
        .append("<language>").append(language).append("</language>")
        .append("<rules>");

      Set<String> ruleKeys = getRuleKeysFromRepository(orchestrator, language);

      for (String key : ruleKeys) {
        sb.append("<rule>")
          .append("<repositoryKey>").append(language).append("</repositoryKey>")
          .append("<key>").append(key).append("</key>")
          .append("<priority>INFO</priority>");

        Collection<Parameter> parameters = ProfileGenerator.parameters.get(key);
        if (!parameters.isEmpty()) {
          sb.append("<parameters>");
          for (Parameter parameter : parameters) {
            sb.append("<parameter>")
              .append("<key>").append(parameter.parameterKey).append("</key>")
              .append("<value>").append(parameter.parameterValue).append("</value>")
              .append("</parameter>");
          }
          sb.append("</parameters>");
        }

        sb.append("</rule>");
      }
      sb.append("</rules>")
        .append("</profile>");

      File file = File.createTempFile("profile", ".xml");
      Files.write(sb, file, Charsets.UTF_8);
      orchestrator.getServer().restoreProfile(FileLocation.of(file));
      file.delete();
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  private static Set<String> getRuleKeysFromRepository(Orchestrator orchestrator, String language) {
    Set<String> ruleKeys = new HashSet<>();
    String json = new HttpRequestFactory(orchestrator.getServer().getUrl())
      .get("/api/rules/search", ImmutableMap.<String, Object>of("languages", language, "repositories", language, "ps", "1000"));
    @SuppressWarnings("unchecked")
    List<Map> jsonRules = (List<Map>) ((Map) JSONValue.parse(json)).get("rules");
    for (Map jsonRule : jsonRules) {
      String key = ((String) jsonRule.get("key")).substring(language.length() + 1);
      ruleKeys.add(key);
    }
    return ruleKeys;
  }

  private static class Parameter {
    String parameterKey;
    String parameterValue;

    public Parameter(String parameterKey, String parameterValue) {
      this.parameterKey = parameterKey;
      this.parameterValue = parameterValue;
    }
  }

}
