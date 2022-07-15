package com.melontech.landsys.repository;

import com.melontech.landsys.domain.LandCompensation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LandCompensation entity.
 */
@Repository
public interface LandCompensationRepository extends JpaRepository<LandCompensation, Long>, JpaSpecificationExecutor<LandCompensation> {
    default Optional<LandCompensation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LandCompensation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LandCompensation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct landCompensation from LandCompensation landCompensation left join fetch landCompensation.village",
        countQuery = "select count(distinct landCompensation) from LandCompensation landCompensation"
    )
    Page<LandCompensation> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct landCompensation from LandCompensation landCompensation left join fetch landCompensation.village")
    List<LandCompensation> findAllWithToOneRelationships();

    @Query(
        "select landCompensation from LandCompensation landCompensation left join fetch landCompensation.village where landCompensation.id =:id"
    )
    Optional<LandCompensation> findOneWithToOneRelationships(@Param("id") Long id);
}
