package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.PaymentFileHeaderRepository;
import com.melontech.landsys.service.PaymentFileHeaderService;
import com.melontech.landsys.service.dto.PaymentFileHeaderDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.PaymentFileHeader}.
 */
@RestController
@RequestMapping("/api")
public class PaymentFileHeaderResource {

    private final Logger log = LoggerFactory.getLogger(PaymentFileHeaderResource.class);

    private static final String ENTITY_NAME = "paymentFileHeader";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentFileHeaderService paymentFileHeaderService;

    private final PaymentFileHeaderRepository paymentFileHeaderRepository;

    public PaymentFileHeaderResource(
        PaymentFileHeaderService paymentFileHeaderService,
        PaymentFileHeaderRepository paymentFileHeaderRepository
    ) {
        this.paymentFileHeaderService = paymentFileHeaderService;
        this.paymentFileHeaderRepository = paymentFileHeaderRepository;
    }

    /**
     * {@code POST  /payment-file-headers} : Create a new paymentFileHeader.
     *
     * @param paymentFileHeaderDTO the paymentFileHeaderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentFileHeaderDTO, or with status {@code 400 (Bad Request)} if the paymentFileHeader has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-file-headers")
    public ResponseEntity<PaymentFileHeaderDTO> createPaymentFileHeader(@Valid @RequestBody PaymentFileHeaderDTO paymentFileHeaderDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentFileHeader : {}", paymentFileHeaderDTO);
        if (paymentFileHeaderDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentFileHeader cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentFileHeaderDTO result = paymentFileHeaderService.save(paymentFileHeaderDTO);
        return ResponseEntity
            .created(new URI("/api/payment-file-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-file-headers/:id} : Updates an existing paymentFileHeader.
     *
     * @param id the id of the paymentFileHeaderDTO to save.
     * @param paymentFileHeaderDTO the paymentFileHeaderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentFileHeaderDTO,
     * or with status {@code 400 (Bad Request)} if the paymentFileHeaderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentFileHeaderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-file-headers/{id}")
    public ResponseEntity<PaymentFileHeaderDTO> updatePaymentFileHeader(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentFileHeaderDTO paymentFileHeaderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentFileHeader : {}, {}", id, paymentFileHeaderDTO);
        if (paymentFileHeaderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentFileHeaderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentFileHeaderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentFileHeaderDTO result = paymentFileHeaderService.update(paymentFileHeaderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentFileHeaderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-file-headers/:id} : Partial updates given fields of an existing paymentFileHeader, field will ignore if it is null
     *
     * @param id the id of the paymentFileHeaderDTO to save.
     * @param paymentFileHeaderDTO the paymentFileHeaderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentFileHeaderDTO,
     * or with status {@code 400 (Bad Request)} if the paymentFileHeaderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentFileHeaderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentFileHeaderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-file-headers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentFileHeaderDTO> partialUpdatePaymentFileHeader(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentFileHeaderDTO paymentFileHeaderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentFileHeader partially : {}, {}", id, paymentFileHeaderDTO);
        if (paymentFileHeaderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentFileHeaderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentFileHeaderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentFileHeaderDTO> result = paymentFileHeaderService.partialUpdate(paymentFileHeaderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentFileHeaderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-file-headers} : get all the paymentFileHeaders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentFileHeaders in body.
     */
    @GetMapping("/payment-file-headers")
    public ResponseEntity<List<PaymentFileHeaderDTO>> getAllPaymentFileHeaders(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PaymentFileHeaders");
        Page<PaymentFileHeaderDTO> page = paymentFileHeaderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-file-headers/:id} : get the "id" paymentFileHeader.
     *
     * @param id the id of the paymentFileHeaderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentFileHeaderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-file-headers/{id}")
    public ResponseEntity<PaymentFileHeaderDTO> getPaymentFileHeader(@PathVariable Long id) {
        log.debug("REST request to get PaymentFileHeader : {}", id);
        Optional<PaymentFileHeaderDTO> paymentFileHeaderDTO = paymentFileHeaderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentFileHeaderDTO);
    }

    /**
     * {@code DELETE  /payment-file-headers/:id} : delete the "id" paymentFileHeader.
     *
     * @param id the id of the paymentFileHeaderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-file-headers/{id}")
    public ResponseEntity<Void> deletePaymentFileHeader(@PathVariable Long id) {
        log.debug("REST request to delete PaymentFileHeader : {}", id);
        paymentFileHeaderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
