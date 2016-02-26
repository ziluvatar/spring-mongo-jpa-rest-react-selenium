package com.eduardods.companies.rest;

import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

public class OwnersRequest {
  @NotEmpty
  private Set<String> owners;

  public Set<String> getOwners() {
    return owners;
  }

  public void setOwners(Set<String> owners) {
    this.owners = owners;
  }
}
