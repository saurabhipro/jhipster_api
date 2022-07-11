package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.PaymentFileReconRepository;
import com.melontech.landsys.service.PaymentFileReconQueryService;
import com.melontech.landsys.service.PaymentFileReconService;
import com.melontech.landsys.service.criteria.PaymentFileReconCriteria;
import com.melontech.landsys.service.dto.PaymentFileReconDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.PaymentFileRecon}.
 */
@RestController
@RequestMapping("/api")
public class PaymentFileReconResource {

    private final Logger log = LoggerFactory.getLogger(PaymentFileReconResource.class);

    private static final String ENTITY_NAME = "paymentFileRecon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentFileReconService paymentFileReconService;

    private final PaymentFileReconRepository paymentFileReconRepository;

    private final PaymentFileReconQueryService paymentFileReconQueryService;

    public PaymentFileReconResource(
        PaymentFileReconService paymentFileReconService,
        PaymentFileReconRepository paymentFileReconRepository,
        PaymentFileReconQueryService paymentFileReconQueryService
    ) {
        this.paymentFileReconService = paymentFileReconService;
        this.paymentFileReconRepository = paymentFileReconRepository;
        this.paymentFileReconQueryService = paymentFileReconQueryService;
    }

    /**
     * {@code POST  /payment-file-recons} : Create a new paymentFileRecon.
     *
     * @param paymentFileReconDTO the paymentFileReconDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentFileReconDTO, or with status {@code 400 (Bad Request)} if the paymentFileRecon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-file-recons")
    public ResponseEntity<PaymentFileReconDTO> createPaymentFileRecon(@Valid @RequestBody PaymentFileReconDTO paymentFileReconDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentFileRecon : {}", paymentFileReconDTO);
        if (paymentFileReconDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentFileRecon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentFileReconDTO result = paymentFileReconService.save(paymentFileReconDTO);
        return ResponseEntity
            .created(new URI("/api/payment-file-recons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-file-recons/:id} : Updates an existing paymentFileRecon.
     *
     * @param id the id of the paymentFileReconDTO to save.
     * @param paymentFileReconDTO the paymentFileReconDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentFileReconDTO,
     * or with status {@code 400 (Bad Request)} if the paymentFileReconDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentFileReconDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-file-recons/{id}")
    public ResponseEntity<PaymentFileReconDTO> updatePaymentFileRecon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentFileReconDTO paymentFileReconDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentFileRecon : {}, {}", id, paymentFileReconDTO);
        if (paymentFileReconDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentFileReconDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentFileReconRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentFileReconDTO result = paymentFileReconService.update(paymentFileReconDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentFileReconDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-file-recons/:id} : Partial updates given fields of an existing paymentFileRecon, field will ignore if it is null
     *
     * @param id the id of the paymentFileReconDTO to save.
     * @param paymentFileReconDTO the paymentFileReconDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentFileReconDTO,
     * or with status {@code 400 (Bad Request)} if the paymentFileReconDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentFileReconDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentFileReconDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-file-recons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentFileReconDTO> partialUpdatePaymentFileRecon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentFileReconDTO paymentFileReconDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentFileRecon partially : {}, {}", id, paymentFileReconDTO);
        if (paymentFileReconDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentFileReconDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentFileReconRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentFileReconDTO> result = paymentFileReconService.partialUpdate(paymentFileReconDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentFileReconDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-file-recons} : get all the paymentFileRecons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentFileRecons in body.
     */
    @GetMapping("/payment-file-recons")
    public ResponseEntity<List<PaymentFileReconDTO>> getAllPaymentFileRecons(
        PaymentFileReconCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PaymentFileRecons by criteria: {}", criteria);
        Page<PaymentFileReconDTO> page = paymentFileReconQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-file-recons/count} : count all the paymentFileRecons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-file-recons/count")
    public ResponseEntity<Long> countPaymentFileRecons(PaymentFileReconCriteria criteria) {
        log.debug("REST request to count PaymentFileRecons by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentFileReconQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-file-recons/:id} : get the "id" paymentFileRecon.
     *
     * @param id the id of the paymentFileReconDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentFileReconDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-file-recons/{id}")
    public ResponseEntity<PaymentFileReconDTO> getPaymentFileRecon(@PathVariable Long id) {
        log.debug("REST request to get PaymentFileRecon : {}", id);
        Optional<PaymentFileReconDTO> paymentFileReconDTO = paymentFileReconService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentFileReconDTO);
    }

    /**
     * {@code DELETE  /payment-file-recons/:id} : delete the "id" paymentFileRecon.
     *
     * @param id the id of the paymentFileReconDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-file-recons/{id}")
    public ResponseEntity<Void> deletePaymentFileRecon(@PathVariable Long id) {
        log.debug("REST request to delete PaymentFileRecon : {}", id);
        paymentFileReconService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
