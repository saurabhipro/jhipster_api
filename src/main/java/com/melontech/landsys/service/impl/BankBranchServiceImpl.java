package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.BankBranch;
import com.melontech.landsys.repository.BankBranchRepository;
import com.melontech.landsys.service.BankBranchService;
import com.melontech.landsys.service.dto.BankBranchDTO;
import com.melontech.landsys.service.mapper.BankBranchMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankBranch}.
 */
@Service
@Transactional
public class BankBranchServiceImpl implements BankBranchService {

    private final Logger log = LoggerFactory.getLogger(BankBranchServiceImpl.class);

    private final BankBranchRepository bankBranchRepository;

    private final BankBranchMapper bankBranchMapper;

    public BankBranchServiceImpl(BankBranchRepository bankBranchRepository, BankBranchMapper bankBranchMapper) {
        this.bankBranchRepository = bankBranchRepository;
        this.bankBranchMapper = bankBranchMapper;
    }

    @Override
    public BankBranchDTO save(BankBranchDTO bankBranchDTO) {
        log.debug("Request to save BankBranch : {}", bankBranchDTO);
        BankBranch bankBranch = bankBranchMapper.toEntity(bankBranchDTO);
        bankBranch = bankBranchRepository.save(bankBranch);
        return bankBranchMapper.toDto(bankBranch);
    }

    @Override
    public BankBranchDTO update(BankBranchDTO bankBranchDTO) {
        log.debug("Request to save BankBranch : {}", bankBranchDTO);
        BankBranch bankBranch = bankBranchMapper.toEntity(bankBranchDTO);
        bankBranch = bankBranchRepository.save(bankBranch);
        return bankBranchMapper.toDto(bankBranch);
    }

    @Override
    public Optional<BankBranchDTO> partialUpdate(BankBranchDTO bankBranchDTO) {
        log.debug("Request to partially update BankBranch : {}", bankBranchDTO);

        return bankBranchRepository
            .findById(bankBranchDTO.getId())
            .map(existingBankBranch -> {
                bankBranchMapper.partialUpdate(existingBankBranch, bankBranchDTO);

                return existingBankBranch;
            })
            .map(bankBranchRepository::save)
            .map(bankBranchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankBranchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankBranches");
        return bankBranchRepository.findAll(pageable).map(bankBranchMapper::toDto);
    }

    public Page<BankBranchDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bankBranchRepository.findAllWithEagerRelationships(pageable).map(bankBranchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankBranchDTO> findOne(Long id) {
        log.debug("Request to get BankBranch : {}", id);
        return bankBranchRepository.findOneWithEagerRelationships(id).map(bankBranchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankBranch : {}", id);
        bankBranchRepository.deleteById(id);
    }
}
