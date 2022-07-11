package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.PaymentFileRepository;
import com.melontech.landsys.service.PaymentFileQueryService;
import com.melontech.landsys.service.PaymentFileService;
import com.melontech.landsys.service.criteria.PaymentFileCriteria;
import com.melontech.landsys.service.dto.PaymentFileDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.PaymentFile}.
 */
@RestController
@RequestMapping("/api")
public class PaymentFileResource {

    private final Logger log = LoggerFactory.getLogger(PaymentFileResource.class);

    private static final String ENTITY_NAME = "paymentFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentFileService paymentFileService;

    private final PaymentFileRepository paymentFileRepository;

    private final PaymentFileQueryService paymentFileQueryService;

    public PaymentFileResource(
        PaymentFileService paymentFileService,
        PaymentFileRepository paymentFileRepository,
        PaymentFileQueryService paymentFileQueryService
    ) {
        this.paymentFileService = paymentFileService;
        this.paymentFileRepository = paymentFileRepository;
        this.paymentFileQueryService = paymentFileQueryService;
    }

    /**
     * {@code POST  /payment-files} : Create a new paymentFile.
     *
     * @param paymentFileDTO the paymentFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentFileDTO, or with status {@code 400 (Bad Request)} if the paymentFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-files")
    public ResponseEntity<PaymentFileDTO> createPaymentFile(@Valid @RequestBody PaymentFileDTO paymentFileDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentFile : {}", paymentFileDTO);
        if (paymentFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentFileDTO result = paymentFileService.save(paymentFileDTO);
        return ResponseEntity
            .created(new URI("/api/payment-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-files/:id} : Updates an existing paymentFile.
     *
     * @param id the id of the paymentFileDTO to save.
     * @param paymentFileDTO the paymentFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentFileDTO,
     * or with status {@code 400 (Bad Request)} if the paymentFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-files/{id}")
    public ResponseEntity<PaymentFileDTO> updatePaymentFile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentFileDTO paymentFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentFile : {}, {}", id, paymentFileDTO);
        if (paymentFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentFileDTO result = paymentFileService.update(paymentFileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-files/:id} : Partial updates given fields of an existing paymentFile, field will ignore if it is null
     *
     * @param id the id of the paymentFileDTO to save.
     * @param paymentFileDTO the paymentFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentFileDTO,
     * or with status {@code 400 (Bad Request)} if the paymentFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentFileDTO> partialUpdatePaymentFile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentFileDTO paymentFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentFile partially : {}, {}", id, paymentFileDTO);
        if (paymentFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentFileDTO> result = paymentFileService.partialUpdate(paymentFileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentFileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-files} : get all the paymentFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentFiles in body.
     */
    @GetMapping("/payment-files")
    public ResponseEntity<List<PaymentFileDTO>> getAllPaymentFiles(
        PaymentFileCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PaymentFiles by criteria: {}", criteria);
        Page<PaymentFileDTO> page = paymentFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-files/count} : count all the paymentFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-files/count")
    public ResponseEntity<Long> countPaymentFiles(PaymentFileCriteria criteria) {
        log.debug("REST request to count PaymentFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-files/:id} : get the "id" paymentFile.
     *
     * @param id the id of the paymentFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-files/{id}")
    public ResponseEntity<PaymentFileDTO> getPaymentFile(@PathVariable Long id) {
        log.debug("REST request to get PaymentFile : {}", id);
        Optional<PaymentFileDTO> paymentFileDTO = paymentFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentFileDTO);
    }

    /**
     * {@code DELETE  /payment-files/:id} : delete the "id" paymentFile.
     *
     * @param id the id of the paymentFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-files/{id}")
    public ResponseEntity<Void> deletePaymentFile(@PathVariable Long id) {
        log.debug("REST request to delete PaymentFile : {}", id);
        paymentFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
