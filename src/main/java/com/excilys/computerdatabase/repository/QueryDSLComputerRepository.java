package com.excilys.computerdatabase.repository;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDSLComputerRepository {

  Page<Computer> findByCompany_QueryDSL(String uuid, Pageable pageable);
}
