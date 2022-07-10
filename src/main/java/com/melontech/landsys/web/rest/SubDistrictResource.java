package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.SubDistrictRepository;
import com.melontech.landsys.service.SubDistrictService;
import com.melontech.landsys.service.dto.SubDistrictDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.melontech.landsys.domain.SubDistrict}.
 */
@RestController
@RequestMapping("/api")
public class SubDistrictResource {

    private final Logger log = LoggerFactory.getLogger(SubDistrictResource.class);

    private static final String ENTITY_NAME = "subDistrict";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubDistrictService subDistrictService;

    private final SubDistrictRepository subDistrictRepository;

    public SubDistrictResource(SubDistrictService subDistrictService, SubDistrictRepository subDistrictRepository) {
        this.subDistrictService = subDistrictService;
        this.subDistrictRepository = subDistrictRepository;
    }

    /**
     * {@code POST  /sub-districts} : Create a new subDistrict.
     *
     * @param subDistrictDTO the subDistrictDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subDistrictDTO, or with status {@code 400 (Bad Request)} if the subDistrict has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-districts")
    public ResponseEntity<SubDistrictDTO> createSubDistrict(@Valid @RequestBody SubDistrictDTO subDistrictDTO) throws URISyntaxException {
        log.debug("REST request to save SubDistrict : {}", subDistrictDTO);
        if (subDistrictDTO.getId() != null) {
            throw new BadRequestAlertException("A new subDistrict cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubDistrictDTO result = subDistrictService.save(subDistrictDTO);
        return ResponseEntity
            .created(new URI("/api/sub-districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-districts/:id} : Updates an existing subDistrict.
     *
     * @param id the id of the subDistrictDTO to save.
     * @param subDistrictDTO the subDistrictDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subDistrictDTO,
     * or with status {@code 400 (Bad Request)} if the subDistrictDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subDistrictDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-districts/{id}")
    public ResponseEntity<SubDistrictDTO> updateSubDistrict(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubDistrictDTO subDistrictDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubDistrict : {}, {}", id, subDistrictDTO);
        if (subDistrictDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subDistrictDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subDistrictRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubDistrictDTO result = subDistrictService.update(subDistrictDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subDistrictDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-districts/:id} : Partial updates given fields of an existing subDistrict, field will ignore if it is null
     *
     * @param id the id of the subDistrictDTO to save.
     * @param subDistrictDTO the subDistrictDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subDistrictDTO,
     * or with status {@code 400 (Bad Request)} if the subDistrictDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subDistrictDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subDistrictDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-districts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubDistrictDTO> partialUpdateSubDistrict(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubDistrictDTO subDistrictDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubDistrict partially : {}, {}", id, subDistrictDTO);
        if (subDistrictDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subDistrictDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subDistrictRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubDistrictDTO> result = subDistrictService.partialUpdate(subDistrictDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subDistrictDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-districts} : get all the subDistricts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subDistricts in body.
     */
    @GetMapping("/sub-districts")
    public ResponseEntity<List<SubDistrictDTO>> getAllSubDistricts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of SubDistricts");
        Page<SubDistrictDTO> page;
        if (eagerload) {
            page = subDistrictService.findAllWithEagerRelationships(pageable);
        } else {
            page = subDistrictService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-districts/:id} : get the "id" subDistrict.
     *
     * @param id the id of the subDistrictDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subDistrictDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-districts/{id}")
    public ResponseEntity<SubDistrictDTO> getSubDistrict(@PathVariable Long id) {
        log.debug("REST request to get SubDistrict : {}", id);
        Optional<SubDistrictDTO> subDistrictDTO = subDistrictService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subDistrictDTO);
    }

    /**
     * {@code DELETE  /sub-districts/:id} : delete the "id" subDistrict.
     *
     * @param id the id of the subDistrictDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-districts/{id}")
    public ResponseEntity<Void> deleteSubDistrict(@PathVariable Long id) {
        log.debug("REST request to delete SubDistrict : {}", id);
        subDistrictService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
