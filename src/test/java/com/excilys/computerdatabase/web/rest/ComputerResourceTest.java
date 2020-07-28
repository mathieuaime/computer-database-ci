package com.excilys.computerdatabase.web.rest;

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

import com.excilys.computerdatabase.repository.CompanyRepository;
import com.excilys.computerdatabase.repository.ComputerRepository;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(ComputerResource.class)
class ComputerResourceTest {
    private static final CompanyDto COMPANY_DTO =
        new CompanyDto(1L, "c-name");

    private static final ComputerDto COMPUTER_DTO =
        new ComputerDto(1L, "name", Instant.now(), null, COMPANY_DTO);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComputerService computerService;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private ComputerRepository computerRepository;

    @Test
    public void findAll() throws Exception {
        when(computerService.find(any())).thenReturn(new PageImpl<>(List.of(COMPUTER_DTO)));

        MockHttpServletRequestBuilder builder =
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
    public void findById() throws Exception {
        long id = 1L;
        when(computerService.findById(id)).thenReturn(Optional.of(COMPUTER_DTO));

        MockHttpServletRequestBuilder builder =
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
    public void findById_notFound() throws Exception {
        long id = 1;
        when(computerService.findById(id)).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder builder = get("/api/v1/computers/{id}", id)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
            .andExpect(status().isNotFound());
    }

    @Test
    public void create_withoutName() throws Exception {
        ComputerDto computerDtoToSave = ComputerDto.builder().build();

        MockHttpServletRequestBuilder builder = post("/api/v1/computers")
            .content(objectMapper.writeValueAsString(computerDtoToSave))
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
            .andExpect(status().isBadRequest());
    }

    @Test
    public void create() throws Exception {
        ComputerDto computerDtoToSave =
            ComputerDto.builder().name("name").introduced(Instant.now()).build();
        when(computerService.save(computerDtoToSave)).thenReturn(COMPUTER_DTO);

        MockHttpServletRequestBuilder builder = post("/api/v1/computers")
            .content(objectMapper.writeValueAsString(computerDtoToSave))
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void update_withoutName() throws Exception {
        long id = 1;
        ComputerDto computerDtoToSave = ComputerDto.builder().id(id).build();

        MockHttpServletRequestBuilder builder = put("/api/v1/computers/{id}", id)
            .content(objectMapper.writeValueAsString(computerDtoToSave))
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
            .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        when(computerService.save(COMPUTER_DTO)).thenReturn(COMPUTER_DTO);

        MockHttpServletRequestBuilder builder = put("/api/v1/computers/{id}", 1)
            .content(objectMapper.writeValueAsString(COMPUTER_DTO))
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("name")));
    }

    @Test
    public void deleteById() throws Exception {
        long id = 1;
        MockHttpServletRequestBuilder builder = delete("/api/v1/computers/{id}", id)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
            .andExpect(status().isAccepted());

        verify(computerService).delete(id);
    }
}