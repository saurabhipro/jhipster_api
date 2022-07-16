package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.SequenceGen;
import com.melontech.landsys.domain.enumeration.SequenceType;
import com.melontech.landsys.repository.SequenceGenRepository;
import com.melontech.landsys.service.criteria.SequenceGenCriteria;
import com.melontech.landsys.service.dto.SequenceGenDTO;
import com.melontech.landsys.service.mapper.SequenceGenMapper;
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
 * Integration tests for the {@link SequenceGenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SequenceGenResourceIT {

    private static final SequenceType DEFAULT_SEQ_TYPE = SequenceType.SURVEY;
    private static final SequenceType UPDATED_SEQ_TYPE = SequenceType.COMPENSATION;

    private static final Integer DEFAULT_LATEST_SEQUENCE = 1;
    private static final Integer UPDATED_LATEST_SEQUENCE = 2;
    private static final Integer SMALLER_LATEST_SEQUENCE = 1 - 1;

    private static final String DEFAULT_SEQUENCE_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_SEQUENCE_SUFFIX = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sequence-gens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SequenceGenRepository sequenceGenRepository;

    @Autowired
    private SequenceGenMapper sequenceGenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequenceGenMockMvc;

    private SequenceGen sequenceGen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceGen createEntity(EntityManager em) {
        SequenceGen sequenceGen = new SequenceGen()
            .seqType(DEFAULT_SEQ_TYPE)
            .latestSequence(DEFAULT_LATEST_SEQUENCE)
            .sequenceSuffix(DEFAULT_SEQUENCE_SUFFIX);
        return sequenceGen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceGen createUpdatedEntity(EntityManager em) {
        SequenceGen sequenceGen = new SequenceGen()
            .seqType(UPDATED_SEQ_TYPE)
            .latestSequence(UPDATED_LATEST_SEQUENCE)
            .sequenceSuffix(UPDATED_SEQUENCE_SUFFIX);
        return sequenceGen;
    }

    @BeforeEach
    public void initTest() {
        sequenceGen = createEntity(em);
    }

    @Test
    @Transactional
    void createSequenceGen() throws Exception {
        int databaseSizeBeforeCreate = sequenceGenRepository.findAll().size();
        // Create the SequenceGen
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);
        restSequenceGenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeCreate + 1);
        SequenceGen testSequenceGen = sequenceGenList.get(sequenceGenList.size() - 1);
        assertThat(testSequenceGen.getSeqType()).isEqualTo(DEFAULT_SEQ_TYPE);
        assertThat(testSequenceGen.getLatestSequence()).isEqualTo(DEFAULT_LATEST_SEQUENCE);
        assertThat(testSequenceGen.getSequenceSuffix()).isEqualTo(DEFAULT_SEQUENCE_SUFFIX);
    }

    @Test
    @Transactional
    void createSequenceGenWithExistingId() throws Exception {
        // Create the SequenceGen with an existing ID
        sequenceGen.setId(1L);
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        int databaseSizeBeforeCreate = sequenceGenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenceGenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSeqTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceGenRepository.findAll().size();
        // set the field null
        sequenceGen.setSeqType(null);

        // Create the SequenceGen, which fails.
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        restSequenceGenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isBadRequest());

        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatestSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceGenRepository.findAll().size();
        // set the field null
        sequenceGen.setLatestSequence(null);

        // Create the SequenceGen, which fails.
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        restSequenceGenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isBadRequest());

        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSequenceSuffixIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceGenRepository.findAll().size();
        // set the field null
        sequenceGen.setSequenceSuffix(null);

        // Create the SequenceGen, which fails.
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        restSequenceGenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isBadRequest());

        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSequenceGens() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList
        restSequenceGenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceGen.getId().intValue())))
            .andExpect(jsonPath("$.[*].seqType").value(hasItem(DEFAULT_SEQ_TYPE.toString())))
            .andExpect(jsonPath("$.[*].latestSequence").value(hasItem(DEFAULT_LATEST_SEQUENCE)))
            .andExpect(jsonPath("$.[*].sequenceSuffix").value(hasItem(DEFAULT_SEQUENCE_SUFFIX)));
    }

    @Test
    @Transactional
    void getSequenceGen() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get the sequenceGen
        restSequenceGenMockMvc
            .perform(get(ENTITY_API_URL_ID, sequenceGen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequenceGen.getId().intValue()))
            .andExpect(jsonPath("$.seqType").value(DEFAULT_SEQ_TYPE.toString()))
            .andExpect(jsonPath("$.latestSequence").value(DEFAULT_LATEST_SEQUENCE))
            .andExpect(jsonPath("$.sequenceSuffix").value(DEFAULT_SEQUENCE_SUFFIX));
    }

    @Test
    @Transactional
    void getSequenceGensByIdFiltering() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        Long id = sequenceGen.getId();

        defaultSequenceGenShouldBeFound("id.equals=" + id);
        defaultSequenceGenShouldNotBeFound("id.notEquals=" + id);

        defaultSequenceGenShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequenceGenShouldNotBeFound("id.greaterThan=" + id);

        defaultSequenceGenShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequenceGenShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySeqTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where seqType equals to DEFAULT_SEQ_TYPE
        defaultSequenceGenShouldBeFound("seqType.equals=" + DEFAULT_SEQ_TYPE);

        // Get all the sequenceGenList where seqType equals to UPDATED_SEQ_TYPE
        defaultSequenceGenShouldNotBeFound("seqType.equals=" + UPDATED_SEQ_TYPE);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySeqTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where seqType not equals to DEFAULT_SEQ_TYPE
        defaultSequenceGenShouldNotBeFound("seqType.notEquals=" + DEFAULT_SEQ_TYPE);

        // Get all the sequenceGenList where seqType not equals to UPDATED_SEQ_TYPE
        defaultSequenceGenShouldBeFound("seqType.notEquals=" + UPDATED_SEQ_TYPE);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySeqTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where seqType in DEFAULT_SEQ_TYPE or UPDATED_SEQ_TYPE
        defaultSequenceGenShouldBeFound("seqType.in=" + DEFAULT_SEQ_TYPE + "," + UPDATED_SEQ_TYPE);

        // Get all the sequenceGenList where seqType equals to UPDATED_SEQ_TYPE
        defaultSequenceGenShouldNotBeFound("seqType.in=" + UPDATED_SEQ_TYPE);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySeqTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where seqType is not null
        defaultSequenceGenShouldBeFound("seqType.specified=true");

        // Get all the sequenceGenList where seqType is null
        defaultSequenceGenShouldNotBeFound("seqType.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGensByLatestSequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where latestSequence equals to DEFAULT_LATEST_SEQUENCE
        defaultSequenceGenShouldBeFound("latestSequence.equals=" + DEFAULT_LATEST_SEQUENCE);

        // Get all the sequenceGenList where latestSequence equals to UPDATED_LATEST_SEQUENCE
        defaultSequenceGenShouldNotBeFound("latestSequence.equals=" + UPDATED_LATEST_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllSequenceGensByLatestSequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where latestSequence not equals to DEFAULT_LATEST_SEQUENCE
        defaultSequenceGenShouldNotBeFound("latestSequence.notEquals=" + DEFAULT_LATEST_SEQUENCE);

        // Get all the sequenceGenList where latestSequence not equals to UPDATED_LATEST_SEQUENCE
        defaultSequenceGenShouldBeFound("latestSequence.notEquals=" + UPDATED_LATEST_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllSequenceGensByLatestSequenceIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where latestSequence in DEFAULT_LATEST_SEQUENCE or UPDATED_LATEST_SEQUENCE
        defaultSequenceGenShouldBeFound("latestSequence.in=" + DEFAULT_LATEST_SEQUENCE + "," + UPDATED_LATEST_SEQUENCE);

        // Get all the sequenceGenList where latestSequence equals to UPDATED_LATEST_SEQUENCE
        defaultSequenceGenShouldNotBeFound("latestSequence.in=" + UPDATED_LATEST_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllSequenceGensByLatestSequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where latestSequence is not null
        defaultSequenceGenShouldBeFound("latestSequence.specified=true");

        // Get all the sequenceGenList where latestSequence is null
        defaultSequenceGenShouldNotBeFound("latestSequence.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGensByLatestSequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where latestSequence is greater than or equal to DEFAULT_LATEST_SEQUENCE
        defaultSequenceGenShouldBeFound("latestSequence.greaterThanOrEqual=" + DEFAULT_LATEST_SEQUENCE);

        // Get all the sequenceGenList where latestSequence is greater than or equal to UPDATED_LATEST_SEQUENCE
        defaultSequenceGenShouldNotBeFound("latestSequence.greaterThanOrEqual=" + UPDATED_LATEST_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllSequenceGensByLatestSequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where latestSequence is less than or equal to DEFAULT_LATEST_SEQUENCE
        defaultSequenceGenShouldBeFound("latestSequence.lessThanOrEqual=" + DEFAULT_LATEST_SEQUENCE);

        // Get all the sequenceGenList where latestSequence is less than or equal to SMALLER_LATEST_SEQUENCE
        defaultSequenceGenShouldNotBeFound("latestSequence.lessThanOrEqual=" + SMALLER_LATEST_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllSequenceGensByLatestSequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where latestSequence is less than DEFAULT_LATEST_SEQUENCE
        defaultSequenceGenShouldNotBeFound("latestSequence.lessThan=" + DEFAULT_LATEST_SEQUENCE);

        // Get all the sequenceGenList where latestSequence is less than UPDATED_LATEST_SEQUENCE
        defaultSequenceGenShouldBeFound("latestSequence.lessThan=" + UPDATED_LATEST_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllSequenceGensByLatestSequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where latestSequence is greater than DEFAULT_LATEST_SEQUENCE
        defaultSequenceGenShouldNotBeFound("latestSequence.greaterThan=" + DEFAULT_LATEST_SEQUENCE);

        // Get all the sequenceGenList where latestSequence is greater than SMALLER_LATEST_SEQUENCE
        defaultSequenceGenShouldBeFound("latestSequence.greaterThan=" + SMALLER_LATEST_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySequenceSuffixIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where sequenceSuffix equals to DEFAULT_SEQUENCE_SUFFIX
        defaultSequenceGenShouldBeFound("sequenceSuffix.equals=" + DEFAULT_SEQUENCE_SUFFIX);

        // Get all the sequenceGenList where sequenceSuffix equals to UPDATED_SEQUENCE_SUFFIX
        defaultSequenceGenShouldNotBeFound("sequenceSuffix.equals=" + UPDATED_SEQUENCE_SUFFIX);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySequenceSuffixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where sequenceSuffix not equals to DEFAULT_SEQUENCE_SUFFIX
        defaultSequenceGenShouldNotBeFound("sequenceSuffix.notEquals=" + DEFAULT_SEQUENCE_SUFFIX);

        // Get all the sequenceGenList where sequenceSuffix not equals to UPDATED_SEQUENCE_SUFFIX
        defaultSequenceGenShouldBeFound("sequenceSuffix.notEquals=" + UPDATED_SEQUENCE_SUFFIX);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySequenceSuffixIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where sequenceSuffix in DEFAULT_SEQUENCE_SUFFIX or UPDATED_SEQUENCE_SUFFIX
        defaultSequenceGenShouldBeFound("sequenceSuffix.in=" + DEFAULT_SEQUENCE_SUFFIX + "," + UPDATED_SEQUENCE_SUFFIX);

        // Get all the sequenceGenList where sequenceSuffix equals to UPDATED_SEQUENCE_SUFFIX
        defaultSequenceGenShouldNotBeFound("sequenceSuffix.in=" + UPDATED_SEQUENCE_SUFFIX);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySequenceSuffixIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where sequenceSuffix is not null
        defaultSequenceGenShouldBeFound("sequenceSuffix.specified=true");

        // Get all the sequenceGenList where sequenceSuffix is null
        defaultSequenceGenShouldNotBeFound("sequenceSuffix.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGensBySequenceSuffixContainsSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where sequenceSuffix contains DEFAULT_SEQUENCE_SUFFIX
        defaultSequenceGenShouldBeFound("sequenceSuffix.contains=" + DEFAULT_SEQUENCE_SUFFIX);

        // Get all the sequenceGenList where sequenceSuffix contains UPDATED_SEQUENCE_SUFFIX
        defaultSequenceGenShouldNotBeFound("sequenceSuffix.contains=" + UPDATED_SEQUENCE_SUFFIX);
    }

    @Test
    @Transactional
    void getAllSequenceGensBySequenceSuffixNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        // Get all the sequenceGenList where sequenceSuffix does not contain DEFAULT_SEQUENCE_SUFFIX
        defaultSequenceGenShouldNotBeFound("sequenceSuffix.doesNotContain=" + DEFAULT_SEQUENCE_SUFFIX);

        // Get all the sequenceGenList where sequenceSuffix does not contain UPDATED_SEQUENCE_SUFFIX
        defaultSequenceGenShouldBeFound("sequenceSuffix.doesNotContain=" + UPDATED_SEQUENCE_SUFFIX);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequenceGenShouldBeFound(String filter) throws Exception {
        restSequenceGenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceGen.getId().intValue())))
            .andExpect(jsonPath("$.[*].seqType").value(hasItem(DEFAULT_SEQ_TYPE.toString())))
            .andExpect(jsonPath("$.[*].latestSequence").value(hasItem(DEFAULT_LATEST_SEQUENCE)))
            .andExpect(jsonPath("$.[*].sequenceSuffix").value(hasItem(DEFAULT_SEQUENCE_SUFFIX)));

        // Check, that the count call also returns 1
        restSequenceGenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequenceGenShouldNotBeFound(String filter) throws Exception {
        restSequenceGenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequenceGenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSequenceGen() throws Exception {
        // Get the sequenceGen
        restSequenceGenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSequenceGen() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();

        // Update the sequenceGen
        SequenceGen updatedSequenceGen = sequenceGenRepository.findById(sequenceGen.getId()).get();
        // Disconnect from session so that the updates on updatedSequenceGen are not directly saved in db
        em.detach(updatedSequenceGen);
        updatedSequenceGen.seqType(UPDATED_SEQ_TYPE).latestSequence(UPDATED_LATEST_SEQUENCE).sequenceSuffix(UPDATED_SEQUENCE_SUFFIX);
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(updatedSequenceGen);

        restSequenceGenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequenceGenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isOk());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
        SequenceGen testSequenceGen = sequenceGenList.get(sequenceGenList.size() - 1);
        assertThat(testSequenceGen.getSeqType()).isEqualTo(UPDATED_SEQ_TYPE);
        assertThat(testSequenceGen.getLatestSequence()).isEqualTo(UPDATED_LATEST_SEQUENCE);
        assertThat(testSequenceGen.getSequenceSuffix()).isEqualTo(UPDATED_SEQUENCE_SUFFIX);
    }

    @Test
    @Transactional
    void putNonExistingSequenceGen() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();
        sequenceGen.setId(count.incrementAndGet());

        // Create the SequenceGen
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceGenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequenceGenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSequenceGen() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();
        sequenceGen.setId(count.incrementAndGet());

        // Create the SequenceGen
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceGenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSequenceGen() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();
        sequenceGen.setId(count.incrementAndGet());

        // Create the SequenceGen
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceGenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSequenceGenWithPatch() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();

        // Update the sequenceGen using partial update
        SequenceGen partialUpdatedSequenceGen = new SequenceGen();
        partialUpdatedSequenceGen.setId(sequenceGen.getId());

        partialUpdatedSequenceGen.sequenceSuffix(UPDATED_SEQUENCE_SUFFIX);

        restSequenceGenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequenceGen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequenceGen))
            )
            .andExpect(status().isOk());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
        SequenceGen testSequenceGen = sequenceGenList.get(sequenceGenList.size() - 1);
        assertThat(testSequenceGen.getSeqType()).isEqualTo(DEFAULT_SEQ_TYPE);
        assertThat(testSequenceGen.getLatestSequence()).isEqualTo(DEFAULT_LATEST_SEQUENCE);
        assertThat(testSequenceGen.getSequenceSuffix()).isEqualTo(UPDATED_SEQUENCE_SUFFIX);
    }

    @Test
    @Transactional
    void fullUpdateSequenceGenWithPatch() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();

        // Update the sequenceGen using partial update
        SequenceGen partialUpdatedSequenceGen = new SequenceGen();
        partialUpdatedSequenceGen.setId(sequenceGen.getId());

        partialUpdatedSequenceGen.seqType(UPDATED_SEQ_TYPE).latestSequence(UPDATED_LATEST_SEQUENCE).sequenceSuffix(UPDATED_SEQUENCE_SUFFIX);

        restSequenceGenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequenceGen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequenceGen))
            )
            .andExpect(status().isOk());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
        SequenceGen testSequenceGen = sequenceGenList.get(sequenceGenList.size() - 1);
        assertThat(testSequenceGen.getSeqType()).isEqualTo(UPDATED_SEQ_TYPE);
        assertThat(testSequenceGen.getLatestSequence()).isEqualTo(UPDATED_LATEST_SEQUENCE);
        assertThat(testSequenceGen.getSequenceSuffix()).isEqualTo(UPDATED_SEQUENCE_SUFFIX);
    }

    @Test
    @Transactional
    void patchNonExistingSequenceGen() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();
        sequenceGen.setId(count.incrementAndGet());

        // Create the SequenceGen
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceGenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sequenceGenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSequenceGen() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();
        sequenceGen.setId(count.incrementAndGet());

        // Create the SequenceGen
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceGenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSequenceGen() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGenRepository.findAll().size();
        sequenceGen.setId(count.incrementAndGet());

        // Create the SequenceGen
        SequenceGenDTO sequenceGenDTO = sequenceGenMapper.toDto(sequenceGen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceGenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sequenceGenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SequenceGen in the database
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSequenceGen() throws Exception {
        // Initialize the database
        sequenceGenRepository.saveAndFlush(sequenceGen);

        int databaseSizeBeforeDelete = sequenceGenRepository.findAll().size();

        // Delete the sequenceGen
        restSequenceGenMockMvc
            .perform(delete(ENTITY_API_URL_ID, sequenceGen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SequenceGen> sequenceGenList = sequenceGenRepository.findAll();
        assertThat(sequenceGenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
