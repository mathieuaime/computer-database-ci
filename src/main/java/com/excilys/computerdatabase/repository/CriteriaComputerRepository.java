package com.excilys.computerdatabase.repository;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CriteriaComputerRepository {

  Page<Computer> findByCompany_Criteria(String uuid, Pageable pageable);
}
