package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import com.melontech.landsys.service.dto.SurveyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Survey} and its DTO {@link SurveyDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveyMapper extends EntityMapper<SurveyDTO, Survey> {
    @Mapping(target = "khatedar", source = "khatedar", qualifiedByName = "khatedarId")
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    SurveyDTO toDto(Survey s);

    @Named("khatedarId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    KhatedarDTO toDtoKhatedarId(Khatedar khatedar);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);
}
