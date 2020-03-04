package com.excilys.computerdatabase.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ComputerController.class)
class ComputerControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComputerService computerService;

    @Test
    public void get() throws Exception {
        Computer computer = new Computer();
        when(this.computerService.findByUuid(computer.getUuid())).thenReturn(Optional.of(computer));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/computer/{uuid}", computer.getUuid())
                                                                      .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = this.mockMvc.perform(builder)
                                                       .andExpect(MockMvcResultMatchers.status().isOk())
                                                       .andReturn()
                                                       .getResponse();

        Computer computerReturned = this.objectMapper.readValue(response.getContentAsString(), Computer.class);

        assertThat(computerReturned).isEqualTo(computer);
    }

    @Test
    public void get_not_found() throws Exception {
        String uuid = UUID.randomUUID().toString();
        when(this.computerService.findByUuid(uuid)).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/computer/{uuid}", uuid).contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}