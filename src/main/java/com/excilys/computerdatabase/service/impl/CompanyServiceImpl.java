package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.dao.CompanyDao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("companyService")
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {
    private final CompanyDao companyDao;

    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public Page<Company> find(Pageable pageable) {
        return this.companyDao.find(pageable);
    }

    @Override
    public Optional<Company> findById(long id) {
        return this.companyDao.findById(id);
    }

    @Override
    public Page<Company> search(String filterText, Pageable pageable) {
        return this.companyDao.search(filterText, pageable);
    }
}
