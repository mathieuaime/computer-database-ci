package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.ComputerRepository;
import com.excilys.computerdatabase.repository.projection.ComputerProjection;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import com.excilys.computerdatabase.service.mapper.ComputerMapper;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComputerService {
    private final ComputerRepository computerRepository;
    private final ComputerMapper computerMapper;

    public ComputerService(ComputerRepository computerRepository, ComputerMapper computerMapper) {
        this.computerRepository = computerRepository;
        this.computerMapper = computerMapper;
    }

    public Page<ComputerDto> find(Pageable pageable) {
        return this.computerRepository.findProjectedBy(pageable, ComputerProjection.class)
            .map(computerMapper::fromProjectionToDto);
    }

    public Optional<ComputerDto> findById(long id) {
        return this.computerRepository.findById(id, ComputerProjection.class)
            .map(computerMapper::fromProjectionToDto);
    }

    public Page<ComputerDto> findByCompany(long id, Pageable pageable) {
        return this.computerRepository.findByCompany(id, pageable, ComputerProjection.class)
            .map(computerMapper::fromProjectionToDto);
    }

    public Page<ComputerDto> search(String filterText, Pageable pageable) {
        return this.computerRepository
            .findByNameStartsWithIgnoreCase(filterText, pageable, ComputerProjection.class)
            .map(computerMapper::fromProjectionToDto);
    }

    public ComputerDto save(ComputerDto computerDto) {
        Computer computer = this.computerMapper.toEntity(computerDto);
        computer = this.computerRepository.save(computer);
        return this.computerMapper.toDto(computer);
    }

    public void delete(long id) {
        this.computerRepository.findById(id).ifPresent(this.computerRepository::delete);
    }

    public long count() {
        return this.computerRepository.count();
    }
}
