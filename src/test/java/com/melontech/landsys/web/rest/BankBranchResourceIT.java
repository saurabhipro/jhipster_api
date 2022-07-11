package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.BankBranch;
import com.melontech.landsys.domain.Citizen;
import com.melontech.landsys.repository.BankBranchRepository;
import com.melontech.landsys.service.BankBranchService;
import com.melontech.landsys.service.dto.BankBranchDTO;
import com.melontech.landsys.service.mapper.BankBranchMapper;
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
 * Integration tests for the {@link BankBranchResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BankBranchResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC = "AAAAAAAAAA";
    private static final String UPDATED_IFSC = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-branches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankBranchRepository bankBranchRepository;

    @Mock
    private BankBranchRepository bankBranchRepositoryMock;

    @Autowired
    private BankBranchMapper bankBranchMapper;

    @Mock
    private BankBranchService bankBranchServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankBranchMockMvc;

    private BankBranch bankBranch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankBranch createEntity(EntityManager em) {
        BankBranch bankBranch = new BankBranch().name(DEFAULT_NAME).ifsc(DEFAULT_IFSC).address(DEFAULT_ADDRESS);
        // Add required entity
        Citizen citizen;
        if (TestUtil.findAll(em, Citizen.class).isEmpty()) {
            citizen = CitizenResourceIT.createEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizen.class).get(0);
        }
        bankBranch.getCitizens().add(citizen);
        return bankBranch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankBranch createUpdatedEntity(EntityManager em) {
        BankBranch bankBranch = new BankBranch().name(UPDATED_NAME).ifsc(UPDATED_IFSC).address(UPDATED_ADDRESS);
        // Add required entity
        Citizen citizen;
        if (TestUtil.findAll(em, Citizen.class).isEmpty()) {
            citizen = CitizenResourceIT.createUpdatedEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizen.class).get(0);
        }
        bankBranch.getCitizens().add(citizen);
        return bankBranch;
    }

    @BeforeEach
    public void initTest() {
        bankBranch = createEntity(em);
    }

    @Test
    @Transactional
    void createBankBranch() throws Exception {
        int databaseSizeBeforeCreate = bankBranchRepository.findAll().size();
        // Create the BankBranch
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);
        restBankBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchDTO)))
            .andExpect(status().isCreated());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeCreate + 1);
        BankBranch testBankBranch = bankBranchList.get(bankBranchList.size() - 1);
        assertThat(testBankBranch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankBranch.getIfsc()).isEqualTo(DEFAULT_IFSC);
        assertThat(testBankBranch.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void createBankBranchWithExistingId() throws Exception {
        // Create the BankBranch with an existing ID
        bankBranch.setId(1L);
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        int databaseSizeBeforeCreate = bankBranchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankBranchRepository.findAll().size();
        // set the field null
        bankBranch.setName(null);

        // Create the BankBranch, which fails.
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        restBankBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchDTO)))
            .andExpect(status().isBadRequest());

        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIfscIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankBranchRepository.findAll().size();
        // set the field null
        bankBranch.setIfsc(null);

        // Create the BankBranch, which fails.
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        restBankBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchDTO)))
            .andExpect(status().isBadRequest());

        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankBranchRepository.findAll().size();
        // set the field null
        bankBranch.setAddress(null);

        // Create the BankBranch, which fails.
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        restBankBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchDTO)))
            .andExpect(status().isBadRequest());

        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankBranches() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

        // Get all the bankBranchList
        restBankBranchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankBranch.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ifsc").value(hasItem(DEFAULT_IFSC)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBankBranchesWithEagerRelationshipsIsEnabled() throws Exception {
        when(bankBranchServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBankBranchMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bankBranchServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBankBranchesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bankBranchServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBankBranchMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bankBranchServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBankBranch() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

        // Get the bankBranch
        restBankBranchMockMvc
            .perform(get(ENTITY_API_URL_ID, bankBranch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankBranch.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ifsc").value(DEFAULT_IFSC))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingBankBranch() throws Exception {
        // Get the bankBranch
        restBankBranchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankBranch() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();

        // Update the bankBranch
        BankBranch updatedBankBranch = bankBranchRepository.findById(bankBranch.getId()).get();
        // Disconnect from session so that the updates on updatedBankBranch are not directly saved in db
        em.detach(updatedBankBranch);
        updatedBankBranch.name(UPDATED_NAME).ifsc(UPDATED_IFSC).address(UPDATED_ADDRESS);
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(updatedBankBranch);

        restBankBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankBranchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
        BankBranch testBankBranch = bankBranchList.get(bankBranchList.size() - 1);
        assertThat(testBankBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankBranch.getIfsc()).isEqualTo(UPDATED_IFSC);
        assertThat(testBankBranch.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void putNonExistingBankBranch() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();
        bankBranch.setId(count.incrementAndGet());

        // Create the BankBranch
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankBranchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankBranch() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();
        bankBranch.setId(count.incrementAndGet());

        // Create the BankBranch
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankBranch() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();
        bankBranch.setId(count.incrementAndGet());

        // Create the BankBranch
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankBranchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankBranchWithPatch() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();

        // Update the bankBranch using partial update
        BankBranch partialUpdatedBankBranch = new BankBranch();
        partialUpdatedBankBranch.setId(bankBranch.getId());

        partialUpdatedBankBranch.name(UPDATED_NAME).ifsc(UPDATED_IFSC).address(UPDATED_ADDRESS);

        restBankBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankBranch))
            )
            .andExpect(status().isOk());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
        BankBranch testBankBranch = bankBranchList.get(bankBranchList.size() - 1);
        assertThat(testBankBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankBranch.getIfsc()).isEqualTo(UPDATED_IFSC);
        assertThat(testBankBranch.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void fullUpdateBankBranchWithPatch() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();

        // Update the bankBranch using partial update
        BankBranch partialUpdatedBankBranch = new BankBranch();
        partialUpdatedBankBranch.setId(bankBranch.getId());

        partialUpdatedBankBranch.name(UPDATED_NAME).ifsc(UPDATED_IFSC).address(UPDATED_ADDRESS);

        restBankBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankBranch))
            )
            .andExpect(status().isOk());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
        BankBranch testBankBranch = bankBranchList.get(bankBranchList.size() - 1);
        assertThat(testBankBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankBranch.getIfsc()).isEqualTo(UPDATED_IFSC);
        assertThat(testBankBranch.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void patchNonExistingBankBranch() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();
        bankBranch.setId(count.incrementAndGet());

        // Create the BankBranch
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankBranchDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankBranch() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();
        bankBranch.setId(count.incrementAndGet());

        // Create the BankBranch
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankBranch() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();
        bankBranch.setId(count.incrementAndGet());

        // Create the BankBranch
        BankBranchDTO bankBranchDTO = bankBranchMapper.toDto(bankBranch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankBranchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankBranchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankBranch() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

        int databaseSizeBeforeDelete = bankBranchRepository.findAll().size();

        // Delete the bankBranch
        restBankBranchMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankBranch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankBranch> bankBranchList = bankBranchRepository.findAll();
        assertThat(bankBranchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
