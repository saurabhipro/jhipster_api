package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.NoticeStatusInfoRepository;
import com.melontech.landsys.service.NoticeStatusInfoQueryService;
import com.melontech.landsys.service.NoticeStatusInfoService;
import com.melontech.landsys.service.criteria.NoticeStatusInfoCriteria;
import com.melontech.landsys.service.dto.NoticeStatusInfoDTO;
import com.melontech.landsys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.melontech.landsys.domain.NoticeStatusInfo}.
 */
@RestController
@RequestMapping("/api")
public class NoticeStatusInfoResource {

    private final Logger log = LoggerFactory.getLogger(NoticeStatusInfoResource.class);

    private static final String ENTITY_NAME = "noticeStatusInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoticeStatusInfoService noticeStatusInfoService;

    private final NoticeStatusInfoRepository noticeStatusInfoRepository;

    private final NoticeStatusInfoQueryService noticeStatusInfoQueryService;

    public NoticeStatusInfoResource(
        NoticeStatusInfoService noticeStatusInfoService,
        NoticeStatusInfoRepository noticeStatusInfoRepository,
        NoticeStatusInfoQueryService noticeStatusInfoQueryService
    ) {
        this.noticeStatusInfoService = noticeStatusInfoService;
        this.noticeStatusInfoRepository = noticeStatusInfoRepository;
        this.noticeStatusInfoQueryService = noticeStatusInfoQueryService;
    }

    /**
     * {@code POST  /notice-status-infos} : Create a new noticeStatusInfo.
     *
     * @param noticeStatusInfoDTO the noticeStatusInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noticeStatusInfoDTO, or with status {@code 400 (Bad Request)} if the noticeStatusInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notice-status-infos")
    public ResponseEntity<NoticeStatusInfoDTO> createNoticeStatusInfo(@RequestBody NoticeStatusInfoDTO noticeStatusInfoDTO)
        throws URISyntaxException {
        log.debug("REST request to save NoticeStatusInfo : {}", noticeStatusInfoDTO);
        if (noticeStatusInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new noticeStatusInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoticeStatusInfoDTO result = noticeStatusInfoService.save(noticeStatusInfoDTO);
        return ResponseEntity
            .created(new URI("/api/notice-status-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notice-status-infos/:id} : Updates an existing noticeStatusInfo.
     *
     * @param id the id of the noticeStatusInfoDTO to save.
     * @param noticeStatusInfoDTO the noticeStatusInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticeStatusInfoDTO,
     * or with status {@code 400 (Bad Request)} if the noticeStatusInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noticeStatusInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notice-status-infos/{id}")
    public ResponseEntity<NoticeStatusInfoDTO> updateNoticeStatusInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NoticeStatusInfoDTO noticeStatusInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NoticeStatusInfo : {}, {}", id, noticeStatusInfoDTO);
        if (noticeStatusInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticeStatusInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticeStatusInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NoticeStatusInfoDTO result = noticeStatusInfoService.update(noticeStatusInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticeStatusInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notice-status-infos/:id} : Partial updates given fields of an existing noticeStatusInfo, field will ignore if it is null
     *
     * @param id the id of the noticeStatusInfoDTO to save.
     * @param noticeStatusInfoDTO the noticeStatusInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticeStatusInfoDTO,
     * or with status {@code 400 (Bad Request)} if the noticeStatusInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the noticeStatusInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the noticeStatusInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notice-status-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NoticeStatusInfoDTO> partialUpdateNoticeStatusInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NoticeStatusInfoDTO noticeStatusInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NoticeStatusInfo partially : {}, {}", id, noticeStatusInfoDTO);
        if (noticeStatusInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticeStatusInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticeStatusInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NoticeStatusInfoDTO> result = noticeStatusInfoService.partialUpdate(noticeStatusInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticeStatusInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /notice-status-infos} : get all the noticeStatusInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noticeStatusInfos in body.
     */
    @GetMapping("/notice-status-infos")
    public ResponseEntity<List<NoticeStatusInfoDTO>> getAllNoticeStatusInfos(
        NoticeStatusInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NoticeStatusInfos by criteria: {}", criteria);
        Page<NoticeStatusInfoDTO> page = noticeStatusInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notice-status-infos/count} : count all the noticeStatusInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notice-status-infos/count")
    public ResponseEntity<Long> countNoticeStatusInfos(NoticeStatusInfoCriteria criteria) {
        log.debug("REST request to count NoticeStatusInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(noticeStatusInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notice-status-infos/:id} : get the "id" noticeStatusInfo.
     *
     * @param id the id of the noticeStatusInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticeStatusInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notice-status-infos/{id}")
    public ResponseEntity<NoticeStatusInfoDTO> getNoticeStatusInfo(@PathVariable Long id) {
        log.debug("REST request to get NoticeStatusInfo : {}", id);
        Optional<NoticeStatusInfoDTO> noticeStatusInfoDTO = noticeStatusInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noticeStatusInfoDTO);
    }

    /**
     * {@code DELETE  /notice-status-infos/:id} : delete the "id" noticeStatusInfo.
     *
     * @param id the id of the noticeStatusInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notice-status-infos/{id}")
    public ResponseEntity<Void> deleteNoticeStatusInfo(@PathVariable Long id) {
        log.debug("REST request to delete NoticeStatusInfo : {}", id);
        noticeStatusInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
