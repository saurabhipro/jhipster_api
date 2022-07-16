package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PaymentFileHeader;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentFileHeader entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentFileHeaderRepository extends JpaRepository<PaymentFileHeader, Long> {}
