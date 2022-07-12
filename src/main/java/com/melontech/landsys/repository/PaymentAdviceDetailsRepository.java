package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PaymentAdviceDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentAdviceDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentAdviceDetailsRepository
    extends JpaRepository<PaymentAdviceDetails, Long>, JpaSpecificationExecutor<PaymentAdviceDetails> {}
