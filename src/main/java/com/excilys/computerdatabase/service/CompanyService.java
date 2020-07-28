package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.repository.CompanyRepository;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Page<CompanyDto> find(Pageable pageable) {
        return this.companyRepository.findProjectedBy(pageable, CompanyDto.class);
    }

    public Optional<CompanyDto> findById(long id) {
        return this.companyRepository.findById(id, CompanyDto.class);
    }

    public Page<CompanyDto> search(String filterText, Pageable pageable) {
        return this.companyRepository
            .findByNameStartsWithIgnoreCase(filterText, pageable, CompanyDto.class);
    }
}
