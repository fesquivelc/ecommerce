package com.doraliz.ecommerce.web.rest;

import com.doraliz.ecommerce.EcommerceApp;

import com.doraliz.ecommerce.domain.Comprobante;
import com.doraliz.ecommerce.repository.ComprobanteRepository;
import com.doraliz.ecommerce.service.ComprobanteService;
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

import com.doraliz.ecommerce.domain.enumeration.TipoComprobante;
/**
 * Test class for the ComprobanteResource REST controller.
 *
 * @see ComprobanteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcommerceApp.class)
public class ComprobanteResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final TipoComprobante DEFAULT_TIPO_COMPROBANTE = TipoComprobante.FACTURA;
    private static final TipoComprobante UPDATED_TIPO_COMPROBANTE = TipoComprobante.BOLETA;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private ComprobanteService comprobanteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComprobanteMockMvc;

    private Comprobante comprobante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComprobanteResource comprobanteResource = new ComprobanteResource(comprobanteService);
        this.restComprobanteMockMvc = MockMvcBuilders.standaloneSetup(comprobanteResource)
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
    public static Comprobante createEntity(EntityManager em) {
        Comprobante comprobante = new Comprobante()
            .numero(DEFAULT_NUMERO)
            .tipoComprobante(DEFAULT_TIPO_COMPROBANTE);
        return comprobante;
    }

    @Before
    public void initTest() {
        comprobante = createEntity(em);
    }

    @Test
    @Transactional
    public void createComprobante() throws Exception {
        int databaseSizeBeforeCreate = comprobanteRepository.findAll().size();

        // Create the Comprobante
        restComprobanteMockMvc.perform(post("/api/comprobantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comprobante)))
            .andExpect(status().isCreated());

        // Validate the Comprobante in the database
        List<Comprobante> comprobanteList = comprobanteRepository.findAll();
        assertThat(comprobanteList).hasSize(databaseSizeBeforeCreate + 1);
        Comprobante testComprobante = comprobanteList.get(comprobanteList.size() - 1);
        assertThat(testComprobante.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testComprobante.getTipoComprobante()).isEqualTo(DEFAULT_TIPO_COMPROBANTE);
    }

    @Test
    @Transactional
    public void createComprobanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comprobanteRepository.findAll().size();

        // Create the Comprobante with an existing ID
        comprobante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComprobanteMockMvc.perform(post("/api/comprobantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comprobante)))
            .andExpect(status().isBadRequest());

        // Validate the Comprobante in the database
        List<Comprobante> comprobanteList = comprobanteRepository.findAll();
        assertThat(comprobanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = comprobanteRepository.findAll().size();
        // set the field null
        comprobante.setNumero(null);

        // Create the Comprobante, which fails.

        restComprobanteMockMvc.perform(post("/api/comprobantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comprobante)))
            .andExpect(status().isBadRequest());

        List<Comprobante> comprobanteList = comprobanteRepository.findAll();
        assertThat(comprobanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComprobantes() throws Exception {
        // Initialize the database
        comprobanteRepository.saveAndFlush(comprobante);

        // Get all the comprobanteList
        restComprobanteMockMvc.perform(get("/api/comprobantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comprobante.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].tipoComprobante").value(hasItem(DEFAULT_TIPO_COMPROBANTE.toString())));
    }

    @Test
    @Transactional
    public void getComprobante() throws Exception {
        // Initialize the database
        comprobanteRepository.saveAndFlush(comprobante);

        // Get the comprobante
        restComprobanteMockMvc.perform(get("/api/comprobantes/{id}", comprobante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comprobante.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.tipoComprobante").value(DEFAULT_TIPO_COMPROBANTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComprobante() throws Exception {
        // Get the comprobante
        restComprobanteMockMvc.perform(get("/api/comprobantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComprobante() throws Exception {
        // Initialize the database
        comprobanteService.save(comprobante);

        int databaseSizeBeforeUpdate = comprobanteRepository.findAll().size();

        // Update the comprobante
        Comprobante updatedComprobante = comprobanteRepository.findOne(comprobante.getId());
        updatedComprobante
            .numero(UPDATED_NUMERO)
            .tipoComprobante(UPDATED_TIPO_COMPROBANTE);

        restComprobanteMockMvc.perform(put("/api/comprobantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComprobante)))
            .andExpect(status().isOk());

        // Validate the Comprobante in the database
        List<Comprobante> comprobanteList = comprobanteRepository.findAll();
        assertThat(comprobanteList).hasSize(databaseSizeBeforeUpdate);
        Comprobante testComprobante = comprobanteList.get(comprobanteList.size() - 1);
        assertThat(testComprobante.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testComprobante.getTipoComprobante()).isEqualTo(UPDATED_TIPO_COMPROBANTE);
    }

    @Test
    @Transactional
    public void updateNonExistingComprobante() throws Exception {
        int databaseSizeBeforeUpdate = comprobanteRepository.findAll().size();

        // Create the Comprobante

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComprobanteMockMvc.perform(put("/api/comprobantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comprobante)))
            .andExpect(status().isCreated());

        // Validate the Comprobante in the database
        List<Comprobante> comprobanteList = comprobanteRepository.findAll();
        assertThat(comprobanteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComprobante() throws Exception {
        // Initialize the database
        comprobanteService.save(comprobante);

        int databaseSizeBeforeDelete = comprobanteRepository.findAll().size();

        // Get the comprobante
        restComprobanteMockMvc.perform(delete("/api/comprobantes/{id}", comprobante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comprobante> comprobanteList = comprobanteRepository.findAll();
        assertThat(comprobanteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comprobante.class);
        Comprobante comprobante1 = new Comprobante();
        comprobante1.setId(1L);
        Comprobante comprobante2 = new Comprobante();
        comprobante2.setId(comprobante1.getId());
        assertThat(comprobante1).isEqualTo(comprobante2);
        comprobante2.setId(2L);
        assertThat(comprobante1).isNotEqualTo(comprobante2);
        comprobante1.setId(null);
        assertThat(comprobante1).isNotEqualTo(comprobante2);
    }
}
