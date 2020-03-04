package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.CompanyService;
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
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final ComputerService computerService;

    public CompanyController(CompanyService companyService, ComputerService computerService) {
        this.companyService = companyService;
        this.computerService = computerService;
    }

    @GetMapping
    public Page<Company> find(Pageable pageable) {
        return this.companyService.find(pageable);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Company> findByUuid(@PathVariable("uuid") String uuid) {
        return ResponseEntity.of(this.companyService.findByUuid(uuid));
    }

    @GetMapping("/{uuid}/computers")
    public Page<Computer> findComputers(@PathVariable("uuid") String uuid, Pageable pageable) {
        return this.computerService.findByCompany(uuid, pageable);
    }

    @PostMapping
    public ResponseEntity<Company> create(@RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.save(company));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Company> update(@RequestBody Company company) {
        return ResponseEntity.ok(this.companyService.save(company));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("uuid") String uuid) {
        this.companyService.delete(uuid);
        return ResponseEntity.accepted().build();
    }
}
