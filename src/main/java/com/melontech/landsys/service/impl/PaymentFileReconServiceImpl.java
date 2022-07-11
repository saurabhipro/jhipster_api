package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.PaymentFileRecon;
import com.melontech.landsys.repository.PaymentFileReconRepository;
import com.melontech.landsys.service.PaymentFileReconService;
import com.melontech.landsys.service.dto.PaymentFileReconDTO;
import com.melontech.landsys.service.mapper.PaymentFileReconMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentFileRecon}.
 */
@Service
@Transactional
public class PaymentFileReconServiceImpl implements PaymentFileReconService {

    private final Logger log = LoggerFactory.getLogger(PaymentFileReconServiceImpl.class);

    private final PaymentFileReconRepository paymentFileReconRepository;

    private final PaymentFileReconMapper paymentFileReconMapper;

    public PaymentFileReconServiceImpl(
        PaymentFileReconRepository paymentFileReconRepository,
        PaymentFileReconMapper paymentFileReconMapper
    ) {
        this.paymentFileReconRepository = paymentFileReconRepository;
        this.paymentFileReconMapper = paymentFileReconMapper;
    }

    @Override
    public PaymentFileReconDTO save(PaymentFileReconDTO paymentFileReconDTO) {
        log.debug("Request to save PaymentFileRecon : {}", paymentFileReconDTO);
        PaymentFileRecon paymentFileRecon = paymentFileReconMapper.toEntity(paymentFileReconDTO);
        paymentFileRecon = paymentFileReconRepository.save(paymentFileRecon);
        return paymentFileReconMapper.toDto(paymentFileRecon);
    }

    @Override
    public PaymentFileReconDTO update(PaymentFileReconDTO paymentFileReconDTO) {
        log.debug("Request to save PaymentFileRecon : {}", paymentFileReconDTO);
        PaymentFileRecon paymentFileRecon = paymentFileReconMapper.toEntity(paymentFileReconDTO);
        paymentFileRecon = paymentFileReconRepository.save(paymentFileRecon);
        return paymentFileReconMapper.toDto(paymentFileRecon);
    }

    @Override
    public Optional<PaymentFileReconDTO> partialUpdate(PaymentFileReconDTO paymentFileReconDTO) {
        log.debug("Request to partially update PaymentFileRecon : {}", paymentFileReconDTO);

        return paymentFileReconRepository
            .findById(paymentFileReconDTO.getId())
            .map(existingPaymentFileRecon -> {
                paymentFileReconMapper.partialUpdate(existingPaymentFileRecon, paymentFileReconDTO);

                return existingPaymentFileRecon;
            })
            .map(paymentFileReconRepository::save)
            .map(paymentFileReconMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentFileReconDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentFileRecons");
        return paymentFileReconRepository.findAll(pageable).map(paymentFileReconMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentFileReconDTO> findOne(Long id) {
        log.debug("Request to get PaymentFileRecon : {}", id);
        return paymentFileReconRepository.findById(id).map(paymentFileReconMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentFileRecon : {}", id);
        paymentFileReconRepository.deleteById(id);
    }
}
