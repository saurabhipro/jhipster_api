package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.TransactionHistory;
import com.melontech.landsys.repository.TransactionHistoryRepository;
import com.melontech.landsys.service.TransactionHistoryService;
import com.melontech.landsys.service.dto.TransactionHistoryDTO;
import com.melontech.landsys.service.mapper.TransactionHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransactionHistory}.
 */
@Service
@Transactional
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final TransactionHistoryMapper transactionHistoryMapper;

    public TransactionHistoryServiceImpl(
        TransactionHistoryRepository transactionHistoryRepository,
        TransactionHistoryMapper transactionHistoryMapper
    ) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryMapper = transactionHistoryMapper;
    }

    @Override
    public TransactionHistoryDTO save(TransactionHistoryDTO transactionHistoryDTO) {
        log.debug("Request to save TransactionHistory : {}", transactionHistoryDTO);
        TransactionHistory transactionHistory = transactionHistoryMapper.toEntity(transactionHistoryDTO);
        transactionHistory = transactionHistoryRepository.save(transactionHistory);
        return transactionHistoryMapper.toDto(transactionHistory);
    }

    @Override
    public TransactionHistoryDTO update(TransactionHistoryDTO transactionHistoryDTO) {
        log.debug("Request to save TransactionHistory : {}", transactionHistoryDTO);
        TransactionHistory transactionHistory = transactionHistoryMapper.toEntity(transactionHistoryDTO);
        transactionHistory = transactionHistoryRepository.save(transactionHistory);
        return transactionHistoryMapper.toDto(transactionHistory);
    }

    @Override
    public Optional<TransactionHistoryDTO> partialUpdate(TransactionHistoryDTO transactionHistoryDTO) {
        log.debug("Request to partially update TransactionHistory : {}", transactionHistoryDTO);

        return transactionHistoryRepository
            .findById(transactionHistoryDTO.getId())
            .map(existingTransactionHistory -> {
                transactionHistoryMapper.partialUpdate(existingTransactionHistory, transactionHistoryDTO);

                return existingTransactionHistory;
            })
            .map(transactionHistoryRepository::save)
            .map(transactionHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionHistories");
        return transactionHistoryRepository.findAll(pageable).map(transactionHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionHistoryDTO> findOne(Long id) {
        log.debug("Request to get TransactionHistory : {}", id);
        return transactionHistoryRepository.findById(id).map(transactionHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionHistory : {}", id);
        transactionHistoryRepository.deleteById(id);
    }
}
