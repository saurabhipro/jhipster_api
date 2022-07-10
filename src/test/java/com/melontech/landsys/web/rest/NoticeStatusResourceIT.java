package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.NoticeStatus;
import com.melontech.landsys.repository.NoticeStatusRepository;
import com.melontech.landsys.service.dto.NoticeStatusDTO;
import com.melontech.landsys.service.mapper.NoticeStatusMapper;
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
 * Integration tests for the {@link NoticeStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NoticeStatusResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/notice-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NoticeStatusRepository noticeStatusRepository;

    @Autowired
    private NoticeStatusMapper noticeStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoticeStatusMockMvc;

    private NoticeStatus noticeStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoticeStatus createEntity(EntityManager em) {
        NoticeStatus noticeStatus = new NoticeStatus().status(DEFAULT_STATUS);
        return noticeStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoticeStatus createUpdatedEntity(EntityManager em) {
        NoticeStatus noticeStatus = new NoticeStatus().status(UPDATED_STATUS);
        return noticeStatus;
    }

    @BeforeEach
    public void initTest() {
        noticeStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createNoticeStatus() throws Exception {
        int databaseSizeBeforeCreate = noticeStatusRepository.findAll().size();
        // Create the NoticeStatus
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(noticeStatus);
        restNoticeStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeCreate + 1);
        NoticeStatus testNoticeStatus = noticeStatusList.get(noticeStatusList.size() - 1);
        assertThat(testNoticeStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createNoticeStatusWithExistingId() throws Exception {
        // Create the NoticeStatus with an existing ID
        noticeStatus.setId(1L);
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(noticeStatus);

        int databaseSizeBeforeCreate = noticeStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticeStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNoticeStatuses() throws Exception {
        // Initialize the database
        noticeStatusRepository.saveAndFlush(noticeStatus);

        // Get all the noticeStatusList
        restNoticeStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticeStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getNoticeStatus() throws Exception {
        // Initialize the database
        noticeStatusRepository.saveAndFlush(noticeStatus);

        // Get the noticeStatus
        restNoticeStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, noticeStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noticeStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingNoticeStatus() throws Exception {
        // Get the noticeStatus
        restNoticeStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNoticeStatus() throws Exception {
        // Initialize the database
        noticeStatusRepository.saveAndFlush(noticeStatus);

        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();

        // Update the noticeStatus
        NoticeStatus updatedNoticeStatus = noticeStatusRepository.findById(noticeStatus.getId()).get();
        // Disconnect from session so that the updates on updatedNoticeStatus are not directly saved in db
        em.detach(updatedNoticeStatus);
        updatedNoticeStatus.status(UPDATED_STATUS);
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(updatedNoticeStatus);

        restNoticeStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticeStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
        NoticeStatus testNoticeStatus = noticeStatusList.get(noticeStatusList.size() - 1);
        assertThat(testNoticeStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingNoticeStatus() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();
        noticeStatus.setId(count.incrementAndGet());

        // Create the NoticeStatus
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(noticeStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticeStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticeStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNoticeStatus() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();
        noticeStatus.setId(count.incrementAndGet());

        // Create the NoticeStatus
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(noticeStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticeStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNoticeStatus() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();
        noticeStatus.setId(count.incrementAndGet());

        // Create the NoticeStatus
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(noticeStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticeStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoticeStatusWithPatch() throws Exception {
        // Initialize the database
        noticeStatusRepository.saveAndFlush(noticeStatus);

        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();

        // Update the noticeStatus using partial update
        NoticeStatus partialUpdatedNoticeStatus = new NoticeStatus();
        partialUpdatedNoticeStatus.setId(noticeStatus.getId());

        restNoticeStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticeStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoticeStatus))
            )
            .andExpect(status().isOk());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
        NoticeStatus testNoticeStatus = noticeStatusList.get(noticeStatusList.size() - 1);
        assertThat(testNoticeStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateNoticeStatusWithPatch() throws Exception {
        // Initialize the database
        noticeStatusRepository.saveAndFlush(noticeStatus);

        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();

        // Update the noticeStatus using partial update
        NoticeStatus partialUpdatedNoticeStatus = new NoticeStatus();
        partialUpdatedNoticeStatus.setId(noticeStatus.getId());

        partialUpdatedNoticeStatus.status(UPDATED_STATUS);

        restNoticeStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticeStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoticeStatus))
            )
            .andExpect(status().isOk());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
        NoticeStatus testNoticeStatus = noticeStatusList.get(noticeStatusList.size() - 1);
        assertThat(testNoticeStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingNoticeStatus() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();
        noticeStatus.setId(count.incrementAndGet());

        // Create the NoticeStatus
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(noticeStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticeStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noticeStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNoticeStatus() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();
        noticeStatus.setId(count.incrementAndGet());

        // Create the NoticeStatus
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(noticeStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticeStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNoticeStatus() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusRepository.findAll().size();
        noticeStatus.setId(count.incrementAndGet());

        // Create the NoticeStatus
        NoticeStatusDTO noticeStatusDTO = noticeStatusMapper.toDto(noticeStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticeStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NoticeStatus in the database
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNoticeStatus() throws Exception {
        // Initialize the database
        noticeStatusRepository.saveAndFlush(noticeStatus);

        int databaseSizeBeforeDelete = noticeStatusRepository.findAll().size();

        // Delete the noticeStatus
        restNoticeStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, noticeStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NoticeStatus> noticeStatusList = noticeStatusRepository.findAll();
        assertThat(noticeStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
