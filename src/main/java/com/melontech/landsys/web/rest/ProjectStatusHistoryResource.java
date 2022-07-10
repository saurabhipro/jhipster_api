package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.ProjectStatusHistoryRepository;
import com.melontech.landsys.service.ProjectStatusHistoryService;
import com.melontech.landsys.service.dto.ProjectStatusHistoryDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.ProjectStatusHistory}.
 */
@RestController
@RequestMapping("/api")
public class ProjectStatusHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusHistoryResource.class);

    private static final String ENTITY_NAME = "projectStatusHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectStatusHistoryService projectStatusHistoryService;

    private final ProjectStatusHistoryRepository projectStatusHistoryRepository;

    public ProjectStatusHistoryResource(
        ProjectStatusHistoryService projectStatusHistoryService,
        ProjectStatusHistoryRepository projectStatusHistoryRepository
    ) {
        this.projectStatusHistoryService = projectStatusHistoryService;
        this.projectStatusHistoryRepository = projectStatusHistoryRepository;
    }

    /**
     * {@code POST  /project-status-histories} : Create a new projectStatusHistory.
     *
     * @param projectStatusHistoryDTO the projectStatusHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectStatusHistoryDTO, or with status {@code 400 (Bad Request)} if the projectStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-status-histories")
    public ResponseEntity<ProjectStatusHistoryDTO> createProjectStatusHistory(@RequestBody ProjectStatusHistoryDTO projectStatusHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectStatusHistory : {}", projectStatusHistoryDTO);
        if (projectStatusHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectStatusHistoryDTO result = projectStatusHistoryService.save(projectStatusHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/project-status-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-status-histories/:id} : Updates an existing projectStatusHistory.
     *
     * @param id the id of the projectStatusHistoryDTO to save.
     * @param projectStatusHistoryDTO the projectStatusHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectStatusHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the projectStatusHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectStatusHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-status-histories/{id}")
    public ResponseEntity<ProjectStatusHistoryDTO> updateProjectStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectStatusHistoryDTO projectStatusHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectStatusHistory : {}, {}", id, projectStatusHistoryDTO);
        if (projectStatusHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectStatusHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectStatusHistoryDTO result = projectStatusHistoryService.update(projectStatusHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectStatusHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-status-histories/:id} : Partial updates given fields of an existing projectStatusHistory, field will ignore if it is null
     *
     * @param id the id of the projectStatusHistoryDTO to save.
     * @param projectStatusHistoryDTO the projectStatusHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectStatusHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the projectStatusHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectStatusHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectStatusHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-status-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectStatusHistoryDTO> partialUpdateProjectStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectStatusHistoryDTO projectStatusHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectStatusHistory partially : {}, {}", id, projectStatusHistoryDTO);
        if (projectStatusHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectStatusHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectStatusHistoryDTO> result = projectStatusHistoryService.partialUpdate(projectStatusHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectStatusHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-status-histories} : get all the projectStatusHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectStatusHistories in body.
     */
    @GetMapping("/project-status-histories")
    public ResponseEntity<List<ProjectStatusHistoryDTO>> getAllProjectStatusHistories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProjectStatusHistories");
        Page<ProjectStatusHistoryDTO> page = projectStatusHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-status-histories/:id} : get the "id" projectStatusHistory.
     *
     * @param id the id of the projectStatusHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectStatusHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-status-histories/{id}")
    public ResponseEntity<ProjectStatusHistoryDTO> getProjectStatusHistory(@PathVariable Long id) {
        log.debug("REST request to get ProjectStatusHistory : {}", id);
        Optional<ProjectStatusHistoryDTO> projectStatusHistoryDTO = projectStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectStatusHistoryDTO);
    }

    /**
     * {@code DELETE  /project-status-histories/:id} : delete the "id" projectStatusHistory.
     *
     * @param id the id of the projectStatusHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-status-histories/{id}")
    public ResponseEntity<Void> deleteProjectStatusHistory(@PathVariable Long id) {
        log.debug("REST request to delete ProjectStatusHistory : {}", id);
        projectStatusHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
