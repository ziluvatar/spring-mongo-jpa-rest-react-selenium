package com.eduardods.companies.acceptance.ui.elements;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.eduardods.companies.acceptance.support.Server;
import com.eduardods.companies.acceptance.support.Browser;

public class HomePage {

  private static final By NEW_COMPANY_BUTTON = By.cssSelector(".global-actions button.btn-primary");
  private static final By COMPANY_FORM = By.cssSelector("form[name='companyForm']");
  private static final By OWNERS_FORM = By.cssSelector("form[name='ownersForm']");

  private WebDriver webDriver = Browser.getWebDriver();

  public void open() {
    webDriver.get(Server.getBaseUri());
  }

  private void enableWebConsole() {
    webDriver.findElement(By.tagName("body")).sendKeys(Keys.ALT, Keys.COMMAND, "i");
  }

  public CompanyListElement getCompanyList() {
    return new CompanyListElement(webDriver.findElement(By.className("companyList")));
  }

  public void clickNewButton() {
    webDriver.findElement(NEW_COMPANY_BUTTON).click();
  }

  public Optional<FormElement> getCompanyForm() {
    return findOptionalElement(COMPANY_FORM);
  }

  private Optional<FormElement> findOptionalElement(By selector) {
    List<WebElement> elements = webDriver.findElements(selector);
    if (elements.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new FormElement(elements.get(0)));
    }
  }

  public Optional<FormElement> getOwnersForm() {
    return findOptionalElement(OWNERS_FORM);
  }
}
