package com.melontech.landsys.repository;

import com.melontech.landsys.domain.Citizen;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Citizen entity.
 */
@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long>, JpaSpecificationExecutor<Citizen> {
    default Optional<Citizen> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Citizen> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Citizen> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct citizen from Citizen citizen left join fetch citizen.bankBranch",
        countQuery = "select count(distinct citizen) from Citizen citizen"
    )
    Page<Citizen> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct citizen from Citizen citizen left join fetch citizen.bankBranch")
    List<Citizen> findAllWithToOneRelationships();

    @Query("select citizen from Citizen citizen left join fetch citizen.bankBranch where citizen.id =:id")
    Optional<Citizen> findOneWithToOneRelationships(@Param("id") Long id);
}
