package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Personal.
 */
public interface PersonalService {

    /**
     * Save a personal.
     *
     * @param personal the entity to save
     * @return the persisted entity
     */
    Personal save(Personal personal);

    /**
     *  Get all the personals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Personal> findAll(Pageable pageable);

    /**
     *  Get the "id" personal.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Personal findOne(Long id);

    /**
     *  Delete the "id" personal.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
