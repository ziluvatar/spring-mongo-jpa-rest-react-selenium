package com.eduardods.companies.rest;

import java.util.List;

import com.eduardods.companies.app.Company;

public class CompanyListResponse {
  private List<Company> companies;

  public CompanyListResponse(List<Company> companies) {
    this.companies = companies;
  }

  public List<Company> getCompanies() {
    return companies;
  }
}
