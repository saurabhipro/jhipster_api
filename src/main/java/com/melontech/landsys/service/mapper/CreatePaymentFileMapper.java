package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.CreatePaymentFile;
import com.melontech.landsys.service.dto.CreatePaymentFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreatePaymentFile} and its DTO {@link CreatePaymentFileDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreatePaymentFileMapper extends EntityMapper<CreatePaymentFileDTO, CreatePaymentFile> {}
