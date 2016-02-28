package com.eduardods.companies.rest.acceptance.support;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.eduardods.companies.main.Application;

public class Server {

  private static final Server INSTANCE = new Server();

  private boolean started = false;
  private ConfigurableApplicationContext app;

  private Server() {}

  public static Server getInstance() { return INSTANCE; }

  public void start() {
    app = SpringApplication.run(Application.class,
      "--spring.profiles.active=mongo",
      "--server.port=" + getPort(),
      "--spring.data.mongodb.uri=" + DBClient.getMongoUri()
    );
    started = true;
  }

  public void stop() {
    app.stop();
    started = false;
  }

  public boolean isStarted() {
    return started;
  }

  public static String getHost() {
    return "localhost";
  }

  public static String getPort() {
    return "8081";
  }

  public static String getBaseUri() {
    return String.format("http://%s:%s", getHost(), getPort());
  }

}
