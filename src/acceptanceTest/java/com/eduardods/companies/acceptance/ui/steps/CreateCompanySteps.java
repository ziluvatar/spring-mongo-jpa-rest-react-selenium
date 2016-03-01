package com.eduardods.companies.acceptance.ui.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

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

  @When("user (?:fills|modifies) and submits the company form with this information:")
  public void fillCompanyForm(Map<String, String> newCompany){
    CompanyForm form = homePage.getCompanyForm().get();
    for (Entry<String, String> dataToInput : newCompany.entrySet()) {
      form.setFieldText(dataToInput.getKey(), dataToInput.getValue());
    }
    form.submit();
  }

  @Then("company form shows these errors for these fields:")
  public void checkFieldErrors(Map<String, String> failedCompany) {
    CompanyForm form = homePage.getCompanyForm().get();
    for (Entry<String, String> fieldAndError : failedCompany.entrySet()) {
      Optional<String> errorOptional = form.get(fieldAndError.getKey()).getErrorMessage();

      assertThat(errorOptional.isPresent(), is(true));
      assertThat(errorOptional.get(), is(fieldAndError.getValue()));
    }
  }
}
