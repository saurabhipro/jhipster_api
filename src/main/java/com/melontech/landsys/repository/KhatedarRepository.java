package com.melontech.landsys.repository;

import com.melontech.landsys.domain.Khatedar;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Khatedar entity.
 */
@Repository
public interface KhatedarRepository extends JpaRepository<Khatedar, Long>, JpaSpecificationExecutor<Khatedar> {
    default Optional<Khatedar> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Khatedar> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Khatedar> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct khatedar from Khatedar khatedar left join fetch khatedar.citizen",
        countQuery = "select count(distinct khatedar) from Khatedar khatedar"
    )
    Page<Khatedar> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct khatedar from Khatedar khatedar left join fetch khatedar.citizen")
    List<Khatedar> findAllWithToOneRelationships();

    @Query("select khatedar from Khatedar khatedar left join fetch khatedar.citizen where khatedar.id =:id")
    Optional<Khatedar> findOneWithToOneRelationships(@Param("id") Long id);
}
