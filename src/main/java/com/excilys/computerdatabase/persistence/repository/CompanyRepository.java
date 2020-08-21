package com.excilys.computerdatabase.persistence.repository;

import com.excilys.computerdatabase.persistence.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
  Page<CompanyEntity> findByNameStartsWithIgnoreCase(String filter, Pageable pageable);
}
