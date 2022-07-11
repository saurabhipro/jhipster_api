package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.ProjectStatusHistory;
import com.melontech.landsys.domain.enumeration.ProjectStatus;
import com.melontech.landsys.repository.ProjectStatusHistoryRepository;
import com.melontech.landsys.service.dto.ProjectStatusHistoryDTO;
import com.melontech.landsys.service.mapper.ProjectStatusHistoryMapper;
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
 * Integration tests for the {@link ProjectStatusHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectStatusHistoryResourceIT {

    private static final ProjectStatus DEFAULT_STATUS = ProjectStatus.NEW;
    private static final ProjectStatus UPDATED_STATUS = ProjectStatus.INPROGRESS;

    private static final LocalDate DEFAULT_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WHEN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-status-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectStatusHistoryRepository projectStatusHistoryRepository;

    @Autowired
    private ProjectStatusHistoryMapper projectStatusHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectStatusHistoryMockMvc;

    private ProjectStatusHistory projectStatusHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectStatusHistory createEntity(EntityManager em) {
        ProjectStatusHistory projectStatusHistory = new ProjectStatusHistory()
            .status(DEFAULT_STATUS)
            .when(DEFAULT_WHEN)
            .remarks(DEFAULT_REMARKS);
        return projectStatusHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectStatusHistory createUpdatedEntity(EntityManager em) {
        ProjectStatusHistory projectStatusHistory = new ProjectStatusHistory()
            .status(UPDATED_STATUS)
            .when(UPDATED_WHEN)
            .remarks(UPDATED_REMARKS);
        return projectStatusHistory;
    }

    @BeforeEach
    public void initTest() {
        projectStatusHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectStatusHistory() throws Exception {
        int databaseSizeBeforeCreate = projectStatusHistoryRepository.findAll().size();
        // Create the ProjectStatusHistory
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(projectStatusHistory);
        restProjectStatusHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectStatusHistory testProjectStatusHistory = projectStatusHistoryList.get(projectStatusHistoryList.size() - 1);
        assertThat(testProjectStatusHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjectStatusHistory.getWhen()).isEqualTo(DEFAULT_WHEN);
        assertThat(testProjectStatusHistory.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    void createProjectStatusHistoryWithExistingId() throws Exception {
        // Create the ProjectStatusHistory with an existing ID
        projectStatusHistory.setId(1L);
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(projectStatusHistory);

        int databaseSizeBeforeCreate = projectStatusHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectStatusHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectStatusHistories() throws Exception {
        // Initialize the database
        projectStatusHistoryRepository.saveAndFlush(projectStatusHistory);

        // Get all the projectStatusHistoryList
        restProjectStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].when").value(hasItem(DEFAULT_WHEN.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    void getProjectStatusHistory() throws Exception {
        // Initialize the database
        projectStatusHistoryRepository.saveAndFlush(projectStatusHistory);

        // Get the projectStatusHistory
        restProjectStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, projectStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.when").value(DEFAULT_WHEN.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }

    @Test
    @Transactional
    void getNonExistingProjectStatusHistory() throws Exception {
        // Get the projectStatusHistory
        restProjectStatusHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectStatusHistory() throws Exception {
        // Initialize the database
        projectStatusHistoryRepository.saveAndFlush(projectStatusHistory);

        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();

        // Update the projectStatusHistory
        ProjectStatusHistory updatedProjectStatusHistory = projectStatusHistoryRepository.findById(projectStatusHistory.getId()).get();
        // Disconnect from session so that the updates on updatedProjectStatusHistory are not directly saved in db
        em.detach(updatedProjectStatusHistory);
        updatedProjectStatusHistory.status(UPDATED_STATUS).when(UPDATED_WHEN).remarks(UPDATED_REMARKS);
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(updatedProjectStatusHistory);

        restProjectStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectStatusHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProjectStatusHistory testProjectStatusHistory = projectStatusHistoryList.get(projectStatusHistoryList.size() - 1);
        assertThat(testProjectStatusHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjectStatusHistory.getWhen()).isEqualTo(UPDATED_WHEN);
        assertThat(testProjectStatusHistory.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void putNonExistingProjectStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();
        projectStatusHistory.setId(count.incrementAndGet());

        // Create the ProjectStatusHistory
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(projectStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectStatusHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();
        projectStatusHistory.setId(count.incrementAndGet());

        // Create the ProjectStatusHistory
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(projectStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();
        projectStatusHistory.setId(count.incrementAndGet());

        // Create the ProjectStatusHistory
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(projectStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        projectStatusHistoryRepository.saveAndFlush(projectStatusHistory);

        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();

        // Update the projectStatusHistory using partial update
        ProjectStatusHistory partialUpdatedProjectStatusHistory = new ProjectStatusHistory();
        partialUpdatedProjectStatusHistory.setId(projectStatusHistory.getId());

        partialUpdatedProjectStatusHistory.status(UPDATED_STATUS).when(UPDATED_WHEN);

        restProjectStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProjectStatusHistory testProjectStatusHistory = projectStatusHistoryList.get(projectStatusHistoryList.size() - 1);
        assertThat(testProjectStatusHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjectStatusHistory.getWhen()).isEqualTo(UPDATED_WHEN);
        assertThat(testProjectStatusHistory.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    void fullUpdateProjectStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        projectStatusHistoryRepository.saveAndFlush(projectStatusHistory);

        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();

        // Update the projectStatusHistory using partial update
        ProjectStatusHistory partialUpdatedProjectStatusHistory = new ProjectStatusHistory();
        partialUpdatedProjectStatusHistory.setId(projectStatusHistory.getId());

        partialUpdatedProjectStatusHistory.status(UPDATED_STATUS).when(UPDATED_WHEN).remarks(UPDATED_REMARKS);

        restProjectStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProjectStatusHistory testProjectStatusHistory = projectStatusHistoryList.get(projectStatusHistoryList.size() - 1);
        assertThat(testProjectStatusHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjectStatusHistory.getWhen()).isEqualTo(UPDATED_WHEN);
        assertThat(testProjectStatusHistory.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void patchNonExistingProjectStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();
        projectStatusHistory.setId(count.incrementAndGet());

        // Create the ProjectStatusHistory
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(projectStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectStatusHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();
        projectStatusHistory.setId(count.incrementAndGet());

        // Create the ProjectStatusHistory
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(projectStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusHistoryRepository.findAll().size();
        projectStatusHistory.setId(count.incrementAndGet());

        // Create the ProjectStatusHistory
        ProjectStatusHistoryDTO projectStatusHistoryDTO = projectStatusHistoryMapper.toDto(projectStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectStatusHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectStatusHistory in the database
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectStatusHistory() throws Exception {
        // Initialize the database
        projectStatusHistoryRepository.saveAndFlush(projectStatusHistory);

        int databaseSizeBeforeDelete = projectStatusHistoryRepository.findAll().size();

        // Delete the projectStatusHistory
        restProjectStatusHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectStatusHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectStatusHistory> projectStatusHistoryList = projectStatusHistoryRepository.findAll();
        assertThat(projectStatusHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
