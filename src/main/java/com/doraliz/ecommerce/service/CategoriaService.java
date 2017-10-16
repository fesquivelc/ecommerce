package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Categoria;
import java.util.List;

/**
 * Service Interface for managing Categoria.
 */
public interface CategoriaService {

    /**
     * Save a categoria.
     *
     * @param categoria the entity to save
     * @return the persisted entity
     */
    Categoria save(Categoria categoria);

    /**
     *  Get all the categorias.
     *
     *  @return the list of entities
     */
    List<Categoria> findAll();

    /**
     *  Get the "id" categoria.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Categoria findOne(Long id);

    /**
     *  Delete the "id" categoria.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
