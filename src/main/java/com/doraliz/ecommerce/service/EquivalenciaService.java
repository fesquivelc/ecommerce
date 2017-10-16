package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Equivalencia;
import java.util.List;

/**
 * Service Interface for managing Equivalencia.
 */
public interface EquivalenciaService {

    /**
     * Save a equivalencia.
     *
     * @param equivalencia the entity to save
     * @return the persisted entity
     */
    Equivalencia save(Equivalencia equivalencia);

    /**
     *  Get all the equivalencias.
     *
     *  @return the list of entities
     */
    List<Equivalencia> findAll();

    /**
     *  Get the "id" equivalencia.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Equivalencia findOne(Long id);

    /**
     *  Delete the "id" equivalencia.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
