package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.Ubigeo;
import com.doraliz.ecommerce.service.UbigeoService;
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
 * REST controller for managing Ubigeo.
 */
@RestController
@RequestMapping("/api")
public class UbigeoResource {

    private final Logger log = LoggerFactory.getLogger(UbigeoResource.class);

    private static final String ENTITY_NAME = "ubigeo";

    private final UbigeoService ubigeoService;

    public UbigeoResource(UbigeoService ubigeoService) {
        this.ubigeoService = ubigeoService;
    }

    /**
     * POST  /ubigeos : Create a new ubigeo.
     *
     * @param ubigeo the ubigeo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ubigeo, or with status 400 (Bad Request) if the ubigeo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ubigeos")
    @Timed
    public ResponseEntity<Ubigeo> createUbigeo(@Valid @RequestBody Ubigeo ubigeo) throws URISyntaxException {
        log.debug("REST request to save Ubigeo : {}", ubigeo);
        if (ubigeo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ubigeo cannot already have an ID")).body(null);
        }
        Ubigeo result = ubigeoService.save(ubigeo);
        return ResponseEntity.created(new URI("/api/ubigeos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ubigeos : Updates an existing ubigeo.
     *
     * @param ubigeo the ubigeo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ubigeo,
     * or with status 400 (Bad Request) if the ubigeo is not valid,
     * or with status 500 (Internal Server Error) if the ubigeo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ubigeos")
    @Timed
    public ResponseEntity<Ubigeo> updateUbigeo(@Valid @RequestBody Ubigeo ubigeo) throws URISyntaxException {
        log.debug("REST request to update Ubigeo : {}", ubigeo);
        if (ubigeo.getId() == null) {
            return createUbigeo(ubigeo);
        }
        Ubigeo result = ubigeoService.save(ubigeo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ubigeo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ubigeos : get all the ubigeos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ubigeos in body
     */
    @GetMapping("/ubigeos")
    @Timed
    public List<Ubigeo> getAllUbigeos() {
        log.debug("REST request to get all Ubigeos");
        return ubigeoService.findAll();
        }

    /**
     * GET  /ubigeos/:id : get the "id" ubigeo.
     *
     * @param id the id of the ubigeo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ubigeo, or with status 404 (Not Found)
     */
    @GetMapping("/ubigeos/{id}")
    @Timed
    public ResponseEntity<Ubigeo> getUbigeo(@PathVariable Long id) {
        log.debug("REST request to get Ubigeo : {}", id);
        Ubigeo ubigeo = ubigeoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ubigeo));
    }

    /**
     * DELETE  /ubigeos/:id : delete the "id" ubigeo.
     *
     * @param id the id of the ubigeo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ubigeos/{id}")
    @Timed
    public ResponseEntity<Void> deleteUbigeo(@PathVariable Long id) {
        log.debug("REST request to delete Ubigeo : {}", id);
        ubigeoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
