package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.SubDistrict;
import com.melontech.landsys.repository.SubDistrictRepository;
import com.melontech.landsys.service.SubDistrictService;
import com.melontech.landsys.service.dto.SubDistrictDTO;
import com.melontech.landsys.service.mapper.SubDistrictMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubDistrict}.
 */
@Service
@Transactional
public class SubDistrictServiceImpl implements SubDistrictService {

    private final Logger log = LoggerFactory.getLogger(SubDistrictServiceImpl.class);

    private final SubDistrictRepository subDistrictRepository;

    private final SubDistrictMapper subDistrictMapper;

    public SubDistrictServiceImpl(SubDistrictRepository subDistrictRepository, SubDistrictMapper subDistrictMapper) {
        this.subDistrictRepository = subDistrictRepository;
        this.subDistrictMapper = subDistrictMapper;
    }

    @Override
    public SubDistrictDTO save(SubDistrictDTO subDistrictDTO) {
        log.debug("Request to save SubDistrict : {}", subDistrictDTO);
        SubDistrict subDistrict = subDistrictMapper.toEntity(subDistrictDTO);
        subDistrict = subDistrictRepository.save(subDistrict);
        return subDistrictMapper.toDto(subDistrict);
    }

    @Override
    public SubDistrictDTO update(SubDistrictDTO subDistrictDTO) {
        log.debug("Request to save SubDistrict : {}", subDistrictDTO);
        SubDistrict subDistrict = subDistrictMapper.toEntity(subDistrictDTO);
        subDistrict = subDistrictRepository.save(subDistrict);
        return subDistrictMapper.toDto(subDistrict);
    }

    @Override
    public Optional<SubDistrictDTO> partialUpdate(SubDistrictDTO subDistrictDTO) {
        log.debug("Request to partially update SubDistrict : {}", subDistrictDTO);

        return subDistrictRepository
            .findById(subDistrictDTO.getId())
            .map(existingSubDistrict -> {
                subDistrictMapper.partialUpdate(existingSubDistrict, subDistrictDTO);

                return existingSubDistrict;
            })
            .map(subDistrictRepository::save)
            .map(subDistrictMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubDistrictDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubDistricts");
        return subDistrictRepository.findAll(pageable).map(subDistrictMapper::toDto);
    }

    public Page<SubDistrictDTO> findAllWithEagerRelationships(Pageable pageable) {
        return subDistrictRepository.findAllWithEagerRelationships(pageable).map(subDistrictMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubDistrictDTO> findOne(Long id) {
        log.debug("Request to get SubDistrict : {}", id);
        return subDistrictRepository.findOneWithEagerRelationships(id).map(subDistrictMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubDistrict : {}", id);
        subDistrictRepository.deleteById(id);
    }
}
