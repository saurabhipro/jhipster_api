package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.SequenceGenRepository;
import com.melontech.landsys.service.SequenceGenQueryService;
import com.melontech.landsys.service.SequenceGenService;
import com.melontech.landsys.service.criteria.SequenceGenCriteria;
import com.melontech.landsys.service.dto.SequenceGenDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.SequenceGen}.
 */
@RestController
@RequestMapping("/api")
public class SequenceGenResource {

    private final Logger log = LoggerFactory.getLogger(SequenceGenResource.class);

    private static final String ENTITY_NAME = "sequenceGen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceGenService sequenceGenService;

    private final SequenceGenRepository sequenceGenRepository;

    private final SequenceGenQueryService sequenceGenQueryService;

    public SequenceGenResource(
        SequenceGenService sequenceGenService,
        SequenceGenRepository sequenceGenRepository,
        SequenceGenQueryService sequenceGenQueryService
    ) {
        this.sequenceGenService = sequenceGenService;
        this.sequenceGenRepository = sequenceGenRepository;
        this.sequenceGenQueryService = sequenceGenQueryService;
    }

    /**
     * {@code POST  /sequence-gens} : Create a new sequenceGen.
     *
     * @param sequenceGenDTO the sequenceGenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceGenDTO, or with status {@code 400 (Bad Request)} if the sequenceGen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sequence-gens")
    public ResponseEntity<SequenceGenDTO> createSequenceGen(@Valid @RequestBody SequenceGenDTO sequenceGenDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceGen : {}", sequenceGenDTO);
        if (sequenceGenDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceGen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceGenDTO result = sequenceGenService.save(sequenceGenDTO);
        return ResponseEntity
            .created(new URI("/api/sequence-gens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-gens/:id} : Updates an existing sequenceGen.
     *
     * @param id the id of the sequenceGenDTO to save.
     * @param sequenceGenDTO the sequenceGenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceGenDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceGenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceGenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sequence-gens/{id}")
    public ResponseEntity<SequenceGenDTO> updateSequenceGen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SequenceGenDTO sequenceGenDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SequenceGen : {}, {}", id, sequenceGenDTO);
        if (sequenceGenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequenceGenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequenceGenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SequenceGenDTO result = sequenceGenService.update(sequenceGenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceGenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sequence-gens/:id} : Partial updates given fields of an existing sequenceGen, field will ignore if it is null
     *
     * @param id the id of the sequenceGenDTO to save.
     * @param sequenceGenDTO the sequenceGenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceGenDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceGenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sequenceGenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sequenceGenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sequence-gens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SequenceGenDTO> partialUpdateSequenceGen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SequenceGenDTO sequenceGenDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SequenceGen partially : {}, {}", id, sequenceGenDTO);
        if (sequenceGenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequenceGenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequenceGenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SequenceGenDTO> result = sequenceGenService.partialUpdate(sequenceGenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceGenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sequence-gens} : get all the sequenceGens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceGens in body.
     */
    @GetMapping("/sequence-gens")
    public ResponseEntity<List<SequenceGenDTO>> getAllSequenceGens(
        SequenceGenCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SequenceGens by criteria: {}", criteria);
        Page<SequenceGenDTO> page = sequenceGenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-gens/count} : count all the sequenceGens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sequence-gens/count")
    public ResponseEntity<Long> countSequenceGens(SequenceGenCriteria criteria) {
        log.debug("REST request to count SequenceGens by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceGenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-gens/:id} : get the "id" sequenceGen.
     *
     * @param id the id of the sequenceGenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceGenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sequence-gens/{id}")
    public ResponseEntity<SequenceGenDTO> getSequenceGen(@PathVariable Long id) {
        log.debug("REST request to get SequenceGen : {}", id);
        Optional<SequenceGenDTO> sequenceGenDTO = sequenceGenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceGenDTO);
    }

    /**
     * {@code DELETE  /sequence-gens/:id} : delete the "id" sequenceGen.
     *
     * @param id the id of the sequenceGenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sequence-gens/{id}")
    public ResponseEntity<Void> deleteSequenceGen(@PathVariable Long id) {
        log.debug("REST request to delete SequenceGen : {}", id);
        sequenceGenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
