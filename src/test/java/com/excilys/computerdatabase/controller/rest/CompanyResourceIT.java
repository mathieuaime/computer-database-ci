package com.excilys.computerdatabase.controller.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyResourceIT {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findByUuid() throws Exception {
    MockHttpServletRequestBuilder builder =
        get("/api/v1/companies/{uuid}", "company-uuid-1")
            .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(builder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse();

    CompanyDto computerReturned = objectMapper
        .readValue(response.getContentAsString(), CompanyDto.class);

    assertThat(computerReturned.getUuid()).isEqualTo("company-uuid-1");
    assertThat(computerReturned.getName()).isEqualTo("company-1");
  }

  @Test
  public void findByUuid_notFound() throws Exception {
    MockHttpServletRequestBuilder builder = get("/api/v1/companies/{uuid}", "company-uuid-unknown")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}