package com.excilys.computerdatabase.controller.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.excilys.computerdatabase.controller.rest.CompanyResourceTest.TestConfig;
import com.excilys.computerdatabase.controller.rest.CompanyResourceTest.WebConfig;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
class CompanyResourceTest {

  @Configuration
  @EnableWebMvc
  @ComponentScan("com.excilys.computerdatabase")
  static class WebConfig implements WebMvcConfigurer {

  }

  @Configuration
  static class TestConfig {

    @Bean
    public CompanyService companyService() {
      return Mockito.mock(CompanyService.class);
    }
  }

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private CompanyService companyService;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    Mockito.reset(companyService);

    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void findByUuid() throws Exception {
    CompanyDto companyDto = new CompanyDto().setUuid("uuid").setName("name");
    when(companyService.findByUuid("uuid")).thenReturn(Optional.of(companyDto));

    MockHttpServletRequestBuilder builder =
        get("/api/v1/companies/{uuid}", "uuid")
            .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(builder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse();

    CompanyDto computerReturned = objectMapper
        .readValue(response.getContentAsString(), CompanyDto.class);

    assertThat(computerReturned.getUuid()).isEqualTo("uuid");
    assertThat(computerReturned.getName()).isEqualTo("name");
  }

  @Test
  public void findByUuid_notFound() throws Exception {
    when(companyService.findByUuid("uuid")).thenReturn(Optional.empty());

    MockHttpServletRequestBuilder builder = get("/api/v1/companies/{uuid}", "company-uuid-unknown")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}