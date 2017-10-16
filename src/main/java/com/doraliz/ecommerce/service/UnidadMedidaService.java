package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.UnidadMedida;
import java.util.List;

/**
 * Service Interface for managing UnidadMedida.
 */
public interface UnidadMedidaService {

    /**
     * Save a unidadMedida.
     *
     * @param unidadMedida the entity to save
     * @return the persisted entity
     */
    UnidadMedida save(UnidadMedida unidadMedida);

    /**
     *  Get all the unidadMedidas.
     *
     *  @return the list of entities
     */
    List<UnidadMedida> findAll();

    /**
     *  Get the "id" unidadMedida.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UnidadMedida findOne(Long id);

    /**
     *  Delete the "id" unidadMedida.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
