package com.eduardods.companies.ui.acceptance.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import com.eduardods.companies.ui.acceptance.HomePage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CompanyListSteps {

  private HomePage homePage = new HomePage();

  @When("User opens the home page")
  public void openHomePage() {
    homePage.open();
  }

  @Then("you can see the list header with (\\d+) columns")
  public void checkListHeaderIsVisible(Integer columns) {
    List<String> headers = homePage.getCompanyList().getHeaders();
    assertThat(headers, hasSize(columns));
  }

  @Then("you can not see any company in the list")
  public void checkListIsEmpty() {
    assertThat(homePage.getCompanyList().hasCompanies(), is(false));
  }
}
