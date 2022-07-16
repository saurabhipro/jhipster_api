package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.CreatePaymentFile;
import com.melontech.landsys.repository.CreatePaymentFileRepository;
import com.melontech.landsys.service.CreatePaymentFileService;
import com.melontech.landsys.service.dto.CreatePaymentFileDTO;
import com.melontech.landsys.service.mapper.CreatePaymentFileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreatePaymentFile}.
 */
@Service
@Transactional
public class CreatePaymentFileServiceImpl implements CreatePaymentFileService {

    private final Logger log = LoggerFactory.getLogger(CreatePaymentFileServiceImpl.class);

    private final CreatePaymentFileRepository createPaymentFileRepository;

    private final CreatePaymentFileMapper createPaymentFileMapper;

    public CreatePaymentFileServiceImpl(
        CreatePaymentFileRepository createPaymentFileRepository,
        CreatePaymentFileMapper createPaymentFileMapper
    ) {
        this.createPaymentFileRepository = createPaymentFileRepository;
        this.createPaymentFileMapper = createPaymentFileMapper;
    }

    @Override
    public CreatePaymentFileDTO save(CreatePaymentFileDTO createPaymentFileDTO) {
        log.debug("Request to save CreatePaymentFile : {}", createPaymentFileDTO);
        CreatePaymentFile createPaymentFile = createPaymentFileMapper.toEntity(createPaymentFileDTO);
        createPaymentFile = createPaymentFileRepository.save(createPaymentFile);
        return createPaymentFileMapper.toDto(createPaymentFile);
    }

    @Override
    public CreatePaymentFileDTO update(CreatePaymentFileDTO createPaymentFileDTO) {
        log.debug("Request to save CreatePaymentFile : {}", createPaymentFileDTO);
        CreatePaymentFile createPaymentFile = createPaymentFileMapper.toEntity(createPaymentFileDTO);
        createPaymentFile = createPaymentFileRepository.save(createPaymentFile);
        return createPaymentFileMapper.toDto(createPaymentFile);
    }

    @Override
    public Optional<CreatePaymentFileDTO> partialUpdate(CreatePaymentFileDTO createPaymentFileDTO) {
        log.debug("Request to partially update CreatePaymentFile : {}", createPaymentFileDTO);

        return createPaymentFileRepository
            .findById(createPaymentFileDTO.getId())
            .map(existingCreatePaymentFile -> {
                createPaymentFileMapper.partialUpdate(existingCreatePaymentFile, createPaymentFileDTO);

                return existingCreatePaymentFile;
            })
            .map(createPaymentFileRepository::save)
            .map(createPaymentFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CreatePaymentFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CreatePaymentFiles");
        return createPaymentFileRepository.findAll(pageable).map(createPaymentFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreatePaymentFileDTO> findOne(Long id) {
        log.debug("Request to get CreatePaymentFile : {}", id);
        return createPaymentFileRepository.findById(id).map(createPaymentFileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreatePaymentFile : {}", id);
        createPaymentFileRepository.deleteById(id);
    }
}
