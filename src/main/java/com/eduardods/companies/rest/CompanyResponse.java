package com.eduardods.companies.rest;

import java.util.Set;

import com.eduardods.companies.app.Company;

public class CompanyResponse {
  private String id;
  private String name;
  private String address;
  private String city;
  private String country;
  private String email;
  private String phone;
  private Set<String> owners;

  public CompanyResponse(){}

  public CompanyResponse(Company company) {
    setId(company.getId());
    setName(company.getName());
    setAddress(company.getAddress());
    setCity(company.getCity());
    setCountry(company.getCountry());
    setEmail(company.getEmail());
    setPhone(company.getPhone());
    setOwners(company.getOwners());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Set<String> getOwners() {
    return owners;
  }

  public void setOwners(Set<String> owners) {
    this.owners = owners;
  }
}
