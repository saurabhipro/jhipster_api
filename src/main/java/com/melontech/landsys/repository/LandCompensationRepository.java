package com.melontech.landsys.repository;

import com.melontech.landsys.domain.LandCompensation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LandCompensation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LandCompensationRepository extends JpaRepository<LandCompensation, Long>, JpaSpecificationExecutor<LandCompensation> {}
