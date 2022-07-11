package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PaymentFileRecon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentFileRecon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentFileReconRepository extends JpaRepository<PaymentFileRecon, Long>, JpaSpecificationExecutor<PaymentFileRecon> {}
