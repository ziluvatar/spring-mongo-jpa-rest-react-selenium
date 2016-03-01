package com.eduardods.companies.acceptance.ui.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;

import java.util.List;

import com.eduardods.companies.acceptance.ui.elements.CompanyListElement.CompanyListRow;
import com.eduardods.companies.acceptance.ui.elements.FormElement;
import com.eduardods.companies.acceptance.ui.elements.FormElement.FieldElement;
import com.eduardods.companies.acceptance.ui.elements.HomePage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddOwnersSteps {

  private static final String OWNERS_FIELD_NAME = "owners";

  private HomePage homePage = new HomePage();

  @When("user clicks on Add Owners Button in row (\\d+) button")
  public void clickEditButtonOnRow(Integer rowPlusOne) {
    int rowIndex = rowPlusOne - 1;

    List<CompanyListRow> rows = homePage.getCompanyList().getRows();
    rows.get(rowIndex).getAddOwnersButton().click();
  }

  @Then("the owners form is displayed")
  public void checkOwnersFormIsDisplayed() {
    assertThat(homePage.getOwnersForm().isPresent(), is(true));
  }

  @Then("the owners form is not displayed")
  public void checkOwnersFormIsNotDisplayed() {
    assertThat(homePage.getOwnersForm().isPresent(), is(false));
  }

  @Then("add owners form is empty")
  public void checkBlankOwners() {
    assertThat(getOwnersField().getText(), isEmptyString());
  }

  @When("user enter these owners: '(.*)'")
  public void userEditOwners(String ownersToAdd) {
    FormElement ownersForm = homePage.getOwnersForm().get();
    ownersForm.setFieldText(OWNERS_FIELD_NAME, ownersToAdd);
    ownersForm.submit();
  }

  @Then("owners form shows this error: '(.+)'")
  public void checkFieldErrors(String expectedError) {
    assertThat(getOwnersField().getErrorMessage().isPresent(), is(true));
    assertThat(getOwnersField().getErrorMessage().get(), is(expectedError));
  }

  private FieldElement getOwnersField() {
    return homePage.getOwnersForm().get().get(OWNERS_FIELD_NAME);
  }
}
