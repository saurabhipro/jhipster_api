package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.TransactionHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.TransactionHistory}.
 */
public interface TransactionHistoryService {
    /**
     * Save a transactionHistory.
     *
     * @param transactionHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionHistoryDTO save(TransactionHistoryDTO transactionHistoryDTO);

    /**
     * Updates a transactionHistory.
     *
     * @param transactionHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    TransactionHistoryDTO update(TransactionHistoryDTO transactionHistoryDTO);

    /**
     * Partially updates a transactionHistory.
     *
     * @param transactionHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransactionHistoryDTO> partialUpdate(TransactionHistoryDTO transactionHistoryDTO);

    /**
     * Get all the transactionHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" transactionHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" transactionHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
