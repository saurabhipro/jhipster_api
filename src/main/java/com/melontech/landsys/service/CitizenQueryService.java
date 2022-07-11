package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.repository.CitizenRepository;
import com.melontech.landsys.service.criteria.CitizenCriteria;
import com.melontech.landsys.service.dto.CitizenDTO;
import com.melontech.landsys.service.mapper.CitizenMapper;
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
 * Service for executing complex queries for {@link Citizen} entities in the database.
 * The main input is a {@link CitizenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitizenDTO} or a {@link Page} of {@link CitizenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitizenQueryService extends QueryService<Citizen> {

    private final Logger log = LoggerFactory.getLogger(CitizenQueryService.class);

    private final CitizenRepository citizenRepository;

    private final CitizenMapper citizenMapper;

    public CitizenQueryService(CitizenRepository citizenRepository, CitizenMapper citizenMapper) {
        this.citizenRepository = citizenRepository;
        this.citizenMapper = citizenMapper;
    }

    /**
     * Return a {@link List} of {@link CitizenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitizenDTO> findByCriteria(CitizenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Citizen> specification = createSpecification(criteria);
        return citizenMapper.toDto(citizenRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CitizenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitizenDTO> findByCriteria(CitizenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Citizen> specification = createSpecification(criteria);
        return citizenRepository.findAll(specification, page).map(citizenMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitizenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Citizen> specification = createSpecification(criteria);
        return citizenRepository.count(specification);
    }

    /**
     * Function to convert {@link CitizenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Citizen> createSpecification(CitizenCriteria criteria) {
        Specification<Citizen> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Citizen_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Citizen_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Citizen_.address));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), Citizen_.accountNumber));
            }
            if (criteria.getFatherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherName(), Citizen_.fatherName));
            }
            if (criteria.getSpouseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpouseName(), Citizen_.spouseName));
            }
            if (criteria.getSuccessorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuccessorName(), Citizen_.successorName));
            }
            if (criteria.getAadhar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadhar(), Citizen_.aadhar));
            }
            if (criteria.getPan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPan(), Citizen_.pan));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNo(), Citizen_.accountNo));
            }
            if (criteria.getBankBranchId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBankBranchId(),
                            root -> root.join(Citizen_.bankBranch, JoinType.LEFT).get(BankBranch_.id)
                        )
                    );
            }
            if (criteria.getKhatedarId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getKhatedarId(), root -> root.join(Citizen_.khatedars, JoinType.LEFT).get(Khatedar_.id))
                    );
            }
        }
        return specification;
    }
}
