package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.SubDistrictDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.SubDistrict}.
 */
public interface SubDistrictService {
    /**
     * Save a subDistrict.
     *
     * @param subDistrictDTO the entity to save.
     * @return the persisted entity.
     */
    SubDistrictDTO save(SubDistrictDTO subDistrictDTO);

    /**
     * Updates a subDistrict.
     *
     * @param subDistrictDTO the entity to update.
     * @return the persisted entity.
     */
    SubDistrictDTO update(SubDistrictDTO subDistrictDTO);

    /**
     * Partially updates a subDistrict.
     *
     * @param subDistrictDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubDistrictDTO> partialUpdate(SubDistrictDTO subDistrictDTO);

    /**
     * Get all the subDistricts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubDistrictDTO> findAll(Pageable pageable);

    /**
     * Get all the subDistricts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubDistrictDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" subDistrict.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubDistrictDTO> findOne(Long id);

    /**
     * Delete the "id" subDistrict.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
