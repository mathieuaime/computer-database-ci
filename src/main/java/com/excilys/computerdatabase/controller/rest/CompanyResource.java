package com.excilys.computerdatabase.controller.rest;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.dto.CompanyDto;
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
@RequestMapping("/api/v1/companies")
public class CompanyResource {

    private final CompanyService companyService;
    private final ComputerService computerService;

    public CompanyResource(CompanyService companyService, ComputerService computerService) {
        this.companyService = companyService;
        this.computerService = computerService;
    }

    @GetMapping
    public Page<CompanyDto> find(Pageable pageable) {
        return this.companyService.find(pageable);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CompanyDto> findByUuid(@PathVariable("uuid") String uuid) {
        return ResponseEntity.of(this.companyService.findByUuid(uuid));
    }

    @GetMapping("/{uuid}/computers")
    public Page<ComputerDto> findComputers(@PathVariable("uuid") String uuid, Pageable pageable) {
        return this.computerService.findByCompany(uuid, pageable);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@Valid @RequestBody CompanyDto company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.save(company));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CompanyDto> update(@Valid @RequestBody CompanyDto company) {
        return ResponseEntity.ok(this.companyService.save(company));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("uuid") String uuid) {
        this.companyService.delete(uuid);
        return ResponseEntity.accepted().build();
    }
}
