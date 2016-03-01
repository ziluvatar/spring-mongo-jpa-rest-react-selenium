package com.eduardods.companies.acceptance.ui.elements;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CompanyForm {

  private WebElement rootElement;

  public CompanyForm(WebElement rootElement) {
    this.rootElement = rootElement;
  }

  public void setFieldText(String name, String text) {
    rootElement.findElement(By.name(name)).sendKeys(text);
  }

  public Field get(String name) {
    return new Field(rootElement.findElement(By.className("form-group-" + name)));
  }

  public void submit() {
    rootElement.findElement(By.className("btn-primary")).click();
  }

  public static class Field {

    private WebElement rootElement;

    public Field(WebElement rootElement) {
      this.rootElement = rootElement;
    }

    public String getValue() {
      return rootElement.findElement(By.tagName("input")).getText();
    }

    public Optional<String> getErrorMessage() {
      List<WebElement> elements = rootElement.findElements(By.className("error-message"));
      if (elements.isEmpty()) {
        return Optional.empty();
      } else {
        return Optional.of(elements.get(0).getText());
      }
    }
  }

}
