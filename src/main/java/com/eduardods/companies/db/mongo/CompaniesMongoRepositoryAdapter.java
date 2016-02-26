package com.eduardods.companies.db.mongo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.eduardods.companies.app.CompaniesRepository;
import com.eduardods.companies.app.Company;

@Component
@Profile("mongo")
public class CompaniesMongoRepositoryAdapter implements CompaniesRepository {

  private CompaniesMongoRepository repository;

  @Autowired
  public CompaniesMongoRepositoryAdapter(CompaniesMongoRepository repository) {
    this.repository = repository;
  }

  @Override
  public String save(Company company) {
    return repository.save(toCompanyMongo(company)).getId();
  }

  @Override
  public List<Company> findAll() {
    return repository.findAll().stream().collect(Collectors.toList());
  }

  @Override
  public Optional<Company> findOne(String id) {
    return Optional.ofNullable(repository.findOne(id));
  }

  @Override
  public Company newCompany() {
    return new CompanyMongoImpl();
  }

  private CompanyMongoImpl toCompanyMongo(Company request) {
    CompanyMongoImpl company = new CompanyMongoImpl();
    company.setId(request.getId());
    company.setName(request.getName());
    company.setAddress(request.getAddress());
    company.setCity(request.getCity());
    company.setCountry(request.getCountry());
    company.setEmail(request.getEmail());
    company.setPhone(request.getPhone());
    company.setOwners(request.getOwners());
    return company;
  }
}
