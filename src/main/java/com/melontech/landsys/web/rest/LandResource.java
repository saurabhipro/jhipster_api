package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.LandRepository;
import com.melontech.landsys.service.LandService;
import com.melontech.landsys.service.dto.LandDTO;
import com.melontech.landsys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.melontech.landsys.domain.Land}.
 */
@RestController
@RequestMapping("/api")
public class LandResource {

    private final Logger log = LoggerFactory.getLogger(LandResource.class);

    private static final String ENTITY_NAME = "land";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandService landService;

    private final LandRepository landRepository;

    public LandResource(LandService landService, LandRepository landRepository) {
        this.landService = landService;
        this.landRepository = landRepository;
    }

    /**
     * {@code POST  /lands} : Create a new land.
     *
     * @param landDTO the landDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new landDTO, or with status {@code 400 (Bad Request)} if the land has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lands")
    public ResponseEntity<LandDTO> createLand(@Valid @RequestBody LandDTO landDTO) throws URISyntaxException {
        log.debug("REST request to save Land : {}", landDTO);
        if (landDTO.getId() != null) {
            throw new BadRequestAlertException("A new land cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LandDTO result = landService.save(landDTO);
        return ResponseEntity
            .created(new URI("/api/lands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lands/:id} : Updates an existing land.
     *
     * @param id the id of the landDTO to save.
     * @param landDTO the landDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landDTO,
     * or with status {@code 400 (Bad Request)} if the landDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the landDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lands/{id}")
    public ResponseEntity<LandDTO> updateLand(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LandDTO landDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Land : {}, {}", id, landDTO);
        if (landDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LandDTO result = landService.update(landDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lands/:id} : Partial updates given fields of an existing land, field will ignore if it is null
     *
     * @param id the id of the landDTO to save.
     * @param landDTO the landDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landDTO,
     * or with status {@code 400 (Bad Request)} if the landDTO is not valid,
     * or with status {@code 404 (Not Found)} if the landDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the landDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lands/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LandDTO> partialUpdateLand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LandDTO landDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Land partially : {}, {}", id, landDTO);
        if (landDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LandDTO> result = landService.partialUpdate(landDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lands} : get all the lands.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lands in body.
     */
    @GetMapping("/lands")
    public ResponseEntity<List<LandDTO>> getAllLands(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("projectland-is-null".equals(filter)) {
            log.debug("REST request to get all Lands where projectLand is null");
            return new ResponseEntity<>(landService.findAllWhereProjectLandIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Lands");
        Page<LandDTO> page;
        if (eagerload) {
            page = landService.findAllWithEagerRelationships(pageable);
        } else {
            page = landService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lands/:id} : get the "id" land.
     *
     * @param id the id of the landDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the landDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lands/{id}")
    public ResponseEntity<LandDTO> getLand(@PathVariable Long id) {
        log.debug("REST request to get Land : {}", id);
        Optional<LandDTO> landDTO = landService.findOne(id);
        return ResponseUtil.wrapOrNotFound(landDTO);
    }

    /**
     * {@code DELETE  /lands/:id} : delete the "id" land.
     *
     * @param id the id of the landDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lands/{id}")
    public ResponseEntity<Void> deleteLand(@PathVariable Long id) {
        log.debug("REST request to delete Land : {}", id);
        landService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
