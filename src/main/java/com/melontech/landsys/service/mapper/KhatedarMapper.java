package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.service.dto.CitizenDTO;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Khatedar} and its DTO {@link KhatedarDTO}.
 */
@Mapper(componentModel = "spring")
public interface KhatedarMapper extends EntityMapper<KhatedarDTO, Khatedar> {
    @Mapping(target = "projectLand", source = "projectLand", qualifiedByName = "projectLandId")
    @Mapping(target = "citizen", source = "citizen", qualifiedByName = "citizenId")
    @Mapping(target = "paymentAdvice", source = "paymentAdvice", qualifiedByName = "paymentAdviceId")
    KhatedarDTO toDto(Khatedar s);

    @Named("projectLandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectLandDTO toDtoProjectLandId(ProjectLand projectLand);

    @Named("citizenId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CitizenDTO toDtoCitizenId(Citizen citizen);

    @Named("paymentAdviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentAdviceDTO toDtoPaymentAdviceId(PaymentAdvice paymentAdvice);
}
