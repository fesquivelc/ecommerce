package com.doraliz.ecommerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doraliz.ecommerce.domain.Personal;
import com.doraliz.ecommerce.service.PersonalService;
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
 * REST controller for managing Personal.
 */
@RestController
@RequestMapping("/api")
public class PersonalResource {

    private final Logger log = LoggerFactory.getLogger(PersonalResource.class);

    private static final String ENTITY_NAME = "personal";

    private final PersonalService personalService;

    public PersonalResource(PersonalService personalService) {
        this.personalService = personalService;
    }

    /**
     * POST  /personals : Create a new personal.
     *
     * @param personal the personal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personal, or with status 400 (Bad Request) if the personal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personals")
    @Timed
    public ResponseEntity<Personal> createPersonal(@Valid @RequestBody Personal personal) throws URISyntaxException {
        log.debug("REST request to save Personal : {}", personal);
        if (personal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personal cannot already have an ID")).body(null);
        }
        Personal result = personalService.save(personal);
        return ResponseEntity.created(new URI("/api/personals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personals : Updates an existing personal.
     *
     * @param personal the personal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personal,
     * or with status 400 (Bad Request) if the personal is not valid,
     * or with status 500 (Internal Server Error) if the personal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personals")
    @Timed
    public ResponseEntity<Personal> updatePersonal(@Valid @RequestBody Personal personal) throws URISyntaxException {
        log.debug("REST request to update Personal : {}", personal);
        if (personal.getId() == null) {
            return createPersonal(personal);
        }
        Personal result = personalService.save(personal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personals : get all the personals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personals in body
     */
    @GetMapping("/personals")
    @Timed
    public ResponseEntity<List<Personal>> getAllPersonals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Personals");
        Page<Personal> page = personalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personals/:id : get the "id" personal.
     *
     * @param id the id of the personal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personal, or with status 404 (Not Found)
     */
    @GetMapping("/personals/{id}")
    @Timed
    public ResponseEntity<Personal> getPersonal(@PathVariable Long id) {
        log.debug("REST request to get Personal : {}", id);
        Personal personal = personalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personal));
    }

    /**
     * DELETE  /personals/:id : delete the "id" personal.
     *
     * @param id the id of the personal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personals/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonal(@PathVariable Long id) {
        log.debug("REST request to delete Personal : {}", id);
        personalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
