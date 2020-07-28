package com.excilys.computerdatabase.ui.view.companies;

import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CompanyViewChromeIT {
  @Mock
  private CompanyService companyService;

  private CompanyView companyView;

  @BeforeEach
  void setUp() {
    List<CompanyDto> companies = List.of(CompanyDto.builder().id(1L).name("Company").build());

    when(companyService.find(Pageable.unpaged())).thenReturn(new PageImpl<>(companies));

    companyView = new CompanyView(companyService);
  }
}