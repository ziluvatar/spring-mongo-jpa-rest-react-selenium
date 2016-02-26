package com.eduardods.companies.db.jpa;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Profile("jpa")
public interface CompaniesJPARepository extends JpaRepository<CompanyJPAImpl, Long> {

  @Query("SELECT DISTINCT a FROM companies a LEFT JOIN FETCH a.owners vp")
  List<CompanyJPAImpl> findAll();
}
