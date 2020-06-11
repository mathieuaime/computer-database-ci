package com.excilys.computerdatabase.service.fake;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.repository.CompanyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FakeCompanyRepository implements CompanyRepository {

  private final Company company;

  public FakeCompanyRepository(Company company) {
    this.company = company;
  }

  @Override
  public List<Company> findAll() {
    return null;
  }

  @Override
  public List<Company> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Company> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Company> findAllById(Iterable<String> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(String s) {

  }

  @Override
  public void delete(Company company) {

  }

  @Override
  public void deleteAll(Iterable<? extends Company> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Company> S save(S s) {
    return null;
  }

  @Override
  public <S extends Company> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Company> findById(String s) {
    return s.equals(company.getUuid()) ? Optional.of(company) : Optional.empty();
  }

  @Override
  public boolean existsById(String s) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Company> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Company> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Company getOne(String s) {
    return null;
  }

  @Override
  public <S extends Company> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Company> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Company> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Company> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Company> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Company> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public Page<Company> findByName(String name, Pageable pageable) {
    return null;
  }
}
