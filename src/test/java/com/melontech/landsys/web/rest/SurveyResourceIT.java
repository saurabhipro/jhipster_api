package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.PaymentAdvice;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.SurveyStatus;
import com.melontech.landsys.repository.SurveyRepository;
import com.melontech.landsys.service.criteria.SurveyCriteria;
import com.melontech.landsys.service.dto.SurveyDTO;
import com.melontech.landsys.service.mapper.SurveyMapper;
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
 * Integration tests for the {@link SurveyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SurveyResourceIT {

    private static final String DEFAULT_SURVEYOR = "AAAAAAAAAA";
    private static final String UPDATED_SURVEYOR = "BBBBBBBBBB";

    private static final HissaType DEFAULT_HISSA_TYPE = HissaType.SINGLE_OWNER;
    private static final HissaType UPDATED_HISSA_TYPE = HissaType.JOINT_OWNER;

    private static final Double DEFAULT_SHARE_PERCENTAGE = 1D;
    private static final Double UPDATED_SHARE_PERCENTAGE = 2D;
    private static final Double SMALLER_SHARE_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_AREA = 1D;
    private static final Double UPDATED_AREA = 2D;
    private static final Double SMALLER_AREA = 1D - 1D;

    private static final Double DEFAULT_LAND_MARKET_VALUE = 1D;
    private static final Double UPDATED_LAND_MARKET_VALUE = 2D;
    private static final Double SMALLER_LAND_MARKET_VALUE = 1D - 1D;

    private static final Double DEFAULT_STRUCTURAL_VALUE = 1D;
    private static final Double UPDATED_STRUCTURAL_VALUE = 2D;
    private static final Double SMALLER_STRUCTURAL_VALUE = 1D - 1D;

    private static final Double DEFAULT_HORTICULTURE_VALUE = 1D;
    private static final Double UPDATED_HORTICULTURE_VALUE = 2D;
    private static final Double SMALLER_HORTICULTURE_VALUE = 1D - 1D;

    private static final Double DEFAULT_FOREST_VALUE = 1D;
    private static final Double UPDATED_FOREST_VALUE = 2D;
    private static final Double SMALLER_FOREST_VALUE = 1D - 1D;

    private static final Double DEFAULT_DISTANCE_FROM_CITY = 1D;
    private static final Double UPDATED_DISTANCE_FROM_CITY = 2D;
    private static final Double SMALLER_DISTANCE_FROM_CITY = 1D - 1D;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final SurveyStatus DEFAULT_STATUS = SurveyStatus.OPEN;
    private static final SurveyStatus UPDATED_STATUS = SurveyStatus.CLOSED;

    private static final String ENTITY_API_URL = "/api/surveys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveyMockMvc;

    private Survey survey;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Survey createEntity(EntityManager em) {
        Survey survey = new Survey()
            .surveyor(DEFAULT_SURVEYOR)
            .hissaType(DEFAULT_HISSA_TYPE)
            .sharePercentage(DEFAULT_SHARE_PERCENTAGE)
            .area(DEFAULT_AREA)
            .landMarketValue(DEFAULT_LAND_MARKET_VALUE)
            .structuralValue(DEFAULT_STRUCTURAL_VALUE)
            .horticultureValue(DEFAULT_HORTICULTURE_VALUE)
            .forestValue(DEFAULT_FOREST_VALUE)
            .distanceFromCity(DEFAULT_DISTANCE_FROM_CITY)
            .remarks(DEFAULT_REMARKS)
            .status(DEFAULT_STATUS);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        survey.setProjectLand(projectLand);
        // Add required entity
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        survey.setLandCompensation(landCompensation);
        return survey;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Survey createUpdatedEntity(EntityManager em) {
        Survey survey = new Survey()
            .surveyor(UPDATED_SURVEYOR)
            .hissaType(UPDATED_HISSA_TYPE)
            .sharePercentage(UPDATED_SHARE_PERCENTAGE)
            .area(UPDATED_AREA)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralValue(UPDATED_STRUCTURAL_VALUE)
            .horticultureValue(UPDATED_HORTICULTURE_VALUE)
            .forestValue(UPDATED_FOREST_VALUE)
            .distanceFromCity(UPDATED_DISTANCE_FROM_CITY)
            .remarks(UPDATED_REMARKS)
            .status(UPDATED_STATUS);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createUpdatedEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        survey.setProjectLand(projectLand);
        // Add required entity
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createUpdatedEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        survey.setLandCompensation(landCompensation);
        return survey;
    }

    @BeforeEach
    public void initTest() {
        survey = createEntity(em);
    }

    @Test
    @Transactional
    void createSurvey() throws Exception {
        int databaseSizeBeforeCreate = surveyRepository.findAll().size();
        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);
        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isCreated());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeCreate + 1);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getSurveyor()).isEqualTo(DEFAULT_SURVEYOR);
        assertThat(testSurvey.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
        assertThat(testSurvey.getSharePercentage()).isEqualTo(DEFAULT_SHARE_PERCENTAGE);
        assertThat(testSurvey.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testSurvey.getLandMarketValue()).isEqualTo(DEFAULT_LAND_MARKET_VALUE);
        assertThat(testSurvey.getStructuralValue()).isEqualTo(DEFAULT_STRUCTURAL_VALUE);
        assertThat(testSurvey.getHorticultureValue()).isEqualTo(DEFAULT_HORTICULTURE_VALUE);
        assertThat(testSurvey.getForestValue()).isEqualTo(DEFAULT_FOREST_VALUE);
        assertThat(testSurvey.getDistanceFromCity()).isEqualTo(DEFAULT_DISTANCE_FROM_CITY);
        assertThat(testSurvey.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testSurvey.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createSurveyWithExistingId() throws Exception {
        // Create the Survey with an existing ID
        survey.setId(1L);
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        int databaseSizeBeforeCreate = surveyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSurveyorIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setSurveyor(null);

        // Create the Survey, which fails.
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHissaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setHissaType(null);

        // Create the Survey, which fails.
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSharePercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setSharePercentage(null);

        // Create the Survey, which fails.
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setArea(null);

        // Create the Survey, which fails.
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLandMarketValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setLandMarketValue(null);

        // Create the Survey, which fails.
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSurveys() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(survey.getId().intValue())))
            .andExpect(jsonPath("$.[*].surveyor").value(hasItem(DEFAULT_SURVEYOR)))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sharePercentage").value(hasItem(DEFAULT_SHARE_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].landMarketValue").value(hasItem(DEFAULT_LAND_MARKET_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].structuralValue").value(hasItem(DEFAULT_STRUCTURAL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].horticultureValue").value(hasItem(DEFAULT_HORTICULTURE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].forestValue").value(hasItem(DEFAULT_FOREST_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].distanceFromCity").value(hasItem(DEFAULT_DISTANCE_FROM_CITY.doubleValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get the survey
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL_ID, survey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(survey.getId().intValue()))
            .andExpect(jsonPath("$.surveyor").value(DEFAULT_SURVEYOR))
            .andExpect(jsonPath("$.hissaType").value(DEFAULT_HISSA_TYPE.toString()))
            .andExpect(jsonPath("$.sharePercentage").value(DEFAULT_SHARE_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.doubleValue()))
            .andExpect(jsonPath("$.landMarketValue").value(DEFAULT_LAND_MARKET_VALUE.doubleValue()))
            .andExpect(jsonPath("$.structuralValue").value(DEFAULT_STRUCTURAL_VALUE.doubleValue()))
            .andExpect(jsonPath("$.horticultureValue").value(DEFAULT_HORTICULTURE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.forestValue").value(DEFAULT_FOREST_VALUE.doubleValue()))
            .andExpect(jsonPath("$.distanceFromCity").value(DEFAULT_DISTANCE_FROM_CITY.doubleValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getSurveysByIdFiltering() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        Long id = survey.getId();

        defaultSurveyShouldBeFound("id.equals=" + id);
        defaultSurveyShouldNotBeFound("id.notEquals=" + id);

        defaultSurveyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSurveyShouldNotBeFound("id.greaterThan=" + id);

        defaultSurveyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSurveyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSurveysBySurveyorIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where surveyor equals to DEFAULT_SURVEYOR
        defaultSurveyShouldBeFound("surveyor.equals=" + DEFAULT_SURVEYOR);

        // Get all the surveyList where surveyor equals to UPDATED_SURVEYOR
        defaultSurveyShouldNotBeFound("surveyor.equals=" + UPDATED_SURVEYOR);
    }

    @Test
    @Transactional
    void getAllSurveysBySurveyorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where surveyor not equals to DEFAULT_SURVEYOR
        defaultSurveyShouldNotBeFound("surveyor.notEquals=" + DEFAULT_SURVEYOR);

        // Get all the surveyList where surveyor not equals to UPDATED_SURVEYOR
        defaultSurveyShouldBeFound("surveyor.notEquals=" + UPDATED_SURVEYOR);
    }

    @Test
    @Transactional
    void getAllSurveysBySurveyorIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where surveyor in DEFAULT_SURVEYOR or UPDATED_SURVEYOR
        defaultSurveyShouldBeFound("surveyor.in=" + DEFAULT_SURVEYOR + "," + UPDATED_SURVEYOR);

        // Get all the surveyList where surveyor equals to UPDATED_SURVEYOR
        defaultSurveyShouldNotBeFound("surveyor.in=" + UPDATED_SURVEYOR);
    }

    @Test
    @Transactional
    void getAllSurveysBySurveyorIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where surveyor is not null
        defaultSurveyShouldBeFound("surveyor.specified=true");

        // Get all the surveyList where surveyor is null
        defaultSurveyShouldNotBeFound("surveyor.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysBySurveyorContainsSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where surveyor contains DEFAULT_SURVEYOR
        defaultSurveyShouldBeFound("surveyor.contains=" + DEFAULT_SURVEYOR);

        // Get all the surveyList where surveyor contains UPDATED_SURVEYOR
        defaultSurveyShouldNotBeFound("surveyor.contains=" + UPDATED_SURVEYOR);
    }

    @Test
    @Transactional
    void getAllSurveysBySurveyorNotContainsSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where surveyor does not contain DEFAULT_SURVEYOR
        defaultSurveyShouldNotBeFound("surveyor.doesNotContain=" + DEFAULT_SURVEYOR);

        // Get all the surveyList where surveyor does not contain UPDATED_SURVEYOR
        defaultSurveyShouldBeFound("surveyor.doesNotContain=" + UPDATED_SURVEYOR);
    }

    @Test
    @Transactional
    void getAllSurveysByHissaTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where hissaType equals to DEFAULT_HISSA_TYPE
        defaultSurveyShouldBeFound("hissaType.equals=" + DEFAULT_HISSA_TYPE);

        // Get all the surveyList where hissaType equals to UPDATED_HISSA_TYPE
        defaultSurveyShouldNotBeFound("hissaType.equals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllSurveysByHissaTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where hissaType not equals to DEFAULT_HISSA_TYPE
        defaultSurveyShouldNotBeFound("hissaType.notEquals=" + DEFAULT_HISSA_TYPE);

        // Get all the surveyList where hissaType not equals to UPDATED_HISSA_TYPE
        defaultSurveyShouldBeFound("hissaType.notEquals=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllSurveysByHissaTypeIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where hissaType in DEFAULT_HISSA_TYPE or UPDATED_HISSA_TYPE
        defaultSurveyShouldBeFound("hissaType.in=" + DEFAULT_HISSA_TYPE + "," + UPDATED_HISSA_TYPE);

        // Get all the surveyList where hissaType equals to UPDATED_HISSA_TYPE
        defaultSurveyShouldNotBeFound("hissaType.in=" + UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void getAllSurveysByHissaTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where hissaType is not null
        defaultSurveyShouldBeFound("hissaType.specified=true");

        // Get all the surveyList where hissaType is null
        defaultSurveyShouldNotBeFound("hissaType.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysBySharePercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where sharePercentage equals to DEFAULT_SHARE_PERCENTAGE
        defaultSurveyShouldBeFound("sharePercentage.equals=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the surveyList where sharePercentage equals to UPDATED_SHARE_PERCENTAGE
        defaultSurveyShouldNotBeFound("sharePercentage.equals=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSurveysBySharePercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where sharePercentage not equals to DEFAULT_SHARE_PERCENTAGE
        defaultSurveyShouldNotBeFound("sharePercentage.notEquals=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the surveyList where sharePercentage not equals to UPDATED_SHARE_PERCENTAGE
        defaultSurveyShouldBeFound("sharePercentage.notEquals=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSurveysBySharePercentageIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where sharePercentage in DEFAULT_SHARE_PERCENTAGE or UPDATED_SHARE_PERCENTAGE
        defaultSurveyShouldBeFound("sharePercentage.in=" + DEFAULT_SHARE_PERCENTAGE + "," + UPDATED_SHARE_PERCENTAGE);

        // Get all the surveyList where sharePercentage equals to UPDATED_SHARE_PERCENTAGE
        defaultSurveyShouldNotBeFound("sharePercentage.in=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSurveysBySharePercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where sharePercentage is not null
        defaultSurveyShouldBeFound("sharePercentage.specified=true");

        // Get all the surveyList where sharePercentage is null
        defaultSurveyShouldNotBeFound("sharePercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysBySharePercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where sharePercentage is greater than or equal to DEFAULT_SHARE_PERCENTAGE
        defaultSurveyShouldBeFound("sharePercentage.greaterThanOrEqual=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the surveyList where sharePercentage is greater than or equal to UPDATED_SHARE_PERCENTAGE
        defaultSurveyShouldNotBeFound("sharePercentage.greaterThanOrEqual=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSurveysBySharePercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where sharePercentage is less than or equal to DEFAULT_SHARE_PERCENTAGE
        defaultSurveyShouldBeFound("sharePercentage.lessThanOrEqual=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the surveyList where sharePercentage is less than or equal to SMALLER_SHARE_PERCENTAGE
        defaultSurveyShouldNotBeFound("sharePercentage.lessThanOrEqual=" + SMALLER_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSurveysBySharePercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where sharePercentage is less than DEFAULT_SHARE_PERCENTAGE
        defaultSurveyShouldNotBeFound("sharePercentage.lessThan=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the surveyList where sharePercentage is less than UPDATED_SHARE_PERCENTAGE
        defaultSurveyShouldBeFound("sharePercentage.lessThan=" + UPDATED_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSurveysBySharePercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where sharePercentage is greater than DEFAULT_SHARE_PERCENTAGE
        defaultSurveyShouldNotBeFound("sharePercentage.greaterThan=" + DEFAULT_SHARE_PERCENTAGE);

        // Get all the surveyList where sharePercentage is greater than SMALLER_SHARE_PERCENTAGE
        defaultSurveyShouldBeFound("sharePercentage.greaterThan=" + SMALLER_SHARE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSurveysByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where area equals to DEFAULT_AREA
        defaultSurveyShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the surveyList where area equals to UPDATED_AREA
        defaultSurveyShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSurveysByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where area not equals to DEFAULT_AREA
        defaultSurveyShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the surveyList where area not equals to UPDATED_AREA
        defaultSurveyShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSurveysByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where area in DEFAULT_AREA or UPDATED_AREA
        defaultSurveyShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the surveyList where area equals to UPDATED_AREA
        defaultSurveyShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSurveysByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where area is not null
        defaultSurveyShouldBeFound("area.specified=true");

        // Get all the surveyList where area is null
        defaultSurveyShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysByAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where area is greater than or equal to DEFAULT_AREA
        defaultSurveyShouldBeFound("area.greaterThanOrEqual=" + DEFAULT_AREA);

        // Get all the surveyList where area is greater than or equal to UPDATED_AREA
        defaultSurveyShouldNotBeFound("area.greaterThanOrEqual=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSurveysByAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where area is less than or equal to DEFAULT_AREA
        defaultSurveyShouldBeFound("area.lessThanOrEqual=" + DEFAULT_AREA);

        // Get all the surveyList where area is less than or equal to SMALLER_AREA
        defaultSurveyShouldNotBeFound("area.lessThanOrEqual=" + SMALLER_AREA);
    }

    @Test
    @Transactional
    void getAllSurveysByAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where area is less than DEFAULT_AREA
        defaultSurveyShouldNotBeFound("area.lessThan=" + DEFAULT_AREA);

        // Get all the surveyList where area is less than UPDATED_AREA
        defaultSurveyShouldBeFound("area.lessThan=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSurveysByAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where area is greater than DEFAULT_AREA
        defaultSurveyShouldNotBeFound("area.greaterThan=" + DEFAULT_AREA);

        // Get all the surveyList where area is greater than SMALLER_AREA
        defaultSurveyShouldBeFound("area.greaterThan=" + SMALLER_AREA);
    }

    @Test
    @Transactional
    void getAllSurveysByLandMarketValueIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where landMarketValue equals to DEFAULT_LAND_MARKET_VALUE
        defaultSurveyShouldBeFound("landMarketValue.equals=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the surveyList where landMarketValue equals to UPDATED_LAND_MARKET_VALUE
        defaultSurveyShouldNotBeFound("landMarketValue.equals=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByLandMarketValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where landMarketValue not equals to DEFAULT_LAND_MARKET_VALUE
        defaultSurveyShouldNotBeFound("landMarketValue.notEquals=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the surveyList where landMarketValue not equals to UPDATED_LAND_MARKET_VALUE
        defaultSurveyShouldBeFound("landMarketValue.notEquals=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByLandMarketValueIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where landMarketValue in DEFAULT_LAND_MARKET_VALUE or UPDATED_LAND_MARKET_VALUE
        defaultSurveyShouldBeFound("landMarketValue.in=" + DEFAULT_LAND_MARKET_VALUE + "," + UPDATED_LAND_MARKET_VALUE);

        // Get all the surveyList where landMarketValue equals to UPDATED_LAND_MARKET_VALUE
        defaultSurveyShouldNotBeFound("landMarketValue.in=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByLandMarketValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where landMarketValue is not null
        defaultSurveyShouldBeFound("landMarketValue.specified=true");

        // Get all the surveyList where landMarketValue is null
        defaultSurveyShouldNotBeFound("landMarketValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysByLandMarketValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where landMarketValue is greater than or equal to DEFAULT_LAND_MARKET_VALUE
        defaultSurveyShouldBeFound("landMarketValue.greaterThanOrEqual=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the surveyList where landMarketValue is greater than or equal to UPDATED_LAND_MARKET_VALUE
        defaultSurveyShouldNotBeFound("landMarketValue.greaterThanOrEqual=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByLandMarketValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where landMarketValue is less than or equal to DEFAULT_LAND_MARKET_VALUE
        defaultSurveyShouldBeFound("landMarketValue.lessThanOrEqual=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the surveyList where landMarketValue is less than or equal to SMALLER_LAND_MARKET_VALUE
        defaultSurveyShouldNotBeFound("landMarketValue.lessThanOrEqual=" + SMALLER_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByLandMarketValueIsLessThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where landMarketValue is less than DEFAULT_LAND_MARKET_VALUE
        defaultSurveyShouldNotBeFound("landMarketValue.lessThan=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the surveyList where landMarketValue is less than UPDATED_LAND_MARKET_VALUE
        defaultSurveyShouldBeFound("landMarketValue.lessThan=" + UPDATED_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByLandMarketValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where landMarketValue is greater than DEFAULT_LAND_MARKET_VALUE
        defaultSurveyShouldNotBeFound("landMarketValue.greaterThan=" + DEFAULT_LAND_MARKET_VALUE);

        // Get all the surveyList where landMarketValue is greater than SMALLER_LAND_MARKET_VALUE
        defaultSurveyShouldBeFound("landMarketValue.greaterThan=" + SMALLER_LAND_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByStructuralValueIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where structuralValue equals to DEFAULT_STRUCTURAL_VALUE
        defaultSurveyShouldBeFound("structuralValue.equals=" + DEFAULT_STRUCTURAL_VALUE);

        // Get all the surveyList where structuralValue equals to UPDATED_STRUCTURAL_VALUE
        defaultSurveyShouldNotBeFound("structuralValue.equals=" + UPDATED_STRUCTURAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByStructuralValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where structuralValue not equals to DEFAULT_STRUCTURAL_VALUE
        defaultSurveyShouldNotBeFound("structuralValue.notEquals=" + DEFAULT_STRUCTURAL_VALUE);

        // Get all the surveyList where structuralValue not equals to UPDATED_STRUCTURAL_VALUE
        defaultSurveyShouldBeFound("structuralValue.notEquals=" + UPDATED_STRUCTURAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByStructuralValueIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where structuralValue in DEFAULT_STRUCTURAL_VALUE or UPDATED_STRUCTURAL_VALUE
        defaultSurveyShouldBeFound("structuralValue.in=" + DEFAULT_STRUCTURAL_VALUE + "," + UPDATED_STRUCTURAL_VALUE);

        // Get all the surveyList where structuralValue equals to UPDATED_STRUCTURAL_VALUE
        defaultSurveyShouldNotBeFound("structuralValue.in=" + UPDATED_STRUCTURAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByStructuralValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where structuralValue is not null
        defaultSurveyShouldBeFound("structuralValue.specified=true");

        // Get all the surveyList where structuralValue is null
        defaultSurveyShouldNotBeFound("structuralValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysByStructuralValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where structuralValue is greater than or equal to DEFAULT_STRUCTURAL_VALUE
        defaultSurveyShouldBeFound("structuralValue.greaterThanOrEqual=" + DEFAULT_STRUCTURAL_VALUE);

        // Get all the surveyList where structuralValue is greater than or equal to UPDATED_STRUCTURAL_VALUE
        defaultSurveyShouldNotBeFound("structuralValue.greaterThanOrEqual=" + UPDATED_STRUCTURAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByStructuralValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where structuralValue is less than or equal to DEFAULT_STRUCTURAL_VALUE
        defaultSurveyShouldBeFound("structuralValue.lessThanOrEqual=" + DEFAULT_STRUCTURAL_VALUE);

        // Get all the surveyList where structuralValue is less than or equal to SMALLER_STRUCTURAL_VALUE
        defaultSurveyShouldNotBeFound("structuralValue.lessThanOrEqual=" + SMALLER_STRUCTURAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByStructuralValueIsLessThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where structuralValue is less than DEFAULT_STRUCTURAL_VALUE
        defaultSurveyShouldNotBeFound("structuralValue.lessThan=" + DEFAULT_STRUCTURAL_VALUE);

        // Get all the surveyList where structuralValue is less than UPDATED_STRUCTURAL_VALUE
        defaultSurveyShouldBeFound("structuralValue.lessThan=" + UPDATED_STRUCTURAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByStructuralValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where structuralValue is greater than DEFAULT_STRUCTURAL_VALUE
        defaultSurveyShouldNotBeFound("structuralValue.greaterThan=" + DEFAULT_STRUCTURAL_VALUE);

        // Get all the surveyList where structuralValue is greater than SMALLER_STRUCTURAL_VALUE
        defaultSurveyShouldBeFound("structuralValue.greaterThan=" + SMALLER_STRUCTURAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByHorticultureValueIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where horticultureValue equals to DEFAULT_HORTICULTURE_VALUE
        defaultSurveyShouldBeFound("horticultureValue.equals=" + DEFAULT_HORTICULTURE_VALUE);

        // Get all the surveyList where horticultureValue equals to UPDATED_HORTICULTURE_VALUE
        defaultSurveyShouldNotBeFound("horticultureValue.equals=" + UPDATED_HORTICULTURE_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByHorticultureValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where horticultureValue not equals to DEFAULT_HORTICULTURE_VALUE
        defaultSurveyShouldNotBeFound("horticultureValue.notEquals=" + DEFAULT_HORTICULTURE_VALUE);

        // Get all the surveyList where horticultureValue not equals to UPDATED_HORTICULTURE_VALUE
        defaultSurveyShouldBeFound("horticultureValue.notEquals=" + UPDATED_HORTICULTURE_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByHorticultureValueIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where horticultureValue in DEFAULT_HORTICULTURE_VALUE or UPDATED_HORTICULTURE_VALUE
        defaultSurveyShouldBeFound("horticultureValue.in=" + DEFAULT_HORTICULTURE_VALUE + "," + UPDATED_HORTICULTURE_VALUE);

        // Get all the surveyList where horticultureValue equals to UPDATED_HORTICULTURE_VALUE
        defaultSurveyShouldNotBeFound("horticultureValue.in=" + UPDATED_HORTICULTURE_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByHorticultureValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where horticultureValue is not null
        defaultSurveyShouldBeFound("horticultureValue.specified=true");

        // Get all the surveyList where horticultureValue is null
        defaultSurveyShouldNotBeFound("horticultureValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysByHorticultureValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where horticultureValue is greater than or equal to DEFAULT_HORTICULTURE_VALUE
        defaultSurveyShouldBeFound("horticultureValue.greaterThanOrEqual=" + DEFAULT_HORTICULTURE_VALUE);

        // Get all the surveyList where horticultureValue is greater than or equal to UPDATED_HORTICULTURE_VALUE
        defaultSurveyShouldNotBeFound("horticultureValue.greaterThanOrEqual=" + UPDATED_HORTICULTURE_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByHorticultureValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where horticultureValue is less than or equal to DEFAULT_HORTICULTURE_VALUE
        defaultSurveyShouldBeFound("horticultureValue.lessThanOrEqual=" + DEFAULT_HORTICULTURE_VALUE);

        // Get all the surveyList where horticultureValue is less than or equal to SMALLER_HORTICULTURE_VALUE
        defaultSurveyShouldNotBeFound("horticultureValue.lessThanOrEqual=" + SMALLER_HORTICULTURE_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByHorticultureValueIsLessThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where horticultureValue is less than DEFAULT_HORTICULTURE_VALUE
        defaultSurveyShouldNotBeFound("horticultureValue.lessThan=" + DEFAULT_HORTICULTURE_VALUE);

        // Get all the surveyList where horticultureValue is less than UPDATED_HORTICULTURE_VALUE
        defaultSurveyShouldBeFound("horticultureValue.lessThan=" + UPDATED_HORTICULTURE_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByHorticultureValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where horticultureValue is greater than DEFAULT_HORTICULTURE_VALUE
        defaultSurveyShouldNotBeFound("horticultureValue.greaterThan=" + DEFAULT_HORTICULTURE_VALUE);

        // Get all the surveyList where horticultureValue is greater than SMALLER_HORTICULTURE_VALUE
        defaultSurveyShouldBeFound("horticultureValue.greaterThan=" + SMALLER_HORTICULTURE_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByForestValueIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where forestValue equals to DEFAULT_FOREST_VALUE
        defaultSurveyShouldBeFound("forestValue.equals=" + DEFAULT_FOREST_VALUE);

        // Get all the surveyList where forestValue equals to UPDATED_FOREST_VALUE
        defaultSurveyShouldNotBeFound("forestValue.equals=" + UPDATED_FOREST_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByForestValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where forestValue not equals to DEFAULT_FOREST_VALUE
        defaultSurveyShouldNotBeFound("forestValue.notEquals=" + DEFAULT_FOREST_VALUE);

        // Get all the surveyList where forestValue not equals to UPDATED_FOREST_VALUE
        defaultSurveyShouldBeFound("forestValue.notEquals=" + UPDATED_FOREST_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByForestValueIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where forestValue in DEFAULT_FOREST_VALUE or UPDATED_FOREST_VALUE
        defaultSurveyShouldBeFound("forestValue.in=" + DEFAULT_FOREST_VALUE + "," + UPDATED_FOREST_VALUE);

        // Get all the surveyList where forestValue equals to UPDATED_FOREST_VALUE
        defaultSurveyShouldNotBeFound("forestValue.in=" + UPDATED_FOREST_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByForestValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where forestValue is not null
        defaultSurveyShouldBeFound("forestValue.specified=true");

        // Get all the surveyList where forestValue is null
        defaultSurveyShouldNotBeFound("forestValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysByForestValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where forestValue is greater than or equal to DEFAULT_FOREST_VALUE
        defaultSurveyShouldBeFound("forestValue.greaterThanOrEqual=" + DEFAULT_FOREST_VALUE);

        // Get all the surveyList where forestValue is greater than or equal to UPDATED_FOREST_VALUE
        defaultSurveyShouldNotBeFound("forestValue.greaterThanOrEqual=" + UPDATED_FOREST_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByForestValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where forestValue is less than or equal to DEFAULT_FOREST_VALUE
        defaultSurveyShouldBeFound("forestValue.lessThanOrEqual=" + DEFAULT_FOREST_VALUE);

        // Get all the surveyList where forestValue is less than or equal to SMALLER_FOREST_VALUE
        defaultSurveyShouldNotBeFound("forestValue.lessThanOrEqual=" + SMALLER_FOREST_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByForestValueIsLessThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where forestValue is less than DEFAULT_FOREST_VALUE
        defaultSurveyShouldNotBeFound("forestValue.lessThan=" + DEFAULT_FOREST_VALUE);

        // Get all the surveyList where forestValue is less than UPDATED_FOREST_VALUE
        defaultSurveyShouldBeFound("forestValue.lessThan=" + UPDATED_FOREST_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByForestValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where forestValue is greater than DEFAULT_FOREST_VALUE
        defaultSurveyShouldNotBeFound("forestValue.greaterThan=" + DEFAULT_FOREST_VALUE);

        // Get all the surveyList where forestValue is greater than SMALLER_FOREST_VALUE
        defaultSurveyShouldBeFound("forestValue.greaterThan=" + SMALLER_FOREST_VALUE);
    }

    @Test
    @Transactional
    void getAllSurveysByDistanceFromCityIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where distanceFromCity equals to DEFAULT_DISTANCE_FROM_CITY
        defaultSurveyShouldBeFound("distanceFromCity.equals=" + DEFAULT_DISTANCE_FROM_CITY);

        // Get all the surveyList where distanceFromCity equals to UPDATED_DISTANCE_FROM_CITY
        defaultSurveyShouldNotBeFound("distanceFromCity.equals=" + UPDATED_DISTANCE_FROM_CITY);
    }

    @Test
    @Transactional
    void getAllSurveysByDistanceFromCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where distanceFromCity not equals to DEFAULT_DISTANCE_FROM_CITY
        defaultSurveyShouldNotBeFound("distanceFromCity.notEquals=" + DEFAULT_DISTANCE_FROM_CITY);

        // Get all the surveyList where distanceFromCity not equals to UPDATED_DISTANCE_FROM_CITY
        defaultSurveyShouldBeFound("distanceFromCity.notEquals=" + UPDATED_DISTANCE_FROM_CITY);
    }

    @Test
    @Transactional
    void getAllSurveysByDistanceFromCityIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where distanceFromCity in DEFAULT_DISTANCE_FROM_CITY or UPDATED_DISTANCE_FROM_CITY
        defaultSurveyShouldBeFound("distanceFromCity.in=" + DEFAULT_DISTANCE_FROM_CITY + "," + UPDATED_DISTANCE_FROM_CITY);

        // Get all the surveyList where distanceFromCity equals to UPDATED_DISTANCE_FROM_CITY
        defaultSurveyShouldNotBeFound("distanceFromCity.in=" + UPDATED_DISTANCE_FROM_CITY);
    }

    @Test
    @Transactional
    void getAllSurveysByDistanceFromCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where distanceFromCity is not null
        defaultSurveyShouldBeFound("distanceFromCity.specified=true");

        // Get all the surveyList where distanceFromCity is null
        defaultSurveyShouldNotBeFound("distanceFromCity.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysByDistanceFromCityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where distanceFromCity is greater than or equal to DEFAULT_DISTANCE_FROM_CITY
        defaultSurveyShouldBeFound("distanceFromCity.greaterThanOrEqual=" + DEFAULT_DISTANCE_FROM_CITY);

        // Get all the surveyList where distanceFromCity is greater than or equal to UPDATED_DISTANCE_FROM_CITY
        defaultSurveyShouldNotBeFound("distanceFromCity.greaterThanOrEqual=" + UPDATED_DISTANCE_FROM_CITY);
    }

    @Test
    @Transactional
    void getAllSurveysByDistanceFromCityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where distanceFromCity is less than or equal to DEFAULT_DISTANCE_FROM_CITY
        defaultSurveyShouldBeFound("distanceFromCity.lessThanOrEqual=" + DEFAULT_DISTANCE_FROM_CITY);

        // Get all the surveyList where distanceFromCity is less than or equal to SMALLER_DISTANCE_FROM_CITY
        defaultSurveyShouldNotBeFound("distanceFromCity.lessThanOrEqual=" + SMALLER_DISTANCE_FROM_CITY);
    }

    @Test
    @Transactional
    void getAllSurveysByDistanceFromCityIsLessThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where distanceFromCity is less than DEFAULT_DISTANCE_FROM_CITY
        defaultSurveyShouldNotBeFound("distanceFromCity.lessThan=" + DEFAULT_DISTANCE_FROM_CITY);

        // Get all the surveyList where distanceFromCity is less than UPDATED_DISTANCE_FROM_CITY
        defaultSurveyShouldBeFound("distanceFromCity.lessThan=" + UPDATED_DISTANCE_FROM_CITY);
    }

    @Test
    @Transactional
    void getAllSurveysByDistanceFromCityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where distanceFromCity is greater than DEFAULT_DISTANCE_FROM_CITY
        defaultSurveyShouldNotBeFound("distanceFromCity.greaterThan=" + DEFAULT_DISTANCE_FROM_CITY);

        // Get all the surveyList where distanceFromCity is greater than SMALLER_DISTANCE_FROM_CITY
        defaultSurveyShouldBeFound("distanceFromCity.greaterThan=" + SMALLER_DISTANCE_FROM_CITY);
    }

    @Test
    @Transactional
    void getAllSurveysByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where remarks equals to DEFAULT_REMARKS
        defaultSurveyShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the surveyList where remarks equals to UPDATED_REMARKS
        defaultSurveyShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllSurveysByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where remarks not equals to DEFAULT_REMARKS
        defaultSurveyShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the surveyList where remarks not equals to UPDATED_REMARKS
        defaultSurveyShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllSurveysByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultSurveyShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the surveyList where remarks equals to UPDATED_REMARKS
        defaultSurveyShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllSurveysByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where remarks is not null
        defaultSurveyShouldBeFound("remarks.specified=true");

        // Get all the surveyList where remarks is null
        defaultSurveyShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysByRemarksContainsSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where remarks contains DEFAULT_REMARKS
        defaultSurveyShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the surveyList where remarks contains UPDATED_REMARKS
        defaultSurveyShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllSurveysByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where remarks does not contain DEFAULT_REMARKS
        defaultSurveyShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the surveyList where remarks does not contain UPDATED_REMARKS
        defaultSurveyShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllSurveysByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where status equals to DEFAULT_STATUS
        defaultSurveyShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the surveyList where status equals to UPDATED_STATUS
        defaultSurveyShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSurveysByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where status not equals to DEFAULT_STATUS
        defaultSurveyShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the surveyList where status not equals to UPDATED_STATUS
        defaultSurveyShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSurveysByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSurveyShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the surveyList where status equals to UPDATED_STATUS
        defaultSurveyShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSurveysByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList where status is not null
        defaultSurveyShouldBeFound("status.specified=true");

        // Get all the surveyList where status is null
        defaultSurveyShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllSurveysByProjectLandIsEqualToSomething() throws Exception {
        // Get already existing entity
        ProjectLand projectLand = survey.getProjectLand();
        surveyRepository.saveAndFlush(survey);
        Long projectLandId = projectLand.getId();

        // Get all the surveyList where projectLand equals to projectLandId
        defaultSurveyShouldBeFound("projectLandId.equals=" + projectLandId);

        // Get all the surveyList where projectLand equals to (projectLandId + 1)
        defaultSurveyShouldNotBeFound("projectLandId.equals=" + (projectLandId + 1));
    }

    @Test
    @Transactional
    void getAllSurveysByLandCompensationIsEqualToSomething() throws Exception {
        // Get already existing entity
        LandCompensation landCompensation = survey.getLandCompensation();
        surveyRepository.saveAndFlush(survey);
        Long landCompensationId = landCompensation.getId();

        // Get all the surveyList where landCompensation equals to landCompensationId
        defaultSurveyShouldBeFound("landCompensationId.equals=" + landCompensationId);

        // Get all the surveyList where landCompensation equals to (landCompensationId + 1)
        defaultSurveyShouldNotBeFound("landCompensationId.equals=" + (landCompensationId + 1));
    }

    @Test
    @Transactional
    void getAllSurveysByPaymentAdviceIsEqualToSomething() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);
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
        survey.addPaymentAdvice(paymentAdvice);
        surveyRepository.saveAndFlush(survey);
        Long paymentAdviceId = paymentAdvice.getId();

        // Get all the surveyList where paymentAdvice equals to paymentAdviceId
        defaultSurveyShouldBeFound("paymentAdviceId.equals=" + paymentAdviceId);

        // Get all the surveyList where paymentAdvice equals to (paymentAdviceId + 1)
        defaultSurveyShouldNotBeFound("paymentAdviceId.equals=" + (paymentAdviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSurveyShouldBeFound(String filter) throws Exception {
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(survey.getId().intValue())))
            .andExpect(jsonPath("$.[*].surveyor").value(hasItem(DEFAULT_SURVEYOR)))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sharePercentage").value(hasItem(DEFAULT_SHARE_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].landMarketValue").value(hasItem(DEFAULT_LAND_MARKET_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].structuralValue").value(hasItem(DEFAULT_STRUCTURAL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].horticultureValue").value(hasItem(DEFAULT_HORTICULTURE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].forestValue").value(hasItem(DEFAULT_FOREST_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].distanceFromCity").value(hasItem(DEFAULT_DISTANCE_FROM_CITY.doubleValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSurveyShouldNotBeFound(String filter) throws Exception {
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSurvey() throws Exception {
        // Get the survey
        restSurveyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey
        Survey updatedSurvey = surveyRepository.findById(survey.getId()).get();
        // Disconnect from session so that the updates on updatedSurvey are not directly saved in db
        em.detach(updatedSurvey);
        updatedSurvey
            .surveyor(UPDATED_SURVEYOR)
            .hissaType(UPDATED_HISSA_TYPE)
            .sharePercentage(UPDATED_SHARE_PERCENTAGE)
            .area(UPDATED_AREA)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralValue(UPDATED_STRUCTURAL_VALUE)
            .horticultureValue(UPDATED_HORTICULTURE_VALUE)
            .forestValue(UPDATED_FOREST_VALUE)
            .distanceFromCity(UPDATED_DISTANCE_FROM_CITY)
            .remarks(UPDATED_REMARKS)
            .status(UPDATED_STATUS);
        SurveyDTO surveyDTO = surveyMapper.toDto(updatedSurvey);

        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getSurveyor()).isEqualTo(UPDATED_SURVEYOR);
        assertThat(testSurvey.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
        assertThat(testSurvey.getSharePercentage()).isEqualTo(UPDATED_SHARE_PERCENTAGE);
        assertThat(testSurvey.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testSurvey.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testSurvey.getStructuralValue()).isEqualTo(UPDATED_STRUCTURAL_VALUE);
        assertThat(testSurvey.getHorticultureValue()).isEqualTo(UPDATED_HORTICULTURE_VALUE);
        assertThat(testSurvey.getForestValue()).isEqualTo(UPDATED_FOREST_VALUE);
        assertThat(testSurvey.getDistanceFromCity()).isEqualTo(UPDATED_DISTANCE_FROM_CITY);
        assertThat(testSurvey.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testSurvey.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSurveyWithPatch() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey using partial update
        Survey partialUpdatedSurvey = new Survey();
        partialUpdatedSurvey.setId(survey.getId());

        partialUpdatedSurvey
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .horticultureValue(UPDATED_HORTICULTURE_VALUE)
            .forestValue(UPDATED_FOREST_VALUE);

        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurvey.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurvey))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getSurveyor()).isEqualTo(DEFAULT_SURVEYOR);
        assertThat(testSurvey.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
        assertThat(testSurvey.getSharePercentage()).isEqualTo(DEFAULT_SHARE_PERCENTAGE);
        assertThat(testSurvey.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testSurvey.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testSurvey.getStructuralValue()).isEqualTo(DEFAULT_STRUCTURAL_VALUE);
        assertThat(testSurvey.getHorticultureValue()).isEqualTo(UPDATED_HORTICULTURE_VALUE);
        assertThat(testSurvey.getForestValue()).isEqualTo(UPDATED_FOREST_VALUE);
        assertThat(testSurvey.getDistanceFromCity()).isEqualTo(DEFAULT_DISTANCE_FROM_CITY);
        assertThat(testSurvey.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testSurvey.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateSurveyWithPatch() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey using partial update
        Survey partialUpdatedSurvey = new Survey();
        partialUpdatedSurvey.setId(survey.getId());

        partialUpdatedSurvey
            .surveyor(UPDATED_SURVEYOR)
            .hissaType(UPDATED_HISSA_TYPE)
            .sharePercentage(UPDATED_SHARE_PERCENTAGE)
            .area(UPDATED_AREA)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralValue(UPDATED_STRUCTURAL_VALUE)
            .horticultureValue(UPDATED_HORTICULTURE_VALUE)
            .forestValue(UPDATED_FOREST_VALUE)
            .distanceFromCity(UPDATED_DISTANCE_FROM_CITY)
            .remarks(UPDATED_REMARKS)
            .status(UPDATED_STATUS);

        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurvey.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurvey))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getSurveyor()).isEqualTo(UPDATED_SURVEYOR);
        assertThat(testSurvey.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
        assertThat(testSurvey.getSharePercentage()).isEqualTo(UPDATED_SHARE_PERCENTAGE);
        assertThat(testSurvey.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testSurvey.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testSurvey.getStructuralValue()).isEqualTo(UPDATED_STRUCTURAL_VALUE);
        assertThat(testSurvey.getHorticultureValue()).isEqualTo(UPDATED_HORTICULTURE_VALUE);
        assertThat(testSurvey.getForestValue()).isEqualTo(UPDATED_FOREST_VALUE);
        assertThat(testSurvey.getDistanceFromCity()).isEqualTo(UPDATED_DISTANCE_FROM_CITY);
        assertThat(testSurvey.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testSurvey.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, surveyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeDelete = surveyRepository.findAll().size();

        // Delete the survey
        restSurveyMockMvc
            .perform(delete(ENTITY_API_URL_ID, survey.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
