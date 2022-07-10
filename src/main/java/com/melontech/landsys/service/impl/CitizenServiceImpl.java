package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.repository.CitizenRepository;
import com.melontech.landsys.service.CitizenService;
import com.melontech.landsys.service.dto.CitizenDTO;
import com.melontech.landsys.service.mapper.CitizenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Citizen}.
 */
@Service
@Transactional
public class CitizenServiceImpl implements CitizenService {

    private final Logger log = LoggerFactory.getLogger(CitizenServiceImpl.class);

    private final CitizenRepository citizenRepository;

    private final CitizenMapper citizenMapper;

    public CitizenServiceImpl(CitizenRepository citizenRepository, CitizenMapper citizenMapper) {
        this.citizenRepository = citizenRepository;
        this.citizenMapper = citizenMapper;
    }

    @Override
    public CitizenDTO save(CitizenDTO citizenDTO) {
        log.debug("Request to save Citizen : {}", citizenDTO);
        Citizen citizen = citizenMapper.toEntity(citizenDTO);
        citizen = citizenRepository.save(citizen);
        return citizenMapper.toDto(citizen);
    }

    @Override
    public CitizenDTO update(CitizenDTO citizenDTO) {
        log.debug("Request to save Citizen : {}", citizenDTO);
        Citizen citizen = citizenMapper.toEntity(citizenDTO);
        citizen = citizenRepository.save(citizen);
        return citizenMapper.toDto(citizen);
    }

    @Override
    public Optional<CitizenDTO> partialUpdate(CitizenDTO citizenDTO) {
        log.debug("Request to partially update Citizen : {}", citizenDTO);

        return citizenRepository
            .findById(citizenDTO.getId())
            .map(existingCitizen -> {
                citizenMapper.partialUpdate(existingCitizen, citizenDTO);

                return existingCitizen;
            })
            .map(citizenRepository::save)
            .map(citizenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CitizenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Citizens");
        return citizenRepository.findAll(pageable).map(citizenMapper::toDto);
    }

    public Page<CitizenDTO> findAllWithEagerRelationships(Pageable pageable) {
        return citizenRepository.findAllWithEagerRelationships(pageable).map(citizenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitizenDTO> findOne(Long id) {
        log.debug("Request to get Citizen : {}", id);
        return citizenRepository.findOneWithEagerRelationships(id).map(citizenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Citizen : {}", id);
        citizenRepository.deleteById(id);
    }
}
