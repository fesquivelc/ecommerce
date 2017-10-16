package com.doraliz.ecommerce.web.rest;

import com.doraliz.ecommerce.EcommerceApp;

import com.doraliz.ecommerce.domain.Equivalencia;
import com.doraliz.ecommerce.repository.EquivalenciaRepository;
import com.doraliz.ecommerce.service.EquivalenciaService;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EquivalenciaResource REST controller.
 *
 * @see EquivalenciaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcommerceApp.class)
public class EquivalenciaResourceIntTest {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private EquivalenciaRepository equivalenciaRepository;

    @Autowired
    private EquivalenciaService equivalenciaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEquivalenciaMockMvc;

    private Equivalencia equivalencia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquivalenciaResource equivalenciaResource = new EquivalenciaResource(equivalenciaService);
        this.restEquivalenciaMockMvc = MockMvcBuilders.standaloneSetup(equivalenciaResource)
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
    public static Equivalencia createEntity(EntityManager em) {
        Equivalencia equivalencia = new Equivalencia()
            .valor(DEFAULT_VALOR);
        return equivalencia;
    }

    @Before
    public void initTest() {
        equivalencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquivalencia() throws Exception {
        int databaseSizeBeforeCreate = equivalenciaRepository.findAll().size();

        // Create the Equivalencia
        restEquivalenciaMockMvc.perform(post("/api/equivalencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equivalencia)))
            .andExpect(status().isCreated());

        // Validate the Equivalencia in the database
        List<Equivalencia> equivalenciaList = equivalenciaRepository.findAll();
        assertThat(equivalenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Equivalencia testEquivalencia = equivalenciaList.get(equivalenciaList.size() - 1);
        assertThat(testEquivalencia.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createEquivalenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equivalenciaRepository.findAll().size();

        // Create the Equivalencia with an existing ID
        equivalencia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquivalenciaMockMvc.perform(post("/api/equivalencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equivalencia)))
            .andExpect(status().isBadRequest());

        // Validate the Equivalencia in the database
        List<Equivalencia> equivalenciaList = equivalenciaRepository.findAll();
        assertThat(equivalenciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = equivalenciaRepository.findAll().size();
        // set the field null
        equivalencia.setValor(null);

        // Create the Equivalencia, which fails.

        restEquivalenciaMockMvc.perform(post("/api/equivalencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equivalencia)))
            .andExpect(status().isBadRequest());

        List<Equivalencia> equivalenciaList = equivalenciaRepository.findAll();
        assertThat(equivalenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquivalencias() throws Exception {
        // Initialize the database
        equivalenciaRepository.saveAndFlush(equivalencia);

        // Get all the equivalenciaList
        restEquivalenciaMockMvc.perform(get("/api/equivalencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equivalencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }

    @Test
    @Transactional
    public void getEquivalencia() throws Exception {
        // Initialize the database
        equivalenciaRepository.saveAndFlush(equivalencia);

        // Get the equivalencia
        restEquivalenciaMockMvc.perform(get("/api/equivalencias/{id}", equivalencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equivalencia.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEquivalencia() throws Exception {
        // Get the equivalencia
        restEquivalenciaMockMvc.perform(get("/api/equivalencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquivalencia() throws Exception {
        // Initialize the database
        equivalenciaService.save(equivalencia);

        int databaseSizeBeforeUpdate = equivalenciaRepository.findAll().size();

        // Update the equivalencia
        Equivalencia updatedEquivalencia = equivalenciaRepository.findOne(equivalencia.getId());
        updatedEquivalencia
            .valor(UPDATED_VALOR);

        restEquivalenciaMockMvc.perform(put("/api/equivalencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquivalencia)))
            .andExpect(status().isOk());

        // Validate the Equivalencia in the database
        List<Equivalencia> equivalenciaList = equivalenciaRepository.findAll();
        assertThat(equivalenciaList).hasSize(databaseSizeBeforeUpdate);
        Equivalencia testEquivalencia = equivalenciaList.get(equivalenciaList.size() - 1);
        assertThat(testEquivalencia.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingEquivalencia() throws Exception {
        int databaseSizeBeforeUpdate = equivalenciaRepository.findAll().size();

        // Create the Equivalencia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEquivalenciaMockMvc.perform(put("/api/equivalencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equivalencia)))
            .andExpect(status().isCreated());

        // Validate the Equivalencia in the database
        List<Equivalencia> equivalenciaList = equivalenciaRepository.findAll();
        assertThat(equivalenciaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEquivalencia() throws Exception {
        // Initialize the database
        equivalenciaService.save(equivalencia);

        int databaseSizeBeforeDelete = equivalenciaRepository.findAll().size();

        // Get the equivalencia
        restEquivalenciaMockMvc.perform(delete("/api/equivalencias/{id}", equivalencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Equivalencia> equivalenciaList = equivalenciaRepository.findAll();
        assertThat(equivalenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equivalencia.class);
        Equivalencia equivalencia1 = new Equivalencia();
        equivalencia1.setId(1L);
        Equivalencia equivalencia2 = new Equivalencia();
        equivalencia2.setId(equivalencia1.getId());
        assertThat(equivalencia1).isEqualTo(equivalencia2);
        equivalencia2.setId(2L);
        assertThat(equivalencia1).isNotEqualTo(equivalencia2);
        equivalencia1.setId(null);
        assertThat(equivalencia1).isNotEqualTo(equivalencia2);
    }
}
