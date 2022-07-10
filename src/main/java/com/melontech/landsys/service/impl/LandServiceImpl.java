package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.Land;
import com.melontech.landsys.repository.LandRepository;
import com.melontech.landsys.service.LandService;
import com.melontech.landsys.service.dto.LandDTO;
import com.melontech.landsys.service.mapper.LandMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Land}.
 */
@Service
@Transactional
public class LandServiceImpl implements LandService {

    private final Logger log = LoggerFactory.getLogger(LandServiceImpl.class);

    private final LandRepository landRepository;

    private final LandMapper landMapper;

    public LandServiceImpl(LandRepository landRepository, LandMapper landMapper) {
        this.landRepository = landRepository;
        this.landMapper = landMapper;
    }

    @Override
    public LandDTO save(LandDTO landDTO) {
        log.debug("Request to save Land : {}", landDTO);
        Land land = landMapper.toEntity(landDTO);
        land = landRepository.save(land);
        return landMapper.toDto(land);
    }

    @Override
    public LandDTO update(LandDTO landDTO) {
        log.debug("Request to save Land : {}", landDTO);
        Land land = landMapper.toEntity(landDTO);
        land = landRepository.save(land);
        return landMapper.toDto(land);
    }

    @Override
    public Optional<LandDTO> partialUpdate(LandDTO landDTO) {
        log.debug("Request to partially update Land : {}", landDTO);

        return landRepository
            .findById(landDTO.getId())
            .map(existingLand -> {
                landMapper.partialUpdate(existingLand, landDTO);

                return existingLand;
            })
            .map(landRepository::save)
            .map(landMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lands");
        return landRepository.findAll(pageable).map(landMapper::toDto);
    }

    public Page<LandDTO> findAllWithEagerRelationships(Pageable pageable) {
        return landRepository.findAllWithEagerRelationships(pageable).map(landMapper::toDto);
    }

    /**
     *  Get all the lands where ProjectLand is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LandDTO> findAllWhereProjectLandIsNull() {
        log.debug("Request to get all lands where ProjectLand is null");
        return StreamSupport
            .stream(landRepository.findAll().spliterator(), false)
            .filter(land -> land.getProjectLand() == null)
            .map(landMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LandDTO> findOne(Long id) {
        log.debug("Request to get Land : {}", id);
        return landRepository.findOneWithEagerRelationships(id).map(landMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Land : {}", id);
        landRepository.deleteById(id);
    }
}
