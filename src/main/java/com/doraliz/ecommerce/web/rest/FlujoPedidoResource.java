package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.FlujoPedido;
import com.doraliz.ecommerce.service.FlujoPedidoService;
import com.doraliz.ecommerce.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FlujoPedido.
 */
@RestController
@RequestMapping("/api")
public class FlujoPedidoResource {

    private final Logger log = LoggerFactory.getLogger(FlujoPedidoResource.class);

    private static final String ENTITY_NAME = "flujoPedido";

    private final FlujoPedidoService flujoPedidoService;

    public FlujoPedidoResource(FlujoPedidoService flujoPedidoService) {
        this.flujoPedidoService = flujoPedidoService;
    }

    /**
     * POST  /flujo-pedidos : Create a new flujoPedido.
     *
     * @param flujoPedido the flujoPedido to create
     * @return the ResponseEntity with status 201 (Created) and with body the new flujoPedido, or with status 400 (Bad Request) if the flujoPedido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/flujo-pedidos")
    @Timed
    public ResponseEntity<FlujoPedido> createFlujoPedido(@Valid @RequestBody FlujoPedido flujoPedido) throws URISyntaxException {
        log.debug("REST request to save FlujoPedido : {}", flujoPedido);
        if (flujoPedido.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new flujoPedido cannot already have an ID")).body(null);
        }
        FlujoPedido result = flujoPedidoService.save(flujoPedido);
        return ResponseEntity.created(new URI("/api/flujo-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /flujo-pedidos : Updates an existing flujoPedido.
     *
     * @param flujoPedido the flujoPedido to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated flujoPedido,
     * or with status 400 (Bad Request) if the flujoPedido is not valid,
     * or with status 500 (Internal Server Error) if the flujoPedido couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/flujo-pedidos")
    @Timed
    public ResponseEntity<FlujoPedido> updateFlujoPedido(@Valid @RequestBody FlujoPedido flujoPedido) throws URISyntaxException {
        log.debug("REST request to update FlujoPedido : {}", flujoPedido);
        if (flujoPedido.getId() == null) {
            return createFlujoPedido(flujoPedido);
        }
        FlujoPedido result = flujoPedidoService.save(flujoPedido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, flujoPedido.getId().toString()))
            .body(result);
    }

    /**
     * GET  /flujo-pedidos : get all the flujoPedidos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of flujoPedidos in body
     */
    @GetMapping("/flujo-pedidos")
    @Timed
    public List<FlujoPedido> getAllFlujoPedidos() {
        log.debug("REST request to get all FlujoPedidos");
        return flujoPedidoService.findAll();
        }

    /**
     * GET  /flujo-pedidos/:id : get the "id" flujoPedido.
     *
     * @param id the id of the flujoPedido to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the flujoPedido, or with status 404 (Not Found)
     */
    @GetMapping("/flujo-pedidos/{id}")
    @Timed
    public ResponseEntity<FlujoPedido> getFlujoPedido(@PathVariable Long id) {
        log.debug("REST request to get FlujoPedido : {}", id);
        FlujoPedido flujoPedido = flujoPedidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(flujoPedido));
    }

    /**
     * DELETE  /flujo-pedidos/:id : delete the "id" flujoPedido.
     *
     * @param id the id of the flujoPedido to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/flujo-pedidos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFlujoPedido(@PathVariable Long id) {
        log.debug("REST request to delete FlujoPedido : {}", id);
        flujoPedidoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
