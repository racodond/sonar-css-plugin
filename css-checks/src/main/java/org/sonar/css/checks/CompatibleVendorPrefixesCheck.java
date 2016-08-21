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

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.model.Vendor;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.plugins.css.api.tree.AtRuleTree;
import org.sonar.plugins.css.api.tree.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.RulesetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "compatible-vendor-prefixes",
  name = "Missing vendor prefixes should be added to experimental properties",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("10min")
public class CompatibleVendorPrefixesCheck extends DoubleDispatchVisitorCheck {

  private final Map<String, Set<Vendor>> properties = new HashMap<>();

  @Override
  public void visitAtRule(AtRuleTree tree) {
    properties.clear();
    super.visitAtRule(tree);
  }

  @Override
  public void visitRuleset(RulesetTree tree) {
    properties.clear();

    super.visitRuleset(tree);

    List<String> missingVendorPrefixes;
    for (Map.Entry<String, Set<Vendor>> props : properties.entrySet()) {
      StandardProperty p = StandardPropertyFactory.getByName(props.getKey());
      missingVendorPrefixes = p.getVendors()
        .stream()
        .filter(vendor -> !props.getValue().contains(vendor))
        .map(Vendor::getPrefix)
        .sorted()
        .collect(Collectors.toList());

      if (!missingVendorPrefixes.isEmpty()) {
        addIssue(tree, p.getName(), missingVendorPrefixes.stream().sorted().collect(Collectors.toList()));
      }
    }
  }

  @Override
  public void visitPropertyDeclaration(PropertyDeclarationTree tree) {
    if (tree.property().isVendorPrefixed()) {
      String standardPropertyName = tree.property().standardProperty().getName();
      Vendor vendor = tree.property().vendor();
      if (properties.containsKey(standardPropertyName)) {
        properties.get(standardPropertyName).add(vendor);
      } else {
        properties.put(standardPropertyName, Sets.newHashSet(vendor));
      }
    }
    super.visitPropertyDeclaration(tree);
  }

  private void addIssue(RulesetTree ruleset, String propertyName, List<String> missingVendorPrefixes) {
    StringBuilder message = new StringBuilder("Define the missing vendor prefixes: ");
    for (String missingVendorPrefix : missingVendorPrefixes) {
      message.append(missingVendorPrefix).append(" ");
    }
    message.append("for the \"").append(propertyName).append("\" property.");

    addPreciseIssue(CheckUtils.rulesetIssueLocation(ruleset), message.toString());
  }

}
