package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.NoticeStatusRepository;
import com.melontech.landsys.service.NoticeStatusService;
import com.melontech.landsys.service.dto.NoticeStatusDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.melontech.landsys.domain.NoticeStatus}.
 */
@RestController
@RequestMapping("/api")
public class NoticeStatusResource {

    private final Logger log = LoggerFactory.getLogger(NoticeStatusResource.class);

    private static final String ENTITY_NAME = "noticeStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoticeStatusService noticeStatusService;

    private final NoticeStatusRepository noticeStatusRepository;

    public NoticeStatusResource(NoticeStatusService noticeStatusService, NoticeStatusRepository noticeStatusRepository) {
        this.noticeStatusService = noticeStatusService;
        this.noticeStatusRepository = noticeStatusRepository;
    }

    /**
     * {@code POST  /notice-statuses} : Create a new noticeStatus.
     *
     * @param noticeStatusDTO the noticeStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noticeStatusDTO, or with status {@code 400 (Bad Request)} if the noticeStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notice-statuses")
    public ResponseEntity<NoticeStatusDTO> createNoticeStatus(@RequestBody NoticeStatusDTO noticeStatusDTO) throws URISyntaxException {
        log.debug("REST request to save NoticeStatus : {}", noticeStatusDTO);
        if (noticeStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new noticeStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoticeStatusDTO result = noticeStatusService.save(noticeStatusDTO);
        return ResponseEntity
            .created(new URI("/api/notice-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notice-statuses/:id} : Updates an existing noticeStatus.
     *
     * @param id the id of the noticeStatusDTO to save.
     * @param noticeStatusDTO the noticeStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticeStatusDTO,
     * or with status {@code 400 (Bad Request)} if the noticeStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noticeStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notice-statuses/{id}")
    public ResponseEntity<NoticeStatusDTO> updateNoticeStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NoticeStatusDTO noticeStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NoticeStatus : {}, {}", id, noticeStatusDTO);
        if (noticeStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticeStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticeStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NoticeStatusDTO result = noticeStatusService.update(noticeStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticeStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notice-statuses/:id} : Partial updates given fields of an existing noticeStatus, field will ignore if it is null
     *
     * @param id the id of the noticeStatusDTO to save.
     * @param noticeStatusDTO the noticeStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticeStatusDTO,
     * or with status {@code 400 (Bad Request)} if the noticeStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the noticeStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the noticeStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notice-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NoticeStatusDTO> partialUpdateNoticeStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NoticeStatusDTO noticeStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NoticeStatus partially : {}, {}", id, noticeStatusDTO);
        if (noticeStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticeStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticeStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NoticeStatusDTO> result = noticeStatusService.partialUpdate(noticeStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticeStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /notice-statuses} : get all the noticeStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noticeStatuses in body.
     */
    @GetMapping("/notice-statuses")
    public ResponseEntity<List<NoticeStatusDTO>> getAllNoticeStatuses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NoticeStatuses");
        Page<NoticeStatusDTO> page = noticeStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notice-statuses/:id} : get the "id" noticeStatus.
     *
     * @param id the id of the noticeStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticeStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notice-statuses/{id}")
    public ResponseEntity<NoticeStatusDTO> getNoticeStatus(@PathVariable Long id) {
        log.debug("REST request to get NoticeStatus : {}", id);
        Optional<NoticeStatusDTO> noticeStatusDTO = noticeStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noticeStatusDTO);
    }

    /**
     * {@code DELETE  /notice-statuses/:id} : delete the "id" noticeStatus.
     *
     * @param id the id of the noticeStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notice-statuses/{id}")
    public ResponseEntity<Void> deleteNoticeStatus(@PathVariable Long id) {
        log.debug("REST request to delete NoticeStatus : {}", id);
        noticeStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
