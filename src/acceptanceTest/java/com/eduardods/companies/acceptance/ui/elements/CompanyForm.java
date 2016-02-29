package com.eduardods.companies.acceptance.ui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CompanyForm {

  private WebElement rootElement;

  public CompanyForm(WebElement rootElement) {
    this.rootElement = rootElement;
  }

  public void set(String name, String text) {
    rootElement.findElement(By.name(name)).sendKeys(text);
  }

  public void submit() {
    rootElement.findElement(By.className("btn-primary")).click();
  }

}
