package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyDao companyDao;

    public CompanyService(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public Page<Company> find(Pageable pageable) {
        return this.companyDao.findAll(pageable);
    }

    public Optional<Company> findByUuid(String uuid) {
        return this.companyDao.findById(uuid);
    }

    public Company save(Company company) {
        return this.companyDao.save(company);
    }

    public void delete(String uuid) {
        this.companyDao.deleteById(uuid);
    }
}
