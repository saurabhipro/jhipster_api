package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PaymentFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentFileRepository extends JpaRepository<PaymentFile, Long>, JpaSpecificationExecutor<PaymentFile> {}
