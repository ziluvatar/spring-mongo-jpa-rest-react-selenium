package com.eduardods.companies.acceptance.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Browser {

  private static Browser INSTANCE = new Browser();

  private WebDriver webDriver;

  private Browser() {
    webDriver = createWebDriver();
  }

  private WebDriver createWebDriver() {
    return new FirefoxDriver(new FirefoxProfile());
//    return new HtmlUnitDriver();
  }

  public static WebDriver getWebDriver() {
    return INSTANCE.webDriver;
  }

  public static void close() {
    INSTANCE.webDriver.quit();
  }

}
