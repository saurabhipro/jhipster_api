package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PaymentAdvice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentAdvice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentAdviceRepository extends JpaRepository<PaymentAdvice, Long>, JpaSpecificationExecutor<PaymentAdvice> {}
