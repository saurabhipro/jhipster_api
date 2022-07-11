package com.melontech.landsys.repository;

import com.melontech.landsys.domain.ProjectLand;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectLand entity.
 */
@Repository
public interface ProjectLandRepository extends JpaRepository<ProjectLand, Long> {
    default Optional<ProjectLand> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ProjectLand> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ProjectLand> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct projectLand from ProjectLand projectLand left join fetch projectLand.project left join fetch projectLand.land",
        countQuery = "select count(distinct projectLand) from ProjectLand projectLand"
    )
    Page<ProjectLand> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct projectLand from ProjectLand projectLand left join fetch projectLand.project left join fetch projectLand.land")
    List<ProjectLand> findAllWithToOneRelationships();

    @Query(
        "select projectLand from ProjectLand projectLand left join fetch projectLand.project left join fetch projectLand.land where projectLand.id =:id"
    )
    Optional<ProjectLand> findOneWithToOneRelationships(@Param("id") Long id);
}
