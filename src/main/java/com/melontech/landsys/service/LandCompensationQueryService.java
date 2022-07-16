package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.repository.LandCompensationRepository;
import com.melontech.landsys.service.criteria.LandCompensationCriteria;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.mapper.LandCompensationMapper;
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
 * Service for executing complex queries for {@link LandCompensation} entities in the database.
 * The main input is a {@link LandCompensationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LandCompensationDTO} or a {@link Page} of {@link LandCompensationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LandCompensationQueryService extends QueryService<LandCompensation> {

    private final Logger log = LoggerFactory.getLogger(LandCompensationQueryService.class);

    private final LandCompensationRepository landCompensationRepository;

    private final LandCompensationMapper landCompensationMapper;

    public LandCompensationQueryService(
        LandCompensationRepository landCompensationRepository,
        LandCompensationMapper landCompensationMapper
    ) {
        this.landCompensationRepository = landCompensationRepository;
        this.landCompensationMapper = landCompensationMapper;
    }

    /**
     * Return a {@link List} of {@link LandCompensationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LandCompensationDTO> findByCriteria(LandCompensationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LandCompensation> specification = createSpecification(criteria);
        return landCompensationMapper.toDto(landCompensationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LandCompensationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LandCompensationDTO> findByCriteria(LandCompensationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LandCompensation> specification = createSpecification(criteria);
        return landCompensationRepository.findAll(specification, page).map(landCompensationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LandCompensationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LandCompensation> specification = createSpecification(criteria);
        return landCompensationRepository.count(specification);
    }

    /**
     * Function to convert {@link LandCompensationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LandCompensation> createSpecification(LandCompensationCriteria criteria) {
        Specification<LandCompensation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LandCompensation_.id));
            }
            if (criteria.getHissaType() != null) {
                specification = specification.and(buildSpecification(criteria.getHissaType(), LandCompensation_.hissaType));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArea(), LandCompensation_.area));
            }
            if (criteria.getSharePercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSharePercentage(), LandCompensation_.sharePercentage));
            }
            if (criteria.getLandMarketValue() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLandMarketValue(), LandCompensation_.landMarketValue));
            }
            if (criteria.getStructuralCompensation() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getStructuralCompensation(), LandCompensation_.structuralCompensation)
                    );
            }
            if (criteria.getHorticultureCompensation() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getHorticultureCompensation(), LandCompensation_.horticultureCompensation)
                    );
            }
            if (criteria.getForestCompensation() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getForestCompensation(), LandCompensation_.forestCompensation));
            }
            if (criteria.getSolatiumMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolatiumMoney(), LandCompensation_.solatiumMoney));
            }
            if (criteria.getAdditionalCompensation() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAdditionalCompensation(), LandCompensation_.additionalCompensation)
                    );
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LandCompensation_.status));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), LandCompensation_.orderDate));
            }
            if (criteria.getPaymentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentAmount(), LandCompensation_.paymentAmount));
            }
            if (criteria.getInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestRate(), LandCompensation_.interestRate));
            }
            if (criteria.getInterestDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestDays(), LandCompensation_.interestDays));
            }
            if (criteria.getTransactionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionId(), LandCompensation_.transactionId));
            }
            if (criteria.getProjectLandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectLandId(),
                            root -> root.join(LandCompensation_.projectLand, JoinType.LEFT).get(ProjectLand_.id)
                        )
                    );
            }
            if (criteria.getSurveyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSurveyId(),
                            root -> root.join(LandCompensation_.survey, JoinType.LEFT).get(Survey_.id)
                        )
                    );
            }
            if (criteria.getPaymentAdviceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentAdviceId(),
                            root -> root.join(LandCompensation_.paymentAdvices, JoinType.LEFT).get(PaymentAdvice_.id)
                        )
                    );
            }
            if (criteria.getPaymentFileId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentFileId(),
                            root -> root.join(LandCompensation_.paymentFiles, JoinType.LEFT).get(PaymentFile_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
