package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.District;
import com.melontech.landsys.service.dto.DistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {}
