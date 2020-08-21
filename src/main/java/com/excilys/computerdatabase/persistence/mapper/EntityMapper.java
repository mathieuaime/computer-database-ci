package com.excilys.computerdatabase.persistence.mapper;

public interface EntityMapper<M, E> {
  E toEntity(M model);

  M toModel(E entity);
}
