package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuOgrenci;
import com.okulservis.repository.OkuOgrenciRepository;
import com.okulservis.service.OkuOgrenciService;
import com.okulservis.repository.search.OkuOgrenciSearchRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OkuOgrenciResource REST controller.
 *
 * @see OkuOgrenciResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuOgrenciResourceIntTest {

    private static final String DEFAULT_NO = "AAAAAAAAAA";
    private static final String UPDATED_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ISIM = "AAAAAAAAAA";
    private static final String UPDATED_ISIM = "BBBBBBBBBB";

    private static final String DEFAULT_TC = "AAAAAAAAAA";
    private static final String UPDATED_TC = "BBBBBBBBBB";

    private static final String DEFAULT_AILE_ISIM = "AAAAAAAAAA";
    private static final String UPDATED_AILE_ISIM = "BBBBBBBBBB";

    private static final String DEFAULT_AILE_TEL = "AAAAAAAAAA";
    private static final String UPDATED_AILE_TEL = "BBBBBBBBBB";

    @Autowired
    private OkuOgrenciRepository okuOgrenciRepository;

    @Autowired
    private OkuOgrenciService okuOgrenciService;

    @Autowired
    private OkuOgrenciSearchRepository okuOgrenciSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuOgrenciMockMvc;

    private OkuOgrenci okuOgrenci;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuOgrenciResource okuOgrenciResource = new OkuOgrenciResource(okuOgrenciService);
        this.restOkuOgrenciMockMvc = MockMvcBuilders.standaloneSetup(okuOgrenciResource)
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
    public static OkuOgrenci createEntity(EntityManager em) {
        OkuOgrenci okuOgrenci = new OkuOgrenci()
            .no(DEFAULT_NO)
            .isim(DEFAULT_ISIM)
            .tc(DEFAULT_TC)
            .aileIsim(DEFAULT_AILE_ISIM)
            .aileTel(DEFAULT_AILE_TEL);
        return okuOgrenci;
    }

    @Before
    public void initTest() {
        okuOgrenciSearchRepository.deleteAll();
        okuOgrenci = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuOgrenci() throws Exception {
        int databaseSizeBeforeCreate = okuOgrenciRepository.findAll().size();

        // Create the OkuOgrenci
        restOkuOgrenciMockMvc.perform(post("/api/oku-ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuOgrenci)))
            .andExpect(status().isCreated());

        // Validate the OkuOgrenci in the database
        List<OkuOgrenci> okuOgrenciList = okuOgrenciRepository.findAll();
        assertThat(okuOgrenciList).hasSize(databaseSizeBeforeCreate + 1);
        OkuOgrenci testOkuOgrenci = okuOgrenciList.get(okuOgrenciList.size() - 1);
        assertThat(testOkuOgrenci.getNo()).isEqualTo(DEFAULT_NO);
        assertThat(testOkuOgrenci.getIsim()).isEqualTo(DEFAULT_ISIM);
        assertThat(testOkuOgrenci.getTc()).isEqualTo(DEFAULT_TC);
        assertThat(testOkuOgrenci.getAileIsim()).isEqualTo(DEFAULT_AILE_ISIM);
        assertThat(testOkuOgrenci.getAileTel()).isEqualTo(DEFAULT_AILE_TEL);

        // Validate the OkuOgrenci in Elasticsearch
        OkuOgrenci okuOgrenciEs = okuOgrenciSearchRepository.findOne(testOkuOgrenci.getId());
        assertThat(okuOgrenciEs).isEqualToComparingFieldByField(testOkuOgrenci);
    }

    @Test
    @Transactional
    public void createOkuOgrenciWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuOgrenciRepository.findAll().size();

        // Create the OkuOgrenci with an existing ID
        okuOgrenci.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuOgrenciMockMvc.perform(post("/api/oku-ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuOgrenci)))
            .andExpect(status().isBadRequest());

        // Validate the OkuOgrenci in the database
        List<OkuOgrenci> okuOgrenciList = okuOgrenciRepository.findAll();
        assertThat(okuOgrenciList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuOgrencis() throws Exception {
        // Initialize the database
        okuOgrenciRepository.saveAndFlush(okuOgrenci);

        // Get all the okuOgrenciList
        restOkuOgrenciMockMvc.perform(get("/api/oku-ogrencis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuOgrenci.getId().intValue())))
            .andExpect(jsonPath("$.[*].no").value(hasItem(DEFAULT_NO.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].tc").value(hasItem(DEFAULT_TC.toString())))
            .andExpect(jsonPath("$.[*].aileIsim").value(hasItem(DEFAULT_AILE_ISIM.toString())))
            .andExpect(jsonPath("$.[*].aileTel").value(hasItem(DEFAULT_AILE_TEL.toString())));
    }

    @Test
    @Transactional
    public void getOkuOgrenci() throws Exception {
        // Initialize the database
        okuOgrenciRepository.saveAndFlush(okuOgrenci);

        // Get the okuOgrenci
        restOkuOgrenciMockMvc.perform(get("/api/oku-ogrencis/{id}", okuOgrenci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuOgrenci.getId().intValue()))
            .andExpect(jsonPath("$.no").value(DEFAULT_NO.toString()))
            .andExpect(jsonPath("$.isim").value(DEFAULT_ISIM.toString()))
            .andExpect(jsonPath("$.tc").value(DEFAULT_TC.toString()))
            .andExpect(jsonPath("$.aileIsim").value(DEFAULT_AILE_ISIM.toString()))
            .andExpect(jsonPath("$.aileTel").value(DEFAULT_AILE_TEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuOgrenci() throws Exception {
        // Get the okuOgrenci
        restOkuOgrenciMockMvc.perform(get("/api/oku-ogrencis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuOgrenci() throws Exception {
        // Initialize the database
        okuOgrenciService.save(okuOgrenci);

        int databaseSizeBeforeUpdate = okuOgrenciRepository.findAll().size();

        // Update the okuOgrenci
        OkuOgrenci updatedOkuOgrenci = okuOgrenciRepository.findOne(okuOgrenci.getId());
        updatedOkuOgrenci
            .no(UPDATED_NO)
            .isim(UPDATED_ISIM)
            .tc(UPDATED_TC)
            .aileIsim(UPDATED_AILE_ISIM)
            .aileTel(UPDATED_AILE_TEL);

        restOkuOgrenciMockMvc.perform(put("/api/oku-ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuOgrenci)))
            .andExpect(status().isOk());

        // Validate the OkuOgrenci in the database
        List<OkuOgrenci> okuOgrenciList = okuOgrenciRepository.findAll();
        assertThat(okuOgrenciList).hasSize(databaseSizeBeforeUpdate);
        OkuOgrenci testOkuOgrenci = okuOgrenciList.get(okuOgrenciList.size() - 1);
        assertThat(testOkuOgrenci.getNo()).isEqualTo(UPDATED_NO);
        assertThat(testOkuOgrenci.getIsim()).isEqualTo(UPDATED_ISIM);
        assertThat(testOkuOgrenci.getTc()).isEqualTo(UPDATED_TC);
        assertThat(testOkuOgrenci.getAileIsim()).isEqualTo(UPDATED_AILE_ISIM);
        assertThat(testOkuOgrenci.getAileTel()).isEqualTo(UPDATED_AILE_TEL);

        // Validate the OkuOgrenci in Elasticsearch
        OkuOgrenci okuOgrenciEs = okuOgrenciSearchRepository.findOne(testOkuOgrenci.getId());
        assertThat(okuOgrenciEs).isEqualToComparingFieldByField(testOkuOgrenci);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuOgrenci() throws Exception {
        int databaseSizeBeforeUpdate = okuOgrenciRepository.findAll().size();

        // Create the OkuOgrenci

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuOgrenciMockMvc.perform(put("/api/oku-ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuOgrenci)))
            .andExpect(status().isCreated());

        // Validate the OkuOgrenci in the database
        List<OkuOgrenci> okuOgrenciList = okuOgrenciRepository.findAll();
        assertThat(okuOgrenciList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuOgrenci() throws Exception {
        // Initialize the database
        okuOgrenciService.save(okuOgrenci);

        int databaseSizeBeforeDelete = okuOgrenciRepository.findAll().size();

        // Get the okuOgrenci
        restOkuOgrenciMockMvc.perform(delete("/api/oku-ogrencis/{id}", okuOgrenci.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuOgrenciExistsInEs = okuOgrenciSearchRepository.exists(okuOgrenci.getId());
        assertThat(okuOgrenciExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuOgrenci> okuOgrenciList = okuOgrenciRepository.findAll();
        assertThat(okuOgrenciList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuOgrenci() throws Exception {
        // Initialize the database
        okuOgrenciService.save(okuOgrenci);

        // Search the okuOgrenci
        restOkuOgrenciMockMvc.perform(get("/api/_search/oku-ogrencis?query=id:" + okuOgrenci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuOgrenci.getId().intValue())))
            .andExpect(jsonPath("$.[*].no").value(hasItem(DEFAULT_NO.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].tc").value(hasItem(DEFAULT_TC.toString())))
            .andExpect(jsonPath("$.[*].aileIsim").value(hasItem(DEFAULT_AILE_ISIM.toString())))
            .andExpect(jsonPath("$.[*].aileTel").value(hasItem(DEFAULT_AILE_TEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuOgrenci.class);
        OkuOgrenci okuOgrenci1 = new OkuOgrenci();
        okuOgrenci1.setId(1L);
        OkuOgrenci okuOgrenci2 = new OkuOgrenci();
        okuOgrenci2.setId(okuOgrenci1.getId());
        assertThat(okuOgrenci1).isEqualTo(okuOgrenci2);
        okuOgrenci2.setId(2L);
        assertThat(okuOgrenci1).isNotEqualTo(okuOgrenci2);
        okuOgrenci1.setId(null);
        assertThat(okuOgrenci1).isNotEqualTo(okuOgrenci2);
    }
}
