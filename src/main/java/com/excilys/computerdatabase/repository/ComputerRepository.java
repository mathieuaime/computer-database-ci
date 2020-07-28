package com.excilys.computerdatabase.repository;

import com.excilys.computerdatabase.model.Computer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerRepository extends JpaRepository<Computer, Long> {
  <T> Page<T> findByCompany(long id, Pageable pageable, Class<T> projection);

  <T> Page<T> findByNameStartsWithIgnoreCase(String filter, Pageable pageable, Class<T> projection);

  <T> Page<T> findProjectedBy(Pageable pageable, Class<T> projection);

  <T> Optional<T> findById(long id, Class<T> projection);
}
