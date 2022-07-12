package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.PaymentAdviceDetails;
import com.melontech.landsys.repository.PaymentAdviceDetailsRepository;
import com.melontech.landsys.service.PaymentAdviceDetailsService;
import com.melontech.landsys.service.dto.PaymentAdviceDetailsDTO;
import com.melontech.landsys.service.mapper.PaymentAdviceDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentAdviceDetails}.
 */
@Service
@Transactional
public class PaymentAdviceDetailsServiceImpl implements PaymentAdviceDetailsService {

    private final Logger log = LoggerFactory.getLogger(PaymentAdviceDetailsServiceImpl.class);

    private final PaymentAdviceDetailsRepository paymentAdviceDetailsRepository;

    private final PaymentAdviceDetailsMapper paymentAdviceDetailsMapper;

    public PaymentAdviceDetailsServiceImpl(
        PaymentAdviceDetailsRepository paymentAdviceDetailsRepository,
        PaymentAdviceDetailsMapper paymentAdviceDetailsMapper
    ) {
        this.paymentAdviceDetailsRepository = paymentAdviceDetailsRepository;
        this.paymentAdviceDetailsMapper = paymentAdviceDetailsMapper;
    }

    @Override
    public PaymentAdviceDetailsDTO save(PaymentAdviceDetailsDTO paymentAdviceDetailsDTO) {
        log.debug("Request to save PaymentAdviceDetails : {}", paymentAdviceDetailsDTO);
        PaymentAdviceDetails paymentAdviceDetails = paymentAdviceDetailsMapper.toEntity(paymentAdviceDetailsDTO);
        paymentAdviceDetails = paymentAdviceDetailsRepository.save(paymentAdviceDetails);
        return paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);
    }

    @Override
    public PaymentAdviceDetailsDTO update(PaymentAdviceDetailsDTO paymentAdviceDetailsDTO) {
        log.debug("Request to save PaymentAdviceDetails : {}", paymentAdviceDetailsDTO);
        PaymentAdviceDetails paymentAdviceDetails = paymentAdviceDetailsMapper.toEntity(paymentAdviceDetailsDTO);
        paymentAdviceDetails = paymentAdviceDetailsRepository.save(paymentAdviceDetails);
        return paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);
    }

    @Override
    public Optional<PaymentAdviceDetailsDTO> partialUpdate(PaymentAdviceDetailsDTO paymentAdviceDetailsDTO) {
        log.debug("Request to partially update PaymentAdviceDetails : {}", paymentAdviceDetailsDTO);

        return paymentAdviceDetailsRepository
            .findById(paymentAdviceDetailsDTO.getId())
            .map(existingPaymentAdviceDetails -> {
                paymentAdviceDetailsMapper.partialUpdate(existingPaymentAdviceDetails, paymentAdviceDetailsDTO);

                return existingPaymentAdviceDetails;
            })
            .map(paymentAdviceDetailsRepository::save)
            .map(paymentAdviceDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentAdviceDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentAdviceDetails");
        return paymentAdviceDetailsRepository.findAll(pageable).map(paymentAdviceDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentAdviceDetailsDTO> findOne(Long id) {
        log.debug("Request to get PaymentAdviceDetails : {}", id);
        return paymentAdviceDetailsRepository.findById(id).map(paymentAdviceDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentAdviceDetails : {}", id);
        paymentAdviceDetailsRepository.deleteById(id);
    }
}
