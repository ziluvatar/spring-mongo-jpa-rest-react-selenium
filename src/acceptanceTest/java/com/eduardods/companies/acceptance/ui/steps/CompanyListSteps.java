package com.eduardods.companies.acceptance.ui.steps;

import static com.eduardods.companies.acceptance.support.Server.getBaseUri;
import static com.eduardods.companies.acceptance.support.ListUtil.map;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.eduardods.companies.acceptance.ui.elements.HomePage;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CompanyListSteps {

  private HomePage homePage = new HomePage();

  @When("User opens the home page")
  public void openHomePage() {
    homePage.open();
  }

  @Then("the list header with (\\d+) columns is displayed")
  public void checkListHeaderIsVisible(Integer columns) {
    List<String> headers = homePage.getCompanyList().getHeaders();
    assertThat(headers, hasSize(columns));
  }

  @Then("the company list is empty")
  public void checkListIsEmpty() {
    assertThat(homePage.getCompanyList().hasCompanies(), is(false));
  }

  @Then("the company list shows this information")
  public void checkListItems(DataTable expectedTable) {
    List<List<String>> expectedCompanies = expectedTable.cells(1);

    List<List<WebElement>> actualTable = homePage.getCompanyList().getRows();
    List<List<String>> actualCompanies = map(actualTable, (rowCells) -> map(rowCells, WebElement::getText));

    assertThat(actualCompanies, equalTo(expectedCompanies));
  }

}
