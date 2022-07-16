package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.repository.LandCompensationRepository;
import com.melontech.landsys.service.LandCompensationService;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.mapper.LandCompensationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LandCompensation}.
 */
@Service
@Transactional
public class LandCompensationServiceImpl implements LandCompensationService {

    private final Logger log = LoggerFactory.getLogger(LandCompensationServiceImpl.class);

    private final LandCompensationRepository landCompensationRepository;

    private final LandCompensationMapper landCompensationMapper;

    public LandCompensationServiceImpl(
        LandCompensationRepository landCompensationRepository,
        LandCompensationMapper landCompensationMapper
    ) {
        this.landCompensationRepository = landCompensationRepository;
        this.landCompensationMapper = landCompensationMapper;
    }

    @Override
    public LandCompensationDTO save(LandCompensationDTO landCompensationDTO) {
        log.debug("Request to save LandCompensation : {}", landCompensationDTO);
        LandCompensation landCompensation = landCompensationMapper.toEntity(landCompensationDTO);
        landCompensation = landCompensationRepository.save(landCompensation);
        return landCompensationMapper.toDto(landCompensation);
    }

    @Override
    public LandCompensationDTO update(LandCompensationDTO landCompensationDTO) {
        log.debug("Request to save LandCompensation : {}", landCompensationDTO);
        LandCompensation landCompensation = landCompensationMapper.toEntity(landCompensationDTO);
        landCompensation = landCompensationRepository.save(landCompensation);
        return landCompensationMapper.toDto(landCompensation);
    }

    @Override
    public Optional<LandCompensationDTO> partialUpdate(LandCompensationDTO landCompensationDTO) {
        log.debug("Request to partially update LandCompensation : {}", landCompensationDTO);

        return landCompensationRepository
            .findById(landCompensationDTO.getId())
            .map(existingLandCompensation -> {
                landCompensationMapper.partialUpdate(existingLandCompensation, landCompensationDTO);

                return existingLandCompensation;
            })
            .map(landCompensationRepository::save)
            .map(landCompensationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LandCompensationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LandCompensations");
        return landCompensationRepository.findAll(pageable).map(landCompensationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LandCompensationDTO> findOne(Long id) {
        log.debug("Request to get LandCompensation : {}", id);
        return landCompensationRepository.findById(id).map(landCompensationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LandCompensation : {}", id);
        landCompensationRepository.deleteById(id);
    }
}
