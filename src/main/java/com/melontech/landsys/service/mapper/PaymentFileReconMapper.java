package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentFileRecon;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.dto.PaymentFileReconDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentFileRecon} and its DTO {@link PaymentFileReconDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentFileReconMapper extends EntityMapper<PaymentFileReconDTO, PaymentFileRecon> {
    @Mapping(target = "paymentAdvice", source = "paymentAdvice", qualifiedByName = "paymentAdviceId")
    PaymentFileReconDTO toDto(PaymentFileRecon s);

    @Named("paymentAdviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentAdviceDTO toDtoPaymentAdviceId(PaymentAdvice paymentAdvice);
}
