package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.ComprobanteService;
import com.doraliz.ecommerce.domain.Comprobante;
import com.doraliz.ecommerce.repository.ComprobanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Comprobante.
 */
@Service
@Transactional
public class ComprobanteServiceImpl implements ComprobanteService{

    private final Logger log = LoggerFactory.getLogger(ComprobanteServiceImpl.class);

    private final ComprobanteRepository comprobanteRepository;

    public ComprobanteServiceImpl(ComprobanteRepository comprobanteRepository) {
        this.comprobanteRepository = comprobanteRepository;
    }

    /**
     * Save a comprobante.
     *
     * @param comprobante the entity to save
     * @return the persisted entity
     */
    @Override
    public Comprobante save(Comprobante comprobante) {
        log.debug("Request to save Comprobante : {}", comprobante);
        return comprobanteRepository.save(comprobante);
    }

    /**
     *  Get all the comprobantes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> findAll(Pageable pageable) {
        log.debug("Request to get all Comprobantes");
        return comprobanteRepository.findAll(pageable);
    }

    /**
     *  Get one comprobante by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Comprobante findOne(Long id) {
        log.debug("Request to get Comprobante : {}", id);
        return comprobanteRepository.findOne(id);
    }

    /**
     *  Delete the  comprobante by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comprobante : {}", id);
        comprobanteRepository.delete(id);
    }
}
