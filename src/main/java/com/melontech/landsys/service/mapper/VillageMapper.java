package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.SubDistrict;
import com.melontech.landsys.domain.Village;
import com.melontech.landsys.service.dto.SubDistrictDTO;
import com.melontech.landsys.service.dto.VillageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Village} and its DTO {@link VillageDTO}.
 */
@Mapper(componentModel = "spring")
public interface VillageMapper extends EntityMapper<VillageDTO, Village> {
    @Mapping(target = "subDistrict", source = "subDistrict", qualifiedByName = "subDistrictName")
    VillageDTO toDto(Village s);

    @Named("subDistrictName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SubDistrictDTO toDtoSubDistrictName(SubDistrict subDistrict);
}
