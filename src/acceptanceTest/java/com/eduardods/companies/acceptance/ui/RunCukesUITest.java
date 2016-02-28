package com.eduardods.companies.acceptance.ui;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
  plugin = {"pretty", "html:build/reports/acceptanceTest/ui"},
  strict = true,
  features = "src/acceptanceTest/resources/features/ui",
  glue = {"com.eduardods.companies.acceptance.ui.steps","com.eduardods.companies.acceptance.common.steps"}
)
public class RunCukesUITest {
}