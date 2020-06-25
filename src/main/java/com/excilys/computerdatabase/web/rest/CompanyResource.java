package com.excilys.computerdatabase.web.rest;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findByUuid(@PathVariable("id") long id) {
        return ResponseEntity.of(this.companyService.findById(id));
    }

    @GetMapping("/{id}/computers")
    public Page<ComputerDto> findComputers(@PathVariable("id") long id, Pageable pageable) {
        return this.computerService.findByCompany(id, pageable);
    }
}
