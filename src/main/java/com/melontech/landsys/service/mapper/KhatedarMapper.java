package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.NoticeStatusInfo;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.service.dto.CitizenDTO;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.dto.NoticeStatusInfoDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Khatedar} and its DTO {@link KhatedarDTO}.
 */
@Mapper(componentModel = "spring")
public interface KhatedarMapper extends EntityMapper<KhatedarDTO, Khatedar> {
    @Mapping(target = "citizen", source = "citizen", qualifiedByName = "citizenName")
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    @Mapping(target = "noticeStatusInfo", source = "noticeStatusInfo", qualifiedByName = "noticeStatusInfoId")
    KhatedarDTO toDto(Khatedar s);

    @Named("citizenName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CitizenDTO toDtoCitizenName(Citizen citizen);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);

    @Named("noticeStatusInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NoticeStatusInfoDTO toDtoNoticeStatusInfoId(NoticeStatusInfo noticeStatusInfo);
}
