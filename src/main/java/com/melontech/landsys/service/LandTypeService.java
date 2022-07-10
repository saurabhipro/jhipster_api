package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.LandTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.LandType}.
 */
public interface LandTypeService {
    /**
     * Save a landType.
     *
     * @param landTypeDTO the entity to save.
     * @return the persisted entity.
     */
    LandTypeDTO save(LandTypeDTO landTypeDTO);

    /**
     * Updates a landType.
     *
     * @param landTypeDTO the entity to update.
     * @return the persisted entity.
     */
    LandTypeDTO update(LandTypeDTO landTypeDTO);

    /**
     * Partially updates a landType.
     *
     * @param landTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LandTypeDTO> partialUpdate(LandTypeDTO landTypeDTO);

    /**
     * Get all the landTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LandTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" landType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LandTypeDTO> findOne(Long id);

    /**
     * Delete the "id" landType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
