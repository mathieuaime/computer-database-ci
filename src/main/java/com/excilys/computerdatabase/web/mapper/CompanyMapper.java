package com.excilys.computerdatabase.web.mapper;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.web.dto.CompanyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "DTOCompanyMapper")
public interface CompanyMapper extends DtoMapper<Company, CompanyDto> {
}
