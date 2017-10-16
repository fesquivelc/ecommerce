package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.Cargo;
import com.doraliz.ecommerce.service.CargoService;
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
 * REST controller for managing Cargo.
 */
@RestController
@RequestMapping("/api")
public class CargoResource {

    private final Logger log = LoggerFactory.getLogger(CargoResource.class);

    private static final String ENTITY_NAME = "cargo";

    private final CargoService cargoService;

    public CargoResource(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    /**
     * POST  /cargos : Create a new cargo.
     *
     * @param cargo the cargo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cargo, or with status 400 (Bad Request) if the cargo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cargos")
    @Timed
    public ResponseEntity<Cargo> createCargo(@Valid @RequestBody Cargo cargo) throws URISyntaxException {
        log.debug("REST request to save Cargo : {}", cargo);
        if (cargo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cargo cannot already have an ID")).body(null);
        }
        Cargo result = cargoService.save(cargo);
        return ResponseEntity.created(new URI("/api/cargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cargos : Updates an existing cargo.
     *
     * @param cargo the cargo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cargo,
     * or with status 400 (Bad Request) if the cargo is not valid,
     * or with status 500 (Internal Server Error) if the cargo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cargos")
    @Timed
    public ResponseEntity<Cargo> updateCargo(@Valid @RequestBody Cargo cargo) throws URISyntaxException {
        log.debug("REST request to update Cargo : {}", cargo);
        if (cargo.getId() == null) {
            return createCargo(cargo);
        }
        Cargo result = cargoService.save(cargo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cargo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cargos : get all the cargos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cargos in body
     */
    @GetMapping("/cargos")
    @Timed
    public List<Cargo> getAllCargos() {
        log.debug("REST request to get all Cargos");
        return cargoService.findAll();
        }

    /**
     * GET  /cargos/:id : get the "id" cargo.
     *
     * @param id the id of the cargo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cargo, or with status 404 (Not Found)
     */
    @GetMapping("/cargos/{id}")
    @Timed
    public ResponseEntity<Cargo> getCargo(@PathVariable Long id) {
        log.debug("REST request to get Cargo : {}", id);
        Cargo cargo = cargoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cargo));
    }

    /**
     * DELETE  /cargos/:id : delete the "id" cargo.
     *
     * @param id the id of the cargo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cargos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        log.debug("REST request to delete Cargo : {}", id);
        cargoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
