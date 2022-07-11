package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.domain.enumeration.CompensationStatus;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.repository.LandCompensationRepository;
import com.melontech.landsys.service.criteria.LandCompensationCriteria;
import com.melontech.landsys.service.dto.LandCompensationDTO;
import com.melontech.landsys.service.mapper.LandCompensationMapper;
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
 * Integration tests for the {@link LandCompensationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LandCompensationResourceIT {

    private static final HissaType DEFAULT_HISSA_TYPE = HissaType.SINGLE_OWNER;
    private static final HissaType UPDATED_HISSA_TYPE = HissaType.JOINT_OWNER;

    private static final Double DEFAULT_AREA = 1D;
    private static final Double UPDATED_AREA = 2D;
    private static final Double SMALLER_AREA = 1D - 1D;

    private static final Double DEFAULT_SHARE_PERCENTAGE = 1D;
    private static final Double UPDATED_SHARE_PERCENTAGE = 2D;
    private static final Double SMALLER_SHARE_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_LAND_MARKET_VALUE = 1D;
    private static final Double UPDATED_LAND_MARKET_VALUE = 2D;
    private static final Double SMALLER_LAND_MARKET_VALUE = 1D - 1D;

    private static final Double DEFAULT_STRUCTURAL_COMPENSATION = 1D;
    private static final Double UPDATED_STRUCTURAL_COMPENSATION = 2D;
    private static final Double SMALLER_STRUCTURAL_COMPENSATION = 1D - 1D;

    private static final Double DEFAULT_HORTICULTURE_COMPENSATION = 1D;
    private static final Double UPDATED_HORTICULTURE_COMPENSATION = 2D;
    private static final Double SMALLER_HORTICULTURE_COMPENSATION = 1D - 1D;

    private static final Double DEFAULT_FOREST_COMPENSATION = 1D;
    private static final Double UPDATED_FOREST_COMPENSATION = 2D;
    private static final Double SMALLER_FOREST_COMPENSATION = 1D - 1D;

    private static final Double DEFAULT_SOLATIUM_MONEY = 1D;
    private static final Double UPDATED_SOLATIUM_MONEY = 2D;
    private static final Double SMALLER_SOLATIUM_MONEY = 1D - 1D;

    private static final Double DEFAULT_ADDITIONAL_COMPENSATION = 1D;
    private static final Double UPDATED_ADDITIONAL_COMPENSATION = 2D;
    private static final Double SMALLER_ADDITIONAL_COMPENSATION = 1D - 1D;

    private static final CompensationStatus DEFAULT_STATUS = CompensationStatus.OPEN;
    private static final CompensationStatus UPDATED_STATUS = CompensationStatus.CLOSED;

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ORDER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_PAYMENT_AMOUNT = 2D;
    private static final Double SMALLER_PAYMENT_AMOUNT = 1D - 1D;

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/land-compensations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LandCompensationRepository landCompensationRepository;

    @Autowired
    private LandCompensationMapper landCompensationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLandCompensationMockMvc;

    private LandCompensation landCompensation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandCompensation createEntity(EntityManager em) {
        LandCompensation landCompensation = new LandCompensation()
            .hissaType(DEFAULT_HISSA_TYPE)
            .area(DEFAULT_AREA)
            .sharePercentage(DEFAULT_SHARE_PERCENTAGE)
            .landMarketValue(DEFAULT_LAND_MARKET_VALUE)
            .structuralCompensation(DEFAULT_STRUCTURAL_COMPENSATION)
            .horticultureCompensation(DEFAULT_HORTICULTURE_COMPENSATION)
            .forestCompensation(DEFAULT_FOREST_COMPENSATION)
            .solatiumMoney(DEFAULT_SOLATIUM_MONEY)
            .additionalCompensation(DEFAULT_ADDITIONAL_COMPENSATION)
            .status(DEFAULT_STATUS)
            .orderDate(DEFAULT_ORDER_DATE)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .transactionId(DEFAULT_TRANSACTION_ID);
        // Add required entity
        Khatedar khatedar;
        if (TestUtil.findAll(em, Khatedar.class).isEmpty()) {
            khatedar = KhatedarResourceIT.createEntity(em);
            em.persist(khatedar);
            em.flush();
        } else {
            khatedar = TestUtil.findAll(em, Khatedar.class).get(0);
        }
        landCompensation.setKhatedar(khatedar);
        // Add required entity
        Survey survey;
        if (TestUtil.findAll(em, Survey.class).isEmpty()) {
            survey = SurveyResourceIT.createEntity(em);
            em.persist(survey);
            em.flush();
        } else {
            survey = TestUtil.findAll(em, Survey.class).get(0);
        }
        landCompensation.setSurvey(survey);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        landCompensation.setProjectLand(projectLand);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        landCompensation.getPaymentAdvices().add(paymentAdvice);
        return landCompensation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandCompensation createUpdatedEntity(EntityManager em) {
        LandCompensation landCompensation = new LandCompensation()
            .hissaType(UPDATED_HISSA_TYPE)
            .area(UPDATED_AREA)
            .sharePercentage(UPDATED_SHARE_PERCENTAGE)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralCompensation(UPDATED_STRUCTURAL_COMPENSATION)
            .horticultureCompensation(UPDATED_HORTICULTURE_COMPENSATION)
            .forestCompensation(UPDATED_FOREST_COMPENSATION)
            .solatiumMoney(UPDATED_SOLATIUM_MONEY)
            .additionalCompensation(UPDATED_ADDITIONAL_COMPENSATION)
            .status(UPDATED_STATUS)
            .orderDate(UPDATED_ORDER_DATE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .transactionId(UPDATED_TRANSACTION_ID);
        // Add required entity
        Khatedar khatedar;
        if (TestUtil.findAll(em, Khatedar.class).isEmpty()) {
            khatedar = KhatedarResourceIT.createUpdatedEntity(em);
            em.persist(khatedar);
            em.flush();
        } else {
            khatedar = TestUtil.findAll(em, Khatedar.class).get(0);
        }
        landCompensation.setKhatedar(khatedar);
        // Add required entity
        Survey survey;
        if (TestUtil.findAll(em, Survey.class).isEmpty()) {
            survey = SurveyResourceIT.createUpdatedEntity(em);
            em.persist(survey);
            em.flush();
        } else {
            survey = TestUtil.findAll(em, Survey.class).get(0);
        }
        landCompensation.setSurvey(survey);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createUpdatedEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        landCompensation.setProjectLand(projectLand);
        // Add required entity
        PaymentAdvice paymentAdvice;
        if (TestUtil.findAll(em, PaymentAdvice.class).isEmpty()) {
            paymentAdvice = PaymentAdviceResourceIT.createUpdatedEntity(em);
            em.persist(paymentAdvice);
            em.flush();
        } else {
            paymentAdvice = TestUtil.findAll(em, PaymentAdvice.class).get(0);
        }
        landCompensation.getPaymentAdvices().add(paymentAdvice);
        return landCompensation;
    }

    @BeforeEach
    public void initTest() {
        landCompensation = createEntity(em);
    }

    @Test
    @Transactional
    void createLandCompensation() throws Exception {
        int databaseSizeBeforeCreate = landCompensationRepository.findAll().size();
        // Create the LandCompensation
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);
        restLandCompensationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeCreate + 1);
        LandCompensation testLandCompensation = landCompensationList.get(landCompensationList.size() - 1);
        assertThat(testLandCompensation.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
        assertThat(testLandCompensation.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testLandCompensation.getSharePercentage()).isEqualTo(DEFAULT_SHARE_PERCENTAGE);
        assertThat(testLandCompensation.getLandMarketValue()).isEqualTo(DEFAULT_LAND_MARKET_VALUE);
        assertThat(testLandCompensation.getStructuralCompensation()).isEqualTo(DEFAULT_STRUCTURAL_COMPENSATION);
        assertThat(testLandCompensation.getHorticultureCompensation()).isEqualTo(DEFAULT_HORTICULTURE_COMPENSATION);
        assertThat(testLandCompensation.getForestCompensation()).isEqualTo(DEFAULT_FOREST_COMPENSATION);
        assertThat(testLandCompensation.getSolatiumMoney()).isEqualTo(DEFAULT_SOLATIUM_MONEY);
        assertThat(testLandCompensation.getAdditionalCompensation()).isEqualTo(DEFAULT_ADDITIONAL_COMPENSATION);
        assertThat(testLandCompensation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLandCompensation.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testLandCompensation.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testLandCompensation.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void createLandCompensationWithExistingId() throws Exception {
        // Create the LandCompensation with an existing ID
        landCompensation.setId(1L);
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        int databaseSizeBeforeCreate = landCompensationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandCompensationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHissaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = landCompensationRepository.findAll().size();
        // set the field null
        landCompensation.setHissaType(null);

        // Create the LandCompensation, which fails.
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        restLandCompensationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = landCompensationRepository.findAll().size();
        // set the field null
        landCompensation.setArea(null);

        // Create the LandCompensation, which fails.
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        restLandCompensationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSharePercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = landCompensationRepository.findAll().size();
        // set the field null
        landCompensation.setSharePercentage(null);

        // Create the LandCompensation, which fails.
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        restLandCompensationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLandMarketValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = landCompensationRepository.findAll().size();
        // set the field null
        landCompensation.setLandMarketValue(null);

        // Create the LandCompensation, which fails.
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        restLandCompensationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLandCompensations() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList
        restLandCompensationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landCompensation.getId().intValue())))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].sharePercentage").value(hasItem(DEFAULT_SHARE_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].landMarketValue").value(hasItem(DEFAULT_LAND_MARKET_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].structuralCompensation").value(hasItem(DEFAULT_STRUCTURAL_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].horticultureCompensation").value(hasItem(DEFAULT_HORTICULTURE_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].forestCompensation").value(hasItem(DEFAULT_FOREST_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].solatiumMoney").value(hasItem(DEFAULT_SOLATIUM_MONEY.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalCompensation").value(hasItem(DEFAULT_ADDITIONAL_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)));
    }

    @Test
    @Transactional
    void getLandCompensation() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get the landCompensation
        restLandCompensationMockMvc
            .perform(get(ENTITY_API_URL_ID, landCompensation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(landCompensation.getId().intValue()))
            .andExpect(jsonPath("$.hissaType").value(DEFAULT_HISSA_TYPE.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.doubleValue()))
            .andExpect(jsonPath("$.sharePercentage").value(DEFAULT_SHARE_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.landMarketValue").value(DEFAULT_LAND_MARKET_VALUE.doubleValue()))
            .andExpect(jsonPath("$.structuralCompensation").value(DEFAULT_STRUCTURAL_COMPENSATION.doubleValue()))
            .andExpect(jsonPath("$.horticultureCompensation").value(DEFAULT_HORTICULTURE_COMPENSATION.doubleValue()))
            .andExpect(jsonPath("$.forestCompensation").value(DEFAULT_FOREST_COMPENSATION.doubleValue()))
            .andExpect(jsonPath("$.solatiumMoney").value(DEFAULT_SOLATIUM_MONEY.doubleValue()))
            .andExpect(jsonPath("$.additionalCompensation").value(DEFAULT_ADDITIONAL_COMPENSATION.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID));
    }

    @Test
    @Transactional
    void getLandCompensationsByIdFiltering() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        Long id = landCompensation.getId();

        defaultLandCompensationShouldBeFound("id.equals=" + id);
        defaultLandCompensationShouldNotBeFound("id.notEquals=" + id);

        defaultLandCompensationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLandCompensationShouldNotBeFound("id.greaterThan=" + id);

        defaultLandCompensationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLandCompensationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHissaTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where hissaType equals to DEFAULT_HISSA_TYPE
        defaultLandCompensationShouldBeFound("hissaType.equals=" + DEFAULT_HISSA_TYPE);

        // Get all the landCompensationList where hissaType equals to UPDATED_HISSA_TYPE
        defaultLandCompensationShouldNotBeFound("hissaType.equals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHissaTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where hissaType not equals to DEFAULT_HISSA_TYPE
        defaultLandCompensationShouldNotBeFound("hissaType.notEquals=" + DEFAULT_HISSA_TYPE);

        // Get all the landCompensationList where hissaType not equals to UPDATED_HISSA_TYPE
        defaultLandCompensationShouldBeFound("hissaType.notEquals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHissaTypeIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where hissaType in DEFAULT_HISSA_TYPE or UPDATED_HISSA_TYPE
        defaultLandCompensationShouldBeFound("hissaType.in=" + DEFAULT_HISSA_TYPE + "," + UPDATED_HISSA_TYPE);

        // Get all the landCompensationList where hissaType equals to UPDATED_HISSA_TYPE
        defaultLandCompensationShouldNotBeFound("hissaType.in=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHissaTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where hissaType is not null
        defaultLandCompensationShouldBeFound("hissaType.specified=true");

        // Get all the landCompensationList where hissaType is null
        defaultLandCompensationShouldNotBeFound("hissaType.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where area equals to DEFAULT_AREA
        defaultLandCompensationShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the landCompensationList where area equals to UPDATED_AREA
        defaultLandCompensationShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where area not equals to DEFAULT_AREA
        defaultLandCompensationShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the landCompensationList where area not equals to UPDATED_AREA
        defaultLandCompensationShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where area in DEFAULT_AREA or UPDATED_AREA
        defaultLandCompensationShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the landCompensationList where area equals to UPDATED_AREA
        defaultLandCompensationShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where area is not null
        defaultLandCompensationShouldBeFound("area.specified=true");

        // Get all the landCompensationList where area is null
        defaultLandCompensationShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where area is greater than or equal to DEFAULT_AREA
        defaultLandCompensationShouldBeFound("area.greaterThanOrEqual=" + DEFAULT_AREA);

        // Get all the landCompensationList where area is greater than or equal to UPDATED_AREA
        defaultLandCompensationShouldNotBeFound("area.greaterThanOrEqual=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where area is less than or equal to DEFAULT_AREA
        defaultLandCompensationShouldBeFound("area.lessThanOrEqual=" + DEFAULT_AREA);

        // Get all the landCompensationList where area is less than or equal to SMALLER_AREA
        defaultLandCompensationShouldNotBeFound("area.lessThanOrEqual=" + SMALLER_AREA);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where area is less than DEFAULT_AREA
        defaultLandCompensationShouldNotBeFound("area.lessThan=" + DEFAULT_AREA);

        // Get all the landCompensationList where area is less than UPDATED_AREA
        defaultLandCompensationShouldBeFound("area.lessThan=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where area is greater than DEFAULT_AREA
        defaultLandCompensationShouldNotBeFound("area.greaterThan=" + DEFAULT_AREA);

        // Get all the landCompensationList where area is greater than SMALLER_AREA
        defaultLandCompensationShouldBeFound("area.greaterThan=" + SMALLER_AREA);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySharePercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where sharePercentage equals to DEFAULT_SHARE_PERCENTAGE
        defaultLandCompensationShouldBeFound("sharePercentage.equals=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the landCompensationList where sharePercentage equals to UPDATED_SHARE_PERCENTAGE
        defaultLandCompensationShouldNotBeFound("sharePercentage.equals=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySharePercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where sharePercentage not equals to DEFAULT_SHARE_PERCENTAGE
        defaultLandCompensationShouldNotBeFound("sharePercentage.notEquals=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the landCompensationList where sharePercentage not equals to UPDATED_SHARE_PERCENTAGE
        defaultLandCompensationShouldBeFound("sharePercentage.notEquals=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySharePercentageIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where sharePercentage in DEFAULT_SHARE_PERCENTAGE or UPDATED_SHARE_PERCENTAGE
        defaultLandCompensationShouldBeFound("sharePercentage.in=" + DEFAULT_SHARE_PERCENTAGE + "," + UPDATED_SHARE_PERCENTAGE);

        // Get all the landCompensationList where sharePercentage equals to UPDATED_SHARE_PERCENTAGE
        defaultLandCompensationShouldNotBeFound("sharePercentage.in=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySharePercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where sharePercentage is not null
        defaultLandCompensationShouldBeFound("sharePercentage.specified=true");

        // Get all the landCompensationList where sharePercentage is null
        defaultLandCompensationShouldNotBeFound("sharePercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySharePercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where sharePercentage is greater than or equal to DEFAULT_SHARE_PERCENTAGE
        defaultLandCompensationShouldBeFound("sharePercentage.greaterThanOrEqual=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the landCompensationList where sharePercentage is greater than or equal to UPDATED_SHARE_PERCENTAGE
        defaultLandCompensationShouldNotBeFound("sharePercentage.greaterThanOrEqual=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySharePercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where sharePercentage is less than or equal to DEFAULT_SHARE_PERCENTAGE
        defaultLandCompensationShouldBeFound("sharePercentage.lessThanOrEqual=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the landCompensationList where sharePercentage is less than or equal to SMALLER_SHARE_PERCENTAGE
        defaultLandCompensationShouldNotBeFound("sharePercentage.lessThanOrEqual=" + SMALLER_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySharePercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where sharePercentage is less than DEFAULT_SHARE_PERCENTAGE
        defaultLandCompensationShouldNotBeFound("sharePercentage.lessThan=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the landCompensationList where sharePercentage is less than UPDATED_SHARE_PERCENTAGE
        defaultLandCompensationShouldBeFound("sharePercentage.lessThan=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySharePercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where sharePercentage is greater than DEFAULT_SHARE_PERCENTAGE
        defaultLandCompensationShouldNotBeFound("sharePercentage.greaterThan=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the landCompensationList where sharePercentage is greater than SMALLER_SHARE_PERCENTAGE
        defaultLandCompensationShouldBeFound("sharePercentage.greaterThan=" + SMALLER_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByLandMarketValueIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where landMarketValue equals to DEFAULT_LAND_MARKET_VALUE
        defaultLandCompensationShouldBeFound("landMarketValue.equals=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the landCompensationList where landMarketValue equals to UPDATED_LAND_MARKET_VALUE
        defaultLandCompensationShouldNotBeFound("landMarketValue.equals=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByLandMarketValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where landMarketValue not equals to DEFAULT_LAND_MARKET_VALUE
        defaultLandCompensationShouldNotBeFound("landMarketValue.notEquals=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the landCompensationList where landMarketValue not equals to UPDATED_LAND_MARKET_VALUE
        defaultLandCompensationShouldBeFound("landMarketValue.notEquals=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByLandMarketValueIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where landMarketValue in DEFAULT_LAND_MARKET_VALUE or UPDATED_LAND_MARKET_VALUE
        defaultLandCompensationShouldBeFound("landMarketValue.in=" + DEFAULT_LAND_MARKET_VALUE + "," + UPDATED_LAND_MARKET_VALUE);

        // Get all the landCompensationList where landMarketValue equals to UPDATED_LAND_MARKET_VALUE
        defaultLandCompensationShouldNotBeFound("landMarketValue.in=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByLandMarketValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where landMarketValue is not null
        defaultLandCompensationShouldBeFound("landMarketValue.specified=true");

        // Get all the landCompensationList where landMarketValue is null
        defaultLandCompensationShouldNotBeFound("landMarketValue.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByLandMarketValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where landMarketValue is greater than or equal to DEFAULT_LAND_MARKET_VALUE
        defaultLandCompensationShouldBeFound("landMarketValue.greaterThanOrEqual=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the landCompensationList where landMarketValue is greater than or equal to UPDATED_LAND_MARKET_VALUE
        defaultLandCompensationShouldNotBeFound("landMarketValue.greaterThanOrEqual=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByLandMarketValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where landMarketValue is less than or equal to DEFAULT_LAND_MARKET_VALUE
        defaultLandCompensationShouldBeFound("landMarketValue.lessThanOrEqual=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the landCompensationList where landMarketValue is less than or equal to SMALLER_LAND_MARKET_VALUE
        defaultLandCompensationShouldNotBeFound("landMarketValue.lessThanOrEqual=" + SMALLER_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByLandMarketValueIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where landMarketValue is less than DEFAULT_LAND_MARKET_VALUE
        defaultLandCompensationShouldNotBeFound("landMarketValue.lessThan=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the landCompensationList where landMarketValue is less than UPDATED_LAND_MARKET_VALUE
        defaultLandCompensationShouldBeFound("landMarketValue.lessThan=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByLandMarketValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where landMarketValue is greater than DEFAULT_LAND_MARKET_VALUE
        defaultLandCompensationShouldNotBeFound("landMarketValue.greaterThan=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the landCompensationList where landMarketValue is greater than SMALLER_LAND_MARKET_VALUE
        defaultLandCompensationShouldBeFound("landMarketValue.greaterThan=" + SMALLER_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStructuralCompensationIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where structuralCompensation equals to DEFAULT_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldBeFound("structuralCompensation.equals=" + DEFAULT_STRUCTURAL_COMPENSATION);

        // Get all the landCompensationList where structuralCompensation equals to UPDATED_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("structuralCompensation.equals=" + UPDATED_STRUCTURAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStructuralCompensationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where structuralCompensation not equals to DEFAULT_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("structuralCompensation.notEquals=" + DEFAULT_STRUCTURAL_COMPENSATION);

        // Get all the landCompensationList where structuralCompensation not equals to UPDATED_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldBeFound("structuralCompensation.notEquals=" + UPDATED_STRUCTURAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStructuralCompensationIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where structuralCompensation in DEFAULT_STRUCTURAL_COMPENSATION or UPDATED_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldBeFound(
            "structuralCompensation.in=" + DEFAULT_STRUCTURAL_COMPENSATION + "," + UPDATED_STRUCTURAL_COMPENSATION
        );

        // Get all the landCompensationList where structuralCompensation equals to UPDATED_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("structuralCompensation.in=" + UPDATED_STRUCTURAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStructuralCompensationIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where structuralCompensation is not null
        defaultLandCompensationShouldBeFound("structuralCompensation.specified=true");

        // Get all the landCompensationList where structuralCompensation is null
        defaultLandCompensationShouldNotBeFound("structuralCompensation.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStructuralCompensationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where structuralCompensation is greater than or equal to DEFAULT_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldBeFound("structuralCompensation.greaterThanOrEqual=" + DEFAULT_STRUCTURAL_COMPENSATION);

        // Get all the landCompensationList where structuralCompensation is greater than or equal to UPDATED_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("structuralCompensation.greaterThanOrEqual=" + UPDATED_STRUCTURAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStructuralCompensationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where structuralCompensation is less than or equal to DEFAULT_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldBeFound("structuralCompensation.lessThanOrEqual=" + DEFAULT_STRUCTURAL_COMPENSATION);

        // Get all the landCompensationList where structuralCompensation is less than or equal to SMALLER_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("structuralCompensation.lessThanOrEqual=" + SMALLER_STRUCTURAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStructuralCompensationIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where structuralCompensation is less than DEFAULT_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("structuralCompensation.lessThan=" + DEFAULT_STRUCTURAL_COMPENSATION);

        // Get all the landCompensationList where structuralCompensation is less than UPDATED_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldBeFound("structuralCompensation.lessThan=" + UPDATED_STRUCTURAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStructuralCompensationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where structuralCompensation is greater than DEFAULT_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("structuralCompensation.greaterThan=" + DEFAULT_STRUCTURAL_COMPENSATION);

        // Get all the landCompensationList where structuralCompensation is greater than SMALLER_STRUCTURAL_COMPENSATION
        defaultLandCompensationShouldBeFound("structuralCompensation.greaterThan=" + SMALLER_STRUCTURAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHorticultureCompensationIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where horticultureCompensation equals to DEFAULT_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldBeFound("horticultureCompensation.equals=" + DEFAULT_HORTICULTURE_COMPENSATION);

        // Get all the landCompensationList where horticultureCompensation equals to UPDATED_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldNotBeFound("horticultureCompensation.equals=" + UPDATED_HORTICULTURE_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHorticultureCompensationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where horticultureCompensation not equals to DEFAULT_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldNotBeFound("horticultureCompensation.notEquals=" + DEFAULT_HORTICULTURE_COMPENSATION);

        // Get all the landCompensationList where horticultureCompensation not equals to UPDATED_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldBeFound("horticultureCompensation.notEquals=" + UPDATED_HORTICULTURE_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHorticultureCompensationIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where horticultureCompensation in DEFAULT_HORTICULTURE_COMPENSATION or UPDATED_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldBeFound(
            "horticultureCompensation.in=" + DEFAULT_HORTICULTURE_COMPENSATION + "," + UPDATED_HORTICULTURE_COMPENSATION
        );

        // Get all the landCompensationList where horticultureCompensation equals to UPDATED_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldNotBeFound("horticultureCompensation.in=" + UPDATED_HORTICULTURE_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHorticultureCompensationIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where horticultureCompensation is not null
        defaultLandCompensationShouldBeFound("horticultureCompensation.specified=true");

        // Get all the landCompensationList where horticultureCompensation is null
        defaultLandCompensationShouldNotBeFound("horticultureCompensation.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHorticultureCompensationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where horticultureCompensation is greater than or equal to DEFAULT_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldBeFound("horticultureCompensation.greaterThanOrEqual=" + DEFAULT_HORTICULTURE_COMPENSATION);

        // Get all the landCompensationList where horticultureCompensation is greater than or equal to UPDATED_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldNotBeFound("horticultureCompensation.greaterThanOrEqual=" + UPDATED_HORTICULTURE_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHorticultureCompensationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where horticultureCompensation is less than or equal to DEFAULT_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldBeFound("horticultureCompensation.lessThanOrEqual=" + DEFAULT_HORTICULTURE_COMPENSATION);

        // Get all the landCompensationList where horticultureCompensation is less than or equal to SMALLER_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldNotBeFound("horticultureCompensation.lessThanOrEqual=" + SMALLER_HORTICULTURE_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHorticultureCompensationIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where horticultureCompensation is less than DEFAULT_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldNotBeFound("horticultureCompensation.lessThan=" + DEFAULT_HORTICULTURE_COMPENSATION);

        // Get all the landCompensationList where horticultureCompensation is less than UPDATED_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldBeFound("horticultureCompensation.lessThan=" + UPDATED_HORTICULTURE_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByHorticultureCompensationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where horticultureCompensation is greater than DEFAULT_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldNotBeFound("horticultureCompensation.greaterThan=" + DEFAULT_HORTICULTURE_COMPENSATION);

        // Get all the landCompensationList where horticultureCompensation is greater than SMALLER_HORTICULTURE_COMPENSATION
        defaultLandCompensationShouldBeFound("horticultureCompensation.greaterThan=" + SMALLER_HORTICULTURE_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByForestCompensationIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where forestCompensation equals to DEFAULT_FOREST_COMPENSATION
        defaultLandCompensationShouldBeFound("forestCompensation.equals=" + DEFAULT_FOREST_COMPENSATION);

        // Get all the landCompensationList where forestCompensation equals to UPDATED_FOREST_COMPENSATION
        defaultLandCompensationShouldNotBeFound("forestCompensation.equals=" + UPDATED_FOREST_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByForestCompensationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where forestCompensation not equals to DEFAULT_FOREST_COMPENSATION
        defaultLandCompensationShouldNotBeFound("forestCompensation.notEquals=" + DEFAULT_FOREST_COMPENSATION);

        // Get all the landCompensationList where forestCompensation not equals to UPDATED_FOREST_COMPENSATION
        defaultLandCompensationShouldBeFound("forestCompensation.notEquals=" + UPDATED_FOREST_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByForestCompensationIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where forestCompensation in DEFAULT_FOREST_COMPENSATION or UPDATED_FOREST_COMPENSATION
        defaultLandCompensationShouldBeFound("forestCompensation.in=" + DEFAULT_FOREST_COMPENSATION + "," + UPDATED_FOREST_COMPENSATION);

        // Get all the landCompensationList where forestCompensation equals to UPDATED_FOREST_COMPENSATION
        defaultLandCompensationShouldNotBeFound("forestCompensation.in=" + UPDATED_FOREST_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByForestCompensationIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where forestCompensation is not null
        defaultLandCompensationShouldBeFound("forestCompensation.specified=true");

        // Get all the landCompensationList where forestCompensation is null
        defaultLandCompensationShouldNotBeFound("forestCompensation.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByForestCompensationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where forestCompensation is greater than or equal to DEFAULT_FOREST_COMPENSATION
        defaultLandCompensationShouldBeFound("forestCompensation.greaterThanOrEqual=" + DEFAULT_FOREST_COMPENSATION);

        // Get all the landCompensationList where forestCompensation is greater than or equal to UPDATED_FOREST_COMPENSATION
        defaultLandCompensationShouldNotBeFound("forestCompensation.greaterThanOrEqual=" + UPDATED_FOREST_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByForestCompensationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where forestCompensation is less than or equal to DEFAULT_FOREST_COMPENSATION
        defaultLandCompensationShouldBeFound("forestCompensation.lessThanOrEqual=" + DEFAULT_FOREST_COMPENSATION);

        // Get all the landCompensationList where forestCompensation is less than or equal to SMALLER_FOREST_COMPENSATION
        defaultLandCompensationShouldNotBeFound("forestCompensation.lessThanOrEqual=" + SMALLER_FOREST_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByForestCompensationIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where forestCompensation is less than DEFAULT_FOREST_COMPENSATION
        defaultLandCompensationShouldNotBeFound("forestCompensation.lessThan=" + DEFAULT_FOREST_COMPENSATION);

        // Get all the landCompensationList where forestCompensation is less than UPDATED_FOREST_COMPENSATION
        defaultLandCompensationShouldBeFound("forestCompensation.lessThan=" + UPDATED_FOREST_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByForestCompensationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where forestCompensation is greater than DEFAULT_FOREST_COMPENSATION
        defaultLandCompensationShouldNotBeFound("forestCompensation.greaterThan=" + DEFAULT_FOREST_COMPENSATION);

        // Get all the landCompensationList where forestCompensation is greater than SMALLER_FOREST_COMPENSATION
        defaultLandCompensationShouldBeFound("forestCompensation.greaterThan=" + SMALLER_FOREST_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySolatiumMoneyIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where solatiumMoney equals to DEFAULT_SOLATIUM_MONEY
        defaultLandCompensationShouldBeFound("solatiumMoney.equals=" + DEFAULT_SOLATIUM_MONEY);

        // Get all the landCompensationList where solatiumMoney equals to UPDATED_SOLATIUM_MONEY
        defaultLandCompensationShouldNotBeFound("solatiumMoney.equals=" + UPDATED_SOLATIUM_MONEY);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySolatiumMoneyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where solatiumMoney not equals to DEFAULT_SOLATIUM_MONEY
        defaultLandCompensationShouldNotBeFound("solatiumMoney.notEquals=" + DEFAULT_SOLATIUM_MONEY);

        // Get all the landCompensationList where solatiumMoney not equals to UPDATED_SOLATIUM_MONEY
        defaultLandCompensationShouldBeFound("solatiumMoney.notEquals=" + UPDATED_SOLATIUM_MONEY);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySolatiumMoneyIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where solatiumMoney in DEFAULT_SOLATIUM_MONEY or UPDATED_SOLATIUM_MONEY
        defaultLandCompensationShouldBeFound("solatiumMoney.in=" + DEFAULT_SOLATIUM_MONEY + "," + UPDATED_SOLATIUM_MONEY);

        // Get all the landCompensationList where solatiumMoney equals to UPDATED_SOLATIUM_MONEY
        defaultLandCompensationShouldNotBeFound("solatiumMoney.in=" + UPDATED_SOLATIUM_MONEY);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySolatiumMoneyIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where solatiumMoney is not null
        defaultLandCompensationShouldBeFound("solatiumMoney.specified=true");

        // Get all the landCompensationList where solatiumMoney is null
        defaultLandCompensationShouldNotBeFound("solatiumMoney.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySolatiumMoneyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where solatiumMoney is greater than or equal to DEFAULT_SOLATIUM_MONEY
        defaultLandCompensationShouldBeFound("solatiumMoney.greaterThanOrEqual=" + DEFAULT_SOLATIUM_MONEY);

        // Get all the landCompensationList where solatiumMoney is greater than or equal to UPDATED_SOLATIUM_MONEY
        defaultLandCompensationShouldNotBeFound("solatiumMoney.greaterThanOrEqual=" + UPDATED_SOLATIUM_MONEY);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySolatiumMoneyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where solatiumMoney is less than or equal to DEFAULT_SOLATIUM_MONEY
        defaultLandCompensationShouldBeFound("solatiumMoney.lessThanOrEqual=" + DEFAULT_SOLATIUM_MONEY);

        // Get all the landCompensationList where solatiumMoney is less than or equal to SMALLER_SOLATIUM_MONEY
        defaultLandCompensationShouldNotBeFound("solatiumMoney.lessThanOrEqual=" + SMALLER_SOLATIUM_MONEY);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySolatiumMoneyIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where solatiumMoney is less than DEFAULT_SOLATIUM_MONEY
        defaultLandCompensationShouldNotBeFound("solatiumMoney.lessThan=" + DEFAULT_SOLATIUM_MONEY);

        // Get all the landCompensationList where solatiumMoney is less than UPDATED_SOLATIUM_MONEY
        defaultLandCompensationShouldBeFound("solatiumMoney.lessThan=" + UPDATED_SOLATIUM_MONEY);
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySolatiumMoneyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where solatiumMoney is greater than DEFAULT_SOLATIUM_MONEY
        defaultLandCompensationShouldNotBeFound("solatiumMoney.greaterThan=" + DEFAULT_SOLATIUM_MONEY);

        // Get all the landCompensationList where solatiumMoney is greater than SMALLER_SOLATIUM_MONEY
        defaultLandCompensationShouldBeFound("solatiumMoney.greaterThan=" + SMALLER_SOLATIUM_MONEY);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAdditionalCompensationIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where additionalCompensation equals to DEFAULT_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldBeFound("additionalCompensation.equals=" + DEFAULT_ADDITIONAL_COMPENSATION);

        // Get all the landCompensationList where additionalCompensation equals to UPDATED_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("additionalCompensation.equals=" + UPDATED_ADDITIONAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAdditionalCompensationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where additionalCompensation not equals to DEFAULT_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("additionalCompensation.notEquals=" + DEFAULT_ADDITIONAL_COMPENSATION);

        // Get all the landCompensationList where additionalCompensation not equals to UPDATED_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldBeFound("additionalCompensation.notEquals=" + UPDATED_ADDITIONAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAdditionalCompensationIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where additionalCompensation in DEFAULT_ADDITIONAL_COMPENSATION or UPDATED_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldBeFound(
            "additionalCompensation.in=" + DEFAULT_ADDITIONAL_COMPENSATION + "," + UPDATED_ADDITIONAL_COMPENSATION
        );

        // Get all the landCompensationList where additionalCompensation equals to UPDATED_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("additionalCompensation.in=" + UPDATED_ADDITIONAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAdditionalCompensationIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where additionalCompensation is not null
        defaultLandCompensationShouldBeFound("additionalCompensation.specified=true");

        // Get all the landCompensationList where additionalCompensation is null
        defaultLandCompensationShouldNotBeFound("additionalCompensation.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAdditionalCompensationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where additionalCompensation is greater than or equal to DEFAULT_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldBeFound("additionalCompensation.greaterThanOrEqual=" + DEFAULT_ADDITIONAL_COMPENSATION);

        // Get all the landCompensationList where additionalCompensation is greater than or equal to UPDATED_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("additionalCompensation.greaterThanOrEqual=" + UPDATED_ADDITIONAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAdditionalCompensationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where additionalCompensation is less than or equal to DEFAULT_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldBeFound("additionalCompensation.lessThanOrEqual=" + DEFAULT_ADDITIONAL_COMPENSATION);

        // Get all the landCompensationList where additionalCompensation is less than or equal to SMALLER_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("additionalCompensation.lessThanOrEqual=" + SMALLER_ADDITIONAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAdditionalCompensationIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where additionalCompensation is less than DEFAULT_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("additionalCompensation.lessThan=" + DEFAULT_ADDITIONAL_COMPENSATION);

        // Get all the landCompensationList where additionalCompensation is less than UPDATED_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldBeFound("additionalCompensation.lessThan=" + UPDATED_ADDITIONAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByAdditionalCompensationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where additionalCompensation is greater than DEFAULT_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldNotBeFound("additionalCompensation.greaterThan=" + DEFAULT_ADDITIONAL_COMPENSATION);

        // Get all the landCompensationList where additionalCompensation is greater than SMALLER_ADDITIONAL_COMPENSATION
        defaultLandCompensationShouldBeFound("additionalCompensation.greaterThan=" + SMALLER_ADDITIONAL_COMPENSATION);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where status equals to DEFAULT_STATUS
        defaultLandCompensationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the landCompensationList where status equals to UPDATED_STATUS
        defaultLandCompensationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where status not equals to DEFAULT_STATUS
        defaultLandCompensationShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the landCompensationList where status not equals to UPDATED_STATUS
        defaultLandCompensationShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLandCompensationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the landCompensationList where status equals to UPDATED_STATUS
        defaultLandCompensationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where status is not null
        defaultLandCompensationShouldBeFound("status.specified=true");

        // Get all the landCompensationList where status is null
        defaultLandCompensationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where orderDate equals to DEFAULT_ORDER_DATE
        defaultLandCompensationShouldBeFound("orderDate.equals=" + DEFAULT_ORDER_DATE);

        // Get all the landCompensationList where orderDate equals to UPDATED_ORDER_DATE
        defaultLandCompensationShouldNotBeFound("orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByOrderDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where orderDate not equals to DEFAULT_ORDER_DATE
        defaultLandCompensationShouldNotBeFound("orderDate.notEquals=" + DEFAULT_ORDER_DATE);

        // Get all the landCompensationList where orderDate not equals to UPDATED_ORDER_DATE
        defaultLandCompensationShouldBeFound("orderDate.notEquals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where orderDate in DEFAULT_ORDER_DATE or UPDATED_ORDER_DATE
        defaultLandCompensationShouldBeFound("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE);

        // Get all the landCompensationList where orderDate equals to UPDATED_ORDER_DATE
        defaultLandCompensationShouldNotBeFound("orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where orderDate is not null
        defaultLandCompensationShouldBeFound("orderDate.specified=true");

        // Get all the landCompensationList where orderDate is null
        defaultLandCompensationShouldNotBeFound("orderDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByOrderDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where orderDate is greater than or equal to DEFAULT_ORDER_DATE
        defaultLandCompensationShouldBeFound("orderDate.greaterThanOrEqual=" + DEFAULT_ORDER_DATE);

        // Get all the landCompensationList where orderDate is greater than or equal to UPDATED_ORDER_DATE
        defaultLandCompensationShouldNotBeFound("orderDate.greaterThanOrEqual=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByOrderDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where orderDate is less than or equal to DEFAULT_ORDER_DATE
        defaultLandCompensationShouldBeFound("orderDate.lessThanOrEqual=" + DEFAULT_ORDER_DATE);

        // Get all the landCompensationList where orderDate is less than or equal to SMALLER_ORDER_DATE
        defaultLandCompensationShouldNotBeFound("orderDate.lessThanOrEqual=" + SMALLER_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByOrderDateIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where orderDate is less than DEFAULT_ORDER_DATE
        defaultLandCompensationShouldNotBeFound("orderDate.lessThan=" + DEFAULT_ORDER_DATE);

        // Get all the landCompensationList where orderDate is less than UPDATED_ORDER_DATE
        defaultLandCompensationShouldBeFound("orderDate.lessThan=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByOrderDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where orderDate is greater than DEFAULT_ORDER_DATE
        defaultLandCompensationShouldNotBeFound("orderDate.greaterThan=" + DEFAULT_ORDER_DATE);

        // Get all the landCompensationList where orderDate is greater than SMALLER_ORDER_DATE
        defaultLandCompensationShouldBeFound("orderDate.greaterThan=" + SMALLER_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where paymentAmount equals to DEFAULT_PAYMENT_AMOUNT
        defaultLandCompensationShouldBeFound("paymentAmount.equals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the landCompensationList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultLandCompensationShouldNotBeFound("paymentAmount.equals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where paymentAmount not equals to DEFAULT_PAYMENT_AMOUNT
        defaultLandCompensationShouldNotBeFound("paymentAmount.notEquals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the landCompensationList where paymentAmount not equals to UPDATED_PAYMENT_AMOUNT
        defaultLandCompensationShouldBeFound("paymentAmount.notEquals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where paymentAmount in DEFAULT_PAYMENT_AMOUNT or UPDATED_PAYMENT_AMOUNT
        defaultLandCompensationShouldBeFound("paymentAmount.in=" + DEFAULT_PAYMENT_AMOUNT + "," + UPDATED_PAYMENT_AMOUNT);

        // Get all the landCompensationList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultLandCompensationShouldNotBeFound("paymentAmount.in=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where paymentAmount is not null
        defaultLandCompensationShouldBeFound("paymentAmount.specified=true");

        // Get all the landCompensationList where paymentAmount is null
        defaultLandCompensationShouldNotBeFound("paymentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where paymentAmount is greater than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultLandCompensationShouldBeFound("paymentAmount.greaterThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the landCompensationList where paymentAmount is greater than or equal to UPDATED_PAYMENT_AMOUNT
        defaultLandCompensationShouldNotBeFound("paymentAmount.greaterThanOrEqual=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where paymentAmount is less than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultLandCompensationShouldBeFound("paymentAmount.lessThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the landCompensationList where paymentAmount is less than or equal to SMALLER_PAYMENT_AMOUNT
        defaultLandCompensationShouldNotBeFound("paymentAmount.lessThanOrEqual=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where paymentAmount is less than DEFAULT_PAYMENT_AMOUNT
        defaultLandCompensationShouldNotBeFound("paymentAmount.lessThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the landCompensationList where paymentAmount is less than UPDATED_PAYMENT_AMOUNT
        defaultLandCompensationShouldBeFound("paymentAmount.lessThan=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where paymentAmount is greater than DEFAULT_PAYMENT_AMOUNT
        defaultLandCompensationShouldNotBeFound("paymentAmount.greaterThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the landCompensationList where paymentAmount is greater than SMALLER_PAYMENT_AMOUNT
        defaultLandCompensationShouldBeFound("paymentAmount.greaterThan=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByTransactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where transactionId equals to DEFAULT_TRANSACTION_ID
        defaultLandCompensationShouldBeFound("transactionId.equals=" + DEFAULT_TRANSACTION_ID);

        // Get all the landCompensationList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultLandCompensationShouldNotBeFound("transactionId.equals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByTransactionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where transactionId not equals to DEFAULT_TRANSACTION_ID
        defaultLandCompensationShouldNotBeFound("transactionId.notEquals=" + DEFAULT_TRANSACTION_ID);

        // Get all the landCompensationList where transactionId not equals to UPDATED_TRANSACTION_ID
        defaultLandCompensationShouldBeFound("transactionId.notEquals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByTransactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where transactionId in DEFAULT_TRANSACTION_ID or UPDATED_TRANSACTION_ID
        defaultLandCompensationShouldBeFound("transactionId.in=" + DEFAULT_TRANSACTION_ID + "," + UPDATED_TRANSACTION_ID);

        // Get all the landCompensationList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultLandCompensationShouldNotBeFound("transactionId.in=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByTransactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where transactionId is not null
        defaultLandCompensationShouldBeFound("transactionId.specified=true");

        // Get all the landCompensationList where transactionId is null
        defaultLandCompensationShouldNotBeFound("transactionId.specified=false");
    }

    @Test
    @Transactional
    void getAllLandCompensationsByTransactionIdContainsSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where transactionId contains DEFAULT_TRANSACTION_ID
        defaultLandCompensationShouldBeFound("transactionId.contains=" + DEFAULT_TRANSACTION_ID);

        // Get all the landCompensationList where transactionId contains UPDATED_TRANSACTION_ID
        defaultLandCompensationShouldNotBeFound("transactionId.contains=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByTransactionIdNotContainsSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        // Get all the landCompensationList where transactionId does not contain DEFAULT_TRANSACTION_ID
        defaultLandCompensationShouldNotBeFound("transactionId.doesNotContain=" + DEFAULT_TRANSACTION_ID);

        // Get all the landCompensationList where transactionId does not contain UPDATED_TRANSACTION_ID
        defaultLandCompensationShouldBeFound("transactionId.doesNotContain=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllLandCompensationsByKhatedarIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);
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
        landCompensation.setKhatedar(khatedar);
        landCompensationRepository.saveAndFlush(landCompensation);
        Long khatedarId = khatedar.getId();

        // Get all the landCompensationList where khatedar equals to khatedarId
        defaultLandCompensationShouldBeFound("khatedarId.equals=" + khatedarId);

        // Get all the landCompensationList where khatedar equals to (khatedarId + 1)
        defaultLandCompensationShouldNotBeFound("khatedarId.equals=" + (khatedarId + 1));
    }

    @Test
    @Transactional
    void getAllLandCompensationsBySurveyIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);
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
        landCompensation.setSurvey(survey);
        landCompensationRepository.saveAndFlush(landCompensation);
        Long surveyId = survey.getId();

        // Get all the landCompensationList where survey equals to surveyId
        defaultLandCompensationShouldBeFound("surveyId.equals=" + surveyId);

        // Get all the landCompensationList where survey equals to (surveyId + 1)
        defaultLandCompensationShouldNotBeFound("surveyId.equals=" + (surveyId + 1));
    }

    @Test
    @Transactional
    void getAllLandCompensationsByProjectLandIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);
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
        landCompensation.setProjectLand(projectLand);
        landCompensationRepository.saveAndFlush(landCompensation);
        Long projectLandId = projectLand.getId();

        // Get all the landCompensationList where projectLand equals to projectLandId
        defaultLandCompensationShouldBeFound("projectLandId.equals=" + projectLandId);

        // Get all the landCompensationList where projectLand equals to (projectLandId + 1)
        defaultLandCompensationShouldNotBeFound("projectLandId.equals=" + (projectLandId + 1));
    }

    @Test
    @Transactional
    void getAllLandCompensationsByPaymentAdviceIsEqualToSomething() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);
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
        landCompensation.addPaymentAdvice(paymentAdvice);
        landCompensationRepository.saveAndFlush(landCompensation);
        Long paymentAdviceId = paymentAdvice.getId();

        // Get all the landCompensationList where paymentAdvice equals to paymentAdviceId
        defaultLandCompensationShouldBeFound("paymentAdviceId.equals=" + paymentAdviceId);

        // Get all the landCompensationList where paymentAdvice equals to (paymentAdviceId + 1)
        defaultLandCompensationShouldNotBeFound("paymentAdviceId.equals=" + (paymentAdviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLandCompensationShouldBeFound(String filter) throws Exception {
        restLandCompensationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landCompensation.getId().intValue())))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].sharePercentage").value(hasItem(DEFAULT_SHARE_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].landMarketValue").value(hasItem(DEFAULT_LAND_MARKET_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].structuralCompensation").value(hasItem(DEFAULT_STRUCTURAL_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].horticultureCompensation").value(hasItem(DEFAULT_HORTICULTURE_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].forestCompensation").value(hasItem(DEFAULT_FOREST_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].solatiumMoney").value(hasItem(DEFAULT_SOLATIUM_MONEY.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalCompensation").value(hasItem(DEFAULT_ADDITIONAL_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)));

        // Check, that the count call also returns 1
        restLandCompensationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLandCompensationShouldNotBeFound(String filter) throws Exception {
        restLandCompensationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLandCompensationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLandCompensation() throws Exception {
        // Get the landCompensation
        restLandCompensationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLandCompensation() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();

        // Update the landCompensation
        LandCompensation updatedLandCompensation = landCompensationRepository.findById(landCompensation.getId()).get();
        // Disconnect from session so that the updates on updatedLandCompensation are not directly saved in db
        em.detach(updatedLandCompensation);
        updatedLandCompensation
            .hissaType(UPDATED_HISSA_TYPE)
            .area(UPDATED_AREA)
            .sharePercentage(UPDATED_SHARE_PERCENTAGE)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralCompensation(UPDATED_STRUCTURAL_COMPENSATION)
            .horticultureCompensation(UPDATED_HORTICULTURE_COMPENSATION)
            .forestCompensation(UPDATED_FOREST_COMPENSATION)
            .solatiumMoney(UPDATED_SOLATIUM_MONEY)
            .additionalCompensation(UPDATED_ADDITIONAL_COMPENSATION)
            .status(UPDATED_STATUS)
            .orderDate(UPDATED_ORDER_DATE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .transactionId(UPDATED_TRANSACTION_ID);
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(updatedLandCompensation);

        restLandCompensationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landCompensationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isOk());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
        LandCompensation testLandCompensation = landCompensationList.get(landCompensationList.size() - 1);
        assertThat(testLandCompensation.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
        assertThat(testLandCompensation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLandCompensation.getSharePercentage()).isEqualTo(UPDATED_SHARE_PERCENTAGE);
        assertThat(testLandCompensation.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testLandCompensation.getStructuralCompensation()).isEqualTo(UPDATED_STRUCTURAL_COMPENSATION);
        assertThat(testLandCompensation.getHorticultureCompensation()).isEqualTo(UPDATED_HORTICULTURE_COMPENSATION);
        assertThat(testLandCompensation.getForestCompensation()).isEqualTo(UPDATED_FOREST_COMPENSATION);
        assertThat(testLandCompensation.getSolatiumMoney()).isEqualTo(UPDATED_SOLATIUM_MONEY);
        assertThat(testLandCompensation.getAdditionalCompensation()).isEqualTo(UPDATED_ADDITIONAL_COMPENSATION);
        assertThat(testLandCompensation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLandCompensation.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testLandCompensation.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testLandCompensation.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void putNonExistingLandCompensation() throws Exception {
        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();
        landCompensation.setId(count.incrementAndGet());

        // Create the LandCompensation
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandCompensationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landCompensationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLandCompensation() throws Exception {
        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();
        landCompensation.setId(count.incrementAndGet());

        // Create the LandCompensation
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandCompensationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLandCompensation() throws Exception {
        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();
        landCompensation.setId(count.incrementAndGet());

        // Create the LandCompensation
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandCompensationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLandCompensationWithPatch() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();

        // Update the landCompensation using partial update
        LandCompensation partialUpdatedLandCompensation = new LandCompensation();
        partialUpdatedLandCompensation.setId(landCompensation.getId());

        partialUpdatedLandCompensation
            .hissaType(UPDATED_HISSA_TYPE)
            .area(UPDATED_AREA)
            .sharePercentage(UPDATED_SHARE_PERCENTAGE)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .horticultureCompensation(UPDATED_HORTICULTURE_COMPENSATION)
            .forestCompensation(UPDATED_FOREST_COMPENSATION)
            .solatiumMoney(UPDATED_SOLATIUM_MONEY)
            .additionalCompensation(UPDATED_ADDITIONAL_COMPENSATION)
            .status(UPDATED_STATUS)
            .orderDate(UPDATED_ORDER_DATE)
            .transactionId(UPDATED_TRANSACTION_ID);

        restLandCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandCompensation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLandCompensation))
            )
            .andExpect(status().isOk());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
        LandCompensation testLandCompensation = landCompensationList.get(landCompensationList.size() - 1);
        assertThat(testLandCompensation.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
        assertThat(testLandCompensation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLandCompensation.getSharePercentage()).isEqualTo(UPDATED_SHARE_PERCENTAGE);
        assertThat(testLandCompensation.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testLandCompensation.getStructuralCompensation()).isEqualTo(DEFAULT_STRUCTURAL_COMPENSATION);
        assertThat(testLandCompensation.getHorticultureCompensation()).isEqualTo(UPDATED_HORTICULTURE_COMPENSATION);
        assertThat(testLandCompensation.getForestCompensation()).isEqualTo(UPDATED_FOREST_COMPENSATION);
        assertThat(testLandCompensation.getSolatiumMoney()).isEqualTo(UPDATED_SOLATIUM_MONEY);
        assertThat(testLandCompensation.getAdditionalCompensation()).isEqualTo(UPDATED_ADDITIONAL_COMPENSATION);
        assertThat(testLandCompensation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLandCompensation.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testLandCompensation.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testLandCompensation.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void fullUpdateLandCompensationWithPatch() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();

        // Update the landCompensation using partial update
        LandCompensation partialUpdatedLandCompensation = new LandCompensation();
        partialUpdatedLandCompensation.setId(landCompensation.getId());

        partialUpdatedLandCompensation
            .hissaType(UPDATED_HISSA_TYPE)
            .area(UPDATED_AREA)
            .sharePercentage(UPDATED_SHARE_PERCENTAGE)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralCompensation(UPDATED_STRUCTURAL_COMPENSATION)
            .horticultureCompensation(UPDATED_HORTICULTURE_COMPENSATION)
            .forestCompensation(UPDATED_FOREST_COMPENSATION)
            .solatiumMoney(UPDATED_SOLATIUM_MONEY)
            .additionalCompensation(UPDATED_ADDITIONAL_COMPENSATION)
            .status(UPDATED_STATUS)
            .orderDate(UPDATED_ORDER_DATE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .transactionId(UPDATED_TRANSACTION_ID);

        restLandCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandCompensation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLandCompensation))
            )
            .andExpect(status().isOk());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
        LandCompensation testLandCompensation = landCompensationList.get(landCompensationList.size() - 1);
        assertThat(testLandCompensation.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
        assertThat(testLandCompensation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLandCompensation.getSharePercentage()).isEqualTo(UPDATED_SHARE_PERCENTAGE);
        assertThat(testLandCompensation.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testLandCompensation.getStructuralCompensation()).isEqualTo(UPDATED_STRUCTURAL_COMPENSATION);
        assertThat(testLandCompensation.getHorticultureCompensation()).isEqualTo(UPDATED_HORTICULTURE_COMPENSATION);
        assertThat(testLandCompensation.getForestCompensation()).isEqualTo(UPDATED_FOREST_COMPENSATION);
        assertThat(testLandCompensation.getSolatiumMoney()).isEqualTo(UPDATED_SOLATIUM_MONEY);
        assertThat(testLandCompensation.getAdditionalCompensation()).isEqualTo(UPDATED_ADDITIONAL_COMPENSATION);
        assertThat(testLandCompensation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLandCompensation.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testLandCompensation.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testLandCompensation.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void patchNonExistingLandCompensation() throws Exception {
        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();
        landCompensation.setId(count.incrementAndGet());

        // Create the LandCompensation
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, landCompensationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLandCompensation() throws Exception {
        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();
        landCompensation.setId(count.incrementAndGet());

        // Create the LandCompensation
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLandCompensation() throws Exception {
        int databaseSizeBeforeUpdate = landCompensationRepository.findAll().size();
        landCompensation.setId(count.incrementAndGet());

        // Create the LandCompensation
        LandCompensationDTO landCompensationDTO = landCompensationMapper.toDto(landCompensation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landCompensationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandCompensation in the database
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLandCompensation() throws Exception {
        // Initialize the database
        landCompensationRepository.saveAndFlush(landCompensation);

        int databaseSizeBeforeDelete = landCompensationRepository.findAll().size();

        // Delete the landCompensation
        restLandCompensationMockMvc
            .perform(delete(ENTITY_API_URL_ID, landCompensation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LandCompensation> landCompensationList = landCompensationRepository.findAll();
        assertThat(landCompensationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
