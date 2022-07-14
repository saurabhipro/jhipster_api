package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Land;
import com.melontech.landsys.domain.LandType;
import com.melontech.landsys.domain.State;
import com.melontech.landsys.domain.Unit;
import com.melontech.landsys.domain.Village;
import com.melontech.landsys.service.dto.LandDTO;
import com.melontech.landsys.service.dto.LandTypeDTO;
import com.melontech.landsys.service.dto.StateDTO;
import com.melontech.landsys.service.dto.UnitDTO;
import com.melontech.landsys.service.dto.VillageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Land} and its DTO {@link LandDTO}.
 */
@Mapper(componentModel = "spring")
public interface LandMapper extends EntityMapper<LandDTO, Land> {
    @Mapping(target = "village", source = "village", qualifiedByName = "villageName")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitName")
    @Mapping(target = "landType", source = "landType", qualifiedByName = "landTypeName")
    @Mapping(target = "state", source = "state", qualifiedByName = "stateName")
    LandDTO toDto(Land s);

    @Named("villageName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    VillageDTO toDtoVillageName(Village village);

    @Named("unitName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UnitDTO toDtoUnitName(Unit unit);

    @Named("landTypeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    LandTypeDTO toDtoLandTypeName(LandType landType);

    @Named("stateName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StateDTO toDtoStateName(State state);
}
