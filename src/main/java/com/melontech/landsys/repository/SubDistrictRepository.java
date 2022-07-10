package com.melontech.landsys.repository;

import com.melontech.landsys.domain.SubDistrict;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubDistrict entity.
 */
@Repository
public interface SubDistrictRepository extends JpaRepository<SubDistrict, Long> {
    default Optional<SubDistrict> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SubDistrict> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SubDistrict> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct subDistrict from SubDistrict subDistrict left join fetch subDistrict.district",
        countQuery = "select count(distinct subDistrict) from SubDistrict subDistrict"
    )
    Page<SubDistrict> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct subDistrict from SubDistrict subDistrict left join fetch subDistrict.district")
    List<SubDistrict> findAllWithToOneRelationships();

    @Query("select subDistrict from SubDistrict subDistrict left join fetch subDistrict.district where subDistrict.id =:id")
    Optional<SubDistrict> findOneWithToOneRelationships(@Param("id") Long id);
}
