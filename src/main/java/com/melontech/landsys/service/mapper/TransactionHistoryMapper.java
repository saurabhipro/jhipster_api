package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.TransactionHistory;
import com.melontech.landsys.service.dto.TransactionHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionHistory} and its DTO {@link TransactionHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionHistoryMapper extends EntityMapper<TransactionHistoryDTO, TransactionHistory> {}
