package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.domain.Land;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.service.dto.CitizenDTO;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.dto.LandDTO;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.dto.PaymentFileDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import com.melontech.landsys.service.dto.SurveyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentAdvice} and its DTO {@link PaymentAdviceDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentAdviceMapper extends EntityMapper<PaymentAdviceDTO, PaymentAdvice> {
    @Mapping(target = "landCompensation", source = "landCompensation", qualifiedByName = "landCompensationId")
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    @Mapping(target = "survey", source = "survey", qualifiedByName = "surveyId")
    @Mapping(target = "citizen", source = "citizen", qualifiedByName = "citizenId")
    @Mapping(target = "paymentFile", source = "paymentFile", qualifiedByName = "paymentFileId")
    @Mapping(target = "land", source = "land", qualifiedByName = "landKhasraNumber")
    PaymentAdviceDTO toDto(PaymentAdvice s);

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

    @Named("citizenId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CitizenDTO toDtoCitizenId(Citizen citizen);

    @Named("paymentFileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentFileDTO toDtoPaymentFileId(PaymentFile paymentFile);

    @Named("landKhasraNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "khasraNumber", source = "khasraNumber")
    LandDTO toDtoLandKhasraNumber(Land land);
}
