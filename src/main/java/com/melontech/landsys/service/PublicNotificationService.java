package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.PublicNotificationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.PublicNotification}.
 */
public interface PublicNotificationService {
    /**
     * Save a publicNotification.
     *
     * @param publicNotificationDTO the entity to save.
     * @return the persisted entity.
     */
    PublicNotificationDTO save(PublicNotificationDTO publicNotificationDTO);

    /**
     * Updates a publicNotification.
     *
     * @param publicNotificationDTO the entity to update.
     * @return the persisted entity.
     */
    PublicNotificationDTO update(PublicNotificationDTO publicNotificationDTO);

    /**
     * Partially updates a publicNotification.
     *
     * @param publicNotificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PublicNotificationDTO> partialUpdate(PublicNotificationDTO publicNotificationDTO);

    /**
     * Get all the publicNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicNotificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" publicNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicNotificationDTO> findOne(Long id);

    /**
     * Delete the "id" publicNotification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
