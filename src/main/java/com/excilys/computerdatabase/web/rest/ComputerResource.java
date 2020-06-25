package com.excilys.computerdatabase.web.rest;

import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.dto.ComputerDto;
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

    public ComputerResource(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping
    public Page<ComputerDto> find(Pageable pageable) {
        return this.computerService.find(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComputerDto> findById(@PathVariable("id") long id) {
        return ResponseEntity.of(this.computerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ComputerDto> create(@Valid @RequestBody ComputerDto computerDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(this.computerService.save(computerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComputerDto> update(@PathVariable("id") long id,
        @Valid @RequestBody ComputerDto computerDto) {
        computerDto.setId(id);
        return ResponseEntity.ok(this.computerService.save(computerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("id") long id) {
        this.computerService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
