package com.excilys.computerdatabase.web.mapper;

public interface DtoMapper<M, D> {
  D toDto(M model);

  M toModel(D dto);
}
