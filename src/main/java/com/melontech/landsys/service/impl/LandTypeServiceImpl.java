package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.LandType;
import com.melontech.landsys.repository.LandTypeRepository;
import com.melontech.landsys.service.LandTypeService;
import com.melontech.landsys.service.dto.LandTypeDTO;
import com.melontech.landsys.service.mapper.LandTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LandType}.
 */
@Service
@Transactional
public class LandTypeServiceImpl implements LandTypeService {

    private final Logger log = LoggerFactory.getLogger(LandTypeServiceImpl.class);

    private final LandTypeRepository landTypeRepository;

    private final LandTypeMapper landTypeMapper;

    public LandTypeServiceImpl(LandTypeRepository landTypeRepository, LandTypeMapper landTypeMapper) {
        this.landTypeRepository = landTypeRepository;
        this.landTypeMapper = landTypeMapper;
    }

    @Override
    public LandTypeDTO save(LandTypeDTO landTypeDTO) {
        log.debug("Request to save LandType : {}", landTypeDTO);
        LandType landType = landTypeMapper.toEntity(landTypeDTO);
        landType = landTypeRepository.save(landType);
        return landTypeMapper.toDto(landType);
    }

    @Override
    public LandTypeDTO update(LandTypeDTO landTypeDTO) {
        log.debug("Request to save LandType : {}", landTypeDTO);
        LandType landType = landTypeMapper.toEntity(landTypeDTO);
        landType = landTypeRepository.save(landType);
        return landTypeMapper.toDto(landType);
    }

    @Override
    public Optional<LandTypeDTO> partialUpdate(LandTypeDTO landTypeDTO) {
        log.debug("Request to partially update LandType : {}", landTypeDTO);

        return landTypeRepository
            .findById(landTypeDTO.getId())
            .map(existingLandType -> {
                landTypeMapper.partialUpdate(existingLandType, landTypeDTO);

                return existingLandType;
            })
            .map(landTypeRepository::save)
            .map(landTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LandTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LandTypes");
        return landTypeRepository.findAll(pageable).map(landTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LandTypeDTO> findOne(Long id) {
        log.debug("Request to get LandType : {}", id);
        return landTypeRepository.findById(id).map(landTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LandType : {}", id);
        landTypeRepository.deleteById(id);
    }
}
