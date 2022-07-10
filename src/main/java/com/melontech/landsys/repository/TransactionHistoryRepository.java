package com.melontech.landsys.repository;

import com.melontech.landsys.domain.TransactionHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TransactionHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionHistoryRepository
    extends JpaRepository<TransactionHistory, Long>, JpaSpecificationExecutor<TransactionHistory> {}
