package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.LandType;
import com.melontech.landsys.repository.LandTypeRepository;
import com.melontech.landsys.service.dto.LandTypeDTO;
import com.melontech.landsys.service.mapper.LandTypeMapper;
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
 * Integration tests for the {@link LandTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LandTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/land-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LandTypeRepository landTypeRepository;

    @Autowired
    private LandTypeMapper landTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLandTypeMockMvc;

    private LandType landType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandType createEntity(EntityManager em) {
        LandType landType = new LandType().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return landType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandType createUpdatedEntity(EntityManager em) {
        LandType landType = new LandType().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return landType;
    }

    @BeforeEach
    public void initTest() {
        landType = createEntity(em);
    }

    @Test
    @Transactional
    void createLandType() throws Exception {
        int databaseSizeBeforeCreate = landTypeRepository.findAll().size();
        // Create the LandType
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);
        restLandTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LandType testLandType = landTypeList.get(landTypeList.size() - 1);
        assertThat(testLandType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLandType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createLandTypeWithExistingId() throws Exception {
        // Create the LandType with an existing ID
        landType.setId(1L);
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        int databaseSizeBeforeCreate = landTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = landTypeRepository.findAll().size();
        // set the field null
        landType.setName(null);

        // Create the LandType, which fails.
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        restLandTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = landTypeRepository.findAll().size();
        // set the field null
        landType.setDescription(null);

        // Create the LandType, which fails.
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        restLandTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLandTypes() throws Exception {
        // Initialize the database
        landTypeRepository.saveAndFlush(landType);

        // Get all the landTypeList
        restLandTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getLandType() throws Exception {
        // Initialize the database
        landTypeRepository.saveAndFlush(landType);

        // Get the landType
        restLandTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, landType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(landType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingLandType() throws Exception {
        // Get the landType
        restLandTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLandType() throws Exception {
        // Initialize the database
        landTypeRepository.saveAndFlush(landType);

        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();

        // Update the landType
        LandType updatedLandType = landTypeRepository.findById(landType.getId()).get();
        // Disconnect from session so that the updates on updatedLandType are not directly saved in db
        em.detach(updatedLandType);
        updatedLandType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(updatedLandType);

        restLandTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
        LandType testLandType = landTypeList.get(landTypeList.size() - 1);
        assertThat(testLandType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLandType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingLandType() throws Exception {
        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();
        landType.setId(count.incrementAndGet());

        // Create the LandType
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLandType() throws Exception {
        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();
        landType.setId(count.incrementAndGet());

        // Create the LandType
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLandType() throws Exception {
        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();
        landType.setId(count.incrementAndGet());

        // Create the LandType
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLandTypeWithPatch() throws Exception {
        // Initialize the database
        landTypeRepository.saveAndFlush(landType);

        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();

        // Update the landType using partial update
        LandType partialUpdatedLandType = new LandType();
        partialUpdatedLandType.setId(landType.getId());

        partialUpdatedLandType.name(UPDATED_NAME);

        restLandTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLandType))
            )
            .andExpect(status().isOk());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
        LandType testLandType = landTypeList.get(landTypeList.size() - 1);
        assertThat(testLandType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLandType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateLandTypeWithPatch() throws Exception {
        // Initialize the database
        landTypeRepository.saveAndFlush(landType);

        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();

        // Update the landType using partial update
        LandType partialUpdatedLandType = new LandType();
        partialUpdatedLandType.setId(landType.getId());

        partialUpdatedLandType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restLandTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLandType))
            )
            .andExpect(status().isOk());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
        LandType testLandType = landTypeList.get(landTypeList.size() - 1);
        assertThat(testLandType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLandType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingLandType() throws Exception {
        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();
        landType.setId(count.incrementAndGet());

        // Create the LandType
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, landTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLandType() throws Exception {
        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();
        landType.setId(count.incrementAndGet());

        // Create the LandType
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLandType() throws Exception {
        int databaseSizeBeforeUpdate = landTypeRepository.findAll().size();
        landType.setId(count.incrementAndGet());

        // Create the LandType
        LandTypeDTO landTypeDTO = landTypeMapper.toDto(landType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(landTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandType in the database
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLandType() throws Exception {
        // Initialize the database
        landTypeRepository.saveAndFlush(landType);

        int databaseSizeBeforeDelete = landTypeRepository.findAll().size();

        // Delete the landType
        restLandTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, landType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LandType> landTypeList = landTypeRepository.findAll();
        assertThat(landTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
