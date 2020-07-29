package com.excilys.computerdatabase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.ComputerRepository;
import com.excilys.computerdatabase.repository.projection.ComputerProjection;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import com.excilys.computerdatabase.service.mapper.ComputerMapper;
import java.time.Instant;
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
  private static final ComputerDto COMPUTER_DTO =
      new ComputerDto(1L, "name", Instant.now(), Instant.now(), null);

  @Mock
  private ComputerRepository computerRepository;

  @Mock
  private ComputerMapper computerMapper;

  @InjectMocks
  private ComputerService computerService;

  @Test
  void find() {
    ComputerProjection projection = mock(ComputerProjection.class);
    when(computerRepository.findProjectedBy(Pageable.unpaged(), ComputerProjection.class))
        .thenReturn(new PageImpl<>(Collections.singletonList(projection)));
    when(computerMapper.fromProjectionToDto(projection)).thenReturn(COMPUTER_DTO);

    Page<ComputerDto> computers = computerService.find(Pageable.unpaged());

    assertThat(computers).containsExactly(COMPUTER_DTO);
  }

  @Test
  void findByUuid() {
    ComputerProjection projection = mock(ComputerProjection.class);
    when(computerRepository.findById(ID, ComputerProjection.class))
        .thenReturn(Optional.of(projection));
    when(computerMapper.fromProjectionToDto(projection)).thenReturn(COMPUTER_DTO);

    Optional<ComputerDto> optComputer = computerService.findById(ID);

    assertThat(optComputer).contains(COMPUTER_DTO);
  }

  @Test
  void findByCompany() {
    int companyId = 1;
    ComputerProjection projection = mock(ComputerProjection.class);
    when(computerRepository
        .findByCompany(companyId, Pageable.unpaged(), ComputerProjection.class))
        .thenReturn(new PageImpl<>(Collections.singletonList(projection)));
    when(computerMapper.fromProjectionToDto(projection)).thenReturn(COMPUTER_DTO);

    Page<ComputerDto> computers = computerService.findByCompany(companyId, Pageable.unpaged());

    assertThat(computers).containsExactly(COMPUTER_DTO);
  }

  @Test
  void search() {
    String filter = "filter";
    ComputerProjection projection = mock(ComputerProjection.class);
    when(computerRepository
        .findByNameStartsWithIgnoreCase(filter, Pageable.unpaged(), ComputerProjection.class))
        .thenReturn(new PageImpl<>(Collections.singletonList(projection)));
    when(computerMapper.fromProjectionToDto(projection)).thenReturn(COMPUTER_DTO);

    Page<ComputerDto> computers = computerService.search(filter, Pageable.unpaged());

    assertThat(computers).containsExactly(COMPUTER_DTO);
  }

  @Test
  void save() {
    Computer computer = new Computer();
    Computer computerSaved = new Computer().setId(ID);

    when(computerRepository.save(computer)).thenReturn(computerSaved);

    when(computerMapper.toEntity(COMPUTER_DTO)).thenReturn(computer);
    when(computerMapper.toDto(computerSaved)).thenReturn(COMPUTER_DTO);

    ComputerDto saved = computerService.save(COMPUTER_DTO);

    assertThat(saved).isEqualTo(COMPUTER_DTO);
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