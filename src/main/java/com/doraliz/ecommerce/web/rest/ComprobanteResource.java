package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.Comprobante;
import com.doraliz.ecommerce.service.ComprobanteService;
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
 * REST controller for managing Comprobante.
 */
@RestController
@RequestMapping("/api")
public class ComprobanteResource {

    private final Logger log = LoggerFactory.getLogger(ComprobanteResource.class);

    private static final String ENTITY_NAME = "comprobante";

    private final ComprobanteService comprobanteService;

    public ComprobanteResource(ComprobanteService comprobanteService) {
        this.comprobanteService = comprobanteService;
    }

    /**
     * POST  /comprobantes : Create a new comprobante.
     *
     * @param comprobante the comprobante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comprobante, or with status 400 (Bad Request) if the comprobante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comprobantes")
    @Timed
    public ResponseEntity<Comprobante> createComprobante(@Valid @RequestBody Comprobante comprobante) throws URISyntaxException {
        log.debug("REST request to save Comprobante : {}", comprobante);
        if (comprobante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new comprobante cannot already have an ID")).body(null);
        }
        Comprobante result = comprobanteService.save(comprobante);
        return ResponseEntity.created(new URI("/api/comprobantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comprobantes : Updates an existing comprobante.
     *
     * @param comprobante the comprobante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comprobante,
     * or with status 400 (Bad Request) if the comprobante is not valid,
     * or with status 500 (Internal Server Error) if the comprobante couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comprobantes")
    @Timed
    public ResponseEntity<Comprobante> updateComprobante(@Valid @RequestBody Comprobante comprobante) throws URISyntaxException {
        log.debug("REST request to update Comprobante : {}", comprobante);
        if (comprobante.getId() == null) {
            return createComprobante(comprobante);
        }
        Comprobante result = comprobanteService.save(comprobante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comprobante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comprobantes : get all the comprobantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comprobantes in body
     */
    @GetMapping("/comprobantes")
    @Timed
    public ResponseEntity<List<Comprobante>> getAllComprobantes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Comprobantes");
        Page<Comprobante> page = comprobanteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comprobantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comprobantes/:id : get the "id" comprobante.
     *
     * @param id the id of the comprobante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comprobante, or with status 404 (Not Found)
     */
    @GetMapping("/comprobantes/{id}")
    @Timed
    public ResponseEntity<Comprobante> getComprobante(@PathVariable Long id) {
        log.debug("REST request to get Comprobante : {}", id);
        Comprobante comprobante = comprobanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comprobante));
    }

    /**
     * DELETE  /comprobantes/:id : delete the "id" comprobante.
     *
     * @param id the id of the comprobante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comprobantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteComprobante(@PathVariable Long id) {
        log.debug("REST request to delete Comprobante : {}", id);
        comprobanteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
