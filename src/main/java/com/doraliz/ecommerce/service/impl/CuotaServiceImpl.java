package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.CuotaService;
import com.doraliz.ecommerce.domain.Cuota;
import com.doraliz.ecommerce.repository.CuotaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Cuota.
 */
@Service
@Transactional
public class CuotaServiceImpl implements CuotaService{

    private final Logger log = LoggerFactory.getLogger(CuotaServiceImpl.class);

    private final CuotaRepository cuotaRepository;

    public CuotaServiceImpl(CuotaRepository cuotaRepository) {
        this.cuotaRepository = cuotaRepository;
    }

    /**
     * Save a cuota.
     *
     * @param cuota the entity to save
     * @return the persisted entity
     */
    @Override
    public Cuota save(Cuota cuota) {
        log.debug("Request to save Cuota : {}", cuota);
        return cuotaRepository.save(cuota);
    }

    /**
     *  Get all the cuotas.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cuota> findAll() {
        log.debug("Request to get all Cuotas");
        return cuotaRepository.findAll();
    }

    /**
     *  Get one cuota by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Cuota findOne(Long id) {
        log.debug("Request to get Cuota : {}", id);
        return cuotaRepository.findOne(id);
    }

    /**
     *  Delete the  cuota by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cuota : {}", id);
        cuotaRepository.delete(id);
    }
}
