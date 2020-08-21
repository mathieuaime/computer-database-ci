package com.excilys.computerdatabase.persistence.mapper;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.entity.CompanyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "EntityCompanyMapper")
public interface CompanyMapper extends EntityMapper<Company, CompanyEntity> {
}
