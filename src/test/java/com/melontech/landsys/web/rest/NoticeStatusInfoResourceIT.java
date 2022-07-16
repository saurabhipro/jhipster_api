package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.NoticeStatusInfo;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.enumeration.NoticeStatus;
import com.melontech.landsys.repository.NoticeStatusInfoRepository;
import com.melontech.landsys.service.criteria.NoticeStatusInfoCriteria;
import com.melontech.landsys.service.dto.NoticeStatusInfoDTO;
import com.melontech.landsys.service.mapper.NoticeStatusInfoMapper;
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
 * Integration tests for the {@link NoticeStatusInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NoticeStatusInfoResourceIT {

    private static final NoticeStatus DEFAULT_STATUS = NoticeStatus.SENT;
    private static final NoticeStatus UPDATED_STATUS = NoticeStatus.ACCEPTED;

    private static final String ENTITY_API_URL = "/api/notice-status-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NoticeStatusInfoRepository noticeStatusInfoRepository;

    @Autowired
    private NoticeStatusInfoMapper noticeStatusInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoticeStatusInfoMockMvc;

    private NoticeStatusInfo noticeStatusInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoticeStatusInfo createEntity(EntityManager em) {
        NoticeStatusInfo noticeStatusInfo = new NoticeStatusInfo().status(DEFAULT_STATUS);
        return noticeStatusInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoticeStatusInfo createUpdatedEntity(EntityManager em) {
        NoticeStatusInfo noticeStatusInfo = new NoticeStatusInfo().status(UPDATED_STATUS);
        return noticeStatusInfo;
    }

    @BeforeEach
    public void initTest() {
        noticeStatusInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createNoticeStatusInfo() throws Exception {
        int databaseSizeBeforeCreate = noticeStatusInfoRepository.findAll().size();
        // Create the NoticeStatusInfo
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(noticeStatusInfo);
        restNoticeStatusInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeCreate + 1);
        NoticeStatusInfo testNoticeStatusInfo = noticeStatusInfoList.get(noticeStatusInfoList.size() - 1);
        assertThat(testNoticeStatusInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createNoticeStatusInfoWithExistingId() throws Exception {
        // Create the NoticeStatusInfo with an existing ID
        noticeStatusInfo.setId(1L);
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(noticeStatusInfo);

        int databaseSizeBeforeCreate = noticeStatusInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticeStatusInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNoticeStatusInfos() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        // Get all the noticeStatusInfoList
        restNoticeStatusInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticeStatusInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getNoticeStatusInfo() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        // Get the noticeStatusInfo
        restNoticeStatusInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, noticeStatusInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noticeStatusInfo.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNoticeStatusInfosByIdFiltering() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        Long id = noticeStatusInfo.getId();

        defaultNoticeStatusInfoShouldBeFound("id.equals=" + id);
        defaultNoticeStatusInfoShouldNotBeFound("id.notEquals=" + id);

        defaultNoticeStatusInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNoticeStatusInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultNoticeStatusInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNoticeStatusInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNoticeStatusInfosByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        // Get all the noticeStatusInfoList where status equals to DEFAULT_STATUS
        defaultNoticeStatusInfoShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the noticeStatusInfoList where status equals to UPDATED_STATUS
        defaultNoticeStatusInfoShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNoticeStatusInfosByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        // Get all the noticeStatusInfoList where status not equals to DEFAULT_STATUS
        defaultNoticeStatusInfoShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the noticeStatusInfoList where status not equals to UPDATED_STATUS
        defaultNoticeStatusInfoShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNoticeStatusInfosByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        // Get all the noticeStatusInfoList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultNoticeStatusInfoShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the noticeStatusInfoList where status equals to UPDATED_STATUS
        defaultNoticeStatusInfoShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNoticeStatusInfosByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        // Get all the noticeStatusInfoList where status is not null
        defaultNoticeStatusInfoShouldBeFound("status.specified=true");

        // Get all the noticeStatusInfoList where status is null
        defaultNoticeStatusInfoShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllNoticeStatusInfosByProjectLandIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);
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
        noticeStatusInfo.addProjectLand(projectLand);
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);
        Long projectLandId = projectLand.getId();

        // Get all the noticeStatusInfoList where projectLand equals to projectLandId
        defaultNoticeStatusInfoShouldBeFound("projectLandId.equals=" + projectLandId);

        // Get all the noticeStatusInfoList where projectLand equals to (projectLandId + 1)
        defaultNoticeStatusInfoShouldNotBeFound("projectLandId.equals=" + (projectLandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoticeStatusInfoShouldBeFound(String filter) throws Exception {
        restNoticeStatusInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticeStatusInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restNoticeStatusInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoticeStatusInfoShouldNotBeFound(String filter) throws Exception {
        restNoticeStatusInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoticeStatusInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNoticeStatusInfo() throws Exception {
        // Get the noticeStatusInfo
        restNoticeStatusInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNoticeStatusInfo() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();

        // Update the noticeStatusInfo
        NoticeStatusInfo updatedNoticeStatusInfo = noticeStatusInfoRepository.findById(noticeStatusInfo.getId()).get();
        // Disconnect from session so that the updates on updatedNoticeStatusInfo are not directly saved in db
        em.detach(updatedNoticeStatusInfo);
        updatedNoticeStatusInfo.status(UPDATED_STATUS);
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(updatedNoticeStatusInfo);

        restNoticeStatusInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticeStatusInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
        NoticeStatusInfo testNoticeStatusInfo = noticeStatusInfoList.get(noticeStatusInfoList.size() - 1);
        assertThat(testNoticeStatusInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingNoticeStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();
        noticeStatusInfo.setId(count.incrementAndGet());

        // Create the NoticeStatusInfo
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(noticeStatusInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticeStatusInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticeStatusInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNoticeStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();
        noticeStatusInfo.setId(count.incrementAndGet());

        // Create the NoticeStatusInfo
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(noticeStatusInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticeStatusInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNoticeStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();
        noticeStatusInfo.setId(count.incrementAndGet());

        // Create the NoticeStatusInfo
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(noticeStatusInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticeStatusInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoticeStatusInfoWithPatch() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();

        // Update the noticeStatusInfo using partial update
        NoticeStatusInfo partialUpdatedNoticeStatusInfo = new NoticeStatusInfo();
        partialUpdatedNoticeStatusInfo.setId(noticeStatusInfo.getId());

        restNoticeStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticeStatusInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoticeStatusInfo))
            )
            .andExpect(status().isOk());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
        NoticeStatusInfo testNoticeStatusInfo = noticeStatusInfoList.get(noticeStatusInfoList.size() - 1);
        assertThat(testNoticeStatusInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateNoticeStatusInfoWithPatch() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();

        // Update the noticeStatusInfo using partial update
        NoticeStatusInfo partialUpdatedNoticeStatusInfo = new NoticeStatusInfo();
        partialUpdatedNoticeStatusInfo.setId(noticeStatusInfo.getId());

        partialUpdatedNoticeStatusInfo.status(UPDATED_STATUS);

        restNoticeStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticeStatusInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoticeStatusInfo))
            )
            .andExpect(status().isOk());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
        NoticeStatusInfo testNoticeStatusInfo = noticeStatusInfoList.get(noticeStatusInfoList.size() - 1);
        assertThat(testNoticeStatusInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingNoticeStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();
        noticeStatusInfo.setId(count.incrementAndGet());

        // Create the NoticeStatusInfo
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(noticeStatusInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticeStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noticeStatusInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNoticeStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();
        noticeStatusInfo.setId(count.incrementAndGet());

        // Create the NoticeStatusInfo
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(noticeStatusInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticeStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNoticeStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = noticeStatusInfoRepository.findAll().size();
        noticeStatusInfo.setId(count.incrementAndGet());

        // Create the NoticeStatusInfo
        NoticeStatusInfoDTO noticeStatusInfoDTO = noticeStatusInfoMapper.toDto(noticeStatusInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticeStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noticeStatusInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NoticeStatusInfo in the database
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNoticeStatusInfo() throws Exception {
        // Initialize the database
        noticeStatusInfoRepository.saveAndFlush(noticeStatusInfo);

        int databaseSizeBeforeDelete = noticeStatusInfoRepository.findAll().size();

        // Delete the noticeStatusInfo
        restNoticeStatusInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, noticeStatusInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NoticeStatusInfo> noticeStatusInfoList = noticeStatusInfoRepository.findAll();
        assertThat(noticeStatusInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
