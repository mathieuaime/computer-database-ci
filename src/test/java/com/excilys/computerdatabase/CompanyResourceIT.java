package com.excilys.computerdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.excilys.computerdatabase.web.dto.CompanyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyResourceIT {
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void findByUuid() throws Exception {
    var builder =
        get("/api/v1/companies/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON);

    var response = mockMvc.perform(builder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse();

    var companyReturned = objectMapper
        .readValue(response.getContentAsString(), CompanyDto.class);

    assertThat(companyReturned.getId()).isEqualTo(1);
    assertThat(companyReturned.getName()).isEqualTo("company-1");
  }

  @Test
  void findByUuid_notFound() throws Exception {
    var builder = get("/api/v1/companies/{id}", 0)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}