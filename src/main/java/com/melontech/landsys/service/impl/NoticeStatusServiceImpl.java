package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.NoticeStatus;
import com.melontech.landsys.repository.NoticeStatusRepository;
import com.melontech.landsys.service.NoticeStatusService;
import com.melontech.landsys.service.dto.NoticeStatusDTO;
import com.melontech.landsys.service.mapper.NoticeStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NoticeStatus}.
 */
@Service
@Transactional
public class NoticeStatusServiceImpl implements NoticeStatusService {

    private final Logger log = LoggerFactory.getLogger(NoticeStatusServiceImpl.class);

    private final NoticeStatusRepository noticeStatusRepository;

    private final NoticeStatusMapper noticeStatusMapper;

    public NoticeStatusServiceImpl(NoticeStatusRepository noticeStatusRepository, NoticeStatusMapper noticeStatusMapper) {
        this.noticeStatusRepository = noticeStatusRepository;
        this.noticeStatusMapper = noticeStatusMapper;
    }

    @Override
    public NoticeStatusDTO save(NoticeStatusDTO noticeStatusDTO) {
        log.debug("Request to save NoticeStatus : {}", noticeStatusDTO);
        NoticeStatus noticeStatus = noticeStatusMapper.toEntity(noticeStatusDTO);
        noticeStatus = noticeStatusRepository.save(noticeStatus);
        return noticeStatusMapper.toDto(noticeStatus);
    }

    @Override
    public NoticeStatusDTO update(NoticeStatusDTO noticeStatusDTO) {
        log.debug("Request to save NoticeStatus : {}", noticeStatusDTO);
        NoticeStatus noticeStatus = noticeStatusMapper.toEntity(noticeStatusDTO);
        noticeStatus = noticeStatusRepository.save(noticeStatus);
        return noticeStatusMapper.toDto(noticeStatus);
    }

    @Override
    public Optional<NoticeStatusDTO> partialUpdate(NoticeStatusDTO noticeStatusDTO) {
        log.debug("Request to partially update NoticeStatus : {}", noticeStatusDTO);

        return noticeStatusRepository
            .findById(noticeStatusDTO.getId())
            .map(existingNoticeStatus -> {
                noticeStatusMapper.partialUpdate(existingNoticeStatus, noticeStatusDTO);

                return existingNoticeStatus;
            })
            .map(noticeStatusRepository::save)
            .map(noticeStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoticeStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NoticeStatuses");
        return noticeStatusRepository.findAll(pageable).map(noticeStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NoticeStatusDTO> findOne(Long id) {
        log.debug("Request to get NoticeStatus : {}", id);
        return noticeStatusRepository.findById(id).map(noticeStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NoticeStatus : {}", id);
        noticeStatusRepository.deleteById(id);
    }
}
