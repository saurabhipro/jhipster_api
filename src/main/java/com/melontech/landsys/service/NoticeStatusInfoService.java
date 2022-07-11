package com.melontech.landsys.service;

import com.melontech.landsys.service.dto.NoticeStatusInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melontech.landsys.domain.NoticeStatusInfo}.
 */
public interface NoticeStatusInfoService {
    /**
     * Save a noticeStatusInfo.
     *
     * @param noticeStatusInfoDTO the entity to save.
     * @return the persisted entity.
     */
    NoticeStatusInfoDTO save(NoticeStatusInfoDTO noticeStatusInfoDTO);

    /**
     * Updates a noticeStatusInfo.
     *
     * @param noticeStatusInfoDTO the entity to update.
     * @return the persisted entity.
     */
    NoticeStatusInfoDTO update(NoticeStatusInfoDTO noticeStatusInfoDTO);

    /**
     * Partially updates a noticeStatusInfo.
     *
     * @param noticeStatusInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NoticeStatusInfoDTO> partialUpdate(NoticeStatusInfoDTO noticeStatusInfoDTO);

    /**
     * Get all the noticeStatusInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoticeStatusInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" noticeStatusInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoticeStatusInfoDTO> findOne(Long id);

    /**
     * Delete the "id" noticeStatusInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
