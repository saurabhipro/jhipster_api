package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.PaymentFileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.PaymentFile}.
 */
public interface PaymentFileService {
    /**
     * Save a paymentFile.
     *
     * @param paymentFileDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentFileDTO save(PaymentFileDTO paymentFileDTO);

    /**
     * Updates a paymentFile.
     *
     * @param paymentFileDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentFileDTO update(PaymentFileDTO paymentFileDTO);

    /**
     * Partially updates a paymentFile.
     *
     * @param paymentFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentFileDTO> partialUpdate(PaymentFileDTO paymentFileDTO);

    /**
     * Get all the paymentFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentFileDTO> findAll(Pageable pageable);

    /**
     * Get all the paymentFiles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentFileDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" paymentFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentFileDTO> findOne(Long id);

    /**
     * Delete the "id" paymentFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
