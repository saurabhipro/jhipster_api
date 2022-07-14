package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.repository.KhatedarRepository;
import com.melontech.landsys.service.criteria.KhatedarCriteria;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.mapper.KhatedarMapper;
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
 * Service for executing complex queries for {@link Khatedar} entities in the database.
 * The main input is a {@link KhatedarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KhatedarDTO} or a {@link Page} of {@link KhatedarDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KhatedarQueryService extends QueryService<Khatedar> {

    private final Logger log = LoggerFactory.getLogger(KhatedarQueryService.class);

    private final KhatedarRepository khatedarRepository;

    private final KhatedarMapper khatedarMapper;

    public KhatedarQueryService(KhatedarRepository khatedarRepository, KhatedarMapper khatedarMapper) {
        this.khatedarRepository = khatedarRepository;
        this.khatedarMapper = khatedarMapper;
    }

    /**
     * Return a {@link List} of {@link KhatedarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KhatedarDTO> findByCriteria(KhatedarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Khatedar> specification = createSpecification(criteria);
        return khatedarMapper.toDto(khatedarRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link KhatedarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KhatedarDTO> findByCriteria(KhatedarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Khatedar> specification = createSpecification(criteria);
        return khatedarRepository.findAll(specification, page).map(khatedarMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KhatedarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Khatedar> specification = createSpecification(criteria);
        return khatedarRepository.count(specification);
    }

    /**
     * Function to convert {@link KhatedarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Khatedar> createSpecification(KhatedarCriteria criteria) {
        Specification<Khatedar> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Khatedar_.id));
            }
            if (criteria.getCaseFileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaseFileNo(), Khatedar_.caseFileNo));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Khatedar_.remarks));
            }
            if (criteria.getKhatedarStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKhatedarStatus(), Khatedar_.khatedarStatus));
            }
            if (criteria.getProjectLandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectLandId(),
                            root -> root.join(Khatedar_.projectLand, JoinType.LEFT).get(ProjectLand_.id)
                        )
                    );
            }
            if (criteria.getCitizenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitizenId(), root -> root.join(Khatedar_.citizen, JoinType.LEFT).get(Citizen_.id))
                    );
            }
            if (criteria.getPaymentAdviceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentAdviceId(),
                            root -> root.join(Khatedar_.paymentAdvice, JoinType.LEFT).get(PaymentAdvice_.id)
                        )
                    );
            }
            if (criteria.getPaymentFileId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaymentFileId(),
                            root -> root.join(Khatedar_.paymentFile, JoinType.LEFT).get(PaymentFile_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
