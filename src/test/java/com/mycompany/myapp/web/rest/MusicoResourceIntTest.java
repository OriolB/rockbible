package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.Musico;
import com.mycompany.myapp.repository.MusicoRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MusicoResource REST controller.
 *
 * @see MusicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class MusicoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private MusicoRepository musicoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMusicoMockMvc;

    private Musico musico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MusicoResource musicoResource = new MusicoResource(musicoRepository);
        this.restMusicoMockMvc = MockMvcBuilders.standaloneSetup(musicoResource)
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
    public static Musico createEntity(EntityManager em) {
        Musico musico = new Musico()
            .nombre(DEFAULT_NOMBRE)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .descripcion(DEFAULT_DESCRIPCION)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return musico;
    }

    @Before
    public void initTest() {
        musico = createEntity(em);
    }

    @Test
    @Transactional
    public void createMusico() throws Exception {
        int databaseSizeBeforeCreate = musicoRepository.findAll().size();

        // Create the Musico
        restMusicoMockMvc.perform(post("/api/musicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musico)))
            .andExpect(status().isCreated());

        // Validate the Musico in the database
        List<Musico> musicoList = musicoRepository.findAll();
        assertThat(musicoList).hasSize(databaseSizeBeforeCreate + 1);
        Musico testMusico = musicoList.get(musicoList.size() - 1);
        assertThat(testMusico.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMusico.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testMusico.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testMusico.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testMusico.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMusicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = musicoRepository.findAll().size();

        // Create the Musico with an existing ID
        musico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMusicoMockMvc.perform(post("/api/musicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musico)))
            .andExpect(status().isBadRequest());

        // Validate the Musico in the database
        List<Musico> musicoList = musicoRepository.findAll();
        assertThat(musicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMusicos() throws Exception {
        // Initialize the database
        musicoRepository.saveAndFlush(musico);

        // Get all the musicoList
        restMusicoMockMvc.perform(get("/api/musicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(musico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getMusico() throws Exception {
        // Initialize the database
        musicoRepository.saveAndFlush(musico);

        // Get the musico
        restMusicoMockMvc.perform(get("/api/musicos/{id}", musico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(musico.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingMusico() throws Exception {
        // Get the musico
        restMusicoMockMvc.perform(get("/api/musicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMusico() throws Exception {
        // Initialize the database
        musicoRepository.saveAndFlush(musico);
        int databaseSizeBeforeUpdate = musicoRepository.findAll().size();

        // Update the musico
        Musico updatedMusico = musicoRepository.findOne(musico.getId());
        updatedMusico
            .nombre(UPDATED_NOMBRE)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restMusicoMockMvc.perform(put("/api/musicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMusico)))
            .andExpect(status().isOk());

        // Validate the Musico in the database
        List<Musico> musicoList = musicoRepository.findAll();
        assertThat(musicoList).hasSize(databaseSizeBeforeUpdate);
        Musico testMusico = musicoList.get(musicoList.size() - 1);
        assertThat(testMusico.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMusico.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testMusico.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testMusico.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testMusico.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMusico() throws Exception {
        int databaseSizeBeforeUpdate = musicoRepository.findAll().size();

        // Create the Musico

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMusicoMockMvc.perform(put("/api/musicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musico)))
            .andExpect(status().isCreated());

        // Validate the Musico in the database
        List<Musico> musicoList = musicoRepository.findAll();
        assertThat(musicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMusico() throws Exception {
        // Initialize the database
        musicoRepository.saveAndFlush(musico);
        int databaseSizeBeforeDelete = musicoRepository.findAll().size();

        // Get the musico
        restMusicoMockMvc.perform(delete("/api/musicos/{id}", musico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Musico> musicoList = musicoRepository.findAll();
        assertThat(musicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Musico.class);
        Musico musico1 = new Musico();
        musico1.setId(1L);
        Musico musico2 = new Musico();
        musico2.setId(musico1.getId());
        assertThat(musico1).isEqualTo(musico2);
        musico2.setId(2L);
        assertThat(musico1).isNotEqualTo(musico2);
        musico1.setId(null);
        assertThat(musico1).isNotEqualTo(musico2);
    }
}
