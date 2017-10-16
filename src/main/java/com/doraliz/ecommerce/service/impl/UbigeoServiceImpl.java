package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.UbigeoService;
import com.doraliz.ecommerce.domain.Ubigeo;
import com.doraliz.ecommerce.repository.UbigeoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Ubigeo.
 */
@Service
@Transactional
public class UbigeoServiceImpl implements UbigeoService{

    private final Logger log = LoggerFactory.getLogger(UbigeoServiceImpl.class);

    private final UbigeoRepository ubigeoRepository;

    public UbigeoServiceImpl(UbigeoRepository ubigeoRepository) {
        this.ubigeoRepository = ubigeoRepository;
    }

    /**
     * Save a ubigeo.
     *
     * @param ubigeo the entity to save
     * @return the persisted entity
     */
    @Override
    public Ubigeo save(Ubigeo ubigeo) {
        log.debug("Request to save Ubigeo : {}", ubigeo);
        return ubigeoRepository.save(ubigeo);
    }

    /**
     *  Get all the ubigeos.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Ubigeo> findAll() {
        log.debug("Request to get all Ubigeos");
        return ubigeoRepository.findAll();
    }

    /**
     *  Get one ubigeo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Ubigeo findOne(Long id) {
        log.debug("Request to get Ubigeo : {}", id);
        return ubigeoRepository.findOne(id);
    }

    /**
     *  Delete the  ubigeo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ubigeo : {}", id);
        ubigeoRepository.delete(id);
    }
}
