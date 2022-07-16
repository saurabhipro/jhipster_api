package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.CreatePaymentFileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.CreatePaymentFile}.
 */
public interface CreatePaymentFileService {
    /**
     * Save a createPaymentFile.
     *
     * @param createPaymentFileDTO the entity to save.
     * @return the persisted entity.
     */
    CreatePaymentFileDTO save(CreatePaymentFileDTO createPaymentFileDTO);

    /**
     * Updates a createPaymentFile.
     *
     * @param createPaymentFileDTO the entity to update.
     * @return the persisted entity.
     */
    CreatePaymentFileDTO update(CreatePaymentFileDTO createPaymentFileDTO);

    /**
     * Partially updates a createPaymentFile.
     *
     * @param createPaymentFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreatePaymentFileDTO> partialUpdate(CreatePaymentFileDTO createPaymentFileDTO);

    /**
     * Get all the createPaymentFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CreatePaymentFileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" createPaymentFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreatePaymentFileDTO> findOne(Long id);

    /**
     * Delete the "id" createPaymentFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
