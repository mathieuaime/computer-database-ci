package com.excilys.computerdatabase.repository.impl;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.HQLComputerRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class HQLComputerRepositoryImpl implements HQLComputerRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<Computer> findByCompany_HQL(String uuid, Pageable pageable) {
    Query query = em.createQuery("from Computer c where c.company.uuid = :uuid")
        .setParameter("uuid", uuid);

    if (pageable.isPaged()) {
      query.setFirstResult((int) pageable.getOffset())
          .setMaxResults(pageable.getPageSize());
    }

    List<Computer> computers = query.getResultList();
    return new PageImpl<>(computers);
  }
}
