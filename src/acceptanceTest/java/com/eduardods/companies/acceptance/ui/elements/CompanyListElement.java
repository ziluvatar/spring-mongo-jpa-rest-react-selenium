package com.eduardods.companies.acceptance.ui.elements;

import static com.eduardods.companies.acceptance.support.ListUtil.map;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CompanyListElement {

  private static final By HEADERS_SELECTOR = By.cssSelector("table th");
  private static final By ROWS_SELECTOR = By.cssSelector("table tr.companyRow");

  private WebElement rootElement;

  public CompanyListElement(WebElement rootElement) {
    this.rootElement = rootElement;
  }

  public List<String> getHeaders() {
    return map(rootElement.findElements(HEADERS_SELECTOR), WebElement::getText);
  }

  public boolean hasCompanies () {
    return !findCompanyRows().isEmpty();
  }

  private List<WebElement> findCompanyRows() {
    return rootElement.findElements(ROWS_SELECTOR);
  }

  public List<CompanyListRow> getRows() {
    return map(findCompanyRows(), (e) -> new CompanyListRow(e.findElements(By.tagName("td"))));
  }

  public class CompanyListRow {
    private List<WebElement> cells;

    public CompanyListRow(List<WebElement> cells) {
      this.cells = cells;
    }

    public List<String> getDataCells() {
      return map(cells.subList(0, 7), WebElement::getText);
    }

    public WebElement getEditButton() {
      return getButtons().get(0);
    }

    public WebElement getAddOwnersButton() {
      return getButtons().get(1);
    }

    private List<WebElement> getButtons() {
      return cells.get(7).findElements(By.tagName("button"));
    }
  }
}
