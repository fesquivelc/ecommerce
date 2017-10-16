package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Cuota;
import java.util.List;

/**
 * Service Interface for managing Cuota.
 */
public interface CuotaService {

    /**
     * Save a cuota.
     *
     * @param cuota the entity to save
     * @return the persisted entity
     */
    Cuota save(Cuota cuota);

    /**
     *  Get all the cuotas.
     *
     *  @return the list of entities
     */
    List<Cuota> findAll();

    /**
     *  Get the "id" cuota.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Cuota findOne(Long id);

    /**
     *  Delete the "id" cuota.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
