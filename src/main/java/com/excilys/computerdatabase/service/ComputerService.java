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

    public Optional<ComputerDto> findByUuid(String uuid) {
        return this.computerRepository.findById(uuid).map(computerMapper::toDto);
    }

    public Page<ComputerDto> findByCompany(String uuid, Pageable pageable) {
        return this.computerRepository.findByCompany_JDBCTemplate(uuid, pageable).map(computerMapper::toDto);
    }

    public ComputerDto save(ComputerDto computerDto) {
        Computer computer = computerMapper.toEntity(computerDto);
        computer = this.computerRepository.save(computer);
        return computerMapper.toDto(computer);
    }

    public void delete(String uuid) {
        this.computerRepository.deleteById(uuid);
    }
}
