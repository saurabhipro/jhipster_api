package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.domain.PaymentFileRecon;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import com.melontech.landsys.repository.PaymentAdviceRepository;
import com.melontech.landsys.service.criteria.PaymentAdviceCriteria;
import com.melontech.landsys.service.dto.PaymentAdviceDTO;
import com.melontech.landsys.service.mapper.PaymentAdviceMapper;
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
 * Integration tests for the {@link PaymentAdviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentAdviceResourceIT {

    private static final String DEFAULT_ACCOUNT_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_PAYMENT_AMOUNT = 2D;
    private static final Double SMALLER_PAYMENT_AMOUNT = 1D - 1D;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MICR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MICR_CODE = "BBBBBBBBBB";

    private static final PaymentAdviceType DEFAULT_PAYMENT_ADVICE_TYPE = PaymentAdviceType.ONLINE;
    private static final PaymentAdviceType UPDATED_PAYMENT_ADVICE_TYPE = PaymentAdviceType.CHECQUE;

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.APPROVED;

    private static final HissaType DEFAULT_HISSA_TYPE = HissaType.SINGLE_OWNER;
    private static final HissaType UPDATED_HISSA_TYPE = HissaType.JOINT_OWNER;

    private static final String ENTITY_API_URL = "/api/payment-advices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentAdviceRepository paymentAdviceRepository;

    @Autowired
    private PaymentAdviceMapper paymentAdviceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentAdviceMockMvc;

    private PaymentAdvice paymentAdvice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAdvice createEntity(EntityManager em) {
        PaymentAdvice paymentAdvice = new PaymentAdvice()
            .accountHolderName(DEFAULT_ACCOUNT_HOLDER_NAME)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .bankName(DEFAULT_BANK_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .ifscCode(DEFAULT_IFSC_CODE)
            .checkNumber(DEFAULT_CHECK_NUMBER)
            .micrCode(DEFAULT_MICR_CODE)
            .paymentAdviceType(DEFAULT_PAYMENT_ADVICE_TYPE)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .hissaType(DEFAULT_HISSA_TYPE);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        paymentAdvice.setProjectLand(projectLand);
        // Add required entity
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        paymentAdvice.setLandCompensation(landCompensation);
        // Add required entity
        PaymentFile paymentFile;
        if (TestUtil.findAll(em, PaymentFile.class).isEmpty()) {
            paymentFile = PaymentFileResourceIT.createEntity(em);
            em.persist(paymentFile);
            em.flush();
        } else {
            paymentFile = TestUtil.findAll(em, PaymentFile.class).get(0);
        }
        paymentAdvice.setPaymentFile(paymentFile);
        // Add required entity
        PaymentFileRecon paymentFileRecon;
        if (TestUtil.findAll(em, PaymentFileRecon.class).isEmpty()) {
            paymentFileRecon = PaymentFileReconResourceIT.createEntity(em);
            em.persist(paymentFileRecon);
            em.flush();
        } else {
            paymentFileRecon = TestUtil.findAll(em, PaymentFileRecon.class).get(0);
        }
        paymentAdvice.setPaymentFileRecon(paymentFileRecon);
        return paymentAdvice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAdvice createUpdatedEntity(EntityManager em) {
        PaymentAdvice paymentAdvice = new PaymentAdvice()
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .bankName(UPDATED_BANK_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifscCode(UPDATED_IFSC_CODE)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .micrCode(UPDATED_MICR_CODE)
            .paymentAdviceType(UPDATED_PAYMENT_ADVICE_TYPE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .hissaType(UPDATED_HISSA_TYPE);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createUpdatedEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        paymentAdvice.setProjectLand(projectLand);
        // Add required entity
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createUpdatedEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        paymentAdvice.setLandCompensation(landCompensation);
        // Add required entity
        PaymentFile paymentFile;
        if (TestUtil.findAll(em, PaymentFile.class).isEmpty()) {
            paymentFile = PaymentFileResourceIT.createUpdatedEntity(em);
            em.persist(paymentFile);
            em.flush();
        } else {
            paymentFile = TestUtil.findAll(em, PaymentFile.class).get(0);
        }
        paymentAdvice.setPaymentFile(paymentFile);
        // Add required entity
        PaymentFileRecon paymentFileRecon;
        if (TestUtil.findAll(em, PaymentFileRecon.class).isEmpty()) {
            paymentFileRecon = PaymentFileReconResourceIT.createUpdatedEntity(em);
            em.persist(paymentFileRecon);
            em.flush();
        } else {
            paymentFileRecon = TestUtil.findAll(em, PaymentFileRecon.class).get(0);
        }
        paymentAdvice.setPaymentFileRecon(paymentFileRecon);
        return paymentAdvice;
    }

    @BeforeEach
    public void initTest() {
        paymentAdvice = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentAdvice() throws Exception {
        int databaseSizeBeforeCreate = paymentAdviceRepository.findAll().size();
        // Create the PaymentAdvice
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);
        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentAdvice testPaymentAdvice = paymentAdviceList.get(paymentAdviceList.size() - 1);
        assertThat(testPaymentAdvice.getAccountHolderName()).isEqualTo(DEFAULT_ACCOUNT_HOLDER_NAME);
        assertThat(testPaymentAdvice.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaymentAdvice.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testPaymentAdvice.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPaymentAdvice.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testPaymentAdvice.getCheckNumber()).isEqualTo(DEFAULT_CHECK_NUMBER);
        assertThat(testPaymentAdvice.getMicrCode()).isEqualTo(DEFAULT_MICR_CODE);
        assertThat(testPaymentAdvice.getPaymentAdviceType()).isEqualTo(DEFAULT_PAYMENT_ADVICE_TYPE);
        assertThat(testPaymentAdvice.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testPaymentAdvice.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPaymentAdvice.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
    }

    @Test
    @Transactional
    void createPaymentAdviceWithExistingId() throws Exception {
        // Create the PaymentAdvice with an existing ID
        paymentAdvice.setId(1L);
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        int databaseSizeBeforeCreate = paymentAdviceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountHolderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceRepository.findAll().size();
        // set the field null
        paymentAdvice.setAccountHolderName(null);

        // Create the PaymentAdvice, which fails.
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceRepository.findAll().size();
        // set the field null
        paymentAdvice.setPaymentAmount(null);

        // Create the PaymentAdvice, which fails.
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceRepository.findAll().size();
        // set the field null
        paymentAdvice.setBankName(null);

        // Create the PaymentAdvice, which fails.
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceRepository.findAll().size();
        // set the field null
        paymentAdvice.setAccountNumber(null);

        // Create the PaymentAdvice, which fails.
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIfscCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceRepository.findAll().size();
        // set the field null
        paymentAdvice.setIfscCode(null);

        // Create the PaymentAdvice, which fails.
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceRepository.findAll().size();
        // set the field null
        paymentAdvice.setPaymentStatus(null);

        // Create the PaymentAdvice, which fails.
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHissaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentAdviceRepository.findAll().size();
        // set the field null
        paymentAdvice.setHissaType(null);

        // Create the PaymentAdvice, which fails.
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        restPaymentAdviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentAdvices() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList
        restPaymentAdviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentAdvice.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountHolderName").value(hasItem(DEFAULT_ACCOUNT_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].paymentAdviceType").value(hasItem(DEFAULT_PAYMENT_ADVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())));
    }

    @Test
    @Transactional
    void getPaymentAdvice() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get the paymentAdvice
        restPaymentAdviceMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentAdvice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentAdvice.getId().intValue()))
            .andExpect(jsonPath("$.accountHolderName").value(DEFAULT_ACCOUNT_HOLDER_NAME))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.checkNumber").value(DEFAULT_CHECK_NUMBER))
            .andExpect(jsonPath("$.micrCode").value(DEFAULT_MICR_CODE))
            .andExpect(jsonPath("$.paymentAdviceType").value(DEFAULT_PAYMENT_ADVICE_TYPE.toString()))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.hissaType").value(DEFAULT_HISSA_TYPE.toString()));
    }

    @Test
    @Transactional
    void getPaymentAdvicesByIdFiltering() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        Long id = paymentAdvice.getId();

        defaultPaymentAdviceShouldBeFound("id.equals=" + id);
        defaultPaymentAdviceShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentAdviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentAdviceShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentAdviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentAdviceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountHolderNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountHolderName equals to DEFAULT_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldBeFound("accountHolderName.equals=" + DEFAULT_ACCOUNT_HOLDER_NAME);

        // Get all the paymentAdviceList where accountHolderName equals to UPDATED_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldNotBeFound("accountHolderName.equals=" + UPDATED_ACCOUNT_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountHolderNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountHolderName not equals to DEFAULT_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldNotBeFound("accountHolderName.notEquals=" + DEFAULT_ACCOUNT_HOLDER_NAME);

        // Get all the paymentAdviceList where accountHolderName not equals to UPDATED_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldBeFound("accountHolderName.notEquals=" + UPDATED_ACCOUNT_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountHolderNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountHolderName in DEFAULT_ACCOUNT_HOLDER_NAME or UPDATED_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldBeFound("accountHolderName.in=" + DEFAULT_ACCOUNT_HOLDER_NAME + "," + UPDATED_ACCOUNT_HOLDER_NAME);

        // Get all the paymentAdviceList where accountHolderName equals to UPDATED_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldNotBeFound("accountHolderName.in=" + UPDATED_ACCOUNT_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountHolderNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountHolderName is not null
        defaultPaymentAdviceShouldBeFound("accountHolderName.specified=true");

        // Get all the paymentAdviceList where accountHolderName is null
        defaultPaymentAdviceShouldNotBeFound("accountHolderName.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountHolderNameContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountHolderName contains DEFAULT_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldBeFound("accountHolderName.contains=" + DEFAULT_ACCOUNT_HOLDER_NAME);

        // Get all the paymentAdviceList where accountHolderName contains UPDATED_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldNotBeFound("accountHolderName.contains=" + UPDATED_ACCOUNT_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountHolderNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountHolderName does not contain DEFAULT_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldNotBeFound("accountHolderName.doesNotContain=" + DEFAULT_ACCOUNT_HOLDER_NAME);

        // Get all the paymentAdviceList where accountHolderName does not contain UPDATED_ACCOUNT_HOLDER_NAME
        defaultPaymentAdviceShouldBeFound("accountHolderName.doesNotContain=" + UPDATED_ACCOUNT_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAmount equals to DEFAULT_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldBeFound("paymentAmount.equals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentAdviceList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldNotBeFound("paymentAmount.equals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAmount not equals to DEFAULT_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldNotBeFound("paymentAmount.notEquals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentAdviceList where paymentAmount not equals to UPDATED_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldBeFound("paymentAmount.notEquals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAmount in DEFAULT_PAYMENT_AMOUNT or UPDATED_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldBeFound("paymentAmount.in=" + DEFAULT_PAYMENT_AMOUNT + "," + UPDATED_PAYMENT_AMOUNT);

        // Get all the paymentAdviceList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldNotBeFound("paymentAmount.in=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAmount is not null
        defaultPaymentAdviceShouldBeFound("paymentAmount.specified=true");

        // Get all the paymentAdviceList where paymentAmount is null
        defaultPaymentAdviceShouldNotBeFound("paymentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAmount is greater than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldBeFound("paymentAmount.greaterThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentAdviceList where paymentAmount is greater than or equal to UPDATED_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldNotBeFound("paymentAmount.greaterThanOrEqual=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAmount is less than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldBeFound("paymentAmount.lessThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentAdviceList where paymentAmount is less than or equal to SMALLER_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldNotBeFound("paymentAmount.lessThanOrEqual=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAmount is less than DEFAULT_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldNotBeFound("paymentAmount.lessThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentAdviceList where paymentAmount is less than UPDATED_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldBeFound("paymentAmount.lessThan=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAmount is greater than DEFAULT_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldNotBeFound("paymentAmount.greaterThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the paymentAdviceList where paymentAmount is greater than SMALLER_PAYMENT_AMOUNT
        defaultPaymentAdviceShouldBeFound("paymentAmount.greaterThan=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where bankName equals to DEFAULT_BANK_NAME
        defaultPaymentAdviceShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the paymentAdviceList where bankName equals to UPDATED_BANK_NAME
        defaultPaymentAdviceShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where bankName not equals to DEFAULT_BANK_NAME
        defaultPaymentAdviceShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the paymentAdviceList where bankName not equals to UPDATED_BANK_NAME
        defaultPaymentAdviceShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultPaymentAdviceShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the paymentAdviceList where bankName equals to UPDATED_BANK_NAME
        defaultPaymentAdviceShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where bankName is not null
        defaultPaymentAdviceShouldBeFound("bankName.specified=true");

        // Get all the paymentAdviceList where bankName is null
        defaultPaymentAdviceShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByBankNameContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where bankName contains DEFAULT_BANK_NAME
        defaultPaymentAdviceShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the paymentAdviceList where bankName contains UPDATED_BANK_NAME
        defaultPaymentAdviceShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where bankName does not contain DEFAULT_BANK_NAME
        defaultPaymentAdviceShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the paymentAdviceList where bankName does not contain UPDATED_BANK_NAME
        defaultPaymentAdviceShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentAdviceList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentAdviceList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the paymentAdviceList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountNumber is not null
        defaultPaymentAdviceShouldBeFound("accountNumber.specified=true");

        // Get all the paymentAdviceList where accountNumber is null
        defaultPaymentAdviceShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentAdviceList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentAdviceList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultPaymentAdviceShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultPaymentAdviceShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the paymentAdviceList where ifscCode equals to UPDATED_IFSC_CODE
        defaultPaymentAdviceShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByIfscCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where ifscCode not equals to DEFAULT_IFSC_CODE
        defaultPaymentAdviceShouldNotBeFound("ifscCode.notEquals=" + DEFAULT_IFSC_CODE);

        // Get all the paymentAdviceList where ifscCode not equals to UPDATED_IFSC_CODE
        defaultPaymentAdviceShouldBeFound("ifscCode.notEquals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultPaymentAdviceShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the paymentAdviceList where ifscCode equals to UPDATED_IFSC_CODE
        defaultPaymentAdviceShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where ifscCode is not null
        defaultPaymentAdviceShouldBeFound("ifscCode.specified=true");

        // Get all the paymentAdviceList where ifscCode is null
        defaultPaymentAdviceShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where ifscCode contains DEFAULT_IFSC_CODE
        defaultPaymentAdviceShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the paymentAdviceList where ifscCode contains UPDATED_IFSC_CODE
        defaultPaymentAdviceShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultPaymentAdviceShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the paymentAdviceList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultPaymentAdviceShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByCheckNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where checkNumber equals to DEFAULT_CHECK_NUMBER
        defaultPaymentAdviceShouldBeFound("checkNumber.equals=" + DEFAULT_CHECK_NUMBER);

        // Get all the paymentAdviceList where checkNumber equals to UPDATED_CHECK_NUMBER
        defaultPaymentAdviceShouldNotBeFound("checkNumber.equals=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByCheckNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where checkNumber not equals to DEFAULT_CHECK_NUMBER
        defaultPaymentAdviceShouldNotBeFound("checkNumber.notEquals=" + DEFAULT_CHECK_NUMBER);

        // Get all the paymentAdviceList where checkNumber not equals to UPDATED_CHECK_NUMBER
        defaultPaymentAdviceShouldBeFound("checkNumber.notEquals=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByCheckNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where checkNumber in DEFAULT_CHECK_NUMBER or UPDATED_CHECK_NUMBER
        defaultPaymentAdviceShouldBeFound("checkNumber.in=" + DEFAULT_CHECK_NUMBER + "," + UPDATED_CHECK_NUMBER);

        // Get all the paymentAdviceList where checkNumber equals to UPDATED_CHECK_NUMBER
        defaultPaymentAdviceShouldNotBeFound("checkNumber.in=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByCheckNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where checkNumber is not null
        defaultPaymentAdviceShouldBeFound("checkNumber.specified=true");

        // Get all the paymentAdviceList where checkNumber is null
        defaultPaymentAdviceShouldNotBeFound("checkNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByCheckNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where checkNumber contains DEFAULT_CHECK_NUMBER
        defaultPaymentAdviceShouldBeFound("checkNumber.contains=" + DEFAULT_CHECK_NUMBER);

        // Get all the paymentAdviceList where checkNumber contains UPDATED_CHECK_NUMBER
        defaultPaymentAdviceShouldNotBeFound("checkNumber.contains=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByCheckNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where checkNumber does not contain DEFAULT_CHECK_NUMBER
        defaultPaymentAdviceShouldNotBeFound("checkNumber.doesNotContain=" + DEFAULT_CHECK_NUMBER);

        // Get all the paymentAdviceList where checkNumber does not contain UPDATED_CHECK_NUMBER
        defaultPaymentAdviceShouldBeFound("checkNumber.doesNotContain=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByMicrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where micrCode equals to DEFAULT_MICR_CODE
        defaultPaymentAdviceShouldBeFound("micrCode.equals=" + DEFAULT_MICR_CODE);

        // Get all the paymentAdviceList where micrCode equals to UPDATED_MICR_CODE
        defaultPaymentAdviceShouldNotBeFound("micrCode.equals=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByMicrCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where micrCode not equals to DEFAULT_MICR_CODE
        defaultPaymentAdviceShouldNotBeFound("micrCode.notEquals=" + DEFAULT_MICR_CODE);

        // Get all the paymentAdviceList where micrCode not equals to UPDATED_MICR_CODE
        defaultPaymentAdviceShouldBeFound("micrCode.notEquals=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByMicrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where micrCode in DEFAULT_MICR_CODE or UPDATED_MICR_CODE
        defaultPaymentAdviceShouldBeFound("micrCode.in=" + DEFAULT_MICR_CODE + "," + UPDATED_MICR_CODE);

        // Get all the paymentAdviceList where micrCode equals to UPDATED_MICR_CODE
        defaultPaymentAdviceShouldNotBeFound("micrCode.in=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByMicrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where micrCode is not null
        defaultPaymentAdviceShouldBeFound("micrCode.specified=true");

        // Get all the paymentAdviceList where micrCode is null
        defaultPaymentAdviceShouldNotBeFound("micrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByMicrCodeContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where micrCode contains DEFAULT_MICR_CODE
        defaultPaymentAdviceShouldBeFound("micrCode.contains=" + DEFAULT_MICR_CODE);

        // Get all the paymentAdviceList where micrCode contains UPDATED_MICR_CODE
        defaultPaymentAdviceShouldNotBeFound("micrCode.contains=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByMicrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where micrCode does not contain DEFAULT_MICR_CODE
        defaultPaymentAdviceShouldNotBeFound("micrCode.doesNotContain=" + DEFAULT_MICR_CODE);

        // Get all the paymentAdviceList where micrCode does not contain UPDATED_MICR_CODE
        defaultPaymentAdviceShouldBeFound("micrCode.doesNotContain=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAdviceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAdviceType equals to DEFAULT_PAYMENT_ADVICE_TYPE
        defaultPaymentAdviceShouldBeFound("paymentAdviceType.equals=" + DEFAULT_PAYMENT_ADVICE_TYPE);

        // Get all the paymentAdviceList where paymentAdviceType equals to UPDATED_PAYMENT_ADVICE_TYPE
        defaultPaymentAdviceShouldNotBeFound("paymentAdviceType.equals=" + UPDATED_PAYMENT_ADVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAdviceTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAdviceType not equals to DEFAULT_PAYMENT_ADVICE_TYPE
        defaultPaymentAdviceShouldNotBeFound("paymentAdviceType.notEquals=" + DEFAULT_PAYMENT_ADVICE_TYPE);

        // Get all the paymentAdviceList where paymentAdviceType not equals to UPDATED_PAYMENT_ADVICE_TYPE
        defaultPaymentAdviceShouldBeFound("paymentAdviceType.notEquals=" + UPDATED_PAYMENT_ADVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAdviceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAdviceType in DEFAULT_PAYMENT_ADVICE_TYPE or UPDATED_PAYMENT_ADVICE_TYPE
        defaultPaymentAdviceShouldBeFound("paymentAdviceType.in=" + DEFAULT_PAYMENT_ADVICE_TYPE + "," + UPDATED_PAYMENT_ADVICE_TYPE);

        // Get all the paymentAdviceList where paymentAdviceType equals to UPDATED_PAYMENT_ADVICE_TYPE
        defaultPaymentAdviceShouldNotBeFound("paymentAdviceType.in=" + UPDATED_PAYMENT_ADVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentAdviceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentAdviceType is not null
        defaultPaymentAdviceShouldBeFound("paymentAdviceType.specified=true");

        // Get all the paymentAdviceList where paymentAdviceType is null
        defaultPaymentAdviceShouldNotBeFound("paymentAdviceType.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByReferenceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where referenceNumber equals to DEFAULT_REFERENCE_NUMBER
        defaultPaymentAdviceShouldBeFound("referenceNumber.equals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the paymentAdviceList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultPaymentAdviceShouldNotBeFound("referenceNumber.equals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByReferenceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where referenceNumber not equals to DEFAULT_REFERENCE_NUMBER
        defaultPaymentAdviceShouldNotBeFound("referenceNumber.notEquals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the paymentAdviceList where referenceNumber not equals to UPDATED_REFERENCE_NUMBER
        defaultPaymentAdviceShouldBeFound("referenceNumber.notEquals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByReferenceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where referenceNumber in DEFAULT_REFERENCE_NUMBER or UPDATED_REFERENCE_NUMBER
        defaultPaymentAdviceShouldBeFound("referenceNumber.in=" + DEFAULT_REFERENCE_NUMBER + "," + UPDATED_REFERENCE_NUMBER);

        // Get all the paymentAdviceList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultPaymentAdviceShouldNotBeFound("referenceNumber.in=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByReferenceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where referenceNumber is not null
        defaultPaymentAdviceShouldBeFound("referenceNumber.specified=true");

        // Get all the paymentAdviceList where referenceNumber is null
        defaultPaymentAdviceShouldNotBeFound("referenceNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByReferenceNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where referenceNumber contains DEFAULT_REFERENCE_NUMBER
        defaultPaymentAdviceShouldBeFound("referenceNumber.contains=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the paymentAdviceList where referenceNumber contains UPDATED_REFERENCE_NUMBER
        defaultPaymentAdviceShouldNotBeFound("referenceNumber.contains=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByReferenceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where referenceNumber does not contain DEFAULT_REFERENCE_NUMBER
        defaultPaymentAdviceShouldNotBeFound("referenceNumber.doesNotContain=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the paymentAdviceList where referenceNumber does not contain UPDATED_REFERENCE_NUMBER
        defaultPaymentAdviceShouldBeFound("referenceNumber.doesNotContain=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultPaymentAdviceShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentAdviceList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultPaymentAdviceShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentStatus not equals to DEFAULT_PAYMENT_STATUS
        defaultPaymentAdviceShouldNotBeFound("paymentStatus.notEquals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentAdviceList where paymentStatus not equals to UPDATED_PAYMENT_STATUS
        defaultPaymentAdviceShouldBeFound("paymentStatus.notEquals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultPaymentAdviceShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the paymentAdviceList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultPaymentAdviceShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where paymentStatus is not null
        defaultPaymentAdviceShouldBeFound("paymentStatus.specified=true");

        // Get all the paymentAdviceList where paymentStatus is null
        defaultPaymentAdviceShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByHissaTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where hissaType equals to DEFAULT_HISSA_TYPE
        defaultPaymentAdviceShouldBeFound("hissaType.equals=" + DEFAULT_HISSA_TYPE);

        // Get all the paymentAdviceList where hissaType equals to UPDATED_HISSA_TYPE
        defaultPaymentAdviceShouldNotBeFound("hissaType.equals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByHissaTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where hissaType not equals to DEFAULT_HISSA_TYPE
        defaultPaymentAdviceShouldNotBeFound("hissaType.notEquals=" + DEFAULT_HISSA_TYPE);

        // Get all the paymentAdviceList where hissaType not equals to UPDATED_HISSA_TYPE
        defaultPaymentAdviceShouldBeFound("hissaType.notEquals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByHissaTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where hissaType in DEFAULT_HISSA_TYPE or UPDATED_HISSA_TYPE
        defaultPaymentAdviceShouldBeFound("hissaType.in=" + DEFAULT_HISSA_TYPE + "," + UPDATED_HISSA_TYPE);

        // Get all the paymentAdviceList where hissaType equals to UPDATED_HISSA_TYPE
        defaultPaymentAdviceShouldNotBeFound("hissaType.in=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByHissaTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        // Get all the paymentAdviceList where hissaType is not null
        defaultPaymentAdviceShouldBeFound("hissaType.specified=true");

        // Get all the paymentAdviceList where hissaType is null
        defaultPaymentAdviceShouldNotBeFound("hissaType.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByProjectLandIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);
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
        paymentAdvice.setProjectLand(projectLand);
        paymentAdviceRepository.saveAndFlush(paymentAdvice);
        Long projectLandId = projectLand.getId();

        // Get all the paymentAdviceList where projectLand equals to projectLandId
        defaultPaymentAdviceShouldBeFound("projectLandId.equals=" + projectLandId);

        // Get all the paymentAdviceList where projectLand equals to (projectLandId + 1)
        defaultPaymentAdviceShouldNotBeFound("projectLandId.equals=" + (projectLandId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByLandCompensationIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        em.persist(landCompensation);
        em.flush();
        paymentAdvice.setLandCompensation(landCompensation);
        paymentAdviceRepository.saveAndFlush(paymentAdvice);
        Long landCompensationId = landCompensation.getId();

        // Get all the paymentAdviceList where landCompensation equals to landCompensationId
        defaultPaymentAdviceShouldBeFound("landCompensationId.equals=" + landCompensationId);

        // Get all the paymentAdviceList where landCompensation equals to (landCompensationId + 1)
        defaultPaymentAdviceShouldNotBeFound("landCompensationId.equals=" + (landCompensationId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentFileIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);
        PaymentFile paymentFile;
        if (TestUtil.findAll(em, PaymentFile.class).isEmpty()) {
            paymentFile = PaymentFileResourceIT.createEntity(em);
            em.persist(paymentFile);
            em.flush();
        } else {
            paymentFile = TestUtil.findAll(em, PaymentFile.class).get(0);
        }
        em.persist(paymentFile);
        em.flush();
        paymentAdvice.setPaymentFile(paymentFile);
        paymentAdviceRepository.saveAndFlush(paymentAdvice);
        Long paymentFileId = paymentFile.getId();

        // Get all the paymentAdviceList where paymentFile equals to paymentFileId
        defaultPaymentAdviceShouldBeFound("paymentFileId.equals=" + paymentFileId);

        // Get all the paymentAdviceList where paymentFile equals to (paymentFileId + 1)
        defaultPaymentAdviceShouldNotBeFound("paymentFileId.equals=" + (paymentFileId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentAdvicesByPaymentFileReconIsEqualToSomething() throws Exception {
        // Get already existing entity
        PaymentFileRecon paymentFileRecon = paymentAdvice.getPaymentFileRecon();
        paymentAdviceRepository.saveAndFlush(paymentAdvice);
        Long paymentFileReconId = paymentFileRecon.getId();

        // Get all the paymentAdviceList where paymentFileRecon equals to paymentFileReconId
        defaultPaymentAdviceShouldBeFound("paymentFileReconId.equals=" + paymentFileReconId);

        // Get all the paymentAdviceList where paymentFileRecon equals to (paymentFileReconId + 1)
        defaultPaymentAdviceShouldNotBeFound("paymentFileReconId.equals=" + (paymentFileReconId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentAdviceShouldBeFound(String filter) throws Exception {
        restPaymentAdviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentAdvice.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountHolderName").value(hasItem(DEFAULT_ACCOUNT_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].paymentAdviceType").value(hasItem(DEFAULT_PAYMENT_ADVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())));

        // Check, that the count call also returns 1
        restPaymentAdviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentAdviceShouldNotBeFound(String filter) throws Exception {
        restPaymentAdviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentAdviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaymentAdvice() throws Exception {
        // Get the paymentAdvice
        restPaymentAdviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentAdvice() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();

        // Update the paymentAdvice
        PaymentAdvice updatedPaymentAdvice = paymentAdviceRepository.findById(paymentAdvice.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentAdvice are not directly saved in db
        em.detach(updatedPaymentAdvice);
        updatedPaymentAdvice
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .bankName(UPDATED_BANK_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifscCode(UPDATED_IFSC_CODE)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .micrCode(UPDATED_MICR_CODE)
            .paymentAdviceType(UPDATED_PAYMENT_ADVICE_TYPE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .hissaType(UPDATED_HISSA_TYPE);
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(updatedPaymentAdvice);

        restPaymentAdviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentAdviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
        PaymentAdvice testPaymentAdvice = paymentAdviceList.get(paymentAdviceList.size() - 1);
        assertThat(testPaymentAdvice.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testPaymentAdvice.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentAdvice.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPaymentAdvice.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPaymentAdvice.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testPaymentAdvice.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testPaymentAdvice.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testPaymentAdvice.getPaymentAdviceType()).isEqualTo(UPDATED_PAYMENT_ADVICE_TYPE);
        assertThat(testPaymentAdvice.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testPaymentAdvice.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentAdvice.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPaymentAdvice() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();
        paymentAdvice.setId(count.incrementAndGet());

        // Create the PaymentAdvice
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentAdviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentAdviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentAdvice() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();
        paymentAdvice.setId(count.incrementAndGet());

        // Create the PaymentAdvice
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAdviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentAdvice() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();
        paymentAdvice.setId(count.incrementAndGet());

        // Create the PaymentAdvice
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAdviceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentAdviceWithPatch() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();

        // Update the paymentAdvice using partial update
        PaymentAdvice partialUpdatedPaymentAdvice = new PaymentAdvice();
        partialUpdatedPaymentAdvice.setId(paymentAdvice.getId());

        partialUpdatedPaymentAdvice
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .bankName(UPDATED_BANK_NAME)
            .checkNumber(UPDATED_CHECK_NUMBER);

        restPaymentAdviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentAdvice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentAdvice))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
        PaymentAdvice testPaymentAdvice = paymentAdviceList.get(paymentAdviceList.size() - 1);
        assertThat(testPaymentAdvice.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testPaymentAdvice.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentAdvice.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPaymentAdvice.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPaymentAdvice.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testPaymentAdvice.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testPaymentAdvice.getMicrCode()).isEqualTo(DEFAULT_MICR_CODE);
        assertThat(testPaymentAdvice.getPaymentAdviceType()).isEqualTo(DEFAULT_PAYMENT_ADVICE_TYPE);
        assertThat(testPaymentAdvice.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testPaymentAdvice.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPaymentAdvice.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePaymentAdviceWithPatch() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();

        // Update the paymentAdvice using partial update
        PaymentAdvice partialUpdatedPaymentAdvice = new PaymentAdvice();
        partialUpdatedPaymentAdvice.setId(paymentAdvice.getId());

        partialUpdatedPaymentAdvice
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .bankName(UPDATED_BANK_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifscCode(UPDATED_IFSC_CODE)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .micrCode(UPDATED_MICR_CODE)
            .paymentAdviceType(UPDATED_PAYMENT_ADVICE_TYPE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .hissaType(UPDATED_HISSA_TYPE);

        restPaymentAdviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentAdvice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentAdvice))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
        PaymentAdvice testPaymentAdvice = paymentAdviceList.get(paymentAdviceList.size() - 1);
        assertThat(testPaymentAdvice.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testPaymentAdvice.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentAdvice.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPaymentAdvice.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPaymentAdvice.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testPaymentAdvice.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testPaymentAdvice.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testPaymentAdvice.getPaymentAdviceType()).isEqualTo(UPDATED_PAYMENT_ADVICE_TYPE);
        assertThat(testPaymentAdvice.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testPaymentAdvice.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentAdvice.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentAdvice() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();
        paymentAdvice.setId(count.incrementAndGet());

        // Create the PaymentAdvice
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentAdviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentAdviceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentAdvice() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();
        paymentAdvice.setId(count.incrementAndGet());

        // Create the PaymentAdvice
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAdviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentAdvice() throws Exception {
        int databaseSizeBeforeUpdate = paymentAdviceRepository.findAll().size();
        paymentAdvice.setId(count.incrementAndGet());

        // Create the PaymentAdvice
        PaymentAdviceDTO paymentAdviceDTO = paymentAdviceMapper.toDto(paymentAdvice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAdviceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAdviceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentAdvice in the database
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentAdvice() throws Exception {
        // Initialize the database
        paymentAdviceRepository.saveAndFlush(paymentAdvice);

        int databaseSizeBeforeDelete = paymentAdviceRepository.findAll().size();

        // Delete the paymentAdvice
        restPaymentAdviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentAdvice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentAdvice> paymentAdviceList = paymentAdviceRepository.findAll();
        assertThat(paymentAdviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
