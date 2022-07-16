package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentAdviceDetails;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.repository.PaymentAdviceDetailsRepository;
import com.melontech.landsys.service.criteria.PaymentAdviceDetailsCriteria;
import com.melontech.landsys.service.dto.PaymentAdviceDetailsDTO;
import com.melontech.landsys.service.mapper.PaymentAdviceDetailsMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentAdviceDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentAdviceDetailsResourceIT {

    private static final String DEFAULT_LAND_OWNERS = "AAAAAAAAAA";
    private static final String UPDATED_LAND_OWNERS = "BBBBBBBBBB";

    private static final HissaType DEFAULT_HISSA_TYPE = HissaType.SINGLE_OWNER;
    private static final HissaType UPDATED_HISSA_TYPE = HissaType.JOINT_OWNER;

    private static final String ENTITY_API_URL = "/api/payment-advice-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentAdviceDetailsRepository paymentAdviceDetailsRepository;

    @Autowired
    private PaymentAdviceDetailsMapper paymentAdviceDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentAdviceDetailsMockMvc;

    private PaymentAdviceDetails paymentAdviceDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAdviceDetails createEntity(EntityManager em) {
        PaymentAdviceDetails paymentAdviceDetails = new PaymentAdviceDetails()
            .landOwners(DEFAULT_LAND_OWNERS)
            .hissaType(DEFAULT_HISSA_TYPE);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        paymentAdviceDetails.setPaymentAdvice(paymentAdvice);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        paymentAdviceDetails.setProjectLand(projectLand);
        // Add required entity
        Khatedar khatedar;
        if (TestUtil.findAll(em, Khatedar.class).isEmpty()) {
            khatedar = KhatedarResourceIT.createEntity(em);
            em.persist(khatedar);
            em.flush();
        } else {
            khatedar = TestUtil.findAll(em, Khatedar.class).get(0);
        }
        paymentAdviceDetails.setKhatedar(khatedar);
        return paymentAdviceDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAdviceDetails createUpdatedEntity(EntityManager em) {
        PaymentAdviceDetails paymentAdviceDetails = new PaymentAdviceDetails()
            .landOwners(UPDATED_LAND_OWNERS)
            .hissaType(UPDATED_HISSA_TYPE);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createUpdatedEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        paymentAdviceDetails.setPaymentAdvice(paymentAdvice);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createUpdatedEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        paymentAdviceDetails.setProjectLand(projectLand);
        // Add required entity
        Khatedar khatedar;
        if (TestUtil.findAll(em, Khatedar.class).isEmpty()) {
            khatedar = KhatedarResourceIT.createUpdatedEntity(em);
            em.persist(khatedar);
            em.flush();
        } else {
            khatedar = TestUtil.findAll(em, Khatedar.class).get(0);
        }
        paymentAdviceDetails.setKhatedar(khatedar);
        return paymentAdviceDetails;
    }

    @BeforeEach
    public void initTest() {
        paymentAdviceDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentAdviceDetails() throws Exception {
        int databaseSizeBeforeCreate = paymentAdviceDetailsRepository.findAll().size();
        // Create the PaymentAdviceDetails
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);
        restPaymentAdviceDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentAdviceDetails testPaymentAdviceDetails = paymentAdviceDetailsList.get(paymentAdviceDetailsList.size() - 1);
        assertThat(testPaymentAdviceDetails.getLandOwners()).isEqualTo(DEFAULT_LAND_OWNERS);
        assertThat(testPaymentAdviceDetails.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
    }

    @Test
    @Transactional
    void createPaymentAdviceDetailsWithExistingId() throws Exception {
        // Create the PaymentAdviceDetails with an existing ID
        paymentAdviceDetails.setId(1L);
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        int databaseSizeBeforeCreate = paymentAdviceDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentAdviceDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLandOwnersIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceDetailsRepository.findAll().size();
        // set the field null
        paymentAdviceDetails.setLandOwners(null);

        // Create the PaymentAdviceDetails, which fails.
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        restPaymentAdviceDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHissaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceDetailsRepository.findAll().size();
        // set the field null
        paymentAdviceDetails.setHissaType(null);

        // Create the PaymentAdviceDetails, which fails.
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        restPaymentAdviceDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetails() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList
        restPaymentAdviceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentAdviceDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].landOwners").value(hasItem(DEFAULT_LAND_OWNERS)))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())));
    }

    @Test
    @Transactional
    void getPaymentAdviceDetails() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get the paymentAdviceDetails
        restPaymentAdviceDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentAdviceDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentAdviceDetails.getId().intValue()))
            .andExpect(jsonPath("$.landOwners").value(DEFAULT_LAND_OWNERS))
            .andExpect(jsonPath("$.hissaType").value(DEFAULT_HISSA_TYPE.toString()));
    }

    @Test
    @Transactional
    void getPaymentAdviceDetailsByIdFiltering() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        Long id = paymentAdviceDetails.getId();

        defaultPaymentAdviceDetailsShouldBeFound("id.equals=" + id);
        defaultPaymentAdviceDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentAdviceDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentAdviceDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentAdviceDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentAdviceDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByLandOwnersIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where landOwners equals to DEFAULT_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldBeFound("landOwners.equals=" + DEFAULT_LAND_OWNERS);

        // Get all the paymentAdviceDetailsList where landOwners equals to UPDATED_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldNotBeFound("landOwners.equals=" + UPDATED_LAND_OWNERS);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByLandOwnersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where landOwners not equals to DEFAULT_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldNotBeFound("landOwners.notEquals=" + DEFAULT_LAND_OWNERS);

        // Get all the paymentAdviceDetailsList where landOwners not equals to UPDATED_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldBeFound("landOwners.notEquals=" + UPDATED_LAND_OWNERS);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByLandOwnersIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where landOwners in DEFAULT_LAND_OWNERS or UPDATED_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldBeFound("landOwners.in=" + DEFAULT_LAND_OWNERS + "," + UPDATED_LAND_OWNERS);

        // Get all the paymentAdviceDetailsList where landOwners equals to UPDATED_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldNotBeFound("landOwners.in=" + UPDATED_LAND_OWNERS);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByLandOwnersIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where landOwners is not null
        defaultPaymentAdviceDetailsShouldBeFound("landOwners.specified=true");

        // Get all the paymentAdviceDetailsList where landOwners is null
        defaultPaymentAdviceDetailsShouldNotBeFound("landOwners.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByLandOwnersContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where landOwners contains DEFAULT_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldBeFound("landOwners.contains=" + DEFAULT_LAND_OWNERS);

        // Get all the paymentAdviceDetailsList where landOwners contains UPDATED_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldNotBeFound("landOwners.contains=" + UPDATED_LAND_OWNERS);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByLandOwnersNotContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where landOwners does not contain DEFAULT_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldNotBeFound("landOwners.doesNotContain=" + DEFAULT_LAND_OWNERS);

        // Get all the paymentAdviceDetailsList where landOwners does not contain UPDATED_LAND_OWNERS
        defaultPaymentAdviceDetailsShouldBeFound("landOwners.doesNotContain=" + UPDATED_LAND_OWNERS);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByHissaTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where hissaType equals to DEFAULT_HISSA_TYPE
        defaultPaymentAdviceDetailsShouldBeFound("hissaType.equals=" + DEFAULT_HISSA_TYPE);

        // Get all the paymentAdviceDetailsList where hissaType equals to UPDATED_HISSA_TYPE
        defaultPaymentAdviceDetailsShouldNotBeFound("hissaType.equals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByHissaTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where hissaType not equals to DEFAULT_HISSA_TYPE
        defaultPaymentAdviceDetailsShouldNotBeFound("hissaType.notEquals=" + DEFAULT_HISSA_TYPE);

        // Get all the paymentAdviceDetailsList where hissaType not equals to UPDATED_HISSA_TYPE
        defaultPaymentAdviceDetailsShouldBeFound("hissaType.notEquals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByHissaTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where hissaType in DEFAULT_HISSA_TYPE or UPDATED_HISSA_TYPE
        defaultPaymentAdviceDetailsShouldBeFound("hissaType.in=" + DEFAULT_HISSA_TYPE + "," + UPDATED_HISSA_TYPE);

        // Get all the paymentAdviceDetailsList where hissaType equals to UPDATED_HISSA_TYPE
        defaultPaymentAdviceDetailsShouldNotBeFound("hissaType.in=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByHissaTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        // Get all the paymentAdviceDetailsList where hissaType is not null
        defaultPaymentAdviceDetailsShouldBeFound("hissaType.specified=true");

        // Get all the paymentAdviceDetailsList where hissaType is null
        defaultPaymentAdviceDetailsShouldNotBeFound("hissaType.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByPaymentAdviceIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        em.persist(paymentAdvice);
        em.flush();
        paymentAdviceDetails.setPaymentAdvice(paymentAdvice);
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);
        Long paymentAdviceId = paymentAdvice.getId();

        // Get all the paymentAdviceDetailsList where paymentAdvice equals to paymentAdviceId
        defaultPaymentAdviceDetailsShouldBeFound("paymentAdviceId.equals=" + paymentAdviceId);

        // Get all the paymentAdviceDetailsList where paymentAdvice equals to (paymentAdviceId + 1)
        defaultPaymentAdviceDetailsShouldNotBeFound("paymentAdviceId.equals=" + (paymentAdviceId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByProjectLandIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        em.persist(projectLand);
        em.flush();
        paymentAdviceDetails.setProjectLand(projectLand);
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);
        Long projectLandId = projectLand.getId();

        // Get all the paymentAdviceDetailsList where projectLand equals to projectLandId
        defaultPaymentAdviceDetailsShouldBeFound("projectLandId.equals=" + projectLandId);

        // Get all the paymentAdviceDetailsList where projectLand equals to (projectLandId + 1)
        defaultPaymentAdviceDetailsShouldNotBeFound("projectLandId.equals=" + (projectLandId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentAdviceDetailsByKhatedarIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);
        Khatedar khatedar;
        if (TestUtil.findAll(em, Khatedar.class).isEmpty()) {
            khatedar = KhatedarResourceIT.createEntity(em);
            em.persist(khatedar);
            em.flush();
        } else {
            khatedar = TestUtil.findAll(em, Khatedar.class).get(0);
        }
        em.persist(khatedar);
        em.flush();
        paymentAdviceDetails.setKhatedar(khatedar);
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);
        Long khatedarId = khatedar.getId();

        // Get all the paymentAdviceDetailsList where khatedar equals to khatedarId
        defaultPaymentAdviceDetailsShouldBeFound("khatedarId.equals=" + khatedarId);

        // Get all the paymentAdviceDetailsList where khatedar equals to (khatedarId + 1)
        defaultPaymentAdviceDetailsShouldNotBeFound("khatedarId.equals=" + (khatedarId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentAdviceDetailsShouldBeFound(String filter) throws Exception {
        restPaymentAdviceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentAdviceDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].landOwners").value(hasItem(DEFAULT_LAND_OWNERS)))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())));

        // Check, that the count call also returns 1
        restPaymentAdviceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentAdviceDetailsShouldNotBeFound(String filter) throws Exception {
        restPaymentAdviceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentAdviceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaymentAdviceDetails() throws Exception {
        // Get the paymentAdviceDetails
        restPaymentAdviceDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentAdviceDetails() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();

        // Update the paymentAdviceDetails
        PaymentAdviceDetails updatedPaymentAdviceDetails = paymentAdviceDetailsRepository.findById(paymentAdviceDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentAdviceDetails are not directly saved in db
        em.detach(updatedPaymentAdviceDetails);
        updatedPaymentAdviceDetails.landOwners(UPDATED_LAND_OWNERS).hissaType(UPDATED_HISSA_TYPE);
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(updatedPaymentAdviceDetails);

        restPaymentAdviceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentAdviceDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentAdviceDetails testPaymentAdviceDetails = paymentAdviceDetailsList.get(paymentAdviceDetailsList.size() - 1);
        assertThat(testPaymentAdviceDetails.getLandOwners()).isEqualTo(UPDATED_LAND_OWNERS);
        assertThat(testPaymentAdviceDetails.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPaymentAdviceDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();
        paymentAdviceDetails.setId(count.incrementAndGet());

        // Create the PaymentAdviceDetails
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentAdviceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentAdviceDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentAdviceDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();
        paymentAdviceDetails.setId(count.incrementAndGet());

        // Create the PaymentAdviceDetails
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAdviceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentAdviceDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();
        paymentAdviceDetails.setId(count.incrementAndGet());

        // Create the PaymentAdviceDetails
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAdviceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentAdviceDetailsWithPatch() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();

        // Update the paymentAdviceDetails using partial update
        PaymentAdviceDetails partialUpdatedPaymentAdviceDetails = new PaymentAdviceDetails();
        partialUpdatedPaymentAdviceDetails.setId(paymentAdviceDetails.getId());

        partialUpdatedPaymentAdviceDetails.landOwners(UPDATED_LAND_OWNERS);

        restPaymentAdviceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentAdviceDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentAdviceDetails))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentAdviceDetails testPaymentAdviceDetails = paymentAdviceDetailsList.get(paymentAdviceDetailsList.size() - 1);
        assertThat(testPaymentAdviceDetails.getLandOwners()).isEqualTo(UPDATED_LAND_OWNERS);
        assertThat(testPaymentAdviceDetails.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePaymentAdviceDetailsWithPatch() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();

        // Update the paymentAdviceDetails using partial update
        PaymentAdviceDetails partialUpdatedPaymentAdviceDetails = new PaymentAdviceDetails();
        partialUpdatedPaymentAdviceDetails.setId(paymentAdviceDetails.getId());

        partialUpdatedPaymentAdviceDetails.landOwners(UPDATED_LAND_OWNERS).hissaType(UPDATED_HISSA_TYPE);

        restPaymentAdviceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentAdviceDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentAdviceDetails))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentAdviceDetails testPaymentAdviceDetails = paymentAdviceDetailsList.get(paymentAdviceDetailsList.size() - 1);
        assertThat(testPaymentAdviceDetails.getLandOwners()).isEqualTo(UPDATED_LAND_OWNERS);
        assertThat(testPaymentAdviceDetails.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentAdviceDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();
        paymentAdviceDetails.setId(count.incrementAndGet());

        // Create the PaymentAdviceDetails
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentAdviceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentAdviceDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentAdviceDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();
        paymentAdviceDetails.setId(count.incrementAndGet());

        // Create the PaymentAdviceDetails
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAdviceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentAdviceDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceDetailsRepository.findAll().size();
        paymentAdviceDetails.setId(count.incrementAndGet());

        // Create the PaymentAdviceDetails
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = paymentAdviceDetailsMapper.toDto(paymentAdviceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAdviceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentAdviceDetails in the database
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentAdviceDetails() throws Exception {
        // Initialize the database
        paymentAdviceDetailsRepository.saveAndFlush(paymentAdviceDetails);

        int databaseSizeBeforeDelete = paymentAdviceDetailsRepository.findAll().size();

        // Delete the paymentAdviceDetails
        restPaymentAdviceDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentAdviceDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentAdviceDetails> paymentAdviceDetailsList = paymentAdviceDetailsRepository.findAll();
        assertThat(paymentAdviceDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
