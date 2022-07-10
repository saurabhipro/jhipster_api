package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.LandTypeRepository;
import com.melontech.landsys.service.LandTypeService;
import com.melontech.landsys.service.dto.LandTypeDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.LandType}.
 */
@RestController
@RequestMapping("/api")
public class LandTypeResource {

    private final Logger log = LoggerFactory.getLogger(LandTypeResource.class);

    private static final String ENTITY_NAME = "landType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandTypeService landTypeService;

    private final LandTypeRepository landTypeRepository;

    public LandTypeResource(LandTypeService landTypeService, LandTypeRepository landTypeRepository) {
        this.landTypeService = landTypeService;
        this.landTypeRepository = landTypeRepository;
    }

    /**
     * {@code POST  /land-types} : Create a new landType.
     *
     * @param landTypeDTO the landTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new landTypeDTO, or with status {@code 400 (Bad Request)} if the landType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/land-types")
    public ResponseEntity<LandTypeDTO> createLandType(@Valid @RequestBody LandTypeDTO landTypeDTO) throws URISyntaxException {
        log.debug("REST request to save LandType : {}", landTypeDTO);
        if (landTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new landType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LandTypeDTO result = landTypeService.save(landTypeDTO);
        return ResponseEntity
            .created(new URI("/api/land-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /land-types/:id} : Updates an existing landType.
     *
     * @param id the id of the landTypeDTO to save.
     * @param landTypeDTO the landTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landTypeDTO,
     * or with status {@code 400 (Bad Request)} if the landTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the landTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/land-types/{id}")
    public ResponseEntity<LandTypeDTO> updateLandType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LandTypeDTO landTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LandType : {}, {}", id, landTypeDTO);
        if (landTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LandTypeDTO result = landTypeService.update(landTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /land-types/:id} : Partial updates given fields of an existing landType, field will ignore if it is null
     *
     * @param id the id of the landTypeDTO to save.
     * @param landTypeDTO the landTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landTypeDTO,
     * or with status {@code 400 (Bad Request)} if the landTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the landTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the landTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/land-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LandTypeDTO> partialUpdateLandType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LandTypeDTO landTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LandType partially : {}, {}", id, landTypeDTO);
        if (landTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LandTypeDTO> result = landTypeService.partialUpdate(landTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /land-types} : get all the landTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of landTypes in body.
     */
    @GetMapping("/land-types")
    public ResponseEntity<List<LandTypeDTO>> getAllLandTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LandTypes");
        Page<LandTypeDTO> page = landTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /land-types/:id} : get the "id" landType.
     *
     * @param id the id of the landTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the landTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/land-types/{id}")
    public ResponseEntity<LandTypeDTO> getLandType(@PathVariable Long id) {
        log.debug("REST request to get LandType : {}", id);
        Optional<LandTypeDTO> landTypeDTO = landTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(landTypeDTO);
    }

    /**
     * {@code DELETE  /land-types/:id} : delete the "id" landType.
     *
     * @param id the id of the landTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/land-types/{id}")
    public ResponseEntity<Void> deleteLandType(@PathVariable Long id) {
        log.debug("REST request to delete LandType : {}", id);
        landTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
