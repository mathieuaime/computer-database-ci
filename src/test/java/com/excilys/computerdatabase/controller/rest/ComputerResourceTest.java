package com.excilys.computerdatabase.controller.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ComputerResource.class)
class ComputerResourceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComputerService computerService;

    @Test
    public void findByUuid() throws Exception {
        ComputerDto computerDto = new ComputerDto();
        computerDto.setUuid("uuid");
        computerDto.setName("name");

        when(computerService.findByUuid("uuid")).thenReturn(Optional.of(computerDto));

        MockHttpServletRequestBuilder builder =
            get("/api/v1/computers/{uuid}", computerDto.getUuid())
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse();

        ComputerDto computerReturned = objectMapper
            .readValue(response.getContentAsString(), ComputerDto.class);

        assertThat(computerReturned).isEqualTo(computerDto);
    }

    @Test
    public void findByUuid_notFound() throws Exception {
        when(computerService.findByUuid("uuid")).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder builder = get("/api/v1/computers/{uuid}", "uuid")
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}