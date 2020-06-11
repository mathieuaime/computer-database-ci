package com.excilys.computerdatabase.repository;

import com.excilys.computerdatabase.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends
    JpaRepository<Computer, String>,
    JDBCTemplateComputerRepository,
    QueryDSLComputerRepository,
    CriteriaComputerRepository,
    HQLComputerRepository {

}
