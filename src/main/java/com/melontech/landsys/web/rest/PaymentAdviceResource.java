package com.melontech.landsys.web.rest;

import com.melontech.landsys.repository.PaymentAdviceRepository;
import com.melontech.landsys.service.PaymentAdviceQueryService;
import com.melontech.landsys.service.PaymentAdviceService;
import com.melontech.landsys.service.criteria.PaymentAdviceCriteria;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.melontech.landsys.domain.PaymentAdvice}.
 */
@RestController
@RequestMapping("/api")
public class PaymentAdviceResource {

    private final Logger log = LoggerFactory.getLogger(PaymentAdviceResource.class);

    private static final String ENTITY_NAME = "paymentAdvice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentAdviceService paymentAdviceService;

    private final PaymentAdviceRepository paymentAdviceRepository;

    private final PaymentAdviceQueryService paymentAdviceQueryService;

    public PaymentAdviceResource(
        PaymentAdviceService paymentAdviceService,
        PaymentAdviceRepository paymentAdviceRepository,
        PaymentAdviceQueryService paymentAdviceQueryService
    ) {
        this.paymentAdviceService = paymentAdviceService;
        this.paymentAdviceRepository = paymentAdviceRepository;
        this.paymentAdviceQueryService = paymentAdviceQueryService;
    }

    /**
     * {@code POST  /payment-advices} : Create a new paymentAdvice.
     *
     * @param paymentAdviceDTO the paymentAdviceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentAdviceDTO, or with status {@code 400 (Bad Request)} if the paymentAdvice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-advices")
    public ResponseEntity<PaymentAdviceDTO> createPaymentAdvice(@Valid @RequestBody PaymentAdviceDTO paymentAdviceDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentAdvice : {}", paymentAdviceDTO);
        if (paymentAdviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentAdvice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentAdviceDTO result = paymentAdviceService.save(paymentAdviceDTO);
        return ResponseEntity
            .created(new URI("/api/payment-advices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-advices/:id} : Updates an existing paymentAdvice.
     *
     * @param id the id of the paymentAdviceDTO to save.
     * @param paymentAdviceDTO the paymentAdviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentAdviceDTO,
     * or with status {@code 400 (Bad Request)} if the paymentAdviceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentAdviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-advices/{id}")
    public ResponseEntity<PaymentAdviceDTO> updatePaymentAdvice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentAdviceDTO paymentAdviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentAdvice : {}, {}", id, paymentAdviceDTO);
        if (paymentAdviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentAdviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentAdviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentAdviceDTO result = paymentAdviceService.update(paymentAdviceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentAdviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-advices/:id} : Partial updates given fields of an existing paymentAdvice, field will ignore if it is null
     *
     * @param id the id of the paymentAdviceDTO to save.
     * @param paymentAdviceDTO the paymentAdviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentAdviceDTO,
     * or with status {@code 400 (Bad Request)} if the paymentAdviceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentAdviceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentAdviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-advices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentAdviceDTO> partialUpdatePaymentAdvice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentAdviceDTO paymentAdviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentAdvice partially : {}, {}", id, paymentAdviceDTO);
        if (paymentAdviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentAdviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentAdviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentAdviceDTO> result = paymentAdviceService.partialUpdate(paymentAdviceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentAdviceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-advices} : get all the paymentAdvices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentAdvices in body.
     */
    @GetMapping("/payment-advices")
    public ResponseEntity<List<PaymentAdviceDTO>> getAllPaymentAdvices(
        PaymentAdviceCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PaymentAdvices by criteria: {}", criteria);
        Page<PaymentAdviceDTO> page = paymentAdviceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-advices/count} : count all the paymentAdvices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-advices/count")
    public ResponseEntity<Long> countPaymentAdvices(PaymentAdviceCriteria criteria) {
        log.debug("REST request to count PaymentAdvices by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentAdviceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-advices/:id} : get the "id" paymentAdvice.
     *
     * @param id the id of the paymentAdviceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentAdviceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-advices/{id}")
    public ResponseEntity<PaymentAdviceDTO> getPaymentAdvice(@PathVariable Long id) {
        log.debug("REST request to get PaymentAdvice : {}", id);
        Optional<PaymentAdviceDTO> paymentAdviceDTO = paymentAdviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentAdviceDTO);
    }

    /**
     * {@code DELETE  /payment-advices/:id} : delete the "id" paymentAdvice.
     *
     * @param id the id of the paymentAdviceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-advices/{id}")
    public ResponseEntity<Void> deletePaymentAdvice(@PathVariable Long id) {
        log.debug("REST request to delete PaymentAdvice : {}", id);
        paymentAdviceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
