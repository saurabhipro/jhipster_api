package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.PaymentFileHeader;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.service.dto.PaymentFileHeaderDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentFileHeader} and its DTO {@link PaymentFileHeaderDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentFileHeaderMapper extends EntityMapper<PaymentFileHeaderDTO, PaymentFileHeader> {
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    PaymentFileHeaderDTO toDto(PaymentFileHeader s);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);
}
