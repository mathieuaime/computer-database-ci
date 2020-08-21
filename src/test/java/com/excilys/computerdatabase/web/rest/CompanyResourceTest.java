package com.excilys.computerdatabase.web.rest;

import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.web.dto.CompanyDto;
import com.excilys.computerdatabase.web.dto.ComputerDto;
import com.excilys.computerdatabase.web.mapper.CompanyMapper;
import com.excilys.computerdatabase.web.mapper.ComputerMapper;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyResource.class)
class CompanyResourceTest {
  private static final CompanyDto COMPANY_DTO = CompanyDto.builder().id(1L).name("name").build();
  private static final ComputerDto COMPUTER_DTO =
      ComputerDto.builder().id(1L).name("c-name").introduced(Instant.now()).build();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CompanyService companyService;

  @MockBean
  private ComputerService computerService;

  @MockBean
  private CompanyMapper companyMapper;

  @MockBean
  private ComputerMapper computerMapper;

  @Mock
  private Company company;

  @Mock
  private Computer computer;

  @BeforeEach
  void setUp() {
    when(companyMapper.toModel(COMPANY_DTO)).thenReturn(company);
    when(companyMapper.toDto(company)).thenReturn(COMPANY_DTO);
    when(computerMapper.toModel(COMPUTER_DTO)).thenReturn(computer);
    when(computerMapper.toDto(computer)).thenReturn(COMPUTER_DTO);
  }

  @Test
  void findAll() throws Exception {
    when(companyService.find(any())).thenReturn(new PageImpl<>(of(company)));

    var builder =
        get("/api/v1/companies")
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size", is(1)))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.totalElements", is(1)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("name")));
  }

  @Test
  void findById() throws Exception {
    long id = 1;
    when(companyService.findById(id)).thenReturn(Optional.of(company));

    var builder =
        get("/api/v1/companies/{id}", id)
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("name")));
  }

  @Test
  void findById_notFound() throws Exception {
    long id = 0;
    when(companyService.findById(id)).thenReturn(Optional.empty());

    var builder =
        get("/api/v1/companies/{id}", id)
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

  @Test
  void findComputers() throws Exception {
    long id = 1;
    when(computerService.findByCompany(eq(id), any())).thenReturn(new PageImpl<>(of(computer)));

    var builder =
        get("/api/v1/companies/{id}/computers", id)
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size", is(1)))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.totalElements", is(1)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("c-name")));
  }
}