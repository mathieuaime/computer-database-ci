package com.excilys.computerdatabase.web.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyResource.class)
class CompanyResourceTest {
  private static final CompanyDto COMPANY_DTO = new CompanyDto(1L, "name");
  private static final ComputerDto COMPUTER_DTO =
      ComputerDto.builder().id(1L).name("c-name").introduced(Instant.now()).build();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CompanyService companyService;

  @MockBean
  private ComputerService computerService;

  @Test
  void findAll() throws Exception {
    when(companyService.find(any())).thenReturn(new PageImpl<>(List.of(COMPANY_DTO)));

    MockHttpServletRequestBuilder builder =
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
    when(companyService.findById(id)).thenReturn(Optional.of(COMPANY_DTO));

    MockHttpServletRequestBuilder builder =
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

    MockHttpServletRequestBuilder builder =
        get("/api/v1/companies/{id}", id)
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

  @Test
  void findComputers() throws Exception {
    long id = 1;
    when(computerService.findByCompany(eq(id), any()))
        .thenReturn(new PageImpl<>(List.of(COMPUTER_DTO)));

    MockHttpServletRequestBuilder builder =
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