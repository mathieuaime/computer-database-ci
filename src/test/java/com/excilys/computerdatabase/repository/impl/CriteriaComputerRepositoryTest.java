package com.excilys.computerdatabase.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.ComputerRepository;
import com.excilys.computerdatabase.repository.CriteriaComputerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

@DataJpaTest
class CriteriaComputerRepositoryTest {

  @Autowired
  private ComputerRepository repository;

  @Test
  void findByCompanyName_withUnpaged() {
    Page<Computer> computers = repository.findByCompany_Criteria("unknown-company", Pageable.unpaged());

    assertThat(computers).isEmpty();
  }

  @Test
  void findByCompanyName_withUnknownCompany() {
    Pageable pageable = PageRequest.of(0, 20);

    Page<Computer> computers = repository.findByCompany_Criteria("company-uuid-unknown", pageable);

    assertThat(computers).isEmpty();
  }

  @Test
  void findByCompanyName_withKnownCompany() {
    Pageable pageable = PageRequest.of(0, 20);

    Page<Computer> computers = repository.findByCompany_Criteria("company-uuid-1", pageable);

    assertThat(computers)
        .extracting(Computer::getUuid)
        .containsExactly("computer-uuid-1", "computer-uuid-2");
  }


  @Test
  void findByCompanyName_withPagination() {
    Pageable pageable = PageRequest.of(1, 1);

    Page<Computer> computers = repository.findByCompany_Criteria("company-uuid-1", pageable);

    assertThat(computers)
        .extracting(Computer::getUuid)
        .containsExactly("computer-uuid-2");
  }

  @Test
  void findByCompanyName_withSortDesc() {
    Pageable pageable = PageRequest.of(0, 20, Direction.DESC, "name");

    Page<Computer> computers = repository.findByCompany_Criteria("company-uuid-1", pageable);

    assertThat(computers)
        .extracting(Computer::getUuid)
        .containsExactly("computer-uuid-2", "computer-uuid-1");
  }
}