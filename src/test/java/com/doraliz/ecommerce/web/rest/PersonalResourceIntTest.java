package com.doraliz.ecommerce.web.rest;

import com.doraliz.ecommerce.EcommerceApp;

import com.doraliz.ecommerce.domain.Personal;
import com.doraliz.ecommerce.repository.PersonalRepository;
import com.doraliz.ecommerce.service.PersonalService;
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
 * Test class for the PersonalResource REST controller.
 *
 * @see PersonalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcommerceApp.class)
public class PersonalResourceIntTest {

    private static final String DEFAULT_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonalMockMvc;

    private Personal personal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalResource personalResource = new PersonalResource(personalService);
        this.restPersonalMockMvc = MockMvcBuilders.standaloneSetup(personalResource)
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
    public static Personal createEntity(EntityManager em) {
        Personal personal = new Personal()
            .paterno(DEFAULT_PATERNO)
            .materno(DEFAULT_MATERNO)
            .nombres(DEFAULT_NOMBRES)
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .email(DEFAULT_EMAIL);
        return personal;
    }

    @Before
    public void initTest() {
        personal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonal() throws Exception {
        int databaseSizeBeforeCreate = personalRepository.findAll().size();

        // Create the Personal
        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isCreated());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeCreate + 1);
        Personal testPersonal = personalList.get(personalList.size() - 1);
        assertThat(testPersonal.getPaterno()).isEqualTo(DEFAULT_PATERNO);
        assertThat(testPersonal.getMaterno()).isEqualTo(DEFAULT_MATERNO);
        assertThat(testPersonal.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testPersonal.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
        assertThat(testPersonal.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createPersonalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalRepository.findAll().size();

        // Create the Personal with an existing ID
        personal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPaternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setPaterno(null);

        // Create the Personal, which fails.

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombresIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setNombres(null);

        // Create the Personal, which fails.

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonals() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList
        restPersonalMockMvc.perform(get("/api/personals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personal.getId().intValue())))
            .andExpect(jsonPath("$.[*].paterno").value(hasItem(DEFAULT_PATERNO.toString())))
            .andExpect(jsonPath("$.[*].materno").value(hasItem(DEFAULT_MATERNO.toString())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES.toString())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getPersonal() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get the personal
        restPersonalMockMvc.perform(get("/api/personals/{id}", personal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personal.getId().intValue()))
            .andExpect(jsonPath("$.paterno").value(DEFAULT_PATERNO.toString()))
            .andExpect(jsonPath("$.materno").value(DEFAULT_MATERNO.toString()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES.toString()))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonal() throws Exception {
        // Get the personal
        restPersonalMockMvc.perform(get("/api/personals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonal() throws Exception {
        // Initialize the database
        personalService.save(personal);

        int databaseSizeBeforeUpdate = personalRepository.findAll().size();

        // Update the personal
        Personal updatedPersonal = personalRepository.findOne(personal.getId());
        updatedPersonal
            .paterno(UPDATED_PATERNO)
            .materno(UPDATED_MATERNO)
            .nombres(UPDATED_NOMBRES)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .email(UPDATED_EMAIL);

        restPersonalMockMvc.perform(put("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonal)))
            .andExpect(status().isOk());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeUpdate);
        Personal testPersonal = personalList.get(personalList.size() - 1);
        assertThat(testPersonal.getPaterno()).isEqualTo(UPDATED_PATERNO);
        assertThat(testPersonal.getMaterno()).isEqualTo(UPDATED_MATERNO);
        assertThat(testPersonal.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testPersonal.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testPersonal.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonal() throws Exception {
        int databaseSizeBeforeUpdate = personalRepository.findAll().size();

        // Create the Personal

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonalMockMvc.perform(put("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isCreated());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonal() throws Exception {
        // Initialize the database
        personalService.save(personal);

        int databaseSizeBeforeDelete = personalRepository.findAll().size();

        // Get the personal
        restPersonalMockMvc.perform(delete("/api/personals/{id}", personal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personal.class);
        Personal personal1 = new Personal();
        personal1.setId(1L);
        Personal personal2 = new Personal();
        personal2.setId(personal1.getId());
        assertThat(personal1).isEqualTo(personal2);
        personal2.setId(2L);
        assertThat(personal1).isNotEqualTo(personal2);
        personal1.setId(null);
        assertThat(personal1).isNotEqualTo(personal2);
    }
}
