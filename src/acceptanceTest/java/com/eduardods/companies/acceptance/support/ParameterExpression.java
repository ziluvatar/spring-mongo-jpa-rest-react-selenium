package com.eduardods.companies.acceptance.support;

import static com.eduardods.companies.acceptance.support.ScenarioContext.getParameterFromContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterExpression {

  //${paramName}whatever
  private static final Pattern PARAMETER_PATTERN = Pattern.compile("\\$\\{(\\w+)\\}(.*)");

  private String value;
  private String name;

  public ParameterExpression(String value) {
    Matcher matcher = PARAMETER_PATTERN.matcher(value);
    if (matcher.find()) {
      this.name = matcher.group(1);
      this.value = getParameterFromContext(name) + matcher.group(2);
    } else {
      this.value = value;
    }
  }

  public String getValue() {
    return value;
  }

  public String getName() {
    return name;
  }
}
