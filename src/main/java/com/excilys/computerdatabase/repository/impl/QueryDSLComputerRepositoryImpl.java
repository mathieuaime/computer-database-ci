package com.excilys.computerdatabase.repository.impl;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.QComputer;
import com.excilys.computerdatabase.repository.QueryDSLComputerRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class QueryDSLComputerRepositoryImpl implements QueryDSLComputerRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<Computer> findByCompany_QueryDSL(String uuid, Pageable pageable) {
    JPAQuery<Computer> query = new JPAQuery<>(em);

    QComputer qComputer = QComputer.computer;

    query.from(qComputer)
        .where(qComputer.company.uuid.eq(uuid));

    if (pageable.isPaged()) {
      query.limit(pageable.getPageSize())
          .offset(pageable.getOffset());

      PathBuilder<Computer> orderByExpression = new PathBuilder<>(Computer.class, "computer");

      for (Sort.Order o : pageable.getSort()) {
        PathBuilder<Object> path = orderByExpression.get(o.getProperty());
        query.orderBy(new OrderSpecifier(Order.valueOf(o.getDirection().name()), path));
      }
    }

    return new PageImpl<>(query.fetch());
  }
}
