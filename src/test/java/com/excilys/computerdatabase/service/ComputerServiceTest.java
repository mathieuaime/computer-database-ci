package com.excilys.computerdatabase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

    private static final long ID = 1L;

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
        when(computerRepository.findById(ID)).thenReturn(Optional.of(computer));

        ComputerDto computerDto = new ComputerDto();
        when(computerMapper.toDto(computer)).thenReturn(computerDto);

        Optional<ComputerDto> optComputer = computerService.findById(ID);

        assertThat(optComputer).contains(computerDto);
    }

    @Test
    void findByCompany() {
        int companyId = 1;
        Computer computer = new Computer();

        when(computerRepository.findByCompany(companyId, Pageable.unpaged()))
            .thenReturn(new PageImpl<>(Collections.singletonList(computer)));

        ComputerDto computerDto = new ComputerDto();
        when(computerMapper.toDto(computer)).thenReturn(computerDto);

        Page<ComputerDto> computers = computerService.findByCompany(companyId, Pageable.unpaged());

        assertThat(computers).containsExactly(computerDto);
    }

    @Test
    void save() {
        Computer computer = new Computer();
        Computer computerSaved = new Computer().setId(ID);

        when(computerRepository.save(computer)).thenReturn(computerSaved);

        ComputerDto computerDto = new ComputerDto();
        ComputerDto computerDtoSaved = new ComputerDto().setId(ID);

        when(computerMapper.toEntity(computerDto)).thenReturn(computer);
        when(computerMapper.toDto(computerSaved)).thenReturn(computerDtoSaved);

        ComputerDto saved = computerService.save(computerDto);

        assertThat(saved).isEqualTo(computerDtoSaved);
    }

    @Test
    void delete_whenNotFound_shouldDoNothing() {
        computerService.delete(ID);

        verify(computerRepository, never()).delete(any());
    }

    @Test
    void delete_whenFound_shouldDelete() {
        Computer computer = new Computer();

        when(computerRepository.findById(ID)).thenReturn(Optional.of(computer));
        computerService.delete(ID);

        verify(computerRepository).delete(computer);
    }
}