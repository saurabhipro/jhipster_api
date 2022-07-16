package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PaymentFile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentFile entity.
 */
@Repository
public interface PaymentFileRepository extends JpaRepository<PaymentFile, Long>, JpaSpecificationExecutor<PaymentFile> {
    default Optional<PaymentFile> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PaymentFile> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PaymentFile> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct paymentFile from PaymentFile paymentFile left join fetch paymentFile.bank left join fetch paymentFile.bankBranch left join fetch paymentFile.project",
        countQuery = "select count(distinct paymentFile) from PaymentFile paymentFile"
    )
    Page<PaymentFile> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct paymentFile from PaymentFile paymentFile left join fetch paymentFile.bank left join fetch paymentFile.bankBranch left join fetch paymentFile.project"
    )
    List<PaymentFile> findAllWithToOneRelationships();

    @Query(
        "select paymentFile from PaymentFile paymentFile left join fetch paymentFile.bank left join fetch paymentFile.bankBranch left join fetch paymentFile.project where paymentFile.id =:id"
    )
    Optional<PaymentFile> findOneWithToOneRelationships(@Param("id") Long id);
}
