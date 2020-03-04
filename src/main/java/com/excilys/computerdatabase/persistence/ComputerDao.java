package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerDao extends JpaRepository<Computer, String> {
    Page<Computer> findByCompany(String company, Pageable pageable);
}
