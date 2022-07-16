package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.CreatePaymentFile;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import com.melontech.landsys.repository.CreatePaymentFileRepository;
import com.melontech.landsys.service.dto.CreatePaymentFileDTO;
import com.melontech.landsys.service.mapper.CreatePaymentFileMapper;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link CreatePaymentFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreatePaymentFileResourceIT {

    private static final String DEFAULT_ACCOUNT_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_HOLDER_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER_BANK_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_PAYMENT_AMOUNT = 2D;

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

    private static final UUID DEFAULT_REFERENCE_NUMBER = UUID.randomUUID();
    private static final UUID UPDATED_REFERENCE_NUMBER = UUID.randomUUID();

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.APPROVED;

    private static final HissaType DEFAULT_HISSA_TYPE = HissaType.SINGLE_OWNER;
    private static final HissaType UPDATED_HISSA_TYPE = HissaType.JOINT_OWNER;

    private static final String ENTITY_API_URL = "/api/create-payment-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreatePaymentFileRepository createPaymentFileRepository;

    @Autowired
    private CreatePaymentFileMapper createPaymentFileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreatePaymentFileMockMvc;

    private CreatePaymentFile createPaymentFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreatePaymentFile createEntity(EntityManager em) {
        CreatePaymentFile createPaymentFile = new CreatePaymentFile()
            .accountHolderName(DEFAULT_ACCOUNT_HOLDER_NAME)
            .accountHolderBankName(DEFAULT_ACCOUNT_HOLDER_BANK_NAME)
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
        return createPaymentFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreatePaymentFile createUpdatedEntity(EntityManager em) {
        CreatePaymentFile createPaymentFile = new CreatePaymentFile()
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .accountHolderBankName(UPDATED_ACCOUNT_HOLDER_BANK_NAME)
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
        return createPaymentFile;
    }

    @BeforeEach
    public void initTest() {
        createPaymentFile = createEntity(em);
    }

    @Test
    @Transactional
    void createCreatePaymentFile() throws Exception {
        int databaseSizeBeforeCreate = createPaymentFileRepository.findAll().size();
        // Create the CreatePaymentFile
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);
        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeCreate + 1);
        CreatePaymentFile testCreatePaymentFile = createPaymentFileList.get(createPaymentFileList.size() - 1);
        assertThat(testCreatePaymentFile.getAccountHolderName()).isEqualTo(DEFAULT_ACCOUNT_HOLDER_NAME);
        assertThat(testCreatePaymentFile.getAccountHolderBankName()).isEqualTo(DEFAULT_ACCOUNT_HOLDER_BANK_NAME);
        assertThat(testCreatePaymentFile.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testCreatePaymentFile.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCreatePaymentFile.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCreatePaymentFile.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testCreatePaymentFile.getCheckNumber()).isEqualTo(DEFAULT_CHECK_NUMBER);
        assertThat(testCreatePaymentFile.getMicrCode()).isEqualTo(DEFAULT_MICR_CODE);
        assertThat(testCreatePaymentFile.getPaymentAdviceType()).isEqualTo(DEFAULT_PAYMENT_ADVICE_TYPE);
        assertThat(testCreatePaymentFile.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testCreatePaymentFile.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testCreatePaymentFile.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
    }

    @Test
    @Transactional
    void createCreatePaymentFileWithExistingId() throws Exception {
        // Create the CreatePaymentFile with an existing ID
        createPaymentFile.setId(1L);
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        int databaseSizeBeforeCreate = createPaymentFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountHolderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = createPaymentFileRepository.findAll().size();
        // set the field null
        createPaymentFile.setAccountHolderName(null);

        // Create the CreatePaymentFile, which fails.
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountHolderBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = createPaymentFileRepository.findAll().size();
        // set the field null
        createPaymentFile.setAccountHolderBankName(null);

        // Create the CreatePaymentFile, which fails.
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = createPaymentFileRepository.findAll().size();
        // set the field null
        createPaymentFile.setPaymentAmount(null);

        // Create the CreatePaymentFile, which fails.
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = createPaymentFileRepository.findAll().size();
        // set the field null
        createPaymentFile.setBankName(null);

        // Create the CreatePaymentFile, which fails.
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = createPaymentFileRepository.findAll().size();
        // set the field null
        createPaymentFile.setAccountNumber(null);

        // Create the CreatePaymentFile, which fails.
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIfscCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = createPaymentFileRepository.findAll().size();
        // set the field null
        createPaymentFile.setIfscCode(null);

        // Create the CreatePaymentFile, which fails.
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = createPaymentFileRepository.findAll().size();
        // set the field null
        createPaymentFile.setPaymentStatus(null);

        // Create the CreatePaymentFile, which fails.
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHissaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = createPaymentFileRepository.findAll().size();
        // set the field null
        createPaymentFile.setHissaType(null);

        // Create the CreatePaymentFile, which fails.
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCreatePaymentFiles() throws Exception {
        // Initialize the database
        createPaymentFileRepository.saveAndFlush(createPaymentFile);

        // Get all the createPaymentFileList
        restCreatePaymentFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(createPaymentFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountHolderName").value(hasItem(DEFAULT_ACCOUNT_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].accountHolderBankName").value(hasItem(DEFAULT_ACCOUNT_HOLDER_BANK_NAME)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].paymentAdviceType").value(hasItem(DEFAULT_PAYMENT_ADVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())));
    }

    @Test
    @Transactional
    void getCreatePaymentFile() throws Exception {
        // Initialize the database
        createPaymentFileRepository.saveAndFlush(createPaymentFile);

        // Get the createPaymentFile
        restCreatePaymentFileMockMvc
            .perform(get(ENTITY_API_URL_ID, createPaymentFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(createPaymentFile.getId().intValue()))
            .andExpect(jsonPath("$.accountHolderName").value(DEFAULT_ACCOUNT_HOLDER_NAME))
            .andExpect(jsonPath("$.accountHolderBankName").value(DEFAULT_ACCOUNT_HOLDER_BANK_NAME))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.checkNumber").value(DEFAULT_CHECK_NUMBER))
            .andExpect(jsonPath("$.micrCode").value(DEFAULT_MICR_CODE))
            .andExpect(jsonPath("$.paymentAdviceType").value(DEFAULT_PAYMENT_ADVICE_TYPE.toString()))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.hissaType").value(DEFAULT_HISSA_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCreatePaymentFile() throws Exception {
        // Get the createPaymentFile
        restCreatePaymentFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCreatePaymentFile() throws Exception {
        // Initialize the database
        createPaymentFileRepository.saveAndFlush(createPaymentFile);

        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();

        // Update the createPaymentFile
        CreatePaymentFile updatedCreatePaymentFile = createPaymentFileRepository.findById(createPaymentFile.getId()).get();
        // Disconnect from session so that the updates on updatedCreatePaymentFile are not directly saved in db
        em.detach(updatedCreatePaymentFile);
        updatedCreatePaymentFile
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .accountHolderBankName(UPDATED_ACCOUNT_HOLDER_BANK_NAME)
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
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(updatedCreatePaymentFile);

        restCreatePaymentFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, createPaymentFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
        CreatePaymentFile testCreatePaymentFile = createPaymentFileList.get(createPaymentFileList.size() - 1);
        assertThat(testCreatePaymentFile.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testCreatePaymentFile.getAccountHolderBankName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_BANK_NAME);
        assertThat(testCreatePaymentFile.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testCreatePaymentFile.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCreatePaymentFile.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCreatePaymentFile.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testCreatePaymentFile.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testCreatePaymentFile.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testCreatePaymentFile.getPaymentAdviceType()).isEqualTo(UPDATED_PAYMENT_ADVICE_TYPE);
        assertThat(testCreatePaymentFile.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testCreatePaymentFile.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testCreatePaymentFile.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCreatePaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();
        createPaymentFile.setId(count.incrementAndGet());

        // Create the CreatePaymentFile
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreatePaymentFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, createPaymentFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreatePaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();
        createPaymentFile.setId(count.incrementAndGet());

        // Create the CreatePaymentFile
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreatePaymentFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreatePaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();
        createPaymentFile.setId(count.incrementAndGet());

        // Create the CreatePaymentFile
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreatePaymentFileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreatePaymentFileWithPatch() throws Exception {
        // Initialize the database
        createPaymentFileRepository.saveAndFlush(createPaymentFile);

        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();

        // Update the createPaymentFile using partial update
        CreatePaymentFile partialUpdatedCreatePaymentFile = new CreatePaymentFile();
        partialUpdatedCreatePaymentFile.setId(createPaymentFile.getId());

        partialUpdatedCreatePaymentFile
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .accountHolderBankName(UPDATED_ACCOUNT_HOLDER_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .hissaType(UPDATED_HISSA_TYPE);

        restCreatePaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreatePaymentFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreatePaymentFile))
            )
            .andExpect(status().isOk());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
        CreatePaymentFile testCreatePaymentFile = createPaymentFileList.get(createPaymentFileList.size() - 1);
        assertThat(testCreatePaymentFile.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testCreatePaymentFile.getAccountHolderBankName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_BANK_NAME);
        assertThat(testCreatePaymentFile.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testCreatePaymentFile.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCreatePaymentFile.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCreatePaymentFile.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testCreatePaymentFile.getCheckNumber()).isEqualTo(DEFAULT_CHECK_NUMBER);
        assertThat(testCreatePaymentFile.getMicrCode()).isEqualTo(DEFAULT_MICR_CODE);
        assertThat(testCreatePaymentFile.getPaymentAdviceType()).isEqualTo(DEFAULT_PAYMENT_ADVICE_TYPE);
        assertThat(testCreatePaymentFile.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testCreatePaymentFile.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testCreatePaymentFile.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCreatePaymentFileWithPatch() throws Exception {
        // Initialize the database
        createPaymentFileRepository.saveAndFlush(createPaymentFile);

        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();

        // Update the createPaymentFile using partial update
        CreatePaymentFile partialUpdatedCreatePaymentFile = new CreatePaymentFile();
        partialUpdatedCreatePaymentFile.setId(createPaymentFile.getId());

        partialUpdatedCreatePaymentFile
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .accountHolderBankName(UPDATED_ACCOUNT_HOLDER_BANK_NAME)
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

        restCreatePaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreatePaymentFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreatePaymentFile))
            )
            .andExpect(status().isOk());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
        CreatePaymentFile testCreatePaymentFile = createPaymentFileList.get(createPaymentFileList.size() - 1);
        assertThat(testCreatePaymentFile.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testCreatePaymentFile.getAccountHolderBankName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_BANK_NAME);
        assertThat(testCreatePaymentFile.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testCreatePaymentFile.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCreatePaymentFile.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCreatePaymentFile.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testCreatePaymentFile.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testCreatePaymentFile.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testCreatePaymentFile.getPaymentAdviceType()).isEqualTo(UPDATED_PAYMENT_ADVICE_TYPE);
        assertThat(testCreatePaymentFile.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testCreatePaymentFile.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testCreatePaymentFile.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCreatePaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();
        createPaymentFile.setId(count.incrementAndGet());

        // Create the CreatePaymentFile
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreatePaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, createPaymentFileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreatePaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();
        createPaymentFile.setId(count.incrementAndGet());

        // Create the CreatePaymentFile
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreatePaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreatePaymentFile() throws Exception {
        int databaseSizeBeforeUpdate = createPaymentFileRepository.findAll().size();
        createPaymentFile.setId(count.incrementAndGet());

        // Create the CreatePaymentFile
        CreatePaymentFileDTO createPaymentFileDTO = createPaymentFileMapper.toDto(createPaymentFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreatePaymentFileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(createPaymentFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreatePaymentFile in the database
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreatePaymentFile() throws Exception {
        // Initialize the database
        createPaymentFileRepository.saveAndFlush(createPaymentFile);

        int databaseSizeBeforeDelete = createPaymentFileRepository.findAll().size();

        // Delete the createPaymentFile
        restCreatePaymentFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, createPaymentFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreatePaymentFile> createPaymentFileList = createPaymentFileRepository.findAll();
        assertThat(createPaymentFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
