package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuSefer;
import com.okulservis.repository.OkuSeferRepository;
import com.okulservis.service.OkuSeferService;
import com.okulservis.repository.search.OkuSeferSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.okulservis.domain.enumeration.OkuServis;
/**
 * Test class for the OkuSeferResource REST controller.
 *
 * @see OkuSeferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuSeferResourceIntTest {

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final OkuServis DEFAULT_SERVIS = OkuServis.SABAH;
    private static final OkuServis UPDATED_SERVIS = OkuServis.AKSAM;

    private static final Boolean DEFAULT_YAPILDI_MI = false;
    private static final Boolean UPDATED_YAPILDI_MI = true;

    @Autowired
    private OkuSeferRepository okuSeferRepository;

    @Autowired
    private OkuSeferService okuSeferService;

    @Autowired
    private OkuSeferSearchRepository okuSeferSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuSeferMockMvc;

    private OkuSefer okuSefer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuSeferResource okuSeferResource = new OkuSeferResource(okuSeferService);
        this.restOkuSeferMockMvc = MockMvcBuilders.standaloneSetup(okuSeferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OkuSefer createEntity(EntityManager em) {
        OkuSefer okuSefer = new OkuSefer()
            .tarih(DEFAULT_TARIH)
            .servis(DEFAULT_SERVIS)
            .yapildiMi(DEFAULT_YAPILDI_MI);
        return okuSefer;
    }

    @Before
    public void initTest() {
        okuSeferSearchRepository.deleteAll();
        okuSefer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuSefer() throws Exception {
        int databaseSizeBeforeCreate = okuSeferRepository.findAll().size();

        // Create the OkuSefer
        restOkuSeferMockMvc.perform(post("/api/oku-sefers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSefer)))
            .andExpect(status().isCreated());

        // Validate the OkuSefer in the database
        List<OkuSefer> okuSeferList = okuSeferRepository.findAll();
        assertThat(okuSeferList).hasSize(databaseSizeBeforeCreate + 1);
        OkuSefer testOkuSefer = okuSeferList.get(okuSeferList.size() - 1);
        assertThat(testOkuSefer.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testOkuSefer.getServis()).isEqualTo(DEFAULT_SERVIS);
        assertThat(testOkuSefer.isYapildiMi()).isEqualTo(DEFAULT_YAPILDI_MI);

        // Validate the OkuSefer in Elasticsearch
        OkuSefer okuSeferEs = okuSeferSearchRepository.findOne(testOkuSefer.getId());
        assertThat(okuSeferEs).isEqualToComparingFieldByField(testOkuSefer);
    }

    @Test
    @Transactional
    public void createOkuSeferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuSeferRepository.findAll().size();

        // Create the OkuSefer with an existing ID
        okuSefer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuSeferMockMvc.perform(post("/api/oku-sefers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSefer)))
            .andExpect(status().isBadRequest());

        // Validate the OkuSefer in the database
        List<OkuSefer> okuSeferList = okuSeferRepository.findAll();
        assertThat(okuSeferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuSefers() throws Exception {
        // Initialize the database
        okuSeferRepository.saveAndFlush(okuSefer);

        // Get all the okuSeferList
        restOkuSeferMockMvc.perform(get("/api/oku-sefers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuSefer.getId().intValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].servis").value(hasItem(DEFAULT_SERVIS.toString())))
            .andExpect(jsonPath("$.[*].yapildiMi").value(hasItem(DEFAULT_YAPILDI_MI.booleanValue())));
    }

    @Test
    @Transactional
    public void getOkuSefer() throws Exception {
        // Initialize the database
        okuSeferRepository.saveAndFlush(okuSefer);

        // Get the okuSefer
        restOkuSeferMockMvc.perform(get("/api/oku-sefers/{id}", okuSefer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuSefer.getId().intValue()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.servis").value(DEFAULT_SERVIS.toString()))
            .andExpect(jsonPath("$.yapildiMi").value(DEFAULT_YAPILDI_MI.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuSefer() throws Exception {
        // Get the okuSefer
        restOkuSeferMockMvc.perform(get("/api/oku-sefers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuSefer() throws Exception {
        // Initialize the database
        okuSeferService.save(okuSefer);

        int databaseSizeBeforeUpdate = okuSeferRepository.findAll().size();

        // Update the okuSefer
        OkuSefer updatedOkuSefer = okuSeferRepository.findOne(okuSefer.getId());
        updatedOkuSefer
            .tarih(UPDATED_TARIH)
            .servis(UPDATED_SERVIS)
            .yapildiMi(UPDATED_YAPILDI_MI);

        restOkuSeferMockMvc.perform(put("/api/oku-sefers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuSefer)))
            .andExpect(status().isOk());

        // Validate the OkuSefer in the database
        List<OkuSefer> okuSeferList = okuSeferRepository.findAll();
        assertThat(okuSeferList).hasSize(databaseSizeBeforeUpdate);
        OkuSefer testOkuSefer = okuSeferList.get(okuSeferList.size() - 1);
        assertThat(testOkuSefer.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testOkuSefer.getServis()).isEqualTo(UPDATED_SERVIS);
        assertThat(testOkuSefer.isYapildiMi()).isEqualTo(UPDATED_YAPILDI_MI);

        // Validate the OkuSefer in Elasticsearch
        OkuSefer okuSeferEs = okuSeferSearchRepository.findOne(testOkuSefer.getId());
        assertThat(okuSeferEs).isEqualToComparingFieldByField(testOkuSefer);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuSefer() throws Exception {
        int databaseSizeBeforeUpdate = okuSeferRepository.findAll().size();

        // Create the OkuSefer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuSeferMockMvc.perform(put("/api/oku-sefers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSefer)))
            .andExpect(status().isCreated());

        // Validate the OkuSefer in the database
        List<OkuSefer> okuSeferList = okuSeferRepository.findAll();
        assertThat(okuSeferList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuSefer() throws Exception {
        // Initialize the database
        okuSeferService.save(okuSefer);

        int databaseSizeBeforeDelete = okuSeferRepository.findAll().size();

        // Get the okuSefer
        restOkuSeferMockMvc.perform(delete("/api/oku-sefers/{id}", okuSefer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuSeferExistsInEs = okuSeferSearchRepository.exists(okuSefer.getId());
        assertThat(okuSeferExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuSefer> okuSeferList = okuSeferRepository.findAll();
        assertThat(okuSeferList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuSefer() throws Exception {
        // Initialize the database
        okuSeferService.save(okuSefer);

        // Search the okuSefer
        restOkuSeferMockMvc.perform(get("/api/_search/oku-sefers?query=id:" + okuSefer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuSefer.getId().intValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].servis").value(hasItem(DEFAULT_SERVIS.toString())))
            .andExpect(jsonPath("$.[*].yapildiMi").value(hasItem(DEFAULT_YAPILDI_MI.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuSefer.class);
        OkuSefer okuSefer1 = new OkuSefer();
        okuSefer1.setId(1L);
        OkuSefer okuSefer2 = new OkuSefer();
        okuSefer2.setId(okuSefer1.getId());
        assertThat(okuSefer1).isEqualTo(okuSefer2);
        okuSefer2.setId(2L);
        assertThat(okuSefer1).isNotEqualTo(okuSefer2);
        okuSefer1.setId(null);
        assertThat(okuSefer1).isNotEqualTo(okuSefer2);
    }
}
