package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.repository.PaymentAdviceRepository;
import com.melontech.landsys.service.criteria.PaymentAdviceCriteria;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.mapper.PaymentAdviceMapper;
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
 * Service for executing complex queries for {@link PaymentAdvice} entities in the database.
 * The main input is a {@link PaymentAdviceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentAdviceDTO} or a {@link Page} of {@link PaymentAdviceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentAdviceQueryService extends QueryService<PaymentAdvice> {

    private final Logger log = LoggerFactory.getLogger(PaymentAdviceQueryService.class);

    private final PaymentAdviceRepository paymentAdviceRepository;

    private final PaymentAdviceMapper paymentAdviceMapper;

    public PaymentAdviceQueryService(PaymentAdviceRepository paymentAdviceRepository, PaymentAdviceMapper paymentAdviceMapper) {
        this.paymentAdviceRepository = paymentAdviceRepository;
        this.paymentAdviceMapper = paymentAdviceMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentAdviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentAdviceDTO> findByCriteria(PaymentAdviceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentAdvice> specification = createSpecification(criteria);
        return paymentAdviceMapper.toDto(paymentAdviceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentAdviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentAdviceDTO> findByCriteria(PaymentAdviceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentAdvice> specification = createSpecification(criteria);
        return paymentAdviceRepository.findAll(specification, page).map(paymentAdviceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentAdviceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentAdvice> specification = createSpecification(criteria);
        return paymentAdviceRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentAdviceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentAdvice> createSpecification(PaymentAdviceCriteria criteria) {
        Specification<PaymentAdvice> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentAdvice_.id));
            }
            if (criteria.getAccountHolderName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccountHolderName(), PaymentAdvice_.accountHolderName));
            }
            if (criteria.getPaymentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentAmount(), PaymentAdvice_.paymentAmount));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), PaymentAdvice_.bankName));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), PaymentAdvice_.accountNumber));
            }
            if (criteria.getIfscCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfscCode(), PaymentAdvice_.ifscCode));
            }
            if (criteria.getCheckNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckNumber(), PaymentAdvice_.checkNumber));
            }
            if (criteria.getMicrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMicrCode(), PaymentAdvice_.micrCode));
            }
            if (criteria.getPaymentAdviceType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentAdviceType(), PaymentAdvice_.paymentAdviceType));
            }
            if (criteria.getReferenceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceNumber(), PaymentAdvice_.referenceNumber));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), PaymentAdvice_.paymentStatus));
            }
            if (criteria.getHisssaType() != null) {
                specification = specification.and(buildSpecification(criteria.getHisssaType(), PaymentAdvice_.hisssaType));
            }
            if (criteria.getLandCompensationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLandCompensationId(),
                            root -> root.join(PaymentAdvice_.landCompensation, JoinType.LEFT).get(LandCompensation_.id)
                        )
                    );
            }
            if (criteria.getProjectLandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectLandId(),
                            root -> root.join(PaymentAdvice_.projectLand, JoinType.LEFT).get(ProjectLand_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
