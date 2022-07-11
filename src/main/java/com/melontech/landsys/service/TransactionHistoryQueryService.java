package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.TransactionHistory;
import com.melontech.landsys.repository.TransactionHistoryRepository;
import com.melontech.landsys.service.criteria.TransactionHistoryCriteria;
import com.melontech.landsys.service.dto.TransactionHistoryDTO;
import com.melontech.landsys.service.mapper.TransactionHistoryMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TransactionHistory} entities in the database.
 * The main input is a {@link TransactionHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionHistoryDTO} or a {@link Page} of {@link TransactionHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionHistoryQueryService extends QueryService<TransactionHistory> {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoryQueryService.class);

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final TransactionHistoryMapper transactionHistoryMapper;

    public TransactionHistoryQueryService(
        TransactionHistoryRepository transactionHistoryRepository,
        TransactionHistoryMapper transactionHistoryMapper
    ) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryMapper = transactionHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionHistoryDTO> findByCriteria(TransactionHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionHistory> specification = createSpecification(criteria);
        return transactionHistoryMapper.toDto(transactionHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionHistoryDTO> findByCriteria(TransactionHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionHistory> specification = createSpecification(criteria);
        return transactionHistoryRepository.findAll(specification, page).map(transactionHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionHistory> specification = createSpecification(criteria);
        return transactionHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransactionHistory> createSpecification(TransactionHistoryCriteria criteria) {
        Specification<TransactionHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransactionHistory_.id));
            }
            if (criteria.getProjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectName(), TransactionHistory_.projectName));
            }
            if (criteria.getKhasraNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKhasraNumber(), TransactionHistory_.khasraNumber));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), TransactionHistory_.state));
            }
            if (criteria.getCitizenName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCitizenName(), TransactionHistory_.citizenName));
            }
            if (criteria.getCitizenAadhar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCitizenAadhar(), TransactionHistory_.citizenAadhar));
            }
            if (criteria.getSurveyerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurveyerName(), TransactionHistory_.surveyerName));
            }
            if (criteria.getLandValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandValue(), TransactionHistory_.landValue));
            }
            if (criteria.getPaymentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentAmount(), TransactionHistory_.paymentAmount));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), TransactionHistory_.accountNumber));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), TransactionHistory_.bankName));
            }
            if (criteria.getTransactionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionId(), TransactionHistory_.transactionId));
            }
            if (criteria.getTransactionType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTransactionType(), TransactionHistory_.transactionType));
            }
            if (criteria.getEventType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventType(), TransactionHistory_.eventType));
            }
            if (criteria.getEventStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getEventStatus(), TransactionHistory_.eventStatus));
            }
            if (criteria.getApprover1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprover1(), TransactionHistory_.approver1));
            }
            if (criteria.getApprover2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprover2(), TransactionHistory_.approver2));
            }
            if (criteria.getApprover3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprover3(), TransactionHistory_.approver3));
            }
        }
        return specification;
    }
}
