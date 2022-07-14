package com.melontech.landsys.repository;

import com.melontech.landsys.domain.Land;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Land entity.
 */
@Repository
public interface LandRepository extends JpaRepository<Land, Long> {
    default Optional<Land> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Land> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Land> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct land from Land land left join fetch land.village left join fetch land.unit left join fetch land.landType left join fetch land.state",
        countQuery = "select count(distinct land) from Land land"
    )
    Page<Land> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct land from Land land left join fetch land.village left join fetch land.unit left join fetch land.landType left join fetch land.state"
    )
    List<Land> findAllWithToOneRelationships();

    @Query(
        "select land from Land land left join fetch land.village left join fetch land.unit left join fetch land.landType left join fetch land.state where land.id =:id"
    )
    Optional<Land> findOneWithToOneRelationships(@Param("id") Long id);
}
