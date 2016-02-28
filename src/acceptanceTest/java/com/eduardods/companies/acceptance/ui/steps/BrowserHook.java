package com.eduardods.companies.acceptance.ui.steps;

import com.eduardods.companies.acceptance.support.Browser;

import cucumber.api.java.Before;

public class BrowserHook {

  private static boolean added = false;

  @Before
  public void startServer() {
    if (!added) {
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          Browser.close();
        }
      });
    }
  }

}
