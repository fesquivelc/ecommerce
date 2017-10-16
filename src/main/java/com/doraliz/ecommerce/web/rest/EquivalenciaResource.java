package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.Equivalencia;
import com.doraliz.ecommerce.service.EquivalenciaService;
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
 * REST controller for managing Equivalencia.
 */
@RestController
@RequestMapping("/api")
public class EquivalenciaResource {

    private final Logger log = LoggerFactory.getLogger(EquivalenciaResource.class);

    private static final String ENTITY_NAME = "equivalencia";

    private final EquivalenciaService equivalenciaService;

    public EquivalenciaResource(EquivalenciaService equivalenciaService) {
        this.equivalenciaService = equivalenciaService;
    }

    /**
     * POST  /equivalencias : Create a new equivalencia.
     *
     * @param equivalencia the equivalencia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new equivalencia, or with status 400 (Bad Request) if the equivalencia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/equivalencias")
    @Timed
    public ResponseEntity<Equivalencia> createEquivalencia(@Valid @RequestBody Equivalencia equivalencia) throws URISyntaxException {
        log.debug("REST request to save Equivalencia : {}", equivalencia);
        if (equivalencia.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new equivalencia cannot already have an ID")).body(null);
        }
        Equivalencia result = equivalenciaService.save(equivalencia);
        return ResponseEntity.created(new URI("/api/equivalencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /equivalencias : Updates an existing equivalencia.
     *
     * @param equivalencia the equivalencia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated equivalencia,
     * or with status 400 (Bad Request) if the equivalencia is not valid,
     * or with status 500 (Internal Server Error) if the equivalencia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/equivalencias")
    @Timed
    public ResponseEntity<Equivalencia> updateEquivalencia(@Valid @RequestBody Equivalencia equivalencia) throws URISyntaxException {
        log.debug("REST request to update Equivalencia : {}", equivalencia);
        if (equivalencia.getId() == null) {
            return createEquivalencia(equivalencia);
        }
        Equivalencia result = equivalenciaService.save(equivalencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, equivalencia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /equivalencias : get all the equivalencias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of equivalencias in body
     */
    @GetMapping("/equivalencias")
    @Timed
    public List<Equivalencia> getAllEquivalencias() {
        log.debug("REST request to get all Equivalencias");
        return equivalenciaService.findAll();
        }

    /**
     * GET  /equivalencias/:id : get the "id" equivalencia.
     *
     * @param id the id of the equivalencia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the equivalencia, or with status 404 (Not Found)
     */
    @GetMapping("/equivalencias/{id}")
    @Timed
    public ResponseEntity<Equivalencia> getEquivalencia(@PathVariable Long id) {
        log.debug("REST request to get Equivalencia : {}", id);
        Equivalencia equivalencia = equivalenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(equivalencia));
    }

    /**
     * DELETE  /equivalencias/:id : delete the "id" equivalencia.
     *
     * @param id the id of the equivalencia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/equivalencias/{id}")
    @Timed
    public ResponseEntity<Void> deleteEquivalencia(@PathVariable Long id) {
        log.debug("REST request to delete Equivalencia : {}", id);
        equivalenciaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
