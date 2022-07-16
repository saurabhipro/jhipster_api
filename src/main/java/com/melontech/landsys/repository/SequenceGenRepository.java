package com.melontech.landsys.repository;

import com.melontech.landsys.domain.SequenceGen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SequenceGen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceGenRepository extends JpaRepository<SequenceGen, Long>, JpaSpecificationExecutor<SequenceGen> {}
