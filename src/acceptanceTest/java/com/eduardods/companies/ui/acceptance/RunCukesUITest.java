package com.eduardods.companies.ui.acceptance;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
  plugin = {"pretty", "html:build/reports/acceptanceTest/ui"},
  strict = true,
  features = "src/acceptanceTest/resources/features/ui",
  glue = "com.eduardods.companies.ui.acceptance.steps"
)
public class RunCukesUITest {
}