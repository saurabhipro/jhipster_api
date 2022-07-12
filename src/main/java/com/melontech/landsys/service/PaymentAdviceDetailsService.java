package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.PaymentAdviceDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.PaymentAdviceDetails}.
 */
public interface PaymentAdviceDetailsService {
    /**
     * Save a paymentAdviceDetails.
     *
     * @param paymentAdviceDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentAdviceDetailsDTO save(PaymentAdviceDetailsDTO paymentAdviceDetailsDTO);

    /**
     * Updates a paymentAdviceDetails.
     *
     * @param paymentAdviceDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentAdviceDetailsDTO update(PaymentAdviceDetailsDTO paymentAdviceDetailsDTO);

    /**
     * Partially updates a paymentAdviceDetails.
     *
     * @param paymentAdviceDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentAdviceDetailsDTO> partialUpdate(PaymentAdviceDetailsDTO paymentAdviceDetailsDTO);

    /**
     * Get all the paymentAdviceDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentAdviceDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paymentAdviceDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentAdviceDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" paymentAdviceDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
