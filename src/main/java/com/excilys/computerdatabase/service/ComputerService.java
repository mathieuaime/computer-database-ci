package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.ComputerRepository;
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
        return this.computerRepository.findAll(pageable).map(computerMapper::toDto);
    }

    public Optional<ComputerDto> findById(long id) {
        return this.computerRepository.findById(id).map(computerMapper::toDto);
    }

    public Page<ComputerDto> findByCompany(long id, Pageable pageable) {
        return this.computerRepository.findByCompany(id, pageable).map(computerMapper::toDto);
    }

    public ComputerDto save(ComputerDto computerDto) {
        Computer computer = computerMapper.toEntity(computerDto);
        computer = this.computerRepository.save(computer);
        return computerMapper.toDto(computer);
    }

    public void delete(long id) {
        computerRepository.findById(id).ifPresent(computerRepository::delete);
    }
}
