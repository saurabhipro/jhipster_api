package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.repository.KhatedarRepository;
import com.melontech.landsys.service.KhatedarService;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.mapper.KhatedarMapper;
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
 * Service Implementation for managing {@link Khatedar}.
 */
@Service
@Transactional
public class KhatedarServiceImpl implements KhatedarService {

    private final Logger log = LoggerFactory.getLogger(KhatedarServiceImpl.class);

    private final KhatedarRepository khatedarRepository;

    private final KhatedarMapper khatedarMapper;

    public KhatedarServiceImpl(KhatedarRepository khatedarRepository, KhatedarMapper khatedarMapper) {
        this.khatedarRepository = khatedarRepository;
        this.khatedarMapper = khatedarMapper;
    }

    @Override
    public KhatedarDTO save(KhatedarDTO khatedarDTO) {
        log.debug("Request to save Khatedar : {}", khatedarDTO);
        Khatedar khatedar = khatedarMapper.toEntity(khatedarDTO);
        khatedar = khatedarRepository.save(khatedar);
        return khatedarMapper.toDto(khatedar);
    }

    @Override
    public KhatedarDTO update(KhatedarDTO khatedarDTO) {
        log.debug("Request to save Khatedar : {}", khatedarDTO);
        Khatedar khatedar = khatedarMapper.toEntity(khatedarDTO);
        khatedar = khatedarRepository.save(khatedar);
        return khatedarMapper.toDto(khatedar);
    }

    @Override
    public Optional<KhatedarDTO> partialUpdate(KhatedarDTO khatedarDTO) {
        log.debug("Request to partially update Khatedar : {}", khatedarDTO);

        return khatedarRepository
            .findById(khatedarDTO.getId())
            .map(existingKhatedar -> {
                khatedarMapper.partialUpdate(existingKhatedar, khatedarDTO);

                return existingKhatedar;
            })
            .map(khatedarRepository::save)
            .map(khatedarMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KhatedarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Khatedars");
        return khatedarRepository.findAll(pageable).map(khatedarMapper::toDto);
    }

    public Page<KhatedarDTO> findAllWithEagerRelationships(Pageable pageable) {
        return khatedarRepository.findAllWithEagerRelationships(pageable).map(khatedarMapper::toDto);
    }

    /**
     *  Get all the khatedars where Survey is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<KhatedarDTO> findAllWhereSurveyIsNull() {
        log.debug("Request to get all khatedars where Survey is null");
        return StreamSupport
            .stream(khatedarRepository.findAll().spliterator(), false)
            .filter(khatedar -> khatedar.getSurvey() == null)
            .map(khatedarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KhatedarDTO> findOne(Long id) {
        log.debug("Request to get Khatedar : {}", id);
        return khatedarRepository.findOneWithEagerRelationships(id).map(khatedarMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Khatedar : {}", id);
        khatedarRepository.deleteById(id);
    }
}
