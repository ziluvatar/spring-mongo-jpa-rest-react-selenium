package com.eduardods.companies.acceptance.rest.steps;

import com.eduardods.companies.acceptance.support.Server;

import cucumber.api.java.Before;

public class StartServerHook {

  private static Server server = Server.getInstance();

  @Before
  public void startServer() {
    if (!server.isStarted()) {
      server.start();
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          server.stop();
        }
      });
    }
  }

}
