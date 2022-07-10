package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.KhatedarRepository;
import com.melontech.landsys.service.KhatedarQueryService;
import com.melontech.landsys.service.KhatedarService;
import com.melontech.landsys.service.criteria.KhatedarCriteria;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.melontech.landsys.domain.Khatedar}.
 */
@RestController
@RequestMapping("/api")
public class KhatedarResource {

    private final Logger log = LoggerFactory.getLogger(KhatedarResource.class);

    private static final String ENTITY_NAME = "khatedar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KhatedarService khatedarService;

    private final KhatedarRepository khatedarRepository;

    private final KhatedarQueryService khatedarQueryService;

    public KhatedarResource(
        KhatedarService khatedarService,
        KhatedarRepository khatedarRepository,
        KhatedarQueryService khatedarQueryService
    ) {
        this.khatedarService = khatedarService;
        this.khatedarRepository = khatedarRepository;
        this.khatedarQueryService = khatedarQueryService;
    }

    /**
     * {@code POST  /khatedars} : Create a new khatedar.
     *
     * @param khatedarDTO the khatedarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new khatedarDTO, or with status {@code 400 (Bad Request)} if the khatedar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/khatedars")
    public ResponseEntity<KhatedarDTO> createKhatedar(@Valid @RequestBody KhatedarDTO khatedarDTO) throws URISyntaxException {
        log.debug("REST request to save Khatedar : {}", khatedarDTO);
        if (khatedarDTO.getId() != null) {
            throw new BadRequestAlertException("A new khatedar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KhatedarDTO result = khatedarService.save(khatedarDTO);
        return ResponseEntity
            .created(new URI("/api/khatedars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /khatedars/:id} : Updates an existing khatedar.
     *
     * @param id the id of the khatedarDTO to save.
     * @param khatedarDTO the khatedarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated khatedarDTO,
     * or with status {@code 400 (Bad Request)} if the khatedarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the khatedarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/khatedars/{id}")
    public ResponseEntity<KhatedarDTO> updateKhatedar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KhatedarDTO khatedarDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Khatedar : {}, {}", id, khatedarDTO);
        if (khatedarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, khatedarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!khatedarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KhatedarDTO result = khatedarService.update(khatedarDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, khatedarDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /khatedars/:id} : Partial updates given fields of an existing khatedar, field will ignore if it is null
     *
     * @param id the id of the khatedarDTO to save.
     * @param khatedarDTO the khatedarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated khatedarDTO,
     * or with status {@code 400 (Bad Request)} if the khatedarDTO is not valid,
     * or with status {@code 404 (Not Found)} if the khatedarDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the khatedarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/khatedars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KhatedarDTO> partialUpdateKhatedar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KhatedarDTO khatedarDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Khatedar partially : {}, {}", id, khatedarDTO);
        if (khatedarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, khatedarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!khatedarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KhatedarDTO> result = khatedarService.partialUpdate(khatedarDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, khatedarDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /khatedars} : get all the khatedars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of khatedars in body.
     */
    @GetMapping("/khatedars")
    public ResponseEntity<List<KhatedarDTO>> getAllKhatedars(
        KhatedarCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Khatedars by criteria: {}", criteria);
        Page<KhatedarDTO> page = khatedarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /khatedars/count} : count all the khatedars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/khatedars/count")
    public ResponseEntity<Long> countKhatedars(KhatedarCriteria criteria) {
        log.debug("REST request to count Khatedars by criteria: {}", criteria);
        return ResponseEntity.ok().body(khatedarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /khatedars/:id} : get the "id" khatedar.
     *
     * @param id the id of the khatedarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the khatedarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/khatedars/{id}")
    public ResponseEntity<KhatedarDTO> getKhatedar(@PathVariable Long id) {
        log.debug("REST request to get Khatedar : {}", id);
        Optional<KhatedarDTO> khatedarDTO = khatedarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(khatedarDTO);
    }

    /**
     * {@code DELETE  /khatedars/:id} : delete the "id" khatedar.
     *
     * @param id the id of the khatedarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/khatedars/{id}")
    public ResponseEntity<Void> deleteKhatedar(@PathVariable Long id) {
        log.debug("REST request to delete Khatedar : {}", id);
        khatedarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
