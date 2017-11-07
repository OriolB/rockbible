package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.VotarFavoritoC;
import com.mycompany.myapp.repository.VotarFavoritoCRepository;
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
 * Test class for the VotarFavoritoCResource REST controller.
 *
 * @see VotarFavoritoCResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class VotarFavoritoCResourceIntTest {

    private static final ZonedDateTime DEFAULT_MOMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MOMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_VALORACIO = 1;
    private static final Integer UPDATED_VALORACIO = 2;

    @Autowired
    private VotarFavoritoCRepository votarFavoritoCRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVotarFavoritoCMockMvc;

    private VotarFavoritoC votarFavoritoC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VotarFavoritoCResource votarFavoritoCResource = new VotarFavoritoCResource(votarFavoritoCRepository);
        this.restVotarFavoritoCMockMvc = MockMvcBuilders.standaloneSetup(votarFavoritoCResource)
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
    public static VotarFavoritoC createEntity(EntityManager em) {
        VotarFavoritoC votarFavoritoC = new VotarFavoritoC()
            .momento(DEFAULT_MOMENTO)
            .valoracio(DEFAULT_VALORACIO);
        return votarFavoritoC;
    }

    @Before
    public void initTest() {
        votarFavoritoC = createEntity(em);
    }

    @Test
    @Transactional
    public void createVotarFavoritoC() throws Exception {
        int databaseSizeBeforeCreate = votarFavoritoCRepository.findAll().size();

        // Create the VotarFavoritoC
        restVotarFavoritoCMockMvc.perform(post("/api/votar-favorito-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoC)))
            .andExpect(status().isCreated());

        // Validate the VotarFavoritoC in the database
        List<VotarFavoritoC> votarFavoritoCList = votarFavoritoCRepository.findAll();
        assertThat(votarFavoritoCList).hasSize(databaseSizeBeforeCreate + 1);
        VotarFavoritoC testVotarFavoritoC = votarFavoritoCList.get(votarFavoritoCList.size() - 1);
        assertThat(testVotarFavoritoC.getMomento()).isEqualTo(DEFAULT_MOMENTO);
        assertThat(testVotarFavoritoC.getValoracio()).isEqualTo(DEFAULT_VALORACIO);
    }

    @Test
    @Transactional
    public void createVotarFavoritoCWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = votarFavoritoCRepository.findAll().size();

        // Create the VotarFavoritoC with an existing ID
        votarFavoritoC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVotarFavoritoCMockMvc.perform(post("/api/votar-favorito-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoC)))
            .andExpect(status().isBadRequest());

        // Validate the VotarFavoritoC in the database
        List<VotarFavoritoC> votarFavoritoCList = votarFavoritoCRepository.findAll();
        assertThat(votarFavoritoCList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVotarFavoritoCS() throws Exception {
        // Initialize the database
        votarFavoritoCRepository.saveAndFlush(votarFavoritoC);

        // Get all the votarFavoritoCList
        restVotarFavoritoCMockMvc.perform(get("/api/votar-favorito-cs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(votarFavoritoC.getId().intValue())))
            .andExpect(jsonPath("$.[*].momento").value(hasItem(sameInstant(DEFAULT_MOMENTO))))
            .andExpect(jsonPath("$.[*].valoracio").value(hasItem(DEFAULT_VALORACIO)));
    }

    @Test
    @Transactional
    public void getVotarFavoritoC() throws Exception {
        // Initialize the database
        votarFavoritoCRepository.saveAndFlush(votarFavoritoC);

        // Get the votarFavoritoC
        restVotarFavoritoCMockMvc.perform(get("/api/votar-favorito-cs/{id}", votarFavoritoC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(votarFavoritoC.getId().intValue()))
            .andExpect(jsonPath("$.momento").value(sameInstant(DEFAULT_MOMENTO)))
            .andExpect(jsonPath("$.valoracio").value(DEFAULT_VALORACIO));
    }

    @Test
    @Transactional
    public void getNonExistingVotarFavoritoC() throws Exception {
        // Get the votarFavoritoC
        restVotarFavoritoCMockMvc.perform(get("/api/votar-favorito-cs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVotarFavoritoC() throws Exception {
        // Initialize the database
        votarFavoritoCRepository.saveAndFlush(votarFavoritoC);
        int databaseSizeBeforeUpdate = votarFavoritoCRepository.findAll().size();

        // Update the votarFavoritoC
        VotarFavoritoC updatedVotarFavoritoC = votarFavoritoCRepository.findOne(votarFavoritoC.getId());
        updatedVotarFavoritoC
            .momento(UPDATED_MOMENTO)
            .valoracio(UPDATED_VALORACIO);

        restVotarFavoritoCMockMvc.perform(put("/api/votar-favorito-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVotarFavoritoC)))
            .andExpect(status().isOk());

        // Validate the VotarFavoritoC in the database
        List<VotarFavoritoC> votarFavoritoCList = votarFavoritoCRepository.findAll();
        assertThat(votarFavoritoCList).hasSize(databaseSizeBeforeUpdate);
        VotarFavoritoC testVotarFavoritoC = votarFavoritoCList.get(votarFavoritoCList.size() - 1);
        assertThat(testVotarFavoritoC.getMomento()).isEqualTo(UPDATED_MOMENTO);
        assertThat(testVotarFavoritoC.getValoracio()).isEqualTo(UPDATED_VALORACIO);
    }

    @Test
    @Transactional
    public void updateNonExistingVotarFavoritoC() throws Exception {
        int databaseSizeBeforeUpdate = votarFavoritoCRepository.findAll().size();

        // Create the VotarFavoritoC

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVotarFavoritoCMockMvc.perform(put("/api/votar-favorito-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoC)))
            .andExpect(status().isCreated());

        // Validate the VotarFavoritoC in the database
        List<VotarFavoritoC> votarFavoritoCList = votarFavoritoCRepository.findAll();
        assertThat(votarFavoritoCList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVotarFavoritoC() throws Exception {
        // Initialize the database
        votarFavoritoCRepository.saveAndFlush(votarFavoritoC);
        int databaseSizeBeforeDelete = votarFavoritoCRepository.findAll().size();

        // Get the votarFavoritoC
        restVotarFavoritoCMockMvc.perform(delete("/api/votar-favorito-cs/{id}", votarFavoritoC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VotarFavoritoC> votarFavoritoCList = votarFavoritoCRepository.findAll();
        assertThat(votarFavoritoCList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VotarFavoritoC.class);
        VotarFavoritoC votarFavoritoC1 = new VotarFavoritoC();
        votarFavoritoC1.setId(1L);
        VotarFavoritoC votarFavoritoC2 = new VotarFavoritoC();
        votarFavoritoC2.setId(votarFavoritoC1.getId());
        assertThat(votarFavoritoC1).isEqualTo(votarFavoritoC2);
        votarFavoritoC2.setId(2L);
        assertThat(votarFavoritoC1).isNotEqualTo(votarFavoritoC2);
        votarFavoritoC1.setId(null);
        assertThat(votarFavoritoC1).isNotEqualTo(votarFavoritoC2);
    }
}
