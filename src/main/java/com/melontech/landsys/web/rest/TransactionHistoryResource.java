package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.TransactionHistoryRepository;
import com.melontech.landsys.service.TransactionHistoryQueryService;
import com.melontech.landsys.service.TransactionHistoryService;
import com.melontech.landsys.service.criteria.TransactionHistoryCriteria;
import com.melontech.landsys.service.dto.TransactionHistoryDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.TransactionHistory}.
 */
@RestController
@RequestMapping("/api")
public class TransactionHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoryResource.class);

    private static final String ENTITY_NAME = "transactionHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionHistoryService transactionHistoryService;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final TransactionHistoryQueryService transactionHistoryQueryService;

    public TransactionHistoryResource(
        TransactionHistoryService transactionHistoryService,
        TransactionHistoryRepository transactionHistoryRepository,
        TransactionHistoryQueryService transactionHistoryQueryService
    ) {
        this.transactionHistoryService = transactionHistoryService;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryQueryService = transactionHistoryQueryService;
    }

    /**
     * {@code POST  /transaction-histories} : Create a new transactionHistory.
     *
     * @param transactionHistoryDTO the transactionHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionHistoryDTO, or with status {@code 400 (Bad Request)} if the transactionHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-histories")
    public ResponseEntity<TransactionHistoryDTO> createTransactionHistory(@Valid @RequestBody TransactionHistoryDTO transactionHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransactionHistory : {}", transactionHistoryDTO);
        if (transactionHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionHistoryDTO result = transactionHistoryService.save(transactionHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/transaction-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-histories/:id} : Updates an existing transactionHistory.
     *
     * @param id the id of the transactionHistoryDTO to save.
     * @param transactionHistoryDTO the transactionHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the transactionHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-histories/{id}")
    public ResponseEntity<TransactionHistoryDTO> updateTransactionHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransactionHistoryDTO transactionHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransactionHistory : {}, {}", id, transactionHistoryDTO);
        if (transactionHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransactionHistoryDTO result = transactionHistoryService.update(transactionHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transaction-histories/:id} : Partial updates given fields of an existing transactionHistory, field will ignore if it is null
     *
     * @param id the id of the transactionHistoryDTO to save.
     * @param transactionHistoryDTO the transactionHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the transactionHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transactionHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactionHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transaction-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransactionHistoryDTO> partialUpdateTransactionHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransactionHistoryDTO transactionHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransactionHistory partially : {}, {}", id, transactionHistoryDTO);
        if (transactionHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransactionHistoryDTO> result = transactionHistoryService.partialUpdate(transactionHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transaction-histories} : get all the transactionHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionHistories in body.
     */
    @GetMapping("/transaction-histories")
    public ResponseEntity<List<TransactionHistoryDTO>> getAllTransactionHistories(
        TransactionHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TransactionHistories by criteria: {}", criteria);
        Page<TransactionHistoryDTO> page = transactionHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-histories/count} : count all the transactionHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transaction-histories/count")
    public ResponseEntity<Long> countTransactionHistories(TransactionHistoryCriteria criteria) {
        log.debug("REST request to count TransactionHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transaction-histories/:id} : get the "id" transactionHistory.
     *
     * @param id the id of the transactionHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-histories/{id}")
    public ResponseEntity<TransactionHistoryDTO> getTransactionHistory(@PathVariable Long id) {
        log.debug("REST request to get TransactionHistory : {}", id);
        Optional<TransactionHistoryDTO> transactionHistoryDTO = transactionHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionHistoryDTO);
    }

    /**
     * {@code DELETE  /transaction-histories/:id} : delete the "id" transactionHistory.
     *
     * @param id the id of the transactionHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-histories/{id}")
    public ResponseEntity<Void> deleteTransactionHistory(@PathVariable Long id) {
        log.debug("REST request to delete TransactionHistory : {}", id);
        transactionHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
