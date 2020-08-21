package com.excilys.computerdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.excilys.computerdatabase.persistence.repository.ComputerRepository;
import com.excilys.computerdatabase.persistence.entity.CompanyEntity;
import com.excilys.computerdatabase.persistence.entity.ComputerEntity;
import com.excilys.computerdatabase.web.dto.CompanyDto;
import com.excilys.computerdatabase.web.dto.ComputerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
  void findById() throws Exception {
    long id = 1;

    var builder =
        get("/api/v1/computers/{id}", id)
            .contentType(MediaType.APPLICATION_JSON);

    var response = mockMvc.perform(builder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse();

    var computerReturned = objectMapper
        .readValue(response.getContentAsString(), ComputerDto.class);

    assertThat(computerReturned.getId()).isEqualTo(id);
    assertThat(computerReturned.getName()).isEqualTo("computer-1");
  }

  @Test
  void create() throws Exception {
    long dbSizeBeforeCreation = computerRepository.count();

    var companyDto = CompanyDto.builder().id(1L).name("Company").build();
    var computerDtoToSave =
        ComputerDto.builder().name("name").introduced(Instant.now()).company(companyDto).build();

    var builder = post("/api/v1/computers")
        .content(objectMapper.writeValueAsString(computerDtoToSave))
        .contentType(MediaType.APPLICATION_JSON);

    var response = mockMvc.perform(builder)
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse();

    var computerReturned = objectMapper
        .readValue(response.getContentAsString(), ComputerDto.class);

    long dbSizeAfterCreation = computerRepository.count();

    assertThat(dbSizeAfterCreation).isEqualTo(dbSizeBeforeCreation + 1);

    var optComputer = computerRepository.findById(computerReturned.getId());

    assertThat(optComputer).map(ComputerEntity::getName).contains("name");
  }

  @Test
  void update() throws Exception {
    var company = new CompanyEntity().setId(1L);

    var created =
        new ComputerEntity().setName("name-to-update").setIntroduced(Instant.now())
            .setCompany(company);
    computerRepository.save(created);

    long dbSizeBeforeCreation = computerRepository.count();

    var companyDto = CompanyDto.builder().id(1L).name("Company").build();
    var computerDtoToSave = ComputerDto.builder()
        .id(created.getId())
        .name("name-updated")
        .introduced(Instant.now())
        .company(companyDto)
        .build();

    var builder = put("/api/v1/computers/{id}", created.getId())
        .content(objectMapper.writeValueAsString(computerDtoToSave))
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder)
        .andExpect(status().isOk());

    long dbSizeAfterCreation = computerRepository.count();

    assertThat(dbSizeAfterCreation).isEqualTo(dbSizeBeforeCreation);

    var optComputer = computerRepository
        .findById(created.getId());

    assertThat(optComputer).map(ComputerEntity::getName).contains("name-updated");
  }

  @Test
  void deleteById() throws Exception {
    long id = 1;

    long dbSizeBeforeDeletion = computerRepository.count();

    var builder = delete("/api/v1/computers/{id}", id);

    mockMvc.perform(builder)
        .andExpect(status().isAccepted());

    long dbSizeAfterDeletion = computerRepository.count();

    assertThat(dbSizeAfterDeletion).isEqualTo(dbSizeBeforeDeletion - 1);

    var optComputer = computerRepository.findById(id);

    assertThat(optComputer).isEmpty();
  }

  @Test
  void deleteById_whenNotFound() throws Exception {
    long id = 0;

    long dbSizeBeforeDeletion = computerRepository.count();

    var builder = delete("/api/v1/computers/{id}", id);

    mockMvc.perform(builder)
        .andExpect(status().isAccepted());

    long dbSizeAfterDeletion = computerRepository.count();

    assertThat(dbSizeAfterDeletion).isEqualTo(dbSizeBeforeDeletion);

    var optComputer = computerRepository.findById(id);

    assertThat(optComputer).isEmpty();
  }
}