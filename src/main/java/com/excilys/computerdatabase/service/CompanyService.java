package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.repository.CompanyRepository;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.excilys.computerdatabase.service.mapper.CompanyMapper;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Page<CompanyDto> find(Pageable pageable) {
        return this.companyRepository.findAll(pageable).map(companyMapper::toDto);
    }

    public Optional<CompanyDto> findByUuid(String uuid) {
        return this.companyRepository.findById(uuid).map(companyMapper::toDto);
    }

    public CompanyDto save(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        company = this.companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    public void delete(String uuid) {
        this.companyRepository.deleteById(uuid);
    }
}
