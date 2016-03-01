package com.eduardods.companies.acceptance.ui.elements;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class FormElement {

  private WebElement rootElement;

  public FormElement(WebElement rootElement) {
    this.rootElement = rootElement;
  }

  public void setFieldText(String name, String text) {
    if (text.isEmpty()) {
      clearFieldText(name);
    } else {
      rootElement.findElement(By.name(name)).clear();
      rootElement.findElement(By.name(name)).sendKeys(text);
    }
  }

  private void clearFieldText(String name) {
    rootElement.findElement(By.name(name)).clear();
    //due to issue of selenium + react (not onChange method is raised for .clear())
    rootElement.findElement(By.name(name)).sendKeys(" ");
    rootElement.findElement(By.name(name)).sendKeys(Keys.BACK_SPACE);
  }

  public FieldElement get(String name) {
    return new FieldElement(rootElement.findElement(By.className("form-group-" + name)));
  }

  public void submit() {
    rootElement.findElement(By.className("btn-primary")).click();
  }

  public static class FieldElement {

    private WebElement rootElement;

    public FieldElement(WebElement rootElement) {
      this.rootElement = rootElement;
    }

    public String getText() {
      return rootElement.findElement(By.tagName("input")).getAttribute("value");
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
