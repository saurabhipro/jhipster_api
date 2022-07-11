package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.PaymentFileReconDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.PaymentFileRecon}.
 */
public interface PaymentFileReconService {
    /**
     * Save a paymentFileRecon.
     *
     * @param paymentFileReconDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentFileReconDTO save(PaymentFileReconDTO paymentFileReconDTO);

    /**
     * Updates a paymentFileRecon.
     *
     * @param paymentFileReconDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentFileReconDTO update(PaymentFileReconDTO paymentFileReconDTO);

    /**
     * Partially updates a paymentFileRecon.
     *
     * @param paymentFileReconDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentFileReconDTO> partialUpdate(PaymentFileReconDTO paymentFileReconDTO);

    /**
     * Get all the paymentFileRecons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentFileReconDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paymentFileRecon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentFileReconDTO> findOne(Long id);

    /**
     * Delete the "id" paymentFileRecon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
