package com.eduardods.companies.ui.acceptance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.eduardods.companies.rest.acceptance.support.Server;
import com.eduardods.companies.ui.acceptance.support.Browser;

public class HomePage {

  private WebDriver webDriver = Browser.getWebDriver();
  private CompanyListElement companyList;

  public void open() {
    webDriver.get(Server.getBaseUri());
    companyList = new CompanyListElement(webDriver.findElement(By.className("companyList")));
  }

  public CompanyListElement getCompanyList() {
    return companyList;
  }
}
