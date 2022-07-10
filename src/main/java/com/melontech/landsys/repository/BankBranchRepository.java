package com.melontech.landsys.repository;

import com.melontech.landsys.domain.BankBranch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankBranch entity.
 */
@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Long> {
    default Optional<BankBranch> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BankBranch> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BankBranch> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct bankBranch from BankBranch bankBranch left join fetch bankBranch.bank",
        countQuery = "select count(distinct bankBranch) from BankBranch bankBranch"
    )
    Page<BankBranch> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct bankBranch from BankBranch bankBranch left join fetch bankBranch.bank")
    List<BankBranch> findAllWithToOneRelationships();

    @Query("select bankBranch from BankBranch bankBranch left join fetch bankBranch.bank where bankBranch.id =:id")
    Optional<BankBranch> findOneWithToOneRelationships(@Param("id") Long id);
}
