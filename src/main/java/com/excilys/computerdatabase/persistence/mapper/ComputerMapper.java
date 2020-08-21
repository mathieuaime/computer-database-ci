package com.excilys.computerdatabase.persistence.mapper;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.entity.ComputerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "EntityComputerMapper")
public interface ComputerMapper extends EntityMapper<Computer, ComputerEntity> {
}
