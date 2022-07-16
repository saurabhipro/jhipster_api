package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.SequenceGen;
import com.melontech.landsys.repository.SequenceGenRepository;
import com.melontech.landsys.service.SequenceGenService;
import com.melontech.landsys.service.dto.SequenceGenDTO;
import com.melontech.landsys.service.mapper.SequenceGenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SequenceGen}.
 */
@Service
@Transactional
public class SequenceGenServiceImpl implements SequenceGenService {

    private final Logger log = LoggerFactory.getLogger(SequenceGenServiceImpl.class);

    private final SequenceGenRepository sequenceGenRepository;

    private final SequenceGenMapper sequenceGenMapper;

    public SequenceGenServiceImpl(SequenceGenRepository sequenceGenRepository, SequenceGenMapper sequenceGenMapper) {
        this.sequenceGenRepository = sequenceGenRepository;
        this.sequenceGenMapper = sequenceGenMapper;
    }

    @Override
    public SequenceGenDTO save(SequenceGenDTO sequenceGenDTO) {
        log.debug("Request to save SequenceGen : {}", sequenceGenDTO);
        SequenceGen sequenceGen = sequenceGenMapper.toEntity(sequenceGenDTO);
        sequenceGen = sequenceGenRepository.save(sequenceGen);
        return sequenceGenMapper.toDto(sequenceGen);
    }

    @Override
    public SequenceGenDTO update(SequenceGenDTO sequenceGenDTO) {
        log.debug("Request to save SequenceGen : {}", sequenceGenDTO);
        SequenceGen sequenceGen = sequenceGenMapper.toEntity(sequenceGenDTO);
        sequenceGen = sequenceGenRepository.save(sequenceGen);
        return sequenceGenMapper.toDto(sequenceGen);
    }

    @Override
    public Optional<SequenceGenDTO> partialUpdate(SequenceGenDTO sequenceGenDTO) {
        log.debug("Request to partially update SequenceGen : {}", sequenceGenDTO);

        return sequenceGenRepository
            .findById(sequenceGenDTO.getId())
            .map(existingSequenceGen -> {
                sequenceGenMapper.partialUpdate(existingSequenceGen, sequenceGenDTO);

                return existingSequenceGen;
            })
            .map(sequenceGenRepository::save)
            .map(sequenceGenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SequenceGenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceGens");
        return sequenceGenRepository.findAll(pageable).map(sequenceGenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SequenceGenDTO> findOne(Long id) {
        log.debug("Request to get SequenceGen : {}", id);
        return sequenceGenRepository.findById(id).map(sequenceGenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SequenceGen : {}", id);
        sequenceGenRepository.deleteById(id);
    }
}
