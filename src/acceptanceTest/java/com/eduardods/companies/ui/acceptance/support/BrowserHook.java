package com.eduardods.companies.ui.acceptance.support;

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
