package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.NoticeStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.NoticeStatus}.
 */
public interface NoticeStatusService {
    /**
     * Save a noticeStatus.
     *
     * @param noticeStatusDTO the entity to save.
     * @return the persisted entity.
     */
    NoticeStatusDTO save(NoticeStatusDTO noticeStatusDTO);

    /**
     * Updates a noticeStatus.
     *
     * @param noticeStatusDTO the entity to update.
     * @return the persisted entity.
     */
    NoticeStatusDTO update(NoticeStatusDTO noticeStatusDTO);

    /**
     * Partially updates a noticeStatus.
     *
     * @param noticeStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NoticeStatusDTO> partialUpdate(NoticeStatusDTO noticeStatusDTO);

    /**
     * Get all the noticeStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoticeStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" noticeStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoticeStatusDTO> findOne(Long id);

    /**
     * Delete the "id" noticeStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
