package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Almacen;
import java.util.List;

/**
 * Service Interface for managing Almacen.
 */
public interface AlmacenService {

    /**
     * Save a almacen.
     *
     * @param almacen the entity to save
     * @return the persisted entity
     */
    Almacen save(Almacen almacen);

    /**
     *  Get all the almacens.
     *
     *  @return the list of entities
     */
    List<Almacen> findAll();

    /**
     *  Get the "id" almacen.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Almacen findOne(Long id);

    /**
     *  Delete the "id" almacen.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
