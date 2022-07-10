package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.PublicNotification;
import com.melontech.landsys.repository.PublicNotificationRepository;
import com.melontech.landsys.service.PublicNotificationService;
import com.melontech.landsys.service.dto.PublicNotificationDTO;
import com.melontech.landsys.service.mapper.PublicNotificationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PublicNotification}.
 */
@Service
@Transactional
public class PublicNotificationServiceImpl implements PublicNotificationService {

    private final Logger log = LoggerFactory.getLogger(PublicNotificationServiceImpl.class);

    private final PublicNotificationRepository publicNotificationRepository;

    private final PublicNotificationMapper publicNotificationMapper;

    public PublicNotificationServiceImpl(
        PublicNotificationRepository publicNotificationRepository,
        PublicNotificationMapper publicNotificationMapper
    ) {
        this.publicNotificationRepository = publicNotificationRepository;
        this.publicNotificationMapper = publicNotificationMapper;
    }

    @Override
    public PublicNotificationDTO save(PublicNotificationDTO publicNotificationDTO) {
        log.debug("Request to save PublicNotification : {}", publicNotificationDTO);
        PublicNotification publicNotification = publicNotificationMapper.toEntity(publicNotificationDTO);
        publicNotification = publicNotificationRepository.save(publicNotification);
        return publicNotificationMapper.toDto(publicNotification);
    }

    @Override
    public PublicNotificationDTO update(PublicNotificationDTO publicNotificationDTO) {
        log.debug("Request to save PublicNotification : {}", publicNotificationDTO);
        PublicNotification publicNotification = publicNotificationMapper.toEntity(publicNotificationDTO);
        publicNotification = publicNotificationRepository.save(publicNotification);
        return publicNotificationMapper.toDto(publicNotification);
    }

    @Override
    public Optional<PublicNotificationDTO> partialUpdate(PublicNotificationDTO publicNotificationDTO) {
        log.debug("Request to partially update PublicNotification : {}", publicNotificationDTO);

        return publicNotificationRepository
            .findById(publicNotificationDTO.getId())
            .map(existingPublicNotification -> {
                publicNotificationMapper.partialUpdate(existingPublicNotification, publicNotificationDTO);

                return existingPublicNotification;
            })
            .map(publicNotificationRepository::save)
            .map(publicNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicNotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PublicNotifications");
        return publicNotificationRepository.findAll(pageable).map(publicNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublicNotificationDTO> findOne(Long id) {
        log.debug("Request to get PublicNotification : {}", id);
        return publicNotificationRepository.findById(id).map(publicNotificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PublicNotification : {}", id);
        publicNotificationRepository.deleteById(id);
    }
}
