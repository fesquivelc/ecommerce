package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.Cuota;
import com.doraliz.ecommerce.service.CuotaService;
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
 * REST controller for managing Cuota.
 */
@RestController
@RequestMapping("/api")
public class CuotaResource {

    private final Logger log = LoggerFactory.getLogger(CuotaResource.class);

    private static final String ENTITY_NAME = "cuota";

    private final CuotaService cuotaService;

    public CuotaResource(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    /**
     * POST  /cuotas : Create a new cuota.
     *
     * @param cuota the cuota to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cuota, or with status 400 (Bad Request) if the cuota has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cuotas")
    @Timed
    public ResponseEntity<Cuota> createCuota(@Valid @RequestBody Cuota cuota) throws URISyntaxException {
        log.debug("REST request to save Cuota : {}", cuota);
        if (cuota.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cuota cannot already have an ID")).body(null);
        }
        Cuota result = cuotaService.save(cuota);
        return ResponseEntity.created(new URI("/api/cuotas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cuotas : Updates an existing cuota.
     *
     * @param cuota the cuota to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cuota,
     * or with status 400 (Bad Request) if the cuota is not valid,
     * or with status 500 (Internal Server Error) if the cuota couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cuotas")
    @Timed
    public ResponseEntity<Cuota> updateCuota(@Valid @RequestBody Cuota cuota) throws URISyntaxException {
        log.debug("REST request to update Cuota : {}", cuota);
        if (cuota.getId() == null) {
            return createCuota(cuota);
        }
        Cuota result = cuotaService.save(cuota);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cuota.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cuotas : get all the cuotas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cuotas in body
     */
    @GetMapping("/cuotas")
    @Timed
    public List<Cuota> getAllCuotas() {
        log.debug("REST request to get all Cuotas");
        return cuotaService.findAll();
        }

    /**
     * GET  /cuotas/:id : get the "id" cuota.
     *
     * @param id the id of the cuota to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cuota, or with status 404 (Not Found)
     */
    @GetMapping("/cuotas/{id}")
    @Timed
    public ResponseEntity<Cuota> getCuota(@PathVariable Long id) {
        log.debug("REST request to get Cuota : {}", id);
        Cuota cuota = cuotaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cuota));
    }

    /**
     * DELETE  /cuotas/:id : delete the "id" cuota.
     *
     * @param id the id of the cuota to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cuotas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCuota(@PathVariable Long id) {
        log.debug("REST request to delete Cuota : {}", id);
        cuotaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
