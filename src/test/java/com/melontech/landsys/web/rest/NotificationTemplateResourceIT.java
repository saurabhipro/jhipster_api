package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.NotificationTemplate;
import com.melontech.landsys.repository.NotificationTemplateRepository;
import com.melontech.landsys.service.dto.NotificationTemplateDTO;
import com.melontech.landsys.service.mapper.NotificationTemplateMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link NotificationTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_USE = false;
    private static final Boolean UPDATED_DEFAULT_USE = true;

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/notification-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    private NotificationTemplateMapper notificationTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationTemplateMockMvc;

    private NotificationTemplate notificationTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationTemplate createEntity(EntityManager em) {
        NotificationTemplate notificationTemplate = new NotificationTemplate()
            .name(DEFAULT_NAME)
            .templateText(DEFAULT_TEMPLATE_TEXT)
            .defaultUse(DEFAULT_DEFAULT_USE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return notificationTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationTemplate createUpdatedEntity(EntityManager em) {
        NotificationTemplate notificationTemplate = new NotificationTemplate()
            .name(UPDATED_NAME)
            .templateText(UPDATED_TEMPLATE_TEXT)
            .defaultUse(UPDATED_DEFAULT_USE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        return notificationTemplate;
    }

    @BeforeEach
    public void initTest() {
        notificationTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createNotificationTemplate() throws Exception {
        int databaseSizeBeforeCreate = notificationTemplateRepository.findAll().size();
        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplate);
        restNotificationTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationTemplate testNotificationTemplate = notificationTemplateList.get(notificationTemplateList.size() - 1);
        assertThat(testNotificationTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotificationTemplate.getTemplateText()).isEqualTo(DEFAULT_TEMPLATE_TEXT);
        assertThat(testNotificationTemplate.getDefaultUse()).isEqualTo(DEFAULT_DEFAULT_USE);
        assertThat(testNotificationTemplate.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testNotificationTemplate.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createNotificationTemplateWithExistingId() throws Exception {
        // Create the NotificationTemplate with an existing ID
        notificationTemplate.setId(1L);
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplate);

        int databaseSizeBeforeCreate = notificationTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotificationTemplates() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplate);

        // Get all the notificationTemplateList
        restNotificationTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].templateText").value(hasItem(DEFAULT_TEMPLATE_TEXT)))
            .andExpect(jsonPath("$.[*].defaultUse").value(hasItem(DEFAULT_DEFAULT_USE.booleanValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    void getNotificationTemplate() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplate);

        // Get the notificationTemplate
        restNotificationTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, notificationTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.templateText").value(DEFAULT_TEMPLATE_TEXT))
            .andExpect(jsonPath("$.defaultUse").value(DEFAULT_DEFAULT_USE.booleanValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    void getNonExistingNotificationTemplate() throws Exception {
        // Get the notificationTemplate
        restNotificationTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNotificationTemplate() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplate);

        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();

        // Update the notificationTemplate
        NotificationTemplate updatedNotificationTemplate = notificationTemplateRepository.findById(notificationTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationTemplate are not directly saved in db
        em.detach(updatedNotificationTemplate);
        updatedNotificationTemplate
            .name(UPDATED_NAME)
            .templateText(UPDATED_TEMPLATE_TEXT)
            .defaultUse(UPDATED_DEFAULT_USE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(updatedNotificationTemplate);

        restNotificationTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplate testNotificationTemplate = notificationTemplateList.get(notificationTemplateList.size() - 1);
        assertThat(testNotificationTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationTemplate.getTemplateText()).isEqualTo(UPDATED_TEMPLATE_TEXT);
        assertThat(testNotificationTemplate.getDefaultUse()).isEqualTo(UPDATED_DEFAULT_USE);
        assertThat(testNotificationTemplate.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testNotificationTemplate.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplate.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplate.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplate.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationTemplateWithPatch() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplate);

        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();

        // Update the notificationTemplate using partial update
        NotificationTemplate partialUpdatedNotificationTemplate = new NotificationTemplate();
        partialUpdatedNotificationTemplate.setId(notificationTemplate.getId());

        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationTemplate))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplate testNotificationTemplate = notificationTemplateList.get(notificationTemplateList.size() - 1);
        assertThat(testNotificationTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotificationTemplate.getTemplateText()).isEqualTo(DEFAULT_TEMPLATE_TEXT);
        assertThat(testNotificationTemplate.getDefaultUse()).isEqualTo(DEFAULT_DEFAULT_USE);
        assertThat(testNotificationTemplate.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testNotificationTemplate.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateNotificationTemplateWithPatch() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplate);

        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();

        // Update the notificationTemplate using partial update
        NotificationTemplate partialUpdatedNotificationTemplate = new NotificationTemplate();
        partialUpdatedNotificationTemplate.setId(notificationTemplate.getId());

        partialUpdatedNotificationTemplate
            .name(UPDATED_NAME)
            .templateText(UPDATED_TEMPLATE_TEXT)
            .defaultUse(UPDATED_DEFAULT_USE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);

        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationTemplate))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplate testNotificationTemplate = notificationTemplateList.get(notificationTemplateList.size() - 1);
        assertThat(testNotificationTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationTemplate.getTemplateText()).isEqualTo(UPDATED_TEMPLATE_TEXT);
        assertThat(testNotificationTemplate.getDefaultUse()).isEqualTo(UPDATED_DEFAULT_USE);
        assertThat(testNotificationTemplate.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testNotificationTemplate.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplate.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplate.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplate.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotificationTemplate() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplate);

        int databaseSizeBeforeDelete = notificationTemplateRepository.findAll().size();

        // Delete the notificationTemplate
        restNotificationTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificationTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationTemplate> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
