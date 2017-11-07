package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.VotarFavoritoM;
import com.mycompany.myapp.repository.VotarFavoritoMRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VotarFavoritoMResource REST controller.
 *
 * @see VotarFavoritoMResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class VotarFavoritoMResourceIntTest {

    private static final ZonedDateTime DEFAULT_MOMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MOMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_VALORACIO = 1;
    private static final Integer UPDATED_VALORACIO = 2;

    @Autowired
    private VotarFavoritoMRepository votarFavoritoMRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVotarFavoritoMMockMvc;

    private VotarFavoritoM votarFavoritoM;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VotarFavoritoMResource votarFavoritoMResource = new VotarFavoritoMResource(votarFavoritoMRepository);
        this.restVotarFavoritoMMockMvc = MockMvcBuilders.standaloneSetup(votarFavoritoMResource)
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
    public static VotarFavoritoM createEntity(EntityManager em) {
        VotarFavoritoM votarFavoritoM = new VotarFavoritoM()
            .momento(DEFAULT_MOMENTO)
            .valoracio(DEFAULT_VALORACIO);
        return votarFavoritoM;
    }

    @Before
    public void initTest() {
        votarFavoritoM = createEntity(em);
    }

    @Test
    @Transactional
    public void createVotarFavoritoM() throws Exception {
        int databaseSizeBeforeCreate = votarFavoritoMRepository.findAll().size();

        // Create the VotarFavoritoM
        restVotarFavoritoMMockMvc.perform(post("/api/votar-favorito-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoM)))
            .andExpect(status().isCreated());

        // Validate the VotarFavoritoM in the database
        List<VotarFavoritoM> votarFavoritoMList = votarFavoritoMRepository.findAll();
        assertThat(votarFavoritoMList).hasSize(databaseSizeBeforeCreate + 1);
        VotarFavoritoM testVotarFavoritoM = votarFavoritoMList.get(votarFavoritoMList.size() - 1);
        assertThat(testVotarFavoritoM.getMomento()).isEqualTo(DEFAULT_MOMENTO);
        assertThat(testVotarFavoritoM.getValoracio()).isEqualTo(DEFAULT_VALORACIO);
    }

    @Test
    @Transactional
    public void createVotarFavoritoMWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = votarFavoritoMRepository.findAll().size();

        // Create the VotarFavoritoM with an existing ID
        votarFavoritoM.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVotarFavoritoMMockMvc.perform(post("/api/votar-favorito-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoM)))
            .andExpect(status().isBadRequest());

        // Validate the VotarFavoritoM in the database
        List<VotarFavoritoM> votarFavoritoMList = votarFavoritoMRepository.findAll();
        assertThat(votarFavoritoMList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVotarFavoritoMS() throws Exception {
        // Initialize the database
        votarFavoritoMRepository.saveAndFlush(votarFavoritoM);

        // Get all the votarFavoritoMList
        restVotarFavoritoMMockMvc.perform(get("/api/votar-favorito-ms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(votarFavoritoM.getId().intValue())))
            .andExpect(jsonPath("$.[*].momento").value(hasItem(sameInstant(DEFAULT_MOMENTO))))
            .andExpect(jsonPath("$.[*].valoracio").value(hasItem(DEFAULT_VALORACIO)));
    }

    @Test
    @Transactional
    public void getVotarFavoritoM() throws Exception {
        // Initialize the database
        votarFavoritoMRepository.saveAndFlush(votarFavoritoM);

        // Get the votarFavoritoM
        restVotarFavoritoMMockMvc.perform(get("/api/votar-favorito-ms/{id}", votarFavoritoM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(votarFavoritoM.getId().intValue()))
            .andExpect(jsonPath("$.momento").value(sameInstant(DEFAULT_MOMENTO)))
            .andExpect(jsonPath("$.valoracio").value(DEFAULT_VALORACIO));
    }

    @Test
    @Transactional
    public void getNonExistingVotarFavoritoM() throws Exception {
        // Get the votarFavoritoM
        restVotarFavoritoMMockMvc.perform(get("/api/votar-favorito-ms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVotarFavoritoM() throws Exception {
        // Initialize the database
        votarFavoritoMRepository.saveAndFlush(votarFavoritoM);
        int databaseSizeBeforeUpdate = votarFavoritoMRepository.findAll().size();

        // Update the votarFavoritoM
        VotarFavoritoM updatedVotarFavoritoM = votarFavoritoMRepository.findOne(votarFavoritoM.getId());
        updatedVotarFavoritoM
            .momento(UPDATED_MOMENTO)
            .valoracio(UPDATED_VALORACIO);

        restVotarFavoritoMMockMvc.perform(put("/api/votar-favorito-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVotarFavoritoM)))
            .andExpect(status().isOk());

        // Validate the VotarFavoritoM in the database
        List<VotarFavoritoM> votarFavoritoMList = votarFavoritoMRepository.findAll();
        assertThat(votarFavoritoMList).hasSize(databaseSizeBeforeUpdate);
        VotarFavoritoM testVotarFavoritoM = votarFavoritoMList.get(votarFavoritoMList.size() - 1);
        assertThat(testVotarFavoritoM.getMomento()).isEqualTo(UPDATED_MOMENTO);
        assertThat(testVotarFavoritoM.getValoracio()).isEqualTo(UPDATED_VALORACIO);
    }

    @Test
    @Transactional
    public void updateNonExistingVotarFavoritoM() throws Exception {
        int databaseSizeBeforeUpdate = votarFavoritoMRepository.findAll().size();

        // Create the VotarFavoritoM

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVotarFavoritoMMockMvc.perform(put("/api/votar-favorito-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoM)))
            .andExpect(status().isCreated());

        // Validate the VotarFavoritoM in the database
        List<VotarFavoritoM> votarFavoritoMList = votarFavoritoMRepository.findAll();
        assertThat(votarFavoritoMList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVotarFavoritoM() throws Exception {
        // Initialize the database
        votarFavoritoMRepository.saveAndFlush(votarFavoritoM);
        int databaseSizeBeforeDelete = votarFavoritoMRepository.findAll().size();

        // Get the votarFavoritoM
        restVotarFavoritoMMockMvc.perform(delete("/api/votar-favorito-ms/{id}", votarFavoritoM.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VotarFavoritoM> votarFavoritoMList = votarFavoritoMRepository.findAll();
        assertThat(votarFavoritoMList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VotarFavoritoM.class);
        VotarFavoritoM votarFavoritoM1 = new VotarFavoritoM();
        votarFavoritoM1.setId(1L);
        VotarFavoritoM votarFavoritoM2 = new VotarFavoritoM();
        votarFavoritoM2.setId(votarFavoritoM1.getId());
        assertThat(votarFavoritoM1).isEqualTo(votarFavoritoM2);
        votarFavoritoM2.setId(2L);
        assertThat(votarFavoritoM1).isNotEqualTo(votarFavoritoM2);
        votarFavoritoM1.setId(null);
        assertThat(votarFavoritoM1).isNotEqualTo(votarFavoritoM2);
    }
}
