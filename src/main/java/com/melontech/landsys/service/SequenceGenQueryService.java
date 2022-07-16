package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.SequenceGen;
import com.melontech.landsys.repository.SequenceGenRepository;
import com.melontech.landsys.service.criteria.SequenceGenCriteria;
import com.melontech.landsys.service.dto.SequenceGenDTO;
import com.melontech.landsys.service.mapper.SequenceGenMapper;
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
 * Service for executing complex queries for {@link SequenceGen} entities in the database.
 * The main input is a {@link SequenceGenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceGenDTO} or a {@link Page} of {@link SequenceGenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceGenQueryService extends QueryService<SequenceGen> {

    private final Logger log = LoggerFactory.getLogger(SequenceGenQueryService.class);

    private final SequenceGenRepository sequenceGenRepository;

    private final SequenceGenMapper sequenceGenMapper;

    public SequenceGenQueryService(SequenceGenRepository sequenceGenRepository, SequenceGenMapper sequenceGenMapper) {
        this.sequenceGenRepository = sequenceGenRepository;
        this.sequenceGenMapper = sequenceGenMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceGenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceGenDTO> findByCriteria(SequenceGenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceGen> specification = createSpecification(criteria);
        return sequenceGenMapper.toDto(sequenceGenRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceGenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceGenDTO> findByCriteria(SequenceGenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceGen> specification = createSpecification(criteria);
        return sequenceGenRepository.findAll(specification, page).map(sequenceGenMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceGenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceGen> specification = createSpecification(criteria);
        return sequenceGenRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceGenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceGen> createSpecification(SequenceGenCriteria criteria) {
        Specification<SequenceGen> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceGen_.id));
            }
            if (criteria.getSeqType() != null) {
                specification = specification.and(buildSpecification(criteria.getSeqType(), SequenceGen_.seqType));
            }
            if (criteria.getLatestSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatestSequence(), SequenceGen_.latestSequence));
            }
            if (criteria.getSequenceSuffix() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSequenceSuffix(), SequenceGen_.sequenceSuffix));
            }
        }
        return specification;
    }
}
