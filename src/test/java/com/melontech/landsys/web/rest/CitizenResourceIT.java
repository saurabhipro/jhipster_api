package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.BankBranch;
import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.domain.Khatedar;
import com.melontech.landsys.repository.CitizenRepository;
import com.melontech.landsys.service.CitizenService;
import com.melontech.landsys.service.criteria.CitizenCriteria;
import com.melontech.landsys.service.dto.CitizenDTO;
import com.melontech.landsys.service.mapper.CitizenMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CitizenResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CitizenResourceIT {

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SPOUSE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SPOUSE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUCCESSOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUCCESSOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR = "BBBBBBBBBB";

    private static final String DEFAULT_PAN = "AAAAAAAAAA";
    private static final String UPDATED_PAN = "BBBBBBBBBB";

    private static final byte[] DEFAULT_AADHAR_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AADHAR_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AADHAR_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AADHAR_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PAN_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PAN_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PAN_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PAN_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ACC_NO_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ACC_NO_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ACC_NO_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ACC_NO_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/citizens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitizenRepository citizenRepository;

    @Mock
    private CitizenRepository citizenRepositoryMock;

    @Autowired
    private CitizenMapper citizenMapper;

    @Mock
    private CitizenService citizenServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitizenMockMvc;

    private Citizen citizen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citizen createEntity(EntityManager em) {
        Citizen citizen = new Citizen()
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .dob(DEFAULT_DOB)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .fatherName(DEFAULT_FATHER_NAME)
            .spouseName(DEFAULT_SPOUSE_NAME)
            .successorName(DEFAULT_SUCCESSOR_NAME)
            .aadhar(DEFAULT_AADHAR)
            .pan(DEFAULT_PAN)
            .aadharImage(DEFAULT_AADHAR_IMAGE)
            .aadharImageContentType(DEFAULT_AADHAR_IMAGE_CONTENT_TYPE)
            .panImage(DEFAULT_PAN_IMAGE)
            .panImageContentType(DEFAULT_PAN_IMAGE_CONTENT_TYPE)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .accNoImage(DEFAULT_ACC_NO_IMAGE)
            .accNoImageContentType(DEFAULT_ACC_NO_IMAGE_CONTENT_TYPE);
        return citizen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citizen createUpdatedEntity(EntityManager em) {
        Citizen citizen = new Citizen()
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .dob(UPDATED_DOB)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .fatherName(UPDATED_FATHER_NAME)
            .spouseName(UPDATED_SPOUSE_NAME)
            .successorName(UPDATED_SUCCESSOR_NAME)
            .aadhar(UPDATED_AADHAR)
            .pan(UPDATED_PAN)
            .aadharImage(UPDATED_AADHAR_IMAGE)
            .aadharImageContentType(UPDATED_AADHAR_IMAGE_CONTENT_TYPE)
            .panImage(UPDATED_PAN_IMAGE)
            .panImageContentType(UPDATED_PAN_IMAGE_CONTENT_TYPE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .accNoImage(UPDATED_ACC_NO_IMAGE)
            .accNoImageContentType(UPDATED_ACC_NO_IMAGE_CONTENT_TYPE);
        return citizen;
    }

    @BeforeEach
    public void initTest() {
        citizen = createEntity(em);
    }

    @Test
    @Transactional
    void createCitizen() throws Exception {
        int databaseSizeBeforeCreate = citizenRepository.findAll().size();
        // Create the Citizen
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);
        restCitizenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenDTO)))
            .andExpect(status().isCreated());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeCreate + 1);
        Citizen testCitizen = citizenList.get(citizenList.size() - 1);
        assertThat(testCitizen.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCitizen.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testCitizen.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCitizen.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCitizen.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testCitizen.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testCitizen.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testCitizen.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCitizen.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testCitizen.getSpouseName()).isEqualTo(DEFAULT_SPOUSE_NAME);
        assertThat(testCitizen.getSuccessorName()).isEqualTo(DEFAULT_SUCCESSOR_NAME);
        assertThat(testCitizen.getAadhar()).isEqualTo(DEFAULT_AADHAR);
        assertThat(testCitizen.getPan()).isEqualTo(DEFAULT_PAN);
        assertThat(testCitizen.getAadharImage()).isEqualTo(DEFAULT_AADHAR_IMAGE);
        assertThat(testCitizen.getAadharImageContentType()).isEqualTo(DEFAULT_AADHAR_IMAGE_CONTENT_TYPE);
        assertThat(testCitizen.getPanImage()).isEqualTo(DEFAULT_PAN_IMAGE);
        assertThat(testCitizen.getPanImageContentType()).isEqualTo(DEFAULT_PAN_IMAGE_CONTENT_TYPE);
        assertThat(testCitizen.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testCitizen.getAccNoImage()).isEqualTo(DEFAULT_ACC_NO_IMAGE);
        assertThat(testCitizen.getAccNoImageContentType()).isEqualTo(DEFAULT_ACC_NO_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createCitizenWithExistingId() throws Exception {
        // Create the Citizen with an existing ID
        citizen.setId(1L);
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        int databaseSizeBeforeCreate = citizenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitizenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizenRepository.findAll().size();
        // set the field null
        citizen.setName(null);

        // Create the Citizen, which fails.
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        restCitizenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenDTO)))
            .andExpect(status().isBadRequest());

        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizenRepository.findAll().size();
        // set the field null
        citizen.setAddress(null);

        // Create the Citizen, which fails.
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        restCitizenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenDTO)))
            .andExpect(status().isBadRequest());

        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFatherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizenRepository.findAll().size();
        // set the field null
        citizen.setFatherName(null);

        // Create the Citizen, which fails.
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        restCitizenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenDTO)))
            .andExpect(status().isBadRequest());

        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAadharIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizenRepository.findAll().size();
        // set the field null
        citizen.setAadhar(null);

        // Create the Citizen, which fails.
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        restCitizenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenDTO)))
            .andExpect(status().isBadRequest());

        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCitizens() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList
        restCitizenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizen.getId().intValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].spouseName").value(hasItem(DEFAULT_SPOUSE_NAME)))
            .andExpect(jsonPath("$.[*].successorName").value(hasItem(DEFAULT_SUCCESSOR_NAME)))
            .andExpect(jsonPath("$.[*].aadhar").value(hasItem(DEFAULT_AADHAR)))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN)))
            .andExpect(jsonPath("$.[*].aadharImageContentType").value(hasItem(DEFAULT_AADHAR_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].aadharImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_AADHAR_IMAGE))))
            .andExpect(jsonPath("$.[*].panImageContentType").value(hasItem(DEFAULT_PAN_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].panImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PAN_IMAGE))))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].accNoImageContentType").value(hasItem(DEFAULT_ACC_NO_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].accNoImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACC_NO_IMAGE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCitizensWithEagerRelationshipsIsEnabled() throws Exception {
        when(citizenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCitizenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(citizenServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCitizensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(citizenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCitizenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(citizenServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCitizen() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get the citizen
        restCitizenMockMvc
            .perform(get(ENTITY_API_URL_ID, citizen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citizen.getId().intValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.spouseName").value(DEFAULT_SPOUSE_NAME))
            .andExpect(jsonPath("$.successorName").value(DEFAULT_SUCCESSOR_NAME))
            .andExpect(jsonPath("$.aadhar").value(DEFAULT_AADHAR))
            .andExpect(jsonPath("$.pan").value(DEFAULT_PAN))
            .andExpect(jsonPath("$.aadharImageContentType").value(DEFAULT_AADHAR_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.aadharImage").value(Base64Utils.encodeToString(DEFAULT_AADHAR_IMAGE)))
            .andExpect(jsonPath("$.panImageContentType").value(DEFAULT_PAN_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.panImage").value(Base64Utils.encodeToString(DEFAULT_PAN_IMAGE)))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO))
            .andExpect(jsonPath("$.accNoImageContentType").value(DEFAULT_ACC_NO_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.accNoImage").value(Base64Utils.encodeToString(DEFAULT_ACC_NO_IMAGE)));
    }

    @Test
    @Transactional
    void getCitizensByIdFiltering() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        Long id = citizen.getId();

        defaultCitizenShouldBeFound("id.equals=" + id);
        defaultCitizenShouldNotBeFound("id.notEquals=" + id);

        defaultCitizenShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitizenShouldNotBeFound("id.greaterThan=" + id);

        defaultCitizenShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitizenShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitizensByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where name equals to DEFAULT_NAME
        defaultCitizenShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the citizenList where name equals to UPDATED_NAME
        defaultCitizenShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where name not equals to DEFAULT_NAME
        defaultCitizenShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the citizenList where name not equals to UPDATED_NAME
        defaultCitizenShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByNameIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCitizenShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the citizenList where name equals to UPDATED_NAME
        defaultCitizenShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where name is not null
        defaultCitizenShouldBeFound("name.specified=true");

        // Get all the citizenList where name is null
        defaultCitizenShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByNameContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where name contains DEFAULT_NAME
        defaultCitizenShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the citizenList where name contains UPDATED_NAME
        defaultCitizenShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByNameNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where name does not contain DEFAULT_NAME
        defaultCitizenShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the citizenList where name does not contain UPDATED_NAME
        defaultCitizenShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where address equals to DEFAULT_ADDRESS
        defaultCitizenShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the citizenList where address equals to UPDATED_ADDRESS
        defaultCitizenShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitizensByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where address not equals to DEFAULT_ADDRESS
        defaultCitizenShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the citizenList where address not equals to UPDATED_ADDRESS
        defaultCitizenShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitizensByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCitizenShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the citizenList where address equals to UPDATED_ADDRESS
        defaultCitizenShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitizensByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where address is not null
        defaultCitizenShouldBeFound("address.specified=true");

        // Get all the citizenList where address is null
        defaultCitizenShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByAddressContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where address contains DEFAULT_ADDRESS
        defaultCitizenShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the citizenList where address contains UPDATED_ADDRESS
        defaultCitizenShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitizensByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where address does not contain DEFAULT_ADDRESS
        defaultCitizenShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the citizenList where address does not contain UPDATED_ADDRESS
        defaultCitizenShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitizensByMobileNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where mobileNumber equals to DEFAULT_MOBILE_NUMBER
        defaultCitizenShouldBeFound("mobileNumber.equals=" + DEFAULT_MOBILE_NUMBER);

        // Get all the citizenList where mobileNumber equals to UPDATED_MOBILE_NUMBER
        defaultCitizenShouldNotBeFound("mobileNumber.equals=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByMobileNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where mobileNumber not equals to DEFAULT_MOBILE_NUMBER
        defaultCitizenShouldNotBeFound("mobileNumber.notEquals=" + DEFAULT_MOBILE_NUMBER);

        // Get all the citizenList where mobileNumber not equals to UPDATED_MOBILE_NUMBER
        defaultCitizenShouldBeFound("mobileNumber.notEquals=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByMobileNumberIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where mobileNumber in DEFAULT_MOBILE_NUMBER or UPDATED_MOBILE_NUMBER
        defaultCitizenShouldBeFound("mobileNumber.in=" + DEFAULT_MOBILE_NUMBER + "," + UPDATED_MOBILE_NUMBER);

        // Get all the citizenList where mobileNumber equals to UPDATED_MOBILE_NUMBER
        defaultCitizenShouldNotBeFound("mobileNumber.in=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByMobileNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where mobileNumber is not null
        defaultCitizenShouldBeFound("mobileNumber.specified=true");

        // Get all the citizenList where mobileNumber is null
        defaultCitizenShouldNotBeFound("mobileNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByMobileNumberContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where mobileNumber contains DEFAULT_MOBILE_NUMBER
        defaultCitizenShouldBeFound("mobileNumber.contains=" + DEFAULT_MOBILE_NUMBER);

        // Get all the citizenList where mobileNumber contains UPDATED_MOBILE_NUMBER
        defaultCitizenShouldNotBeFound("mobileNumber.contains=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByMobileNumberNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where mobileNumber does not contain DEFAULT_MOBILE_NUMBER
        defaultCitizenShouldNotBeFound("mobileNumber.doesNotContain=" + DEFAULT_MOBILE_NUMBER);

        // Get all the citizenList where mobileNumber does not contain UPDATED_MOBILE_NUMBER
        defaultCitizenShouldBeFound("mobileNumber.doesNotContain=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where dob equals to DEFAULT_DOB
        defaultCitizenShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the citizenList where dob equals to UPDATED_DOB
        defaultCitizenShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllCitizensByDobIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where dob not equals to DEFAULT_DOB
        defaultCitizenShouldNotBeFound("dob.notEquals=" + DEFAULT_DOB);

        // Get all the citizenList where dob not equals to UPDATED_DOB
        defaultCitizenShouldBeFound("dob.notEquals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllCitizensByDobIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultCitizenShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the citizenList where dob equals to UPDATED_DOB
        defaultCitizenShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllCitizensByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where dob is not null
        defaultCitizenShouldBeFound("dob.specified=true");

        // Get all the citizenList where dob is null
        defaultCitizenShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where dob is greater than or equal to DEFAULT_DOB
        defaultCitizenShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the citizenList where dob is greater than or equal to UPDATED_DOB
        defaultCitizenShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllCitizensByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where dob is less than or equal to DEFAULT_DOB
        defaultCitizenShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the citizenList where dob is less than or equal to SMALLER_DOB
        defaultCitizenShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllCitizensByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where dob is less than DEFAULT_DOB
        defaultCitizenShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the citizenList where dob is less than UPDATED_DOB
        defaultCitizenShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllCitizensByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where dob is greater than DEFAULT_DOB
        defaultCitizenShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the citizenList where dob is greater than SMALLER_DOB
        defaultCitizenShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultCitizenShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the citizenList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultCitizenShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountName not equals to DEFAULT_ACCOUNT_NAME
        defaultCitizenShouldNotBeFound("accountName.notEquals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the citizenList where accountName not equals to UPDATED_ACCOUNT_NAME
        defaultCitizenShouldBeFound("accountName.notEquals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultCitizenShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the citizenList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultCitizenShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountName is not null
        defaultCitizenShouldBeFound("accountName.specified=true");

        // Get all the citizenList where accountName is null
        defaultCitizenShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNameContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountName contains DEFAULT_ACCOUNT_NAME
        defaultCitizenShouldBeFound("accountName.contains=" + DEFAULT_ACCOUNT_NAME);

        // Get all the citizenList where accountName contains UPDATED_ACCOUNT_NAME
        defaultCitizenShouldNotBeFound("accountName.contains=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountName does not contain DEFAULT_ACCOUNT_NAME
        defaultCitizenShouldNotBeFound("accountName.doesNotContain=" + DEFAULT_ACCOUNT_NAME);

        // Get all the citizenList where accountName does not contain UPDATED_ACCOUNT_NAME
        defaultCitizenShouldBeFound("accountName.doesNotContain=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultCitizenShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the citizenList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultCitizenShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultCitizenShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the citizenList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultCitizenShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultCitizenShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the citizenList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultCitizenShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNumber is not null
        defaultCitizenShouldBeFound("accountNumber.specified=true");

        // Get all the citizenList where accountNumber is null
        defaultCitizenShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultCitizenShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the citizenList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultCitizenShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultCitizenShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the citizenList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultCitizenShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCitizensByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where fatherName equals to DEFAULT_FATHER_NAME
        defaultCitizenShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the citizenList where fatherName equals to UPDATED_FATHER_NAME
        defaultCitizenShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFatherNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where fatherName not equals to DEFAULT_FATHER_NAME
        defaultCitizenShouldNotBeFound("fatherName.notEquals=" + DEFAULT_FATHER_NAME);

        // Get all the citizenList where fatherName not equals to UPDATED_FATHER_NAME
        defaultCitizenShouldBeFound("fatherName.notEquals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultCitizenShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the citizenList where fatherName equals to UPDATED_FATHER_NAME
        defaultCitizenShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where fatherName is not null
        defaultCitizenShouldBeFound("fatherName.specified=true");

        // Get all the citizenList where fatherName is null
        defaultCitizenShouldNotBeFound("fatherName.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByFatherNameContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where fatherName contains DEFAULT_FATHER_NAME
        defaultCitizenShouldBeFound("fatherName.contains=" + DEFAULT_FATHER_NAME);

        // Get all the citizenList where fatherName contains UPDATED_FATHER_NAME
        defaultCitizenShouldNotBeFound("fatherName.contains=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFatherNameNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where fatherName does not contain DEFAULT_FATHER_NAME
        defaultCitizenShouldNotBeFound("fatherName.doesNotContain=" + DEFAULT_FATHER_NAME);

        // Get all the citizenList where fatherName does not contain UPDATED_FATHER_NAME
        defaultCitizenShouldBeFound("fatherName.doesNotContain=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySpouseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where spouseName equals to DEFAULT_SPOUSE_NAME
        defaultCitizenShouldBeFound("spouseName.equals=" + DEFAULT_SPOUSE_NAME);

        // Get all the citizenList where spouseName equals to UPDATED_SPOUSE_NAME
        defaultCitizenShouldNotBeFound("spouseName.equals=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySpouseNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where spouseName not equals to DEFAULT_SPOUSE_NAME
        defaultCitizenShouldNotBeFound("spouseName.notEquals=" + DEFAULT_SPOUSE_NAME);

        // Get all the citizenList where spouseName not equals to UPDATED_SPOUSE_NAME
        defaultCitizenShouldBeFound("spouseName.notEquals=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySpouseNameIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where spouseName in DEFAULT_SPOUSE_NAME or UPDATED_SPOUSE_NAME
        defaultCitizenShouldBeFound("spouseName.in=" + DEFAULT_SPOUSE_NAME + "," + UPDATED_SPOUSE_NAME);

        // Get all the citizenList where spouseName equals to UPDATED_SPOUSE_NAME
        defaultCitizenShouldNotBeFound("spouseName.in=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySpouseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where spouseName is not null
        defaultCitizenShouldBeFound("spouseName.specified=true");

        // Get all the citizenList where spouseName is null
        defaultCitizenShouldNotBeFound("spouseName.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensBySpouseNameContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where spouseName contains DEFAULT_SPOUSE_NAME
        defaultCitizenShouldBeFound("spouseName.contains=" + DEFAULT_SPOUSE_NAME);

        // Get all the citizenList where spouseName contains UPDATED_SPOUSE_NAME
        defaultCitizenShouldNotBeFound("spouseName.contains=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySpouseNameNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where spouseName does not contain DEFAULT_SPOUSE_NAME
        defaultCitizenShouldNotBeFound("spouseName.doesNotContain=" + DEFAULT_SPOUSE_NAME);

        // Get all the citizenList where spouseName does not contain UPDATED_SPOUSE_NAME
        defaultCitizenShouldBeFound("spouseName.doesNotContain=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySuccessorNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where successorName equals to DEFAULT_SUCCESSOR_NAME
        defaultCitizenShouldBeFound("successorName.equals=" + DEFAULT_SUCCESSOR_NAME);

        // Get all the citizenList where successorName equals to UPDATED_SUCCESSOR_NAME
        defaultCitizenShouldNotBeFound("successorName.equals=" + UPDATED_SUCCESSOR_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySuccessorNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where successorName not equals to DEFAULT_SUCCESSOR_NAME
        defaultCitizenShouldNotBeFound("successorName.notEquals=" + DEFAULT_SUCCESSOR_NAME);

        // Get all the citizenList where successorName not equals to UPDATED_SUCCESSOR_NAME
        defaultCitizenShouldBeFound("successorName.notEquals=" + UPDATED_SUCCESSOR_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySuccessorNameIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where successorName in DEFAULT_SUCCESSOR_NAME or UPDATED_SUCCESSOR_NAME
        defaultCitizenShouldBeFound("successorName.in=" + DEFAULT_SUCCESSOR_NAME + "," + UPDATED_SUCCESSOR_NAME);

        // Get all the citizenList where successorName equals to UPDATED_SUCCESSOR_NAME
        defaultCitizenShouldNotBeFound("successorName.in=" + UPDATED_SUCCESSOR_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySuccessorNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where successorName is not null
        defaultCitizenShouldBeFound("successorName.specified=true");

        // Get all the citizenList where successorName is null
        defaultCitizenShouldNotBeFound("successorName.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensBySuccessorNameContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where successorName contains DEFAULT_SUCCESSOR_NAME
        defaultCitizenShouldBeFound("successorName.contains=" + DEFAULT_SUCCESSOR_NAME);

        // Get all the citizenList where successorName contains UPDATED_SUCCESSOR_NAME
        defaultCitizenShouldNotBeFound("successorName.contains=" + UPDATED_SUCCESSOR_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensBySuccessorNameNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where successorName does not contain DEFAULT_SUCCESSOR_NAME
        defaultCitizenShouldNotBeFound("successorName.doesNotContain=" + DEFAULT_SUCCESSOR_NAME);

        // Get all the citizenList where successorName does not contain UPDATED_SUCCESSOR_NAME
        defaultCitizenShouldBeFound("successorName.doesNotContain=" + UPDATED_SUCCESSOR_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByAadharIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where aadhar equals to DEFAULT_AADHAR
        defaultCitizenShouldBeFound("aadhar.equals=" + DEFAULT_AADHAR);

        // Get all the citizenList where aadhar equals to UPDATED_AADHAR
        defaultCitizenShouldNotBeFound("aadhar.equals=" + UPDATED_AADHAR);
    }

    @Test
    @Transactional
    void getAllCitizensByAadharIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where aadhar not equals to DEFAULT_AADHAR
        defaultCitizenShouldNotBeFound("aadhar.notEquals=" + DEFAULT_AADHAR);

        // Get all the citizenList where aadhar not equals to UPDATED_AADHAR
        defaultCitizenShouldBeFound("aadhar.notEquals=" + UPDATED_AADHAR);
    }

    @Test
    @Transactional
    void getAllCitizensByAadharIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where aadhar in DEFAULT_AADHAR or UPDATED_AADHAR
        defaultCitizenShouldBeFound("aadhar.in=" + DEFAULT_AADHAR + "," + UPDATED_AADHAR);

        // Get all the citizenList where aadhar equals to UPDATED_AADHAR
        defaultCitizenShouldNotBeFound("aadhar.in=" + UPDATED_AADHAR);
    }

    @Test
    @Transactional
    void getAllCitizensByAadharIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where aadhar is not null
        defaultCitizenShouldBeFound("aadhar.specified=true");

        // Get all the citizenList where aadhar is null
        defaultCitizenShouldNotBeFound("aadhar.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByAadharContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where aadhar contains DEFAULT_AADHAR
        defaultCitizenShouldBeFound("aadhar.contains=" + DEFAULT_AADHAR);

        // Get all the citizenList where aadhar contains UPDATED_AADHAR
        defaultCitizenShouldNotBeFound("aadhar.contains=" + UPDATED_AADHAR);
    }

    @Test
    @Transactional
    void getAllCitizensByAadharNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where aadhar does not contain DEFAULT_AADHAR
        defaultCitizenShouldNotBeFound("aadhar.doesNotContain=" + DEFAULT_AADHAR);

        // Get all the citizenList where aadhar does not contain UPDATED_AADHAR
        defaultCitizenShouldBeFound("aadhar.doesNotContain=" + UPDATED_AADHAR);
    }

    @Test
    @Transactional
    void getAllCitizensByPanIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where pan equals to DEFAULT_PAN
        defaultCitizenShouldBeFound("pan.equals=" + DEFAULT_PAN);

        // Get all the citizenList where pan equals to UPDATED_PAN
        defaultCitizenShouldNotBeFound("pan.equals=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllCitizensByPanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where pan not equals to DEFAULT_PAN
        defaultCitizenShouldNotBeFound("pan.notEquals=" + DEFAULT_PAN);

        // Get all the citizenList where pan not equals to UPDATED_PAN
        defaultCitizenShouldBeFound("pan.notEquals=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllCitizensByPanIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where pan in DEFAULT_PAN or UPDATED_PAN
        defaultCitizenShouldBeFound("pan.in=" + DEFAULT_PAN + "," + UPDATED_PAN);

        // Get all the citizenList where pan equals to UPDATED_PAN
        defaultCitizenShouldNotBeFound("pan.in=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllCitizensByPanIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where pan is not null
        defaultCitizenShouldBeFound("pan.specified=true");

        // Get all the citizenList where pan is null
        defaultCitizenShouldNotBeFound("pan.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByPanContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where pan contains DEFAULT_PAN
        defaultCitizenShouldBeFound("pan.contains=" + DEFAULT_PAN);

        // Get all the citizenList where pan contains UPDATED_PAN
        defaultCitizenShouldNotBeFound("pan.contains=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllCitizensByPanNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where pan does not contain DEFAULT_PAN
        defaultCitizenShouldNotBeFound("pan.doesNotContain=" + DEFAULT_PAN);

        // Get all the citizenList where pan does not contain UPDATED_PAN
        defaultCitizenShouldBeFound("pan.doesNotContain=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultCitizenShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the citizenList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultCitizenShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNo not equals to DEFAULT_ACCOUNT_NO
        defaultCitizenShouldNotBeFound("accountNo.notEquals=" + DEFAULT_ACCOUNT_NO);

        // Get all the citizenList where accountNo not equals to UPDATED_ACCOUNT_NO
        defaultCitizenShouldBeFound("accountNo.notEquals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultCitizenShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the citizenList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultCitizenShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNo is not null
        defaultCitizenShouldBeFound("accountNo.specified=true");

        // Get all the citizenList where accountNo is null
        defaultCitizenShouldNotBeFound("accountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNoContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNo contains DEFAULT_ACCOUNT_NO
        defaultCitizenShouldBeFound("accountNo.contains=" + DEFAULT_ACCOUNT_NO);

        // Get all the citizenList where accountNo contains UPDATED_ACCOUNT_NO
        defaultCitizenShouldNotBeFound("accountNo.contains=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCitizensByAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        // Get all the citizenList where accountNo does not contain DEFAULT_ACCOUNT_NO
        defaultCitizenShouldNotBeFound("accountNo.doesNotContain=" + DEFAULT_ACCOUNT_NO);

        // Get all the citizenList where accountNo does not contain UPDATED_ACCOUNT_NO
        defaultCitizenShouldBeFound("accountNo.doesNotContain=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCitizensByBankBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);
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
        citizen.setBankBranch(bankBranch);
        citizenRepository.saveAndFlush(citizen);
        Long bankBranchId = bankBranch.getId();

        // Get all the citizenList where bankBranch equals to bankBranchId
        defaultCitizenShouldBeFound("bankBranchId.equals=" + bankBranchId);

        // Get all the citizenList where bankBranch equals to (bankBranchId + 1)
        defaultCitizenShouldNotBeFound("bankBranchId.equals=" + (bankBranchId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByKhatedarIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);
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
        citizen.addKhatedar(khatedar);
        citizenRepository.saveAndFlush(citizen);
        Long khatedarId = khatedar.getId();

        // Get all the citizenList where khatedar equals to khatedarId
        defaultCitizenShouldBeFound("khatedarId.equals=" + khatedarId);

        // Get all the citizenList where khatedar equals to (khatedarId + 1)
        defaultCitizenShouldNotBeFound("khatedarId.equals=" + (khatedarId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitizenShouldBeFound(String filter) throws Exception {
        restCitizenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizen.getId().intValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].spouseName").value(hasItem(DEFAULT_SPOUSE_NAME)))
            .andExpect(jsonPath("$.[*].successorName").value(hasItem(DEFAULT_SUCCESSOR_NAME)))
            .andExpect(jsonPath("$.[*].aadhar").value(hasItem(DEFAULT_AADHAR)))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN)))
            .andExpect(jsonPath("$.[*].aadharImageContentType").value(hasItem(DEFAULT_AADHAR_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].aadharImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_AADHAR_IMAGE))))
            .andExpect(jsonPath("$.[*].panImageContentType").value(hasItem(DEFAULT_PAN_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].panImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PAN_IMAGE))))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].accNoImageContentType").value(hasItem(DEFAULT_ACC_NO_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].accNoImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACC_NO_IMAGE))));

        // Check, that the count call also returns 1
        restCitizenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitizenShouldNotBeFound(String filter) throws Exception {
        restCitizenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitizenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCitizen() throws Exception {
        // Get the citizen
        restCitizenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCitizen() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();

        // Update the citizen
        Citizen updatedCitizen = citizenRepository.findById(citizen.getId()).get();
        // Disconnect from session so that the updates on updatedCitizen are not directly saved in db
        em.detach(updatedCitizen);
        updatedCitizen
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .dob(UPDATED_DOB)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .fatherName(UPDATED_FATHER_NAME)
            .spouseName(UPDATED_SPOUSE_NAME)
            .successorName(UPDATED_SUCCESSOR_NAME)
            .aadhar(UPDATED_AADHAR)
            .pan(UPDATED_PAN)
            .aadharImage(UPDATED_AADHAR_IMAGE)
            .aadharImageContentType(UPDATED_AADHAR_IMAGE_CONTENT_TYPE)
            .panImage(UPDATED_PAN_IMAGE)
            .panImageContentType(UPDATED_PAN_IMAGE_CONTENT_TYPE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .accNoImage(UPDATED_ACC_NO_IMAGE)
            .accNoImageContentType(UPDATED_ACC_NO_IMAGE_CONTENT_TYPE);
        CitizenDTO citizenDTO = citizenMapper.toDto(updatedCitizen);

        restCitizenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citizenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizenDTO))
            )
            .andExpect(status().isOk());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
        Citizen testCitizen = citizenList.get(citizenList.size() - 1);
        assertThat(testCitizen.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCitizen.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCitizen.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCitizen.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCitizen.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testCitizen.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testCitizen.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCitizen.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCitizen.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testCitizen.getSpouseName()).isEqualTo(UPDATED_SPOUSE_NAME);
        assertThat(testCitizen.getSuccessorName()).isEqualTo(UPDATED_SUCCESSOR_NAME);
        assertThat(testCitizen.getAadhar()).isEqualTo(UPDATED_AADHAR);
        assertThat(testCitizen.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testCitizen.getAadharImage()).isEqualTo(UPDATED_AADHAR_IMAGE);
        assertThat(testCitizen.getAadharImageContentType()).isEqualTo(UPDATED_AADHAR_IMAGE_CONTENT_TYPE);
        assertThat(testCitizen.getPanImage()).isEqualTo(UPDATED_PAN_IMAGE);
        assertThat(testCitizen.getPanImageContentType()).isEqualTo(UPDATED_PAN_IMAGE_CONTENT_TYPE);
        assertThat(testCitizen.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testCitizen.getAccNoImage()).isEqualTo(UPDATED_ACC_NO_IMAGE);
        assertThat(testCitizen.getAccNoImageContentType()).isEqualTo(UPDATED_ACC_NO_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCitizen() throws Exception {
        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();
        citizen.setId(count.incrementAndGet());

        // Create the Citizen
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citizenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCitizen() throws Exception {
        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();
        citizen.setId(count.incrementAndGet());

        // Create the Citizen
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCitizen() throws Exception {
        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();
        citizen.setId(count.incrementAndGet());

        // Create the Citizen
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitizenWithPatch() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();

        // Update the citizen using partial update
        Citizen partialUpdatedCitizen = new Citizen();
        partialUpdatedCitizen.setId(citizen.getId());

        partialUpdatedCitizen.address(UPDATED_ADDRESS).fatherName(UPDATED_FATHER_NAME).pan(UPDATED_PAN).accountNo(UPDATED_ACCOUNT_NO);

        restCitizenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizen))
            )
            .andExpect(status().isOk());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
        Citizen testCitizen = citizenList.get(citizenList.size() - 1);
        assertThat(testCitizen.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCitizen.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testCitizen.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCitizen.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCitizen.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testCitizen.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testCitizen.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testCitizen.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCitizen.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testCitizen.getSpouseName()).isEqualTo(DEFAULT_SPOUSE_NAME);
        assertThat(testCitizen.getSuccessorName()).isEqualTo(DEFAULT_SUCCESSOR_NAME);
        assertThat(testCitizen.getAadhar()).isEqualTo(DEFAULT_AADHAR);
        assertThat(testCitizen.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testCitizen.getAadharImage()).isEqualTo(DEFAULT_AADHAR_IMAGE);
        assertThat(testCitizen.getAadharImageContentType()).isEqualTo(DEFAULT_AADHAR_IMAGE_CONTENT_TYPE);
        assertThat(testCitizen.getPanImage()).isEqualTo(DEFAULT_PAN_IMAGE);
        assertThat(testCitizen.getPanImageContentType()).isEqualTo(DEFAULT_PAN_IMAGE_CONTENT_TYPE);
        assertThat(testCitizen.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testCitizen.getAccNoImage()).isEqualTo(DEFAULT_ACC_NO_IMAGE);
        assertThat(testCitizen.getAccNoImageContentType()).isEqualTo(DEFAULT_ACC_NO_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCitizenWithPatch() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();

        // Update the citizen using partial update
        Citizen partialUpdatedCitizen = new Citizen();
        partialUpdatedCitizen.setId(citizen.getId());

        partialUpdatedCitizen
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .dob(UPDATED_DOB)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .fatherName(UPDATED_FATHER_NAME)
            .spouseName(UPDATED_SPOUSE_NAME)
            .successorName(UPDATED_SUCCESSOR_NAME)
            .aadhar(UPDATED_AADHAR)
            .pan(UPDATED_PAN)
            .aadharImage(UPDATED_AADHAR_IMAGE)
            .aadharImageContentType(UPDATED_AADHAR_IMAGE_CONTENT_TYPE)
            .panImage(UPDATED_PAN_IMAGE)
            .panImageContentType(UPDATED_PAN_IMAGE_CONTENT_TYPE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .accNoImage(UPDATED_ACC_NO_IMAGE)
            .accNoImageContentType(UPDATED_ACC_NO_IMAGE_CONTENT_TYPE);

        restCitizenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizen))
            )
            .andExpect(status().isOk());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
        Citizen testCitizen = citizenList.get(citizenList.size() - 1);
        assertThat(testCitizen.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCitizen.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCitizen.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCitizen.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCitizen.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testCitizen.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testCitizen.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCitizen.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCitizen.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testCitizen.getSpouseName()).isEqualTo(UPDATED_SPOUSE_NAME);
        assertThat(testCitizen.getSuccessorName()).isEqualTo(UPDATED_SUCCESSOR_NAME);
        assertThat(testCitizen.getAadhar()).isEqualTo(UPDATED_AADHAR);
        assertThat(testCitizen.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testCitizen.getAadharImage()).isEqualTo(UPDATED_AADHAR_IMAGE);
        assertThat(testCitizen.getAadharImageContentType()).isEqualTo(UPDATED_AADHAR_IMAGE_CONTENT_TYPE);
        assertThat(testCitizen.getPanImage()).isEqualTo(UPDATED_PAN_IMAGE);
        assertThat(testCitizen.getPanImageContentType()).isEqualTo(UPDATED_PAN_IMAGE_CONTENT_TYPE);
        assertThat(testCitizen.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testCitizen.getAccNoImage()).isEqualTo(UPDATED_ACC_NO_IMAGE);
        assertThat(testCitizen.getAccNoImageContentType()).isEqualTo(UPDATED_ACC_NO_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCitizen() throws Exception {
        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();
        citizen.setId(count.incrementAndGet());

        // Create the Citizen
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citizenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCitizen() throws Exception {
        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();
        citizen.setId(count.incrementAndGet());

        // Create the Citizen
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCitizen() throws Exception {
        int databaseSizeBeforeUpdate = citizenRepository.findAll().size();
        citizen.setId(count.incrementAndGet());

        // Create the Citizen
        CitizenDTO citizenDTO = citizenMapper.toDto(citizen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(citizenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Citizen in the database
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCitizen() throws Exception {
        // Initialize the database
        citizenRepository.saveAndFlush(citizen);

        int databaseSizeBeforeDelete = citizenRepository.findAll().size();

        // Delete the citizen
        restCitizenMockMvc
            .perform(delete(ENTITY_API_URL_ID, citizen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Citizen> citizenList = citizenRepository.findAll();
        assertThat(citizenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
