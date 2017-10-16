package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.DireccionService;
import com.doraliz.ecommerce.domain.Direccion;
import com.doraliz.ecommerce.repository.DireccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Direccion.
 */
@Service
@Transactional
public class DireccionServiceImpl implements DireccionService{

    private final Logger log = LoggerFactory.getLogger(DireccionServiceImpl.class);

    private final DireccionRepository direccionRepository;

    public DireccionServiceImpl(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    /**
     * Save a direccion.
     *
     * @param direccion the entity to save
     * @return the persisted entity
     */
    @Override
    public Direccion save(Direccion direccion) {
        log.debug("Request to save Direccion : {}", direccion);
        return direccionRepository.save(direccion);
    }

    /**
     *  Get all the direccions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Direccion> findAll() {
        log.debug("Request to get all Direccions");
        return direccionRepository.findAll();
    }

    /**
     *  Get one direccion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Direccion findOne(Long id) {
        log.debug("Request to get Direccion : {}", id);
        return direccionRepository.findOne(id);
    }

    /**
     *  Delete the  direccion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Direccion : {}", id);
        direccionRepository.delete(id);
    }
}
