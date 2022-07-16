package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Bank;
import com.melontech.landsys.domain.BankBranch;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.domain.PaymentFileHeader;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.service.dto.BankBranchDTO;
import com.melontech.landsys.service.dto.BankDTO;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.dto.PaymentFileDTO;
import com.melontech.landsys.service.dto.PaymentFileHeaderDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import com.melontech.landsys.service.dto.SurveyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentFile} and its DTO {@link PaymentFileDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentFileMapper extends EntityMapper<PaymentFileDTO, PaymentFile> {
    @Mapping(target = "khatedar", source = "khatedar", qualifiedByName = "khatedarId")
    @Mapping(target = "paymentAdvice", source = "paymentAdvice", qualifiedByName = "paymentAdviceId")
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    @Mapping(target = "survey", source = "survey", qualifiedByName = "surveyId")
    @Mapping(target = "bank", source = "bank", qualifiedByName = "bankName")
    @Mapping(target = "bankBranch", source = "bankBranch", qualifiedByName = "bankBranchName")
    @Mapping(target = "landCompensation", source = "landCompensation", qualifiedByName = "landCompensationId")
    @Mapping(target = "paymentFileHeader", source = "paymentFileHeader", qualifiedByName = "paymentFileHeaderId")
    PaymentFileDTO toDto(PaymentFile s);

    @Named("khatedarId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    KhatedarDTO toDtoKhatedarId(Khatedar khatedar);

    @Named("paymentAdviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentAdviceDTO toDtoPaymentAdviceId(PaymentAdvice paymentAdvice);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);

    @Named("surveyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyDTO toDtoSurveyId(Survey survey);

    @Named("bankName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BankDTO toDtoBankName(Bank bank);

    @Named("bankBranchName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BankBranchDTO toDtoBankBranchName(BankBranch bankBranch);

    @Named("landCompensationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LandCompensationDTO toDtoLandCompensationId(LandCompensation landCompensation);

    @Named("paymentFileHeaderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentFileHeaderDTO toDtoPaymentFileHeaderId(PaymentFileHeader paymentFileHeader);
}
