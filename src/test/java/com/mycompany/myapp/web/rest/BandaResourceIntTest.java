package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.Banda;
import com.mycompany.myapp.repository.BandaRepository;
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
 * Test class for the BandaResource REST controller.
 *
 * @see BandaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class BandaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CREACION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_YEARS_ACTIVOS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YEARS_ACTIVOS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private BandaRepository bandaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBandaMockMvc;

    private Banda banda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BandaResource bandaResource = new BandaResource(bandaRepository);
        this.restBandaMockMvc = MockMvcBuilders.standaloneSetup(bandaResource)
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
    public static Banda createEntity(EntityManager em) {
        Banda banda = new Banda()
            .nombre(DEFAULT_NOMBRE)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .yearsActivos(DEFAULT_YEARS_ACTIVOS)
            .descripcion(DEFAULT_DESCRIPCION)
            .estado(DEFAULT_ESTADO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return banda;
    }

    @Before
    public void initTest() {
        banda = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanda() throws Exception {
        int databaseSizeBeforeCreate = bandaRepository.findAll().size();

        // Create the Banda
        restBandaMockMvc.perform(post("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banda)))
            .andExpect(status().isCreated());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeCreate + 1);
        Banda testBanda = bandaList.get(bandaList.size() - 1);
        assertThat(testBanda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testBanda.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testBanda.getYearsActivos()).isEqualTo(DEFAULT_YEARS_ACTIVOS);
        assertThat(testBanda.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testBanda.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testBanda.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testBanda.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBandaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bandaRepository.findAll().size();

        // Create the Banda with an existing ID
        banda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBandaMockMvc.perform(post("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banda)))
            .andExpect(status().isBadRequest());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBandas() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);

        // Get all the bandaList
        restBandaMockMvc.perform(get("/api/bandas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].yearsActivos").value(hasItem(DEFAULT_YEARS_ACTIVOS.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);

        // Get the banda
        restBandaMockMvc.perform(get("/api/bandas/{id}", banda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banda.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.yearsActivos").value(DEFAULT_YEARS_ACTIVOS.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingBanda() throws Exception {
        // Get the banda
        restBandaMockMvc.perform(get("/api/bandas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);
        int databaseSizeBeforeUpdate = bandaRepository.findAll().size();

        // Update the banda
        Banda updatedBanda = bandaRepository.findOne(banda.getId());
        updatedBanda
            .nombre(UPDATED_NOMBRE)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .yearsActivos(UPDATED_YEARS_ACTIVOS)
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restBandaMockMvc.perform(put("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanda)))
            .andExpect(status().isOk());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeUpdate);
        Banda testBanda = bandaList.get(bandaList.size() - 1);
        assertThat(testBanda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testBanda.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testBanda.getYearsActivos()).isEqualTo(UPDATED_YEARS_ACTIVOS);
        assertThat(testBanda.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testBanda.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testBanda.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testBanda.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBanda() throws Exception {
        int databaseSizeBeforeUpdate = bandaRepository.findAll().size();

        // Create the Banda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBandaMockMvc.perform(put("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banda)))
            .andExpect(status().isCreated());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);
        int databaseSizeBeforeDelete = bandaRepository.findAll().size();

        // Get the banda
        restBandaMockMvc.perform(delete("/api/bandas/{id}", banda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banda.class);
        Banda banda1 = new Banda();
        banda1.setId(1L);
        Banda banda2 = new Banda();
        banda2.setId(banda1.getId());
        assertThat(banda1).isEqualTo(banda2);
        banda2.setId(2L);
        assertThat(banda1).isNotEqualTo(banda2);
        banda1.setId(null);
        assertThat(banda1).isNotEqualTo(banda2);
    }
}
