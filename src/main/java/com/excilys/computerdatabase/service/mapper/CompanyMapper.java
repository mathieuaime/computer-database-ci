package com.excilys.computerdatabase.service.mapper;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<Company, CompanyDto> {

}
