package com.excilys.computerdatabase.repository;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

  Page<Computer> findByCompany(long id, Pageable pageable);
}
