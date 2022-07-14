package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.KhatedarDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.Khatedar}.
 */
public interface KhatedarService {
    /**
     * Save a khatedar.
     *
     * @param khatedarDTO the entity to save.
     * @return the persisted entity.
     */
    KhatedarDTO save(KhatedarDTO khatedarDTO);

    /**
     * Updates a khatedar.
     *
     * @param khatedarDTO the entity to update.
     * @return the persisted entity.
     */
    KhatedarDTO update(KhatedarDTO khatedarDTO);

    /**
     * Partially updates a khatedar.
     *
     * @param khatedarDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KhatedarDTO> partialUpdate(KhatedarDTO khatedarDTO);

    /**
     * Get all the khatedars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KhatedarDTO> findAll(Pageable pageable);
    /**
     * Get all the KhatedarDTO where PaymentFile is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<KhatedarDTO> findAllWherePaymentFileIsNull();

    /**
     * Get the "id" khatedar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KhatedarDTO> findOne(Long id);

    /**
     * Delete the "id" khatedar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
