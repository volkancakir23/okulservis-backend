package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuSehir;
import com.okulservis.repository.OkuSehirRepository;
import com.okulservis.service.OkuSehirService;
import com.okulservis.repository.search.OkuSehirSearchRepository;
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
 * Test class for the OkuSehirResource REST controller.
 *
 * @see OkuSehirResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuSehirResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final String DEFAULT_ISIM = "AAAAAAAAAA";
    private static final String UPDATED_ISIM = "BBBBBBBBBB";

    @Autowired
    private OkuSehirRepository okuSehirRepository;

    @Autowired
    private OkuSehirService okuSehirService;

    @Autowired
    private OkuSehirSearchRepository okuSehirSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuSehirMockMvc;

    private OkuSehir okuSehir;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuSehirResource okuSehirResource = new OkuSehirResource(okuSehirService);
        this.restOkuSehirMockMvc = MockMvcBuilders.standaloneSetup(okuSehirResource)
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
    public static OkuSehir createEntity(EntityManager em) {
        OkuSehir okuSehir = new OkuSehir()
            .kod(DEFAULT_KOD)
            .isim(DEFAULT_ISIM);
        return okuSehir;
    }

    @Before
    public void initTest() {
        okuSehirSearchRepository.deleteAll();
        okuSehir = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuSehir() throws Exception {
        int databaseSizeBeforeCreate = okuSehirRepository.findAll().size();

        // Create the OkuSehir
        restOkuSehirMockMvc.perform(post("/api/oku-sehirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSehir)))
            .andExpect(status().isCreated());

        // Validate the OkuSehir in the database
        List<OkuSehir> okuSehirList = okuSehirRepository.findAll();
        assertThat(okuSehirList).hasSize(databaseSizeBeforeCreate + 1);
        OkuSehir testOkuSehir = okuSehirList.get(okuSehirList.size() - 1);
        assertThat(testOkuSehir.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testOkuSehir.getIsim()).isEqualTo(DEFAULT_ISIM);

        // Validate the OkuSehir in Elasticsearch
        OkuSehir okuSehirEs = okuSehirSearchRepository.findOne(testOkuSehir.getId());
        assertThat(okuSehirEs).isEqualToComparingFieldByField(testOkuSehir);
    }

    @Test
    @Transactional
    public void createOkuSehirWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuSehirRepository.findAll().size();

        // Create the OkuSehir with an existing ID
        okuSehir.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuSehirMockMvc.perform(post("/api/oku-sehirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSehir)))
            .andExpect(status().isBadRequest());

        // Validate the OkuSehir in the database
        List<OkuSehir> okuSehirList = okuSehirRepository.findAll();
        assertThat(okuSehirList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuSehirs() throws Exception {
        // Initialize the database
        okuSehirRepository.saveAndFlush(okuSehir);

        // Get all the okuSehirList
        restOkuSehirMockMvc.perform(get("/api/oku-sehirs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuSehir.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())));
    }

    @Test
    @Transactional
    public void getOkuSehir() throws Exception {
        // Initialize the database
        okuSehirRepository.saveAndFlush(okuSehir);

        // Get the okuSehir
        restOkuSehirMockMvc.perform(get("/api/oku-sehirs/{id}", okuSehir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuSehir.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.isim").value(DEFAULT_ISIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuSehir() throws Exception {
        // Get the okuSehir
        restOkuSehirMockMvc.perform(get("/api/oku-sehirs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuSehir() throws Exception {
        // Initialize the database
        okuSehirService.save(okuSehir);

        int databaseSizeBeforeUpdate = okuSehirRepository.findAll().size();

        // Update the okuSehir
        OkuSehir updatedOkuSehir = okuSehirRepository.findOne(okuSehir.getId());
        updatedOkuSehir
            .kod(UPDATED_KOD)
            .isim(UPDATED_ISIM);

        restOkuSehirMockMvc.perform(put("/api/oku-sehirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuSehir)))
            .andExpect(status().isOk());

        // Validate the OkuSehir in the database
        List<OkuSehir> okuSehirList = okuSehirRepository.findAll();
        assertThat(okuSehirList).hasSize(databaseSizeBeforeUpdate);
        OkuSehir testOkuSehir = okuSehirList.get(okuSehirList.size() - 1);
        assertThat(testOkuSehir.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testOkuSehir.getIsim()).isEqualTo(UPDATED_ISIM);

        // Validate the OkuSehir in Elasticsearch
        OkuSehir okuSehirEs = okuSehirSearchRepository.findOne(testOkuSehir.getId());
        assertThat(okuSehirEs).isEqualToComparingFieldByField(testOkuSehir);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuSehir() throws Exception {
        int databaseSizeBeforeUpdate = okuSehirRepository.findAll().size();

        // Create the OkuSehir

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuSehirMockMvc.perform(put("/api/oku-sehirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSehir)))
            .andExpect(status().isCreated());

        // Validate the OkuSehir in the database
        List<OkuSehir> okuSehirList = okuSehirRepository.findAll();
        assertThat(okuSehirList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuSehir() throws Exception {
        // Initialize the database
        okuSehirService.save(okuSehir);

        int databaseSizeBeforeDelete = okuSehirRepository.findAll().size();

        // Get the okuSehir
        restOkuSehirMockMvc.perform(delete("/api/oku-sehirs/{id}", okuSehir.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuSehirExistsInEs = okuSehirSearchRepository.exists(okuSehir.getId());
        assertThat(okuSehirExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuSehir> okuSehirList = okuSehirRepository.findAll();
        assertThat(okuSehirList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuSehir() throws Exception {
        // Initialize the database
        okuSehirService.save(okuSehir);

        // Search the okuSehir
        restOkuSehirMockMvc.perform(get("/api/_search/oku-sehirs?query=id:" + okuSehir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuSehir.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuSehir.class);
        OkuSehir okuSehir1 = new OkuSehir();
        okuSehir1.setId(1L);
        OkuSehir okuSehir2 = new OkuSehir();
        okuSehir2.setId(okuSehir1.getId());
        assertThat(okuSehir1).isEqualTo(okuSehir2);
        okuSehir2.setId(2L);
        assertThat(okuSehir1).isNotEqualTo(okuSehir2);
        okuSehir1.setId(null);
        assertThat(okuSehir1).isNotEqualTo(okuSehir2);
    }
}
