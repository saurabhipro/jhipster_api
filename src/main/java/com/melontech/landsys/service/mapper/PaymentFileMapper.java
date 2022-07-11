package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.service.dto.PaymentFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentFile} and its DTO {@link PaymentFileDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentFileMapper extends EntityMapper<PaymentFileDTO, PaymentFile> {}
