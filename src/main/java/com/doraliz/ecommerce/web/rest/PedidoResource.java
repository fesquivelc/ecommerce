package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.Pedido;
import com.doraliz.ecommerce.service.PedidoService;
import com.doraliz.ecommerce.web.rest.util.HeaderUtil;
import com.doraliz.ecommerce.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pedido.
 */
@RestController
@RequestMapping("/api")
public class PedidoResource {

    private final Logger log = LoggerFactory.getLogger(PedidoResource.class);

    private static final String ENTITY_NAME = "pedido";

    private final PedidoService pedidoService;

    public PedidoResource(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * POST  /pedidos : Create a new pedido.
     *
     * @param pedido the pedido to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pedido, or with status 400 (Bad Request) if the pedido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pedidos")
    @Timed
    public ResponseEntity<Pedido> createPedido(@Valid @RequestBody Pedido pedido) throws URISyntaxException {
        log.debug("REST request to save Pedido : {}", pedido);
        if (pedido.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pedido cannot already have an ID")).body(null);
        }
        Pedido result = pedidoService.save(pedido);
        return ResponseEntity.created(new URI("/api/pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pedidos : Updates an existing pedido.
     *
     * @param pedido the pedido to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pedido,
     * or with status 400 (Bad Request) if the pedido is not valid,
     * or with status 500 (Internal Server Error) if the pedido couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pedidos")
    @Timed
    public ResponseEntity<Pedido> updatePedido(@Valid @RequestBody Pedido pedido) throws URISyntaxException {
        log.debug("REST request to update Pedido : {}", pedido);
        if (pedido.getId() == null) {
            return createPedido(pedido);
        }
        Pedido result = pedidoService.save(pedido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pedido.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pedidos : get all the pedidos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pedidos in body
     */
    @GetMapping("/pedidos")
    @Timed
    public ResponseEntity<List<Pedido>> getAllPedidos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Pedidos");
        Page<Pedido> page = pedidoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pedidos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pedidos/:id : get the "id" pedido.
     *
     * @param id the id of the pedido to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pedido, or with status 404 (Not Found)
     */
    @GetMapping("/pedidos/{id}")
    @Timed
    public ResponseEntity<Pedido> getPedido(@PathVariable Long id) {
        log.debug("REST request to get Pedido : {}", id);
        Pedido pedido = pedidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pedido));
    }

    /**
     * DELETE  /pedidos/:id : delete the "id" pedido.
     *
     * @param id the id of the pedido to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pedidos/{id}")
    @Timed
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        log.debug("REST request to delete Pedido : {}", id);
        pedidoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
