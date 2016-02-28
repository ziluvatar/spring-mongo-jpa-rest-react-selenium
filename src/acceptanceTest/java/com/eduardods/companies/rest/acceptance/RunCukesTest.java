package com.eduardods.companies.rest.acceptance;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
  plugin = {"pretty", "html:build/reports/acceptanceTest/rest"},
  strict = true,
  features = "src/acceptanceTest/resources/features/rest",
  glue = "com.eduardods.companies.rest.acceptance.steps"
)
public class RunCukesTest {
}