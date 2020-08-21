package com.excilys.computerdatabase.web.rest;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.web.dto.CompanyDto;
import com.excilys.computerdatabase.web.dto.ComputerDto;
import com.excilys.computerdatabase.web.mapper.CompanyMapper;
import com.excilys.computerdatabase.web.mapper.ComputerMapper;
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
    private final CompanyMapper companyMapper;
    private final ComputerService computerService;
    private final ComputerMapper computerMapper;

    public CompanyResource(CompanyService companyService, CompanyMapper companyMapper,
        ComputerService computerService, ComputerMapper computerMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    @GetMapping
    public Page<CompanyDto> find(Pageable pageable) {
        return this.companyService.find(pageable).map(companyMapper::toDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable("id") long id) {
        return ResponseEntity.of(this.companyService.findById(id).map(companyMapper::toDto));
    }

    @GetMapping("/{id}/computers")
    public Page<ComputerDto> findComputers(@PathVariable("id") long id, Pageable pageable) {
        return this.computerService.findByCompany(id, pageable).map(computerMapper::toDto);
    }
}
