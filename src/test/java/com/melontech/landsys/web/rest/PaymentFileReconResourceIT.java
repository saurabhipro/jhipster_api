package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentFileRecon;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import com.melontech.landsys.repository.PaymentFileReconRepository;
import com.melontech.landsys.service.criteria.PaymentFileReconCriteria;
import com.melontech.landsys.service.dto.PaymentFileReconDTO;
import com.melontech.landsys.service.mapper.PaymentFileReconMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PaymentFileReconResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentFileReconResourceIT {

    private static final String DEFAULT_PRIMARY_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_HOLDER_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_PAYMENT_AMOUNT = 2D;
    private static final Double SMALLER_PAYMENT_AMOUNT = 1D - 1D;

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PAYMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_UTR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_UTR_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.APPROVED;

    private static final String ENTITY_API_URL = "/api/payment-file-recons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentFileReconRepository paymentFileReconRepository;

    @Autowired
    private PaymentFileReconMapper paymentFileReconMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentFileReconMockMvc;

    private PaymentFileRecon paymentFileRecon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentFileRecon createEntity(EntityManager em) {
        PaymentFileRecon paymentFileRecon = new PaymentFileRecon()
            .primaryHolderName(DEFAULT_PRIMARY_HOLDER_NAME)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .utrNumber(DEFAULT_UTR_NUMBER)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        paymentFileRecon.setPaymentAdvice(paymentAdvice);
        return paymentFileRecon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentFileRecon createUpdatedEntity(EntityManager em) {
        PaymentFileRecon paymentFileRecon = new PaymentFileRecon()
            .primaryHolderName(UPDATED_PRIMARY_HOLDER_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .utrNumber(UPDATED_UTR_NUMBER)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createUpdatedEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        paymentFileRecon.setPaymentAdvice(paymentAdvice);
        return paymentFileRecon;
    }

    @BeforeEach
    public void initTest() {
        paymentFileRecon = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentFileRecon() throws Exception {
        int databaseSizeBeforeCreate = paymentFileReconRepository.findAll().size();
        // Create the PaymentFileRecon
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);
        restPaymentFileReconMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentFileRecon testPaymentFileRecon = paymentFileReconList.get(paymentFileReconList.size() - 1);
        assertThat(testPaymentFileRecon.getPrimaryHolderName()).isEqualTo(DEFAULT_PRIMARY_HOLDER_NAME);
        assertThat(testPaymentFileRecon.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaymentFileRecon.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPaymentFileRecon.getUtrNumber()).isEqualTo(DEFAULT_UTR_NUMBER);
        assertThat(testPaymentFileRecon.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testPaymentFileRecon.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void createPaymentFileReconWithExistingId() throws Exception {
        // Create the PaymentFileRecon with an existing ID
        paymentFileRecon.setId(1L);
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        int databaseSizeBeforeCreate = paymentFileReconRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentFileReconMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrimaryHolderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileReconRepository.findAll().size();
        // set the field null
        paymentFileRecon.setPrimaryHolderName(null);

        // Create the PaymentFileRecon, which fails.
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        restPaymentFileReconMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileReconRepository.findAll().size();
        // set the field null
        paymentFileRecon.setPaymentAmount(null);

        // Create the PaymentFileRecon, which fails.
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        restPaymentFileReconMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUtrNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileReconRepository.findAll().size();
        // set the field null
        paymentFileRecon.setUtrNumber(null);

        // Create the PaymentFileRecon, which fails.
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        restPaymentFileReconMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileReconRepository.findAll().size();
        // set the field null
        paymentFileRecon.setPaymentStatus(null);

        // Create the PaymentFileRecon, which fails.
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        restPaymentFileReconMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentFileRecons() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList
        restPaymentFileReconMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentFileRecon.getId().intValue())))
            .andExpect(jsonPath("$.[*].primaryHolderName").value(hasItem(DEFAULT_PRIMARY_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].utrNumber").value(hasItem(DEFAULT_UTR_NUMBER)))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getPaymentFileRecon() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get the paymentFileRecon
        restPaymentFileReconMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentFileRecon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentFileRecon.getId().intValue()))
            .andExpect(jsonPath("$.primaryHolderName").value(DEFAULT_PRIMARY_HOLDER_NAME))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.utrNumber").value(DEFAULT_UTR_NUMBER))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getPaymentFileReconsByIdFiltering() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        Long id = paymentFileRecon.getId();

        defaultPaymentFileReconShouldBeFound("id.equals=" + id);
        defaultPaymentFileReconShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentFileReconShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentFileReconShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentFileReconShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentFileReconShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPrimaryHolderNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where primaryHolderName equals to DEFAULT_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldBeFound("primaryHolderName.equals=" + DEFAULT_PRIMARY_HOLDER_NAME);

        // Get all the paymentFileReconList where primaryHolderName equals to UPDATED_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldNotBeFound("primaryHolderName.equals=" + UPDATED_PRIMARY_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPrimaryHolderNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where primaryHolderName not equals to DEFAULT_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldNotBeFound("primaryHolderName.notEquals=" + DEFAULT_PRIMARY_HOLDER_NAME);

        // Get all the paymentFileReconList where primaryHolderName not equals to UPDATED_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldBeFound("primaryHolderName.notEquals=" + UPDATED_PRIMARY_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPrimaryHolderNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where primaryHolderName in DEFAULT_PRIMARY_HOLDER_NAME or UPDATED_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldBeFound("primaryHolderName.in=" + DEFAULT_PRIMARY_HOLDER_NAME + "," + UPDATED_PRIMARY_HOLDER_NAME);

        // Get all the paymentFileReconList where primaryHolderName equals to UPDATED_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldNotBeFound("primaryHolderName.in=" + UPDATED_PRIMARY_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPrimaryHolderNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where primaryHolderName is not null
        defaultPaymentFileReconShouldBeFound("primaryHolderName.specified=true");

        // Get all the paymentFileReconList where primaryHolderName is null
        defaultPaymentFileReconShouldNotBeFound("primaryHolderName.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPrimaryHolderNameContainsSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where primaryHolderName contains DEFAULT_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldBeFound("primaryHolderName.contains=" + DEFAULT_PRIMARY_HOLDER_NAME);

        // Get all the paymentFileReconList where primaryHolderName contains UPDATED_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldNotBeFound("primaryHolderName.contains=" + UPDATED_PRIMARY_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPrimaryHolderNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where primaryHolderName does not contain DEFAULT_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldNotBeFound("primaryHolderName.doesNotContain=" + DEFAULT_PRIMARY_HOLDER_NAME);

        // Get all the paymentFileReconList where primaryHolderName does not contain UPDATED_PRIMARY_HOLDER_NAME
        defaultPaymentFileReconShouldBeFound("primaryHolderName.doesNotContain=" + UPDATED_PRIMARY_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentAmount equals to DEFAULT_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldBeFound("paymentAmount.equals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentFileReconList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldNotBeFound("paymentAmount.equals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentAmount not equals to DEFAULT_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldNotBeFound("paymentAmount.notEquals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentFileReconList where paymentAmount not equals to UPDATED_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldBeFound("paymentAmount.notEquals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentAmount in DEFAULT_PAYMENT_AMOUNT or UPDATED_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldBeFound("paymentAmount.in=" + DEFAULT_PAYMENT_AMOUNT + "," + UPDATED_PAYMENT_AMOUNT);

        // Get all the paymentFileReconList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldNotBeFound("paymentAmount.in=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentAmount is not null
        defaultPaymentFileReconShouldBeFound("paymentAmount.specified=true");

        // Get all the paymentFileReconList where paymentAmount is null
        defaultPaymentFileReconShouldNotBeFound("paymentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentAmount is greater than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldBeFound("paymentAmount.greaterThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentFileReconList where paymentAmount is greater than or equal to UPDATED_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldNotBeFound("paymentAmount.greaterThanOrEqual=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentAmount is less than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldBeFound("paymentAmount.lessThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentFileReconList where paymentAmount is less than or equal to SMALLER_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldNotBeFound("paymentAmount.lessThanOrEqual=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentAmount is less than DEFAULT_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldNotBeFound("paymentAmount.lessThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentFileReconList where paymentAmount is less than UPDATED_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldBeFound("paymentAmount.lessThan=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentAmount is greater than DEFAULT_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldNotBeFound("paymentAmount.greaterThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentFileReconList where paymentAmount is greater than SMALLER_PAYMENT_AMOUNT
        defaultPaymentFileReconShouldBeFound("paymentAmount.greaterThan=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentDate equals to DEFAULT_PAYMENT_DATE
        defaultPaymentFileReconShouldBeFound("paymentDate.equals=" + DEFAULT_PAYMENT_DATE);

        // Get all the paymentFileReconList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultPaymentFileReconShouldNotBeFound("paymentDate.equals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentDate not equals to DEFAULT_PAYMENT_DATE
        defaultPaymentFileReconShouldNotBeFound("paymentDate.notEquals=" + DEFAULT_PAYMENT_DATE);

        // Get all the paymentFileReconList where paymentDate not equals to UPDATED_PAYMENT_DATE
        defaultPaymentFileReconShouldBeFound("paymentDate.notEquals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentDate in DEFAULT_PAYMENT_DATE or UPDATED_PAYMENT_DATE
        defaultPaymentFileReconShouldBeFound("paymentDate.in=" + DEFAULT_PAYMENT_DATE + "," + UPDATED_PAYMENT_DATE);

        // Get all the paymentFileReconList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultPaymentFileReconShouldNotBeFound("paymentDate.in=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentDate is not null
        defaultPaymentFileReconShouldBeFound("paymentDate.specified=true");

        // Get all the paymentFileReconList where paymentDate is null
        defaultPaymentFileReconShouldNotBeFound("paymentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentDate is greater than or equal to DEFAULT_PAYMENT_DATE
        defaultPaymentFileReconShouldBeFound("paymentDate.greaterThanOrEqual=" + DEFAULT_PAYMENT_DATE);

        // Get all the paymentFileReconList where paymentDate is greater than or equal to UPDATED_PAYMENT_DATE
        defaultPaymentFileReconShouldNotBeFound("paymentDate.greaterThanOrEqual=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentDate is less than or equal to DEFAULT_PAYMENT_DATE
        defaultPaymentFileReconShouldBeFound("paymentDate.lessThanOrEqual=" + DEFAULT_PAYMENT_DATE);

        // Get all the paymentFileReconList where paymentDate is less than or equal to SMALLER_PAYMENT_DATE
        defaultPaymentFileReconShouldNotBeFound("paymentDate.lessThanOrEqual=" + SMALLER_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentDate is less than DEFAULT_PAYMENT_DATE
        defaultPaymentFileReconShouldNotBeFound("paymentDate.lessThan=" + DEFAULT_PAYMENT_DATE);

        // Get all the paymentFileReconList where paymentDate is less than UPDATED_PAYMENT_DATE
        defaultPaymentFileReconShouldBeFound("paymentDate.lessThan=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentDate is greater than DEFAULT_PAYMENT_DATE
        defaultPaymentFileReconShouldNotBeFound("paymentDate.greaterThan=" + DEFAULT_PAYMENT_DATE);

        // Get all the paymentFileReconList where paymentDate is greater than SMALLER_PAYMENT_DATE
        defaultPaymentFileReconShouldBeFound("paymentDate.greaterThan=" + SMALLER_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByUtrNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where utrNumber equals to DEFAULT_UTR_NUMBER
        defaultPaymentFileReconShouldBeFound("utrNumber.equals=" + DEFAULT_UTR_NUMBER);

        // Get all the paymentFileReconList where utrNumber equals to UPDATED_UTR_NUMBER
        defaultPaymentFileReconShouldNotBeFound("utrNumber.equals=" + UPDATED_UTR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByUtrNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where utrNumber not equals to DEFAULT_UTR_NUMBER
        defaultPaymentFileReconShouldNotBeFound("utrNumber.notEquals=" + DEFAULT_UTR_NUMBER);

        // Get all the paymentFileReconList where utrNumber not equals to UPDATED_UTR_NUMBER
        defaultPaymentFileReconShouldBeFound("utrNumber.notEquals=" + UPDATED_UTR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByUtrNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where utrNumber in DEFAULT_UTR_NUMBER or UPDATED_UTR_NUMBER
        defaultPaymentFileReconShouldBeFound("utrNumber.in=" + DEFAULT_UTR_NUMBER + "," + UPDATED_UTR_NUMBER);

        // Get all the paymentFileReconList where utrNumber equals to UPDATED_UTR_NUMBER
        defaultPaymentFileReconShouldNotBeFound("utrNumber.in=" + UPDATED_UTR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByUtrNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where utrNumber is not null
        defaultPaymentFileReconShouldBeFound("utrNumber.specified=true");

        // Get all the paymentFileReconList where utrNumber is null
        defaultPaymentFileReconShouldNotBeFound("utrNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByUtrNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where utrNumber contains DEFAULT_UTR_NUMBER
        defaultPaymentFileReconShouldBeFound("utrNumber.contains=" + DEFAULT_UTR_NUMBER);

        // Get all the paymentFileReconList where utrNumber contains UPDATED_UTR_NUMBER
        defaultPaymentFileReconShouldNotBeFound("utrNumber.contains=" + UPDATED_UTR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByUtrNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where utrNumber does not contain DEFAULT_UTR_NUMBER
        defaultPaymentFileReconShouldNotBeFound("utrNumber.doesNotContain=" + DEFAULT_UTR_NUMBER);

        // Get all the paymentFileReconList where utrNumber does not contain UPDATED_UTR_NUMBER
        defaultPaymentFileReconShouldBeFound("utrNumber.doesNotContain=" + UPDATED_UTR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByReferenceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where referenceNumber equals to DEFAULT_REFERENCE_NUMBER
        defaultPaymentFileReconShouldBeFound("referenceNumber.equals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the paymentFileReconList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultPaymentFileReconShouldNotBeFound("referenceNumber.equals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByReferenceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where referenceNumber not equals to DEFAULT_REFERENCE_NUMBER
        defaultPaymentFileReconShouldNotBeFound("referenceNumber.notEquals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the paymentFileReconList where referenceNumber not equals to UPDATED_REFERENCE_NUMBER
        defaultPaymentFileReconShouldBeFound("referenceNumber.notEquals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByReferenceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where referenceNumber in DEFAULT_REFERENCE_NUMBER or UPDATED_REFERENCE_NUMBER
        defaultPaymentFileReconShouldBeFound("referenceNumber.in=" + DEFAULT_REFERENCE_NUMBER + "," + UPDATED_REFERENCE_NUMBER);

        // Get all the paymentFileReconList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultPaymentFileReconShouldNotBeFound("referenceNumber.in=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByReferenceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where referenceNumber is not null
        defaultPaymentFileReconShouldBeFound("referenceNumber.specified=true");

        // Get all the paymentFileReconList where referenceNumber is null
        defaultPaymentFileReconShouldNotBeFound("referenceNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByReferenceNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where referenceNumber contains DEFAULT_REFERENCE_NUMBER
        defaultPaymentFileReconShouldBeFound("referenceNumber.contains=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the paymentFileReconList where referenceNumber contains UPDATED_REFERENCE_NUMBER
        defaultPaymentFileReconShouldNotBeFound("referenceNumber.contains=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByReferenceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where referenceNumber does not contain DEFAULT_REFERENCE_NUMBER
        defaultPaymentFileReconShouldNotBeFound("referenceNumber.doesNotContain=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the paymentFileReconList where referenceNumber does not contain UPDATED_REFERENCE_NUMBER
        defaultPaymentFileReconShouldBeFound("referenceNumber.doesNotContain=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultPaymentFileReconShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentFileReconList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultPaymentFileReconShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentStatus not equals to DEFAULT_PAYMENT_STATUS
        defaultPaymentFileReconShouldNotBeFound("paymentStatus.notEquals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentFileReconList where paymentStatus not equals to UPDATED_PAYMENT_STATUS
        defaultPaymentFileReconShouldBeFound("paymentStatus.notEquals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultPaymentFileReconShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the paymentFileReconList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultPaymentFileReconShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        // Get all the paymentFileReconList where paymentStatus is not null
        defaultPaymentFileReconShouldBeFound("paymentStatus.specified=true");

        // Get all the paymentFileReconList where paymentStatus is null
        defaultPaymentFileReconShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFileReconsByPaymentAdviceIsEqualToSomething() throws Exception {
        // Get already existing entity
        PaymentAdvice paymentAdvice = paymentFileRecon.getPaymentAdvice();
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);
        Long paymentAdviceId = paymentAdvice.getId();

        // Get all the paymentFileReconList where paymentAdvice equals to paymentAdviceId
        defaultPaymentFileReconShouldBeFound("paymentAdviceId.equals=" + paymentAdviceId);

        // Get all the paymentFileReconList where paymentAdvice equals to (paymentAdviceId + 1)
        defaultPaymentFileReconShouldNotBeFound("paymentAdviceId.equals=" + (paymentAdviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentFileReconShouldBeFound(String filter) throws Exception {
        restPaymentFileReconMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentFileRecon.getId().intValue())))
            .andExpect(jsonPath("$.[*].primaryHolderName").value(hasItem(DEFAULT_PRIMARY_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].utrNumber").value(hasItem(DEFAULT_UTR_NUMBER)))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));

        // Check, that the count call also returns 1
        restPaymentFileReconMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentFileReconShouldNotBeFound(String filter) throws Exception {
        restPaymentFileReconMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentFileReconMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaymentFileRecon() throws Exception {
        // Get the paymentFileRecon
        restPaymentFileReconMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentFileRecon() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();

        // Update the paymentFileRecon
        PaymentFileRecon updatedPaymentFileRecon = paymentFileReconRepository.findById(paymentFileRecon.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentFileRecon are not directly saved in db
        em.detach(updatedPaymentFileRecon);
        updatedPaymentFileRecon
            .primaryHolderName(UPDATED_PRIMARY_HOLDER_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .utrNumber(UPDATED_UTR_NUMBER)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(updatedPaymentFileRecon);

        restPaymentFileReconMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentFileReconDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
        PaymentFileRecon testPaymentFileRecon = paymentFileReconList.get(paymentFileReconList.size() - 1);
        assertThat(testPaymentFileRecon.getPrimaryHolderName()).isEqualTo(UPDATED_PRIMARY_HOLDER_NAME);
        assertThat(testPaymentFileRecon.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentFileRecon.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPaymentFileRecon.getUtrNumber()).isEqualTo(UPDATED_UTR_NUMBER);
        assertThat(testPaymentFileRecon.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testPaymentFileRecon.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPaymentFileRecon() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();
        paymentFileRecon.setId(count.incrementAndGet());

        // Create the PaymentFileRecon
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentFileReconMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentFileReconDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentFileRecon() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();
        paymentFileRecon.setId(count.incrementAndGet());

        // Create the PaymentFileRecon
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileReconMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentFileRecon() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();
        paymentFileRecon.setId(count.incrementAndGet());

        // Create the PaymentFileRecon
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileReconMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentFileReconWithPatch() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();

        // Update the paymentFileRecon using partial update
        PaymentFileRecon partialUpdatedPaymentFileRecon = new PaymentFileRecon();
        partialUpdatedPaymentFileRecon.setId(paymentFileRecon.getId());

        partialUpdatedPaymentFileRecon
            .primaryHolderName(UPDATED_PRIMARY_HOLDER_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .utrNumber(UPDATED_UTR_NUMBER)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restPaymentFileReconMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentFileRecon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentFileRecon))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
        PaymentFileRecon testPaymentFileRecon = paymentFileReconList.get(paymentFileReconList.size() - 1);
        assertThat(testPaymentFileRecon.getPrimaryHolderName()).isEqualTo(UPDATED_PRIMARY_HOLDER_NAME);
        assertThat(testPaymentFileRecon.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentFileRecon.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPaymentFileRecon.getUtrNumber()).isEqualTo(UPDATED_UTR_NUMBER);
        assertThat(testPaymentFileRecon.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testPaymentFileRecon.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePaymentFileReconWithPatch() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();

        // Update the paymentFileRecon using partial update
        PaymentFileRecon partialUpdatedPaymentFileRecon = new PaymentFileRecon();
        partialUpdatedPaymentFileRecon.setId(paymentFileRecon.getId());

        partialUpdatedPaymentFileRecon
            .primaryHolderName(UPDATED_PRIMARY_HOLDER_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .utrNumber(UPDATED_UTR_NUMBER)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restPaymentFileReconMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentFileRecon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentFileRecon))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
        PaymentFileRecon testPaymentFileRecon = paymentFileReconList.get(paymentFileReconList.size() - 1);
        assertThat(testPaymentFileRecon.getPrimaryHolderName()).isEqualTo(UPDATED_PRIMARY_HOLDER_NAME);
        assertThat(testPaymentFileRecon.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentFileRecon.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPaymentFileRecon.getUtrNumber()).isEqualTo(UPDATED_UTR_NUMBER);
        assertThat(testPaymentFileRecon.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testPaymentFileRecon.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentFileRecon() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();
        paymentFileRecon.setId(count.incrementAndGet());

        // Create the PaymentFileRecon
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentFileReconMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentFileReconDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentFileRecon() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();
        paymentFileRecon.setId(count.incrementAndGet());

        // Create the PaymentFileRecon
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileReconMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentFileRecon() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileReconRepository.findAll().size();
        paymentFileRecon.setId(count.incrementAndGet());

        // Create the PaymentFileRecon
        PaymentFileReconDTO paymentFileReconDTO = paymentFileReconMapper.toDto(paymentFileRecon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileReconMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileReconDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentFileRecon in the database
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentFileRecon() throws Exception {
        // Initialize the database
        paymentFileReconRepository.saveAndFlush(paymentFileRecon);

        int databaseSizeBeforeDelete = paymentFileReconRepository.findAll().size();

        // Delete the paymentFileRecon
        restPaymentFileReconMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentFileRecon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentFileRecon> paymentFileReconList = paymentFileReconRepository.findAll();
        assertThat(paymentFileReconList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
