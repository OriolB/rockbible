package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.VotarFavoritoA;
import com.mycompany.myapp.repository.VotarFavoritoARepository;
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
 * Test class for the VotarFavoritoAResource REST controller.
 *
 * @see VotarFavoritoAResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class VotarFavoritoAResourceIntTest {

    private static final ZonedDateTime DEFAULT_MOMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MOMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_VALORACIO = 1;
    private static final Integer UPDATED_VALORACIO = 2;

    @Autowired
    private VotarFavoritoARepository votarFavoritoARepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVotarFavoritoAMockMvc;

    private VotarFavoritoA votarFavoritoA;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VotarFavoritoAResource votarFavoritoAResource = new VotarFavoritoAResource(votarFavoritoARepository);
        this.restVotarFavoritoAMockMvc = MockMvcBuilders.standaloneSetup(votarFavoritoAResource)
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
    public static VotarFavoritoA createEntity(EntityManager em) {
        VotarFavoritoA votarFavoritoA = new VotarFavoritoA()
            .momento(DEFAULT_MOMENTO)
            .valoracio(DEFAULT_VALORACIO);
        return votarFavoritoA;
    }

    @Before
    public void initTest() {
        votarFavoritoA = createEntity(em);
    }

    @Test
    @Transactional
    public void createVotarFavoritoA() throws Exception {
        int databaseSizeBeforeCreate = votarFavoritoARepository.findAll().size();

        // Create the VotarFavoritoA
        restVotarFavoritoAMockMvc.perform(post("/api/votar-favorito-as")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoA)))
            .andExpect(status().isCreated());

        // Validate the VotarFavoritoA in the database
        List<VotarFavoritoA> votarFavoritoAList = votarFavoritoARepository.findAll();
        assertThat(votarFavoritoAList).hasSize(databaseSizeBeforeCreate + 1);
        VotarFavoritoA testVotarFavoritoA = votarFavoritoAList.get(votarFavoritoAList.size() - 1);
        assertThat(testVotarFavoritoA.getMomento()).isEqualTo(DEFAULT_MOMENTO);
        assertThat(testVotarFavoritoA.getValoracio()).isEqualTo(DEFAULT_VALORACIO);
    }

    @Test
    @Transactional
    public void createVotarFavoritoAWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = votarFavoritoARepository.findAll().size();

        // Create the VotarFavoritoA with an existing ID
        votarFavoritoA.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVotarFavoritoAMockMvc.perform(post("/api/votar-favorito-as")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoA)))
            .andExpect(status().isBadRequest());

        // Validate the VotarFavoritoA in the database
        List<VotarFavoritoA> votarFavoritoAList = votarFavoritoARepository.findAll();
        assertThat(votarFavoritoAList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVotarFavoritoAS() throws Exception {
        // Initialize the database
        votarFavoritoARepository.saveAndFlush(votarFavoritoA);

        // Get all the votarFavoritoAList
        restVotarFavoritoAMockMvc.perform(get("/api/votar-favorito-as?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(votarFavoritoA.getId().intValue())))
            .andExpect(jsonPath("$.[*].momento").value(hasItem(sameInstant(DEFAULT_MOMENTO))))
            .andExpect(jsonPath("$.[*].valoracio").value(hasItem(DEFAULT_VALORACIO)));
    }

    @Test
    @Transactional
    public void getVotarFavoritoA() throws Exception {
        // Initialize the database
        votarFavoritoARepository.saveAndFlush(votarFavoritoA);

        // Get the votarFavoritoA
        restVotarFavoritoAMockMvc.perform(get("/api/votar-favorito-as/{id}", votarFavoritoA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(votarFavoritoA.getId().intValue()))
            .andExpect(jsonPath("$.momento").value(sameInstant(DEFAULT_MOMENTO)))
            .andExpect(jsonPath("$.valoracio").value(DEFAULT_VALORACIO));
    }

    @Test
    @Transactional
    public void getNonExistingVotarFavoritoA() throws Exception {
        // Get the votarFavoritoA
        restVotarFavoritoAMockMvc.perform(get("/api/votar-favorito-as/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVotarFavoritoA() throws Exception {
        // Initialize the database
        votarFavoritoARepository.saveAndFlush(votarFavoritoA);
        int databaseSizeBeforeUpdate = votarFavoritoARepository.findAll().size();

        // Update the votarFavoritoA
        VotarFavoritoA updatedVotarFavoritoA = votarFavoritoARepository.findOne(votarFavoritoA.getId());
        updatedVotarFavoritoA
            .momento(UPDATED_MOMENTO)
            .valoracio(UPDATED_VALORACIO);

        restVotarFavoritoAMockMvc.perform(put("/api/votar-favorito-as")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVotarFavoritoA)))
            .andExpect(status().isOk());

        // Validate the VotarFavoritoA in the database
        List<VotarFavoritoA> votarFavoritoAList = votarFavoritoARepository.findAll();
        assertThat(votarFavoritoAList).hasSize(databaseSizeBeforeUpdate);
        VotarFavoritoA testVotarFavoritoA = votarFavoritoAList.get(votarFavoritoAList.size() - 1);
        assertThat(testVotarFavoritoA.getMomento()).isEqualTo(UPDATED_MOMENTO);
        assertThat(testVotarFavoritoA.getValoracio()).isEqualTo(UPDATED_VALORACIO);
    }

    @Test
    @Transactional
    public void updateNonExistingVotarFavoritoA() throws Exception {
        int databaseSizeBeforeUpdate = votarFavoritoARepository.findAll().size();

        // Create the VotarFavoritoA

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVotarFavoritoAMockMvc.perform(put("/api/votar-favorito-as")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoA)))
            .andExpect(status().isCreated());

        // Validate the VotarFavoritoA in the database
        List<VotarFavoritoA> votarFavoritoAList = votarFavoritoARepository.findAll();
        assertThat(votarFavoritoAList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVotarFavoritoA() throws Exception {
        // Initialize the database
        votarFavoritoARepository.saveAndFlush(votarFavoritoA);
        int databaseSizeBeforeDelete = votarFavoritoARepository.findAll().size();

        // Get the votarFavoritoA
        restVotarFavoritoAMockMvc.perform(delete("/api/votar-favorito-as/{id}", votarFavoritoA.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VotarFavoritoA> votarFavoritoAList = votarFavoritoARepository.findAll();
        assertThat(votarFavoritoAList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VotarFavoritoA.class);
        VotarFavoritoA votarFavoritoA1 = new VotarFavoritoA();
        votarFavoritoA1.setId(1L);
        VotarFavoritoA votarFavoritoA2 = new VotarFavoritoA();
        votarFavoritoA2.setId(votarFavoritoA1.getId());
        assertThat(votarFavoritoA1).isEqualTo(votarFavoritoA2);
        votarFavoritoA2.setId(2L);
        assertThat(votarFavoritoA1).isNotEqualTo(votarFavoritoA2);
        votarFavoritoA1.setId(null);
        assertThat(votarFavoritoA1).isNotEqualTo(votarFavoritoA2);
    }
}
