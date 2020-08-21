package com.excilys.computerdatabase.web.rest;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.web.dto.ComputerDto;
import com.excilys.computerdatabase.web.mapper.ComputerMapper;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/computers")
public class ComputerResource {
    private final ComputerService computerService;
    private final ComputerMapper computerMapper;

    public ComputerResource(ComputerService computerService, ComputerMapper computerMapper) {
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    @GetMapping
    public Page<ComputerDto> find(Pageable pageable) {
        return this.computerService.find(pageable).map(computerMapper::toDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComputerDto> findById(@PathVariable("id") long id) {
        return ResponseEntity.of(this.computerService.findById(id).map(computerMapper::toDto));
    }

    @PostMapping
    public ResponseEntity<ComputerDto> create(@Valid @RequestBody ComputerDto computerDto) {
        Computer computer = getModelFromDto(computerDto);
        computer = this.computerService.save(computer);
        return ResponseEntity.status(HttpStatus.CREATED).body(computerMapper.toDto(computer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComputerDto> update(@Valid @RequestBody ComputerDto computerDto) {
        if (computerDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Computer computer = getModelFromDto(computerDto);
        computer = this.computerService.save(computer);
        return ResponseEntity.ok(computerMapper.toDto(computer));
    }

    private Computer getModelFromDto(ComputerDto computerDto) {
        return computerMapper.toModel(computerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") long id) {
        this.computerService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
