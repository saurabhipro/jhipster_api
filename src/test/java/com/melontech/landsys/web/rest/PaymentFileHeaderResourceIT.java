package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.PaymentFileHeader;
import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import com.melontech.landsys.repository.PaymentFileHeaderRepository;
import com.melontech.landsys.service.dto.PaymentFileHeaderDTO;
import com.melontech.landsys.service.mapper.PaymentFileHeaderMapper;
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
 * Integration tests for the {@link PaymentFileHeaderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentFileHeaderResourceIT {

    private static final Double DEFAULT_GRAND_TOTAL_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_GRAND_TOTAL_PAYMENT_AMOUNT = 2D;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.APPROVED;

    private static final PaymentAdviceType DEFAULT_PAYMENT_MODE = PaymentAdviceType.ONLINE;
    private static final PaymentAdviceType UPDATED_PAYMENT_MODE = PaymentAdviceType.CHECQUE;

    private static final String DEFAULT_APPROVER_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_REMARKS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-file-headers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentFileHeaderRepository paymentFileHeaderRepository;

    @Autowired
    private PaymentFileHeaderMapper paymentFileHeaderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentFileHeaderMockMvc;

    private PaymentFileHeader paymentFileHeader;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentFileHeader createEntity(EntityManager em) {
        PaymentFileHeader paymentFileHeader = new PaymentFileHeader()
            .grandTotalPaymentAmount(DEFAULT_GRAND_TOTAL_PAYMENT_AMOUNT)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .approverRemarks(DEFAULT_APPROVER_REMARKS);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        paymentFileHeader.setProjectLand(projectLand);
        return paymentFileHeader;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentFileHeader createUpdatedEntity(EntityManager em) {
        PaymentFileHeader paymentFileHeader = new PaymentFileHeader()
            .grandTotalPaymentAmount(UPDATED_GRAND_TOTAL_PAYMENT_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .approverRemarks(UPDATED_APPROVER_REMARKS);
        // Add required entity
        ProjectLand projectLand;
        if (TestUtil.findAll(em, ProjectLand.class).isEmpty()) {
            projectLand = ProjectLandResourceIT.createUpdatedEntity(em);
            em.persist(projectLand);
            em.flush();
        } else {
            projectLand = TestUtil.findAll(em, ProjectLand.class).get(0);
        }
        paymentFileHeader.setProjectLand(projectLand);
        return paymentFileHeader;
    }

    @BeforeEach
    public void initTest() {
        paymentFileHeader = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentFileHeader() throws Exception {
        int databaseSizeBeforeCreate = paymentFileHeaderRepository.findAll().size();
        // Create the PaymentFileHeader
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);
        restPaymentFileHeaderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentFileHeader testPaymentFileHeader = paymentFileHeaderList.get(paymentFileHeaderList.size() - 1);
        assertThat(testPaymentFileHeader.getGrandTotalPaymentAmount()).isEqualTo(DEFAULT_GRAND_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPaymentFileHeader.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPaymentFileHeader.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testPaymentFileHeader.getApproverRemarks()).isEqualTo(DEFAULT_APPROVER_REMARKS);
    }

    @Test
    @Transactional
    void createPaymentFileHeaderWithExistingId() throws Exception {
        // Create the PaymentFileHeader with an existing ID
        paymentFileHeader.setId(1L);
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        int databaseSizeBeforeCreate = paymentFileHeaderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentFileHeaderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGrandTotalPaymentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileHeaderRepository.findAll().size();
        // set the field null
        paymentFileHeader.setGrandTotalPaymentAmount(null);

        // Create the PaymentFileHeader, which fails.
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        restPaymentFileHeaderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFileHeaderRepository.findAll().size();
        // set the field null
        paymentFileHeader.setPaymentStatus(null);

        // Create the PaymentFileHeader, which fails.
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        restPaymentFileHeaderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentFileHeaders() throws Exception {
        // Initialize the database
        paymentFileHeaderRepository.saveAndFlush(paymentFileHeader);

        // Get all the paymentFileHeaderList
        restPaymentFileHeaderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentFileHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].grandTotalPaymentAmount").value(hasItem(DEFAULT_GRAND_TOTAL_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].approverRemarks").value(hasItem(DEFAULT_APPROVER_REMARKS)));
    }

    @Test
    @Transactional
    void getPaymentFileHeader() throws Exception {
        // Initialize the database
        paymentFileHeaderRepository.saveAndFlush(paymentFileHeader);

        // Get the paymentFileHeader
        restPaymentFileHeaderMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentFileHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentFileHeader.getId().intValue()))
            .andExpect(jsonPath("$.grandTotalPaymentAmount").value(DEFAULT_GRAND_TOTAL_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.approverRemarks").value(DEFAULT_APPROVER_REMARKS));
    }

    @Test
    @Transactional
    void getNonExistingPaymentFileHeader() throws Exception {
        // Get the paymentFileHeader
        restPaymentFileHeaderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentFileHeader() throws Exception {
        // Initialize the database
        paymentFileHeaderRepository.saveAndFlush(paymentFileHeader);

        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();

        // Update the paymentFileHeader
        PaymentFileHeader updatedPaymentFileHeader = paymentFileHeaderRepository.findById(paymentFileHeader.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentFileHeader are not directly saved in db
        em.detach(updatedPaymentFileHeader);
        updatedPaymentFileHeader
            .grandTotalPaymentAmount(UPDATED_GRAND_TOTAL_PAYMENT_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .approverRemarks(UPDATED_APPROVER_REMARKS);
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(updatedPaymentFileHeader);

        restPaymentFileHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentFileHeaderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
        PaymentFileHeader testPaymentFileHeader = paymentFileHeaderList.get(paymentFileHeaderList.size() - 1);
        assertThat(testPaymentFileHeader.getGrandTotalPaymentAmount()).isEqualTo(UPDATED_GRAND_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPaymentFileHeader.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentFileHeader.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testPaymentFileHeader.getApproverRemarks()).isEqualTo(UPDATED_APPROVER_REMARKS);
    }

    @Test
    @Transactional
    void putNonExistingPaymentFileHeader() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();
        paymentFileHeader.setId(count.incrementAndGet());

        // Create the PaymentFileHeader
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentFileHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentFileHeaderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentFileHeader() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();
        paymentFileHeader.setId(count.incrementAndGet());

        // Create the PaymentFileHeader
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentFileHeader() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();
        paymentFileHeader.setId(count.incrementAndGet());

        // Create the PaymentFileHeader
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileHeaderMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentFileHeaderWithPatch() throws Exception {
        // Initialize the database
        paymentFileHeaderRepository.saveAndFlush(paymentFileHeader);

        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();

        // Update the paymentFileHeader using partial update
        PaymentFileHeader partialUpdatedPaymentFileHeader = new PaymentFileHeader();
        partialUpdatedPaymentFileHeader.setId(paymentFileHeader.getId());

        partialUpdatedPaymentFileHeader
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .approverRemarks(UPDATED_APPROVER_REMARKS);

        restPaymentFileHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentFileHeader.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentFileHeader))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
        PaymentFileHeader testPaymentFileHeader = paymentFileHeaderList.get(paymentFileHeaderList.size() - 1);
        assertThat(testPaymentFileHeader.getGrandTotalPaymentAmount()).isEqualTo(DEFAULT_GRAND_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPaymentFileHeader.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentFileHeader.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testPaymentFileHeader.getApproverRemarks()).isEqualTo(UPDATED_APPROVER_REMARKS);
    }

    @Test
    @Transactional
    void fullUpdatePaymentFileHeaderWithPatch() throws Exception {
        // Initialize the database
        paymentFileHeaderRepository.saveAndFlush(paymentFileHeader);

        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();

        // Update the paymentFileHeader using partial update
        PaymentFileHeader partialUpdatedPaymentFileHeader = new PaymentFileHeader();
        partialUpdatedPaymentFileHeader.setId(paymentFileHeader.getId());

        partialUpdatedPaymentFileHeader
            .grandTotalPaymentAmount(UPDATED_GRAND_TOTAL_PAYMENT_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .approverRemarks(UPDATED_APPROVER_REMARKS);

        restPaymentFileHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentFileHeader.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentFileHeader))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
        PaymentFileHeader testPaymentFileHeader = paymentFileHeaderList.get(paymentFileHeaderList.size() - 1);
        assertThat(testPaymentFileHeader.getGrandTotalPaymentAmount()).isEqualTo(UPDATED_GRAND_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPaymentFileHeader.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentFileHeader.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testPaymentFileHeader.getApproverRemarks()).isEqualTo(UPDATED_APPROVER_REMARKS);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentFileHeader() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();
        paymentFileHeader.setId(count.incrementAndGet());

        // Create the PaymentFileHeader
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentFileHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentFileHeaderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentFileHeader() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();
        paymentFileHeader.setId(count.incrementAndGet());

        // Create the PaymentFileHeader
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentFileHeader() throws Exception {
        int databaseSizeBeforeUpdate = paymentFileHeaderRepository.findAll().size();
        paymentFileHeader.setId(count.incrementAndGet());

        // Create the PaymentFileHeader
        PaymentFileHeaderDTO paymentFileHeaderDTO = paymentFileHeaderMapper.toDto(paymentFileHeader);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFileHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFileHeaderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentFileHeader in the database
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentFileHeader() throws Exception {
        // Initialize the database
        paymentFileHeaderRepository.saveAndFlush(paymentFileHeader);

        int databaseSizeBeforeDelete = paymentFileHeaderRepository.findAll().size();

        // Delete the paymentFileHeader
        restPaymentFileHeaderMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentFileHeader.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentFileHeader> paymentFileHeaderList = paymentFileHeaderRepository.findAll();
        assertThat(paymentFileHeaderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
