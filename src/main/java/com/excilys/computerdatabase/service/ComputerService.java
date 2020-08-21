package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ComputerService {
  Page<Computer> find(Pageable pageable);

  Optional<Computer> findById(long id);

  Page<Computer> findByCompany(long id, Pageable pageable);

  boolean exist(long id);

  long count();

  Page<Computer> search(String filterText, Pageable pageable);

  Computer save(Computer computerDto);

  void deleteById(long id);
}
