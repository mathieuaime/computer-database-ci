package com.excilys.computerdatabase.service.dao;

import com.excilys.computerdatabase.model.Computer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComputerDao {
  Page<Computer> find(Pageable pageable);

  Optional<Computer> findById(long id);

  Page<Computer> search(String filter, Pageable pageable);

  Page<Computer> findByCompanyId(long companyId, Pageable pageable);

  Computer save(Computer computerEntity);

  long count();

  boolean existsById(long id);

  void deleteById(long id);
}
