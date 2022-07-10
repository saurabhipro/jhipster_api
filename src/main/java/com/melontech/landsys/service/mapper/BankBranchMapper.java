package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.Bank;
import com.melontech.landsys.domain.BankBranch;
import com.melontech.landsys.service.dto.BankBranchDTO;
import com.melontech.landsys.service.dto.BankDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankBranch} and its DTO {@link BankBranchDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankBranchMapper extends EntityMapper<BankBranchDTO, BankBranch> {
    @Mapping(target = "bank", source = "bank", qualifiedByName = "bankName")
    BankBranchDTO toDto(BankBranch s);

    @Named("bankName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BankDTO toDtoBankName(Bank bank);
}
