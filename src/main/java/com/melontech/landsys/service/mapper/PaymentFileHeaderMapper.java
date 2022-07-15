package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.PaymentFileHeader;
import com.melontech.landsys.domain.Project;
import com.melontech.landsys.service.dto.PaymentFileHeaderDTO;
import com.melontech.landsys.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentFileHeader} and its DTO {@link PaymentFileHeaderDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentFileHeaderMapper extends EntityMapper<PaymentFileHeaderDTO, PaymentFileHeader> {
    @Mapping(target = "project", source = "project", qualifiedByName = "projectName")
    PaymentFileHeaderDTO toDto(PaymentFileHeader s);

    @Named("projectName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProjectDTO toDtoProjectName(Project project);
}
