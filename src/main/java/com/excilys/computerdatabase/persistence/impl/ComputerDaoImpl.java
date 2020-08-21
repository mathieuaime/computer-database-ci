package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.repository.ComputerRepository;
import com.excilys.computerdatabase.persistence.entity.ComputerEntity;
import com.excilys.computerdatabase.persistence.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.dao.ComputerDao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ComputerDaoImpl implements ComputerDao {
  private final ComputerRepository repository;
  private final ComputerMapper mapper;

  public ComputerDaoImpl(ComputerRepository repository, ComputerMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Page<Computer> find(Pageable pageable) {
    return repository.findAll(pageable).map(mapper::toModel);
  }

  @Override
  public Optional<Computer> findById(long id) {
    return repository.findById(id).map(mapper::toModel);
  }

  @Override
  public Page<Computer> search(String filter, Pageable pageable) {
    return repository.findByNameStartsWithIgnoreCase(filter, pageable).map(mapper::toModel);
  }

  @Override
  public Page<Computer> findByCompanyId(long companyId, Pageable pageable) {
    return repository.findByCompanyId(companyId, pageable).map(mapper::toModel);
  }

  @Override
  public Computer save(Computer computer) {
    ComputerEntity entity = mapper.toEntity(computer);
    entity = repository.save(entity);
    return mapper.toModel(entity);
  }

  @Override
  public long count() {
    return repository.count();
  }

  @Override
  public boolean existsById(long id) {
    return repository.existsById(id);
  }

  @Override
  public void deleteById(long id) {
    repository.findById(id).ifPresent(repository::delete);
  }
}
