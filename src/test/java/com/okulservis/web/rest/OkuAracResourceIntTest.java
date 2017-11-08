package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuArac;
import com.okulservis.repository.OkuAracRepository;
import com.okulservis.service.OkuAracService;
import com.okulservis.repository.search.OkuAracSearchRepository;
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
 * Test class for the OkuAracResource REST controller.
 *
 * @see OkuAracResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuAracResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final String DEFAULT_PLAKA = "AAAAAAAAAA";
    private static final String UPDATED_PLAKA = "BBBBBBBBBB";

    private static final String DEFAULT_MARKA = "AAAAAAAAAA";
    private static final String UPDATED_MARKA = "BBBBBBBBBB";

    private static final String DEFAULT_RENK = "AAAAAAAAAA";
    private static final String UPDATED_RENK = "BBBBBBBBBB";

    @Autowired
    private OkuAracRepository okuAracRepository;

    @Autowired
    private OkuAracService okuAracService;

    @Autowired
    private OkuAracSearchRepository okuAracSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuAracMockMvc;

    private OkuArac okuArac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuAracResource okuAracResource = new OkuAracResource(okuAracService);
        this.restOkuAracMockMvc = MockMvcBuilders.standaloneSetup(okuAracResource)
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
    public static OkuArac createEntity(EntityManager em) {
        OkuArac okuArac = new OkuArac()
            .kod(DEFAULT_KOD)
            .plaka(DEFAULT_PLAKA)
            .marka(DEFAULT_MARKA)
            .renk(DEFAULT_RENK);
        return okuArac;
    }

    @Before
    public void initTest() {
        okuAracSearchRepository.deleteAll();
        okuArac = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuArac() throws Exception {
        int databaseSizeBeforeCreate = okuAracRepository.findAll().size();

        // Create the OkuArac
        restOkuAracMockMvc.perform(post("/api/oku-aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuArac)))
            .andExpect(status().isCreated());

        // Validate the OkuArac in the database
        List<OkuArac> okuAracList = okuAracRepository.findAll();
        assertThat(okuAracList).hasSize(databaseSizeBeforeCreate + 1);
        OkuArac testOkuArac = okuAracList.get(okuAracList.size() - 1);
        assertThat(testOkuArac.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testOkuArac.getPlaka()).isEqualTo(DEFAULT_PLAKA);
        assertThat(testOkuArac.getMarka()).isEqualTo(DEFAULT_MARKA);
        assertThat(testOkuArac.getRenk()).isEqualTo(DEFAULT_RENK);

        // Validate the OkuArac in Elasticsearch
        OkuArac okuAracEs = okuAracSearchRepository.findOne(testOkuArac.getId());
        assertThat(okuAracEs).isEqualToComparingFieldByField(testOkuArac);
    }

    @Test
    @Transactional
    public void createOkuAracWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuAracRepository.findAll().size();

        // Create the OkuArac with an existing ID
        okuArac.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuAracMockMvc.perform(post("/api/oku-aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuArac)))
            .andExpect(status().isBadRequest());

        // Validate the OkuArac in the database
        List<OkuArac> okuAracList = okuAracRepository.findAll();
        assertThat(okuAracList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuAracs() throws Exception {
        // Initialize the database
        okuAracRepository.saveAndFlush(okuArac);

        // Get all the okuAracList
        restOkuAracMockMvc.perform(get("/api/oku-aracs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuArac.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].plaka").value(hasItem(DEFAULT_PLAKA.toString())))
            .andExpect(jsonPath("$.[*].marka").value(hasItem(DEFAULT_MARKA.toString())))
            .andExpect(jsonPath("$.[*].renk").value(hasItem(DEFAULT_RENK.toString())));
    }

    @Test
    @Transactional
    public void getOkuArac() throws Exception {
        // Initialize the database
        okuAracRepository.saveAndFlush(okuArac);

        // Get the okuArac
        restOkuAracMockMvc.perform(get("/api/oku-aracs/{id}", okuArac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuArac.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.plaka").value(DEFAULT_PLAKA.toString()))
            .andExpect(jsonPath("$.marka").value(DEFAULT_MARKA.toString()))
            .andExpect(jsonPath("$.renk").value(DEFAULT_RENK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuArac() throws Exception {
        // Get the okuArac
        restOkuAracMockMvc.perform(get("/api/oku-aracs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuArac() throws Exception {
        // Initialize the database
        okuAracService.save(okuArac);

        int databaseSizeBeforeUpdate = okuAracRepository.findAll().size();

        // Update the okuArac
        OkuArac updatedOkuArac = okuAracRepository.findOne(okuArac.getId());
        updatedOkuArac
            .kod(UPDATED_KOD)
            .plaka(UPDATED_PLAKA)
            .marka(UPDATED_MARKA)
            .renk(UPDATED_RENK);

        restOkuAracMockMvc.perform(put("/api/oku-aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuArac)))
            .andExpect(status().isOk());

        // Validate the OkuArac in the database
        List<OkuArac> okuAracList = okuAracRepository.findAll();
        assertThat(okuAracList).hasSize(databaseSizeBeforeUpdate);
        OkuArac testOkuArac = okuAracList.get(okuAracList.size() - 1);
        assertThat(testOkuArac.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testOkuArac.getPlaka()).isEqualTo(UPDATED_PLAKA);
        assertThat(testOkuArac.getMarka()).isEqualTo(UPDATED_MARKA);
        assertThat(testOkuArac.getRenk()).isEqualTo(UPDATED_RENK);

        // Validate the OkuArac in Elasticsearch
        OkuArac okuAracEs = okuAracSearchRepository.findOne(testOkuArac.getId());
        assertThat(okuAracEs).isEqualToComparingFieldByField(testOkuArac);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuArac() throws Exception {
        int databaseSizeBeforeUpdate = okuAracRepository.findAll().size();

        // Create the OkuArac

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuAracMockMvc.perform(put("/api/oku-aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuArac)))
            .andExpect(status().isCreated());

        // Validate the OkuArac in the database
        List<OkuArac> okuAracList = okuAracRepository.findAll();
        assertThat(okuAracList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuArac() throws Exception {
        // Initialize the database
        okuAracService.save(okuArac);

        int databaseSizeBeforeDelete = okuAracRepository.findAll().size();

        // Get the okuArac
        restOkuAracMockMvc.perform(delete("/api/oku-aracs/{id}", okuArac.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuAracExistsInEs = okuAracSearchRepository.exists(okuArac.getId());
        assertThat(okuAracExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuArac> okuAracList = okuAracRepository.findAll();
        assertThat(okuAracList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuArac() throws Exception {
        // Initialize the database
        okuAracService.save(okuArac);

        // Search the okuArac
        restOkuAracMockMvc.perform(get("/api/_search/oku-aracs?query=id:" + okuArac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuArac.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].plaka").value(hasItem(DEFAULT_PLAKA.toString())))
            .andExpect(jsonPath("$.[*].marka").value(hasItem(DEFAULT_MARKA.toString())))
            .andExpect(jsonPath("$.[*].renk").value(hasItem(DEFAULT_RENK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuArac.class);
        OkuArac okuArac1 = new OkuArac();
        okuArac1.setId(1L);
        OkuArac okuArac2 = new OkuArac();
        okuArac2.setId(okuArac1.getId());
        assertThat(okuArac1).isEqualTo(okuArac2);
        okuArac2.setId(2L);
        assertThat(okuArac1).isNotEqualTo(okuArac2);
        okuArac1.setId(null);
        assertThat(okuArac1).isNotEqualTo(okuArac2);
    }
}
