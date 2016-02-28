package com.eduardods.companies.acceptance.common.steps;

import static com.eduardods.companies.acceptance.support.ScenarioContext.saveResponseInContext;
import static com.eduardods.companies.acceptance.support.Server.getBaseUri;
import static com.jayway.restassured.RestAssured.given;

import com.eduardods.companies.acceptance.support.DBClient;
import com.eduardods.companies.acceptance.support.ScenarioContext;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;

public class DataSteps {

  @After
  public void clean() {
    ScenarioContext.clear();
    DBClient.getInstance().clearDatabase();
  }

  @Given("No company exists")
  public void databaseIsEmpty() {
    DBClient.getInstance().clearDatabase();
  }


  @Given("This company exists")
  public void preCreateCompany(String payload) {
    saveResponseInContext(requestTocreateCompany("/companies", ContentType.JSON.toString(), payload));
  }

  private Response requestTocreateCompany(String path, String contentType, String payload) {
    return given()
      .contentType(contentType)
      .body(payload)
      .when()
      .post(getBaseUri() + path)
      .andReturn();
  }

}
