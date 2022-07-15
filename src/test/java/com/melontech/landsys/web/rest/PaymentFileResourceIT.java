package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Bank;
import com.melontech.landsys.domain.BankBranch;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.domain.PaymentFileHeader;
import com.melontech.landsys.domain.Project;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import com.melontech.landsys.repository.PaymentFileRepository;
import com.melontech.landsys.service.PaymentFileService;
import com.melontech.landsys.service.criteria.PaymentFileCriteria;
import com.melontech.landsys.service.dto.PaymentFileDTO;
import com.melontech.landsys.service.mapper.PaymentFileMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentFileResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
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

    private static final PaymentStatus DEFAULT_PAYMENT_FILE_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_FILE_STATUS = PaymentStatus.APPROVED;

    private static final String DEFAULT_KHATEDAR_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_KHATEDAR_IFSC_CODE = "BBBBBBBBBB";

    private static final PaymentAdviceType DEFAULT_PAYMENT_MODE = PaymentAdviceType.ONLINE;
    private static final PaymentAdviceType UPDATED_PAYMENT_MODE = PaymentAdviceType.CHECQUE;

    private static final String ENTITY_API_URL = "/api/payment-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentFileRepository paymentFileRepository;

    @Mock
    private PaymentFileRepository paymentFileRepositoryMock;

    @Autowired
    private PaymentFileMapper paymentFileMapper;

    @Mock
    private PaymentFileService paymentFileServiceMock;

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
            .paymentFileStatus(DEFAULT_PAYMENT_FILE_STATUS)
            .khatedarIfscCode(DEFAULT_KHATEDAR_IFSC_CODE)
            .paymentMode(DEFAULT_PAYMENT_MODE);
        // Add required entity
        Khatedar khatedar;
        if (TestUtil.findAll(em, Khatedar.class).isEmpty()) {
            khatedar = KhatedarResourceIT.createEntity(em);
            em.persist(khatedar);
            em.flush();
        } else {
            khatedar = TestUtil.findAll(em, Khatedar.class).get(0);
        }
        paymentFile.setKhatedar(khatedar);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        paymentFile.setPaymentAdvice(paymentAdvice);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        paymentFile.setProjectLand(projectLand);
        // Add required entity
        Survey survey;
        if (TestUtil.findAll(em, Survey.class).isEmpty()) {
            survey = SurveyResourceIT.createEntity(em);
            em.persist(survey);
            em.flush();
        } else {
            survey = TestUtil.findAll(em, Survey.class).get(0);
        }
        paymentFile.setSurvey(survey);
        // Add required entity
        Bank bank;
        if (TestUtil.findAll(em, Bank.class).isEmpty()) {
            bank = BankResourceIT.createEntity(em);
            em.persist(bank);
            em.flush();
        } else {
            bank = TestUtil.findAll(em, Bank.class).get(0);
        }
        paymentFile.setBank(bank);
        // Add required entity
        BankBranch bankBranch;
        if (TestUtil.findAll(em, BankBranch.class).isEmpty()) {
            bankBranch = BankBranchResourceIT.createEntity(em);
            em.persist(bankBranch);
            em.flush();
        } else {
            bankBranch = TestUtil.findAll(em, BankBranch.class).get(0);
        }
        paymentFile.setBankBranch(bankBranch);
        // Add required entity
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        paymentFile.setLandCompensation(landCompensation);
        // Add required entity
        PaymentFileHeader paymentFileHeader;
        if (TestUtil.findAll(em, PaymentFileHeader.class).isEmpty()) {
            paymentFileHeader = PaymentFileHeaderResourceIT.createEntity(em);
            em.persist(paymentFileHeader);
            em.flush();
        } else {
            paymentFileHeader = TestUtil.findAll(em, PaymentFileHeader.class).get(0);
        }
        paymentFile.setPaymentFileHeader(paymentFileHeader);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        paymentFile.setProject(project);
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
            .paymentFileStatus(UPDATED_PAYMENT_FILE_STATUS)
            .khatedarIfscCode(UPDATED_KHATEDAR_IFSC_CODE)
            .paymentMode(UPDATED_PAYMENT_MODE);
        // Add required entity
        Khatedar khatedar;
        if (TestUtil.findAll(em, Khatedar.class).isEmpty()) {
            khatedar = KhatedarResourceIT.createUpdatedEntity(em);
            em.persist(khatedar);
            em.flush();
        } else {
            khatedar = TestUtil.findAll(em, Khatedar.class).get(0);
        }
        paymentFile.setKhatedar(khatedar);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createUpdatedEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        paymentFile.setPaymentAdvice(paymentAdvice);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createUpdatedEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        paymentFile.setProjectLand(projectLand);
        // Add required entity
        Survey survey;
        if (TestUtil.findAll(em, Survey.class).isEmpty()) {
            survey = SurveyResourceIT.createUpdatedEntity(em);
            em.persist(survey);
            em.flush();
        } else {
            survey = TestUtil.findAll(em, Survey.class).get(0);
        }
        paymentFile.setSurvey(survey);
        // Add required entity
        Bank bank;
        if (TestUtil.findAll(em, Bank.class).isEmpty()) {
            bank = BankResourceIT.createUpdatedEntity(em);
            em.persist(bank);
            em.flush();
        } else {
            bank = TestUtil.findAll(em, Bank.class).get(0);
        }
        paymentFile.setBank(bank);
        // Add required entity
        BankBranch bankBranch;
        if (TestUtil.findAll(em, BankBranch.class).isEmpty()) {
            bankBranch = BankBranchResourceIT.createUpdatedEntity(em);
            em.persist(bankBranch);
            em.flush();
        } else {
            bankBranch = TestUtil.findAll(em, BankBranch.class).get(0);
        }
        paymentFile.setBankBranch(bankBranch);
        // Add required entity
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createUpdatedEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        paymentFile.setLandCompensation(landCompensation);
        // Add required entity
        PaymentFileHeader paymentFileHeader;
        if (TestUtil.findAll(em, PaymentFileHeader.class).isEmpty()) {
            paymentFileHeader = PaymentFileHeaderResourceIT.createUpdatedEntity(em);
            em.persist(paymentFileHeader);
            em.flush();
        } else {
            paymentFileHeader = TestUtil.findAll(em, PaymentFileHeader.class).get(0);
        }
        paymentFile.setPaymentFileHeader(paymentFileHeader);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        paymentFile.setProject(project);
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
        assertThat(testPaymentFile.getPaymentFileStatus()).isEqualTo(DEFAULT_PAYMENT_FILE_STATUS);
        assertThat(testPaymentFile.getKhatedarIfscCode()).isEqualTo(DEFAULT_KHATEDAR_IFSC_CODE);
        assertThat(testPaymentFile.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
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
    void checkPaymentFileStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileRepository.findAll().size();
        // set the field null
        paymentFile.setPaymentFileStatus(null);

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
    void checkPaymentModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileRepository.findAll().size();
        // set the field null
        paymentFile.setPaymentMode(null);

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
            .andExpect(jsonPath("$.[*].paymentFileStatus").value(hasItem(DEFAULT_PAYMENT_FILE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].khatedarIfscCode").value(hasItem(DEFAULT_KHATEDAR_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPaymentFilesWithEagerRelationshipsIsEnabled() throws Exception {
        when(paymentFileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentFileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(paymentFileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPaymentFilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(paymentFileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentFileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(paymentFileServiceMock, times(1)).findAllWithEagerRelationships(any());
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
            .andExpect(jsonPath("$.paymentFileStatus").value(DEFAULT_PAYMENT_FILE_STATUS.toString()))
            .andExpect(jsonPath("$.khatedarIfscCode").value(DEFAULT_KHATEDAR_IFSC_CODE))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()));
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
    void getAllPaymentFilesByPaymentFileStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileStatus equals to DEFAULT_PAYMENT_FILE_STATUS
        defaultPaymentFileShouldBeFound("paymentFileStatus.equals=" + DEFAULT_PAYMENT_FILE_STATUS);

        // Get all the paymentFileList where paymentFileStatus equals to UPDATED_PAYMENT_FILE_STATUS
        defaultPaymentFileShouldNotBeFound("paymentFileStatus.equals=" + UPDATED_PAYMENT_FILE_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileStatus not equals to DEFAULT_PAYMENT_FILE_STATUS
        defaultPaymentFileShouldNotBeFound("paymentFileStatus.notEquals=" + DEFAULT_PAYMENT_FILE_STATUS);

        // Get all the paymentFileList where paymentFileStatus not equals to UPDATED_PAYMENT_FILE_STATUS
        defaultPaymentFileShouldBeFound("paymentFileStatus.notEquals=" + UPDATED_PAYMENT_FILE_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileStatusIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileStatus in DEFAULT_PAYMENT_FILE_STATUS or UPDATED_PAYMENT_FILE_STATUS
        defaultPaymentFileShouldBeFound("paymentFileStatus.in=" + DEFAULT_PAYMENT_FILE_STATUS + "," + UPDATED_PAYMENT_FILE_STATUS);

        // Get all the paymentFileList where paymentFileStatus equals to UPDATED_PAYMENT_FILE_STATUS
        defaultPaymentFileShouldNotBeFound("paymentFileStatus.in=" + UPDATED_PAYMENT_FILE_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentFileStatus is not null
        defaultPaymentFileShouldBeFound("paymentFileStatus.specified=true");

        // Get all the paymentFileList where paymentFileStatus is null
        defaultPaymentFileShouldNotBeFound("paymentFileStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByKhatedarIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where khatedarIfscCode equals to DEFAULT_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldBeFound("khatedarIfscCode.equals=" + DEFAULT_KHATEDAR_IFSC_CODE);

        // Get all the paymentFileList where khatedarIfscCode equals to UPDATED_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("khatedarIfscCode.equals=" + UPDATED_KHATEDAR_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByKhatedarIfscCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where khatedarIfscCode not equals to DEFAULT_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("khatedarIfscCode.notEquals=" + DEFAULT_KHATEDAR_IFSC_CODE);

        // Get all the paymentFileList where khatedarIfscCode not equals to UPDATED_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldBeFound("khatedarIfscCode.notEquals=" + UPDATED_KHATEDAR_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByKhatedarIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where khatedarIfscCode in DEFAULT_KHATEDAR_IFSC_CODE or UPDATED_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldBeFound("khatedarIfscCode.in=" + DEFAULT_KHATEDAR_IFSC_CODE + "," + UPDATED_KHATEDAR_IFSC_CODE);

        // Get all the paymentFileList where khatedarIfscCode equals to UPDATED_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("khatedarIfscCode.in=" + UPDATED_KHATEDAR_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByKhatedarIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where khatedarIfscCode is not null
        defaultPaymentFileShouldBeFound("khatedarIfscCode.specified=true");

        // Get all the paymentFileList where khatedarIfscCode is null
        defaultPaymentFileShouldNotBeFound("khatedarIfscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByKhatedarIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where khatedarIfscCode contains DEFAULT_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldBeFound("khatedarIfscCode.contains=" + DEFAULT_KHATEDAR_IFSC_CODE);

        // Get all the paymentFileList where khatedarIfscCode contains UPDATED_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("khatedarIfscCode.contains=" + UPDATED_KHATEDAR_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByKhatedarIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where khatedarIfscCode does not contain DEFAULT_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldNotBeFound("khatedarIfscCode.doesNotContain=" + DEFAULT_KHATEDAR_IFSC_CODE);

        // Get all the paymentFileList where khatedarIfscCode does not contain UPDATED_KHATEDAR_IFSC_CODE
        defaultPaymentFileShouldBeFound("khatedarIfscCode.doesNotContain=" + UPDATED_KHATEDAR_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentModeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentMode equals to DEFAULT_PAYMENT_MODE
        defaultPaymentFileShouldBeFound("paymentMode.equals=" + DEFAULT_PAYMENT_MODE);

        // Get all the paymentFileList where paymentMode equals to UPDATED_PAYMENT_MODE
        defaultPaymentFileShouldNotBeFound("paymentMode.equals=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentModeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentMode not equals to DEFAULT_PAYMENT_MODE
        defaultPaymentFileShouldNotBeFound("paymentMode.notEquals=" + DEFAULT_PAYMENT_MODE);

        // Get all the paymentFileList where paymentMode not equals to UPDATED_PAYMENT_MODE
        defaultPaymentFileShouldBeFound("paymentMode.notEquals=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentModeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentMode in DEFAULT_PAYMENT_MODE or UPDATED_PAYMENT_MODE
        defaultPaymentFileShouldBeFound("paymentMode.in=" + DEFAULT_PAYMENT_MODE + "," + UPDATED_PAYMENT_MODE);

        // Get all the paymentFileList where paymentMode equals to UPDATED_PAYMENT_MODE
        defaultPaymentFileShouldNotBeFound("paymentMode.in=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);

        // Get all the paymentFileList where paymentMode is not null
        defaultPaymentFileShouldBeFound("paymentMode.specified=true");

        // Get all the paymentFileList where paymentMode is null
        defaultPaymentFileShouldNotBeFound("paymentMode.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentFilesByKhatedarIsEqualToSomething() throws Exception {
        // Get already existing entity
        Khatedar khatedar = paymentFile.getKhatedar();
        paymentFileRepository.saveAndFlush(paymentFile);
        Long khatedarId = khatedar.getId();

        // Get all the paymentFileList where khatedar equals to khatedarId
        defaultPaymentFileShouldBeFound("khatedarId.equals=" + khatedarId);

        // Get all the paymentFileList where khatedar equals to (khatedarId + 1)
        defaultPaymentFileShouldNotBeFound("khatedarId.equals=" + (khatedarId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentAdviceIsEqualToSomething() throws Exception {
        // Get already existing entity
        PaymentAdvice paymentAdvice = paymentFile.getPaymentAdvice();
        paymentFileRepository.saveAndFlush(paymentFile);
        Long paymentAdviceId = paymentAdvice.getId();

        // Get all the paymentFileList where paymentAdvice equals to paymentAdviceId
        defaultPaymentFileShouldBeFound("paymentAdviceId.equals=" + paymentAdviceId);

        // Get all the paymentFileList where paymentAdvice equals to (paymentAdviceId + 1)
        defaultPaymentFileShouldNotBeFound("paymentAdviceId.equals=" + (paymentAdviceId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentFilesByProjectLandIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);
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
        paymentFile.setProjectLand(projectLand);
        paymentFileRepository.saveAndFlush(paymentFile);
        Long projectLandId = projectLand.getId();

        // Get all the paymentFileList where projectLand equals to projectLandId
        defaultPaymentFileShouldBeFound("projectLandId.equals=" + projectLandId);

        // Get all the paymentFileList where projectLand equals to (projectLandId + 1)
        defaultPaymentFileShouldNotBeFound("projectLandId.equals=" + (projectLandId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentFilesBySurveyIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);
        Survey survey;
        if (TestUtil.findAll(em, Survey.class).isEmpty()) {
            survey = SurveyResourceIT.createEntity(em);
            em.persist(survey);
            em.flush();
        } else {
            survey = TestUtil.findAll(em, Survey.class).get(0);
        }
        em.persist(survey);
        em.flush();
        paymentFile.setSurvey(survey);
        paymentFileRepository.saveAndFlush(paymentFile);
        Long surveyId = survey.getId();

        // Get all the paymentFileList where survey equals to surveyId
        defaultPaymentFileShouldBeFound("surveyId.equals=" + surveyId);

        // Get all the paymentFileList where survey equals to (surveyId + 1)
        defaultPaymentFileShouldNotBeFound("surveyId.equals=" + (surveyId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentFilesByBankIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);
        Bank bank;
        if (TestUtil.findAll(em, Bank.class).isEmpty()) {
            bank = BankResourceIT.createEntity(em);
            em.persist(bank);
            em.flush();
        } else {
            bank = TestUtil.findAll(em, Bank.class).get(0);
        }
        em.persist(bank);
        em.flush();
        paymentFile.setBank(bank);
        paymentFileRepository.saveAndFlush(paymentFile);
        Long bankId = bank.getId();

        // Get all the paymentFileList where bank equals to bankId
        defaultPaymentFileShouldBeFound("bankId.equals=" + bankId);

        // Get all the paymentFileList where bank equals to (bankId + 1)
        defaultPaymentFileShouldNotBeFound("bankId.equals=" + (bankId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentFilesByBankBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);
        BankBranch bankBranch;
        if (TestUtil.findAll(em, BankBranch.class).isEmpty()) {
            bankBranch = BankBranchResourceIT.createEntity(em);
            em.persist(bankBranch);
            em.flush();
        } else {
            bankBranch = TestUtil.findAll(em, BankBranch.class).get(0);
        }
        em.persist(bankBranch);
        em.flush();
        paymentFile.setBankBranch(bankBranch);
        paymentFileRepository.saveAndFlush(paymentFile);
        Long bankBranchId = bankBranch.getId();

        // Get all the paymentFileList where bankBranch equals to bankBranchId
        defaultPaymentFileShouldBeFound("bankBranchId.equals=" + bankBranchId);

        // Get all the paymentFileList where bankBranch equals to (bankBranchId + 1)
        defaultPaymentFileShouldNotBeFound("bankBranchId.equals=" + (bankBranchId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentFilesByLandCompensationIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);
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
        paymentFile.setLandCompensation(landCompensation);
        paymentFileRepository.saveAndFlush(paymentFile);
        Long landCompensationId = landCompensation.getId();

        // Get all the paymentFileList where landCompensation equals to landCompensationId
        defaultPaymentFileShouldBeFound("landCompensationId.equals=" + landCompensationId);

        // Get all the paymentFileList where landCompensation equals to (landCompensationId + 1)
        defaultPaymentFileShouldNotBeFound("landCompensationId.equals=" + (landCompensationId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentFilesByPaymentFileHeaderIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);
        PaymentFileHeader paymentFileHeader;
        if (TestUtil.findAll(em, PaymentFileHeader.class).isEmpty()) {
            paymentFileHeader = PaymentFileHeaderResourceIT.createEntity(em);
            em.persist(paymentFileHeader);
            em.flush();
        } else {
            paymentFileHeader = TestUtil.findAll(em, PaymentFileHeader.class).get(0);
        }
        em.persist(paymentFileHeader);
        em.flush();
        paymentFile.setPaymentFileHeader(paymentFileHeader);
        paymentFileRepository.saveAndFlush(paymentFile);
        Long paymentFileHeaderId = paymentFileHeader.getId();

        // Get all the paymentFileList where paymentFileHeader equals to paymentFileHeaderId
        defaultPaymentFileShouldBeFound("paymentFileHeaderId.equals=" + paymentFileHeaderId);

        // Get all the paymentFileList where paymentFileHeader equals to (paymentFileHeaderId + 1)
        defaultPaymentFileShouldNotBeFound("paymentFileHeaderId.equals=" + (paymentFileHeaderId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentFilesByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentFileRepository.saveAndFlush(paymentFile);
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        em.persist(project);
        em.flush();
        paymentFile.setProject(project);
        paymentFileRepository.saveAndFlush(paymentFile);
        Long projectId = project.getId();

        // Get all the paymentFileList where project equals to projectId
        defaultPaymentFileShouldBeFound("projectId.equals=" + projectId);

        // Get all the paymentFileList where project equals to (projectId + 1)
        defaultPaymentFileShouldNotBeFound("projectId.equals=" + (projectId + 1));
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
            .andExpect(jsonPath("$.[*].paymentFileStatus").value(hasItem(DEFAULT_PAYMENT_FILE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].khatedarIfscCode").value(hasItem(DEFAULT_KHATEDAR_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())));

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
            .paymentFileStatus(UPDATED_PAYMENT_FILE_STATUS)
            .khatedarIfscCode(UPDATED_KHATEDAR_IFSC_CODE)
            .paymentMode(UPDATED_PAYMENT_MODE);
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
        assertThat(testPaymentFile.getPaymentFileStatus()).isEqualTo(UPDATED_PAYMENT_FILE_STATUS);
        assertThat(testPaymentFile.getKhatedarIfscCode()).isEqualTo(UPDATED_KHATEDAR_IFSC_CODE);
        assertThat(testPaymentFile.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
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

        partialUpdatedPaymentFile.khatedarIfscCode(UPDATED_KHATEDAR_IFSC_CODE);

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
        assertThat(testPaymentFile.getPaymentFileStatus()).isEqualTo(DEFAULT_PAYMENT_FILE_STATUS);
        assertThat(testPaymentFile.getKhatedarIfscCode()).isEqualTo(UPDATED_KHATEDAR_IFSC_CODE);
        assertThat(testPaymentFile.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
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
            .paymentFileStatus(UPDATED_PAYMENT_FILE_STATUS)
            .khatedarIfscCode(UPDATED_KHATEDAR_IFSC_CODE)
            .paymentMode(UPDATED_PAYMENT_MODE);

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
        assertThat(testPaymentFile.getPaymentFileStatus()).isEqualTo(UPDATED_PAYMENT_FILE_STATUS);
        assertThat(testPaymentFile.getKhatedarIfscCode()).isEqualTo(UPDATED_KHATEDAR_IFSC_CODE);
        assertThat(testPaymentFile.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
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
