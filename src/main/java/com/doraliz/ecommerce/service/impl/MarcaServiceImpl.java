package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.MarcaService;
import com.doraliz.ecommerce.domain.Marca;
import com.doraliz.ecommerce.repository.MarcaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Marca.
 */
@Service
@Transactional
public class MarcaServiceImpl implements MarcaService{

    private final Logger log = LoggerFactory.getLogger(MarcaServiceImpl.class);

    private final MarcaRepository marcaRepository;

    public MarcaServiceImpl(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    /**
     * Save a marca.
     *
     * @param marca the entity to save
     * @return the persisted entity
     */
    @Override
    public Marca save(Marca marca) {
        log.debug("Request to save Marca : {}", marca);
        return marcaRepository.save(marca);
    }

    /**
     *  Get all the marcas.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Marca> findAll() {
        log.debug("Request to get all Marcas");
        return marcaRepository.findAll();
    }

    /**
     *  Get one marca by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Marca findOne(Long id) {
        log.debug("Request to get Marca : {}", id);
        return marcaRepository.findOne(id);
    }

    /**
     *  Delete the  marca by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Marca : {}", id);
        marcaRepository.delete(id);
    }
}
