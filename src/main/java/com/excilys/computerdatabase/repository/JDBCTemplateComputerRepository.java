package com.excilys.computerdatabase.repository;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JDBCTemplateComputerRepository {

  Page<Computer> findByCompany_JDBCTemplate(String uuid, Pageable pageable);
}
