package com.eduardods.companies.acceptance.rest.steps;

import static com.eduardods.companies.acceptance.support.HamcrestUtil.hasEntries;
import static com.eduardods.companies.acceptance.support.ScenarioContext.getResponseFromContext;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.util.List;
import java.util.Map;

import cucumber.api.java.en.Then;

public class GetCompanySteps {

  @Then("the response body contains the expected company")
  public void checkBody(Map<String, String> expectedCompany) {
    getResponseFromContext().then()
      .body("$", hasEntries(expectedCompany));
  }

  @Then("the company has (?:these|this) (?:owner|owners)")
  public void checkOwners(List<String> expectedOwners) {
    getResponseFromContext().then()
      .body("owners", hasItems(expectedOwners.toArray()));
  }

  @Then("content type is '(.+)'")
  public void checkContentType(String contentType) {
    getResponseFromContext().then()
      .header(CONTENT_TYPE, startsWith(contentType));
  }
}
