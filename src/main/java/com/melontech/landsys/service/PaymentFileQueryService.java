package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.repository.PaymentFileRepository;
import com.melontech.landsys.service.criteria.PaymentFileCriteria;
import com.melontech.landsys.service.dto.PaymentFileDTO;
import com.melontech.landsys.service.mapper.PaymentFileMapper;
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
 * Service for executing complex queries for {@link PaymentFile} entities in the database.
 * The main input is a {@link PaymentFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentFileDTO} or a {@link Page} of {@link PaymentFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentFileQueryService extends QueryService<PaymentFile> {

    private final Logger log = LoggerFactory.getLogger(PaymentFileQueryService.class);

    private final PaymentFileRepository paymentFileRepository;

    private final PaymentFileMapper paymentFileMapper;

    public PaymentFileQueryService(PaymentFileRepository paymentFileRepository, PaymentFileMapper paymentFileMapper) {
        this.paymentFileRepository = paymentFileRepository;
        this.paymentFileMapper = paymentFileMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentFileDTO> findByCriteria(PaymentFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentFile> specification = createSpecification(criteria);
        return paymentFileMapper.toDto(paymentFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentFileDTO> findByCriteria(PaymentFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentFile> specification = createSpecification(criteria);
        return paymentFileRepository.findAll(specification, page).map(paymentFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentFile> specification = createSpecification(criteria);
        return paymentFileRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentFile> createSpecification(PaymentFileCriteria criteria) {
        Specification<PaymentFile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentFile_.id));
            }
            if (criteria.getPaymentFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentFileId(), PaymentFile_.paymentFileId));
            }
            if (criteria.getTotalPaymentAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalPaymentAmount(), PaymentFile_.totalPaymentAmount));
            }
            if (criteria.getPaymentFileDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentFileDate(), PaymentFile_.paymentFileDate));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), PaymentFile_.paymentStatus));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), PaymentFile_.bankName));
            }
            if (criteria.getIfscCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfscCode(), PaymentFile_.ifscCode));
            }
            if (criteria.getPaymentMode() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMode(), PaymentFile_.paymentMode));
            }
            if (criteria.getKhatedarId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKhatedarId(),
                            root -> root.join(PaymentFile_.khatedar, JoinType.LEFT).get(Khatedar_.id)
                        )
                    );
            }
            if (criteria.getPaymentAdviceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentAdviceId(),
                            root -> root.join(PaymentFile_.paymentAdvice, JoinType.LEFT).get(PaymentAdvice_.id)
                        )
                    );
            }
            if (criteria.getProjectLandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectLandId(),
                            root -> root.join(PaymentFile_.projectLand, JoinType.LEFT).get(ProjectLand_.id)
                        )
                    );
            }
            if (criteria.getSurveyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSurveyId(), root -> root.join(PaymentFile_.survey, JoinType.LEFT).get(Survey_.id))
                    );
            }
            if (criteria.getBankId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBankId(), root -> root.join(PaymentFile_.bank, JoinType.LEFT).get(Bank_.id))
                    );
            }
            if (criteria.getBankBranchId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBankBranchId(),
                            root -> root.join(PaymentFile_.bankBranch, JoinType.LEFT).get(BankBranch_.id)
                        )
                    );
            }
            if (criteria.getLandCompensationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLandCompensationId(),
                            root -> root.join(PaymentFile_.landCompensation, JoinType.LEFT).get(LandCompensation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
