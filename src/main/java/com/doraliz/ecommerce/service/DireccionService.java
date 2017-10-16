package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Direccion;
import java.util.List;

/**
 * Service Interface for managing Direccion.
 */
public interface DireccionService {

    /**
     * Save a direccion.
     *
     * @param direccion the entity to save
     * @return the persisted entity
     */
    Direccion save(Direccion direccion);

    /**
     *  Get all the direccions.
     *
     *  @return the list of entities
     */
    List<Direccion> findAll();

    /**
     *  Get the "id" direccion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Direccion findOne(Long id);

    /**
     *  Delete the "id" direccion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
