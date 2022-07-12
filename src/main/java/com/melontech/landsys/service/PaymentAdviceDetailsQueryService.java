package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.PaymentAdviceDetails;
import com.melontech.landsys.repository.PaymentAdviceDetailsRepository;
import com.melontech.landsys.service.criteria.PaymentAdviceDetailsCriteria;
import com.melontech.landsys.service.dto.PaymentAdviceDetailsDTO;
import com.melontech.landsys.service.mapper.PaymentAdviceDetailsMapper;
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
 * Service for executing complex queries for {@link PaymentAdviceDetails} entities in the database.
 * The main input is a {@link PaymentAdviceDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentAdviceDetailsDTO} or a {@link Page} of {@link PaymentAdviceDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentAdviceDetailsQueryService extends QueryService<PaymentAdviceDetails> {

    private final Logger log = LoggerFactory.getLogger(PaymentAdviceDetailsQueryService.class);

    private final PaymentAdviceDetailsRepository paymentAdviceDetailsRepository;

    private final PaymentAdviceDetailsMapper paymentAdviceDetailsMapper;

    public PaymentAdviceDetailsQueryService(
        PaymentAdviceDetailsRepository paymentAdviceDetailsRepository,
        PaymentAdviceDetailsMapper paymentAdviceDetailsMapper
    ) {
        this.paymentAdviceDetailsRepository = paymentAdviceDetailsRepository;
        this.paymentAdviceDetailsMapper = paymentAdviceDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentAdviceDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentAdviceDetailsDTO> findByCriteria(PaymentAdviceDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentAdviceDetails> specification = createSpecification(criteria);
        return paymentAdviceDetailsMapper.toDto(paymentAdviceDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentAdviceDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentAdviceDetailsDTO> findByCriteria(PaymentAdviceDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentAdviceDetails> specification = createSpecification(criteria);
        return paymentAdviceDetailsRepository.findAll(specification, page).map(paymentAdviceDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentAdviceDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentAdviceDetails> specification = createSpecification(criteria);
        return paymentAdviceDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentAdviceDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentAdviceDetails> createSpecification(PaymentAdviceDetailsCriteria criteria) {
        Specification<PaymentAdviceDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentAdviceDetails_.id));
            }
            if (criteria.getLandOwners() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandOwners(), PaymentAdviceDetails_.landOwners));
            }
            if (criteria.getHissaType() != null) {
                specification = specification.and(buildSpecification(criteria.getHissaType(), PaymentAdviceDetails_.hissaType));
            }
            if (criteria.getPaymentAdviceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentAdviceId(),
                            root -> root.join(PaymentAdviceDetails_.paymentAdvice, JoinType.LEFT).get(PaymentAdvice_.id)
                        )
                    );
            }
            if (criteria.getProjectLandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectLandId(),
                            root -> root.join(PaymentAdviceDetails_.projectLand, JoinType.LEFT).get(ProjectLand_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
