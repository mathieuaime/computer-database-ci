package com.excilys.computerdatabase.repository.impl;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.JDBCTemplateComputerRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCTemplateComputerRepositoryImpl implements JDBCTemplateComputerRepository {

  private static final String QUERY = "select uuid, name from computer where company_uuid = :uuid";

  private final NamedParameterJdbcTemplate template;
  private final ComputerMapper rowMapper;

  public JDBCTemplateComputerRepositoryImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
    this.rowMapper = new ComputerMapper();
  }

  @Override
  public Page<Computer> findByCompany_JDBCTemplate(String uuid, Pageable pageable) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource("uuid", uuid);

    List<Computer> computers = template.query(QUERY, parameterSource, rowMapper);

    return new PageImpl<>(computers);
  }

  static class ComputerMapper implements RowMapper<Computer> {

    @Override
    public Computer mapRow(ResultSet resultSet, int i) throws SQLException {
      String uuid = resultSet.getString("uuid");
      String name = resultSet.getString("name");

      return new Computer().setUuid(uuid).setName(name);
    }
  }
}
