package com.excilys.computerdatabase.service.dao;

import com.excilys.computerdatabase.model.Company;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyDao {
  Page<Company> find(Pageable pageable);

  Optional<Company> findById(long id);

  Page<Company> search(String filter, Pageable pageable);
}
