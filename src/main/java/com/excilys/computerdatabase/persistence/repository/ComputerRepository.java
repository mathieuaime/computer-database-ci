package com.excilys.computerdatabase.persistence.repository;

import com.excilys.computerdatabase.persistence.entity.ComputerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerRepository extends JpaRepository<ComputerEntity, Long> {
  Page<ComputerEntity> findByNameStartsWithIgnoreCase(String filter, Pageable pageable);

  Page<ComputerEntity> findByCompanyId(long companyId, Pageable pageable);
}
