package com.melontech.landsys.repository;

import com.melontech.landsys.domain.Survey;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Survey entity.
 */
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>, JpaSpecificationExecutor<Survey> {
    default Optional<Survey> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Survey> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Survey> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct survey from Survey survey left join fetch survey.village",
        countQuery = "select count(distinct survey) from Survey survey"
    )
    Page<Survey> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct survey from Survey survey left join fetch survey.village")
    List<Survey> findAllWithToOneRelationships();

    @Query("select survey from Survey survey left join fetch survey.village where survey.id =:id")
    Optional<Survey> findOneWithToOneRelationships(@Param("id") Long id);
}
