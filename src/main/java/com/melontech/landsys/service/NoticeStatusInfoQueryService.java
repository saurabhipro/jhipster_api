package com.melontech.landsys.service;

import com.melontech.landsys.domain.*; // for static metamodels
import com.melontech.landsys.domain.NoticeStatusInfo;
import com.melontech.landsys.repository.NoticeStatusInfoRepository;
import com.melontech.landsys.service.criteria.NoticeStatusInfoCriteria;
import com.melontech.landsys.service.dto.NoticeStatusInfoDTO;
import com.melontech.landsys.service.mapper.NoticeStatusInfoMapper;
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
 * Service for executing complex queries for {@link NoticeStatusInfo} entities in the database.
 * The main input is a {@link NoticeStatusInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoticeStatusInfoDTO} or a {@link Page} of {@link NoticeStatusInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoticeStatusInfoQueryService extends QueryService<NoticeStatusInfo> {

    private final Logger log = LoggerFactory.getLogger(NoticeStatusInfoQueryService.class);

    private final NoticeStatusInfoRepository noticeStatusInfoRepository;

    private final NoticeStatusInfoMapper noticeStatusInfoMapper;

    public NoticeStatusInfoQueryService(
        NoticeStatusInfoRepository noticeStatusInfoRepository,
        NoticeStatusInfoMapper noticeStatusInfoMapper
    ) {
        this.noticeStatusInfoRepository = noticeStatusInfoRepository;
        this.noticeStatusInfoMapper = noticeStatusInfoMapper;
    }

    /**
     * Return a {@link List} of {@link NoticeStatusInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoticeStatusInfoDTO> findByCriteria(NoticeStatusInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NoticeStatusInfo> specification = createSpecification(criteria);
        return noticeStatusInfoMapper.toDto(noticeStatusInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoticeStatusInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoticeStatusInfoDTO> findByCriteria(NoticeStatusInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NoticeStatusInfo> specification = createSpecification(criteria);
        return noticeStatusInfoRepository.findAll(specification, page).map(noticeStatusInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoticeStatusInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NoticeStatusInfo> specification = createSpecification(criteria);
        return noticeStatusInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link NoticeStatusInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NoticeStatusInfo> createSpecification(NoticeStatusInfoCriteria criteria) {
        Specification<NoticeStatusInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NoticeStatusInfo_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), NoticeStatusInfo_.status));
            }
            if (criteria.getKhatedarId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKhatedarId(),
                            root -> root.join(NoticeStatusInfo_.khatedars, JoinType.LEFT).get(Khatedar_.id)
                        )
                    );
            }
            if (criteria.getProjectLandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectLandId(),
                            root -> root.join(NoticeStatusInfo_.projectLands, JoinType.LEFT).get(ProjectLand_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
