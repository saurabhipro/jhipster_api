package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.SequenceGenDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.SequenceGen}.
 */
public interface SequenceGenService {
    /**
     * Save a sequenceGen.
     *
     * @param sequenceGenDTO the entity to save.
     * @return the persisted entity.
     */
    SequenceGenDTO save(SequenceGenDTO sequenceGenDTO);

    /**
     * Updates a sequenceGen.
     *
     * @param sequenceGenDTO the entity to update.
     * @return the persisted entity.
     */
    SequenceGenDTO update(SequenceGenDTO sequenceGenDTO);

    /**
     * Partially updates a sequenceGen.
     *
     * @param sequenceGenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SequenceGenDTO> partialUpdate(SequenceGenDTO sequenceGenDTO);

    /**
     * Get all the sequenceGens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SequenceGenDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sequenceGen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SequenceGenDTO> findOne(Long id);

    /**
     * Delete the "id" sequenceGen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
