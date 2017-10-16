package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.Marca;
import com.doraliz.ecommerce.service.MarcaService;
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
 * REST controller for managing Marca.
 */
@RestController
@RequestMapping("/api")
public class MarcaResource {

    private final Logger log = LoggerFactory.getLogger(MarcaResource.class);

    private static final String ENTITY_NAME = "marca";

    private final MarcaService marcaService;

    public MarcaResource(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    /**
     * POST  /marcas : Create a new marca.
     *
     * @param marca the marca to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marca, or with status 400 (Bad Request) if the marca has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marcas")
    @Timed
    public ResponseEntity<Marca> createMarca(@Valid @RequestBody Marca marca) throws URISyntaxException {
        log.debug("REST request to save Marca : {}", marca);
        if (marca.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marca cannot already have an ID")).body(null);
        }
        Marca result = marcaService.save(marca);
        return ResponseEntity.created(new URI("/api/marcas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marcas : Updates an existing marca.
     *
     * @param marca the marca to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marca,
     * or with status 400 (Bad Request) if the marca is not valid,
     * or with status 500 (Internal Server Error) if the marca couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marcas")
    @Timed
    public ResponseEntity<Marca> updateMarca(@Valid @RequestBody Marca marca) throws URISyntaxException {
        log.debug("REST request to update Marca : {}", marca);
        if (marca.getId() == null) {
            return createMarca(marca);
        }
        Marca result = marcaService.save(marca);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marca.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marcas : get all the marcas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marcas in body
     */
    @GetMapping("/marcas")
    @Timed
    public List<Marca> getAllMarcas() {
        log.debug("REST request to get all Marcas");
        return marcaService.findAll();
        }

    /**
     * GET  /marcas/:id : get the "id" marca.
     *
     * @param id the id of the marca to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marca, or with status 404 (Not Found)
     */
    @GetMapping("/marcas/{id}")
    @Timed
    public ResponseEntity<Marca> getMarca(@PathVariable Long id) {
        log.debug("REST request to get Marca : {}", id);
        Marca marca = marcaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marca));
    }

    /**
     * DELETE  /marcas/:id : delete the "id" marca.
     *
     * @param id the id of the marca to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marcas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
        log.debug("REST request to delete Marca : {}", id);
        marcaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
