package com.eduardods.companies.rest.acceptance.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.eduardods.companies.rest.acceptance.support.DBClient;
import com.eduardods.companies.rest.acceptance.support.ScenarioContext;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CommonSteps{

  @After
  public void clean() {
    ScenarioContext.clear();
    DBClient.getInstance().clearDatabase();
  }

  @Given("No company exists")
  public void databaseIsEmpty() {
    DBClient.getInstance().clearDatabase();
  }

  @Then("response status is (\\d+)")
  public void checkStatus(Integer status) {
    assertThat(ScenarioContext.getResponseFromContext().statusCode(), is(status));
  }

}
