package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.District;
import com.melontech.landsys.domain.SubDistrict;
import com.melontech.landsys.service.dto.DistrictDTO;
import com.melontech.landsys.service.dto.SubDistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubDistrict} and its DTO {@link SubDistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubDistrictMapper extends EntityMapper<SubDistrictDTO, SubDistrict> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtName")
    SubDistrictDTO toDto(SubDistrict s);

    @Named("districtName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DistrictDTO toDtoDistrictName(District district);
}
