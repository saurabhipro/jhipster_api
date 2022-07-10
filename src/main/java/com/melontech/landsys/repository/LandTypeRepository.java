package com.melontech.landsys.repository;

import com.melontech.landsys.domain.LandType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LandType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LandTypeRepository extends JpaRepository<LandType, Long> {}
