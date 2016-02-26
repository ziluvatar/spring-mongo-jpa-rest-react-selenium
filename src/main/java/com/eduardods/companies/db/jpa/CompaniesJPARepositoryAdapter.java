package com.eduardods.companies.db.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.eduardods.companies.app.CompaniesRepository;
import com.eduardods.companies.app.Company;

@Component
@Profile("jpa")
public class CompaniesJPARepositoryAdapter implements CompaniesRepository {

  private CompaniesJPARepository repository;

  @Autowired
  public CompaniesJPARepositoryAdapter(CompaniesJPARepository repository) {
    this.repository = repository;
  }

  @Override
  public String save(Company company) {
    return repository.save(toCompanyJPA(company)).getId();
  }

  @Override
  public List<Company> findAll() {
    return repository.findAll().stream().collect(Collectors.toList());
  }

  @Override
  public Optional<Company> findOne(String id) {
    return Optional.ofNullable(repository.findOne(Long.valueOf(id)));
  }

  @Override
  public Company newCompany() {
    return new CompanyJPAImpl();
  }

  private CompanyJPAImpl toCompanyJPA(Company request) {
    CompanyJPAImpl company = new CompanyJPAImpl();
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
