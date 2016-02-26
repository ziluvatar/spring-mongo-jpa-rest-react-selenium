package com.eduardods.companies.rest.acceptance.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.NamedThreadLocal;

import com.jayway.restassured.response.Response;

public class ScenarioContext {

  private static final ThreadLocal<Response> RESPONSE_HOLDER = new NamedThreadLocal<>("Response Holder");
  private static final ThreadLocal<Map<String, String>> PARAMETERS_HOLDER = new NamedThreadLocal<>("Parameters Holder");

  public static Response getResponseFromContext() {
    return RESPONSE_HOLDER.get();
  }

  public static void saveResponseInContext(Response response) {
    RESPONSE_HOLDER.set(response);
  }

  public static String getParameterFromContext(String key) {
    createMapIfNeeded();
    return PARAMETERS_HOLDER.get().get(key);
  }

  public static void saveParameterInContext(String key, String value) {
    createMapIfNeeded();
    PARAMETERS_HOLDER.get().put(key, value);
  }

  private static void createMapIfNeeded() {
    if (PARAMETERS_HOLDER.get() == null) {
      PARAMETERS_HOLDER.set(new HashMap<>());
    }
  }

  public static void clear() {
    RESPONSE_HOLDER.remove();
    PARAMETERS_HOLDER.remove();
  }
}
