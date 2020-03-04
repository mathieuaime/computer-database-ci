package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.ComputerService;
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
@RequestMapping("/computer")
public class ComputerController {
    private final ComputerService computerService;

    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping
    public Page<Computer> find(Pageable pageable) {
        return this.computerService.find(pageable);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Computer> findByUuid(@PathVariable("uuid") String uuid) {
        return ResponseEntity.of(this.computerService.findByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<Computer> create(@RequestBody Computer computer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.computerService.save(computer));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Computer> update(@RequestBody Computer computer) {
        return ResponseEntity.ok(this.computerService.save(computer));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("uuid") String uuid) {
        this.computerService.delete(uuid);
        return ResponseEntity.accepted().build();
    }
}
