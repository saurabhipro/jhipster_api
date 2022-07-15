package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.domain.Village;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import com.melontech.landsys.service.dto.SurveyDTO;
import com.melontech.landsys.service.dto.VillageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LandCompensation} and its DTO {@link LandCompensationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LandCompensationMapper extends EntityMapper<LandCompensationDTO, LandCompensation> {
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    @Mapping(target = "survey", source = "survey", qualifiedByName = "surveyId")
    @Mapping(target = "village", source = "village", qualifiedByName = "villageName")
    LandCompensationDTO toDto(LandCompensation s);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);

    @Named("surveyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyDTO toDtoSurveyId(Survey survey);

    @Named("villageName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    VillageDTO toDtoVillageName(Village village);
}
