package com.eduardods.companies.acceptance.rest.steps;

import static com.eduardods.companies.acceptance.support.ScenarioContext.getResponseFromContext;
import static com.eduardods.companies.acceptance.support.ScenarioContext.saveParameterInContext;
import static com.eduardods.companies.acceptance.support.ScenarioContext.saveResponseInContext;
import static com.eduardods.companies.acceptance.support.Server.getBaseUri;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.HttpHeaders.LOCATION;

import java.util.List;

import com.eduardods.companies.acceptance.support.FieldError;
import com.eduardods.companies.acceptance.support.ParameterExpression;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateCompanySteps {

  @When("^I make a POST request to '(.*)' with content-type '(.*)' and payload:$")
  public void createCompany(ParameterExpression path, String contentType, String payload) throws Throwable {
    saveResponseInContext(
      requestTocreateCompany(path.getValue(), contentType, payload));
  }

  private Response requestTocreateCompany(String path, String contentType, String payload) {
    return given()
      .contentType(contentType)
      .body(payload)
    .when()
      .post(getBaseUri() + path)
      .andReturn();
  }

  @Then("response contains (?:this|these) (?:error|errors)")
  public void checkExpectedErrors(List<FieldError> expectedErrors) {
    Response response = getResponseFromContext();

    for (FieldError expectedError : expectedErrors) {
      response.then()
        .body("errors.field", hasItems(expectedError.getField()))
        .body("errors.code", hasItems(expectedError.getCode()));

    }
  }

  @Then("response contains a location header with the uri to the created resource")
  public void checkLocationHeader() {
    getResponseFromContext().then().assertThat().header(LOCATION, startsWith("/companies/"));
  }

  @Given("the resource Location header is saved in '(.*)'")
  public void saveLocation(ParameterExpression parameter) {
    String location = getResponseFromContext().then().extract().header(LOCATION);
    saveParameterInContext(parameter.getName(), location);
  }

}
