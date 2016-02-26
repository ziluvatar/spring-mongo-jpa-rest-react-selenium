package com.eduardods.companies.rest;

import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CompanyRequest {

  @NotEmpty
  private String name;

  @NotEmpty
  private String address;

  @NotEmpty
  private String city;

  @NotEmpty
  private String country;

  @Email
  private String email;

  private String phone;

  @NotEmpty
  private Set<String> owners;

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
