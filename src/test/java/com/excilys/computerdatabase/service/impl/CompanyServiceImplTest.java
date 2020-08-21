package com.excilys.computerdatabase.service.impl;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.dao.CompanyDao;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {
  @Mock
  private CompanyDao companyDao;

  @InjectMocks
  private CompanyServiceImpl companyService;

  @Test
  void find() {
    var company = mock(Company.class);

    when(companyDao.find(Pageable.unpaged())).thenReturn(new PageImpl<>(of(company)));

    var companies = companyService.find(Pageable.unpaged());

    assertThat(companies.getContent()).contains(company);
  }

  @Test
  void findById_notFound() {
    long id = 0;

    when(companyDao.findById(id)).thenReturn(Optional.empty());

    var optCompany = companyService.findById(id);

    assertThat(optCompany).isEmpty();
  }

  @Test
  void findById() {
    var company = mock(Company.class);
    long id = 1;

    when(companyDao.findById(id)).thenReturn(Optional.of(company));

    var optCompany = companyService.findById(id);

    assertThat(optCompany).contains(company);
  }

  @Test
  void search() {
    var company = mock(Company.class);
    String filter = "filter";

    when(companyDao.search(filter, Pageable.unpaged())).thenReturn(new PageImpl<>(of(company)));

    var companies = companyService.search(filter, Pageable.unpaged());

    assertThat(companies.getContent()).contains(company);
  }
}