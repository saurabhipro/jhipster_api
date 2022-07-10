package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.VillageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.Village}.
 */
public interface VillageService {
    /**
     * Save a village.
     *
     * @param villageDTO the entity to save.
     * @return the persisted entity.
     */
    VillageDTO save(VillageDTO villageDTO);

    /**
     * Updates a village.
     *
     * @param villageDTO the entity to update.
     * @return the persisted entity.
     */
    VillageDTO update(VillageDTO villageDTO);

    /**
     * Partially updates a village.
     *
     * @param villageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VillageDTO> partialUpdate(VillageDTO villageDTO);

    /**
     * Get all the villages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VillageDTO> findAll(Pageable pageable);

    /**
     * Get all the villages with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VillageDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" village.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VillageDTO> findOne(Long id);

    /**
     * Delete the "id" village.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
