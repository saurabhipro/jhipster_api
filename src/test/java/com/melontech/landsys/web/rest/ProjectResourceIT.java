package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Land;
import com.melontech.landsys.domain.Project;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.repository.ProjectRepository;
import com.melontech.landsys.service.criteria.ProjectCriteria;
import com.melontech.landsys.service.dto.ProjectDTO;
import com.melontech.landsys.service.mapper.ProjectMapper;
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
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_BUDGET = 1D;
    private static final Double UPDATED_BUDGET = 2D;
    private static final Double SMALLER_BUDGET = 1D - 1D;

    private static final String DEFAULT_APPROVER_1 = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_2 = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_3 = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectMockMvc;

    private Project project;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .budget(DEFAULT_BUDGET)
            .approver1(DEFAULT_APPROVER_1)
            .approver2(DEFAULT_APPROVER_2)
            .approver3(DEFAULT_APPROVER_3);
        return project;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .budget(UPDATED_BUDGET)
            .approver1(UPDATED_APPROVER_1)
            .approver2(UPDATED_APPROVER_2)
            .approver3(UPDATED_APPROVER_3);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();
        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProject.getBudget()).isEqualTo(DEFAULT_BUDGET);
        assertThat(testProject.getApprover1()).isEqualTo(DEFAULT_APPROVER_1);
        assertThat(testProject.getApprover2()).isEqualTo(DEFAULT_APPROVER_2);
        assertThat(testProject.getApprover3()).isEqualTo(DEFAULT_APPROVER_3);
    }

    @Test
    @Transactional
    void createProjectWithExistingId() throws Exception {
        // Create the Project with an existing ID
        project.setId(1L);
        ProjectDTO projectDTO = projectMapper.toDto(project);

        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setName(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setStartDate(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setEndDate(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBudgetIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setBudget(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].approver1").value(hasItem(DEFAULT_APPROVER_1)))
            .andExpect(jsonPath("$.[*].approver2").value(hasItem(DEFAULT_APPROVER_2)))
            .andExpect(jsonPath("$.[*].approver3").value(hasItem(DEFAULT_APPROVER_3)));
    }

    @Test
    @Transactional
    void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.budget").value(DEFAULT_BUDGET.doubleValue()))
            .andExpect(jsonPath("$.approver1").value(DEFAULT_APPROVER_1))
            .andExpect(jsonPath("$.approver2").value(DEFAULT_APPROVER_2))
            .andExpect(jsonPath("$.approver3").value(DEFAULT_APPROVER_3));
    }

    @Test
    @Transactional
    void getProjectsByIdFiltering() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        Long id = project.getId();

        defaultProjectShouldBeFound("id.equals=" + id);
        defaultProjectShouldNotBeFound("id.notEquals=" + id);

        defaultProjectShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where name equals to DEFAULT_NAME
        defaultProjectShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectList where name equals to UPDATED_NAME
        defaultProjectShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where name not equals to DEFAULT_NAME
        defaultProjectShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the projectList where name not equals to UPDATED_NAME
        defaultProjectShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectList where name equals to UPDATED_NAME
        defaultProjectShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where name is not null
        defaultProjectShouldBeFound("name.specified=true");

        // Get all the projectList where name is null
        defaultProjectShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByNameContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where name contains DEFAULT_NAME
        defaultProjectShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the projectList where name contains UPDATED_NAME
        defaultProjectShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where name does not contain DEFAULT_NAME
        defaultProjectShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the projectList where name does not contain UPDATED_NAME
        defaultProjectShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate equals to DEFAULT_START_DATE
        defaultProjectShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the projectList where startDate equals to UPDATED_START_DATE
        defaultProjectShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate not equals to DEFAULT_START_DATE
        defaultProjectShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the projectList where startDate not equals to UPDATED_START_DATE
        defaultProjectShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultProjectShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the projectList where startDate equals to UPDATED_START_DATE
        defaultProjectShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate is not null
        defaultProjectShouldBeFound("startDate.specified=true");

        // Get all the projectList where startDate is null
        defaultProjectShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultProjectShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the projectList where startDate is greater than or equal to UPDATED_START_DATE
        defaultProjectShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate is less than or equal to DEFAULT_START_DATE
        defaultProjectShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the projectList where startDate is less than or equal to SMALLER_START_DATE
        defaultProjectShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate is less than DEFAULT_START_DATE
        defaultProjectShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the projectList where startDate is less than UPDATED_START_DATE
        defaultProjectShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate is greater than DEFAULT_START_DATE
        defaultProjectShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the projectList where startDate is greater than SMALLER_START_DATE
        defaultProjectShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate equals to DEFAULT_END_DATE
        defaultProjectShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the projectList where endDate equals to UPDATED_END_DATE
        defaultProjectShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate not equals to DEFAULT_END_DATE
        defaultProjectShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the projectList where endDate not equals to UPDATED_END_DATE
        defaultProjectShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultProjectShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the projectList where endDate equals to UPDATED_END_DATE
        defaultProjectShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate is not null
        defaultProjectShouldBeFound("endDate.specified=true");

        // Get all the projectList where endDate is null
        defaultProjectShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultProjectShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the projectList where endDate is greater than or equal to UPDATED_END_DATE
        defaultProjectShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate is less than or equal to DEFAULT_END_DATE
        defaultProjectShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the projectList where endDate is less than or equal to SMALLER_END_DATE
        defaultProjectShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate is less than DEFAULT_END_DATE
        defaultProjectShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the projectList where endDate is less than UPDATED_END_DATE
        defaultProjectShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate is greater than DEFAULT_END_DATE
        defaultProjectShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the projectList where endDate is greater than SMALLER_END_DATE
        defaultProjectShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where budget equals to DEFAULT_BUDGET
        defaultProjectShouldBeFound("budget.equals=" + DEFAULT_BUDGET);

        // Get all the projectList where budget equals to UPDATED_BUDGET
        defaultProjectShouldNotBeFound("budget.equals=" + UPDATED_BUDGET);
    }

    @Test
    @Transactional
    void getAllProjectsByBudgetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where budget not equals to DEFAULT_BUDGET
        defaultProjectShouldNotBeFound("budget.notEquals=" + DEFAULT_BUDGET);

        // Get all the projectList where budget not equals to UPDATED_BUDGET
        defaultProjectShouldBeFound("budget.notEquals=" + UPDATED_BUDGET);
    }

    @Test
    @Transactional
    void getAllProjectsByBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where budget in DEFAULT_BUDGET or UPDATED_BUDGET
        defaultProjectShouldBeFound("budget.in=" + DEFAULT_BUDGET + "," + UPDATED_BUDGET);

        // Get all the projectList where budget equals to UPDATED_BUDGET
        defaultProjectShouldNotBeFound("budget.in=" + UPDATED_BUDGET);
    }

    @Test
    @Transactional
    void getAllProjectsByBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where budget is not null
        defaultProjectShouldBeFound("budget.specified=true");

        // Get all the projectList where budget is null
        defaultProjectShouldNotBeFound("budget.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where budget is greater than or equal to DEFAULT_BUDGET
        defaultProjectShouldBeFound("budget.greaterThanOrEqual=" + DEFAULT_BUDGET);

        // Get all the projectList where budget is greater than or equal to UPDATED_BUDGET
        defaultProjectShouldNotBeFound("budget.greaterThanOrEqual=" + UPDATED_BUDGET);
    }

    @Test
    @Transactional
    void getAllProjectsByBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where budget is less than or equal to DEFAULT_BUDGET
        defaultProjectShouldBeFound("budget.lessThanOrEqual=" + DEFAULT_BUDGET);

        // Get all the projectList where budget is less than or equal to SMALLER_BUDGET
        defaultProjectShouldNotBeFound("budget.lessThanOrEqual=" + SMALLER_BUDGET);
    }

    @Test
    @Transactional
    void getAllProjectsByBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where budget is less than DEFAULT_BUDGET
        defaultProjectShouldNotBeFound("budget.lessThan=" + DEFAULT_BUDGET);

        // Get all the projectList where budget is less than UPDATED_BUDGET
        defaultProjectShouldBeFound("budget.lessThan=" + UPDATED_BUDGET);
    }

    @Test
    @Transactional
    void getAllProjectsByBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where budget is greater than DEFAULT_BUDGET
        defaultProjectShouldNotBeFound("budget.greaterThan=" + DEFAULT_BUDGET);

        // Get all the projectList where budget is greater than SMALLER_BUDGET
        defaultProjectShouldBeFound("budget.greaterThan=" + SMALLER_BUDGET);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover1IsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver1 equals to DEFAULT_APPROVER_1
        defaultProjectShouldBeFound("approver1.equals=" + DEFAULT_APPROVER_1);

        // Get all the projectList where approver1 equals to UPDATED_APPROVER_1
        defaultProjectShouldNotBeFound("approver1.equals=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver1 not equals to DEFAULT_APPROVER_1
        defaultProjectShouldNotBeFound("approver1.notEquals=" + DEFAULT_APPROVER_1);

        // Get all the projectList where approver1 not equals to UPDATED_APPROVER_1
        defaultProjectShouldBeFound("approver1.notEquals=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover1IsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver1 in DEFAULT_APPROVER_1 or UPDATED_APPROVER_1
        defaultProjectShouldBeFound("approver1.in=" + DEFAULT_APPROVER_1 + "," + UPDATED_APPROVER_1);

        // Get all the projectList where approver1 equals to UPDATED_APPROVER_1
        defaultProjectShouldNotBeFound("approver1.in=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover1IsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver1 is not null
        defaultProjectShouldBeFound("approver1.specified=true");

        // Get all the projectList where approver1 is null
        defaultProjectShouldNotBeFound("approver1.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByApprover1ContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver1 contains DEFAULT_APPROVER_1
        defaultProjectShouldBeFound("approver1.contains=" + DEFAULT_APPROVER_1);

        // Get all the projectList where approver1 contains UPDATED_APPROVER_1
        defaultProjectShouldNotBeFound("approver1.contains=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover1NotContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver1 does not contain DEFAULT_APPROVER_1
        defaultProjectShouldNotBeFound("approver1.doesNotContain=" + DEFAULT_APPROVER_1);

        // Get all the projectList where approver1 does not contain UPDATED_APPROVER_1
        defaultProjectShouldBeFound("approver1.doesNotContain=" + UPDATED_APPROVER_1);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover2IsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver2 equals to DEFAULT_APPROVER_2
        defaultProjectShouldBeFound("approver2.equals=" + DEFAULT_APPROVER_2);

        // Get all the projectList where approver2 equals to UPDATED_APPROVER_2
        defaultProjectShouldNotBeFound("approver2.equals=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver2 not equals to DEFAULT_APPROVER_2
        defaultProjectShouldNotBeFound("approver2.notEquals=" + DEFAULT_APPROVER_2);

        // Get all the projectList where approver2 not equals to UPDATED_APPROVER_2
        defaultProjectShouldBeFound("approver2.notEquals=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover2IsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver2 in DEFAULT_APPROVER_2 or UPDATED_APPROVER_2
        defaultProjectShouldBeFound("approver2.in=" + DEFAULT_APPROVER_2 + "," + UPDATED_APPROVER_2);

        // Get all the projectList where approver2 equals to UPDATED_APPROVER_2
        defaultProjectShouldNotBeFound("approver2.in=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover2IsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver2 is not null
        defaultProjectShouldBeFound("approver2.specified=true");

        // Get all the projectList where approver2 is null
        defaultProjectShouldNotBeFound("approver2.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByApprover2ContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver2 contains DEFAULT_APPROVER_2
        defaultProjectShouldBeFound("approver2.contains=" + DEFAULT_APPROVER_2);

        // Get all the projectList where approver2 contains UPDATED_APPROVER_2
        defaultProjectShouldNotBeFound("approver2.contains=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover2NotContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver2 does not contain DEFAULT_APPROVER_2
        defaultProjectShouldNotBeFound("approver2.doesNotContain=" + DEFAULT_APPROVER_2);

        // Get all the projectList where approver2 does not contain UPDATED_APPROVER_2
        defaultProjectShouldBeFound("approver2.doesNotContain=" + UPDATED_APPROVER_2);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover3IsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver3 equals to DEFAULT_APPROVER_3
        defaultProjectShouldBeFound("approver3.equals=" + DEFAULT_APPROVER_3);

        // Get all the projectList where approver3 equals to UPDATED_APPROVER_3
        defaultProjectShouldNotBeFound("approver3.equals=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver3 not equals to DEFAULT_APPROVER_3
        defaultProjectShouldNotBeFound("approver3.notEquals=" + DEFAULT_APPROVER_3);

        // Get all the projectList where approver3 not equals to UPDATED_APPROVER_3
        defaultProjectShouldBeFound("approver3.notEquals=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover3IsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver3 in DEFAULT_APPROVER_3 or UPDATED_APPROVER_3
        defaultProjectShouldBeFound("approver3.in=" + DEFAULT_APPROVER_3 + "," + UPDATED_APPROVER_3);

        // Get all the projectList where approver3 equals to UPDATED_APPROVER_3
        defaultProjectShouldNotBeFound("approver3.in=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover3IsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver3 is not null
        defaultProjectShouldBeFound("approver3.specified=true");

        // Get all the projectList where approver3 is null
        defaultProjectShouldNotBeFound("approver3.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByApprover3ContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver3 contains DEFAULT_APPROVER_3
        defaultProjectShouldBeFound("approver3.contains=" + DEFAULT_APPROVER_3);

        // Get all the projectList where approver3 contains UPDATED_APPROVER_3
        defaultProjectShouldNotBeFound("approver3.contains=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllProjectsByApprover3NotContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where approver3 does not contain DEFAULT_APPROVER_3
        defaultProjectShouldNotBeFound("approver3.doesNotContain=" + DEFAULT_APPROVER_3);

        // Get all the projectList where approver3 does not contain UPDATED_APPROVER_3
        defaultProjectShouldBeFound("approver3.doesNotContain=" + UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void getAllProjectsByLandIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);
        Land land;
        if (TestUtil.findAll(em, Land.class).isEmpty()) {
            land = LandResourceIT.createEntity(em);
            em.persist(land);
            em.flush();
        } else {
            land = TestUtil.findAll(em, Land.class).get(0);
        }
        em.persist(land);
        em.flush();
        project.addLand(land);
        projectRepository.saveAndFlush(project);
        Long landId = land.getId();

        // Get all the projectList where land equals to landId
        defaultProjectShouldBeFound("landId.equals=" + landId);

        // Get all the projectList where land equals to (landId + 1)
        defaultProjectShouldNotBeFound("landId.equals=" + (landId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByProjectLandIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);
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
        project.addProjectLand(projectLand);
        projectRepository.saveAndFlush(project);
        Long projectLandId = projectLand.getId();

        // Get all the projectList where projectLand equals to projectLandId
        defaultProjectShouldBeFound("projectLandId.equals=" + projectLandId);

        // Get all the projectList where projectLand equals to (projectLandId + 1)
        defaultProjectShouldNotBeFound("projectLandId.equals=" + (projectLandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectShouldBeFound(String filter) throws Exception {
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].approver1").value(hasItem(DEFAULT_APPROVER_1)))
            .andExpect(jsonPath("$.[*].approver2").value(hasItem(DEFAULT_APPROVER_2)))
            .andExpect(jsonPath("$.[*].approver3").value(hasItem(DEFAULT_APPROVER_3)));

        // Check, that the count call also returns 1
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectShouldNotBeFound(String filter) throws Exception {
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .budget(UPDATED_BUDGET)
            .approver1(UPDATED_APPROVER_1)
            .approver2(UPDATED_APPROVER_2)
            .approver3(UPDATED_APPROVER_3);
        ProjectDTO projectDTO = projectMapper.toDto(updatedProject);

        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getBudget()).isEqualTo(UPDATED_BUDGET);
        assertThat(testProject.getApprover1()).isEqualTo(UPDATED_APPROVER_1);
        assertThat(testProject.getApprover2()).isEqualTo(UPDATED_APPROVER_2);
        assertThat(testProject.getApprover3()).isEqualTo(UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void putNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject.name(UPDATED_NAME).budget(UPDATED_BUDGET).approver1(UPDATED_APPROVER_1).approver3(UPDATED_APPROVER_3);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProject.getBudget()).isEqualTo(UPDATED_BUDGET);
        assertThat(testProject.getApprover1()).isEqualTo(UPDATED_APPROVER_1);
        assertThat(testProject.getApprover2()).isEqualTo(DEFAULT_APPROVER_2);
        assertThat(testProject.getApprover3()).isEqualTo(UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void fullUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .budget(UPDATED_BUDGET)
            .approver1(UPDATED_APPROVER_1)
            .approver2(UPDATED_APPROVER_2)
            .approver3(UPDATED_APPROVER_3);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getBudget()).isEqualTo(UPDATED_BUDGET);
        assertThat(testProject.getApprover1()).isEqualTo(UPDATED_APPROVER_1);
        assertThat(testProject.getApprover2()).isEqualTo(UPDATED_APPROVER_2);
        assertThat(testProject.getApprover3()).isEqualTo(UPDATED_APPROVER_3);
    }

    @Test
    @Transactional
    void patchNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, project.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
