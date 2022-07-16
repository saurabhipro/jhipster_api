package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.PaymentFileHeader;
import com.melontech.landsys.repository.PaymentFileHeaderRepository;
import com.melontech.landsys.service.PaymentFileHeaderService;
import com.melontech.landsys.service.dto.PaymentFileHeaderDTO;
import com.melontech.landsys.service.mapper.PaymentFileHeaderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentFileHeader}.
 */
@Service
@Transactional
public class PaymentFileHeaderServiceImpl implements PaymentFileHeaderService {

    private final Logger log = LoggerFactory.getLogger(PaymentFileHeaderServiceImpl.class);

    private final PaymentFileHeaderRepository paymentFileHeaderRepository;

    private final PaymentFileHeaderMapper paymentFileHeaderMapper;

    public PaymentFileHeaderServiceImpl(
        PaymentFileHeaderRepository paymentFileHeaderRepository,
        PaymentFileHeaderMapper paymentFileHeaderMapper
    ) {
        this.paymentFileHeaderRepository = paymentFileHeaderRepository;
        this.paymentFileHeaderMapper = paymentFileHeaderMapper;
    }

    @Override
    public PaymentFileHeaderDTO save(PaymentFileHeaderDTO paymentFileHeaderDTO) {
        log.debug("Request to save PaymentFileHeader : {}", paymentFileHeaderDTO);
        PaymentFileHeader paymentFileHeader = paymentFileHeaderMapper.toEntity(paymentFileHeaderDTO);
        paymentFileHeader = paymentFileHeaderRepository.save(paymentFileHeader);
        return paymentFileHeaderMapper.toDto(paymentFileHeader);
    }

    @Override
    public PaymentFileHeaderDTO update(PaymentFileHeaderDTO paymentFileHeaderDTO) {
        log.debug("Request to save PaymentFileHeader : {}", paymentFileHeaderDTO);
        PaymentFileHeader paymentFileHeader = paymentFileHeaderMapper.toEntity(paymentFileHeaderDTO);
        paymentFileHeader = paymentFileHeaderRepository.save(paymentFileHeader);
        return paymentFileHeaderMapper.toDto(paymentFileHeader);
    }

    @Override
    public Optional<PaymentFileHeaderDTO> partialUpdate(PaymentFileHeaderDTO paymentFileHeaderDTO) {
        log.debug("Request to partially update PaymentFileHeader : {}", paymentFileHeaderDTO);

        return paymentFileHeaderRepository
            .findById(paymentFileHeaderDTO.getId())
            .map(existingPaymentFileHeader -> {
                paymentFileHeaderMapper.partialUpdate(existingPaymentFileHeader, paymentFileHeaderDTO);

                return existingPaymentFileHeader;
            })
            .map(paymentFileHeaderRepository::save)
            .map(paymentFileHeaderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentFileHeaderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentFileHeaders");
        return paymentFileHeaderRepository.findAll(pageable).map(paymentFileHeaderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentFileHeaderDTO> findOne(Long id) {
        log.debug("Request to get PaymentFileHeader : {}", id);
        return paymentFileHeaderRepository.findById(id).map(paymentFileHeaderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentFileHeader : {}", id);
        paymentFileHeaderRepository.deleteById(id);
    }
}
