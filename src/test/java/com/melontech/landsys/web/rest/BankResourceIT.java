package com.melontech.landsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melontech.landsys.IntegrationTest;
import com.melontech.landsys.domain.Bank;
import com.melontech.landsys.domain.BankBranch;
import com.melontech.landsys.repository.BankRepository;
import com.melontech.landsys.service.dto.BankDTO;
import com.melontech.landsys.service.mapper.BankMapper;
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
 * Integration tests for the {@link BankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/banks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankMapper bankMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankMockMvc;

    private Bank bank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bank createEntity(EntityManager em) {
        Bank bank = new Bank().name(DEFAULT_NAME).code(DEFAULT_CODE);
        // Add required entity
        BankBranch bankBranch;
        if (TestUtil.findAll(em, BankBranch.class).isEmpty()) {
            bankBranch = BankBranchResourceIT.createEntity(em);
            em.persist(bankBranch);
            em.flush();
        } else {
            bankBranch = TestUtil.findAll(em, BankBranch.class).get(0);
        }
        bank.getBankNames().add(bankBranch);
        return bank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bank createUpdatedEntity(EntityManager em) {
        Bank bank = new Bank().name(UPDATED_NAME).code(UPDATED_CODE);
        // Add required entity
        BankBranch bankBranch;
        if (TestUtil.findAll(em, BankBranch.class).isEmpty()) {
            bankBranch = BankBranchResourceIT.createUpdatedEntity(em);
            em.persist(bankBranch);
            em.flush();
        } else {
            bankBranch = TestUtil.findAll(em, BankBranch.class).get(0);
        }
        bank.getBankNames().add(bankBranch);
        return bank;
    }

    @BeforeEach
    public void initTest() {
        bank = createEntity(em);
    }

    @Test
    @Transactional
    void createBank() throws Exception {
        int databaseSizeBeforeCreate = bankRepository.findAll().size();
        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);
        restBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isCreated());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeCreate + 1);
        Bank testBank = bankList.get(bankList.size() - 1);
        assertThat(testBank.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBank.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createBankWithExistingId() throws Exception {
        // Create the Bank with an existing ID
        bank.setId(1L);
        BankDTO bankDTO = bankMapper.toDto(bank);

        int databaseSizeBeforeCreate = bankRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankRepository.findAll().size();
        // set the field null
        bank.setName(null);

        // Create the Bank, which fails.
        BankDTO bankDTO = bankMapper.toDto(bank);

        restBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isBadRequest());

        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankRepository.findAll().size();
        // set the field null
        bank.setCode(null);

        // Create the Bank, which fails.
        BankDTO bankDTO = bankMapper.toDto(bank);

        restBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isBadRequest());

        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBanks() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList
        restBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getBank() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get the bank
        restBankMockMvc
            .perform(get(ENTITY_API_URL_ID, bank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bank.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingBank() throws Exception {
        // Get the bank
        restBankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBank() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        int databaseSizeBeforeUpdate = bankRepository.findAll().size();

        // Update the bank
        Bank updatedBank = bankRepository.findById(bank.getId()).get();
        // Disconnect from session so that the updates on updatedBank are not directly saved in db
        em.detach(updatedBank);
        updatedBank.name(UPDATED_NAME).code(UPDATED_CODE);
        BankDTO bankDTO = bankMapper.toDto(updatedBank);

        restBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
        Bank testBank = bankList.get(bankList.size() - 1);
        assertThat(testBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBank.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankWithPatch() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        int databaseSizeBeforeUpdate = bankRepository.findAll().size();

        // Update the bank using partial update
        Bank partialUpdatedBank = new Bank();
        partialUpdatedBank.setId(bank.getId());

        partialUpdatedBank.name(UPDATED_NAME).code(UPDATED_CODE);

        restBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBank))
            )
            .andExpect(status().isOk());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
        Bank testBank = bankList.get(bankList.size() - 1);
        assertThat(testBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBank.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateBankWithPatch() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        int databaseSizeBeforeUpdate = bankRepository.findAll().size();

        // Update the bank using partial update
        Bank partialUpdatedBank = new Bank();
        partialUpdatedBank.setId(bank.getId());

        partialUpdatedBank.name(UPDATED_NAME).code(UPDATED_CODE);

        restBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBank))
            )
            .andExpect(status().isOk());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
        Bank testBank = bankList.get(bankList.size() - 1);
        assertThat(testBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBank.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBank() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        int databaseSizeBeforeDelete = bankRepository.findAll().size();

        // Delete the bank
        restBankMockMvc
            .perform(delete(ENTITY_API_URL_ID, bank.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
