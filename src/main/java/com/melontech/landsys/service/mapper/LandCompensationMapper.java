package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import com.melontech.landsys.service.dto.SurveyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LandCompensation} and its DTO {@link LandCompensationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LandCompensationMapper extends EntityMapper<LandCompensationDTO, LandCompensation> {
    @Mapping(target = "khatedar", source = "khatedar", qualifiedByName = "khatedarId")
    @Mapping(target = "survey", source = "survey", qualifiedByName = "surveyId")
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    LandCompensationDTO toDto(LandCompensation s);

    @Named("khatedarId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    KhatedarDTO toDtoKhatedarId(Khatedar khatedar);

    @Named("surveyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyDTO toDtoSurveyId(Survey survey);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);
}
