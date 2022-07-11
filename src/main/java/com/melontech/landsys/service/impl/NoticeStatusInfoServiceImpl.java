package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.NoticeStatusInfo;
import com.melontech.landsys.repository.NoticeStatusInfoRepository;
import com.melontech.landsys.service.NoticeStatusInfoService;
import com.melontech.landsys.service.dto.NoticeStatusInfoDTO;
import com.melontech.landsys.service.mapper.NoticeStatusInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NoticeStatusInfo}.
 */
@Service
@Transactional
public class NoticeStatusInfoServiceImpl implements NoticeStatusInfoService {

    private final Logger log = LoggerFactory.getLogger(NoticeStatusInfoServiceImpl.class);

    private final NoticeStatusInfoRepository noticeStatusInfoRepository;

    private final NoticeStatusInfoMapper noticeStatusInfoMapper;

    public NoticeStatusInfoServiceImpl(
        NoticeStatusInfoRepository noticeStatusInfoRepository,
        NoticeStatusInfoMapper noticeStatusInfoMapper
    ) {
        this.noticeStatusInfoRepository = noticeStatusInfoRepository;
        this.noticeStatusInfoMapper = noticeStatusInfoMapper;
    }

    @Override
    public NoticeStatusInfoDTO save(NoticeStatusInfoDTO noticeStatusInfoDTO) {
        log.debug("Request to save NoticeStatusInfo : {}", noticeStatusInfoDTO);
        NoticeStatusInfo noticeStatusInfo = noticeStatusInfoMapper.toEntity(noticeStatusInfoDTO);
        noticeStatusInfo = noticeStatusInfoRepository.save(noticeStatusInfo);
        return noticeStatusInfoMapper.toDto(noticeStatusInfo);
    }

    @Override
    public NoticeStatusInfoDTO update(NoticeStatusInfoDTO noticeStatusInfoDTO) {
        log.debug("Request to save NoticeStatusInfo : {}", noticeStatusInfoDTO);
        NoticeStatusInfo noticeStatusInfo = noticeStatusInfoMapper.toEntity(noticeStatusInfoDTO);
        noticeStatusInfo = noticeStatusInfoRepository.save(noticeStatusInfo);
        return noticeStatusInfoMapper.toDto(noticeStatusInfo);
    }

    @Override
    public Optional<NoticeStatusInfoDTO> partialUpdate(NoticeStatusInfoDTO noticeStatusInfoDTO) {
        log.debug("Request to partially update NoticeStatusInfo : {}", noticeStatusInfoDTO);

        return noticeStatusInfoRepository
            .findById(noticeStatusInfoDTO.getId())
            .map(existingNoticeStatusInfo -> {
                noticeStatusInfoMapper.partialUpdate(existingNoticeStatusInfo, noticeStatusInfoDTO);

                return existingNoticeStatusInfo;
            })
            .map(noticeStatusInfoRepository::save)
            .map(noticeStatusInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoticeStatusInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NoticeStatusInfos");
        return noticeStatusInfoRepository.findAll(pageable).map(noticeStatusInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NoticeStatusInfoDTO> findOne(Long id) {
        log.debug("Request to get NoticeStatusInfo : {}", id);
        return noticeStatusInfoRepository.findById(id).map(noticeStatusInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NoticeStatusInfo : {}", id);
        noticeStatusInfoRepository.deleteById(id);
    }
}
