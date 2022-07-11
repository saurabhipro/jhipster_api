package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.District;
import com.melontech.landsys.domain.State;
import com.melontech.landsys.service.dto.DistrictDTO;
import com.melontech.landsys.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring")
public interface StateMapper extends EntityMapper<StateDTO, State> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    StateDTO toDto(State s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
