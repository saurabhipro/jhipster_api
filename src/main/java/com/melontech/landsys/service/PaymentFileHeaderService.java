package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.PaymentFileHeaderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.PaymentFileHeader}.
 */
public interface PaymentFileHeaderService {
    /**
     * Save a paymentFileHeader.
     *
     * @param paymentFileHeaderDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentFileHeaderDTO save(PaymentFileHeaderDTO paymentFileHeaderDTO);

    /**
     * Updates a paymentFileHeader.
     *
     * @param paymentFileHeaderDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentFileHeaderDTO update(PaymentFileHeaderDTO paymentFileHeaderDTO);

    /**
     * Partially updates a paymentFileHeader.
     *
     * @param paymentFileHeaderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentFileHeaderDTO> partialUpdate(PaymentFileHeaderDTO paymentFileHeaderDTO);

    /**
     * Get all the paymentFileHeaders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentFileHeaderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paymentFileHeader.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentFileHeaderDTO> findOne(Long id);

    /**
     * Delete the "id" paymentFileHeader.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
