package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.District;
import com.melontech.landsys.domain.SubDistrict;
import com.melontech.landsys.domain.Village;
import com.melontech.landsys.repository.SubDistrictRepository;
import com.melontech.landsys.service.SubDistrictService;
import com.melontech.landsys.service.dto.SubDistrictDTO;
import com.melontech.landsys.service.mapper.SubDistrictMapper;
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
 * Integration tests for the {@link SubDistrictResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SubDistrictResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-districts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubDistrictRepository subDistrictRepository;

    @Mock
    private SubDistrictRepository subDistrictRepositoryMock;

    @Autowired
    private SubDistrictMapper subDistrictMapper;

    @Mock
    private SubDistrictService subDistrictServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubDistrictMockMvc;

    private SubDistrict subDistrict;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubDistrict createEntity(EntityManager em) {
        SubDistrict subDistrict = new SubDistrict().name(DEFAULT_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        subDistrict.setDistrict(district);
        // Add required entity
        Village village;
        if (TestUtil.findAll(em, Village.class).isEmpty()) {
            village = VillageResourceIT.createEntity(em);
            em.persist(village);
            em.flush();
        } else {
            village = TestUtil.findAll(em, Village.class).get(0);
        }
        subDistrict.getVillages().add(village);
        return subDistrict;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubDistrict createUpdatedEntity(EntityManager em) {
        SubDistrict subDistrict = new SubDistrict().name(UPDATED_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        subDistrict.setDistrict(district);
        // Add required entity
        Village village;
        if (TestUtil.findAll(em, Village.class).isEmpty()) {
            village = VillageResourceIT.createUpdatedEntity(em);
            em.persist(village);
            em.flush();
        } else {
            village = TestUtil.findAll(em, Village.class).get(0);
        }
        subDistrict.getVillages().add(village);
        return subDistrict;
    }

    @BeforeEach
    public void initTest() {
        subDistrict = createEntity(em);
    }

    @Test
    @Transactional
    void createSubDistrict() throws Exception {
        int databaseSizeBeforeCreate = subDistrictRepository.findAll().size();
        // Create the SubDistrict
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);
        restSubDistrictMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeCreate + 1);
        SubDistrict testSubDistrict = subDistrictList.get(subDistrictList.size() - 1);
        assertThat(testSubDistrict.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSubDistrictWithExistingId() throws Exception {
        // Create the SubDistrict with an existing ID
        subDistrict.setId(1L);
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);

        int databaseSizeBeforeCreate = subDistrictRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubDistrictMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subDistrictRepository.findAll().size();
        // set the field null
        subDistrict.setName(null);

        // Create the SubDistrict, which fails.
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);

        restSubDistrictMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isBadRequest());

        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubDistricts() throws Exception {
        // Initialize the database
        subDistrictRepository.saveAndFlush(subDistrict);

        // Get all the subDistrictList
        restSubDistrictMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subDistrict.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubDistrictsWithEagerRelationshipsIsEnabled() throws Exception {
        when(subDistrictServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubDistrictMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(subDistrictServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubDistrictsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(subDistrictServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubDistrictMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(subDistrictServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSubDistrict() throws Exception {
        // Initialize the database
        subDistrictRepository.saveAndFlush(subDistrict);

        // Get the subDistrict
        restSubDistrictMockMvc
            .perform(get(ENTITY_API_URL_ID, subDistrict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subDistrict.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSubDistrict() throws Exception {
        // Get the subDistrict
        restSubDistrictMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubDistrict() throws Exception {
        // Initialize the database
        subDistrictRepository.saveAndFlush(subDistrict);

        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();

        // Update the subDistrict
        SubDistrict updatedSubDistrict = subDistrictRepository.findById(subDistrict.getId()).get();
        // Disconnect from session so that the updates on updatedSubDistrict are not directly saved in db
        em.detach(updatedSubDistrict);
        updatedSubDistrict.name(UPDATED_NAME);
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(updatedSubDistrict);

        restSubDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subDistrictDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
        SubDistrict testSubDistrict = subDistrictList.get(subDistrictList.size() - 1);
        assertThat(testSubDistrict.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSubDistrict() throws Exception {
        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();
        subDistrict.setId(count.incrementAndGet());

        // Create the SubDistrict
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subDistrictDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubDistrict() throws Exception {
        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();
        subDistrict.setId(count.incrementAndGet());

        // Create the SubDistrict
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubDistrict() throws Exception {
        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();
        subDistrict.setId(count.incrementAndGet());

        // Create the SubDistrict
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubDistrictMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDistrictDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubDistrictWithPatch() throws Exception {
        // Initialize the database
        subDistrictRepository.saveAndFlush(subDistrict);

        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();

        // Update the subDistrict using partial update
        SubDistrict partialUpdatedSubDistrict = new SubDistrict();
        partialUpdatedSubDistrict.setId(subDistrict.getId());

        partialUpdatedSubDistrict.name(UPDATED_NAME);

        restSubDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubDistrict.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubDistrict))
            )
            .andExpect(status().isOk());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
        SubDistrict testSubDistrict = subDistrictList.get(subDistrictList.size() - 1);
        assertThat(testSubDistrict.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSubDistrictWithPatch() throws Exception {
        // Initialize the database
        subDistrictRepository.saveAndFlush(subDistrict);

        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();

        // Update the subDistrict using partial update
        SubDistrict partialUpdatedSubDistrict = new SubDistrict();
        partialUpdatedSubDistrict.setId(subDistrict.getId());

        partialUpdatedSubDistrict.name(UPDATED_NAME);

        restSubDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubDistrict.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubDistrict))
            )
            .andExpect(status().isOk());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
        SubDistrict testSubDistrict = subDistrictList.get(subDistrictList.size() - 1);
        assertThat(testSubDistrict.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSubDistrict() throws Exception {
        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();
        subDistrict.setId(count.incrementAndGet());

        // Create the SubDistrict
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subDistrictDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubDistrict() throws Exception {
        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();
        subDistrict.setId(count.incrementAndGet());

        // Create the SubDistrict
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubDistrict() throws Exception {
        int databaseSizeBeforeUpdate = subDistrictRepository.findAll().size();
        subDistrict.setId(count.incrementAndGet());

        // Create the SubDistrict
        SubDistrictDTO subDistrictDTO = subDistrictMapper.toDto(subDistrict);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subDistrictDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubDistrict in the database
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubDistrict() throws Exception {
        // Initialize the database
        subDistrictRepository.saveAndFlush(subDistrict);

        int databaseSizeBeforeDelete = subDistrictRepository.findAll().size();

        // Delete the subDistrict
        restSubDistrictMockMvc
            .perform(delete(ENTITY_API_URL_ID, subDistrict.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubDistrict> subDistrictList = subDistrictRepository.findAll();
        assertThat(subDistrictList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
