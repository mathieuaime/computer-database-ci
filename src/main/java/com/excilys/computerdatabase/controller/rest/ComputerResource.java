package com.excilys.computerdatabase.controller.rest;

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

    @GetMapping("/{uuid}")
    public ResponseEntity<ComputerDto> findByUuid(@PathVariable("uuid") String uuid) {
        return ResponseEntity.of(this.computerService.findByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<ComputerDto> create(@Valid @RequestBody ComputerDto computerDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(this.computerService.save(computerDto));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ComputerDto> update(@Valid @RequestBody ComputerDto computerDto) {
        return ResponseEntity.ok(this.computerService.save(computerDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("uuid") String uuid) {
        this.computerService.delete(uuid);
        return ResponseEntity.accepted().build();
    }
}
