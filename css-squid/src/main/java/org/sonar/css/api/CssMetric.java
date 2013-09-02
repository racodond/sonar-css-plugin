package org.sonar.css.api;

import org.sonar.squid.measures.CalculatedMetricFormula;

import org.sonar.squid.measures.MetricDef;

public enum CssMetric implements MetricDef {

  FILES,
  LINES,
  LINES_OF_CODE,
  COMMENT_LINES,
  COMMENT_BLANK_LINES,
  STATEMENTS,
  AT_RULES,
  RULE_SETS;

  public String getName() {
    return name();
  }

  public boolean isCalculatedMetric() {
    return false;
  }

  public boolean aggregateIfThereIsAlreadyAValue() {
    return true;
  }

  public boolean isThereAggregationFormula() {
    return false;
  }

  public CalculatedMetricFormula getCalculatedMetricFormula() {
    return null;
  }

}
