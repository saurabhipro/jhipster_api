package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Land;
import com.melontech.landsys.domain.NoticeStatusInfo;
import com.melontech.landsys.domain.Project;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.service.dto.LandDTO;
import com.melontech.landsys.service.dto.NoticeStatusInfoDTO;
import com.melontech.landsys.service.dto.ProjectDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectLand} and its DTO {@link ProjectLandDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectLandMapper extends EntityMapper<ProjectLandDTO, ProjectLand> {
    @Mapping(target = "land", source = "land", qualifiedByName = "landKhasraNumber")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectName")
    @Mapping(target = "noticeStatusInfo", source = "noticeStatusInfo", qualifiedByName = "noticeStatusInfoId")
    ProjectLandDTO toDto(ProjectLand s);

    @Named("landKhasraNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "khasraNumber", source = "khasraNumber")
    LandDTO toDtoLandKhasraNumber(Land land);

    @Named("projectName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProjectDTO toDtoProjectName(Project project);

    @Named("noticeStatusInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NoticeStatusInfoDTO toDtoNoticeStatusInfoId(NoticeStatusInfo noticeStatusInfo);
}
