package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuSofor;
import com.okulservis.repository.OkuSoforRepository;
import com.okulservis.service.OkuSoforService;
import com.okulservis.repository.search.OkuSoforSearchRepository;
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
 * Test class for the OkuSoforResource REST controller.
 *
 * @see OkuSoforResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuSoforResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final String DEFAULT_ISIM = "AAAAAAAAAA";
    private static final String UPDATED_ISIM = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    @Autowired
    private OkuSoforRepository okuSoforRepository;

    @Autowired
    private OkuSoforService okuSoforService;

    @Autowired
    private OkuSoforSearchRepository okuSoforSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuSoforMockMvc;

    private OkuSofor okuSofor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuSoforResource okuSoforResource = new OkuSoforResource(okuSoforService);
        this.restOkuSoforMockMvc = MockMvcBuilders.standaloneSetup(okuSoforResource)
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
    public static OkuSofor createEntity(EntityManager em) {
        OkuSofor okuSofor = new OkuSofor()
            .kod(DEFAULT_KOD)
            .isim(DEFAULT_ISIM)
            .tel(DEFAULT_TEL);
        return okuSofor;
    }

    @Before
    public void initTest() {
        okuSoforSearchRepository.deleteAll();
        okuSofor = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuSofor() throws Exception {
        int databaseSizeBeforeCreate = okuSoforRepository.findAll().size();

        // Create the OkuSofor
        restOkuSoforMockMvc.perform(post("/api/oku-sofors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSofor)))
            .andExpect(status().isCreated());

        // Validate the OkuSofor in the database
        List<OkuSofor> okuSoforList = okuSoforRepository.findAll();
        assertThat(okuSoforList).hasSize(databaseSizeBeforeCreate + 1);
        OkuSofor testOkuSofor = okuSoforList.get(okuSoforList.size() - 1);
        assertThat(testOkuSofor.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testOkuSofor.getIsim()).isEqualTo(DEFAULT_ISIM);
        assertThat(testOkuSofor.getTel()).isEqualTo(DEFAULT_TEL);

        // Validate the OkuSofor in Elasticsearch
        OkuSofor okuSoforEs = okuSoforSearchRepository.findOne(testOkuSofor.getId());
        assertThat(okuSoforEs).isEqualToComparingFieldByField(testOkuSofor);
    }

    @Test
    @Transactional
    public void createOkuSoforWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuSoforRepository.findAll().size();

        // Create the OkuSofor with an existing ID
        okuSofor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuSoforMockMvc.perform(post("/api/oku-sofors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSofor)))
            .andExpect(status().isBadRequest());

        // Validate the OkuSofor in the database
        List<OkuSofor> okuSoforList = okuSoforRepository.findAll();
        assertThat(okuSoforList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuSofors() throws Exception {
        // Initialize the database
        okuSoforRepository.saveAndFlush(okuSofor);

        // Get all the okuSoforList
        restOkuSoforMockMvc.perform(get("/api/oku-sofors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuSofor.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }

    @Test
    @Transactional
    public void getOkuSofor() throws Exception {
        // Initialize the database
        okuSoforRepository.saveAndFlush(okuSofor);

        // Get the okuSofor
        restOkuSoforMockMvc.perform(get("/api/oku-sofors/{id}", okuSofor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuSofor.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.isim").value(DEFAULT_ISIM.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuSofor() throws Exception {
        // Get the okuSofor
        restOkuSoforMockMvc.perform(get("/api/oku-sofors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuSofor() throws Exception {
        // Initialize the database
        okuSoforService.save(okuSofor);

        int databaseSizeBeforeUpdate = okuSoforRepository.findAll().size();

        // Update the okuSofor
        OkuSofor updatedOkuSofor = okuSoforRepository.findOne(okuSofor.getId());
        updatedOkuSofor
            .kod(UPDATED_KOD)
            .isim(UPDATED_ISIM)
            .tel(UPDATED_TEL);

        restOkuSoforMockMvc.perform(put("/api/oku-sofors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuSofor)))
            .andExpect(status().isOk());

        // Validate the OkuSofor in the database
        List<OkuSofor> okuSoforList = okuSoforRepository.findAll();
        assertThat(okuSoforList).hasSize(databaseSizeBeforeUpdate);
        OkuSofor testOkuSofor = okuSoforList.get(okuSoforList.size() - 1);
        assertThat(testOkuSofor.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testOkuSofor.getIsim()).isEqualTo(UPDATED_ISIM);
        assertThat(testOkuSofor.getTel()).isEqualTo(UPDATED_TEL);

        // Validate the OkuSofor in Elasticsearch
        OkuSofor okuSoforEs = okuSoforSearchRepository.findOne(testOkuSofor.getId());
        assertThat(okuSoforEs).isEqualToComparingFieldByField(testOkuSofor);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuSofor() throws Exception {
        int databaseSizeBeforeUpdate = okuSoforRepository.findAll().size();

        // Create the OkuSofor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuSoforMockMvc.perform(put("/api/oku-sofors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuSofor)))
            .andExpect(status().isCreated());

        // Validate the OkuSofor in the database
        List<OkuSofor> okuSoforList = okuSoforRepository.findAll();
        assertThat(okuSoforList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuSofor() throws Exception {
        // Initialize the database
        okuSoforService.save(okuSofor);

        int databaseSizeBeforeDelete = okuSoforRepository.findAll().size();

        // Get the okuSofor
        restOkuSoforMockMvc.perform(delete("/api/oku-sofors/{id}", okuSofor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuSoforExistsInEs = okuSoforSearchRepository.exists(okuSofor.getId());
        assertThat(okuSoforExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuSofor> okuSoforList = okuSoforRepository.findAll();
        assertThat(okuSoforList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuSofor() throws Exception {
        // Initialize the database
        okuSoforService.save(okuSofor);

        // Search the okuSofor
        restOkuSoforMockMvc.perform(get("/api/_search/oku-sofors?query=id:" + okuSofor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuSofor.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuSofor.class);
        OkuSofor okuSofor1 = new OkuSofor();
        okuSofor1.setId(1L);
        OkuSofor okuSofor2 = new OkuSofor();
        okuSofor2.setId(okuSofor1.getId());
        assertThat(okuSofor1).isEqualTo(okuSofor2);
        okuSofor2.setId(2L);
        assertThat(okuSofor1).isNotEqualTo(okuSofor2);
        okuSofor1.setId(null);
        assertThat(okuSofor1).isNotEqualTo(okuSofor2);
    }
}
