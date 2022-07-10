package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.ProjectStatusHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.ProjectStatusHistory}.
 */
public interface ProjectStatusHistoryService {
    /**
     * Save a projectStatusHistory.
     *
     * @param projectStatusHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectStatusHistoryDTO save(ProjectStatusHistoryDTO projectStatusHistoryDTO);

    /**
     * Updates a projectStatusHistory.
     *
     * @param projectStatusHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    ProjectStatusHistoryDTO update(ProjectStatusHistoryDTO projectStatusHistoryDTO);

    /**
     * Partially updates a projectStatusHistory.
     *
     * @param projectStatusHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectStatusHistoryDTO> partialUpdate(ProjectStatusHistoryDTO projectStatusHistoryDTO);

    /**
     * Get all the projectStatusHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectStatusHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projectStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectStatusHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" projectStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
