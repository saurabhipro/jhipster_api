package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Unit;
import com.melontech.landsys.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {}
