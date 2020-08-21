package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.dao.CompanyDao;
import com.excilys.computerdatabase.persistence.repository.CompanyRepository;
import com.excilys.computerdatabase.persistence.mapper.CompanyMapper;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDaoImpl implements CompanyDao {
  private final CompanyRepository repository;
  private final CompanyMapper mapper;

  public CompanyDaoImpl(CompanyRepository repository, CompanyMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Page<Company> find(Pageable pageable) {
    return repository.findAll(pageable).map(mapper::toModel);
  }

  @Override
  public Optional<Company> findById(long id) {
    return repository.findById(id).map(mapper::toModel);
  }

  @Override
  public Page<Company> search(String filter, Pageable pageable) {
    return repository.findByNameStartsWithIgnoreCase(filter, pageable).map(mapper::toModel);
  }
}
