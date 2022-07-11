package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.PaymentFileRecon;
import com.melontech.landsys.repository.PaymentFileReconRepository;
import com.melontech.landsys.service.criteria.PaymentFileReconCriteria;
import com.melontech.landsys.service.dto.PaymentFileReconDTO;
import com.melontech.landsys.service.mapper.PaymentFileReconMapper;
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
 * Service for executing complex queries for {@link PaymentFileRecon} entities in the database.
 * The main input is a {@link PaymentFileReconCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentFileReconDTO} or a {@link Page} of {@link PaymentFileReconDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentFileReconQueryService extends QueryService<PaymentFileRecon> {

    private final Logger log = LoggerFactory.getLogger(PaymentFileReconQueryService.class);

    private final PaymentFileReconRepository paymentFileReconRepository;

    private final PaymentFileReconMapper paymentFileReconMapper;

    public PaymentFileReconQueryService(
        PaymentFileReconRepository paymentFileReconRepository,
        PaymentFileReconMapper paymentFileReconMapper
    ) {
        this.paymentFileReconRepository = paymentFileReconRepository;
        this.paymentFileReconMapper = paymentFileReconMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentFileReconDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentFileReconDTO> findByCriteria(PaymentFileReconCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentFileRecon> specification = createSpecification(criteria);
        return paymentFileReconMapper.toDto(paymentFileReconRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentFileReconDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentFileReconDTO> findByCriteria(PaymentFileReconCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentFileRecon> specification = createSpecification(criteria);
        return paymentFileReconRepository.findAll(specification, page).map(paymentFileReconMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentFileReconCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentFileRecon> specification = createSpecification(criteria);
        return paymentFileReconRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentFileReconCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentFileRecon> createSpecification(PaymentFileReconCriteria criteria) {
        Specification<PaymentFileRecon> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentFileRecon_.id));
            }
            if (criteria.getPrimaryHolderName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrimaryHolderName(), PaymentFileRecon_.primaryHolderName));
            }
            if (criteria.getPaymentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentAmount(), PaymentFileRecon_.paymentAmount));
            }
            if (criteria.getPaymentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentDate(), PaymentFileRecon_.paymentDate));
            }
            if (criteria.getUtrNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUtrNumber(), PaymentFileRecon_.utrNumber));
            }
            if (criteria.getReferenceNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReferenceNumber(), PaymentFileRecon_.referenceNumber));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), PaymentFileRecon_.paymentStatus));
            }
            if (criteria.getPaymentAdviceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentAdviceId(),
                            root -> root.join(PaymentFileRecon_.paymentAdvice, JoinType.LEFT).get(PaymentAdvice_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
