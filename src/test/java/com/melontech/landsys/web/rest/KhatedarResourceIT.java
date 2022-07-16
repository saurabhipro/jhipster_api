package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.PaymentAdviceDetails;
import com.melontech.landsys.domain.PaymentFile;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.KhatedarStatus;
import com.melontech.landsys.repository.KhatedarRepository;
import com.melontech.landsys.service.criteria.KhatedarCriteria;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.mapper.KhatedarMapper;
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
 * Integration tests for the {@link KhatedarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KhatedarResourceIT {

    private static final String DEFAULT_CASE_FILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CASE_FILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final HissaType DEFAULT_HISSA_TYPE = HissaType.SINGLE_OWNER;
    private static final HissaType UPDATED_HISSA_TYPE = HissaType.JOINT_OWNER;

    private static final KhatedarStatus DEFAULT_KHATEDAR_STATUS = KhatedarStatus.NEW;
    private static final KhatedarStatus UPDATED_KHATEDAR_STATUS = KhatedarStatus.SURVEY_CREATED;

    private static final String ENTITY_API_URL = "/api/khatedars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KhatedarRepository khatedarRepository;

    @Autowired
    private KhatedarMapper khatedarMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKhatedarMockMvc;

    private Khatedar khatedar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Khatedar createEntity(EntityManager em) {
        Khatedar khatedar = new Khatedar()
            .caseFileNo(DEFAULT_CASE_FILE_NO)
            .remarks(DEFAULT_REMARKS)
            .hissaType(DEFAULT_HISSA_TYPE)
            .khatedarStatus(DEFAULT_KHATEDAR_STATUS);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        khatedar.setProjectLand(projectLand);
        // Add required entity
        Citizen citizen;
        if (TestUtil.findAll(em, Citizen.class).isEmpty()) {
            citizen = CitizenResourceIT.createEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizen.class).get(0);
        }
        khatedar.setCitizen(citizen);
        return khatedar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Khatedar createUpdatedEntity(EntityManager em) {
        Khatedar khatedar = new Khatedar()
            .caseFileNo(UPDATED_CASE_FILE_NO)
            .remarks(UPDATED_REMARKS)
            .hissaType(UPDATED_HISSA_TYPE)
            .khatedarStatus(UPDATED_KHATEDAR_STATUS);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createUpdatedEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        khatedar.setProjectLand(projectLand);
        // Add required entity
        Citizen citizen;
        if (TestUtil.findAll(em, Citizen.class).isEmpty()) {
            citizen = CitizenResourceIT.createUpdatedEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizen.class).get(0);
        }
        khatedar.setCitizen(citizen);
        return khatedar;
    }

    @BeforeEach
    public void initTest() {
        khatedar = createEntity(em);
    }

    @Test
    @Transactional
    void createKhatedar() throws Exception {
        int databaseSizeBeforeCreate = khatedarRepository.findAll().size();
        // Create the Khatedar
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);
        restKhatedarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khatedarDTO)))
            .andExpect(status().isCreated());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeCreate + 1);
        Khatedar testKhatedar = khatedarList.get(khatedarList.size() - 1);
        assertThat(testKhatedar.getCaseFileNo()).isEqualTo(DEFAULT_CASE_FILE_NO);
        assertThat(testKhatedar.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testKhatedar.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
        assertThat(testKhatedar.getKhatedarStatus()).isEqualTo(DEFAULT_KHATEDAR_STATUS);
    }

    @Test
    @Transactional
    void createKhatedarWithExistingId() throws Exception {
        // Create the Khatedar with an existing ID
        khatedar.setId(1L);
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        int databaseSizeBeforeCreate = khatedarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKhatedarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khatedarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCaseFileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = khatedarRepository.findAll().size();
        // set the field null
        khatedar.setCaseFileNo(null);

        // Create the Khatedar, which fails.
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        restKhatedarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khatedarDTO)))
            .andExpect(status().isBadRequest());

        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRemarksIsRequired() throws Exception {
        int databaseSizeBeforeTest = khatedarRepository.findAll().size();
        // set the field null
        khatedar.setRemarks(null);

        // Create the Khatedar, which fails.
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        restKhatedarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khatedarDTO)))
            .andExpect(status().isBadRequest());

        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHissaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = khatedarRepository.findAll().size();
        // set the field null
        khatedar.setHissaType(null);

        // Create the Khatedar, which fails.
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        restKhatedarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khatedarDTO)))
            .andExpect(status().isBadRequest());

        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKhatedarStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = khatedarRepository.findAll().size();
        // set the field null
        khatedar.setKhatedarStatus(null);

        // Create the Khatedar, which fails.
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        restKhatedarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khatedarDTO)))
            .andExpect(status().isBadRequest());

        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKhatedars() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList
        restKhatedarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(khatedar.getId().intValue())))
            .andExpect(jsonPath("$.[*].caseFileNo").value(hasItem(DEFAULT_CASE_FILE_NO)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].khatedarStatus").value(hasItem(DEFAULT_KHATEDAR_STATUS.toString())));
    }

    @Test
    @Transactional
    void getKhatedar() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get the khatedar
        restKhatedarMockMvc
            .perform(get(ENTITY_API_URL_ID, khatedar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(khatedar.getId().intValue()))
            .andExpect(jsonPath("$.caseFileNo").value(DEFAULT_CASE_FILE_NO))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.hissaType").value(DEFAULT_HISSA_TYPE.toString()))
            .andExpect(jsonPath("$.khatedarStatus").value(DEFAULT_KHATEDAR_STATUS.toString()));
    }

    @Test
    @Transactional
    void getKhatedarsByIdFiltering() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        Long id = khatedar.getId();

        defaultKhatedarShouldBeFound("id.equals=" + id);
        defaultKhatedarShouldNotBeFound("id.notEquals=" + id);

        defaultKhatedarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKhatedarShouldNotBeFound("id.greaterThan=" + id);

        defaultKhatedarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKhatedarShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKhatedarsByCaseFileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where caseFileNo equals to DEFAULT_CASE_FILE_NO
        defaultKhatedarShouldBeFound("caseFileNo.equals=" + DEFAULT_CASE_FILE_NO);

        // Get all the khatedarList where caseFileNo equals to UPDATED_CASE_FILE_NO
        defaultKhatedarShouldNotBeFound("caseFileNo.equals=" + UPDATED_CASE_FILE_NO);
    }

    @Test
    @Transactional
    void getAllKhatedarsByCaseFileNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where caseFileNo not equals to DEFAULT_CASE_FILE_NO
        defaultKhatedarShouldNotBeFound("caseFileNo.notEquals=" + DEFAULT_CASE_FILE_NO);

        // Get all the khatedarList where caseFileNo not equals to UPDATED_CASE_FILE_NO
        defaultKhatedarShouldBeFound("caseFileNo.notEquals=" + UPDATED_CASE_FILE_NO);
    }

    @Test
    @Transactional
    void getAllKhatedarsByCaseFileNoIsInShouldWork() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where caseFileNo in DEFAULT_CASE_FILE_NO or UPDATED_CASE_FILE_NO
        defaultKhatedarShouldBeFound("caseFileNo.in=" + DEFAULT_CASE_FILE_NO + "," + UPDATED_CASE_FILE_NO);

        // Get all the khatedarList where caseFileNo equals to UPDATED_CASE_FILE_NO
        defaultKhatedarShouldNotBeFound("caseFileNo.in=" + UPDATED_CASE_FILE_NO);
    }

    @Test
    @Transactional
    void getAllKhatedarsByCaseFileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where caseFileNo is not null
        defaultKhatedarShouldBeFound("caseFileNo.specified=true");

        // Get all the khatedarList where caseFileNo is null
        defaultKhatedarShouldNotBeFound("caseFileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKhatedarsByCaseFileNoContainsSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where caseFileNo contains DEFAULT_CASE_FILE_NO
        defaultKhatedarShouldBeFound("caseFileNo.contains=" + DEFAULT_CASE_FILE_NO);

        // Get all the khatedarList where caseFileNo contains UPDATED_CASE_FILE_NO
        defaultKhatedarShouldNotBeFound("caseFileNo.contains=" + UPDATED_CASE_FILE_NO);
    }

    @Test
    @Transactional
    void getAllKhatedarsByCaseFileNoNotContainsSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where caseFileNo does not contain DEFAULT_CASE_FILE_NO
        defaultKhatedarShouldNotBeFound("caseFileNo.doesNotContain=" + DEFAULT_CASE_FILE_NO);

        // Get all the khatedarList where caseFileNo does not contain UPDATED_CASE_FILE_NO
        defaultKhatedarShouldBeFound("caseFileNo.doesNotContain=" + UPDATED_CASE_FILE_NO);
    }

    @Test
    @Transactional
    void getAllKhatedarsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where remarks equals to DEFAULT_REMARKS
        defaultKhatedarShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the khatedarList where remarks equals to UPDATED_REMARKS
        defaultKhatedarShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where remarks not equals to DEFAULT_REMARKS
        defaultKhatedarShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the khatedarList where remarks not equals to UPDATED_REMARKS
        defaultKhatedarShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultKhatedarShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the khatedarList where remarks equals to UPDATED_REMARKS
        defaultKhatedarShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where remarks is not null
        defaultKhatedarShouldBeFound("remarks.specified=true");

        // Get all the khatedarList where remarks is null
        defaultKhatedarShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllKhatedarsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where remarks contains DEFAULT_REMARKS
        defaultKhatedarShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the khatedarList where remarks contains UPDATED_REMARKS
        defaultKhatedarShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where remarks does not contain DEFAULT_REMARKS
        defaultKhatedarShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the khatedarList where remarks does not contain UPDATED_REMARKS
        defaultKhatedarShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByHissaTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where hissaType equals to DEFAULT_HISSA_TYPE
        defaultKhatedarShouldBeFound("hissaType.equals=" + DEFAULT_HISSA_TYPE);

        // Get all the khatedarList where hissaType equals to UPDATED_HISSA_TYPE
        defaultKhatedarShouldNotBeFound("hissaType.equals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllKhatedarsByHissaTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where hissaType not equals to DEFAULT_HISSA_TYPE
        defaultKhatedarShouldNotBeFound("hissaType.notEquals=" + DEFAULT_HISSA_TYPE);

        // Get all the khatedarList where hissaType not equals to UPDATED_HISSA_TYPE
        defaultKhatedarShouldBeFound("hissaType.notEquals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllKhatedarsByHissaTypeIsInShouldWork() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where hissaType in DEFAULT_HISSA_TYPE or UPDATED_HISSA_TYPE
        defaultKhatedarShouldBeFound("hissaType.in=" + DEFAULT_HISSA_TYPE + "," + UPDATED_HISSA_TYPE);

        // Get all the khatedarList where hissaType equals to UPDATED_HISSA_TYPE
        defaultKhatedarShouldNotBeFound("hissaType.in=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllKhatedarsByHissaTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where hissaType is not null
        defaultKhatedarShouldBeFound("hissaType.specified=true");

        // Get all the khatedarList where hissaType is null
        defaultKhatedarShouldNotBeFound("hissaType.specified=false");
    }

    @Test
    @Transactional
    void getAllKhatedarsByKhatedarStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where khatedarStatus equals to DEFAULT_KHATEDAR_STATUS
        defaultKhatedarShouldBeFound("khatedarStatus.equals=" + DEFAULT_KHATEDAR_STATUS);

        // Get all the khatedarList where khatedarStatus equals to UPDATED_KHATEDAR_STATUS
        defaultKhatedarShouldNotBeFound("khatedarStatus.equals=" + UPDATED_KHATEDAR_STATUS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByKhatedarStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where khatedarStatus not equals to DEFAULT_KHATEDAR_STATUS
        defaultKhatedarShouldNotBeFound("khatedarStatus.notEquals=" + DEFAULT_KHATEDAR_STATUS);

        // Get all the khatedarList where khatedarStatus not equals to UPDATED_KHATEDAR_STATUS
        defaultKhatedarShouldBeFound("khatedarStatus.notEquals=" + UPDATED_KHATEDAR_STATUS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByKhatedarStatusIsInShouldWork() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where khatedarStatus in DEFAULT_KHATEDAR_STATUS or UPDATED_KHATEDAR_STATUS
        defaultKhatedarShouldBeFound("khatedarStatus.in=" + DEFAULT_KHATEDAR_STATUS + "," + UPDATED_KHATEDAR_STATUS);

        // Get all the khatedarList where khatedarStatus equals to UPDATED_KHATEDAR_STATUS
        defaultKhatedarShouldNotBeFound("khatedarStatus.in=" + UPDATED_KHATEDAR_STATUS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByKhatedarStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where khatedarStatus is not null
        defaultKhatedarShouldBeFound("khatedarStatus.specified=true");

        // Get all the khatedarList where khatedarStatus is null
        defaultKhatedarShouldNotBeFound("khatedarStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllKhatedarsByProjectLandIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);
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
        khatedar.setProjectLand(projectLand);
        khatedarRepository.saveAndFlush(khatedar);
        Long projectLandId = projectLand.getId();

        // Get all the khatedarList where projectLand equals to projectLandId
        defaultKhatedarShouldBeFound("projectLandId.equals=" + projectLandId);

        // Get all the khatedarList where projectLand equals to (projectLandId + 1)
        defaultKhatedarShouldNotBeFound("projectLandId.equals=" + (projectLandId + 1));
    }

    @Test
    @Transactional
    void getAllKhatedarsByCitizenIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);
        Citizen citizen;
        if (TestUtil.findAll(em, Citizen.class).isEmpty()) {
            citizen = CitizenResourceIT.createEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizen.class).get(0);
        }
        em.persist(citizen);
        em.flush();
        khatedar.setCitizen(citizen);
        khatedarRepository.saveAndFlush(khatedar);
        Long citizenId = citizen.getId();

        // Get all the khatedarList where citizen equals to citizenId
        defaultKhatedarShouldBeFound("citizenId.equals=" + citizenId);

        // Get all the khatedarList where citizen equals to (citizenId + 1)
        defaultKhatedarShouldNotBeFound("citizenId.equals=" + (citizenId + 1));
    }

    @Test
    @Transactional
    void getAllKhatedarsByPaymentAdviceIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);
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
        khatedar.setPaymentAdvice(paymentAdvice);
        paymentAdvice.setKhatedar(khatedar);
        khatedarRepository.saveAndFlush(khatedar);
        Long paymentAdviceId = paymentAdvice.getId();

        // Get all the khatedarList where paymentAdvice equals to paymentAdviceId
        defaultKhatedarShouldBeFound("paymentAdviceId.equals=" + paymentAdviceId);

        // Get all the khatedarList where paymentAdvice equals to (paymentAdviceId + 1)
        defaultKhatedarShouldNotBeFound("paymentAdviceId.equals=" + (paymentAdviceId + 1));
    }

    @Test
    @Transactional
    void getAllKhatedarsByPaymentFileIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);
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
        khatedar.setPaymentFile(paymentFile);
        paymentFile.setKhatedar(khatedar);
        khatedarRepository.saveAndFlush(khatedar);
        Long paymentFileId = paymentFile.getId();

        // Get all the khatedarList where paymentFile equals to paymentFileId
        defaultKhatedarShouldBeFound("paymentFileId.equals=" + paymentFileId);

        // Get all the khatedarList where paymentFile equals to (paymentFileId + 1)
        defaultKhatedarShouldNotBeFound("paymentFileId.equals=" + (paymentFileId + 1));
    }

    @Test
    @Transactional
    void getAllKhatedarsByPaymentAdviceDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);
        PaymentAdviceDetails paymentAdviceDetails;
        if (TestUtil.findAll(em, PaymentAdviceDetails.class).isEmpty()) {
            paymentAdviceDetails = PaymentAdviceDetailsResourceIT.createEntity(em);
            em.persist(paymentAdviceDetails);
            em.flush();
        } else {
            paymentAdviceDetails = TestUtil.findAll(em, PaymentAdviceDetails.class).get(0);
        }
        em.persist(paymentAdviceDetails);
        em.flush();
        khatedar.addPaymentAdviceDetails(paymentAdviceDetails);
        khatedarRepository.saveAndFlush(khatedar);
        Long paymentAdviceDetailsId = paymentAdviceDetails.getId();

        // Get all the khatedarList where paymentAdviceDetails equals to paymentAdviceDetailsId
        defaultKhatedarShouldBeFound("paymentAdviceDetailsId.equals=" + paymentAdviceDetailsId);

        // Get all the khatedarList where paymentAdviceDetails equals to (paymentAdviceDetailsId + 1)
        defaultKhatedarShouldNotBeFound("paymentAdviceDetailsId.equals=" + (paymentAdviceDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKhatedarShouldBeFound(String filter) throws Exception {
        restKhatedarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(khatedar.getId().intValue())))
            .andExpect(jsonPath("$.[*].caseFileNo").value(hasItem(DEFAULT_CASE_FILE_NO)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].khatedarStatus").value(hasItem(DEFAULT_KHATEDAR_STATUS.toString())));

        // Check, that the count call also returns 1
        restKhatedarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKhatedarShouldNotBeFound(String filter) throws Exception {
        restKhatedarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKhatedarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKhatedar() throws Exception {
        // Get the khatedar
        restKhatedarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKhatedar() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();

        // Update the khatedar
        Khatedar updatedKhatedar = khatedarRepository.findById(khatedar.getId()).get();
        // Disconnect from session so that the updates on updatedKhatedar are not directly saved in db
        em.detach(updatedKhatedar);
        updatedKhatedar
            .caseFileNo(UPDATED_CASE_FILE_NO)
            .remarks(UPDATED_REMARKS)
            .hissaType(UPDATED_HISSA_TYPE)
            .khatedarStatus(UPDATED_KHATEDAR_STATUS);
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(updatedKhatedar);

        restKhatedarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, khatedarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khatedarDTO))
            )
            .andExpect(status().isOk());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
        Khatedar testKhatedar = khatedarList.get(khatedarList.size() - 1);
        assertThat(testKhatedar.getCaseFileNo()).isEqualTo(UPDATED_CASE_FILE_NO);
        assertThat(testKhatedar.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testKhatedar.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
        assertThat(testKhatedar.getKhatedarStatus()).isEqualTo(UPDATED_KHATEDAR_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingKhatedar() throws Exception {
        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();
        khatedar.setId(count.incrementAndGet());

        // Create the Khatedar
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKhatedarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, khatedarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khatedarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKhatedar() throws Exception {
        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();
        khatedar.setId(count.incrementAndGet());

        // Create the Khatedar
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhatedarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khatedarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKhatedar() throws Exception {
        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();
        khatedar.setId(count.incrementAndGet());

        // Create the Khatedar
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhatedarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khatedarDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKhatedarWithPatch() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();

        // Update the khatedar using partial update
        Khatedar partialUpdatedKhatedar = new Khatedar();
        partialUpdatedKhatedar.setId(khatedar.getId());

        partialUpdatedKhatedar.remarks(UPDATED_REMARKS).khatedarStatus(UPDATED_KHATEDAR_STATUS);

        restKhatedarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKhatedar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKhatedar))
            )
            .andExpect(status().isOk());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
        Khatedar testKhatedar = khatedarList.get(khatedarList.size() - 1);
        assertThat(testKhatedar.getCaseFileNo()).isEqualTo(DEFAULT_CASE_FILE_NO);
        assertThat(testKhatedar.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testKhatedar.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
        assertThat(testKhatedar.getKhatedarStatus()).isEqualTo(UPDATED_KHATEDAR_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateKhatedarWithPatch() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();

        // Update the khatedar using partial update
        Khatedar partialUpdatedKhatedar = new Khatedar();
        partialUpdatedKhatedar.setId(khatedar.getId());

        partialUpdatedKhatedar
            .caseFileNo(UPDATED_CASE_FILE_NO)
            .remarks(UPDATED_REMARKS)
            .hissaType(UPDATED_HISSA_TYPE)
            .khatedarStatus(UPDATED_KHATEDAR_STATUS);

        restKhatedarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKhatedar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKhatedar))
            )
            .andExpect(status().isOk());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
        Khatedar testKhatedar = khatedarList.get(khatedarList.size() - 1);
        assertThat(testKhatedar.getCaseFileNo()).isEqualTo(UPDATED_CASE_FILE_NO);
        assertThat(testKhatedar.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testKhatedar.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
        assertThat(testKhatedar.getKhatedarStatus()).isEqualTo(UPDATED_KHATEDAR_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingKhatedar() throws Exception {
        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();
        khatedar.setId(count.incrementAndGet());

        // Create the Khatedar
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKhatedarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, khatedarDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(khatedarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKhatedar() throws Exception {
        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();
        khatedar.setId(count.incrementAndGet());

        // Create the Khatedar
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhatedarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(khatedarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKhatedar() throws Exception {
        int databaseSizeBeforeUpdate = khatedarRepository.findAll().size();
        khatedar.setId(count.incrementAndGet());

        // Create the Khatedar
        KhatedarDTO khatedarDTO = khatedarMapper.toDto(khatedar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhatedarMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(khatedarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Khatedar in the database
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKhatedar() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        int databaseSizeBeforeDelete = khatedarRepository.findAll().size();

        // Delete the khatedar
        restKhatedarMockMvc
            .perform(delete(ENTITY_API_URL_ID, khatedar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Khatedar> khatedarList = khatedarRepository.findAll();
        assertThat(khatedarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
