package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.PublicNotification;
import com.melontech.landsys.repository.PublicNotificationRepository;
import com.melontech.landsys.service.dto.PublicNotificationDTO;
import com.melontech.landsys.service.mapper.PublicNotificationMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PublicNotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicNotificationResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/public-notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PublicNotificationRepository publicNotificationRepository;

    @Autowired
    private PublicNotificationMapper publicNotificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicNotificationMockMvc;

    private PublicNotification publicNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicNotification createEntity(EntityManager em) {
        PublicNotification publicNotification = new PublicNotification()
            .date(DEFAULT_DATE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return publicNotification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicNotification createUpdatedEntity(EntityManager em) {
        PublicNotification publicNotification = new PublicNotification()
            .date(UPDATED_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        return publicNotification;
    }

    @BeforeEach
    public void initTest() {
        publicNotification = createEntity(em);
    }

    @Test
    @Transactional
    void createPublicNotification() throws Exception {
        int databaseSizeBeforeCreate = publicNotificationRepository.findAll().size();
        // Create the PublicNotification
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);
        restPublicNotificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        PublicNotification testPublicNotification = publicNotificationList.get(publicNotificationList.size() - 1);
        assertThat(testPublicNotification.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPublicNotification.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testPublicNotification.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testPublicNotification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createPublicNotificationWithExistingId() throws Exception {
        // Create the PublicNotification with an existing ID
        publicNotification.setId(1L);
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);

        int databaseSizeBeforeCreate = publicNotificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicNotificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicNotificationRepository.findAll().size();
        // set the field null
        publicNotification.setDate(null);

        // Create the PublicNotification, which fails.
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);

        restPublicNotificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPublicNotifications() throws Exception {
        // Initialize the database
        publicNotificationRepository.saveAndFlush(publicNotification);

        // Get all the publicNotificationList
        restPublicNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getPublicNotification() throws Exception {
        // Initialize the database
        publicNotificationRepository.saveAndFlush(publicNotification);

        // Get the publicNotification
        restPublicNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, publicNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicNotification.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPublicNotification() throws Exception {
        // Get the publicNotification
        restPublicNotificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPublicNotification() throws Exception {
        // Initialize the database
        publicNotificationRepository.saveAndFlush(publicNotification);

        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();

        // Update the publicNotification
        PublicNotification updatedPublicNotification = publicNotificationRepository.findById(publicNotification.getId()).get();
        // Disconnect from session so that the updates on updatedPublicNotification are not directly saved in db
        em.detach(updatedPublicNotification);
        updatedPublicNotification
            .date(UPDATED_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(updatedPublicNotification);

        restPublicNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicNotificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
        PublicNotification testPublicNotification = publicNotificationList.get(publicNotificationList.size() - 1);
        assertThat(testPublicNotification.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPublicNotification.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testPublicNotification.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testPublicNotification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingPublicNotification() throws Exception {
        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();
        publicNotification.setId(count.incrementAndGet());

        // Create the PublicNotification
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicNotificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicNotification() throws Exception {
        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();
        publicNotification.setId(count.incrementAndGet());

        // Create the PublicNotification
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicNotification() throws Exception {
        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();
        publicNotification.setId(count.incrementAndGet());

        // Create the PublicNotification
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicNotificationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicNotificationWithPatch() throws Exception {
        // Initialize the database
        publicNotificationRepository.saveAndFlush(publicNotification);

        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();

        // Update the publicNotification using partial update
        PublicNotification partialUpdatedPublicNotification = new PublicNotification();
        partialUpdatedPublicNotification.setId(publicNotification.getId());

        restPublicNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicNotification))
            )
            .andExpect(status().isOk());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
        PublicNotification testPublicNotification = publicNotificationList.get(publicNotificationList.size() - 1);
        assertThat(testPublicNotification.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPublicNotification.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testPublicNotification.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testPublicNotification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdatePublicNotificationWithPatch() throws Exception {
        // Initialize the database
        publicNotificationRepository.saveAndFlush(publicNotification);

        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();

        // Update the publicNotification using partial update
        PublicNotification partialUpdatedPublicNotification = new PublicNotification();
        partialUpdatedPublicNotification.setId(publicNotification.getId());

        partialUpdatedPublicNotification
            .date(UPDATED_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);

        restPublicNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicNotification))
            )
            .andExpect(status().isOk());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
        PublicNotification testPublicNotification = publicNotificationList.get(publicNotificationList.size() - 1);
        assertThat(testPublicNotification.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPublicNotification.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testPublicNotification.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testPublicNotification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingPublicNotification() throws Exception {
        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();
        publicNotification.setId(count.incrementAndGet());

        // Create the PublicNotification
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicNotificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicNotification() throws Exception {
        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();
        publicNotification.setId(count.incrementAndGet());

        // Create the PublicNotification
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicNotification() throws Exception {
        int databaseSizeBeforeUpdate = publicNotificationRepository.findAll().size();
        publicNotification.setId(count.incrementAndGet());

        // Create the PublicNotification
        PublicNotificationDTO publicNotificationDTO = publicNotificationMapper.toDto(publicNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicNotification in the database
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicNotification() throws Exception {
        // Initialize the database
        publicNotificationRepository.saveAndFlush(publicNotification);

        int databaseSizeBeforeDelete = publicNotificationRepository.findAll().size();

        // Delete the publicNotification
        restPublicNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicNotification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicNotification> publicNotificationList = publicNotificationRepository.findAll();
        assertThat(publicNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
