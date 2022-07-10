package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.CitizenDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.Citizen}.
 */
public interface CitizenService {
    /**
     * Save a citizen.
     *
     * @param citizenDTO the entity to save.
     * @return the persisted entity.
     */
    CitizenDTO save(CitizenDTO citizenDTO);

    /**
     * Updates a citizen.
     *
     * @param citizenDTO the entity to update.
     * @return the persisted entity.
     */
    CitizenDTO update(CitizenDTO citizenDTO);

    /**
     * Partially updates a citizen.
     *
     * @param citizenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CitizenDTO> partialUpdate(CitizenDTO citizenDTO);

    /**
     * Get all the citizens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CitizenDTO> findAll(Pageable pageable);

    /**
     * Get all the citizens with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CitizenDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" citizen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitizenDTO> findOne(Long id);

    /**
     * Delete the "id" citizen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
