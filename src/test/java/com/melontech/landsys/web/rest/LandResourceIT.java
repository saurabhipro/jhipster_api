package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Land;
import com.melontech.landsys.domain.LandType;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.Unit;
import com.melontech.landsys.domain.Village;
import com.melontech.landsys.repository.LandRepository;
import com.melontech.landsys.service.LandService;
import com.melontech.landsys.service.dto.LandDTO;
import com.melontech.landsys.service.mapper.LandMapper;
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
 * Integration tests for the {@link LandResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LandResourceIT {

    private static final String DEFAULT_ULPIN = "AAAAAAAAAA";
    private static final String UPDATED_ULPIN = "BBBBBBBBBB";

    private static final String DEFAULT_KHASRA_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_KHASRA_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_AREA = 1D;
    private static final Double UPDATED_AREA = 2D;

    private static final Double DEFAULT_LAND_MARKET_VALUE = 1D;
    private static final Double UPDATED_LAND_MARKET_VALUE = 2D;

    private static final Double DEFAULT_STRUCTURAL_VALUE = 1D;
    private static final Double UPDATED_STRUCTURAL_VALUE = 2D;

    private static final Double DEFAULT_HORTICULTURE_VALUE = 1D;
    private static final Double UPDATED_HORTICULTURE_VALUE = 2D;

    private static final Double DEFAULT_FOREST_VALUE = 1D;
    private static final Double UPDATED_FOREST_VALUE = 2D;

    private static final String DEFAULT_DISTANCE_FROM_CITY = "AAAAAAAAAA";
    private static final String UPDATED_DISTANCE_FROM_CITY = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_LAND_VALUE = 1D;
    private static final Double UPDATED_TOTAL_LAND_VALUE = 2D;

    private static final String ENTITY_API_URL = "/api/lands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LandRepository landRepository;

    @Mock
    private LandRepository landRepositoryMock;

    @Autowired
    private LandMapper landMapper;

    @Mock
    private LandService landServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLandMockMvc;

    private Land land;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Land createEntity(EntityManager em) {
        Land land = new Land()
            .ulpin(DEFAULT_ULPIN)
            .khasraNumber(DEFAULT_KHASRA_NUMBER)
            .area(DEFAULT_AREA)
            .landMarketValue(DEFAULT_LAND_MARKET_VALUE)
            .structuralValue(DEFAULT_STRUCTURAL_VALUE)
            .horticultureValue(DEFAULT_HORTICULTURE_VALUE)
            .forestValue(DEFAULT_FOREST_VALUE)
            .distanceFromCity(DEFAULT_DISTANCE_FROM_CITY)
            .totalLandValue(DEFAULT_TOTAL_LAND_VALUE);
        // Add required entity
        Village village;
        if (TestUtil.findAll(em, Village.class).isEmpty()) {
            village = VillageResourceIT.createEntity(em);
            em.persist(village);
            em.flush();
        } else {
            village = TestUtil.findAll(em, Village.class).get(0);
        }
        land.setVillage(village);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        land.setUnit(unit);
        // Add required entity
        LandType landType;
        if (TestUtil.findAll(em, LandType.class).isEmpty()) {
            landType = LandTypeResourceIT.createEntity(em);
            em.persist(landType);
            em.flush();
        } else {
            landType = TestUtil.findAll(em, LandType.class).get(0);
        }
        land.setLandType(landType);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        land.getProjectLands().add(projectLand);
        return land;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Land createUpdatedEntity(EntityManager em) {
        Land land = new Land()
            .ulpin(UPDATED_ULPIN)
            .khasraNumber(UPDATED_KHASRA_NUMBER)
            .area(UPDATED_AREA)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralValue(UPDATED_STRUCTURAL_VALUE)
            .horticultureValue(UPDATED_HORTICULTURE_VALUE)
            .forestValue(UPDATED_FOREST_VALUE)
            .distanceFromCity(UPDATED_DISTANCE_FROM_CITY)
            .totalLandValue(UPDATED_TOTAL_LAND_VALUE);
        // Add required entity
        Village village;
        if (TestUtil.findAll(em, Village.class).isEmpty()) {
            village = VillageResourceIT.createUpdatedEntity(em);
            em.persist(village);
            em.flush();
        } else {
            village = TestUtil.findAll(em, Village.class).get(0);
        }
        land.setVillage(village);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createUpdatedEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        land.setUnit(unit);
        // Add required entity
        LandType landType;
        if (TestUtil.findAll(em, LandType.class).isEmpty()) {
            landType = LandTypeResourceIT.createUpdatedEntity(em);
            em.persist(landType);
            em.flush();
        } else {
            landType = TestUtil.findAll(em, LandType.class).get(0);
        }
        land.setLandType(landType);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createUpdatedEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        land.getProjectLands().add(projectLand);
        return land;
    }

    @BeforeEach
    public void initTest() {
        land = createEntity(em);
    }

    @Test
    @Transactional
    void createLand() throws Exception {
        int databaseSizeBeforeCreate = landRepository.findAll().size();
        // Create the Land
        LandDTO landDTO = landMapper.toDto(land);
        restLandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landDTO)))
            .andExpect(status().isCreated());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeCreate + 1);
        Land testLand = landList.get(landList.size() - 1);
        assertThat(testLand.getUlpin()).isEqualTo(DEFAULT_ULPIN);
        assertThat(testLand.getKhasraNumber()).isEqualTo(DEFAULT_KHASRA_NUMBER);
        assertThat(testLand.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testLand.getLandMarketValue()).isEqualTo(DEFAULT_LAND_MARKET_VALUE);
        assertThat(testLand.getStructuralValue()).isEqualTo(DEFAULT_STRUCTURAL_VALUE);
        assertThat(testLand.getHorticultureValue()).isEqualTo(DEFAULT_HORTICULTURE_VALUE);
        assertThat(testLand.getForestValue()).isEqualTo(DEFAULT_FOREST_VALUE);
        assertThat(testLand.getDistanceFromCity()).isEqualTo(DEFAULT_DISTANCE_FROM_CITY);
        assertThat(testLand.getTotalLandValue()).isEqualTo(DEFAULT_TOTAL_LAND_VALUE);
    }

    @Test
    @Transactional
    void createLandWithExistingId() throws Exception {
        // Create the Land with an existing ID
        land.setId(1L);
        LandDTO landDTO = landMapper.toDto(land);

        int databaseSizeBeforeCreate = landRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKhasraNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = landRepository.findAll().size();
        // set the field null
        land.setKhasraNumber(null);

        // Create the Land, which fails.
        LandDTO landDTO = landMapper.toDto(land);

        restLandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landDTO)))
            .andExpect(status().isBadRequest());

        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLands() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        // Get all the landList
        restLandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(land.getId().intValue())))
            .andExpect(jsonPath("$.[*].ulpin").value(hasItem(DEFAULT_ULPIN)))
            .andExpect(jsonPath("$.[*].khasraNumber").value(hasItem(DEFAULT_KHASRA_NUMBER)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].landMarketValue").value(hasItem(DEFAULT_LAND_MARKET_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].structuralValue").value(hasItem(DEFAULT_STRUCTURAL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].horticultureValue").value(hasItem(DEFAULT_HORTICULTURE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].forestValue").value(hasItem(DEFAULT_FOREST_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].distanceFromCity").value(hasItem(DEFAULT_DISTANCE_FROM_CITY)))
            .andExpect(jsonPath("$.[*].totalLandValue").value(hasItem(DEFAULT_TOTAL_LAND_VALUE.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLandsWithEagerRelationshipsIsEnabled() throws Exception {
        when(landServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLandMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(landServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLandsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(landServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLandMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(landServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        // Get the land
        restLandMockMvc
            .perform(get(ENTITY_API_URL_ID, land.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(land.getId().intValue()))
            .andExpect(jsonPath("$.ulpin").value(DEFAULT_ULPIN))
            .andExpect(jsonPath("$.khasraNumber").value(DEFAULT_KHASRA_NUMBER))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.doubleValue()))
            .andExpect(jsonPath("$.landMarketValue").value(DEFAULT_LAND_MARKET_VALUE.doubleValue()))
            .andExpect(jsonPath("$.structuralValue").value(DEFAULT_STRUCTURAL_VALUE.doubleValue()))
            .andExpect(jsonPath("$.horticultureValue").value(DEFAULT_HORTICULTURE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.forestValue").value(DEFAULT_FOREST_VALUE.doubleValue()))
            .andExpect(jsonPath("$.distanceFromCity").value(DEFAULT_DISTANCE_FROM_CITY))
            .andExpect(jsonPath("$.totalLandValue").value(DEFAULT_TOTAL_LAND_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingLand() throws Exception {
        // Get the land
        restLandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        int databaseSizeBeforeUpdate = landRepository.findAll().size();

        // Update the land
        Land updatedLand = landRepository.findById(land.getId()).get();
        // Disconnect from session so that the updates on updatedLand are not directly saved in db
        em.detach(updatedLand);
        updatedLand
            .ulpin(UPDATED_ULPIN)
            .khasraNumber(UPDATED_KHASRA_NUMBER)
            .area(UPDATED_AREA)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralValue(UPDATED_STRUCTURAL_VALUE)
            .horticultureValue(UPDATED_HORTICULTURE_VALUE)
            .forestValue(UPDATED_FOREST_VALUE)
            .distanceFromCity(UPDATED_DISTANCE_FROM_CITY)
            .totalLandValue(UPDATED_TOTAL_LAND_VALUE);
        LandDTO landDTO = landMapper.toDto(updatedLand);

        restLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landDTO))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
        Land testLand = landList.get(landList.size() - 1);
        assertThat(testLand.getUlpin()).isEqualTo(UPDATED_ULPIN);
        assertThat(testLand.getKhasraNumber()).isEqualTo(UPDATED_KHASRA_NUMBER);
        assertThat(testLand.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLand.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testLand.getStructuralValue()).isEqualTo(UPDATED_STRUCTURAL_VALUE);
        assertThat(testLand.getHorticultureValue()).isEqualTo(UPDATED_HORTICULTURE_VALUE);
        assertThat(testLand.getForestValue()).isEqualTo(UPDATED_FOREST_VALUE);
        assertThat(testLand.getDistanceFromCity()).isEqualTo(UPDATED_DISTANCE_FROM_CITY);
        assertThat(testLand.getTotalLandValue()).isEqualTo(UPDATED_TOTAL_LAND_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // Create the Land
        LandDTO landDTO = landMapper.toDto(land);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // Create the Land
        LandDTO landDTO = landMapper.toDto(land);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // Create the Land
        LandDTO landDTO = landMapper.toDto(land);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLandWithPatch() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        int databaseSizeBeforeUpdate = landRepository.findAll().size();

        // Update the land using partial update
        Land partialUpdatedLand = new Land();
        partialUpdatedLand.setId(land.getId());

        partialUpdatedLand
            .area(UPDATED_AREA)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralValue(UPDATED_STRUCTURAL_VALUE)
            .horticultureValue(UPDATED_HORTICULTURE_VALUE)
            .forestValue(UPDATED_FOREST_VALUE)
            .distanceFromCity(UPDATED_DISTANCE_FROM_CITY)
            .totalLandValue(UPDATED_TOTAL_LAND_VALUE);

        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLand))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
        Land testLand = landList.get(landList.size() - 1);
        assertThat(testLand.getUlpin()).isEqualTo(DEFAULT_ULPIN);
        assertThat(testLand.getKhasraNumber()).isEqualTo(DEFAULT_KHASRA_NUMBER);
        assertThat(testLand.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLand.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testLand.getStructuralValue()).isEqualTo(UPDATED_STRUCTURAL_VALUE);
        assertThat(testLand.getHorticultureValue()).isEqualTo(UPDATED_HORTICULTURE_VALUE);
        assertThat(testLand.getForestValue()).isEqualTo(UPDATED_FOREST_VALUE);
        assertThat(testLand.getDistanceFromCity()).isEqualTo(UPDATED_DISTANCE_FROM_CITY);
        assertThat(testLand.getTotalLandValue()).isEqualTo(UPDATED_TOTAL_LAND_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateLandWithPatch() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        int databaseSizeBeforeUpdate = landRepository.findAll().size();

        // Update the land using partial update
        Land partialUpdatedLand = new Land();
        partialUpdatedLand.setId(land.getId());

        partialUpdatedLand
            .ulpin(UPDATED_ULPIN)
            .khasraNumber(UPDATED_KHASRA_NUMBER)
            .area(UPDATED_AREA)
            .landMarketValue(UPDATED_LAND_MARKET_VALUE)
            .structuralValue(UPDATED_STRUCTURAL_VALUE)
            .horticultureValue(UPDATED_HORTICULTURE_VALUE)
            .forestValue(UPDATED_FOREST_VALUE)
            .distanceFromCity(UPDATED_DISTANCE_FROM_CITY)
            .totalLandValue(UPDATED_TOTAL_LAND_VALUE);

        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLand))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
        Land testLand = landList.get(landList.size() - 1);
        assertThat(testLand.getUlpin()).isEqualTo(UPDATED_ULPIN);
        assertThat(testLand.getKhasraNumber()).isEqualTo(UPDATED_KHASRA_NUMBER);
        assertThat(testLand.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLand.getLandMarketValue()).isEqualTo(UPDATED_LAND_MARKET_VALUE);
        assertThat(testLand.getStructuralValue()).isEqualTo(UPDATED_STRUCTURAL_VALUE);
        assertThat(testLand.getHorticultureValue()).isEqualTo(UPDATED_HORTICULTURE_VALUE);
        assertThat(testLand.getForestValue()).isEqualTo(UPDATED_FOREST_VALUE);
        assertThat(testLand.getDistanceFromCity()).isEqualTo(UPDATED_DISTANCE_FROM_CITY);
        assertThat(testLand.getTotalLandValue()).isEqualTo(UPDATED_TOTAL_LAND_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // Create the Land
        LandDTO landDTO = landMapper.toDto(land);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, landDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // Create the Land
        LandDTO landDTO = landMapper.toDto(land);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // Create the Land
        LandDTO landDTO = landMapper.toDto(land);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(landDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        int databaseSizeBeforeDelete = landRepository.findAll().size();

        // Delete the land
        restLandMockMvc
            .perform(delete(ENTITY_API_URL_ID, land.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
