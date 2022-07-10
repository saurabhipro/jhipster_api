package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.BankBranchDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.BankBranch}.
 */
public interface BankBranchService {
    /**
     * Save a bankBranch.
     *
     * @param bankBranchDTO the entity to save.
     * @return the persisted entity.
     */
    BankBranchDTO save(BankBranchDTO bankBranchDTO);

    /**
     * Updates a bankBranch.
     *
     * @param bankBranchDTO the entity to update.
     * @return the persisted entity.
     */
    BankBranchDTO update(BankBranchDTO bankBranchDTO);

    /**
     * Partially updates a bankBranch.
     *
     * @param bankBranchDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankBranchDTO> partialUpdate(BankBranchDTO bankBranchDTO);

    /**
     * Get all the bankBranches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankBranchDTO> findAll(Pageable pageable);

    /**
     * Get all the bankBranches with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankBranchDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" bankBranch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankBranchDTO> findOne(Long id);

    /**
     * Delete the "id" bankBranch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
