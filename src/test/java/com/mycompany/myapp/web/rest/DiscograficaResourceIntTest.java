package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.Discografica;
import com.mycompany.myapp.repository.DiscograficaRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DiscograficaResource REST controller.
 *
 * @see DiscograficaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class DiscograficaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private DiscograficaRepository discograficaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiscograficaMockMvc;

    private Discografica discografica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscograficaResource discograficaResource = new DiscograficaResource(discograficaRepository);
        this.restDiscograficaMockMvc = MockMvcBuilders.standaloneSetup(discograficaResource)
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
    public static Discografica createEntity(EntityManager em) {
        Discografica discografica = new Discografica()
            .nombre(DEFAULT_NOMBRE)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return discografica;
    }

    @Before
    public void initTest() {
        discografica = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscografica() throws Exception {
        int databaseSizeBeforeCreate = discograficaRepository.findAll().size();

        // Create the Discografica
        restDiscograficaMockMvc.perform(post("/api/discograficas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discografica)))
            .andExpect(status().isCreated());

        // Validate the Discografica in the database
        List<Discografica> discograficaList = discograficaRepository.findAll();
        assertThat(discograficaList).hasSize(databaseSizeBeforeCreate + 1);
        Discografica testDiscografica = discograficaList.get(discograficaList.size() - 1);
        assertThat(testDiscografica.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDiscografica.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testDiscografica.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDiscograficaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discograficaRepository.findAll().size();

        // Create the Discografica with an existing ID
        discografica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscograficaMockMvc.perform(post("/api/discograficas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discografica)))
            .andExpect(status().isBadRequest());

        // Validate the Discografica in the database
        List<Discografica> discograficaList = discograficaRepository.findAll();
        assertThat(discograficaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDiscograficas() throws Exception {
        // Initialize the database
        discograficaRepository.saveAndFlush(discografica);

        // Get all the discograficaList
        restDiscograficaMockMvc.perform(get("/api/discograficas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discografica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getDiscografica() throws Exception {
        // Initialize the database
        discograficaRepository.saveAndFlush(discografica);

        // Get the discografica
        restDiscograficaMockMvc.perform(get("/api/discograficas/{id}", discografica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discografica.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingDiscografica() throws Exception {
        // Get the discografica
        restDiscograficaMockMvc.perform(get("/api/discograficas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscografica() throws Exception {
        // Initialize the database
        discograficaRepository.saveAndFlush(discografica);
        int databaseSizeBeforeUpdate = discograficaRepository.findAll().size();

        // Update the discografica
        Discografica updatedDiscografica = discograficaRepository.findOne(discografica.getId());
        updatedDiscografica
            .nombre(UPDATED_NOMBRE)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restDiscograficaMockMvc.perform(put("/api/discograficas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiscografica)))
            .andExpect(status().isOk());

        // Validate the Discografica in the database
        List<Discografica> discograficaList = discograficaRepository.findAll();
        assertThat(discograficaList).hasSize(databaseSizeBeforeUpdate);
        Discografica testDiscografica = discograficaList.get(discograficaList.size() - 1);
        assertThat(testDiscografica.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDiscografica.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testDiscografica.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscografica() throws Exception {
        int databaseSizeBeforeUpdate = discograficaRepository.findAll().size();

        // Create the Discografica

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiscograficaMockMvc.perform(put("/api/discograficas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discografica)))
            .andExpect(status().isCreated());

        // Validate the Discografica in the database
        List<Discografica> discograficaList = discograficaRepository.findAll();
        assertThat(discograficaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDiscografica() throws Exception {
        // Initialize the database
        discograficaRepository.saveAndFlush(discografica);
        int databaseSizeBeforeDelete = discograficaRepository.findAll().size();

        // Get the discografica
        restDiscograficaMockMvc.perform(delete("/api/discograficas/{id}", discografica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Discografica> discograficaList = discograficaRepository.findAll();
        assertThat(discograficaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discografica.class);
        Discografica discografica1 = new Discografica();
        discografica1.setId(1L);
        Discografica discografica2 = new Discografica();
        discografica2.setId(discografica1.getId());
        assertThat(discografica1).isEqualTo(discografica2);
        discografica2.setId(2L);
        assertThat(discografica1).isNotEqualTo(discografica2);
        discografica1.setId(null);
        assertThat(discografica1).isNotEqualTo(discografica2);
    }
}
