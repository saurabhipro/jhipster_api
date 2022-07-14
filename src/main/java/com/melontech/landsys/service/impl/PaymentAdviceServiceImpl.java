package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.repository.PaymentAdviceRepository;
import com.melontech.landsys.service.PaymentAdviceService;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.mapper.PaymentAdviceMapper;
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
 * Service Implementation for managing {@link PaymentAdvice}.
 */
@Service
@Transactional
public class PaymentAdviceServiceImpl implements PaymentAdviceService {

    private final Logger log = LoggerFactory.getLogger(PaymentAdviceServiceImpl.class);

    private final PaymentAdviceRepository paymentAdviceRepository;

    private final PaymentAdviceMapper paymentAdviceMapper;

    public PaymentAdviceServiceImpl(PaymentAdviceRepository paymentAdviceRepository, PaymentAdviceMapper paymentAdviceMapper) {
        this.paymentAdviceRepository = paymentAdviceRepository;
        this.paymentAdviceMapper = paymentAdviceMapper;
    }

    @Override
    public PaymentAdviceDTO save(PaymentAdviceDTO paymentAdviceDTO) {
        log.debug("Request to save PaymentAdvice : {}", paymentAdviceDTO);
        PaymentAdvice paymentAdvice = paymentAdviceMapper.toEntity(paymentAdviceDTO);
        paymentAdvice = paymentAdviceRepository.save(paymentAdvice);
        return paymentAdviceMapper.toDto(paymentAdvice);
    }

    @Override
    public PaymentAdviceDTO update(PaymentAdviceDTO paymentAdviceDTO) {
        log.debug("Request to save PaymentAdvice : {}", paymentAdviceDTO);
        PaymentAdvice paymentAdvice = paymentAdviceMapper.toEntity(paymentAdviceDTO);
        paymentAdvice = paymentAdviceRepository.save(paymentAdvice);
        return paymentAdviceMapper.toDto(paymentAdvice);
    }

    @Override
    public Optional<PaymentAdviceDTO> partialUpdate(PaymentAdviceDTO paymentAdviceDTO) {
        log.debug("Request to partially update PaymentAdvice : {}", paymentAdviceDTO);

        return paymentAdviceRepository
            .findById(paymentAdviceDTO.getId())
            .map(existingPaymentAdvice -> {
                paymentAdviceMapper.partialUpdate(existingPaymentAdvice, paymentAdviceDTO);

                return existingPaymentAdvice;
            })
            .map(paymentAdviceRepository::save)
            .map(paymentAdviceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentAdviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentAdvices");
        return paymentAdviceRepository.findAll(pageable).map(paymentAdviceMapper::toDto);
    }

    /**
     *  Get all the paymentAdvices where PaymentFileRecon is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentAdviceDTO> findAllWherePaymentFileReconIsNull() {
        log.debug("Request to get all paymentAdvices where PaymentFileRecon is null");
        return StreamSupport
            .stream(paymentAdviceRepository.findAll().spliterator(), false)
            .filter(paymentAdvice -> paymentAdvice.getPaymentFileRecon() == null)
            .map(paymentAdviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the paymentAdvices where PaymentFile is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentAdviceDTO> findAllWherePaymentFileIsNull() {
        log.debug("Request to get all paymentAdvices where PaymentFile is null");
        return StreamSupport
            .stream(paymentAdviceRepository.findAll().spliterator(), false)
            .filter(paymentAdvice -> paymentAdvice.getPaymentFile() == null)
            .map(paymentAdviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentAdviceDTO> findOne(Long id) {
        log.debug("Request to get PaymentAdvice : {}", id);
        return paymentAdviceRepository.findById(id).map(paymentAdviceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentAdvice : {}", id);
        paymentAdviceRepository.deleteById(id);
    }
}
