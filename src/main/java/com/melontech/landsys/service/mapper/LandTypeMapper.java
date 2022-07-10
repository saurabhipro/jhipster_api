package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.LandType;
import com.melontech.landsys.service.dto.LandTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LandType} and its DTO {@link LandTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LandTypeMapper extends EntityMapper<LandTypeDTO, LandType> {}
