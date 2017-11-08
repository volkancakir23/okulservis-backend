package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuVeli;
import com.okulservis.repository.OkuVeliRepository;
import com.okulservis.service.OkuVeliService;
import com.okulservis.repository.search.OkuVeliSearchRepository;
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
 * Test class for the OkuVeliResource REST controller.
 *
 * @see OkuVeliResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuVeliResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final String DEFAULT_ISIM = "AAAAAAAAAA";
    private static final String UPDATED_ISIM = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    @Autowired
    private OkuVeliRepository okuVeliRepository;

    @Autowired
    private OkuVeliService okuVeliService;

    @Autowired
    private OkuVeliSearchRepository okuVeliSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuVeliMockMvc;

    private OkuVeli okuVeli;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuVeliResource okuVeliResource = new OkuVeliResource(okuVeliService);
        this.restOkuVeliMockMvc = MockMvcBuilders.standaloneSetup(okuVeliResource)
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
    public static OkuVeli createEntity(EntityManager em) {
        OkuVeli okuVeli = new OkuVeli()
            .kod(DEFAULT_KOD)
            .isim(DEFAULT_ISIM)
            .tel(DEFAULT_TEL);
        return okuVeli;
    }

    @Before
    public void initTest() {
        okuVeliSearchRepository.deleteAll();
        okuVeli = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuVeli() throws Exception {
        int databaseSizeBeforeCreate = okuVeliRepository.findAll().size();

        // Create the OkuVeli
        restOkuVeliMockMvc.perform(post("/api/oku-velis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuVeli)))
            .andExpect(status().isCreated());

        // Validate the OkuVeli in the database
        List<OkuVeli> okuVeliList = okuVeliRepository.findAll();
        assertThat(okuVeliList).hasSize(databaseSizeBeforeCreate + 1);
        OkuVeli testOkuVeli = okuVeliList.get(okuVeliList.size() - 1);
        assertThat(testOkuVeli.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testOkuVeli.getIsim()).isEqualTo(DEFAULT_ISIM);
        assertThat(testOkuVeli.getTel()).isEqualTo(DEFAULT_TEL);

        // Validate the OkuVeli in Elasticsearch
        OkuVeli okuVeliEs = okuVeliSearchRepository.findOne(testOkuVeli.getId());
        assertThat(okuVeliEs).isEqualToComparingFieldByField(testOkuVeli);
    }

    @Test
    @Transactional
    public void createOkuVeliWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuVeliRepository.findAll().size();

        // Create the OkuVeli with an existing ID
        okuVeli.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuVeliMockMvc.perform(post("/api/oku-velis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuVeli)))
            .andExpect(status().isBadRequest());

        // Validate the OkuVeli in the database
        List<OkuVeli> okuVeliList = okuVeliRepository.findAll();
        assertThat(okuVeliList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuVelis() throws Exception {
        // Initialize the database
        okuVeliRepository.saveAndFlush(okuVeli);

        // Get all the okuVeliList
        restOkuVeliMockMvc.perform(get("/api/oku-velis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuVeli.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }

    @Test
    @Transactional
    public void getOkuVeli() throws Exception {
        // Initialize the database
        okuVeliRepository.saveAndFlush(okuVeli);

        // Get the okuVeli
        restOkuVeliMockMvc.perform(get("/api/oku-velis/{id}", okuVeli.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuVeli.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.isim").value(DEFAULT_ISIM.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuVeli() throws Exception {
        // Get the okuVeli
        restOkuVeliMockMvc.perform(get("/api/oku-velis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuVeli() throws Exception {
        // Initialize the database
        okuVeliService.save(okuVeli);

        int databaseSizeBeforeUpdate = okuVeliRepository.findAll().size();

        // Update the okuVeli
        OkuVeli updatedOkuVeli = okuVeliRepository.findOne(okuVeli.getId());
        updatedOkuVeli
            .kod(UPDATED_KOD)
            .isim(UPDATED_ISIM)
            .tel(UPDATED_TEL);

        restOkuVeliMockMvc.perform(put("/api/oku-velis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuVeli)))
            .andExpect(status().isOk());

        // Validate the OkuVeli in the database
        List<OkuVeli> okuVeliList = okuVeliRepository.findAll();
        assertThat(okuVeliList).hasSize(databaseSizeBeforeUpdate);
        OkuVeli testOkuVeli = okuVeliList.get(okuVeliList.size() - 1);
        assertThat(testOkuVeli.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testOkuVeli.getIsim()).isEqualTo(UPDATED_ISIM);
        assertThat(testOkuVeli.getTel()).isEqualTo(UPDATED_TEL);

        // Validate the OkuVeli in Elasticsearch
        OkuVeli okuVeliEs = okuVeliSearchRepository.findOne(testOkuVeli.getId());
        assertThat(okuVeliEs).isEqualToComparingFieldByField(testOkuVeli);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuVeli() throws Exception {
        int databaseSizeBeforeUpdate = okuVeliRepository.findAll().size();

        // Create the OkuVeli

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuVeliMockMvc.perform(put("/api/oku-velis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuVeli)))
            .andExpect(status().isCreated());

        // Validate the OkuVeli in the database
        List<OkuVeli> okuVeliList = okuVeliRepository.findAll();
        assertThat(okuVeliList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuVeli() throws Exception {
        // Initialize the database
        okuVeliService.save(okuVeli);

        int databaseSizeBeforeDelete = okuVeliRepository.findAll().size();

        // Get the okuVeli
        restOkuVeliMockMvc.perform(delete("/api/oku-velis/{id}", okuVeli.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuVeliExistsInEs = okuVeliSearchRepository.exists(okuVeli.getId());
        assertThat(okuVeliExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuVeli> okuVeliList = okuVeliRepository.findAll();
        assertThat(okuVeliList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuVeli() throws Exception {
        // Initialize the database
        okuVeliService.save(okuVeli);

        // Search the okuVeli
        restOkuVeliMockMvc.perform(get("/api/_search/oku-velis?query=id:" + okuVeli.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuVeli.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuVeli.class);
        OkuVeli okuVeli1 = new OkuVeli();
        okuVeli1.setId(1L);
        OkuVeli okuVeli2 = new OkuVeli();
        okuVeli2.setId(okuVeli1.getId());
        assertThat(okuVeli1).isEqualTo(okuVeli2);
        okuVeli2.setId(2L);
        assertThat(okuVeli1).isNotEqualTo(okuVeli2);
        okuVeli1.setId(null);
        assertThat(okuVeli1).isNotEqualTo(okuVeli2);
    }
}
