package com.excilys.computerdatabase.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.repository.CompanyRepository;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.excilys.computerdatabase.service.fake.FakeCompanyMapper;
import com.excilys.computerdatabase.service.fake.FakeCompanyRepository;
import com.excilys.computerdatabase.service.mapper.CompanyMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyServiceTest {

    public static final Company COMPANY = new Company().setUuid("uuid").setName("name");
    public static final CompanyDto COMPANY_DTO = new CompanyDto().setUuid("uuid").setName("name");

    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        CompanyMapper companyMapper = new FakeCompanyMapper(COMPANY, COMPANY_DTO);
        CompanyRepository companyRepository = new FakeCompanyRepository(COMPANY);

        companyService = new CompanyService(companyRepository, companyMapper);
    }

    @Test
    void findByUuid_notFound() {
        Optional<CompanyDto> optCompany = companyService.findByUuid("uuid-unknown");

        assertThat(optCompany).isEmpty();
    }

    @Test
    void findByUuid() {
        Optional<CompanyDto> optCompany = companyService.findByUuid("uuid");

        assertThat(optCompany).contains(COMPANY_DTO);
    }

}