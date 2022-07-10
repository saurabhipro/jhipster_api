package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.PublicNotificationRepository;
import com.melontech.landsys.service.PublicNotificationService;
import com.melontech.landsys.service.dto.PublicNotificationDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.PublicNotification}.
 */
@RestController
@RequestMapping("/api")
public class PublicNotificationResource {

    private final Logger log = LoggerFactory.getLogger(PublicNotificationResource.class);

    private static final String ENTITY_NAME = "publicNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicNotificationService publicNotificationService;

    private final PublicNotificationRepository publicNotificationRepository;

    public PublicNotificationResource(
        PublicNotificationService publicNotificationService,
        PublicNotificationRepository publicNotificationRepository
    ) {
        this.publicNotificationService = publicNotificationService;
        this.publicNotificationRepository = publicNotificationRepository;
    }

    /**
     * {@code POST  /public-notifications} : Create a new publicNotification.
     *
     * @param publicNotificationDTO the publicNotificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicNotificationDTO, or with status {@code 400 (Bad Request)} if the publicNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-notifications")
    public ResponseEntity<PublicNotificationDTO> createPublicNotification(@RequestBody PublicNotificationDTO publicNotificationDTO)
        throws URISyntaxException {
        log.debug("REST request to save PublicNotification : {}", publicNotificationDTO);
        if (publicNotificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new publicNotification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicNotificationDTO result = publicNotificationService.save(publicNotificationDTO);
        return ResponseEntity
            .created(new URI("/api/public-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-notifications/:id} : Updates an existing publicNotification.
     *
     * @param id the id of the publicNotificationDTO to save.
     * @param publicNotificationDTO the publicNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the publicNotificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-notifications/{id}")
    public ResponseEntity<PublicNotificationDTO> updatePublicNotification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicNotificationDTO publicNotificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PublicNotification : {}, {}", id, publicNotificationDTO);
        if (publicNotificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicNotificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicNotificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PublicNotificationDTO result = publicNotificationService.update(publicNotificationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicNotificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /public-notifications/:id} : Partial updates given fields of an existing publicNotification, field will ignore if it is null
     *
     * @param id the id of the publicNotificationDTO to save.
     * @param publicNotificationDTO the publicNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the publicNotificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicNotificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/public-notifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PublicNotificationDTO> partialUpdatePublicNotification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicNotificationDTO publicNotificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PublicNotification partially : {}, {}", id, publicNotificationDTO);
        if (publicNotificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicNotificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicNotificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicNotificationDTO> result = publicNotificationService.partialUpdate(publicNotificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicNotificationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /public-notifications} : get all the publicNotifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicNotifications in body.
     */
    @GetMapping("/public-notifications")
    public ResponseEntity<List<PublicNotificationDTO>> getAllPublicNotifications(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PublicNotifications");
        Page<PublicNotificationDTO> page = publicNotificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /public-notifications/:id} : get the "id" publicNotification.
     *
     * @param id the id of the publicNotificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-notifications/{id}")
    public ResponseEntity<PublicNotificationDTO> getPublicNotification(@PathVariable Long id) {
        log.debug("REST request to get PublicNotification : {}", id);
        Optional<PublicNotificationDTO> publicNotificationDTO = publicNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicNotificationDTO);
    }

    /**
     * {@code DELETE  /public-notifications/:id} : delete the "id" publicNotification.
     *
     * @param id the id of the publicNotificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-notifications/{id}")
    public ResponseEntity<Void> deletePublicNotification(@PathVariable Long id) {
        log.debug("REST request to delete PublicNotification : {}", id);
        publicNotificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
