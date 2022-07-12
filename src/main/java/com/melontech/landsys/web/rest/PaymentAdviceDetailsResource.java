package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.PaymentAdviceDetailsRepository;
import com.melontech.landsys.service.PaymentAdviceDetailsQueryService;
import com.melontech.landsys.service.PaymentAdviceDetailsService;
import com.melontech.landsys.service.criteria.PaymentAdviceDetailsCriteria;
import com.melontech.landsys.service.dto.PaymentAdviceDetailsDTO;
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
 * REST controller for managing {@link com.melontech.landsys.domain.PaymentAdviceDetails}.
 */
@RestController
@RequestMapping("/api")
public class PaymentAdviceDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentAdviceDetailsResource.class);

    private static final String ENTITY_NAME = "paymentAdviceDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentAdviceDetailsService paymentAdviceDetailsService;

    private final PaymentAdviceDetailsRepository paymentAdviceDetailsRepository;

    private final PaymentAdviceDetailsQueryService paymentAdviceDetailsQueryService;

    public PaymentAdviceDetailsResource(
        PaymentAdviceDetailsService paymentAdviceDetailsService,
        PaymentAdviceDetailsRepository paymentAdviceDetailsRepository,
        PaymentAdviceDetailsQueryService paymentAdviceDetailsQueryService
    ) {
        this.paymentAdviceDetailsService = paymentAdviceDetailsService;
        this.paymentAdviceDetailsRepository = paymentAdviceDetailsRepository;
        this.paymentAdviceDetailsQueryService = paymentAdviceDetailsQueryService;
    }

    /**
     * {@code POST  /payment-advice-details} : Create a new paymentAdviceDetails.
     *
     * @param paymentAdviceDetailsDTO the paymentAdviceDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentAdviceDetailsDTO, or with status {@code 400 (Bad Request)} if the paymentAdviceDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-advice-details")
    public ResponseEntity<PaymentAdviceDetailsDTO> createPaymentAdviceDetails(
        @Valid @RequestBody PaymentAdviceDetailsDTO paymentAdviceDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PaymentAdviceDetails : {}", paymentAdviceDetailsDTO);
        if (paymentAdviceDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentAdviceDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentAdviceDetailsDTO result = paymentAdviceDetailsService.save(paymentAdviceDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/payment-advice-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-advice-details/:id} : Updates an existing paymentAdviceDetails.
     *
     * @param id the id of the paymentAdviceDetailsDTO to save.
     * @param paymentAdviceDetailsDTO the paymentAdviceDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentAdviceDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentAdviceDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentAdviceDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-advice-details/{id}")
    public ResponseEntity<PaymentAdviceDetailsDTO> updatePaymentAdviceDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentAdviceDetailsDTO paymentAdviceDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentAdviceDetails : {}, {}", id, paymentAdviceDetailsDTO);
        if (paymentAdviceDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentAdviceDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentAdviceDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentAdviceDetailsDTO result = paymentAdviceDetailsService.update(paymentAdviceDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentAdviceDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-advice-details/:id} : Partial updates given fields of an existing paymentAdviceDetails, field will ignore if it is null
     *
     * @param id the id of the paymentAdviceDetailsDTO to save.
     * @param paymentAdviceDetailsDTO the paymentAdviceDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentAdviceDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentAdviceDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentAdviceDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentAdviceDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-advice-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentAdviceDetailsDTO> partialUpdatePaymentAdviceDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentAdviceDetailsDTO paymentAdviceDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentAdviceDetails partially : {}, {}", id, paymentAdviceDetailsDTO);
        if (paymentAdviceDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentAdviceDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentAdviceDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentAdviceDetailsDTO> result = paymentAdviceDetailsService.partialUpdate(paymentAdviceDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentAdviceDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-advice-details} : get all the paymentAdviceDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentAdviceDetails in body.
     */
    @GetMapping("/payment-advice-details")
    public ResponseEntity<List<PaymentAdviceDetailsDTO>> getAllPaymentAdviceDetails(
        PaymentAdviceDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PaymentAdviceDetails by criteria: {}", criteria);
        Page<PaymentAdviceDetailsDTO> page = paymentAdviceDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-advice-details/count} : count all the paymentAdviceDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-advice-details/count")
    public ResponseEntity<Long> countPaymentAdviceDetails(PaymentAdviceDetailsCriteria criteria) {
        log.debug("REST request to count PaymentAdviceDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentAdviceDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-advice-details/:id} : get the "id" paymentAdviceDetails.
     *
     * @param id the id of the paymentAdviceDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentAdviceDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-advice-details/{id}")
    public ResponseEntity<PaymentAdviceDetailsDTO> getPaymentAdviceDetails(@PathVariable Long id) {
        log.debug("REST request to get PaymentAdviceDetails : {}", id);
        Optional<PaymentAdviceDetailsDTO> paymentAdviceDetailsDTO = paymentAdviceDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentAdviceDetailsDTO);
    }

    /**
     * {@code DELETE  /payment-advice-details/:id} : delete the "id" paymentAdviceDetails.
     *
     * @param id the id of the paymentAdviceDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-advice-details/{id}")
    public ResponseEntity<Void> deletePaymentAdviceDetails(@PathVariable Long id) {
        log.debug("REST request to delete PaymentAdviceDetails : {}", id);
        paymentAdviceDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
