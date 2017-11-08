package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuYolcu;
import com.okulservis.repository.OkuYolcuRepository;
import com.okulservis.service.OkuYolcuService;
import com.okulservis.repository.search.OkuYolcuSearchRepository;
import com.okulservis.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.okulservis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OkuYolcuResource REST controller.
 *
 * @see OkuYolcuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuYolcuResourceIntTest {

    private static final Boolean DEFAULT_BINDI_MI = false;
    private static final Boolean UPDATED_BINDI_MI = true;

    private static final Boolean DEFAULT_ULASTI_MI = false;
    private static final Boolean UPDATED_ULASTI_MI = true;

    @Autowired
    private OkuYolcuRepository okuYolcuRepository;

    @Autowired
    private OkuYolcuService okuYolcuService;

    @Autowired
    private OkuYolcuSearchRepository okuYolcuSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuYolcuMockMvc;

    private OkuYolcu okuYolcu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuYolcuResource okuYolcuResource = new OkuYolcuResource(okuYolcuService);
        this.restOkuYolcuMockMvc = MockMvcBuilders.standaloneSetup(okuYolcuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OkuYolcu createEntity(EntityManager em) {
        OkuYolcu okuYolcu = new OkuYolcu()
            .bindiMi(DEFAULT_BINDI_MI)
            .ulastiMi(DEFAULT_ULASTI_MI);
        return okuYolcu;
    }

    @Before
    public void initTest() {
        okuYolcuSearchRepository.deleteAll();
        okuYolcu = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuYolcu() throws Exception {
        int databaseSizeBeforeCreate = okuYolcuRepository.findAll().size();

        // Create the OkuYolcu
        restOkuYolcuMockMvc.perform(post("/api/oku-yolcus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuYolcu)))
            .andExpect(status().isCreated());

        // Validate the OkuYolcu in the database
        List<OkuYolcu> okuYolcuList = okuYolcuRepository.findAll();
        assertThat(okuYolcuList).hasSize(databaseSizeBeforeCreate + 1);
        OkuYolcu testOkuYolcu = okuYolcuList.get(okuYolcuList.size() - 1);
        assertThat(testOkuYolcu.isBindiMi()).isEqualTo(DEFAULT_BINDI_MI);
        assertThat(testOkuYolcu.isUlastiMi()).isEqualTo(DEFAULT_ULASTI_MI);

        // Validate the OkuYolcu in Elasticsearch
        OkuYolcu okuYolcuEs = okuYolcuSearchRepository.findOne(testOkuYolcu.getId());
        assertThat(okuYolcuEs).isEqualToComparingFieldByField(testOkuYolcu);
    }

    @Test
    @Transactional
    public void createOkuYolcuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuYolcuRepository.findAll().size();

        // Create the OkuYolcu with an existing ID
        okuYolcu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuYolcuMockMvc.perform(post("/api/oku-yolcus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuYolcu)))
            .andExpect(status().isBadRequest());

        // Validate the OkuYolcu in the database
        List<OkuYolcu> okuYolcuList = okuYolcuRepository.findAll();
        assertThat(okuYolcuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuYolcus() throws Exception {
        // Initialize the database
        okuYolcuRepository.saveAndFlush(okuYolcu);

        // Get all the okuYolcuList
        restOkuYolcuMockMvc.perform(get("/api/oku-yolcus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuYolcu.getId().intValue())))
            .andExpect(jsonPath("$.[*].bindiMi").value(hasItem(DEFAULT_BINDI_MI.booleanValue())))
            .andExpect(jsonPath("$.[*].ulastiMi").value(hasItem(DEFAULT_ULASTI_MI.booleanValue())));
    }

    @Test
    @Transactional
    public void getOkuYolcu() throws Exception {
        // Initialize the database
        okuYolcuRepository.saveAndFlush(okuYolcu);

        // Get the okuYolcu
        restOkuYolcuMockMvc.perform(get("/api/oku-yolcus/{id}", okuYolcu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuYolcu.getId().intValue()))
            .andExpect(jsonPath("$.bindiMi").value(DEFAULT_BINDI_MI.booleanValue()))
            .andExpect(jsonPath("$.ulastiMi").value(DEFAULT_ULASTI_MI.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuYolcu() throws Exception {
        // Get the okuYolcu
        restOkuYolcuMockMvc.perform(get("/api/oku-yolcus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuYolcu() throws Exception {
        // Initialize the database
        okuYolcuService.save(okuYolcu);

        int databaseSizeBeforeUpdate = okuYolcuRepository.findAll().size();

        // Update the okuYolcu
        OkuYolcu updatedOkuYolcu = okuYolcuRepository.findOne(okuYolcu.getId());
        updatedOkuYolcu
            .bindiMi(UPDATED_BINDI_MI)
            .ulastiMi(UPDATED_ULASTI_MI);

        restOkuYolcuMockMvc.perform(put("/api/oku-yolcus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuYolcu)))
            .andExpect(status().isOk());

        // Validate the OkuYolcu in the database
        List<OkuYolcu> okuYolcuList = okuYolcuRepository.findAll();
        assertThat(okuYolcuList).hasSize(databaseSizeBeforeUpdate);
        OkuYolcu testOkuYolcu = okuYolcuList.get(okuYolcuList.size() - 1);
        assertThat(testOkuYolcu.isBindiMi()).isEqualTo(UPDATED_BINDI_MI);
        assertThat(testOkuYolcu.isUlastiMi()).isEqualTo(UPDATED_ULASTI_MI);

        // Validate the OkuYolcu in Elasticsearch
        OkuYolcu okuYolcuEs = okuYolcuSearchRepository.findOne(testOkuYolcu.getId());
        assertThat(okuYolcuEs).isEqualToComparingFieldByField(testOkuYolcu);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuYolcu() throws Exception {
        int databaseSizeBeforeUpdate = okuYolcuRepository.findAll().size();

        // Create the OkuYolcu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuYolcuMockMvc.perform(put("/api/oku-yolcus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuYolcu)))
            .andExpect(status().isCreated());

        // Validate the OkuYolcu in the database
        List<OkuYolcu> okuYolcuList = okuYolcuRepository.findAll();
        assertThat(okuYolcuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuYolcu() throws Exception {
        // Initialize the database
        okuYolcuService.save(okuYolcu);

        int databaseSizeBeforeDelete = okuYolcuRepository.findAll().size();

        // Get the okuYolcu
        restOkuYolcuMockMvc.perform(delete("/api/oku-yolcus/{id}", okuYolcu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuYolcuExistsInEs = okuYolcuSearchRepository.exists(okuYolcu.getId());
        assertThat(okuYolcuExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuYolcu> okuYolcuList = okuYolcuRepository.findAll();
        assertThat(okuYolcuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuYolcu() throws Exception {
        // Initialize the database
        okuYolcuService.save(okuYolcu);

        // Search the okuYolcu
        restOkuYolcuMockMvc.perform(get("/api/_search/oku-yolcus?query=id:" + okuYolcu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuYolcu.getId().intValue())))
            .andExpect(jsonPath("$.[*].bindiMi").value(hasItem(DEFAULT_BINDI_MI.booleanValue())))
            .andExpect(jsonPath("$.[*].ulastiMi").value(hasItem(DEFAULT_ULASTI_MI.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuYolcu.class);
        OkuYolcu okuYolcu1 = new OkuYolcu();
        okuYolcu1.setId(1L);
        OkuYolcu okuYolcu2 = new OkuYolcu();
        okuYolcu2.setId(okuYolcu1.getId());
        assertThat(okuYolcu1).isEqualTo(okuYolcu2);
        okuYolcu2.setId(2L);
        assertThat(okuYolcu1).isNotEqualTo(okuYolcu2);
        okuYolcu1.setId(null);
        assertThat(okuYolcu1).isNotEqualTo(okuYolcu2);
    }
}
