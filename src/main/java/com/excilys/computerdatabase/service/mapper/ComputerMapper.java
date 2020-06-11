package com.excilys.computerdatabase.service.mapper;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComputerMapper extends EntityMapper<Computer, ComputerDto> {

}
