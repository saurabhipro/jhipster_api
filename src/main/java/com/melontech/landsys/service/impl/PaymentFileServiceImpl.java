package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.repository.PaymentFileRepository;
import com.melontech.landsys.service.PaymentFileService;
import com.melontech.landsys.service.dto.PaymentFileDTO;
import com.melontech.landsys.service.mapper.PaymentFileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentFile}.
 */
@Service
@Transactional
public class PaymentFileServiceImpl implements PaymentFileService {

    private final Logger log = LoggerFactory.getLogger(PaymentFileServiceImpl.class);

    private final PaymentFileRepository paymentFileRepository;

    private final PaymentFileMapper paymentFileMapper;

    public PaymentFileServiceImpl(PaymentFileRepository paymentFileRepository, PaymentFileMapper paymentFileMapper) {
        this.paymentFileRepository = paymentFileRepository;
        this.paymentFileMapper = paymentFileMapper;
    }

    @Override
    public PaymentFileDTO save(PaymentFileDTO paymentFileDTO) {
        log.debug("Request to save PaymentFile : {}", paymentFileDTO);
        PaymentFile paymentFile = paymentFileMapper.toEntity(paymentFileDTO);
        paymentFile = paymentFileRepository.save(paymentFile);
        return paymentFileMapper.toDto(paymentFile);
    }

    @Override
    public PaymentFileDTO update(PaymentFileDTO paymentFileDTO) {
        log.debug("Request to save PaymentFile : {}", paymentFileDTO);
        PaymentFile paymentFile = paymentFileMapper.toEntity(paymentFileDTO);
        paymentFile = paymentFileRepository.save(paymentFile);
        return paymentFileMapper.toDto(paymentFile);
    }

    @Override
    public Optional<PaymentFileDTO> partialUpdate(PaymentFileDTO paymentFileDTO) {
        log.debug("Request to partially update PaymentFile : {}", paymentFileDTO);

        return paymentFileRepository
            .findById(paymentFileDTO.getId())
            .map(existingPaymentFile -> {
                paymentFileMapper.partialUpdate(existingPaymentFile, paymentFileDTO);

                return existingPaymentFile;
            })
            .map(paymentFileRepository::save)
            .map(paymentFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentFiles");
        return paymentFileRepository.findAll(pageable).map(paymentFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentFileDTO> findOne(Long id) {
        log.debug("Request to get PaymentFile : {}", id);
        return paymentFileRepository.findById(id).map(paymentFileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentFile : {}", id);
        paymentFileRepository.deleteById(id);
    }
}
