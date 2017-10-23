package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuOkul;
import com.okulservis.repository.OkuOkulRepository;
import com.okulservis.service.OkuOkulService;
import com.okulservis.repository.search.OkuOkulSearchRepository;
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
 * Test class for the OkuOkulResource REST controller.
 *
 * @see OkuOkulResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuOkulResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final String DEFAULT_ISIM = "AAAAAAAAAA";
    private static final String UPDATED_ISIM = "BBBBBBBBBB";

    private static final String DEFAULT_MUDUR_ISIM = "AAAAAAAAAA";
    private static final String UPDATED_MUDUR_ISIM = "BBBBBBBBBB";

    private static final String DEFAULT_MUDUR_TEL = "AAAAAAAAAA";
    private static final String UPDATED_MUDUR_TEL = "BBBBBBBBBB";

    @Autowired
    private OkuOkulRepository okuOkulRepository;

    @Autowired
    private OkuOkulService okuOkulService;

    @Autowired
    private OkuOkulSearchRepository okuOkulSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuOkulMockMvc;

    private OkuOkul okuOkul;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuOkulResource okuOkulResource = new OkuOkulResource(okuOkulService);
        this.restOkuOkulMockMvc = MockMvcBuilders.standaloneSetup(okuOkulResource)
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
    public static OkuOkul createEntity(EntityManager em) {
        OkuOkul okuOkul = new OkuOkul()
            .kod(DEFAULT_KOD)
            .isim(DEFAULT_ISIM)
            .mudurIsim(DEFAULT_MUDUR_ISIM)
            .mudurTel(DEFAULT_MUDUR_TEL);
        return okuOkul;
    }

    @Before
    public void initTest() {
        okuOkulSearchRepository.deleteAll();
        okuOkul = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuOkul() throws Exception {
        int databaseSizeBeforeCreate = okuOkulRepository.findAll().size();

        // Create the OkuOkul
        restOkuOkulMockMvc.perform(post("/api/oku-okuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuOkul)))
            .andExpect(status().isCreated());

        // Validate the OkuOkul in the database
        List<OkuOkul> okuOkulList = okuOkulRepository.findAll();
        assertThat(okuOkulList).hasSize(databaseSizeBeforeCreate + 1);
        OkuOkul testOkuOkul = okuOkulList.get(okuOkulList.size() - 1);
        assertThat(testOkuOkul.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testOkuOkul.getIsim()).isEqualTo(DEFAULT_ISIM);
        assertThat(testOkuOkul.getMudurIsim()).isEqualTo(DEFAULT_MUDUR_ISIM);
        assertThat(testOkuOkul.getMudurTel()).isEqualTo(DEFAULT_MUDUR_TEL);

        // Validate the OkuOkul in Elasticsearch
        OkuOkul okuOkulEs = okuOkulSearchRepository.findOne(testOkuOkul.getId());
        assertThat(okuOkulEs).isEqualToComparingFieldByField(testOkuOkul);
    }

    @Test
    @Transactional
    public void createOkuOkulWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuOkulRepository.findAll().size();

        // Create the OkuOkul with an existing ID
        okuOkul.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuOkulMockMvc.perform(post("/api/oku-okuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuOkul)))
            .andExpect(status().isBadRequest());

        // Validate the OkuOkul in the database
        List<OkuOkul> okuOkulList = okuOkulRepository.findAll();
        assertThat(okuOkulList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuOkuls() throws Exception {
        // Initialize the database
        okuOkulRepository.saveAndFlush(okuOkul);

        // Get all the okuOkulList
        restOkuOkulMockMvc.perform(get("/api/oku-okuls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuOkul.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].mudurIsim").value(hasItem(DEFAULT_MUDUR_ISIM.toString())))
            .andExpect(jsonPath("$.[*].mudurTel").value(hasItem(DEFAULT_MUDUR_TEL.toString())));
    }

    @Test
    @Transactional
    public void getOkuOkul() throws Exception {
        // Initialize the database
        okuOkulRepository.saveAndFlush(okuOkul);

        // Get the okuOkul
        restOkuOkulMockMvc.perform(get("/api/oku-okuls/{id}", okuOkul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuOkul.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.isim").value(DEFAULT_ISIM.toString()))
            .andExpect(jsonPath("$.mudurIsim").value(DEFAULT_MUDUR_ISIM.toString()))
            .andExpect(jsonPath("$.mudurTel").value(DEFAULT_MUDUR_TEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuOkul() throws Exception {
        // Get the okuOkul
        restOkuOkulMockMvc.perform(get("/api/oku-okuls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuOkul() throws Exception {
        // Initialize the database
        okuOkulService.save(okuOkul);

        int databaseSizeBeforeUpdate = okuOkulRepository.findAll().size();

        // Update the okuOkul
        OkuOkul updatedOkuOkul = okuOkulRepository.findOne(okuOkul.getId());
        updatedOkuOkul
            .kod(UPDATED_KOD)
            .isim(UPDATED_ISIM)
            .mudurIsim(UPDATED_MUDUR_ISIM)
            .mudurTel(UPDATED_MUDUR_TEL);

        restOkuOkulMockMvc.perform(put("/api/oku-okuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuOkul)))
            .andExpect(status().isOk());

        // Validate the OkuOkul in the database
        List<OkuOkul> okuOkulList = okuOkulRepository.findAll();
        assertThat(okuOkulList).hasSize(databaseSizeBeforeUpdate);
        OkuOkul testOkuOkul = okuOkulList.get(okuOkulList.size() - 1);
        assertThat(testOkuOkul.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testOkuOkul.getIsim()).isEqualTo(UPDATED_ISIM);
        assertThat(testOkuOkul.getMudurIsim()).isEqualTo(UPDATED_MUDUR_ISIM);
        assertThat(testOkuOkul.getMudurTel()).isEqualTo(UPDATED_MUDUR_TEL);

        // Validate the OkuOkul in Elasticsearch
        OkuOkul okuOkulEs = okuOkulSearchRepository.findOne(testOkuOkul.getId());
        assertThat(okuOkulEs).isEqualToComparingFieldByField(testOkuOkul);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuOkul() throws Exception {
        int databaseSizeBeforeUpdate = okuOkulRepository.findAll().size();

        // Create the OkuOkul

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuOkulMockMvc.perform(put("/api/oku-okuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuOkul)))
            .andExpect(status().isCreated());

        // Validate the OkuOkul in the database
        List<OkuOkul> okuOkulList = okuOkulRepository.findAll();
        assertThat(okuOkulList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuOkul() throws Exception {
        // Initialize the database
        okuOkulService.save(okuOkul);

        int databaseSizeBeforeDelete = okuOkulRepository.findAll().size();

        // Get the okuOkul
        restOkuOkulMockMvc.perform(delete("/api/oku-okuls/{id}", okuOkul.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuOkulExistsInEs = okuOkulSearchRepository.exists(okuOkul.getId());
        assertThat(okuOkulExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuOkul> okuOkulList = okuOkulRepository.findAll();
        assertThat(okuOkulList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuOkul() throws Exception {
        // Initialize the database
        okuOkulService.save(okuOkul);

        // Search the okuOkul
        restOkuOkulMockMvc.perform(get("/api/_search/oku-okuls?query=id:" + okuOkul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuOkul.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].mudurIsim").value(hasItem(DEFAULT_MUDUR_ISIM.toString())))
            .andExpect(jsonPath("$.[*].mudurTel").value(hasItem(DEFAULT_MUDUR_TEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuOkul.class);
        OkuOkul okuOkul1 = new OkuOkul();
        okuOkul1.setId(1L);
        OkuOkul okuOkul2 = new OkuOkul();
        okuOkul2.setId(okuOkul1.getId());
        assertThat(okuOkul1).isEqualTo(okuOkul2);
        okuOkul2.setId(2L);
        assertThat(okuOkul1).isNotEqualTo(okuOkul2);
        okuOkul1.setId(null);
        assertThat(okuOkul1).isNotEqualTo(okuOkul2);
    }
}
