package com.doraliz.ecommerce.web.rest;

import com.doraliz.ecommerce.EcommerceApp;

import com.doraliz.ecommerce.domain.Ubigeo;
import com.doraliz.ecommerce.repository.UbigeoRepository;
import com.doraliz.ecommerce.service.UbigeoService;
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
 * Test class for the UbigeoResource REST controller.
 *
 * @see UbigeoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcommerceApp.class)
public class UbigeoResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRITO = "AAAAAAAAAA";
    private static final String UPDATED_DISTRITO = "BBBBBBBBBB";

    @Autowired
    private UbigeoRepository ubigeoRepository;

    @Autowired
    private UbigeoService ubigeoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUbigeoMockMvc;

    private Ubigeo ubigeo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UbigeoResource ubigeoResource = new UbigeoResource(ubigeoService);
        this.restUbigeoMockMvc = MockMvcBuilders.standaloneSetup(ubigeoResource)
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
    public static Ubigeo createEntity(EntityManager em) {
        Ubigeo ubigeo = new Ubigeo()
            .codigo(DEFAULT_CODIGO)
            .departamento(DEFAULT_DEPARTAMENTO)
            .provincia(DEFAULT_PROVINCIA)
            .distrito(DEFAULT_DISTRITO);
        return ubigeo;
    }

    @Before
    public void initTest() {
        ubigeo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUbigeo() throws Exception {
        int databaseSizeBeforeCreate = ubigeoRepository.findAll().size();

        // Create the Ubigeo
        restUbigeoMockMvc.perform(post("/api/ubigeos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubigeo)))
            .andExpect(status().isCreated());

        // Validate the Ubigeo in the database
        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeCreate + 1);
        Ubigeo testUbigeo = ubigeoList.get(ubigeoList.size() - 1);
        assertThat(testUbigeo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testUbigeo.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testUbigeo.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testUbigeo.getDistrito()).isEqualTo(DEFAULT_DISTRITO);
    }

    @Test
    @Transactional
    public void createUbigeoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ubigeoRepository.findAll().size();

        // Create the Ubigeo with an existing ID
        ubigeo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUbigeoMockMvc.perform(post("/api/ubigeos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubigeo)))
            .andExpect(status().isBadRequest());

        // Validate the Ubigeo in the database
        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ubigeoRepository.findAll().size();
        // set the field null
        ubigeo.setCodigo(null);

        // Create the Ubigeo, which fails.

        restUbigeoMockMvc.perform(post("/api/ubigeos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubigeo)))
            .andExpect(status().isBadRequest());

        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ubigeoRepository.findAll().size();
        // set the field null
        ubigeo.setDepartamento(null);

        // Create the Ubigeo, which fails.

        restUbigeoMockMvc.perform(post("/api/ubigeos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubigeo)))
            .andExpect(status().isBadRequest());

        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ubigeoRepository.findAll().size();
        // set the field null
        ubigeo.setProvincia(null);

        // Create the Ubigeo, which fails.

        restUbigeoMockMvc.perform(post("/api/ubigeos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubigeo)))
            .andExpect(status().isBadRequest());

        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistritoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ubigeoRepository.findAll().size();
        // set the field null
        ubigeo.setDistrito(null);

        // Create the Ubigeo, which fails.

        restUbigeoMockMvc.perform(post("/api/ubigeos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubigeo)))
            .andExpect(status().isBadRequest());

        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUbigeos() throws Exception {
        // Initialize the database
        ubigeoRepository.saveAndFlush(ubigeo);

        // Get all the ubigeoList
        restUbigeoMockMvc.perform(get("/api/ubigeos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ubigeo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].departamento").value(hasItem(DEFAULT_DEPARTAMENTO.toString())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
            .andExpect(jsonPath("$.[*].distrito").value(hasItem(DEFAULT_DISTRITO.toString())));
    }

    @Test
    @Transactional
    public void getUbigeo() throws Exception {
        // Initialize the database
        ubigeoRepository.saveAndFlush(ubigeo);

        // Get the ubigeo
        restUbigeoMockMvc.perform(get("/api/ubigeos/{id}", ubigeo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ubigeo.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.departamento").value(DEFAULT_DEPARTAMENTO.toString()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
            .andExpect(jsonPath("$.distrito").value(DEFAULT_DISTRITO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUbigeo() throws Exception {
        // Get the ubigeo
        restUbigeoMockMvc.perform(get("/api/ubigeos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUbigeo() throws Exception {
        // Initialize the database
        ubigeoService.save(ubigeo);

        int databaseSizeBeforeUpdate = ubigeoRepository.findAll().size();

        // Update the ubigeo
        Ubigeo updatedUbigeo = ubigeoRepository.findOne(ubigeo.getId());
        updatedUbigeo
            .codigo(UPDATED_CODIGO)
            .departamento(UPDATED_DEPARTAMENTO)
            .provincia(UPDATED_PROVINCIA)
            .distrito(UPDATED_DISTRITO);

        restUbigeoMockMvc.perform(put("/api/ubigeos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUbigeo)))
            .andExpect(status().isOk());

        // Validate the Ubigeo in the database
        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeUpdate);
        Ubigeo testUbigeo = ubigeoList.get(ubigeoList.size() - 1);
        assertThat(testUbigeo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testUbigeo.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
        assertThat(testUbigeo.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testUbigeo.getDistrito()).isEqualTo(UPDATED_DISTRITO);
    }

    @Test
    @Transactional
    public void updateNonExistingUbigeo() throws Exception {
        int databaseSizeBeforeUpdate = ubigeoRepository.findAll().size();

        // Create the Ubigeo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUbigeoMockMvc.perform(put("/api/ubigeos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubigeo)))
            .andExpect(status().isCreated());

        // Validate the Ubigeo in the database
        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUbigeo() throws Exception {
        // Initialize the database
        ubigeoService.save(ubigeo);

        int databaseSizeBeforeDelete = ubigeoRepository.findAll().size();

        // Get the ubigeo
        restUbigeoMockMvc.perform(delete("/api/ubigeos/{id}", ubigeo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ubigeo> ubigeoList = ubigeoRepository.findAll();
        assertThat(ubigeoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ubigeo.class);
        Ubigeo ubigeo1 = new Ubigeo();
        ubigeo1.setId(1L);
        Ubigeo ubigeo2 = new Ubigeo();
        ubigeo2.setId(ubigeo1.getId());
        assertThat(ubigeo1).isEqualTo(ubigeo2);
        ubigeo2.setId(2L);
        assertThat(ubigeo1).isNotEqualTo(ubigeo2);
        ubigeo1.setId(null);
        assertThat(ubigeo1).isNotEqualTo(ubigeo2);
    }
}
