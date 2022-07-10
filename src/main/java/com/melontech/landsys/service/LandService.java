package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.LandDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.Land}.
 */
public interface LandService {
    /**
     * Save a land.
     *
     * @param landDTO the entity to save.
     * @return the persisted entity.
     */
    LandDTO save(LandDTO landDTO);

    /**
     * Updates a land.
     *
     * @param landDTO the entity to update.
     * @return the persisted entity.
     */
    LandDTO update(LandDTO landDTO);

    /**
     * Partially updates a land.
     *
     * @param landDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LandDTO> partialUpdate(LandDTO landDTO);

    /**
     * Get all the lands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LandDTO> findAll(Pageable pageable);
    /**
     * Get all the LandDTO where ProjectLand is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LandDTO> findAllWhereProjectLandIsNull();

    /**
     * Get all the lands with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LandDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" land.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LandDTO> findOne(Long id);

    /**
     * Delete the "id" land.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
