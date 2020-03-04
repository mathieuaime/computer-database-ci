package com.excilys.computerdatabase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyDao companyDao;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void find() {
        Company company = new Company();
        when(this.companyDao.findAll(Pageable.unpaged())).thenReturn(
                new PageImpl<>(Collections.singletonList(company)));

        Page<Company> companies = this.companyService.find(Pageable.unpaged());

        assertThat(companies).containsExactly(company);
        verify(this.companyDao).findAll(Pageable.unpaged());
    }

    @Test
    void findByUuid() {
        Company company = new Company();
        String uuid = UUID.randomUUID().toString();
        when(this.companyDao.findById(uuid)).thenReturn(Optional.of(company));

        Optional<Company> optCompany = this.companyService.findByUuid(uuid);

        assertThat(optCompany).contains(company);
        verify(this.companyDao).findById(uuid);
    }

    @Test
    void save() {
        Company company = new Company();
        when(this.companyDao.save(company)).thenReturn(company);

        Company companySaved = this.companyService.save(company);

        assertThat(companySaved).isEqualTo(company);
        verify(this.companyDao).save(company);
    }

    @Test
    void delete() {
        String uuid = UUID.randomUUID().toString();
        this.companyService.delete(uuid);

        verify(this.companyDao).deleteById(uuid);
    }
}