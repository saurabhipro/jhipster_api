package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.BankBranch;
import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.service.dto.BankBranchDTO;
import com.melontech.landsys.service.dto.CitizenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Citizen} and its DTO {@link CitizenDTO}.
 */
@Mapper(componentModel = "spring")
public interface CitizenMapper extends EntityMapper<CitizenDTO, Citizen> {
    @Mapping(target = "bankBranch", source = "bankBranch", qualifiedByName = "bankBranchName")
    CitizenDTO toDto(Citizen s);

    @Named("bankBranchName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BankBranchDTO toDtoBankBranchName(BankBranch bankBranch);
}
