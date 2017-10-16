package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Inventario;
import java.util.List;

/**
 * Service Interface for managing Inventario.
 */
public interface InventarioService {

    /**
     * Save a inventario.
     *
     * @param inventario the entity to save
     * @return the persisted entity
     */
    Inventario save(Inventario inventario);

    /**
     *  Get all the inventarios.
     *
     *  @return the list of entities
     */
    List<Inventario> findAll();

    /**
     *  Get the "id" inventario.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Inventario findOne(Long id);

    /**
     *  Delete the "id" inventario.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
