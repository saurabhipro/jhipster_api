package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentAdviceDetails;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.dto.PaymentAdviceDetailsDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentAdviceDetails} and its DTO {@link PaymentAdviceDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentAdviceDetailsMapper extends EntityMapper<PaymentAdviceDetailsDTO, PaymentAdviceDetails> {
    @Mapping(target = "paymentAdvice", source = "paymentAdvice", qualifiedByName = "paymentAdviceId")
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    PaymentAdviceDetailsDTO toDto(PaymentAdviceDetails s);

    @Named("paymentAdviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentAdviceDTO toDtoPaymentAdviceId(PaymentAdvice paymentAdvice);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);
}
