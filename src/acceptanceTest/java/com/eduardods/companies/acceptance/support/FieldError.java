package com.eduardods.companies.acceptance.support;

public class FieldError {
  private String field;
  private String code;

  public FieldError(String field, String code) {
    this.field = field;
    this.code = code;
  }

  public String getField() {
    return field;
  }

  public String getCode() {
    return code;
  }
}
