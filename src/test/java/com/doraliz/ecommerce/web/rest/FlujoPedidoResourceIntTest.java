package com.doraliz.ecommerce.web.rest;

import com.doraliz.ecommerce.EcommerceApp;

import com.doraliz.ecommerce.domain.FlujoPedido;
import com.doraliz.ecommerce.repository.FlujoPedidoRepository;
import com.doraliz.ecommerce.service.FlujoPedidoService;
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
 * Test class for the FlujoPedidoResource REST controller.
 *
 * @see FlujoPedidoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcommerceApp.class)
public class FlujoPedidoResourceIntTest {

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private FlujoPedidoRepository flujoPedidoRepository;

    @Autowired
    private FlujoPedidoService flujoPedidoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFlujoPedidoMockMvc;

    private FlujoPedido flujoPedido;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FlujoPedidoResource flujoPedidoResource = new FlujoPedidoResource(flujoPedidoService);
        this.restFlujoPedidoMockMvc = MockMvcBuilders.standaloneSetup(flujoPedidoResource)
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
    public static FlujoPedido createEntity(EntityManager em) {
        FlujoPedido flujoPedido = new FlujoPedido()
            .activo(DEFAULT_ACTIVO);
        return flujoPedido;
    }

    @Before
    public void initTest() {
        flujoPedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createFlujoPedido() throws Exception {
        int databaseSizeBeforeCreate = flujoPedidoRepository.findAll().size();

        // Create the FlujoPedido
        restFlujoPedidoMockMvc.perform(post("/api/flujo-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flujoPedido)))
            .andExpect(status().isCreated());

        // Validate the FlujoPedido in the database
        List<FlujoPedido> flujoPedidoList = flujoPedidoRepository.findAll();
        assertThat(flujoPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        FlujoPedido testFlujoPedido = flujoPedidoList.get(flujoPedidoList.size() - 1);
        assertThat(testFlujoPedido.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createFlujoPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = flujoPedidoRepository.findAll().size();

        // Create the FlujoPedido with an existing ID
        flujoPedido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlujoPedidoMockMvc.perform(post("/api/flujo-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flujoPedido)))
            .andExpect(status().isBadRequest());

        // Validate the FlujoPedido in the database
        List<FlujoPedido> flujoPedidoList = flujoPedidoRepository.findAll();
        assertThat(flujoPedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = flujoPedidoRepository.findAll().size();
        // set the field null
        flujoPedido.setActivo(null);

        // Create the FlujoPedido, which fails.

        restFlujoPedidoMockMvc.perform(post("/api/flujo-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flujoPedido)))
            .andExpect(status().isBadRequest());

        List<FlujoPedido> flujoPedidoList = flujoPedidoRepository.findAll();
        assertThat(flujoPedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFlujoPedidos() throws Exception {
        // Initialize the database
        flujoPedidoRepository.saveAndFlush(flujoPedido);

        // Get all the flujoPedidoList
        restFlujoPedidoMockMvc.perform(get("/api/flujo-pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flujoPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getFlujoPedido() throws Exception {
        // Initialize the database
        flujoPedidoRepository.saveAndFlush(flujoPedido);

        // Get the flujoPedido
        restFlujoPedidoMockMvc.perform(get("/api/flujo-pedidos/{id}", flujoPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(flujoPedido.getId().intValue()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFlujoPedido() throws Exception {
        // Get the flujoPedido
        restFlujoPedidoMockMvc.perform(get("/api/flujo-pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFlujoPedido() throws Exception {
        // Initialize the database
        flujoPedidoService.save(flujoPedido);

        int databaseSizeBeforeUpdate = flujoPedidoRepository.findAll().size();

        // Update the flujoPedido
        FlujoPedido updatedFlujoPedido = flujoPedidoRepository.findOne(flujoPedido.getId());
        updatedFlujoPedido
            .activo(UPDATED_ACTIVO);

        restFlujoPedidoMockMvc.perform(put("/api/flujo-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFlujoPedido)))
            .andExpect(status().isOk());

        // Validate the FlujoPedido in the database
        List<FlujoPedido> flujoPedidoList = flujoPedidoRepository.findAll();
        assertThat(flujoPedidoList).hasSize(databaseSizeBeforeUpdate);
        FlujoPedido testFlujoPedido = flujoPedidoList.get(flujoPedidoList.size() - 1);
        assertThat(testFlujoPedido.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingFlujoPedido() throws Exception {
        int databaseSizeBeforeUpdate = flujoPedidoRepository.findAll().size();

        // Create the FlujoPedido

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFlujoPedidoMockMvc.perform(put("/api/flujo-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flujoPedido)))
            .andExpect(status().isCreated());

        // Validate the FlujoPedido in the database
        List<FlujoPedido> flujoPedidoList = flujoPedidoRepository.findAll();
        assertThat(flujoPedidoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFlujoPedido() throws Exception {
        // Initialize the database
        flujoPedidoService.save(flujoPedido);

        int databaseSizeBeforeDelete = flujoPedidoRepository.findAll().size();

        // Get the flujoPedido
        restFlujoPedidoMockMvc.perform(delete("/api/flujo-pedidos/{id}", flujoPedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FlujoPedido> flujoPedidoList = flujoPedidoRepository.findAll();
        assertThat(flujoPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FlujoPedido.class);
        FlujoPedido flujoPedido1 = new FlujoPedido();
        flujoPedido1.setId(1L);
        FlujoPedido flujoPedido2 = new FlujoPedido();
        flujoPedido2.setId(flujoPedido1.getId());
        assertThat(flujoPedido1).isEqualTo(flujoPedido2);
        flujoPedido2.setId(2L);
        assertThat(flujoPedido1).isNotEqualTo(flujoPedido2);
        flujoPedido1.setId(null);
        assertThat(flujoPedido1).isNotEqualTo(flujoPedido2);
    }
}
