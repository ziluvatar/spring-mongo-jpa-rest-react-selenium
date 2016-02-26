package com.eduardods.companies.db.jpa;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.eduardods.companies.app.Company;

@Entity(name = "companies")
public class CompanyJPAImpl implements Company {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "companies_gen")
  @SequenceGenerator(name = "companies_gen", sequenceName = "COMPANIES_SEQ")
  @Column(name = "id")
  private Long seqId;
  private String name;
  private String address;
  private String city;
  private String country;
  private String email;
  private String phone;

  @ElementCollection()
  @CollectionTable(
    name="company_owners",
    joinColumns=@JoinColumn(name="company_id")
  )
  @Column(name="owner")
  private Set<String> owners;

  @Override
  public String getId() {
    return getSeqId() == null ? null : String.valueOf(getSeqId());
  }

  @Override
  public void setId(String id) {
    if (id == null) {
      setSeqId(null);
    } else {
      setSeqId(Long.valueOf(id));
    }
  }

  public Long getSeqId() {
    return seqId;
  }

  public void setSeqId(Long seqId) {
    this.seqId = seqId;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getAddress() {
    return address;
  }

  @Override
  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String getCountry() {
    return country;
  }

  @Override
  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getPhone() {
    return phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public Set<String> getOwners() {
    return owners;
  }

  @Override
  public void setOwners(Set<String> owners) {
    this.owners = owners;
  }
}
