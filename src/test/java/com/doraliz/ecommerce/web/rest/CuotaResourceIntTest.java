package com.doraliz.ecommerce.web.rest;

import com.doraliz.ecommerce.EcommerceApp;

import com.doraliz.ecommerce.domain.Cuota;
import com.doraliz.ecommerce.repository.CuotaRepository;
import com.doraliz.ecommerce.service.CuotaService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doraliz.ecommerce.domain.enumeration.CuotaEstado;
/**
 * Test class for the CuotaResource REST controller.
 *
 * @see CuotaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcommerceApp.class)
public class CuotaResourceIntTest {

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_COMUNICADO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_COMUNICADO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_MONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTO = new BigDecimal(2);

    private static final CuotaEstado DEFAULT_ESTADO = CuotaEstado.PAGADA;
    private static final CuotaEstado UPDATED_ESTADO = CuotaEstado.ESPERA;

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private CuotaService cuotaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCuotaMockMvc;

    private Cuota cuota;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CuotaResource cuotaResource = new CuotaResource(cuotaService);
        this.restCuotaMockMvc = MockMvcBuilders.standaloneSetup(cuotaResource)
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
    public static Cuota createEntity(EntityManager em) {
        Cuota cuota = new Cuota()
            .fechaVencimiento(DEFAULT_FECHA_VENCIMIENTO)
            .fechaComunicado(DEFAULT_FECHA_COMUNICADO)
            .monto(DEFAULT_MONTO)
            .estado(DEFAULT_ESTADO);
        return cuota;
    }

    @Before
    public void initTest() {
        cuota = createEntity(em);
    }

    @Test
    @Transactional
    public void createCuota() throws Exception {
        int databaseSizeBeforeCreate = cuotaRepository.findAll().size();

        // Create the Cuota
        restCuotaMockMvc.perform(post("/api/cuotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuota)))
            .andExpect(status().isCreated());

        // Validate the Cuota in the database
        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeCreate + 1);
        Cuota testCuota = cuotaList.get(cuotaList.size() - 1);
        assertThat(testCuota.getFechaVencimiento()).isEqualTo(DEFAULT_FECHA_VENCIMIENTO);
        assertThat(testCuota.getFechaComunicado()).isEqualTo(DEFAULT_FECHA_COMUNICADO);
        assertThat(testCuota.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testCuota.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createCuotaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cuotaRepository.findAll().size();

        // Create the Cuota with an existing ID
        cuota.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuotaMockMvc.perform(post("/api/cuotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuota)))
            .andExpect(status().isBadRequest());

        // Validate the Cuota in the database
        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaVencimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuotaRepository.findAll().size();
        // set the field null
        cuota.setFechaVencimiento(null);

        // Create the Cuota, which fails.

        restCuotaMockMvc.perform(post("/api/cuotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuota)))
            .andExpect(status().isBadRequest());

        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaComunicadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuotaRepository.findAll().size();
        // set the field null
        cuota.setFechaComunicado(null);

        // Create the Cuota, which fails.

        restCuotaMockMvc.perform(post("/api/cuotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuota)))
            .andExpect(status().isBadRequest());

        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuotaRepository.findAll().size();
        // set the field null
        cuota.setMonto(null);

        // Create the Cuota, which fails.

        restCuotaMockMvc.perform(post("/api/cuotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuota)))
            .andExpect(status().isBadRequest());

        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuotaRepository.findAll().size();
        // set the field null
        cuota.setEstado(null);

        // Create the Cuota, which fails.

        restCuotaMockMvc.perform(post("/api/cuotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuota)))
            .andExpect(status().isBadRequest());

        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCuotas() throws Exception {
        // Initialize the database
        cuotaRepository.saveAndFlush(cuota);

        // Get all the cuotaList
        restCuotaMockMvc.perform(get("/api/cuotas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuota.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaComunicado").value(hasItem(DEFAULT_FECHA_COMUNICADO.toString())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getCuota() throws Exception {
        // Initialize the database
        cuotaRepository.saveAndFlush(cuota);

        // Get the cuota
        restCuotaMockMvc.perform(get("/api/cuotas/{id}", cuota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cuota.getId().intValue()))
            .andExpect(jsonPath("$.fechaVencimiento").value(DEFAULT_FECHA_VENCIMIENTO.toString()))
            .andExpect(jsonPath("$.fechaComunicado").value(DEFAULT_FECHA_COMUNICADO.toString()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCuota() throws Exception {
        // Get the cuota
        restCuotaMockMvc.perform(get("/api/cuotas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCuota() throws Exception {
        // Initialize the database
        cuotaService.save(cuota);

        int databaseSizeBeforeUpdate = cuotaRepository.findAll().size();

        // Update the cuota
        Cuota updatedCuota = cuotaRepository.findOne(cuota.getId());
        updatedCuota
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .fechaComunicado(UPDATED_FECHA_COMUNICADO)
            .monto(UPDATED_MONTO)
            .estado(UPDATED_ESTADO);

        restCuotaMockMvc.perform(put("/api/cuotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCuota)))
            .andExpect(status().isOk());

        // Validate the Cuota in the database
        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeUpdate);
        Cuota testCuota = cuotaList.get(cuotaList.size() - 1);
        assertThat(testCuota.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);
        assertThat(testCuota.getFechaComunicado()).isEqualTo(UPDATED_FECHA_COMUNICADO);
        assertThat(testCuota.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testCuota.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingCuota() throws Exception {
        int databaseSizeBeforeUpdate = cuotaRepository.findAll().size();

        // Create the Cuota

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCuotaMockMvc.perform(put("/api/cuotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuota)))
            .andExpect(status().isCreated());

        // Validate the Cuota in the database
        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCuota() throws Exception {
        // Initialize the database
        cuotaService.save(cuota);

        int databaseSizeBeforeDelete = cuotaRepository.findAll().size();

        // Get the cuota
        restCuotaMockMvc.perform(delete("/api/cuotas/{id}", cuota.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cuota> cuotaList = cuotaRepository.findAll();
        assertThat(cuotaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cuota.class);
        Cuota cuota1 = new Cuota();
        cuota1.setId(1L);
        Cuota cuota2 = new Cuota();
        cuota2.setId(cuota1.getId());
        assertThat(cuota1).isEqualTo(cuota2);
        cuota2.setId(2L);
        assertThat(cuota1).isNotEqualTo(cuota2);
        cuota1.setId(null);
        assertThat(cuota1).isNotEqualTo(cuota2);
    }
}
