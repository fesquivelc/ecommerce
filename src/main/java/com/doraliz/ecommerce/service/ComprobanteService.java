package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Comprobante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Comprobante.
 */
public interface ComprobanteService {

    /**
     * Save a comprobante.
     *
     * @param comprobante the entity to save
     * @return the persisted entity
     */
    Comprobante save(Comprobante comprobante);

    /**
     *  Get all the comprobantes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Comprobante> findAll(Pageable pageable);

    /**
     *  Get the "id" comprobante.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Comprobante findOne(Long id);

    /**
     *  Delete the "id" comprobante.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
