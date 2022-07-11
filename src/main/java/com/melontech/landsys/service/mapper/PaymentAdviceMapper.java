package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.dto.PaymentFileDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentAdvice} and its DTO {@link PaymentAdviceDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentAdviceMapper extends EntityMapper<PaymentAdviceDTO, PaymentAdvice> {
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    @Mapping(target = "landCompensation", source = "landCompensation", qualifiedByName = "landCompensationId")
    @Mapping(target = "paymentFile", source = "paymentFile", qualifiedByName = "paymentFileId")
    PaymentAdviceDTO toDto(PaymentAdvice s);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);

    @Named("landCompensationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LandCompensationDTO toDtoLandCompensationId(LandCompensation landCompensation);

    @Named("paymentFileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentFileDTO toDtoPaymentFileId(PaymentFile paymentFile);
}
