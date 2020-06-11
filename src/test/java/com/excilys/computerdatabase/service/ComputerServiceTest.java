package com.excilys.computerdatabase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.ComputerRepository;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import com.excilys.computerdatabase.service.mapper.ComputerMapper;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ComputerServiceTest {

    private static final String UUID = "uuid";

    @Mock
    private ComputerRepository computerRepository;

    @Mock
    private ComputerMapper computerMapper;

    @InjectMocks
    private ComputerService computerService;

    @Test
    void find() {
        Computer computer = new Computer();
        when(computerRepository.findAll(Pageable.unpaged()))
            .thenReturn(new PageImpl<>(Collections.singletonList(computer)));

        ComputerDto computerDto = new ComputerDto();
        when(computerMapper.toDto(computer)).thenReturn(computerDto);

        Page<ComputerDto> computers = computerService.find(Pageable.unpaged());

        assertThat(computers).containsExactly(computerDto);
    }

    @Test
    void findByUuid() {
        Computer computer = new Computer();
        when(computerRepository.findById(UUID)).thenReturn(Optional.of(computer));

        ComputerDto computerDto = new ComputerDto();
        when(computerMapper.toDto(computer)).thenReturn(computerDto);

        Optional<ComputerDto> optComputer = computerService.findByUuid(UUID);

        assertThat(optComputer).contains(computerDto);
    }

    @Test
    void findByCompany() {
        Computer computer = new Computer();
        when(computerRepository.findByCompany_JDBCTemplate("company", Pageable.unpaged()))
            .thenReturn(new PageImpl<>(Collections.singletonList(computer)));

        ComputerDto computerDto = new ComputerDto();
        when(computerMapper.toDto(computer)).thenReturn(computerDto);

        Page<ComputerDto> computers = computerService.findByCompany("company", Pageable.unpaged());

        assertThat(computers).containsExactly(computerDto);
    }

    @Test
    void save() {
        Computer computer = new Computer();
        Computer computerSaved = new Computer();
        computerSaved.setUuid(UUID);

        when(computerRepository.save(computer)).thenReturn(computerSaved);

        ComputerDto computerDto = new ComputerDto();
        ComputerDto computerDtoSaved = new ComputerDto();
        computerDtoSaved.setUuid(UUID);

        when(computerMapper.toEntity(computerDto)).thenReturn(computer);
        when(computerMapper.toDto(computerSaved)).thenReturn(computerDtoSaved);

        ComputerDto saved = computerService.save(computerDto);

        assertThat(saved).isEqualTo(computerDtoSaved);
    }

    @Test
    void delete() {
        computerService.delete(UUID);

        verify(computerRepository).deleteById(UUID);
    }
}