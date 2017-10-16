package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.AlmacenService;
import com.doraliz.ecommerce.domain.Almacen;
import com.doraliz.ecommerce.repository.AlmacenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Almacen.
 */
@Service
@Transactional
public class AlmacenServiceImpl implements AlmacenService{

    private final Logger log = LoggerFactory.getLogger(AlmacenServiceImpl.class);

    private final AlmacenRepository almacenRepository;

    public AlmacenServiceImpl(AlmacenRepository almacenRepository) {
        this.almacenRepository = almacenRepository;
    }

    /**
     * Save a almacen.
     *
     * @param almacen the entity to save
     * @return the persisted entity
     */
    @Override
    public Almacen save(Almacen almacen) {
        log.debug("Request to save Almacen : {}", almacen);
        return almacenRepository.save(almacen);
    }

    /**
     *  Get all the almacens.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Almacen> findAll() {
        log.debug("Request to get all Almacens");
        return almacenRepository.findAll();
    }

    /**
     *  Get one almacen by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Almacen findOne(Long id) {
        log.debug("Request to get Almacen : {}", id);
        return almacenRepository.findOne(id);
    }

    /**
     *  Delete the  almacen by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Almacen : {}", id);
        almacenRepository.delete(id);
    }
}
