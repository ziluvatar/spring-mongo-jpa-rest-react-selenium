package com.eduardods.companies.app;

import java.util.List;
import java.util.Optional;

public interface CompaniesRepository {

  Company newCompany();

  String save(Company company);

  List<Company> findAll();

  Optional<Company> findOne(String id);

}
