package com.eduardods.companies.ui.acceptance.steps;

import com.eduardods.companies.rest.acceptance.support.DBClient;

import cucumber.api.java.en.Given;

public class CommonSteps {

  @Given("No company exists")
  public void databaseIsEmpty() {
    DBClient.getInstance().clearDatabase();
  }


}
