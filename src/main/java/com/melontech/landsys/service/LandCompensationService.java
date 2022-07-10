package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.LandCompensationDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.LandCompensation}.
 */
public interface LandCompensationService {
    /**
     * Save a landCompensation.
     *
     * @param landCompensationDTO the entity to save.
     * @return the persisted entity.
     */
    LandCompensationDTO save(LandCompensationDTO landCompensationDTO);

    /**
     * Updates a landCompensation.
     *
     * @param landCompensationDTO the entity to update.
     * @return the persisted entity.
     */
    LandCompensationDTO update(LandCompensationDTO landCompensationDTO);

    /**
     * Partially updates a landCompensation.
     *
     * @param landCompensationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LandCompensationDTO> partialUpdate(LandCompensationDTO landCompensationDTO);

    /**
     * Get all the landCompensations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LandCompensationDTO> findAll(Pageable pageable);
    /**
     * Get all the LandCompensationDTO where PaymentAdvice is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LandCompensationDTO> findAllWherePaymentAdviceIsNull();

    /**
     * Get the "id" landCompensation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LandCompensationDTO> findOne(Long id);

    /**
     * Delete the "id" landCompensation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
