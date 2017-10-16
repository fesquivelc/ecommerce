package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.UnidadMedida;
import com.doraliz.ecommerce.service.UnidadMedidaService;
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
 * REST controller for managing UnidadMedida.
 */
@RestController
@RequestMapping("/api")
public class UnidadMedidaResource {

    private final Logger log = LoggerFactory.getLogger(UnidadMedidaResource.class);

    private static final String ENTITY_NAME = "unidadMedida";

    private final UnidadMedidaService unidadMedidaService;

    public UnidadMedidaResource(UnidadMedidaService unidadMedidaService) {
        this.unidadMedidaService = unidadMedidaService;
    }

    /**
     * POST  /unidad-medidas : Create a new unidadMedida.
     *
     * @param unidadMedida the unidadMedida to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unidadMedida, or with status 400 (Bad Request) if the unidadMedida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unidad-medidas")
    @Timed
    public ResponseEntity<UnidadMedida> createUnidadMedida(@Valid @RequestBody UnidadMedida unidadMedida) throws URISyntaxException {
        log.debug("REST request to save UnidadMedida : {}", unidadMedida);
        if (unidadMedida.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new unidadMedida cannot already have an ID")).body(null);
        }
        UnidadMedida result = unidadMedidaService.save(unidadMedida);
        return ResponseEntity.created(new URI("/api/unidad-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unidad-medidas : Updates an existing unidadMedida.
     *
     * @param unidadMedida the unidadMedida to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unidadMedida,
     * or with status 400 (Bad Request) if the unidadMedida is not valid,
     * or with status 500 (Internal Server Error) if the unidadMedida couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unidad-medidas")
    @Timed
    public ResponseEntity<UnidadMedida> updateUnidadMedida(@Valid @RequestBody UnidadMedida unidadMedida) throws URISyntaxException {
        log.debug("REST request to update UnidadMedida : {}", unidadMedida);
        if (unidadMedida.getId() == null) {
            return createUnidadMedida(unidadMedida);
        }
        UnidadMedida result = unidadMedidaService.save(unidadMedida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unidadMedida.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unidad-medidas : get all the unidadMedidas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unidadMedidas in body
     */
    @GetMapping("/unidad-medidas")
    @Timed
    public List<UnidadMedida> getAllUnidadMedidas() {
        log.debug("REST request to get all UnidadMedidas");
        return unidadMedidaService.findAll();
        }

    /**
     * GET  /unidad-medidas/:id : get the "id" unidadMedida.
     *
     * @param id the id of the unidadMedida to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unidadMedida, or with status 404 (Not Found)
     */
    @GetMapping("/unidad-medidas/{id}")
    @Timed
    public ResponseEntity<UnidadMedida> getUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to get UnidadMedida : {}", id);
        UnidadMedida unidadMedida = unidadMedidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(unidadMedida));
    }

    /**
     * DELETE  /unidad-medidas/:id : delete the "id" unidadMedida.
     *
     * @param id the id of the unidadMedida to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unidad-medidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to delete UnidadMedida : {}", id);
        unidadMedidaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
