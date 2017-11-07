package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.VotarFavoritoB;
import com.mycompany.myapp.repository.VotarFavoritoBRepository;
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
 * Test class for the VotarFavoritoBResource REST controller.
 *
 * @see VotarFavoritoBResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class VotarFavoritoBResourceIntTest {

    private static final ZonedDateTime DEFAULT_MOMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MOMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_VALORACIO = 1;
    private static final Integer UPDATED_VALORACIO = 2;

    @Autowired
    private VotarFavoritoBRepository votarFavoritoBRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVotarFavoritoBMockMvc;

    private VotarFavoritoB votarFavoritoB;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VotarFavoritoBResource votarFavoritoBResource = new VotarFavoritoBResource(votarFavoritoBRepository);
        this.restVotarFavoritoBMockMvc = MockMvcBuilders.standaloneSetup(votarFavoritoBResource)
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
    public static VotarFavoritoB createEntity(EntityManager em) {
        VotarFavoritoB votarFavoritoB = new VotarFavoritoB()
            .momento(DEFAULT_MOMENTO)
            .valoracio(DEFAULT_VALORACIO);
        return votarFavoritoB;
    }

    @Before
    public void initTest() {
        votarFavoritoB = createEntity(em);
    }

    @Test
    @Transactional
    public void createVotarFavoritoB() throws Exception {
        int databaseSizeBeforeCreate = votarFavoritoBRepository.findAll().size();

        // Create the VotarFavoritoB
        restVotarFavoritoBMockMvc.perform(post("/api/votar-favorito-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoB)))
            .andExpect(status().isCreated());

        // Validate the VotarFavoritoB in the database
        List<VotarFavoritoB> votarFavoritoBList = votarFavoritoBRepository.findAll();
        assertThat(votarFavoritoBList).hasSize(databaseSizeBeforeCreate + 1);
        VotarFavoritoB testVotarFavoritoB = votarFavoritoBList.get(votarFavoritoBList.size() - 1);
        assertThat(testVotarFavoritoB.getMomento()).isEqualTo(DEFAULT_MOMENTO);
        assertThat(testVotarFavoritoB.getValoracio()).isEqualTo(DEFAULT_VALORACIO);
    }

    @Test
    @Transactional
    public void createVotarFavoritoBWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = votarFavoritoBRepository.findAll().size();

        // Create the VotarFavoritoB with an existing ID
        votarFavoritoB.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVotarFavoritoBMockMvc.perform(post("/api/votar-favorito-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoB)))
            .andExpect(status().isBadRequest());

        // Validate the VotarFavoritoB in the database
        List<VotarFavoritoB> votarFavoritoBList = votarFavoritoBRepository.findAll();
        assertThat(votarFavoritoBList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVotarFavoritoBS() throws Exception {
        // Initialize the database
        votarFavoritoBRepository.saveAndFlush(votarFavoritoB);

        // Get all the votarFavoritoBList
        restVotarFavoritoBMockMvc.perform(get("/api/votar-favorito-bs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(votarFavoritoB.getId().intValue())))
            .andExpect(jsonPath("$.[*].momento").value(hasItem(sameInstant(DEFAULT_MOMENTO))))
            .andExpect(jsonPath("$.[*].valoracio").value(hasItem(DEFAULT_VALORACIO)));
    }

    @Test
    @Transactional
    public void getVotarFavoritoB() throws Exception {
        // Initialize the database
        votarFavoritoBRepository.saveAndFlush(votarFavoritoB);

        // Get the votarFavoritoB
        restVotarFavoritoBMockMvc.perform(get("/api/votar-favorito-bs/{id}", votarFavoritoB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(votarFavoritoB.getId().intValue()))
            .andExpect(jsonPath("$.momento").value(sameInstant(DEFAULT_MOMENTO)))
            .andExpect(jsonPath("$.valoracio").value(DEFAULT_VALORACIO));
    }

    @Test
    @Transactional
    public void getNonExistingVotarFavoritoB() throws Exception {
        // Get the votarFavoritoB
        restVotarFavoritoBMockMvc.perform(get("/api/votar-favorito-bs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVotarFavoritoB() throws Exception {
        // Initialize the database
        votarFavoritoBRepository.saveAndFlush(votarFavoritoB);
        int databaseSizeBeforeUpdate = votarFavoritoBRepository.findAll().size();

        // Update the votarFavoritoB
        VotarFavoritoB updatedVotarFavoritoB = votarFavoritoBRepository.findOne(votarFavoritoB.getId());
        updatedVotarFavoritoB
            .momento(UPDATED_MOMENTO)
            .valoracio(UPDATED_VALORACIO);

        restVotarFavoritoBMockMvc.perform(put("/api/votar-favorito-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVotarFavoritoB)))
            .andExpect(status().isOk());

        // Validate the VotarFavoritoB in the database
        List<VotarFavoritoB> votarFavoritoBList = votarFavoritoBRepository.findAll();
        assertThat(votarFavoritoBList).hasSize(databaseSizeBeforeUpdate);
        VotarFavoritoB testVotarFavoritoB = votarFavoritoBList.get(votarFavoritoBList.size() - 1);
        assertThat(testVotarFavoritoB.getMomento()).isEqualTo(UPDATED_MOMENTO);
        assertThat(testVotarFavoritoB.getValoracio()).isEqualTo(UPDATED_VALORACIO);
    }

    @Test
    @Transactional
    public void updateNonExistingVotarFavoritoB() throws Exception {
        int databaseSizeBeforeUpdate = votarFavoritoBRepository.findAll().size();

        // Create the VotarFavoritoB

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVotarFavoritoBMockMvc.perform(put("/api/votar-favorito-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votarFavoritoB)))
            .andExpect(status().isCreated());

        // Validate the VotarFavoritoB in the database
        List<VotarFavoritoB> votarFavoritoBList = votarFavoritoBRepository.findAll();
        assertThat(votarFavoritoBList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVotarFavoritoB() throws Exception {
        // Initialize the database
        votarFavoritoBRepository.saveAndFlush(votarFavoritoB);
        int databaseSizeBeforeDelete = votarFavoritoBRepository.findAll().size();

        // Get the votarFavoritoB
        restVotarFavoritoBMockMvc.perform(delete("/api/votar-favorito-bs/{id}", votarFavoritoB.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VotarFavoritoB> votarFavoritoBList = votarFavoritoBRepository.findAll();
        assertThat(votarFavoritoBList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VotarFavoritoB.class);
        VotarFavoritoB votarFavoritoB1 = new VotarFavoritoB();
        votarFavoritoB1.setId(1L);
        VotarFavoritoB votarFavoritoB2 = new VotarFavoritoB();
        votarFavoritoB2.setId(votarFavoritoB1.getId());
        assertThat(votarFavoritoB1).isEqualTo(votarFavoritoB2);
        votarFavoritoB2.setId(2L);
        assertThat(votarFavoritoB1).isNotEqualTo(votarFavoritoB2);
        votarFavoritoB1.setId(null);
        assertThat(votarFavoritoB1).isNotEqualTo(votarFavoritoB2);
    }
}
