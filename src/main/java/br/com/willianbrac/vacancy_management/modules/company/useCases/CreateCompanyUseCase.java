package br.com.willianbrac.vacancy_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.willianbrac.vacancy_management.exceptions.UserFoundException;
import br.com.willianbrac.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.willianbrac.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private CompanyRepository companyRepository;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository
        .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent(company -> {
          throw new UserFoundException();
        });

    return this.companyRepository.save(companyEntity);
  }
}