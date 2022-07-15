package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.repository.SurveyRepository;
import com.melontech.landsys.service.criteria.SurveyCriteria;
import com.melontech.landsys.service.dto.SurveyDTO;
import com.melontech.landsys.service.mapper.SurveyMapper;
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
 * Service for executing complex queries for {@link Survey} entities in the database.
 * The main input is a {@link SurveyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SurveyDTO} or a {@link Page} of {@link SurveyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SurveyQueryService extends QueryService<Survey> {

    private final Logger log = LoggerFactory.getLogger(SurveyQueryService.class);

    private final SurveyRepository surveyRepository;

    private final SurveyMapper surveyMapper;

    public SurveyQueryService(SurveyRepository surveyRepository, SurveyMapper surveyMapper) {
        this.surveyRepository = surveyRepository;
        this.surveyMapper = surveyMapper;
    }

    /**
     * Return a {@link List} of {@link SurveyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SurveyDTO> findByCriteria(SurveyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Survey> specification = createSpecification(criteria);
        return surveyMapper.toDto(surveyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SurveyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SurveyDTO> findByCriteria(SurveyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Survey> specification = createSpecification(criteria);
        return surveyRepository.findAll(specification, page).map(surveyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SurveyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Survey> specification = createSpecification(criteria);
        return surveyRepository.count(specification);
    }

    /**
     * Function to convert {@link SurveyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Survey> createSpecification(SurveyCriteria criteria) {
        Specification<Survey> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Survey_.id));
            }
            if (criteria.getSurveyor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurveyor(), Survey_.surveyor));
            }
            if (criteria.getHissaType() != null) {
                specification = specification.and(buildSpecification(criteria.getHissaType(), Survey_.hissaType));
            }
            if (criteria.getSharePercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSharePercentage(), Survey_.sharePercentage));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArea(), Survey_.area));
            }
            if (criteria.getLandMarketValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLandMarketValue(), Survey_.landMarketValue));
            }
            if (criteria.getStructuralValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStructuralValue(), Survey_.structuralValue));
            }
            if (criteria.getHorticultureValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHorticultureValue(), Survey_.horticultureValue));
            }
            if (criteria.getForestValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForestValue(), Survey_.forestValue));
            }
            if (criteria.getDistanceFromCity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistanceFromCity(), Survey_.distanceFromCity));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Survey_.remarks));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Survey_.status));
            }
            if (criteria.getProjectLandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectLandId(),
                            root -> root.join(Survey_.projectLand, JoinType.LEFT).get(ProjectLand_.id)
                        )
                    );
            }
            if (criteria.getVillageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVillageId(), root -> root.join(Survey_.village, JoinType.LEFT).get(Village_.id))
                    );
            }
            if (criteria.getLandCompensationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLandCompensationId(),
                            root -> root.join(Survey_.landCompensation, JoinType.LEFT).get(LandCompensation_.id)
                        )
                    );
            }
            if (criteria.getPaymentAdviceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentAdviceId(),
                            root -> root.join(Survey_.paymentAdvices, JoinType.LEFT).get(PaymentAdvice_.id)
                        )
                    );
            }
            if (criteria.getPaymentFileId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentFileId(),
                            root -> root.join(Survey_.paymentFiles, JoinType.LEFT).get(PaymentFile_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
