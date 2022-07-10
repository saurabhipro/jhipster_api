package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Bank;
import com.melontech.landsys.service.dto.BankDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bank} and its DTO {@link BankDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankMapper extends EntityMapper<BankDTO, Bank> {}
