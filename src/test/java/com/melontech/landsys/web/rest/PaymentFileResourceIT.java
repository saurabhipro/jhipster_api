package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import com.melontech.landsys.repository.PaymentFileRepository;
import com.melontech.landsys.service.criteria.PaymentFileCriteria;
import com.melontech.landsys.service.dto.PaymentFileDTO;
import com.melontech.landsys.service.mapper.PaymentFileMapper;
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
 * Integration tests for the {@link PaymentFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentFileResourceIT {

    private static final Double DEFAULT_PAYMENT_FILE_ID = 1D;
    private static final Double UPDATED_PAYMENT_FILE_ID = 2D;
    private static final Double SMALLER_PAYMENT_FILE_ID = 1D - 1D;

    private static final Double DEFAULT_TOTAL_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_PAYMENT_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_PAYMENT_AMOUNT = 1D - 1D;

    private static final LocalDate DEFAULT_PAYMENT_FILE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_FILE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PAYMENT_FILE_DATE = LocalDate.ofEpochDay(-1L);

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.APPROVED;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentFileRepository paymentFileRepository;

    @Autowired
    private PaymentFileMapper paymentFileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentFileMockMvc;

    private PaymentFile paymentFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentFile createEntity(EntityManager em) {
        PaymentFile paymentFile = new PaymentFile()
            .paymentFileId(DEFAULT_PAYMENT_FILE_ID)
            .totalPaymentAmount(DEFAULT_TOTAL_PAYMENT_AMOUNT)
            .paymentFileDate(DEFAULT_PAYMENT_FILE_DATE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .bankName(DEFAULT_BANK_NAME)
            .ifscCode(DEFAULT_IFSC_CODE);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        paymentFile.getPaymentAdvices().add(paymentAdvice);
        return paymentFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentFile createUpdatedEntity(EntityManager em) {
        PaymentFile paymentFile = new PaymentFile()
            .paymentFileId(UPDATED_PAYMENT_FILE_ID)
            .totalPaymentAmount(UPDATED_TOTAL_PAYMENT_AMOUNT)
            .paymentFileDate(UPDATED_PAYMENT_FILE_DATE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createUpdatedEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        paymentFile.getPaymentAdvices().add(paymentAdvice);
        return paymentFile;
    }

    @BeforeEach
    public void initTest() {
        paymentFile = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentFile() throws Exception {
        int databaseSizeBeforeCreate = paymentFileRepository.findAll().size();
        // Create the PaymentFile
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);
        restPaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentFile testPaymentFile = paymentFileList.get(paymentFileList.size() - 1);
        assertThat(testPaymentFile.getPaymentFileId()).isEqualTo(DEFAULT_PAYMENT_FILE_ID);
        assertThat(testPaymentFile.getTotalPaymentAmount()).isEqualTo(DEFAULT_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPaymentFile.getPaymentFileDate()).isEqualTo(DEFAULT_PAYMENT_FILE_DATE);
        assertThat(testPaymentFile.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPaymentFile.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testPaymentFile.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
    }

    @Test
    @Transactional
    void createPaymentFileWithExistingId() throws Exception {
        // Create the PaymentFile with an existing ID
        paymentFile.setId(1L);
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        int databaseSizeBeforeCreate = paymentFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPaymentFileIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileRepository.findAll().size();
        // set the field null
        paymentFile.setPaymentFileId(null);

        // Create the PaymentFile, which fails.
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        restPaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalPaymentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileRepository.findAll().size();
        // set the field null
        paymentFile.setTotalPaymentAmount(null);

        // Create the PaymentFile, which fails.
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        restPaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileRepository.findAll().size();
        // set the field null
        paymentFile.setPaymentStatus(null);

        // Create the PaymentFile, which fails.
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        restPaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentFiles() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList
        restPaymentFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentFileId").value(hasItem(DEFAULT_PAYMENT_FILE_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPaymentAmount").value(hasItem(DEFAULT_TOTAL_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentFileDate").value(hasItem(DEFAULT_PAYMENT_FILE_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)));
    }

    @Test
    @Transactional
    void getPaymentFile() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get the paymentFile
        restPaymentFileMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentFile.getId().intValue()))
            .andExpect(jsonPath("$.paymentFileId").value(DEFAULT_PAYMENT_FILE_ID.doubleValue()))
            .andExpect(jsonPath("$.totalPaymentAmount").value(DEFAULT_TOTAL_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentFileDate").value(DEFAULT_PAYMENT_FILE_DATE.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE));
    }

    @Test
    @Transactional
    void getPaymentFilesByIdFiltering() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        Long id = paymentFile.getId();

        defaultPaymentFileShouldBeFound("id.equals=" + id);
        defaultPaymentFileShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentFileShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentFileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileId equals to DEFAULT_PAYMENT_FILE_ID
        defaultPaymentFileShouldBeFound("paymentFileId.equals=" + DEFAULT_PAYMENT_FILE_ID);

        // Get all the paymentFileList where paymentFileId equals to UPDATED_PAYMENT_FILE_ID
        defaultPaymentFileShouldNotBeFound("paymentFileId.equals=" + UPDATED_PAYMENT_FILE_ID);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileId not equals to DEFAULT_PAYMENT_FILE_ID
        defaultPaymentFileShouldNotBeFound("paymentFileId.notEquals=" + DEFAULT_PAYMENT_FILE_ID);

        // Get all the paymentFileList where paymentFileId not equals to UPDATED_PAYMENT_FILE_ID
        defaultPaymentFileShouldBeFound("paymentFileId.notEquals=" + UPDATED_PAYMENT_FILE_ID);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileId in DEFAULT_PAYMENT_FILE_ID or UPDATED_PAYMENT_FILE_ID
        defaultPaymentFileShouldBeFound("paymentFileId.in=" + DEFAULT_PAYMENT_FILE_ID + "," + UPDATED_PAYMENT_FILE_ID);

        // Get all the paymentFileList where paymentFileId equals to UPDATED_PAYMENT_FILE_ID
        defaultPaymentFileShouldNotBeFound("paymentFileId.in=" + UPDATED_PAYMENT_FILE_ID);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileId is not null
        defaultPaymentFileShouldBeFound("paymentFileId.specified=true");

        // Get all the paymentFileList where paymentFileId is null
        defaultPaymentFileShouldNotBeFound("paymentFileId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileId is greater than or equal to DEFAULT_PAYMENT_FILE_ID
        defaultPaymentFileShouldBeFound("paymentFileId.greaterThanOrEqual=" + DEFAULT_PAYMENT_FILE_ID);

        // Get all the paymentFileList where paymentFileId is greater than or equal to UPDATED_PAYMENT_FILE_ID
        defaultPaymentFileShouldNotBeFound("paymentFileId.greaterThanOrEqual=" + UPDATED_PAYMENT_FILE_ID);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileId is less than or equal to DEFAULT_PAYMENT_FILE_ID
        defaultPaymentFileShouldBeFound("paymentFileId.lessThanOrEqual=" + DEFAULT_PAYMENT_FILE_ID);

        // Get all the paymentFileList where paymentFileId is less than or equal to SMALLER_PAYMENT_FILE_ID
        defaultPaymentFileShouldNotBeFound("paymentFileId.lessThanOrEqual=" + SMALLER_PAYMENT_FILE_ID);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileIdIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileId is less than DEFAULT_PAYMENT_FILE_ID
        defaultPaymentFileShouldNotBeFound("paymentFileId.lessThan=" + DEFAULT_PAYMENT_FILE_ID);

        // Get all the paymentFileList where paymentFileId is less than UPDATED_PAYMENT_FILE_ID
        defaultPaymentFileShouldBeFound("paymentFileId.lessThan=" + UPDATED_PAYMENT_FILE_ID);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileId is greater than DEFAULT_PAYMENT_FILE_ID
        defaultPaymentFileShouldNotBeFound("paymentFileId.greaterThan=" + DEFAULT_PAYMENT_FILE_ID);

        // Get all the paymentFileList where paymentFileId is greater than SMALLER_PAYMENT_FILE_ID
        defaultPaymentFileShouldBeFound("paymentFileId.greaterThan=" + SMALLER_PAYMENT_FILE_ID);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByTotalPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where totalPaymentAmount equals to DEFAULT_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldBeFound("totalPaymentAmount.equals=" + DEFAULT_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentFileList where totalPaymentAmount equals to UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldNotBeFound("totalPaymentAmount.equals=" + UPDATED_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByTotalPaymentAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where totalPaymentAmount not equals to DEFAULT_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldNotBeFound("totalPaymentAmount.notEquals=" + DEFAULT_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentFileList where totalPaymentAmount not equals to UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldBeFound("totalPaymentAmount.notEquals=" + UPDATED_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByTotalPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where totalPaymentAmount in DEFAULT_TOTAL_PAYMENT_AMOUNT or UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldBeFound("totalPaymentAmount.in=" + DEFAULT_TOTAL_PAYMENT_AMOUNT + "," + UPDATED_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentFileList where totalPaymentAmount equals to UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldNotBeFound("totalPaymentAmount.in=" + UPDATED_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByTotalPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where totalPaymentAmount is not null
        defaultPaymentFileShouldBeFound("totalPaymentAmount.specified=true");

        // Get all the paymentFileList where totalPaymentAmount is null
        defaultPaymentFileShouldNotBeFound("totalPaymentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByTotalPaymentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where totalPaymentAmount is greater than or equal to DEFAULT_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldBeFound("totalPaymentAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentFileList where totalPaymentAmount is greater than or equal to UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldNotBeFound("totalPaymentAmount.greaterThanOrEqual=" + UPDATED_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByTotalPaymentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where totalPaymentAmount is less than or equal to DEFAULT_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldBeFound("totalPaymentAmount.lessThanOrEqual=" + DEFAULT_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentFileList where totalPaymentAmount is less than or equal to SMALLER_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldNotBeFound("totalPaymentAmount.lessThanOrEqual=" + SMALLER_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByTotalPaymentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where totalPaymentAmount is less than DEFAULT_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldNotBeFound("totalPaymentAmount.lessThan=" + DEFAULT_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentFileList where totalPaymentAmount is less than UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldBeFound("totalPaymentAmount.lessThan=" + UPDATED_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByTotalPaymentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where totalPaymentAmount is greater than DEFAULT_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldNotBeFound("totalPaymentAmount.greaterThan=" + DEFAULT_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentFileList where totalPaymentAmount is greater than SMALLER_TOTAL_PAYMENT_AMOUNT
        defaultPaymentFileShouldBeFound("totalPaymentAmount.greaterThan=" + SMALLER_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileDate equals to DEFAULT_PAYMENT_FILE_DATE
        defaultPaymentFileShouldBeFound("paymentFileDate.equals=" + DEFAULT_PAYMENT_FILE_DATE);

        // Get all the paymentFileList where paymentFileDate equals to UPDATED_PAYMENT_FILE_DATE
        defaultPaymentFileShouldNotBeFound("paymentFileDate.equals=" + UPDATED_PAYMENT_FILE_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileDate not equals to DEFAULT_PAYMENT_FILE_DATE
        defaultPaymentFileShouldNotBeFound("paymentFileDate.notEquals=" + DEFAULT_PAYMENT_FILE_DATE);

        // Get all the paymentFileList where paymentFileDate not equals to UPDATED_PAYMENT_FILE_DATE
        defaultPaymentFileShouldBeFound("paymentFileDate.notEquals=" + UPDATED_PAYMENT_FILE_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileDate in DEFAULT_PAYMENT_FILE_DATE or UPDATED_PAYMENT_FILE_DATE
        defaultPaymentFileShouldBeFound("paymentFileDate.in=" + DEFAULT_PAYMENT_FILE_DATE + "," + UPDATED_PAYMENT_FILE_DATE);

        // Get all the paymentFileList where paymentFileDate equals to UPDATED_PAYMENT_FILE_DATE
        defaultPaymentFileShouldNotBeFound("paymentFileDate.in=" + UPDATED_PAYMENT_FILE_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileDate is not null
        defaultPaymentFileShouldBeFound("paymentFileDate.specified=true");

        // Get all the paymentFileList where paymentFileDate is null
        defaultPaymentFileShouldNotBeFound("paymentFileDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileDate is greater than or equal to DEFAULT_PAYMENT_FILE_DATE
        defaultPaymentFileShouldBeFound("paymentFileDate.greaterThanOrEqual=" + DEFAULT_PAYMENT_FILE_DATE);

        // Get all the paymentFileList where paymentFileDate is greater than or equal to UPDATED_PAYMENT_FILE_DATE
        defaultPaymentFileShouldNotBeFound("paymentFileDate.greaterThanOrEqual=" + UPDATED_PAYMENT_FILE_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileDate is less than or equal to DEFAULT_PAYMENT_FILE_DATE
        defaultPaymentFileShouldBeFound("paymentFileDate.lessThanOrEqual=" + DEFAULT_PAYMENT_FILE_DATE);

        // Get all the paymentFileList where paymentFileDate is less than or equal to SMALLER_PAYMENT_FILE_DATE
        defaultPaymentFileShouldNotBeFound("paymentFileDate.lessThanOrEqual=" + SMALLER_PAYMENT_FILE_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileDateIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileDate is less than DEFAULT_PAYMENT_FILE_DATE
        defaultPaymentFileShouldNotBeFound("paymentFileDate.lessThan=" + DEFAULT_PAYMENT_FILE_DATE);

        // Get all the paymentFileList where paymentFileDate is less than UPDATED_PAYMENT_FILE_DATE
        defaultPaymentFileShouldBeFound("paymentFileDate.lessThan=" + UPDATED_PAYMENT_FILE_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileDate is greater than DEFAULT_PAYMENT_FILE_DATE
        defaultPaymentFileShouldNotBeFound("paymentFileDate.greaterThan=" + DEFAULT_PAYMENT_FILE_DATE);

        // Get all the paymentFileList where paymentFileDate is greater than SMALLER_PAYMENT_FILE_DATE
        defaultPaymentFileShouldBeFound("paymentFileDate.greaterThan=" + SMALLER_PAYMENT_FILE_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultPaymentFileShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentFileList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultPaymentFileShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentStatus not equals to DEFAULT_PAYMENT_STATUS
        defaultPaymentFileShouldNotBeFound("paymentStatus.notEquals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentFileList where paymentStatus not equals to UPDATED_PAYMENT_STATUS
        defaultPaymentFileShouldBeFound("paymentStatus.notEquals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultPaymentFileShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the paymentFileList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultPaymentFileShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentStatus is not null
        defaultPaymentFileShouldBeFound("paymentStatus.specified=true");

        // Get all the paymentFileList where paymentStatus is null
        defaultPaymentFileShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where bankName equals to DEFAULT_BANK_NAME
        defaultPaymentFileShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the paymentFileList where bankName equals to UPDATED_BANK_NAME
        defaultPaymentFileShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where bankName not equals to DEFAULT_BANK_NAME
        defaultPaymentFileShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the paymentFileList where bankName not equals to UPDATED_BANK_NAME
        defaultPaymentFileShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultPaymentFileShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the paymentFileList where bankName equals to UPDATED_BANK_NAME
        defaultPaymentFileShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where bankName is not null
        defaultPaymentFileShouldBeFound("bankName.specified=true");

        // Get all the paymentFileList where bankName is null
        defaultPaymentFileShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByBankNameContainsSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where bankName contains DEFAULT_BANK_NAME
        defaultPaymentFileShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the paymentFileList where bankName contains UPDATED_BANK_NAME
        defaultPaymentFileShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where bankName does not contain DEFAULT_BANK_NAME
        defaultPaymentFileShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the paymentFileList where bankName does not contain UPDATED_BANK_NAME
        defaultPaymentFileShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultPaymentFileShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the paymentFileList where ifscCode equals to UPDATED_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByIfscCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where ifscCode not equals to DEFAULT_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("ifscCode.notEquals=" + DEFAULT_IFSC_CODE);

        // Get all the paymentFileList where ifscCode not equals to UPDATED_IFSC_CODE
        defaultPaymentFileShouldBeFound("ifscCode.notEquals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultPaymentFileShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the paymentFileList where ifscCode equals to UPDATED_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where ifscCode is not null
        defaultPaymentFileShouldBeFound("ifscCode.specified=true");

        // Get all the paymentFileList where ifscCode is null
        defaultPaymentFileShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where ifscCode contains DEFAULT_IFSC_CODE
        defaultPaymentFileShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the paymentFileList where ifscCode contains UPDATED_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the paymentFileList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultPaymentFileShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentAdviceIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);
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
        paymentFile.addPaymentAdvice(paymentAdvice);
        paymentFileRepository.saveAndFlush(paymentFile);
        Long paymentAdviceId = paymentAdvice.getId();

        // Get all the paymentFileList where paymentAdvice equals to paymentAdviceId
        defaultPaymentFileShouldBeFound("paymentAdviceId.equals=" + paymentAdviceId);

        // Get all the paymentFileList where paymentAdvice equals to (paymentAdviceId + 1)
        defaultPaymentFileShouldNotBeFound("paymentAdviceId.equals=" + (paymentAdviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentFileShouldBeFound(String filter) throws Exception {
        restPaymentFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentFileId").value(hasItem(DEFAULT_PAYMENT_FILE_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPaymentAmount").value(hasItem(DEFAULT_TOTAL_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentFileDate").value(hasItem(DEFAULT_PAYMENT_FILE_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)));

        // Check, that the count call also returns 1
        restPaymentFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentFileShouldNotBeFound(String filter) throws Exception {
        restPaymentFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaymentFile() throws Exception {
        // Get the paymentFile
        restPaymentFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentFile() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();

        // Update the paymentFile
        PaymentFile updatedPaymentFile = paymentFileRepository.findById(paymentFile.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentFile are not directly saved in db
        em.detach(updatedPaymentFile);
        updatedPaymentFile
            .paymentFileId(UPDATED_PAYMENT_FILE_ID)
            .totalPaymentAmount(UPDATED_TOTAL_PAYMENT_AMOUNT)
            .paymentFileDate(UPDATED_PAYMENT_FILE_DATE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE);
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(updatedPaymentFile);

        restPaymentFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
        PaymentFile testPaymentFile = paymentFileList.get(paymentFileList.size() - 1);
        assertThat(testPaymentFile.getPaymentFileId()).isEqualTo(UPDATED_PAYMENT_FILE_ID);
        assertThat(testPaymentFile.getTotalPaymentAmount()).isEqualTo(UPDATED_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPaymentFile.getPaymentFileDate()).isEqualTo(UPDATED_PAYMENT_FILE_DATE);
        assertThat(testPaymentFile.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentFile.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPaymentFile.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void putNonExistingPaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();
        paymentFile.setId(count.incrementAndGet());

        // Create the PaymentFile
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();
        paymentFile.setId(count.incrementAndGet());

        // Create the PaymentFile
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();
        paymentFile.setId(count.incrementAndGet());

        // Create the PaymentFile
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentFileWithPatch() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();

        // Update the paymentFile using partial update
        PaymentFile partialUpdatedPaymentFile = new PaymentFile();
        partialUpdatedPaymentFile.setId(paymentFile.getId());

        partialUpdatedPaymentFile.bankName(UPDATED_BANK_NAME);

        restPaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentFile))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
        PaymentFile testPaymentFile = paymentFileList.get(paymentFileList.size() - 1);
        assertThat(testPaymentFile.getPaymentFileId()).isEqualTo(DEFAULT_PAYMENT_FILE_ID);
        assertThat(testPaymentFile.getTotalPaymentAmount()).isEqualTo(DEFAULT_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPaymentFile.getPaymentFileDate()).isEqualTo(DEFAULT_PAYMENT_FILE_DATE);
        assertThat(testPaymentFile.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPaymentFile.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPaymentFile.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
    }

    @Test
    @Transactional
    void fullUpdatePaymentFileWithPatch() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();

        // Update the paymentFile using partial update
        PaymentFile partialUpdatedPaymentFile = new PaymentFile();
        partialUpdatedPaymentFile.setId(paymentFile.getId());

        partialUpdatedPaymentFile
            .paymentFileId(UPDATED_PAYMENT_FILE_ID)
            .totalPaymentAmount(UPDATED_TOTAL_PAYMENT_AMOUNT)
            .paymentFileDate(UPDATED_PAYMENT_FILE_DATE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE);

        restPaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentFile))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
        PaymentFile testPaymentFile = paymentFileList.get(paymentFileList.size() - 1);
        assertThat(testPaymentFile.getPaymentFileId()).isEqualTo(UPDATED_PAYMENT_FILE_ID);
        assertThat(testPaymentFile.getTotalPaymentAmount()).isEqualTo(UPDATED_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPaymentFile.getPaymentFileDate()).isEqualTo(UPDATED_PAYMENT_FILE_DATE);
        assertThat(testPaymentFile.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentFile.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPaymentFile.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();
        paymentFile.setId(count.incrementAndGet());

        // Create the PaymentFile
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentFileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();
        paymentFile.setId(count.incrementAndGet());

        // Create the PaymentFile
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileRepository.findAll().size();
        paymentFile.setId(count.incrementAndGet());

        // Create the PaymentFile
        PaymentFileDTO paymentFileDTO = paymentFileMapper.toDto(paymentFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentFile in the database
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentFile() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        int databaseSizeBeforeDelete = paymentFileRepository.findAll().size();

        // Delete the paymentFile
        restPaymentFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentFile> paymentFileList = paymentFileRepository.findAll();
        assertThat(paymentFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
