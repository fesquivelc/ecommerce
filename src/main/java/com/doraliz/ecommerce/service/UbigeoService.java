package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Ubigeo;
import java.util.List;

/**
 * Service Interface for managing Ubigeo.
 */
public interface UbigeoService {

    /**
     * Save a ubigeo.
     *
     * @param ubigeo the entity to save
     * @return the persisted entity
     */
    Ubigeo save(Ubigeo ubigeo);

    /**
     *  Get all the ubigeos.
     *
     *  @return the list of entities
     */
    List<Ubigeo> findAll();

    /**
     *  Get the "id" ubigeo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Ubigeo findOne(Long id);

    /**
     *  Delete the "id" ubigeo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
