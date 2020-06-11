package com.excilys.computerdatabase.service.fake;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.excilys.computerdatabase.service.mapper.CompanyMapper;

public class FakeCompanyMapper implements CompanyMapper {

  private final Company company;
  private final CompanyDto companyDto;

  public FakeCompanyMapper(Company company, CompanyDto companyDto) {
    this.company = company;
    this.companyDto = companyDto;
  }

  @Override
  public CompanyDto toDto(Company entity) {
    return companyDto;
  }

  @Override
  public Company toEntity(CompanyDto dto) {
    return company;
  }
}
