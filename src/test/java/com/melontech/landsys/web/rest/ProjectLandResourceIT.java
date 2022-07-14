package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.domain.Project;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.repository.ProjectLandRepository;
import com.melontech.landsys.service.ProjectLandService;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import com.melontech.landsys.service.mapper.ProjectLandMapper;
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
 * Integration tests for the {@link ProjectLandResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProjectLandResourceIT {

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTS_CONTENT_TYPE = "image/png";

    private static final HissaType DEFAULT_HISSA_TYPE = HissaType.SINGLE_OWNER;
    private static final HissaType UPDATED_HISSA_TYPE = HissaType.JOINT_OWNER;

    private static final String ENTITY_API_URL = "/api/project-lands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectLandRepository projectLandRepository;

    @Mock
    private ProjectLandRepository projectLandRepositoryMock;

    @Autowired
    private ProjectLandMapper projectLandMapper;

    @Mock
    private ProjectLandService projectLandServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectLandMockMvc;

    private ProjectLand projectLand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectLand createEntity(EntityManager em) {
        ProjectLand projectLand = new ProjectLand()
            .remarks(DEFAULT_REMARKS)
            .documents(DEFAULT_DOCUMENTS)
            .documentsContentType(DEFAULT_DOCUMENTS_CONTENT_TYPE)
            .hissaType(DEFAULT_HISSA_TYPE);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        projectLand.setProject(project);
        // Add required entity
        Citizen citizen;
        if (TestUtil.findAll(em, Citizen.class).isEmpty()) {
            citizen = CitizenResourceIT.createEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizen.class).get(0);
        }
        projectLand.setCitizen(citizen);
        return projectLand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectLand createUpdatedEntity(EntityManager em) {
        ProjectLand projectLand = new ProjectLand()
            .remarks(UPDATED_REMARKS)
            .documents(UPDATED_DOCUMENTS)
            .documentsContentType(UPDATED_DOCUMENTS_CONTENT_TYPE)
            .hissaType(UPDATED_HISSA_TYPE);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        projectLand.setProject(project);
        // Add required entity
        Citizen citizen;
        if (TestUtil.findAll(em, Citizen.class).isEmpty()) {
            citizen = CitizenResourceIT.createUpdatedEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizen.class).get(0);
        }
        projectLand.setCitizen(citizen);
        return projectLand;
    }

    @BeforeEach
    public void initTest() {
        projectLand = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectLand() throws Exception {
        int databaseSizeBeforeCreate = projectLandRepository.findAll().size();
        // Create the ProjectLand
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(projectLand);
        restProjectLandMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectLandDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectLand testProjectLand = projectLandList.get(projectLandList.size() - 1);
        assertThat(testProjectLand.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testProjectLand.getDocuments()).isEqualTo(DEFAULT_DOCUMENTS);
        assertThat(testProjectLand.getDocumentsContentType()).isEqualTo(DEFAULT_DOCUMENTS_CONTENT_TYPE);
        assertThat(testProjectLand.getHissaType()).isEqualTo(DEFAULT_HISSA_TYPE);
    }

    @Test
    @Transactional
    void createProjectLandWithExistingId() throws Exception {
        // Create the ProjectLand with an existing ID
        projectLand.setId(1L);
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(projectLand);

        int databaseSizeBeforeCreate = projectLandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectLandMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectLandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectLands() throws Exception {
        // Initialize the database
        projectLandRepository.saveAndFlush(projectLand);

        // Get all the projectLandList
        restProjectLandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectLand.getId().intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].documentsContentType").value(hasItem(DEFAULT_DOCUMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documents").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTS))))
            .andExpect(jsonPath("$.[*].hissaType").value(hasItem(DEFAULT_HISSA_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjectLandsWithEagerRelationshipsIsEnabled() throws Exception {
        when(projectLandServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjectLandMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(projectLandServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjectLandsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(projectLandServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjectLandMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(projectLandServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProjectLand() throws Exception {
        // Initialize the database
        projectLandRepository.saveAndFlush(projectLand);

        // Get the projectLand
        restProjectLandMockMvc
            .perform(get(ENTITY_API_URL_ID, projectLand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectLand.getId().intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.documentsContentType").value(DEFAULT_DOCUMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.documents").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTS)))
            .andExpect(jsonPath("$.hissaType").value(DEFAULT_HISSA_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProjectLand() throws Exception {
        // Get the projectLand
        restProjectLandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectLand() throws Exception {
        // Initialize the database
        projectLandRepository.saveAndFlush(projectLand);

        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();

        // Update the projectLand
        ProjectLand updatedProjectLand = projectLandRepository.findById(projectLand.getId()).get();
        // Disconnect from session so that the updates on updatedProjectLand are not directly saved in db
        em.detach(updatedProjectLand);
        updatedProjectLand
            .remarks(UPDATED_REMARKS)
            .documents(UPDATED_DOCUMENTS)
            .documentsContentType(UPDATED_DOCUMENTS_CONTENT_TYPE)
            .hissaType(UPDATED_HISSA_TYPE);
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(updatedProjectLand);

        restProjectLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectLandDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectLandDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
        ProjectLand testProjectLand = projectLandList.get(projectLandList.size() - 1);
        assertThat(testProjectLand.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testProjectLand.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
        assertThat(testProjectLand.getDocumentsContentType()).isEqualTo(UPDATED_DOCUMENTS_CONTENT_TYPE);
        assertThat(testProjectLand.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProjectLand() throws Exception {
        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();
        projectLand.setId(count.incrementAndGet());

        // Create the ProjectLand
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(projectLand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectLandDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectLandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectLand() throws Exception {
        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();
        projectLand.setId(count.incrementAndGet());

        // Create the ProjectLand
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(projectLand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectLandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectLand() throws Exception {
        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();
        projectLand.setId(count.incrementAndGet());

        // Create the ProjectLand
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(projectLand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectLandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectLandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectLandWithPatch() throws Exception {
        // Initialize the database
        projectLandRepository.saveAndFlush(projectLand);

        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();

        // Update the projectLand using partial update
        ProjectLand partialUpdatedProjectLand = new ProjectLand();
        partialUpdatedProjectLand.setId(projectLand.getId());

        partialUpdatedProjectLand.remarks(UPDATED_REMARKS).hissaType(UPDATED_HISSA_TYPE);

        restProjectLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectLand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectLand))
            )
            .andExpect(status().isOk());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
        ProjectLand testProjectLand = projectLandList.get(projectLandList.size() - 1);
        assertThat(testProjectLand.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testProjectLand.getDocuments()).isEqualTo(DEFAULT_DOCUMENTS);
        assertThat(testProjectLand.getDocumentsContentType()).isEqualTo(DEFAULT_DOCUMENTS_CONTENT_TYPE);
        assertThat(testProjectLand.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProjectLandWithPatch() throws Exception {
        // Initialize the database
        projectLandRepository.saveAndFlush(projectLand);

        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();

        // Update the projectLand using partial update
        ProjectLand partialUpdatedProjectLand = new ProjectLand();
        partialUpdatedProjectLand.setId(projectLand.getId());

        partialUpdatedProjectLand
            .remarks(UPDATED_REMARKS)
            .documents(UPDATED_DOCUMENTS)
            .documentsContentType(UPDATED_DOCUMENTS_CONTENT_TYPE)
            .hissaType(UPDATED_HISSA_TYPE);

        restProjectLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectLand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectLand))
            )
            .andExpect(status().isOk());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
        ProjectLand testProjectLand = projectLandList.get(projectLandList.size() - 1);
        assertThat(testProjectLand.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testProjectLand.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
        assertThat(testProjectLand.getDocumentsContentType()).isEqualTo(UPDATED_DOCUMENTS_CONTENT_TYPE);
        assertThat(testProjectLand.getHissaType()).isEqualTo(UPDATED_HISSA_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProjectLand() throws Exception {
        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();
        projectLand.setId(count.incrementAndGet());

        // Create the ProjectLand
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(projectLand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectLandDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectLandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectLand() throws Exception {
        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();
        projectLand.setId(count.incrementAndGet());

        // Create the ProjectLand
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(projectLand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectLandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectLand() throws Exception {
        int databaseSizeBeforeUpdate = projectLandRepository.findAll().size();
        projectLand.setId(count.incrementAndGet());

        // Create the ProjectLand
        ProjectLandDTO projectLandDTO = projectLandMapper.toDto(projectLand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectLandMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projectLandDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectLand in the database
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectLand() throws Exception {
        // Initialize the database
        projectLandRepository.saveAndFlush(projectLand);

        int databaseSizeBeforeDelete = projectLandRepository.findAll().size();

        // Delete the projectLand
        restProjectLandMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectLand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectLand> projectLandList = projectLandRepository.findAll();
        assertThat(projectLandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
