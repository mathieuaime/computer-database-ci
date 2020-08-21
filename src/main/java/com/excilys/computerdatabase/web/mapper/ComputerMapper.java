package com.excilys.computerdatabase.web.mapper;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.web.dto.ComputerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "DTOComputerMapper")
public interface ComputerMapper extends DtoMapper<Computer, ComputerDto> {
}
