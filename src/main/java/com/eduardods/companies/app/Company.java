package com.eduardods.companies.app;

import java.util.Collection;
import java.util.Set;


public interface Company {

  String getId();
  void setId(String id);

  String getName();
  void setName(String name);

  String getAddress();
  void setAddress(String address);

  String getCity();
  void setCity(String city);

  String getCountry();
  void setCountry(String country);

  String getEmail();
  void setEmail(String email);

  String getPhone();
  void setPhone(String phone);

  Set<String> getOwners();
  void setOwners(Set<String> owners);

  default void addOwners(Set<String> owners) {
    getOwners().addAll(owners);
  }
}
