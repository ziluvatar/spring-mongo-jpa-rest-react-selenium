package com.eduardods.companies.acceptance.ui.elements;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CompanyListElement {

  private WebElement rootElement;

  public CompanyListElement(WebElement rootElement) {
    this.rootElement = rootElement;
  }

  public List<String> getHeaders() {
    return rootElement.findElements(By.cssSelector("table th"))
      .stream()
      .map(WebElement::getText)
      .collect(Collectors.toList());
  }

  public boolean hasCompanies () {
    return !findCompanyRows().isEmpty();
  }

  private List<WebElement> findCompanyRows() {
    return rootElement.findElements(By.cssSelector("table tr.companyRow"));
  }

  public List<List<WebElement>> getRows() {
    return findCompanyRows()
      .stream()
      .map((e) -> e.findElements(By.tagName("td")))
      .collect(Collectors.toList());
  }
}
