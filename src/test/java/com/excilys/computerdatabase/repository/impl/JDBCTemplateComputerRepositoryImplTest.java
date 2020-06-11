package com.excilys.computerdatabase.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.JDBCTemplateComputerRepository;
import java.io.FileInputStream;
import javax.sql.DataSource;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JDBCTemplateComputerRepositoryImplTest extends DataSourceBasedDBTestCase {

  private final JDBCTemplateComputerRepository repository;
  private final JdbcDataSource dataSource;

  public JDBCTemplateComputerRepositoryImplTest() {
    this.dataSource = new JdbcDataSource();
    this.dataSource.setURL(
        "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:dbunit/schema.sql'");
    this.dataSource.setUser("sa");
    this.dataSource.setPassword("");

    NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.dataSource);
    this.repository = new JDBCTemplateComputerRepositoryImpl(template);
  }

  @Override
  protected DataSource getDataSource() {
    return this.dataSource;
  }

  @Override
  protected IDataSet getDataSet() throws Exception {
    return new FlatXmlDataSetBuilder()
        .build(new FileInputStream("src/test/resources/dbunit/dataset.xml"));
  }

  public void testFindByCompanyName_withUnpaged() {
    Page<Computer> computers = repository
        .findByCompany_JDBCTemplate("unknown-company", Pageable.unpaged());

    assertThat(computers).isEmpty();
  }

  public void testFindByCompanyName_withUnknownCompany() {
    Pageable pageable = PageRequest.of(0, 20);

    Page<Computer> computers = repository
        .findByCompany_JDBCTemplate("company-uuid-unknown", pageable);

    assertThat(computers).isEmpty();
  }

  public void testFindByCompanyName_withKnownCompany() {
    Pageable pageable = PageRequest.of(0, 20);

    Page<Computer> computers = repository.findByCompany_JDBCTemplate("company-uuid-1", pageable);

    assertThat(computers)
        .extracting(Computer::getUuid)
        .containsExactly("computer-uuid-1", "computer-uuid-2");
  }
}