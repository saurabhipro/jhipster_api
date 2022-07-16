package com.melontech.landsys.repository;

import com.melontech.landsys.domain.CreatePaymentFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CreatePaymentFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreatePaymentFileRepository extends JpaRepository<CreatePaymentFile, Long> {}
