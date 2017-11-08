package com.okulservis.web.rest;

import com.okulservis.OkulservisApp;

import com.okulservis.domain.OkuPersonel;
import com.okulservis.repository.OkuPersonelRepository;
import com.okulservis.service.OkuPersonelService;
import com.okulservis.repository.search.OkuPersonelSearchRepository;
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
 * Test class for the OkuPersonelResource REST controller.
 *
 * @see OkuPersonelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkulservisApp.class)
public class OkuPersonelResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final String DEFAULT_ISIM = "AAAAAAAAAA";
    private static final String UPDATED_ISIM = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    @Autowired
    private OkuPersonelRepository okuPersonelRepository;

    @Autowired
    private OkuPersonelService okuPersonelService;

    @Autowired
    private OkuPersonelSearchRepository okuPersonelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOkuPersonelMockMvc;

    private OkuPersonel okuPersonel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OkuPersonelResource okuPersonelResource = new OkuPersonelResource(okuPersonelService);
        this.restOkuPersonelMockMvc = MockMvcBuilders.standaloneSetup(okuPersonelResource)
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
    public static OkuPersonel createEntity(EntityManager em) {
        OkuPersonel okuPersonel = new OkuPersonel()
            .kod(DEFAULT_KOD)
            .isim(DEFAULT_ISIM)
            .tel(DEFAULT_TEL);
        return okuPersonel;
    }

    @Before
    public void initTest() {
        okuPersonelSearchRepository.deleteAll();
        okuPersonel = createEntity(em);
    }

    @Test
    @Transactional
    public void createOkuPersonel() throws Exception {
        int databaseSizeBeforeCreate = okuPersonelRepository.findAll().size();

        // Create the OkuPersonel
        restOkuPersonelMockMvc.perform(post("/api/oku-personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuPersonel)))
            .andExpect(status().isCreated());

        // Validate the OkuPersonel in the database
        List<OkuPersonel> okuPersonelList = okuPersonelRepository.findAll();
        assertThat(okuPersonelList).hasSize(databaseSizeBeforeCreate + 1);
        OkuPersonel testOkuPersonel = okuPersonelList.get(okuPersonelList.size() - 1);
        assertThat(testOkuPersonel.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testOkuPersonel.getIsim()).isEqualTo(DEFAULT_ISIM);
        assertThat(testOkuPersonel.getTel()).isEqualTo(DEFAULT_TEL);

        // Validate the OkuPersonel in Elasticsearch
        OkuPersonel okuPersonelEs = okuPersonelSearchRepository.findOne(testOkuPersonel.getId());
        assertThat(okuPersonelEs).isEqualToComparingFieldByField(testOkuPersonel);
    }

    @Test
    @Transactional
    public void createOkuPersonelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = okuPersonelRepository.findAll().size();

        // Create the OkuPersonel with an existing ID
        okuPersonel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOkuPersonelMockMvc.perform(post("/api/oku-personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuPersonel)))
            .andExpect(status().isBadRequest());

        // Validate the OkuPersonel in the database
        List<OkuPersonel> okuPersonelList = okuPersonelRepository.findAll();
        assertThat(okuPersonelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOkuPersonels() throws Exception {
        // Initialize the database
        okuPersonelRepository.saveAndFlush(okuPersonel);

        // Get all the okuPersonelList
        restOkuPersonelMockMvc.perform(get("/api/oku-personels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuPersonel.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }

    @Test
    @Transactional
    public void getOkuPersonel() throws Exception {
        // Initialize the database
        okuPersonelRepository.saveAndFlush(okuPersonel);

        // Get the okuPersonel
        restOkuPersonelMockMvc.perform(get("/api/oku-personels/{id}", okuPersonel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(okuPersonel.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.isim").value(DEFAULT_ISIM.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOkuPersonel() throws Exception {
        // Get the okuPersonel
        restOkuPersonelMockMvc.perform(get("/api/oku-personels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOkuPersonel() throws Exception {
        // Initialize the database
        okuPersonelService.save(okuPersonel);

        int databaseSizeBeforeUpdate = okuPersonelRepository.findAll().size();

        // Update the okuPersonel
        OkuPersonel updatedOkuPersonel = okuPersonelRepository.findOne(okuPersonel.getId());
        updatedOkuPersonel
            .kod(UPDATED_KOD)
            .isim(UPDATED_ISIM)
            .tel(UPDATED_TEL);

        restOkuPersonelMockMvc.perform(put("/api/oku-personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOkuPersonel)))
            .andExpect(status().isOk());

        // Validate the OkuPersonel in the database
        List<OkuPersonel> okuPersonelList = okuPersonelRepository.findAll();
        assertThat(okuPersonelList).hasSize(databaseSizeBeforeUpdate);
        OkuPersonel testOkuPersonel = okuPersonelList.get(okuPersonelList.size() - 1);
        assertThat(testOkuPersonel.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testOkuPersonel.getIsim()).isEqualTo(UPDATED_ISIM);
        assertThat(testOkuPersonel.getTel()).isEqualTo(UPDATED_TEL);

        // Validate the OkuPersonel in Elasticsearch
        OkuPersonel okuPersonelEs = okuPersonelSearchRepository.findOne(testOkuPersonel.getId());
        assertThat(okuPersonelEs).isEqualToComparingFieldByField(testOkuPersonel);
    }

    @Test
    @Transactional
    public void updateNonExistingOkuPersonel() throws Exception {
        int databaseSizeBeforeUpdate = okuPersonelRepository.findAll().size();

        // Create the OkuPersonel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOkuPersonelMockMvc.perform(put("/api/oku-personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(okuPersonel)))
            .andExpect(status().isCreated());

        // Validate the OkuPersonel in the database
        List<OkuPersonel> okuPersonelList = okuPersonelRepository.findAll();
        assertThat(okuPersonelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOkuPersonel() throws Exception {
        // Initialize the database
        okuPersonelService.save(okuPersonel);

        int databaseSizeBeforeDelete = okuPersonelRepository.findAll().size();

        // Get the okuPersonel
        restOkuPersonelMockMvc.perform(delete("/api/oku-personels/{id}", okuPersonel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean okuPersonelExistsInEs = okuPersonelSearchRepository.exists(okuPersonel.getId());
        assertThat(okuPersonelExistsInEs).isFalse();

        // Validate the database is empty
        List<OkuPersonel> okuPersonelList = okuPersonelRepository.findAll();
        assertThat(okuPersonelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOkuPersonel() throws Exception {
        // Initialize the database
        okuPersonelService.save(okuPersonel);

        // Search the okuPersonel
        restOkuPersonelMockMvc.perform(get("/api/_search/oku-personels?query=id:" + okuPersonel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(okuPersonel.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].isim").value(hasItem(DEFAULT_ISIM.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OkuPersonel.class);
        OkuPersonel okuPersonel1 = new OkuPersonel();
        okuPersonel1.setId(1L);
        OkuPersonel okuPersonel2 = new OkuPersonel();
        okuPersonel2.setId(okuPersonel1.getId());
        assertThat(okuPersonel1).isEqualTo(okuPersonel2);
        okuPersonel2.setId(2L);
        assertThat(okuPersonel1).isNotEqualTo(okuPersonel2);
        okuPersonel1.setId(null);
        assertThat(okuPersonel1).isNotEqualTo(okuPersonel2);
    }
}
