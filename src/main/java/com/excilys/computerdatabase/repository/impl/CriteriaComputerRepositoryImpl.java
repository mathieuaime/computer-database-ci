package com.excilys.computerdatabase.repository.impl;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.CriteriaComputerRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CriteriaComputerRepositoryImpl implements CriteriaComputerRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<Computer> findByCompany_Criteria(String uuid, Pageable pageable) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Computer> query = cb.createQuery(Computer.class);

    Root<Computer> computer = query.from(Computer.class);
    Join<Computer, Company> company = computer.join("company");

    query.select(computer)
        .where(cb.equal(company.get("uuid"), uuid));

    for (Sort.Order o : pageable.getSort()) {
      query.orderBy(new OrderImpl(computer.get(o.getProperty()), o.isAscending()));
    }

    TypedQuery<Computer> typedQuery = em.createQuery(query);

    if (pageable.isPaged()) {
      typedQuery.setFirstResult((int) pageable.getOffset())
          .setMaxResults(pageable.getPageSize());
    }
    List<Computer> result = typedQuery.getResultList();

    return new PageImpl<>(result);
  }
}
