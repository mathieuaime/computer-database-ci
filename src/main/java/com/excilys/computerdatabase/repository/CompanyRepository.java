package com.excilys.computerdatabase.repository;

import com.excilys.computerdatabase.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

  Page<Company> findByName(String name, Pageable pageable);
}
