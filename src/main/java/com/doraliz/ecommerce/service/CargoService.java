package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Cargo;
import java.util.List;

/**
 * Service Interface for managing Cargo.
 */
public interface CargoService {

    /**
     * Save a cargo.
     *
     * @param cargo the entity to save
     * @return the persisted entity
     */
    Cargo save(Cargo cargo);

    /**
     *  Get all the cargos.
     *
     *  @return the list of entities
     */
    List<Cargo> findAll();

    /**
     *  Get the "id" cargo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Cargo findOne(Long id);

    /**
     *  Delete the "id" cargo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
