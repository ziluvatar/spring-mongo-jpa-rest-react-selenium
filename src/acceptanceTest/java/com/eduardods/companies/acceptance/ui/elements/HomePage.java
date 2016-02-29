package com.eduardods.companies.acceptance.ui.elements;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.eduardods.companies.acceptance.support.Server;
import com.eduardods.companies.acceptance.support.Browser;

public class HomePage {

  private static final By NEW_COMPANY_BUTTON = By.cssSelector(".global-actions button.btn-primary");
  private static final By COMPANY_FORM = By.cssSelector("form[name='companyForm']");

  private WebDriver webDriver = Browser.getWebDriver();

  public void open() {
    webDriver.get(Server.getBaseUri());
  }

  public CompanyListElement getCompanyList() {
    return new CompanyListElement(webDriver.findElement(By.className("companyList")));
  }

  public void clickNewButton() {
    webDriver.findElement(NEW_COMPANY_BUTTON).click();
  }

  public Optional<CompanyForm> getCompanyForm() {
    List<WebElement> elements = webDriver.findElements(COMPANY_FORM);
    if (elements.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new CompanyForm(elements.get(0)));
    }
  }
}
