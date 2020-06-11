package com.excilys.computerdatabase.repository;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HQLComputerRepository {

  Page<Computer> findByCompany_HQL(String uuid, Pageable pageable);
}
