package com.eduardods.companies.acceptance.ui.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Map;
import java.util.Map.Entry;

import com.eduardods.companies.acceptance.ui.elements.CompanyForm;
import com.eduardods.companies.acceptance.ui.elements.HomePage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateCompanySteps {

  private HomePage homePage = new HomePage();

  @When("user clicks on New Company button")
  public void clickOnButton() {
    homePage.clickNewButton();
  }

  @Then("the company form is displayed")
  public void checkCompanyFormIsDisplayed() {
    assertThat(homePage.getCompanyForm().isPresent(), is(true));
  }

  @Then("the company form is not displayed")
  public void checkCompanyFormIsNotDisplayed() {
    assertThat(homePage.getCompanyForm().isPresent(), is(false));
  }

  @When("company form is filled and submitted with this information:")
  public void fillCompanyForm(Map<String, String> company){
    checkCompanyFormIsDisplayed();
    CompanyForm form = homePage.getCompanyForm().get();
    for (Entry<String, String> entry : company.entrySet()) {
      form.set(entry.getKey(), entry.getValue());
    }
    form.submit();
  }
}
