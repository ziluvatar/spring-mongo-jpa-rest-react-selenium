package com.eduardods.companies.rest.acceptance.steps;

import com.eduardods.companies.rest.acceptance.support.Server;

import cucumber.api.java.Before;

public class StartServerHook {

  private static Server server = new Server();

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
