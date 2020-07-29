package com.excilys.computerdatabase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.repository.CompanyRepository;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import java.util.List;
import java.util.Optional;
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
  private CompanyRepository companyRepository;

  @InjectMocks
  private CompanyService companyService;

  @Test
  void find() {
    CompanyDto companyDto = new CompanyDto(1L, "name");
    when(companyRepository.findProjectedBy(Pageable.unpaged(), CompanyDto.class))
        .thenReturn(new PageImpl<>(List.of(companyDto)));

    Page<CompanyDto> companies = companyService.find(Pageable.unpaged());

    assertThat(companies).contains(companyDto);
  }

  @Test
  void findById_notFound() {
    long id = 0;
    when(companyRepository.findById(id, CompanyDto.class)).thenReturn(Optional.empty());

    Optional<CompanyDto> optCompany = companyService.findById(id);

    assertThat(optCompany).isEmpty();
  }

  @Test
  void findById() {
    long id = 1;
    CompanyDto companyDto = new CompanyDto(id, "name");
    when(companyRepository.findById(id, CompanyDto.class)).thenReturn(Optional.of(companyDto));

    Optional<CompanyDto> optCompany = companyService.findById(id);

    assertThat(optCompany).contains(companyDto);
  }

  @Test
  void search() {
    String filter = "filter";
    CompanyDto companyDto = new CompanyDto(1L, "name");
    when(companyRepository
        .findByNameStartsWithIgnoreCase(filter, Pageable.unpaged(), CompanyDto.class))
        .thenReturn(new PageImpl<>(List.of(companyDto)));

    Page<CompanyDto> companies = companyService.search(filter, Pageable.unpaged());

    assertThat(companies).contains(companyDto);
  }
}