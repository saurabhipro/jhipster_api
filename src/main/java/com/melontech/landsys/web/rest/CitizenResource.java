package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.CitizenRepository;
import com.melontech.landsys.service.CitizenQueryService;
import com.melontech.landsys.service.CitizenService;
import com.melontech.landsys.service.criteria.CitizenCriteria;
import com.melontech.landsys.service.dto.CitizenDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.Citizen}.
 */
@RestController
@RequestMapping("/api")
public class CitizenResource {

    private final Logger log = LoggerFactory.getLogger(CitizenResource.class);

    private static final String ENTITY_NAME = "citizen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitizenService citizenService;

    private final CitizenRepository citizenRepository;

    private final CitizenQueryService citizenQueryService;

    public CitizenResource(CitizenService citizenService, CitizenRepository citizenRepository, CitizenQueryService citizenQueryService) {
        this.citizenService = citizenService;
        this.citizenRepository = citizenRepository;
        this.citizenQueryService = citizenQueryService;
    }

    /**
     * {@code POST  /citizens} : Create a new citizen.
     *
     * @param citizenDTO the citizenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citizenDTO, or with status {@code 400 (Bad Request)} if the citizen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citizens")
    public ResponseEntity<CitizenDTO> createCitizen(@Valid @RequestBody CitizenDTO citizenDTO) throws URISyntaxException {
        log.debug("REST request to save Citizen : {}", citizenDTO);
        if (citizenDTO.getId() != null) {
            throw new BadRequestAlertException("A new citizen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitizenDTO result = citizenService.save(citizenDTO);
        return ResponseEntity
            .created(new URI("/api/citizens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citizens/:id} : Updates an existing citizen.
     *
     * @param id the id of the citizenDTO to save.
     * @param citizenDTO the citizenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizenDTO,
     * or with status {@code 400 (Bad Request)} if the citizenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citizenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citizens/{id}")
    public ResponseEntity<CitizenDTO> updateCitizen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CitizenDTO citizenDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Citizen : {}, {}", id, citizenDTO);
        if (citizenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CitizenDTO result = citizenService.update(citizenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /citizens/:id} : Partial updates given fields of an existing citizen, field will ignore if it is null
     *
     * @param id the id of the citizenDTO to save.
     * @param citizenDTO the citizenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizenDTO,
     * or with status {@code 400 (Bad Request)} if the citizenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the citizenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the citizenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/citizens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CitizenDTO> partialUpdateCitizen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CitizenDTO citizenDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Citizen partially : {}, {}", id, citizenDTO);
        if (citizenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CitizenDTO> result = citizenService.partialUpdate(citizenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /citizens} : get all the citizens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citizens in body.
     */
    @GetMapping("/citizens")
    public ResponseEntity<List<CitizenDTO>> getAllCitizens(
        CitizenCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Citizens by criteria: {}", criteria);
        Page<CitizenDTO> page = citizenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /citizens/count} : count all the citizens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/citizens/count")
    public ResponseEntity<Long> countCitizens(CitizenCriteria criteria) {
        log.debug("REST request to count Citizens by criteria: {}", criteria);
        return ResponseEntity.ok().body(citizenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /citizens/:id} : get the "id" citizen.
     *
     * @param id the id of the citizenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citizenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citizens/{id}")
    public ResponseEntity<CitizenDTO> getCitizen(@PathVariable Long id) {
        log.debug("REST request to get Citizen : {}", id);
        Optional<CitizenDTO> citizenDTO = citizenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citizenDTO);
    }

    /**
     * {@code DELETE  /citizens/:id} : delete the "id" citizen.
     *
     * @param id the id of the citizenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citizens/{id}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable Long id) {
        log.debug("REST request to delete Citizen : {}", id);
        citizenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
