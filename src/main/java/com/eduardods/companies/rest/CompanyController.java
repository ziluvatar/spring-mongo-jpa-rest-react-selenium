package com.eduardods.companies.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.eduardods.companies.app.Company;
import com.eduardods.companies.app.CompaniesRepository;

@RestController
@RequestMapping(value = CompanyController.PATH)
public class CompanyController {

  public static final String RESOURCE = "companies";
  public static final String PATH = "/" + RESOURCE;

  private CompaniesRepository companiesRepository;

  @Autowired
  public CompanyController(CompaniesRepository companiesRepository) {
    this.companiesRepository = companiesRepository;
  }

  @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void post(@RequestBody @Valid CompanyRequest companyRequest,
                   UriComponentsBuilder uriBuilder, HttpServletResponse response) {
    String id = companiesRepository.save(toCompany(companyRequest));

    response.setHeader(HttpHeaders.LOCATION, uriBuilder.pathSegment(RESOURCE, id).build().getPath());
  }

  private Company toCompany(CompanyRequest request) {
    Company company = companiesRepository.newCompany();
    company.setName(request.getName());
    company.setAddress(request.getAddress());
    company.setCity(request.getCity());
    company.setCountry(request.getCountry());
    company.setEmail(request.getEmail());
    company.setPhone(request.getPhone());
    company.setOwners(request.getOwners());
    return company;
  }


  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(value = HttpStatus.OK)
  public CompanyListResponse list() {
    return new CompanyListResponse(companiesRepository.findAll());
  }

  @RequestMapping(method = RequestMethod.GET, path = "/{companyId}", produces = "application/json")
  public ResponseEntity<CompanyResponse> get(@PathVariable() String companyId) {
    Optional<Company> companyOptional = companiesRepository.findOne(companyId);

    if (companyOptional.isPresent()) {
      return new ResponseEntity<>(new CompanyResponse(companyOptional.get()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/{companyId}", consumes = "application/json")
  public ResponseEntity<Void> put(@PathVariable String companyId,
                                  @RequestBody @Valid CompanyRequest companyRequest) {
    if (companyExists(companyId)) {
      saveCompany(companyId, companyRequest);
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  private boolean companyExists(String companyId) {
    return companiesRepository.findOne(companyId).isPresent();
  }

  private void saveCompany(String companyId, CompanyRequest companyRequest) {
    Company companyToUpdate = toCompany(companyRequest);
    companyToUpdate.setId(companyId);
    companiesRepository.save(companyToUpdate);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/{companyId}/owners", consumes = "application/json")
  public ResponseEntity<Void> postOwner(@PathVariable String companyId,
                                        @RequestBody @Valid OwnersRequest ownersRequest) {
    Optional<Company> storedCompanyOptional = companiesRepository.findOne(companyId);

    if (storedCompanyOptional.isPresent()) {
      addOwners(ownersRequest, storedCompanyOptional.get());
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  private void addOwners(@RequestBody @Valid OwnersRequest ownersRequest, Company companyToUpdate) {
    companyToUpdate.addOwners(ownersRequest.getOwners());
    companiesRepository.save(companyToUpdate);
  }

}
