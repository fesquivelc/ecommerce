package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Marca;
import java.util.List;

/**
 * Service Interface for managing Marca.
 */
public interface MarcaService {

    /**
     * Save a marca.
     *
     * @param marca the entity to save
     * @return the persisted entity
     */
    Marca save(Marca marca);

    /**
     *  Get all the marcas.
     *
     *  @return the list of entities
     */
    List<Marca> findAll();

    /**
     *  Get the "id" marca.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Marca findOne(Long id);

    /**
     *  Delete the "id" marca.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
