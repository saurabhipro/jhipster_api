package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PaymentAdvice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentAdvice entity.
 */
@Repository
public interface PaymentAdviceRepository extends JpaRepository<PaymentAdvice, Long>, JpaSpecificationExecutor<PaymentAdvice> {
    default Optional<PaymentAdvice> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PaymentAdvice> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PaymentAdvice> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct paymentAdvice from PaymentAdvice paymentAdvice left join fetch paymentAdvice.land",
        countQuery = "select count(distinct paymentAdvice) from PaymentAdvice paymentAdvice"
    )
    Page<PaymentAdvice> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct paymentAdvice from PaymentAdvice paymentAdvice left join fetch paymentAdvice.land")
    List<PaymentAdvice> findAllWithToOneRelationships();

    @Query("select paymentAdvice from PaymentAdvice paymentAdvice left join fetch paymentAdvice.land where paymentAdvice.id =:id")
    Optional<PaymentAdvice> findOneWithToOneRelationships(@Param("id") Long id);
}
