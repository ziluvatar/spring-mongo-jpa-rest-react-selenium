package com.eduardods.companies.acceptance.support;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class Browser {

  private static Browser INSTANCE = new Browser();

  private WebDriver webDriver;

  private Browser() {
    webDriver = createFirefoxWebDriver();
  }

  private WebDriver createFirefoxWebDriver() {
    FirefoxDriver driver = new FirefoxDriver(new FirefoxProfile());
    return driver;
  }

  private WebDriver createHtmlUnitDriver() {
    HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38);
    driver.setJavascriptEnabled(true);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    return driver;
  }

  public static WebDriver getWebDriver() {
    return INSTANCE.webDriver;
  }

  public static void close() {
    INSTANCE.webDriver.quit();
  }

}
