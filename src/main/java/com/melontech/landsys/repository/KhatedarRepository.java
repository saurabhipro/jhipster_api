package com.melontech.landsys.repository;

import com.melontech.landsys.domain.Khatedar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Khatedar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KhatedarRepository extends JpaRepository<Khatedar, Long>, JpaSpecificationExecutor<Khatedar> {}
