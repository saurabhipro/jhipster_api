package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PaymentFileHeader;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentFileHeader entity.
 */
@Repository
public interface PaymentFileHeaderRepository extends JpaRepository<PaymentFileHeader, Long> {
    default Optional<PaymentFileHeader> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PaymentFileHeader> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PaymentFileHeader> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct paymentFileHeader from PaymentFileHeader paymentFileHeader left join fetch paymentFileHeader.project",
        countQuery = "select count(distinct paymentFileHeader) from PaymentFileHeader paymentFileHeader"
    )
    Page<PaymentFileHeader> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct paymentFileHeader from PaymentFileHeader paymentFileHeader left join fetch paymentFileHeader.project")
    List<PaymentFileHeader> findAllWithToOneRelationships();

    @Query(
        "select paymentFileHeader from PaymentFileHeader paymentFileHeader left join fetch paymentFileHeader.project where paymentFileHeader.id =:id"
    )
    Optional<PaymentFileHeader> findOneWithToOneRelationships(@Param("id") Long id);
}
