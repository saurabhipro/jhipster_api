package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.LandCompensationRepository;
import com.melontech.landsys.service.LandCompensationQueryService;
import com.melontech.landsys.service.LandCompensationService;
import com.melontech.landsys.service.criteria.LandCompensationCriteria;
import com.melontech.landsys.service.dto.LandCompensationDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.LandCompensation}.
 */
@RestController
@RequestMapping("/api")
public class LandCompensationResource {

    private final Logger log = LoggerFactory.getLogger(LandCompensationResource.class);

    private static final String ENTITY_NAME = "landCompensation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandCompensationService landCompensationService;

    private final LandCompensationRepository landCompensationRepository;

    private final LandCompensationQueryService landCompensationQueryService;

    public LandCompensationResource(
        LandCompensationService landCompensationService,
        LandCompensationRepository landCompensationRepository,
        LandCompensationQueryService landCompensationQueryService
    ) {
        this.landCompensationService = landCompensationService;
        this.landCompensationRepository = landCompensationRepository;
        this.landCompensationQueryService = landCompensationQueryService;
    }

    /**
     * {@code POST  /land-compensations} : Create a new landCompensation.
     *
     * @param landCompensationDTO the landCompensationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new landCompensationDTO, or with status {@code 400 (Bad Request)} if the landCompensation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/land-compensations")
    public ResponseEntity<LandCompensationDTO> createLandCompensation(@Valid @RequestBody LandCompensationDTO landCompensationDTO)
        throws URISyntaxException {
        log.debug("REST request to save LandCompensation : {}", landCompensationDTO);
        if (landCompensationDTO.getId() != null) {
            throw new BadRequestAlertException("A new landCompensation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LandCompensationDTO result = landCompensationService.save(landCompensationDTO);
        return ResponseEntity
            .created(new URI("/api/land-compensations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /land-compensations/:id} : Updates an existing landCompensation.
     *
     * @param id the id of the landCompensationDTO to save.
     * @param landCompensationDTO the landCompensationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landCompensationDTO,
     * or with status {@code 400 (Bad Request)} if the landCompensationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the landCompensationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/land-compensations/{id}")
    public ResponseEntity<LandCompensationDTO> updateLandCompensation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LandCompensationDTO landCompensationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LandCompensation : {}, {}", id, landCompensationDTO);
        if (landCompensationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landCompensationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landCompensationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LandCompensationDTO result = landCompensationService.update(landCompensationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landCompensationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /land-compensations/:id} : Partial updates given fields of an existing landCompensation, field will ignore if it is null
     *
     * @param id the id of the landCompensationDTO to save.
     * @param landCompensationDTO the landCompensationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landCompensationDTO,
     * or with status {@code 400 (Bad Request)} if the landCompensationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the landCompensationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the landCompensationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/land-compensations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LandCompensationDTO> partialUpdateLandCompensation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LandCompensationDTO landCompensationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LandCompensation partially : {}, {}", id, landCompensationDTO);
        if (landCompensationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landCompensationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landCompensationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LandCompensationDTO> result = landCompensationService.partialUpdate(landCompensationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landCompensationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /land-compensations} : get all the landCompensations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of landCompensations in body.
     */
    @GetMapping("/land-compensations")
    public ResponseEntity<List<LandCompensationDTO>> getAllLandCompensations(
        LandCompensationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LandCompensations by criteria: {}", criteria);
        Page<LandCompensationDTO> page = landCompensationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /land-compensations/count} : count all the landCompensations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/land-compensations/count")
    public ResponseEntity<Long> countLandCompensations(LandCompensationCriteria criteria) {
        log.debug("REST request to count LandCompensations by criteria: {}", criteria);
        return ResponseEntity.ok().body(landCompensationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /land-compensations/:id} : get the "id" landCompensation.
     *
     * @param id the id of the landCompensationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the landCompensationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/land-compensations/{id}")
    public ResponseEntity<LandCompensationDTO> getLandCompensation(@PathVariable Long id) {
        log.debug("REST request to get LandCompensation : {}", id);
        Optional<LandCompensationDTO> landCompensationDTO = landCompensationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(landCompensationDTO);
    }

    /**
     * {@code DELETE  /land-compensations/:id} : delete the "id" landCompensation.
     *
     * @param id the id of the landCompensationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/land-compensations/{id}")
    public ResponseEntity<Void> deleteLandCompensation(@PathVariable Long id) {
        log.debug("REST request to delete LandCompensation : {}", id);
        landCompensationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
