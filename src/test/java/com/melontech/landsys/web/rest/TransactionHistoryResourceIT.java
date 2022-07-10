package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.TransactionHistory;
import com.melontech.landsys.repository.TransactionHistoryRepository;
import com.melontech.landsys.service.criteria.TransactionHistoryCriteria;
import com.melontech.landsys.service.dto.TransactionHistoryDTO;
import com.melontech.landsys.service.mapper.TransactionHistoryMapper;
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
 * Integration tests for the {@link TransactionHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionHistoryResourceIT {

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KHASRA_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_KHASRA_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CITIZEN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITIZEN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITIZEN_AADHAR = "AAAAAAAAAA";
    private static final String UPDATED_CITIZEN_AADHAR = "BBBBBBBBBB";

    private static final String DEFAULT_SURVEYER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SURVEYER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAND_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_LAND_VALUE = "BBBBBBBBBB";

    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_PAYMENT_AMOUNT = 2D;
    private static final Double SMALLER_PAYMENT_AMOUNT = 1D - 1D;

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_1 = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_2 = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_3 = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transaction-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionHistoryMockMvc;

    private TransactionHistory transactionHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionHistory createEntity(EntityManager em) {
        TransactionHistory transactionHistory = new TransactionHistory()
            .projectName(DEFAULT_PROJECT_NAME)
            .khasraNumber(DEFAULT_KHASRA_NUMBER)
            .state(DEFAULT_STATE)
            .citizenName(DEFAULT_CITIZEN_NAME)
            .citizenAadhar(DEFAULT_CITIZEN_AADHAR)
            .surveyerName(DEFAULT_SURVEYER_NAME)
            .landValue(DEFAULT_LAND_VALUE)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .bankName(DEFAULT_BANK_NAME)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .eventType(DEFAULT_EVENT_TYPE)
            .eventStatus(DEFAULT_EVENT_STATUS)
            .approver1(DEFAULT_APPROVER_1)
            .approver2(DEFAULT_APPROVER_2)
            .approver3(DEFAULT_APPROVER_3);
        return transactionHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionHistory createUpdatedEntity(EntityManager em) {
        TransactionHistory transactionHistory = new TransactionHistory()
            .projectName(UPDATED_PROJECT_NAME)
            .khasraNumber(UPDATED_KHASRA_NUMBER)
            .state(UPDATED_STATE)
            .citizenName(UPDATED_CITIZEN_NAME)
            .citizenAadhar(UPDATED_CITIZEN_AADHAR)
            .surveyerName(UPDATED_SURVEYER_NAME)
            .landValue(UPDATED_LAND_VALUE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .eventType(UPDATED_EVENT_TYPE)
            .eventStatus(UPDATED_EVENT_STATUS)
            .approver1(UPDATED_APPROVER_1)
            .approver2(UPDATED_APPROVER_2)
            .approver3(UPDATED_APPROVER_3);
        return transactionHistory;
    }

    @BeforeEach
    public void initTest() {
        transactionHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createTransactionHistory() throws Exception {
        int databaseSizeBeforeCreate = transactionHistoryRepository.findAll().size();
        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);
        restTransactionHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionHistory testTransactionHistory = transactionHistoryList.get(transactionHistoryList.size() - 1);
        assertThat(testTransactionHistory.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testTransactionHistory.getKhasraNumber()).isEqualTo(DEFAULT_KHASRA_NUMBER);
        assertThat(testTransactionHistory.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testTransactionHistory.getCitizenName()).isEqualTo(DEFAULT_CITIZEN_NAME);
        assertThat(testTransactionHistory.getCitizenAadhar()).isEqualTo(DEFAULT_CITIZEN_AADHAR);
        assertThat(testTransactionHistory.getSurveyerName()).isEqualTo(DEFAULT_SURVEYER_NAME);
        assertThat(testTransactionHistory.getLandValue()).isEqualTo(DEFAULT_LAND_VALUE);
        assertThat(testTransactionHistory.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testTransactionHistory.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testTransactionHistory.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testTransactionHistory.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testTransactionHistory.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testTransactionHistory.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testTransactionHistory.getEventStatus()).isEqualTo(DEFAULT_EVENT_STATUS);
        assertThat(testTransactionHistory.getApprover1()).isEqualTo(DEFAULT_APPROVER_1);
        assertThat(testTransactionHistory.getApprover2()).isEqualTo(DEFAULT_APPROVER_2);
        assertThat(testTransactionHistory.getApprover3()).isEqualTo(DEFAULT_APPROVER_3);
    }

    @Test
    @Transactional
    void createTransactionHistoryWithExistingId() throws Exception {
        // Create the TransactionHistory with an existing ID
        transactionHistory.setId(1L);
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        int databaseSizeBeforeCreate = transactionHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkProjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setProjectName(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKhasraNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setKhasraNumber(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setState(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCitizenNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setCitizenName(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCitizenAadharIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setCitizenAadhar(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLandValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setLandValue(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransactionHistories() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList
        restTransactionHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].khasraNumber").value(hasItem(DEFAULT_KHASRA_NUMBER)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].citizenName").value(hasItem(DEFAULT_CITIZEN_NAME)))
            .andExpect(jsonPath("$.[*].citizenAadhar").value(hasItem(DEFAULT_CITIZEN_AADHAR)))
            .andExpect(jsonPath("$.[*].surveyerName").value(hasItem(DEFAULT_SURVEYER_NAME)))
            .andExpect(jsonPath("$.[*].landValue").value(hasItem(DEFAULT_LAND_VALUE)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventStatus").value(hasItem(DEFAULT_EVENT_STATUS)))
            .andExpect(jsonPath("$.[*].approver1").value(hasItem(DEFAULT_APPROVER_1)))
            .andExpect(jsonPath("$.[*].approver2").value(hasItem(DEFAULT_APPROVER_2)))
            .andExpect(jsonPath("$.[*].approver3").value(hasItem(DEFAULT_APPROVER_3)));
    }

    @Test
    @Transactional
    void getTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get the transactionHistory
        restTransactionHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, transactionHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionHistory.getId().intValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.khasraNumber").value(DEFAULT_KHASRA_NUMBER))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.citizenName").value(DEFAULT_CITIZEN_NAME))
            .andExpect(jsonPath("$.citizenAadhar").value(DEFAULT_CITIZEN_AADHAR))
            .andExpect(jsonPath("$.surveyerName").value(DEFAULT_SURVEYER_NAME))
            .andExpect(jsonPath("$.landValue").value(DEFAULT_LAND_VALUE))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.eventStatus").value(DEFAULT_EVENT_STATUS))
            .andExpect(jsonPath("$.approver1").value(DEFAULT_APPROVER_1))
            .andExpect(jsonPath("$.approver2").value(DEFAULT_APPROVER_2))
            .andExpect(jsonPath("$.approver3").value(DEFAULT_APPROVER_3));
    }

    @Test
    @Transactional
    void getTransactionHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        Long id = transactionHistory.getId();

        defaultTransactionHistoryShouldBeFound("id.equals=" + id);
        defaultTransactionHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionHistoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByProjectNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where projectName equals to DEFAULT_PROJECT_NAME
        defaultTransactionHistoryShouldBeFound("projectName.equals=" + DEFAULT_PROJECT_NAME);

        // Get all the transactionHistoryList where projectName equals to UPDATED_PROJECT_NAME
        defaultTransactionHistoryShouldNotBeFound("projectName.equals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByProjectNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where projectName not equals to DEFAULT_PROJECT_NAME
        defaultTransactionHistoryShouldNotBeFound("projectName.notEquals=" + DEFAULT_PROJECT_NAME);

        // Get all the transactionHistoryList where projectName not equals to UPDATED_PROJECT_NAME
        defaultTransactionHistoryShouldBeFound("projectName.notEquals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByProjectNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where projectName in DEFAULT_PROJECT_NAME or UPDATED_PROJECT_NAME
        defaultTransactionHistoryShouldBeFound("projectName.in=" + DEFAULT_PROJECT_NAME + "," + UPDATED_PROJECT_NAME);

        // Get all the transactionHistoryList where projectName equals to UPDATED_PROJECT_NAME
        defaultTransactionHistoryShouldNotBeFound("projectName.in=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByProjectNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where projectName is not null
        defaultTransactionHistoryShouldBeFound("projectName.specified=true");

        // Get all the transactionHistoryList where projectName is null
        defaultTransactionHistoryShouldNotBeFound("projectName.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByProjectNameContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where projectName contains DEFAULT_PROJECT_NAME
        defaultTransactionHistoryShouldBeFound("projectName.contains=" + DEFAULT_PROJECT_NAME);

        // Get all the transactionHistoryList where projectName contains UPDATED_PROJECT_NAME
        defaultTransactionHistoryShouldNotBeFound("projectName.contains=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByProjectNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where projectName does not contain DEFAULT_PROJECT_NAME
        defaultTransactionHistoryShouldNotBeFound("projectName.doesNotContain=" + DEFAULT_PROJECT_NAME);

        // Get all the transactionHistoryList where projectName does not contain UPDATED_PROJECT_NAME
        defaultTransactionHistoryShouldBeFound("projectName.doesNotContain=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByKhasraNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where khasraNumber equals to DEFAULT_KHASRA_NUMBER
        defaultTransactionHistoryShouldBeFound("khasraNumber.equals=" + DEFAULT_KHASRA_NUMBER);

        // Get all the transactionHistoryList where khasraNumber equals to UPDATED_KHASRA_NUMBER
        defaultTransactionHistoryShouldNotBeFound("khasraNumber.equals=" + UPDATED_KHASRA_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByKhasraNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where khasraNumber not equals to DEFAULT_KHASRA_NUMBER
        defaultTransactionHistoryShouldNotBeFound("khasraNumber.notEquals=" + DEFAULT_KHASRA_NUMBER);

        // Get all the transactionHistoryList where khasraNumber not equals to UPDATED_KHASRA_NUMBER
        defaultTransactionHistoryShouldBeFound("khasraNumber.notEquals=" + UPDATED_KHASRA_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByKhasraNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where khasraNumber in DEFAULT_KHASRA_NUMBER or UPDATED_KHASRA_NUMBER
        defaultTransactionHistoryShouldBeFound("khasraNumber.in=" + DEFAULT_KHASRA_NUMBER + "," + UPDATED_KHASRA_NUMBER);

        // Get all the transactionHistoryList where khasraNumber equals to UPDATED_KHASRA_NUMBER
        defaultTransactionHistoryShouldNotBeFound("khasraNumber.in=" + UPDATED_KHASRA_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByKhasraNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where khasraNumber is not null
        defaultTransactionHistoryShouldBeFound("khasraNumber.specified=true");

        // Get all the transactionHistoryList where khasraNumber is null
        defaultTransactionHistoryShouldNotBeFound("khasraNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByKhasraNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where khasraNumber contains DEFAULT_KHASRA_NUMBER
        defaultTransactionHistoryShouldBeFound("khasraNumber.contains=" + DEFAULT_KHASRA_NUMBER);

        // Get all the transactionHistoryList where khasraNumber contains UPDATED_KHASRA_NUMBER
        defaultTransactionHistoryShouldNotBeFound("khasraNumber.contains=" + UPDATED_KHASRA_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByKhasraNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where khasraNumber does not contain DEFAULT_KHASRA_NUMBER
        defaultTransactionHistoryShouldNotBeFound("khasraNumber.doesNotContain=" + DEFAULT_KHASRA_NUMBER);

        // Get all the transactionHistoryList where khasraNumber does not contain UPDATED_KHASRA_NUMBER
        defaultTransactionHistoryShouldBeFound("khasraNumber.doesNotContain=" + UPDATED_KHASRA_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where state equals to DEFAULT_STATE
        defaultTransactionHistoryShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the transactionHistoryList where state equals to UPDATED_STATE
        defaultTransactionHistoryShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where state not equals to DEFAULT_STATE
        defaultTransactionHistoryShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the transactionHistoryList where state not equals to UPDATED_STATE
        defaultTransactionHistoryShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where state in DEFAULT_STATE or UPDATED_STATE
        defaultTransactionHistoryShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the transactionHistoryList where state equals to UPDATED_STATE
        defaultTransactionHistoryShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where state is not null
        defaultTransactionHistoryShouldBeFound("state.specified=true");

        // Get all the transactionHistoryList where state is null
        defaultTransactionHistoryShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByStateContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where state contains DEFAULT_STATE
        defaultTransactionHistoryShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the transactionHistoryList where state contains UPDATED_STATE
        defaultTransactionHistoryShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where state does not contain DEFAULT_STATE
        defaultTransactionHistoryShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the transactionHistoryList where state does not contain UPDATED_STATE
        defaultTransactionHistoryShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenName equals to DEFAULT_CITIZEN_NAME
        defaultTransactionHistoryShouldBeFound("citizenName.equals=" + DEFAULT_CITIZEN_NAME);

        // Get all the transactionHistoryList where citizenName equals to UPDATED_CITIZEN_NAME
        defaultTransactionHistoryShouldNotBeFound("citizenName.equals=" + UPDATED_CITIZEN_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenName not equals to DEFAULT_CITIZEN_NAME
        defaultTransactionHistoryShouldNotBeFound("citizenName.notEquals=" + DEFAULT_CITIZEN_NAME);

        // Get all the transactionHistoryList where citizenName not equals to UPDATED_CITIZEN_NAME
        defaultTransactionHistoryShouldBeFound("citizenName.notEquals=" + UPDATED_CITIZEN_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenName in DEFAULT_CITIZEN_NAME or UPDATED_CITIZEN_NAME
        defaultTransactionHistoryShouldBeFound("citizenName.in=" + DEFAULT_CITIZEN_NAME + "," + UPDATED_CITIZEN_NAME);

        // Get all the transactionHistoryList where citizenName equals to UPDATED_CITIZEN_NAME
        defaultTransactionHistoryShouldNotBeFound("citizenName.in=" + UPDATED_CITIZEN_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenName is not null
        defaultTransactionHistoryShouldBeFound("citizenName.specified=true");

        // Get all the transactionHistoryList where citizenName is null
        defaultTransactionHistoryShouldNotBeFound("citizenName.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenNameContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenName contains DEFAULT_CITIZEN_NAME
        defaultTransactionHistoryShouldBeFound("citizenName.contains=" + DEFAULT_CITIZEN_NAME);

        // Get all the transactionHistoryList where citizenName contains UPDATED_CITIZEN_NAME
        defaultTransactionHistoryShouldNotBeFound("citizenName.contains=" + UPDATED_CITIZEN_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenName does not contain DEFAULT_CITIZEN_NAME
        defaultTransactionHistoryShouldNotBeFound("citizenName.doesNotContain=" + DEFAULT_CITIZEN_NAME);

        // Get all the transactionHistoryList where citizenName does not contain UPDATED_CITIZEN_NAME
        defaultTransactionHistoryShouldBeFound("citizenName.doesNotContain=" + UPDATED_CITIZEN_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenAadharIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenAadhar equals to DEFAULT_CITIZEN_AADHAR
        defaultTransactionHistoryShouldBeFound("citizenAadhar.equals=" + DEFAULT_CITIZEN_AADHAR);

        // Get all the transactionHistoryList where citizenAadhar equals to UPDATED_CITIZEN_AADHAR
        defaultTransactionHistoryShouldNotBeFound("citizenAadhar.equals=" + UPDATED_CITIZEN_AADHAR);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenAadharIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenAadhar not equals to DEFAULT_CITIZEN_AADHAR
        defaultTransactionHistoryShouldNotBeFound("citizenAadhar.notEquals=" + DEFAULT_CITIZEN_AADHAR);

        // Get all the transactionHistoryList where citizenAadhar not equals to UPDATED_CITIZEN_AADHAR
        defaultTransactionHistoryShouldBeFound("citizenAadhar.notEquals=" + UPDATED_CITIZEN_AADHAR);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenAadharIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenAadhar in DEFAULT_CITIZEN_AADHAR or UPDATED_CITIZEN_AADHAR
        defaultTransactionHistoryShouldBeFound("citizenAadhar.in=" + DEFAULT_CITIZEN_AADHAR + "," + UPDATED_CITIZEN_AADHAR);

        // Get all the transactionHistoryList where citizenAadhar equals to UPDATED_CITIZEN_AADHAR
        defaultTransactionHistoryShouldNotBeFound("citizenAadhar.in=" + UPDATED_CITIZEN_AADHAR);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenAadharIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenAadhar is not null
        defaultTransactionHistoryShouldBeFound("citizenAadhar.specified=true");

        // Get all the transactionHistoryList where citizenAadhar is null
        defaultTransactionHistoryShouldNotBeFound("citizenAadhar.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenAadharContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenAadhar contains DEFAULT_CITIZEN_AADHAR
        defaultTransactionHistoryShouldBeFound("citizenAadhar.contains=" + DEFAULT_CITIZEN_AADHAR);

        // Get all the transactionHistoryList where citizenAadhar contains UPDATED_CITIZEN_AADHAR
        defaultTransactionHistoryShouldNotBeFound("citizenAadhar.contains=" + UPDATED_CITIZEN_AADHAR);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByCitizenAadharNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where citizenAadhar does not contain DEFAULT_CITIZEN_AADHAR
        defaultTransactionHistoryShouldNotBeFound("citizenAadhar.doesNotContain=" + DEFAULT_CITIZEN_AADHAR);

        // Get all the transactionHistoryList where citizenAadhar does not contain UPDATED_CITIZEN_AADHAR
        defaultTransactionHistoryShouldBeFound("citizenAadhar.doesNotContain=" + UPDATED_CITIZEN_AADHAR);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesBySurveyerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where surveyerName equals to DEFAULT_SURVEYER_NAME
        defaultTransactionHistoryShouldBeFound("surveyerName.equals=" + DEFAULT_SURVEYER_NAME);

        // Get all the transactionHistoryList where surveyerName equals to UPDATED_SURVEYER_NAME
        defaultTransactionHistoryShouldNotBeFound("surveyerName.equals=" + UPDATED_SURVEYER_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesBySurveyerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where surveyerName not equals to DEFAULT_SURVEYER_NAME
        defaultTransactionHistoryShouldNotBeFound("surveyerName.notEquals=" + DEFAULT_SURVEYER_NAME);

        // Get all the transactionHistoryList where surveyerName not equals to UPDATED_SURVEYER_NAME
        defaultTransactionHistoryShouldBeFound("surveyerName.notEquals=" + UPDATED_SURVEYER_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesBySurveyerNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where surveyerName in DEFAULT_SURVEYER_NAME or UPDATED_SURVEYER_NAME
        defaultTransactionHistoryShouldBeFound("surveyerName.in=" + DEFAULT_SURVEYER_NAME + "," + UPDATED_SURVEYER_NAME);

        // Get all the transactionHistoryList where surveyerName equals to UPDATED_SURVEYER_NAME
        defaultTransactionHistoryShouldNotBeFound("surveyerName.in=" + UPDATED_SURVEYER_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesBySurveyerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where surveyerName is not null
        defaultTransactionHistoryShouldBeFound("surveyerName.specified=true");

        // Get all the transactionHistoryList where surveyerName is null
        defaultTransactionHistoryShouldNotBeFound("surveyerName.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesBySurveyerNameContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where surveyerName contains DEFAULT_SURVEYER_NAME
        defaultTransactionHistoryShouldBeFound("surveyerName.contains=" + DEFAULT_SURVEYER_NAME);

        // Get all the transactionHistoryList where surveyerName contains UPDATED_SURVEYER_NAME
        defaultTransactionHistoryShouldNotBeFound("surveyerName.contains=" + UPDATED_SURVEYER_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesBySurveyerNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where surveyerName does not contain DEFAULT_SURVEYER_NAME
        defaultTransactionHistoryShouldNotBeFound("surveyerName.doesNotContain=" + DEFAULT_SURVEYER_NAME);

        // Get all the transactionHistoryList where surveyerName does not contain UPDATED_SURVEYER_NAME
        defaultTransactionHistoryShouldBeFound("surveyerName.doesNotContain=" + UPDATED_SURVEYER_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByLandValueIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where landValue equals to DEFAULT_LAND_VALUE
        defaultTransactionHistoryShouldBeFound("landValue.equals=" + DEFAULT_LAND_VALUE);

        // Get all the transactionHistoryList where landValue equals to UPDATED_LAND_VALUE
        defaultTransactionHistoryShouldNotBeFound("landValue.equals=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByLandValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where landValue not equals to DEFAULT_LAND_VALUE
        defaultTransactionHistoryShouldNotBeFound("landValue.notEquals=" + DEFAULT_LAND_VALUE);

        // Get all the transactionHistoryList where landValue not equals to UPDATED_LAND_VALUE
        defaultTransactionHistoryShouldBeFound("landValue.notEquals=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByLandValueIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where landValue in DEFAULT_LAND_VALUE or UPDATED_LAND_VALUE
        defaultTransactionHistoryShouldBeFound("landValue.in=" + DEFAULT_LAND_VALUE + "," + UPDATED_LAND_VALUE);

        // Get all the transactionHistoryList where landValue equals to UPDATED_LAND_VALUE
        defaultTransactionHistoryShouldNotBeFound("landValue.in=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByLandValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where landValue is not null
        defaultTransactionHistoryShouldBeFound("landValue.specified=true");

        // Get all the transactionHistoryList where landValue is null
        defaultTransactionHistoryShouldNotBeFound("landValue.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByLandValueContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where landValue contains DEFAULT_LAND_VALUE
        defaultTransactionHistoryShouldBeFound("landValue.contains=" + DEFAULT_LAND_VALUE);

        // Get all the transactionHistoryList where landValue contains UPDATED_LAND_VALUE
        defaultTransactionHistoryShouldNotBeFound("landValue.contains=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByLandValueNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where landValue does not contain DEFAULT_LAND_VALUE
        defaultTransactionHistoryShouldNotBeFound("landValue.doesNotContain=" + DEFAULT_LAND_VALUE);

        // Get all the transactionHistoryList where landValue does not contain UPDATED_LAND_VALUE
        defaultTransactionHistoryShouldBeFound("landValue.doesNotContain=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentAmount equals to DEFAULT_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldBeFound("paymentAmount.equals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the transactionHistoryList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldNotBeFound("paymentAmount.equals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByPaymentAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentAmount not equals to DEFAULT_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldNotBeFound("paymentAmount.notEquals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the transactionHistoryList where paymentAmount not equals to UPDATED_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldBeFound("paymentAmount.notEquals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentAmount in DEFAULT_PAYMENT_AMOUNT or UPDATED_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldBeFound("paymentAmount.in=" + DEFAULT_PAYMENT_AMOUNT + "," + UPDATED_PAYMENT_AMOUNT);

        // Get all the transactionHistoryList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldNotBeFound("paymentAmount.in=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentAmount is not null
        defaultTransactionHistoryShouldBeFound("paymentAmount.specified=true");

        // Get all the transactionHistoryList where paymentAmount is null
        defaultTransactionHistoryShouldNotBeFound("paymentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByPaymentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentAmount is greater than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldBeFound("paymentAmount.greaterThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the transactionHistoryList where paymentAmount is greater than or equal to UPDATED_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldNotBeFound("paymentAmount.greaterThanOrEqual=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByPaymentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentAmount is less than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldBeFound("paymentAmount.lessThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the transactionHistoryList where paymentAmount is less than or equal to SMALLER_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldNotBeFound("paymentAmount.lessThanOrEqual=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByPaymentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentAmount is less than DEFAULT_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldNotBeFound("paymentAmount.lessThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the transactionHistoryList where paymentAmount is less than UPDATED_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldBeFound("paymentAmount.lessThan=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByPaymentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentAmount is greater than DEFAULT_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldNotBeFound("paymentAmount.greaterThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the transactionHistoryList where paymentAmount is greater than SMALLER_PAYMENT_AMOUNT
        defaultTransactionHistoryShouldBeFound("paymentAmount.greaterThan=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the transactionHistoryList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the transactionHistoryList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the transactionHistoryList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where accountNumber is not null
        defaultTransactionHistoryShouldBeFound("accountNumber.specified=true");

        // Get all the transactionHistoryList where accountNumber is null
        defaultTransactionHistoryShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the transactionHistoryList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the transactionHistoryList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultTransactionHistoryShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where bankName equals to DEFAULT_BANK_NAME
        defaultTransactionHistoryShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the transactionHistoryList where bankName equals to UPDATED_BANK_NAME
        defaultTransactionHistoryShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where bankName not equals to DEFAULT_BANK_NAME
        defaultTransactionHistoryShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the transactionHistoryList where bankName not equals to UPDATED_BANK_NAME
        defaultTransactionHistoryShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultTransactionHistoryShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the transactionHistoryList where bankName equals to UPDATED_BANK_NAME
        defaultTransactionHistoryShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where bankName is not null
        defaultTransactionHistoryShouldBeFound("bankName.specified=true");

        // Get all the transactionHistoryList where bankName is null
        defaultTransactionHistoryShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByBankNameContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where bankName contains DEFAULT_BANK_NAME
        defaultTransactionHistoryShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the transactionHistoryList where bankName contains UPDATED_BANK_NAME
        defaultTransactionHistoryShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where bankName does not contain DEFAULT_BANK_NAME
        defaultTransactionHistoryShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the transactionHistoryList where bankName does not contain UPDATED_BANK_NAME
        defaultTransactionHistoryShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionId equals to DEFAULT_TRANSACTION_ID
        defaultTransactionHistoryShouldBeFound("transactionId.equals=" + DEFAULT_TRANSACTION_ID);

        // Get all the transactionHistoryList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultTransactionHistoryShouldNotBeFound("transactionId.equals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionId not equals to DEFAULT_TRANSACTION_ID
        defaultTransactionHistoryShouldNotBeFound("transactionId.notEquals=" + DEFAULT_TRANSACTION_ID);

        // Get all the transactionHistoryList where transactionId not equals to UPDATED_TRANSACTION_ID
        defaultTransactionHistoryShouldBeFound("transactionId.notEquals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionId in DEFAULT_TRANSACTION_ID or UPDATED_TRANSACTION_ID
        defaultTransactionHistoryShouldBeFound("transactionId.in=" + DEFAULT_TRANSACTION_ID + "," + UPDATED_TRANSACTION_ID);

        // Get all the transactionHistoryList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultTransactionHistoryShouldNotBeFound("transactionId.in=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionId is not null
        defaultTransactionHistoryShouldBeFound("transactionId.specified=true");

        // Get all the transactionHistoryList where transactionId is null
        defaultTransactionHistoryShouldNotBeFound("transactionId.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionIdContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionId contains DEFAULT_TRANSACTION_ID
        defaultTransactionHistoryShouldBeFound("transactionId.contains=" + DEFAULT_TRANSACTION_ID);

        // Get all the transactionHistoryList where transactionId contains UPDATED_TRANSACTION_ID
        defaultTransactionHistoryShouldNotBeFound("transactionId.contains=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionIdNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionId does not contain DEFAULT_TRANSACTION_ID
        defaultTransactionHistoryShouldNotBeFound("transactionId.doesNotContain=" + DEFAULT_TRANSACTION_ID);

        // Get all the transactionHistoryList where transactionId does not contain UPDATED_TRANSACTION_ID
        defaultTransactionHistoryShouldBeFound("transactionId.doesNotContain=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultTransactionHistoryShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the transactionHistoryList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultTransactionHistoryShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionType not equals to DEFAULT_TRANSACTION_TYPE
        defaultTransactionHistoryShouldNotBeFound("transactionType.notEquals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the transactionHistoryList where transactionType not equals to UPDATED_TRANSACTION_TYPE
        defaultTransactionHistoryShouldBeFound("transactionType.notEquals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultTransactionHistoryShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the transactionHistoryList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultTransactionHistoryShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionType is not null
        defaultTransactionHistoryShouldBeFound("transactionType.specified=true");

        // Get all the transactionHistoryList where transactionType is null
        defaultTransactionHistoryShouldNotBeFound("transactionType.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionTypeContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionType contains DEFAULT_TRANSACTION_TYPE
        defaultTransactionHistoryShouldBeFound("transactionType.contains=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the transactionHistoryList where transactionType contains UPDATED_TRANSACTION_TYPE
        defaultTransactionHistoryShouldNotBeFound("transactionType.contains=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByTransactionTypeNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where transactionType does not contain DEFAULT_TRANSACTION_TYPE
        defaultTransactionHistoryShouldNotBeFound("transactionType.doesNotContain=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the transactionHistoryList where transactionType does not contain UPDATED_TRANSACTION_TYPE
        defaultTransactionHistoryShouldBeFound("transactionType.doesNotContain=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventType equals to DEFAULT_EVENT_TYPE
        defaultTransactionHistoryShouldBeFound("eventType.equals=" + DEFAULT_EVENT_TYPE);

        // Get all the transactionHistoryList where eventType equals to UPDATED_EVENT_TYPE
        defaultTransactionHistoryShouldNotBeFound("eventType.equals=" + UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventType not equals to DEFAULT_EVENT_TYPE
        defaultTransactionHistoryShouldNotBeFound("eventType.notEquals=" + DEFAULT_EVENT_TYPE);

        // Get all the transactionHistoryList where eventType not equals to UPDATED_EVENT_TYPE
        defaultTransactionHistoryShouldBeFound("eventType.notEquals=" + UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventTypeIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventType in DEFAULT_EVENT_TYPE or UPDATED_EVENT_TYPE
        defaultTransactionHistoryShouldBeFound("eventType.in=" + DEFAULT_EVENT_TYPE + "," + UPDATED_EVENT_TYPE);

        // Get all the transactionHistoryList where eventType equals to UPDATED_EVENT_TYPE
        defaultTransactionHistoryShouldNotBeFound("eventType.in=" + UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventType is not null
        defaultTransactionHistoryShouldBeFound("eventType.specified=true");

        // Get all the transactionHistoryList where eventType is null
        defaultTransactionHistoryShouldNotBeFound("eventType.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventTypeContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventType contains DEFAULT_EVENT_TYPE
        defaultTransactionHistoryShouldBeFound("eventType.contains=" + DEFAULT_EVENT_TYPE);

        // Get all the transactionHistoryList where eventType contains UPDATED_EVENT_TYPE
        defaultTransactionHistoryShouldNotBeFound("eventType.contains=" + UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventTypeNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventType does not contain DEFAULT_EVENT_TYPE
        defaultTransactionHistoryShouldNotBeFound("eventType.doesNotContain=" + DEFAULT_EVENT_TYPE);

        // Get all the transactionHistoryList where eventType does not contain UPDATED_EVENT_TYPE
        defaultTransactionHistoryShouldBeFound("eventType.doesNotContain=" + UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventStatus equals to DEFAULT_EVENT_STATUS
        defaultTransactionHistoryShouldBeFound("eventStatus.equals=" + DEFAULT_EVENT_STATUS);

        // Get all the transactionHistoryList where eventStatus equals to UPDATED_EVENT_STATUS
        defaultTransactionHistoryShouldNotBeFound("eventStatus.equals=" + UPDATED_EVENT_STATUS);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventStatus not equals to DEFAULT_EVENT_STATUS
        defaultTransactionHistoryShouldNotBeFound("eventStatus.notEquals=" + DEFAULT_EVENT_STATUS);

        // Get all the transactionHistoryList where eventStatus not equals to UPDATED_EVENT_STATUS
        defaultTransactionHistoryShouldBeFound("eventStatus.notEquals=" + UPDATED_EVENT_STATUS);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventStatus in DEFAULT_EVENT_STATUS or UPDATED_EVENT_STATUS
        defaultTransactionHistoryShouldBeFound("eventStatus.in=" + DEFAULT_EVENT_STATUS + "," + UPDATED_EVENT_STATUS);

        // Get all the transactionHistoryList where eventStatus equals to UPDATED_EVENT_STATUS
        defaultTransactionHistoryShouldNotBeFound("eventStatus.in=" + UPDATED_EVENT_STATUS);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventStatus is not null
        defaultTransactionHistoryShouldBeFound("eventStatus.specified=true");

        // Get all the transactionHistoryList where eventStatus is null
        defaultTransactionHistoryShouldNotBeFound("eventStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventStatusContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventStatus contains DEFAULT_EVENT_STATUS
        defaultTransactionHistoryShouldBeFound("eventStatus.contains=" + DEFAULT_EVENT_STATUS);

        // Get all the transactionHistoryList where eventStatus contains UPDATED_EVENT_STATUS
        defaultTransactionHistoryShouldNotBeFound("eventStatus.contains=" + UPDATED_EVENT_STATUS);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByEventStatusNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where eventStatus does not contain DEFAULT_EVENT_STATUS
        defaultTransactionHistoryShouldNotBeFound("eventStatus.doesNotContain=" + DEFAULT_EVENT_STATUS);

        // Get all the transactionHistoryList where eventStatus does not contain UPDATED_EVENT_STATUS
        defaultTransactionHistoryShouldBeFound("eventStatus.doesNotContain=" + UPDATED_EVENT_STATUS);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover1IsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver1 equals to DEFAULT_APPROVER_1
        defaultTransactionHistoryShouldBeFound("approver1.equals=" + DEFAULT_APPROVER_1);

        // Get all the transactionHistoryList where approver1 equals to UPDATED_APPROVER_1
        defaultTransactionHistoryShouldNotBeFound("approver1.equals=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver1 not equals to DEFAULT_APPROVER_1
        defaultTransactionHistoryShouldNotBeFound("approver1.notEquals=" + DEFAULT_APPROVER_1);

        // Get all the transactionHistoryList where approver1 not equals to UPDATED_APPROVER_1
        defaultTransactionHistoryShouldBeFound("approver1.notEquals=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover1IsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver1 in DEFAULT_APPROVER_1 or UPDATED_APPROVER_1
        defaultTransactionHistoryShouldBeFound("approver1.in=" + DEFAULT_APPROVER_1 + "," + UPDATED_APPROVER_1);

        // Get all the transactionHistoryList where approver1 equals to UPDATED_APPROVER_1
        defaultTransactionHistoryShouldNotBeFound("approver1.in=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover1IsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver1 is not null
        defaultTransactionHistoryShouldBeFound("approver1.specified=true");

        // Get all the transactionHistoryList where approver1 is null
        defaultTransactionHistoryShouldNotBeFound("approver1.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover1ContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver1 contains DEFAULT_APPROVER_1
        defaultTransactionHistoryShouldBeFound("approver1.contains=" + DEFAULT_APPROVER_1);

        // Get all the transactionHistoryList where approver1 contains UPDATED_APPROVER_1
        defaultTransactionHistoryShouldNotBeFound("approver1.contains=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover1NotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver1 does not contain DEFAULT_APPROVER_1
        defaultTransactionHistoryShouldNotBeFound("approver1.doesNotContain=" + DEFAULT_APPROVER_1);

        // Get all the transactionHistoryList where approver1 does not contain UPDATED_APPROVER_1
        defaultTransactionHistoryShouldBeFound("approver1.doesNotContain=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover2IsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver2 equals to DEFAULT_APPROVER_2
        defaultTransactionHistoryShouldBeFound("approver2.equals=" + DEFAULT_APPROVER_2);

        // Get all the transactionHistoryList where approver2 equals to UPDATED_APPROVER_2
        defaultTransactionHistoryShouldNotBeFound("approver2.equals=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver2 not equals to DEFAULT_APPROVER_2
        defaultTransactionHistoryShouldNotBeFound("approver2.notEquals=" + DEFAULT_APPROVER_2);

        // Get all the transactionHistoryList where approver2 not equals to UPDATED_APPROVER_2
        defaultTransactionHistoryShouldBeFound("approver2.notEquals=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover2IsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver2 in DEFAULT_APPROVER_2 or UPDATED_APPROVER_2
        defaultTransactionHistoryShouldBeFound("approver2.in=" + DEFAULT_APPROVER_2 + "," + UPDATED_APPROVER_2);

        // Get all the transactionHistoryList where approver2 equals to UPDATED_APPROVER_2
        defaultTransactionHistoryShouldNotBeFound("approver2.in=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover2IsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver2 is not null
        defaultTransactionHistoryShouldBeFound("approver2.specified=true");

        // Get all the transactionHistoryList where approver2 is null
        defaultTransactionHistoryShouldNotBeFound("approver2.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover2ContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver2 contains DEFAULT_APPROVER_2
        defaultTransactionHistoryShouldBeFound("approver2.contains=" + DEFAULT_APPROVER_2);

        // Get all the transactionHistoryList where approver2 contains UPDATED_APPROVER_2
        defaultTransactionHistoryShouldNotBeFound("approver2.contains=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover2NotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver2 does not contain DEFAULT_APPROVER_2
        defaultTransactionHistoryShouldNotBeFound("approver2.doesNotContain=" + DEFAULT_APPROVER_2);

        // Get all the transactionHistoryList where approver2 does not contain UPDATED_APPROVER_2
        defaultTransactionHistoryShouldBeFound("approver2.doesNotContain=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover3IsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver3 equals to DEFAULT_APPROVER_3
        defaultTransactionHistoryShouldBeFound("approver3.equals=" + DEFAULT_APPROVER_3);

        // Get all the transactionHistoryList where approver3 equals to UPDATED_APPROVER_3
        defaultTransactionHistoryShouldNotBeFound("approver3.equals=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver3 not equals to DEFAULT_APPROVER_3
        defaultTransactionHistoryShouldNotBeFound("approver3.notEquals=" + DEFAULT_APPROVER_3);

        // Get all the transactionHistoryList where approver3 not equals to UPDATED_APPROVER_3
        defaultTransactionHistoryShouldBeFound("approver3.notEquals=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover3IsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver3 in DEFAULT_APPROVER_3 or UPDATED_APPROVER_3
        defaultTransactionHistoryShouldBeFound("approver3.in=" + DEFAULT_APPROVER_3 + "," + UPDATED_APPROVER_3);

        // Get all the transactionHistoryList where approver3 equals to UPDATED_APPROVER_3
        defaultTransactionHistoryShouldNotBeFound("approver3.in=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover3IsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver3 is not null
        defaultTransactionHistoryShouldBeFound("approver3.specified=true");

        // Get all the transactionHistoryList where approver3 is null
        defaultTransactionHistoryShouldNotBeFound("approver3.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover3ContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver3 contains DEFAULT_APPROVER_3
        defaultTransactionHistoryShouldBeFound("approver3.contains=" + DEFAULT_APPROVER_3);

        // Get all the transactionHistoryList where approver3 contains UPDATED_APPROVER_3
        defaultTransactionHistoryShouldNotBeFound("approver3.contains=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllTransactionHistoriesByApprover3NotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where approver3 does not contain DEFAULT_APPROVER_3
        defaultTransactionHistoryShouldNotBeFound("approver3.doesNotContain=" + DEFAULT_APPROVER_3);

        // Get all the transactionHistoryList where approver3 does not contain UPDATED_APPROVER_3
        defaultTransactionHistoryShouldBeFound("approver3.doesNotContain=" + UPDATED_APPROVER_3);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionHistoryShouldBeFound(String filter) throws Exception {
        restTransactionHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].khasraNumber").value(hasItem(DEFAULT_KHASRA_NUMBER)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].citizenName").value(hasItem(DEFAULT_CITIZEN_NAME)))
            .andExpect(jsonPath("$.[*].citizenAadhar").value(hasItem(DEFAULT_CITIZEN_AADHAR)))
            .andExpect(jsonPath("$.[*].surveyerName").value(hasItem(DEFAULT_SURVEYER_NAME)))
            .andExpect(jsonPath("$.[*].landValue").value(hasItem(DEFAULT_LAND_VALUE)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventStatus").value(hasItem(DEFAULT_EVENT_STATUS)))
            .andExpect(jsonPath("$.[*].approver1").value(hasItem(DEFAULT_APPROVER_1)))
            .andExpect(jsonPath("$.[*].approver2").value(hasItem(DEFAULT_APPROVER_2)))
            .andExpect(jsonPath("$.[*].approver3").value(hasItem(DEFAULT_APPROVER_3)));

        // Check, that the count call also returns 1
        restTransactionHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionHistoryShouldNotBeFound(String filter) throws Exception {
        restTransactionHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransactionHistory() throws Exception {
        // Get the transactionHistory
        restTransactionHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();

        // Update the transactionHistory
        TransactionHistory updatedTransactionHistory = transactionHistoryRepository.findById(transactionHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionHistory are not directly saved in db
        em.detach(updatedTransactionHistory);
        updatedTransactionHistory
            .projectName(UPDATED_PROJECT_NAME)
            .khasraNumber(UPDATED_KHASRA_NUMBER)
            .state(UPDATED_STATE)
            .citizenName(UPDATED_CITIZEN_NAME)
            .citizenAadhar(UPDATED_CITIZEN_AADHAR)
            .surveyerName(UPDATED_SURVEYER_NAME)
            .landValue(UPDATED_LAND_VALUE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .eventType(UPDATED_EVENT_TYPE)
            .eventStatus(UPDATED_EVENT_STATUS)
            .approver1(UPDATED_APPROVER_1)
            .approver2(UPDATED_APPROVER_2)
            .approver3(UPDATED_APPROVER_3);
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(updatedTransactionHistory);

        restTransactionHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistory testTransactionHistory = transactionHistoryList.get(transactionHistoryList.size() - 1);
        assertThat(testTransactionHistory.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testTransactionHistory.getKhasraNumber()).isEqualTo(UPDATED_KHASRA_NUMBER);
        assertThat(testTransactionHistory.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTransactionHistory.getCitizenName()).isEqualTo(UPDATED_CITIZEN_NAME);
        assertThat(testTransactionHistory.getCitizenAadhar()).isEqualTo(UPDATED_CITIZEN_AADHAR);
        assertThat(testTransactionHistory.getSurveyerName()).isEqualTo(UPDATED_SURVEYER_NAME);
        assertThat(testTransactionHistory.getLandValue()).isEqualTo(UPDATED_LAND_VALUE);
        assertThat(testTransactionHistory.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testTransactionHistory.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testTransactionHistory.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testTransactionHistory.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testTransactionHistory.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testTransactionHistory.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testTransactionHistory.getEventStatus()).isEqualTo(UPDATED_EVENT_STATUS);
        assertThat(testTransactionHistory.getApprover1()).isEqualTo(UPDATED_APPROVER_1);
        assertThat(testTransactionHistory.getApprover2()).isEqualTo(UPDATED_APPROVER_2);
        assertThat(testTransactionHistory.getApprover3()).isEqualTo(UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void putNonExistingTransactionHistory() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();
        transactionHistory.setId(count.incrementAndGet());

        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransactionHistory() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();
        transactionHistory.setId(count.incrementAndGet());

        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransactionHistory() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();
        transactionHistory.setId(count.incrementAndGet());

        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionHistoryWithPatch() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();

        // Update the transactionHistory using partial update
        TransactionHistory partialUpdatedTransactionHistory = new TransactionHistory();
        partialUpdatedTransactionHistory.setId(transactionHistory.getId());

        partialUpdatedTransactionHistory
            .state(UPDATED_STATE)
            .surveyerName(UPDATED_SURVEYER_NAME)
            .landValue(UPDATED_LAND_VALUE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .eventStatus(UPDATED_EVENT_STATUS)
            .approver1(UPDATED_APPROVER_1)
            .approver3(UPDATED_APPROVER_3);

        restTransactionHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionHistory))
            )
            .andExpect(status().isOk());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistory testTransactionHistory = transactionHistoryList.get(transactionHistoryList.size() - 1);
        assertThat(testTransactionHistory.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testTransactionHistory.getKhasraNumber()).isEqualTo(DEFAULT_KHASRA_NUMBER);
        assertThat(testTransactionHistory.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTransactionHistory.getCitizenName()).isEqualTo(DEFAULT_CITIZEN_NAME);
        assertThat(testTransactionHistory.getCitizenAadhar()).isEqualTo(DEFAULT_CITIZEN_AADHAR);
        assertThat(testTransactionHistory.getSurveyerName()).isEqualTo(UPDATED_SURVEYER_NAME);
        assertThat(testTransactionHistory.getLandValue()).isEqualTo(UPDATED_LAND_VALUE);
        assertThat(testTransactionHistory.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testTransactionHistory.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testTransactionHistory.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testTransactionHistory.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testTransactionHistory.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testTransactionHistory.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testTransactionHistory.getEventStatus()).isEqualTo(UPDATED_EVENT_STATUS);
        assertThat(testTransactionHistory.getApprover1()).isEqualTo(UPDATED_APPROVER_1);
        assertThat(testTransactionHistory.getApprover2()).isEqualTo(DEFAULT_APPROVER_2);
        assertThat(testTransactionHistory.getApprover3()).isEqualTo(UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void fullUpdateTransactionHistoryWithPatch() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();

        // Update the transactionHistory using partial update
        TransactionHistory partialUpdatedTransactionHistory = new TransactionHistory();
        partialUpdatedTransactionHistory.setId(transactionHistory.getId());

        partialUpdatedTransactionHistory
            .projectName(UPDATED_PROJECT_NAME)
            .khasraNumber(UPDATED_KHASRA_NUMBER)
            .state(UPDATED_STATE)
            .citizenName(UPDATED_CITIZEN_NAME)
            .citizenAadhar(UPDATED_CITIZEN_AADHAR)
            .surveyerName(UPDATED_SURVEYER_NAME)
            .landValue(UPDATED_LAND_VALUE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .eventType(UPDATED_EVENT_TYPE)
            .eventStatus(UPDATED_EVENT_STATUS)
            .approver1(UPDATED_APPROVER_1)
            .approver2(UPDATED_APPROVER_2)
            .approver3(UPDATED_APPROVER_3);

        restTransactionHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionHistory))
            )
            .andExpect(status().isOk());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistory testTransactionHistory = transactionHistoryList.get(transactionHistoryList.size() - 1);
        assertThat(testTransactionHistory.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testTransactionHistory.getKhasraNumber()).isEqualTo(UPDATED_KHASRA_NUMBER);
        assertThat(testTransactionHistory.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTransactionHistory.getCitizenName()).isEqualTo(UPDATED_CITIZEN_NAME);
        assertThat(testTransactionHistory.getCitizenAadhar()).isEqualTo(UPDATED_CITIZEN_AADHAR);
        assertThat(testTransactionHistory.getSurveyerName()).isEqualTo(UPDATED_SURVEYER_NAME);
        assertThat(testTransactionHistory.getLandValue()).isEqualTo(UPDATED_LAND_VALUE);
        assertThat(testTransactionHistory.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testTransactionHistory.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testTransactionHistory.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testTransactionHistory.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testTransactionHistory.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testTransactionHistory.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testTransactionHistory.getEventStatus()).isEqualTo(UPDATED_EVENT_STATUS);
        assertThat(testTransactionHistory.getApprover1()).isEqualTo(UPDATED_APPROVER_1);
        assertThat(testTransactionHistory.getApprover2()).isEqualTo(UPDATED_APPROVER_2);
        assertThat(testTransactionHistory.getApprover3()).isEqualTo(UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void patchNonExistingTransactionHistory() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();
        transactionHistory.setId(count.incrementAndGet());

        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactionHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransactionHistory() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();
        transactionHistory.setId(count.incrementAndGet());

        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransactionHistory() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();
        transactionHistory.setId(count.incrementAndGet());

        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        int databaseSizeBeforeDelete = transactionHistoryRepository.findAll().size();

        // Delete the transactionHistory
        restTransactionHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, transactionHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
