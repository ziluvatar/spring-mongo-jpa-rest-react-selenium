package com.eduardods.companies.acceptance.rest.steps;

import static com.eduardods.companies.acceptance.support.HamcrestUtil.hasEntries;
import static com.eduardods.companies.acceptance.support.ScenarioContext.getResponseFromContext;
import static com.eduardods.companies.acceptance.support.ScenarioContext.saveResponseInContext;
import static com.eduardods.companies.acceptance.support.Server.getBaseUri;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.Map;

import com.eduardods.companies.acceptance.support.ParameterExpression;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ListCompanySteps {

  @When("I make a GET request to '(.*)'")
  public void requestList(ParameterExpression path) {
    saveResponseInContext(
      given()
        .get(getBaseUri() + path.getValue()));
  }

  @Then("the response list contains (\\d+) companies")
  public void checkCompaniesSize(Integer expectedSize) {
    getResponseFromContext().then().body("companies", hasSize(expectedSize));
  }

  @Then("the response list contains this expected company")
  public void checkBody(Map<String, String> expectedCompany) {
    getResponseFromContext().then().body(
      "companies", hasItem(hasEntries(expectedCompany))
    );
  }

  @Then("the company '(.*)' in the list has these owners")
  public void checkOwners(String companyName, List<String> expectedOwners) {
    getResponseFromContext().then().body(
      "companies.find { it.name == '" + companyName + "' }.owners", hasItems(expectedOwners.toArray()));
  }

}
