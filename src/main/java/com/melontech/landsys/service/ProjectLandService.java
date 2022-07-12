package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.ProjectLandDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.ProjectLand}.
 */
public interface ProjectLandService {
    /**
     * Save a projectLand.
     *
     * @param projectLandDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectLandDTO save(ProjectLandDTO projectLandDTO);

    /**
     * Updates a projectLand.
     *
     * @param projectLandDTO the entity to update.
     * @return the persisted entity.
     */
    ProjectLandDTO update(ProjectLandDTO projectLandDTO);

    /**
     * Partially updates a projectLand.
     *
     * @param projectLandDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectLandDTO> partialUpdate(ProjectLandDTO projectLandDTO);

    /**
     * Get all the projectLands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectLandDTO> findAll(Pageable pageable);
    /**
     * Get all the ProjectLandDTO where Survey is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ProjectLandDTO> findAllWhereSurveyIsNull();
    /**
     * Get all the ProjectLandDTO where LandCompensation is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ProjectLandDTO> findAllWhereLandCompensationIsNull();

    /**
     * Get all the projectLands with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectLandDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" projectLand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectLandDTO> findOne(Long id);

    /**
     * Delete the "id" projectLand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
