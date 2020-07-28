package com.excilys.computerdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.ComputerRepository;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Optional;
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
class ComputerResourceIT {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ComputerRepository computerRepository;

  @Test
  public void findById() throws Exception {
    long id = 1;

    MockHttpServletRequestBuilder builder =
        get("/api/v1/computers/{id}", id)
            .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(builder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse();

    ComputerDto computerReturned = objectMapper
        .readValue(response.getContentAsString(), ComputerDto.class);

    assertThat(computerReturned.getId()).isEqualTo(id);
    assertThat(computerReturned.getName()).isEqualTo("computer-1");
  }

  @Test
  public void create() throws Exception {
    long dbSizeBeforeCreation = computerRepository.count();

    ComputerDto computerDtoToSave =
        ComputerDto.builder().name("name").introduced(Instant.now()).build();

    MockHttpServletRequestBuilder builder = post("/api/v1/computers")
        .content(objectMapper.writeValueAsString(computerDtoToSave))
        .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(builder)
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse();

    ComputerDto computerReturned = objectMapper
        .readValue(response.getContentAsString(), ComputerDto.class);

    long dbSizeAfterCreation = computerRepository.count();

    assertThat(dbSizeAfterCreation).isEqualTo(dbSizeBeforeCreation + 1);

    Optional<Computer> optComputer = computerRepository.findById(computerReturned.getId());

    assertThat(optComputer).map(Computer::getName).contains("name");
  }

  @Test
  public void update() throws Exception {
    Computer created = new Computer().setName("name");
    computerRepository.save(created);

    long dbSizeBeforeCreation = computerRepository.count();

    ComputerDto computerDtoToSave =
        ComputerDto.builder().id(created.getId()).name("name-updated").introduced(Instant.now())
            .build();

    MockHttpServletRequestBuilder builder = put("/api/v1/computers/{id}", created.getId())
        .content(objectMapper.writeValueAsString(computerDtoToSave))
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isOk());

    long dbSizeAfterCreation = computerRepository.count();

    assertThat(dbSizeAfterCreation).isEqualTo(dbSizeBeforeCreation);

    Optional<Computer> optComputer = computerRepository.findById(created.getId());

    assertThat(optComputer).map(Computer::getName).contains("name-updated");
  }

  @Test
  void deleteById() throws Exception {
    long id = 1;

    long dbSizeBeforeDeletion = computerRepository.count();

    MockHttpServletRequestBuilder builder = delete("/api/v1/computers/{id}", id);

    mockMvc.perform(builder)
        .andExpect(status().isAccepted());

    long dbSizeAfterDeletion = computerRepository.count();

    assertThat(dbSizeAfterDeletion).isEqualTo(dbSizeBeforeDeletion - 1);

    Optional<Computer> optComputer = computerRepository.findById(id);

    assertThat(optComputer).isEmpty();
  }

  @Test
  void deleteById_whenNotFound() throws Exception {
    long id = 0;

    long dbSizeBeforeDeletion = computerRepository.count();

    MockHttpServletRequestBuilder builder = delete("/api/v1/computers/{id}", id);

    mockMvc.perform(builder)
        .andExpect(status().isAccepted());

    long dbSizeAfterDeletion = computerRepository.count();

    assertThat(dbSizeAfterDeletion).isEqualTo(dbSizeBeforeDeletion);

    Optional<Computer> optComputer = computerRepository.findById(id);

    assertThat(optComputer).isEmpty();
  }
}