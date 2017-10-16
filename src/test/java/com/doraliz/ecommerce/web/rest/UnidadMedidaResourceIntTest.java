package com.doraliz.ecommerce.web.rest;

import com.doraliz.ecommerce.EcommerceApp;

import com.doraliz.ecommerce.domain.UnidadMedida;
import com.doraliz.ecommerce.repository.UnidadMedidaRepository;
import com.doraliz.ecommerce.service.UnidadMedidaService;
import com.doraliz.ecommerce.web.rest.errors.ExceptionTranslator;

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
 * Test class for the UnidadMedidaResource REST controller.
 *
 * @see UnidadMedidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcommerceApp.class)
public class UnidadMedidaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private UnidadMedidaService unidadMedidaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnidadMedidaMockMvc;

    private UnidadMedida unidadMedida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadMedidaResource unidadMedidaResource = new UnidadMedidaResource(unidadMedidaService);
        this.restUnidadMedidaMockMvc = MockMvcBuilders.standaloneSetup(unidadMedidaResource)
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
    public static UnidadMedida createEntity(EntityManager em) {
        UnidadMedida unidadMedida = new UnidadMedida()
            .nombre(DEFAULT_NOMBRE)
            .codigo(DEFAULT_CODIGO);
        return unidadMedida;
    }

    @Before
    public void initTest() {
        unidadMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadMedida() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida
        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedida)))
            .andExpect(status().isCreated());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUnidadMedida.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void createUnidadMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida with an existing ID
        unidadMedida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadMedidaRepository.findAll().size();
        // set the field null
        unidadMedida.setNombre(null);

        // Create the UnidadMedida, which fails.

        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedida)))
            .andExpect(status().isBadRequest());

        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadMedidaRepository.findAll().size();
        // set the field null
        unidadMedida.setCodigo(null);

        // Create the UnidadMedida, which fails.

        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedida)))
            .andExpect(status().isBadRequest());

        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadMedidas() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }

    @Test
    @Transactional
    public void getUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas/{id}", unidadMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadMedida.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadMedida() throws Exception {
        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaService.save(unidadMedida);

        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Update the unidadMedida
        UnidadMedida updatedUnidadMedida = unidadMedidaRepository.findOne(unidadMedida.getId());
        updatedUnidadMedida
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO);

        restUnidadMedidaMockMvc.perform(put("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnidadMedida)))
            .andExpect(status().isOk());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUnidadMedida.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUnidadMedidaMockMvc.perform(put("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedida)))
            .andExpect(status().isCreated());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaService.save(unidadMedida);

        int databaseSizeBeforeDelete = unidadMedidaRepository.findAll().size();

        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(delete("/api/unidad-medidas/{id}", unidadMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadMedida.class);
        UnidadMedida unidadMedida1 = new UnidadMedida();
        unidadMedida1.setId(1L);
        UnidadMedida unidadMedida2 = new UnidadMedida();
        unidadMedida2.setId(unidadMedida1.getId());
        assertThat(unidadMedida1).isEqualTo(unidadMedida2);
        unidadMedida2.setId(2L);
        assertThat(unidadMedida1).isNotEqualTo(unidadMedida2);
        unidadMedida1.setId(null);
        assertThat(unidadMedida1).isNotEqualTo(unidadMedida2);
    }
}
