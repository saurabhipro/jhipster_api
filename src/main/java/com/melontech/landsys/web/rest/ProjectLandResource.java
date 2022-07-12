package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.ProjectLandRepository;
import com.melontech.landsys.service.ProjectLandService;
import com.melontech.landsys.service.dto.ProjectLandDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.ProjectLand}.
 */
@RestController
@RequestMapping("/api")
public class ProjectLandResource {

    private final Logger log = LoggerFactory.getLogger(ProjectLandResource.class);

    private static final String ENTITY_NAME = "projectLand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectLandService projectLandService;

    private final ProjectLandRepository projectLandRepository;

    public ProjectLandResource(ProjectLandService projectLandService, ProjectLandRepository projectLandRepository) {
        this.projectLandService = projectLandService;
        this.projectLandRepository = projectLandRepository;
    }

    /**
     * {@code POST  /project-lands} : Create a new projectLand.
     *
     * @param projectLandDTO the projectLandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectLandDTO, or with status {@code 400 (Bad Request)} if the projectLand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-lands")
    public ResponseEntity<ProjectLandDTO> createProjectLand(@Valid @RequestBody ProjectLandDTO projectLandDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectLand : {}", projectLandDTO);
        if (projectLandDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectLand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectLandDTO result = projectLandService.save(projectLandDTO);
        return ResponseEntity
            .created(new URI("/api/project-lands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-lands/:id} : Updates an existing projectLand.
     *
     * @param id the id of the projectLandDTO to save.
     * @param projectLandDTO the projectLandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectLandDTO,
     * or with status {@code 400 (Bad Request)} if the projectLandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectLandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-lands/{id}")
    public ResponseEntity<ProjectLandDTO> updateProjectLand(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectLandDTO projectLandDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectLand : {}, {}", id, projectLandDTO);
        if (projectLandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectLandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectLandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectLandDTO result = projectLandService.update(projectLandDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectLandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-lands/:id} : Partial updates given fields of an existing projectLand, field will ignore if it is null
     *
     * @param id the id of the projectLandDTO to save.
     * @param projectLandDTO the projectLandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectLandDTO,
     * or with status {@code 400 (Bad Request)} if the projectLandDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectLandDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectLandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-lands/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectLandDTO> partialUpdateProjectLand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectLandDTO projectLandDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectLand partially : {}, {}", id, projectLandDTO);
        if (projectLandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectLandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectLandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectLandDTO> result = projectLandService.partialUpdate(projectLandDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectLandDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-lands} : get all the projectLands.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectLands in body.
     */
    @GetMapping("/project-lands")
    public ResponseEntity<List<ProjectLandDTO>> getAllProjectLands(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("survey-is-null".equals(filter)) {
            log.debug("REST request to get all ProjectLands where survey is null");
            return new ResponseEntity<>(projectLandService.findAllWhereSurveyIsNull(), HttpStatus.OK);
        }

        if ("landcompensation-is-null".equals(filter)) {
            log.debug("REST request to get all ProjectLands where landCompensation is null");
            return new ResponseEntity<>(projectLandService.findAllWhereLandCompensationIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of ProjectLands");
        Page<ProjectLandDTO> page;
        if (eagerload) {
            page = projectLandService.findAllWithEagerRelationships(pageable);
        } else {
            page = projectLandService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-lands/:id} : get the "id" projectLand.
     *
     * @param id the id of the projectLandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectLandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-lands/{id}")
    public ResponseEntity<ProjectLandDTO> getProjectLand(@PathVariable Long id) {
        log.debug("REST request to get ProjectLand : {}", id);
        Optional<ProjectLandDTO> projectLandDTO = projectLandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectLandDTO);
    }

    /**
     * {@code DELETE  /project-lands/:id} : delete the "id" projectLand.
     *
     * @param id the id of the projectLandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-lands/{id}")
    public ResponseEntity<Void> deleteProjectLand(@PathVariable Long id) {
        log.debug("REST request to delete ProjectLand : {}", id);
        projectLandService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
