package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.PaymentAdvice}.
 */
public interface PaymentAdviceService {
    /**
     * Save a paymentAdvice.
     *
     * @param paymentAdviceDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentAdviceDTO save(PaymentAdviceDTO paymentAdviceDTO);

    /**
     * Updates a paymentAdvice.
     *
     * @param paymentAdviceDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentAdviceDTO update(PaymentAdviceDTO paymentAdviceDTO);

    /**
     * Partially updates a paymentAdvice.
     *
     * @param paymentAdviceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentAdviceDTO> partialUpdate(PaymentAdviceDTO paymentAdviceDTO);

    /**
     * Get all the paymentAdvices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentAdviceDTO> findAll(Pageable pageable);
    /**
     * Get all the PaymentAdviceDTO where PaymentFileRecon is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PaymentAdviceDTO> findAllWherePaymentFileReconIsNull();

    /**
     * Get the "id" paymentAdvice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentAdviceDTO> findOne(Long id);

    /**
     * Delete the "id" paymentAdvice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
