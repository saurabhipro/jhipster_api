package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.District;
import com.melontech.landsys.domain.State;
import com.melontech.landsys.service.dto.DistrictDTO;
import com.melontech.landsys.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "state", source = "state", qualifiedByName = "stateName")
    DistrictDTO toDto(District s);

    @Named("stateName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StateDTO toDtoStateName(State state);
}
