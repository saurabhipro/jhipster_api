package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.domain.LandCompensation;
import com.melontech.landsys.domain.NoticeStatusInfo;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Survey;
import com.melontech.landsys.domain.enumeration.KhatedarStatus;
import com.melontech.landsys.repository.KhatedarRepository;
import com.melontech.landsys.service.KhatedarService;
import com.melontech.landsys.service.criteria.KhatedarCriteria;
import com.melontech.landsys.service.dto.KhatedarDTO;
import com.melontech.landsys.service.mapper.KhatedarMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link KhatedarResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class KhatedarResourceIT {

    private static final String DEFAULT_CASE_FILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CASE_FILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_NOTICE_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NOTICE_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NOTICE_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NOTICE_FILE_CONTENT_TYPE = "image/png";

    private static final KhatedarStatus DEFAULT_STATUS = KhatedarStatus.PENDING_FOR_SURVEY;
    private static final KhatedarStatus UPDATED_STATUS = KhatedarStatus.SURVEY_CREATED;

    private static final String ENTITY_API_URL = "/api/khatedars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KhatedarRepository khatedarRepository;

    @Mock
    private KhatedarRepository khatedarRepositoryMock;

    @Autowired
    private KhatedarMapper khatedarMapper;

    @Mock
    private KhatedarService khatedarServiceMock;

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
            .noticeFile(DEFAULT_NOTICE_FILE)
            .noticeFileContentType(DEFAULT_NOTICE_FILE_CONTENT_TYPE)
            .status(DEFAULT_STATUS);
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
        Survey survey;
        if (TestUtil.findAll(em, Survey.class).isEmpty()) {
            survey = SurveyResourceIT.createEntity(em);
            em.persist(survey);
            em.flush();
        } else {
            survey = TestUtil.findAll(em, Survey.class).get(0);
        }
        khatedar.setSurvey(survey);
        // Add required entity
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        khatedar.getLandCompensations().add(landCompensation);
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
            .noticeFile(UPDATED_NOTICE_FILE)
            .noticeFileContentType(UPDATED_NOTICE_FILE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
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
        Survey survey;
        if (TestUtil.findAll(em, Survey.class).isEmpty()) {
            survey = SurveyResourceIT.createUpdatedEntity(em);
            em.persist(survey);
            em.flush();
        } else {
            survey = TestUtil.findAll(em, Survey.class).get(0);
        }
        khatedar.setSurvey(survey);
        // Add required entity
        LandCompensation landCompensation;
        if (TestUtil.findAll(em, LandCompensation.class).isEmpty()) {
            landCompensation = LandCompensationResourceIT.createUpdatedEntity(em);
            em.persist(landCompensation);
            em.flush();
        } else {
            landCompensation = TestUtil.findAll(em, LandCompensation.class).get(0);
        }
        khatedar.getLandCompensations().add(landCompensation);
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
        assertThat(testKhatedar.getNoticeFile()).isEqualTo(DEFAULT_NOTICE_FILE);
        assertThat(testKhatedar.getNoticeFileContentType()).isEqualTo(DEFAULT_NOTICE_FILE_CONTENT_TYPE);
        assertThat(testKhatedar.getStatus()).isEqualTo(DEFAULT_STATUS);
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
            .andExpect(jsonPath("$.[*].noticeFileContentType").value(hasItem(DEFAULT_NOTICE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].noticeFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOTICE_FILE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllKhatedarsWithEagerRelationshipsIsEnabled() throws Exception {
        when(khatedarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restKhatedarMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(khatedarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllKhatedarsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(khatedarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restKhatedarMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(khatedarServiceMock, times(1)).findAllWithEagerRelationships(any());
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
            .andExpect(jsonPath("$.noticeFileContentType").value(DEFAULT_NOTICE_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.noticeFile").value(Base64Utils.encodeToString(DEFAULT_NOTICE_FILE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
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
    void getAllKhatedarsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where status equals to DEFAULT_STATUS
        defaultKhatedarShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the khatedarList where status equals to UPDATED_STATUS
        defaultKhatedarShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where status not equals to DEFAULT_STATUS
        defaultKhatedarShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the khatedarList where status not equals to UPDATED_STATUS
        defaultKhatedarShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultKhatedarShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the khatedarList where status equals to UPDATED_STATUS
        defaultKhatedarShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllKhatedarsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);

        // Get all the khatedarList where status is not null
        defaultKhatedarShouldBeFound("status.specified=true");

        // Get all the khatedarList where status is null
        defaultKhatedarShouldNotBeFound("status.specified=false");
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
    void getAllKhatedarsByNoticeStatusInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);
        NoticeStatusInfo noticeStatusInfo;
        if (TestUtil.findAll(em, NoticeStatusInfo.class).isEmpty()) {
            noticeStatusInfo = NoticeStatusInfoResourceIT.createEntity(em);
            em.persist(noticeStatusInfo);
            em.flush();
        } else {
            noticeStatusInfo = TestUtil.findAll(em, NoticeStatusInfo.class).get(0);
        }
        em.persist(noticeStatusInfo);
        em.flush();
        khatedar.setNoticeStatusInfo(noticeStatusInfo);
        khatedarRepository.saveAndFlush(khatedar);
        Long noticeStatusInfoId = noticeStatusInfo.getId();

        // Get all the khatedarList where noticeStatusInfo equals to noticeStatusInfoId
        defaultKhatedarShouldBeFound("noticeStatusInfoId.equals=" + noticeStatusInfoId);

        // Get all the khatedarList where noticeStatusInfo equals to (noticeStatusInfoId + 1)
        defaultKhatedarShouldNotBeFound("noticeStatusInfoId.equals=" + (noticeStatusInfoId + 1));
    }

    @Test
    @Transactional
    void getAllKhatedarsBySurveyIsEqualToSomething() throws Exception {
        // Get already existing entity
        Survey survey = khatedar.getSurvey();
        khatedarRepository.saveAndFlush(khatedar);
        Long surveyId = survey.getId();

        // Get all the khatedarList where survey equals to surveyId
        defaultKhatedarShouldBeFound("surveyId.equals=" + surveyId);

        // Get all the khatedarList where survey equals to (surveyId + 1)
        defaultKhatedarShouldNotBeFound("surveyId.equals=" + (surveyId + 1));
    }

    @Test
    @Transactional
    void getAllKhatedarsByLandCompensationIsEqualToSomething() throws Exception {
        // Initialize the database
        khatedarRepository.saveAndFlush(khatedar);
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
        khatedar.addLandCompensation(landCompensation);
        khatedarRepository.saveAndFlush(khatedar);
        Long landCompensationId = landCompensation.getId();

        // Get all the khatedarList where landCompensation equals to landCompensationId
        defaultKhatedarShouldBeFound("landCompensationId.equals=" + landCompensationId);

        // Get all the khatedarList where landCompensation equals to (landCompensationId + 1)
        defaultKhatedarShouldNotBeFound("landCompensationId.equals=" + (landCompensationId + 1));
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
            .andExpect(jsonPath("$.[*].noticeFileContentType").value(hasItem(DEFAULT_NOTICE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].noticeFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOTICE_FILE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

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
            .noticeFile(UPDATED_NOTICE_FILE)
            .noticeFileContentType(UPDATED_NOTICE_FILE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
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
        assertThat(testKhatedar.getNoticeFile()).isEqualTo(UPDATED_NOTICE_FILE);
        assertThat(testKhatedar.getNoticeFileContentType()).isEqualTo(UPDATED_NOTICE_FILE_CONTENT_TYPE);
        assertThat(testKhatedar.getStatus()).isEqualTo(UPDATED_STATUS);
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

        partialUpdatedKhatedar.remarks(UPDATED_REMARKS).status(UPDATED_STATUS);

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
        assertThat(testKhatedar.getNoticeFile()).isEqualTo(DEFAULT_NOTICE_FILE);
        assertThat(testKhatedar.getNoticeFileContentType()).isEqualTo(DEFAULT_NOTICE_FILE_CONTENT_TYPE);
        assertThat(testKhatedar.getStatus()).isEqualTo(UPDATED_STATUS);
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
            .noticeFile(UPDATED_NOTICE_FILE)
            .noticeFileContentType(UPDATED_NOTICE_FILE_CONTENT_TYPE)
            .status(UPDATED_STATUS);

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
        assertThat(testKhatedar.getNoticeFile()).isEqualTo(UPDATED_NOTICE_FILE);
        assertThat(testKhatedar.getNoticeFileContentType()).isEqualTo(UPDATED_NOTICE_FILE_CONTENT_TYPE);
        assertThat(testKhatedar.getStatus()).isEqualTo(UPDATED_STATUS);
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
