package com.excilys.computerdatabase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.repository.CompanyRepository;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
  @Mock
  private CompanyRepository companyRepository;

  @InjectMocks
  private CompanyService companyService;

  @Test
  void findByUuid_notFound() {
    long id = 0;
    when(companyRepository.findById(id, CompanyDto.class)).thenReturn(Optional.empty());

    Optional<CompanyDto> optCompany = companyService.findById(id);

    assertThat(optCompany).isEmpty();
  }

  @Test
  void findByUuid() {
    long id = 1;
    CompanyDto companyDto = new CompanyDto(id, "name");
    when(companyRepository.findById(id, CompanyDto.class)).thenReturn(Optional.of(companyDto));

    Optional<CompanyDto> optCompany = companyService.findById(id);

    assertThat(optCompany).contains(companyDto);
  }
}