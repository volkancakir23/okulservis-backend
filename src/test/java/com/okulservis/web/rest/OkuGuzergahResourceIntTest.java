package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuGuzergah;
import com.okulservis.repository.OkuGuzergahRepository;
import com.okulservis.service.OkuGuzergahService;
import com.okulservis.repository.search.OkuGuzergahSearchRepository;
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
 * Test class for the OkuGuzergahResource REST controller.
 *
 * @see OkuGuzergahResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuGuzergahResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final String DEFAULT_ROTA = "AAAAAAAAAA";
    private static final String UPDATED_ROTA = "BBBBBBBBBB";

    private static final String DEFAULT_HARITA = "AAAAAAAAAA";
    private static final String UPDATED_HARITA = "BBBBBBBBBB";

    @Autowired
    private OkuGuzergahRepository okuGuzergahRepository;

    @Autowired
    private OkuGuzergahService okuGuzergahService;

    @Autowired
    private OkuGuzergahSearchRepository okuGuzergahSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuGuzergahMockMvc;

    private OkuGuzergah okuGuzergah;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuGuzergahResource okuGuzergahResource = new OkuGuzergahResource(okuGuzergahService);
        this.restOkuGuzergahMockMvc = MockMvcBuilders.standaloneSetup(okuGuzergahResource)
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
    public static OkuGuzergah createEntity(EntityManager em) {
        OkuGuzergah okuGuzergah = new OkuGuzergah()
            .kod(DEFAULT_KOD)
            .rota(DEFAULT_ROTA)
            .harita(DEFAULT_HARITA);
        return okuGuzergah;
    }

    @Before
    public void initTest() {
        okuGuzergahSearchRepository.deleteAll();
        okuGuzergah = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuGuzergah() throws Exception {
        int databaseSizeBeforeCreate = okuGuzergahRepository.findAll().size();

        // Create the OkuGuzergah
        restOkuGuzergahMockMvc.perform(post("/api/oku-guzergahs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuGuzergah)))
            .andExpect(status().isCreated());

        // Validate the OkuGuzergah in the database
        List<OkuGuzergah> okuGuzergahList = okuGuzergahRepository.findAll();
        assertThat(okuGuzergahList).hasSize(databaseSizeBeforeCreate + 1);
        OkuGuzergah testOkuGuzergah = okuGuzergahList.get(okuGuzergahList.size() - 1);
        assertThat(testOkuGuzergah.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testOkuGuzergah.getRota()).isEqualTo(DEFAULT_ROTA);
        assertThat(testOkuGuzergah.getHarita()).isEqualTo(DEFAULT_HARITA);

        // Validate the OkuGuzergah in Elasticsearch
        OkuGuzergah okuGuzergahEs = okuGuzergahSearchRepository.findOne(testOkuGuzergah.getId());
        assertThat(okuGuzergahEs).isEqualToComparingFieldByField(testOkuGuzergah);
    }

    @Test
    @Transactional
    public void createOkuGuzergahWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuGuzergahRepository.findAll().size();

        // Create the OkuGuzergah with an existing ID
        okuGuzergah.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuGuzergahMockMvc.perform(post("/api/oku-guzergahs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuGuzergah)))
            .andExpect(status().isBadRequest());

        // Validate the OkuGuzergah in the database
        List<OkuGuzergah> okuGuzergahList = okuGuzergahRepository.findAll();
        assertThat(okuGuzergahList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuGuzergahs() throws Exception {
        // Initialize the database
        okuGuzergahRepository.saveAndFlush(okuGuzergah);

        // Get all the okuGuzergahList
        restOkuGuzergahMockMvc.perform(get("/api/oku-guzergahs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuGuzergah.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].rota").value(hasItem(DEFAULT_ROTA.toString())))
            .andExpect(jsonPath("$.[*].harita").value(hasItem(DEFAULT_HARITA.toString())));
    }

    @Test
    @Transactional
    public void getOkuGuzergah() throws Exception {
        // Initialize the database
        okuGuzergahRepository.saveAndFlush(okuGuzergah);

        // Get the okuGuzergah
        restOkuGuzergahMockMvc.perform(get("/api/oku-guzergahs/{id}", okuGuzergah.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuGuzergah.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.rota").value(DEFAULT_ROTA.toString()))
            .andExpect(jsonPath("$.harita").value(DEFAULT_HARITA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuGuzergah() throws Exception {
        // Get the okuGuzergah
        restOkuGuzergahMockMvc.perform(get("/api/oku-guzergahs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuGuzergah() throws Exception {
        // Initialize the database
        okuGuzergahService.save(okuGuzergah);

        int databaseSizeBeforeUpdate = okuGuzergahRepository.findAll().size();

        // Update the okuGuzergah
        OkuGuzergah updatedOkuGuzergah = okuGuzergahRepository.findOne(okuGuzergah.getId());
        updatedOkuGuzergah
            .kod(UPDATED_KOD)
            .rota(UPDATED_ROTA)
            .harita(UPDATED_HARITA);

        restOkuGuzergahMockMvc.perform(put("/api/oku-guzergahs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuGuzergah)))
            .andExpect(status().isOk());

        // Validate the OkuGuzergah in the database
        List<OkuGuzergah> okuGuzergahList = okuGuzergahRepository.findAll();
        assertThat(okuGuzergahList).hasSize(databaseSizeBeforeUpdate);
        OkuGuzergah testOkuGuzergah = okuGuzergahList.get(okuGuzergahList.size() - 1);
        assertThat(testOkuGuzergah.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testOkuGuzergah.getRota()).isEqualTo(UPDATED_ROTA);
        assertThat(testOkuGuzergah.getHarita()).isEqualTo(UPDATED_HARITA);

        // Validate the OkuGuzergah in Elasticsearch
        OkuGuzergah okuGuzergahEs = okuGuzergahSearchRepository.findOne(testOkuGuzergah.getId());
        assertThat(okuGuzergahEs).isEqualToComparingFieldByField(testOkuGuzergah);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuGuzergah() throws Exception {
        int databaseSizeBeforeUpdate = okuGuzergahRepository.findAll().size();

        // Create the OkuGuzergah

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuGuzergahMockMvc.perform(put("/api/oku-guzergahs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuGuzergah)))
            .andExpect(status().isCreated());

        // Validate the OkuGuzergah in the database
        List<OkuGuzergah> okuGuzergahList = okuGuzergahRepository.findAll();
        assertThat(okuGuzergahList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuGuzergah() throws Exception {
        // Initialize the database
        okuGuzergahService.save(okuGuzergah);

        int databaseSizeBeforeDelete = okuGuzergahRepository.findAll().size();

        // Get the okuGuzergah
        restOkuGuzergahMockMvc.perform(delete("/api/oku-guzergahs/{id}", okuGuzergah.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuGuzergahExistsInEs = okuGuzergahSearchRepository.exists(okuGuzergah.getId());
        assertThat(okuGuzergahExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuGuzergah> okuGuzergahList = okuGuzergahRepository.findAll();
        assertThat(okuGuzergahList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuGuzergah() throws Exception {
        // Initialize the database
        okuGuzergahService.save(okuGuzergah);

        // Search the okuGuzergah
        restOkuGuzergahMockMvc.perform(get("/api/_search/oku-guzergahs?query=id:" + okuGuzergah.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuGuzergah.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].rota").value(hasItem(DEFAULT_ROTA.toString())))
            .andExpect(jsonPath("$.[*].harita").value(hasItem(DEFAULT_HARITA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuGuzergah.class);
        OkuGuzergah okuGuzergah1 = new OkuGuzergah();
        okuGuzergah1.setId(1L);
        OkuGuzergah okuGuzergah2 = new OkuGuzergah();
        okuGuzergah2.setId(okuGuzergah1.getId());
        assertThat(okuGuzergah1).isEqualTo(okuGuzergah2);
        okuGuzergah2.setId(2L);
        assertThat(okuGuzergah1).isNotEqualTo(okuGuzergah2);
        okuGuzergah1.setId(null);
        assertThat(okuGuzergah1).isNotEqualTo(okuGuzergah2);
    }
}
