package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.CreatePaymentFileRepository;
import com.melontech.landsys.service.CreatePaymentFileService;
import com.melontech.landsys.service.dto.CreatePaymentFileDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.CreatePaymentFile}.
 */
@RestController
@RequestMapping("/api")
public class CreatePaymentFileResource {

    private final Logger log = LoggerFactory.getLogger(CreatePaymentFileResource.class);

    private static final String ENTITY_NAME = "createPaymentFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreatePaymentFileService createPaymentFileService;

    private final CreatePaymentFileRepository createPaymentFileRepository;

    public CreatePaymentFileResource(
        CreatePaymentFileService createPaymentFileService,
        CreatePaymentFileRepository createPaymentFileRepository
    ) {
        this.createPaymentFileService = createPaymentFileService;
        this.createPaymentFileRepository = createPaymentFileRepository;
    }

    /**
     * {@code POST  /create-payment-files} : Create a new createPaymentFile.
     *
     * @param createPaymentFileDTO the createPaymentFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new createPaymentFileDTO, or with status {@code 400 (Bad Request)} if the createPaymentFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-payment-files")
    public ResponseEntity<CreatePaymentFileDTO> createCreatePaymentFile(@Valid @RequestBody CreatePaymentFileDTO createPaymentFileDTO)
        throws URISyntaxException {
        log.debug("REST request to save CreatePaymentFile : {}", createPaymentFileDTO);
        if (createPaymentFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new createPaymentFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreatePaymentFileDTO result = createPaymentFileService.save(createPaymentFileDTO);
        return ResponseEntity
            .created(new URI("/api/create-payment-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /create-payment-files/:id} : Updates an existing createPaymentFile.
     *
     * @param id the id of the createPaymentFileDTO to save.
     * @param createPaymentFileDTO the createPaymentFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated createPaymentFileDTO,
     * or with status {@code 400 (Bad Request)} if the createPaymentFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the createPaymentFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/create-payment-files/{id}")
    public ResponseEntity<CreatePaymentFileDTO> updateCreatePaymentFile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CreatePaymentFileDTO createPaymentFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreatePaymentFile : {}, {}", id, createPaymentFileDTO);
        if (createPaymentFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, createPaymentFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!createPaymentFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreatePaymentFileDTO result = createPaymentFileService.update(createPaymentFileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, createPaymentFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /create-payment-files/:id} : Partial updates given fields of an existing createPaymentFile, field will ignore if it is null
     *
     * @param id the id of the createPaymentFileDTO to save.
     * @param createPaymentFileDTO the createPaymentFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated createPaymentFileDTO,
     * or with status {@code 400 (Bad Request)} if the createPaymentFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the createPaymentFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the createPaymentFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/create-payment-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreatePaymentFileDTO> partialUpdateCreatePaymentFile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CreatePaymentFileDTO createPaymentFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreatePaymentFile partially : {}, {}", id, createPaymentFileDTO);
        if (createPaymentFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, createPaymentFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!createPaymentFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreatePaymentFileDTO> result = createPaymentFileService.partialUpdate(createPaymentFileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, createPaymentFileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /create-payment-files} : get all the createPaymentFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of createPaymentFiles in body.
     */
    @GetMapping("/create-payment-files")
    public ResponseEntity<List<CreatePaymentFileDTO>> getAllCreatePaymentFiles(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CreatePaymentFiles");
        Page<CreatePaymentFileDTO> page = createPaymentFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /create-payment-files/:id} : get the "id" createPaymentFile.
     *
     * @param id the id of the createPaymentFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the createPaymentFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/create-payment-files/{id}")
    public ResponseEntity<CreatePaymentFileDTO> getCreatePaymentFile(@PathVariable Long id) {
        log.debug("REST request to get CreatePaymentFile : {}", id);
        Optional<CreatePaymentFileDTO> createPaymentFileDTO = createPaymentFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(createPaymentFileDTO);
    }

    /**
     * {@code DELETE  /create-payment-files/:id} : delete the "id" createPaymentFile.
     *
     * @param id the id of the createPaymentFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/create-payment-files/{id}")
    public ResponseEntity<Void> deleteCreatePaymentFile(@PathVariable Long id) {
        log.debug("REST request to delete CreatePaymentFile : {}", id);
        createPaymentFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
