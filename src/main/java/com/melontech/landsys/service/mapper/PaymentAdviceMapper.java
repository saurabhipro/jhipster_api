package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import com.melontech.landsys.service.dto.SurveyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentAdvice} and its DTO {@link PaymentAdviceDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentAdviceMapper extends EntityMapper<PaymentAdviceDTO, PaymentAdvice> {
    @Mapping(target = "khatedar", source = "khatedar", qualifiedByName = "khatedarId")
    @Mapping(target = "landCompensation", source = "landCompensation", qualifiedByName = "landCompensationId")
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    @Mapping(target = "survey", source = "survey", qualifiedByName = "surveyId")
    PaymentAdviceDTO toDto(PaymentAdvice s);

    @Named("khatedarId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    KhatedarDTO toDtoKhatedarId(Khatedar khatedar);

    @Named("landCompensationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LandCompensationDTO toDtoLandCompensationId(LandCompensation landCompensation);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);

    @Named("surveyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyDTO toDtoSurveyId(Survey survey);
}
