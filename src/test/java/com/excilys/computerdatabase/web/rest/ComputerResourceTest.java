package com.excilys.computerdatabase.web.rest;

import static java.time.Instant.now;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.web.dto.CompanyDto;
import com.excilys.computerdatabase.web.dto.ComputerDto;
import com.excilys.computerdatabase.web.mapper.ComputerMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
@WebMvcTest(ComputerResource.class)
class ComputerResourceTest {
  private static final CompanyDto COMPANY_DTO = CompanyDto.builder().id(1L).name("c-name").build();

  private static final ComputerDto COMPUTER_DTO =
      ComputerDto.builder().id(1L).name("name").introduced(now()).company(COMPANY_DTO).build();

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ComputerService computerService;

  @MockBean
  private ComputerMapper computerMapper;

  @Mock
  private Computer computer;

  @BeforeEach
  void setUp() {
    when(computerMapper.toDto(computer)).thenReturn(COMPUTER_DTO);
    when(computerMapper.toModel(COMPUTER_DTO)).thenReturn(computer);
  }

  @Test
  void findAll() throws Exception {
    when(computerService.find(any())).thenReturn(new PageImpl<>(List.of(computer)));

    var builder =
        get("/api/v1/computers")
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size", is(1)))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.totalElements", is(1)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("name")))
        .andExpect(jsonPath("$.content[0].company.id", is(1)))
        .andExpect(jsonPath("$.content[0].company.name", is("c-name")));
  }

  @Test
  void findById() throws Exception {
    long id = 1L;
    when(computerService.findById(id)).thenReturn(Optional.of(computer));

    var builder =
        get("/api/v1/computers/{id}", id)
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("name")))
        .andExpect(jsonPath("$.company.id", is(1)))
        .andExpect(jsonPath("$.company.name", is("c-name")));
  }

  @Test
  void findById_notFound() throws Exception {
    long id = 1;
    when(computerService.findById(id)).thenReturn(Optional.empty());

    var builder = get("/api/v1/computers/{id}", id)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isNotFound());
  }

  @Test
  void create_withoutMandatoryAttributes() throws Exception {
    var computerDtoToSave = "{}";

    var builder = post("/api/v1/computers")
        .content(computerDtoToSave)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isBadRequest());
  }

  @Test
  void create() throws Exception {
    var computerDtoToSave =
        ComputerDto.builder().name("name").introduced(now()).build();

    when(computerMapper.toModel(computerDtoToSave)).thenReturn(computer);

    when(computerService.save(computer)).thenReturn(computer);

    var builder = post("/api/v1/computers")
        .content(objectMapper.writeValueAsString(computerDtoToSave))
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(1)));
  }

  @Test
  void update_withoutMandatoryAttributes() throws Exception {
    long id = 1;
    var computerDtoToSave = "{}";

    var builder = put("/api/v1/computers/{id}", id)
        .content(computerDtoToSave)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isBadRequest());
  }

  @Test
  void update_withoutId() throws Exception {
    var computerDtoToSave =
        ComputerDto.builder().name("name").introduced(now()).build();

    var builder = put("/api/v1/computers/{id}", 1)
        .content(objectMapper.writeValueAsString(computerDtoToSave))
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isBadRequest());
  }

  @Test
  void update() throws Exception {
    when(computerService.save(computer)).thenReturn(computer);

    var builder = put("/api/v1/computers/{id}", 1)
        .content(objectMapper.writeValueAsString(COMPUTER_DTO))
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("name")));
  }

  @Test
  void deleteById() throws Exception {
    long id = 1;
    var builder = delete("/api/v1/computers/{id}", id).contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isAccepted());

    verify(computerService).deleteById(id);
  }
}