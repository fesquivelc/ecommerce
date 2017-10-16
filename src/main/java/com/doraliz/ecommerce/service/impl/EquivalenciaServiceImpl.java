package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.EquivalenciaService;
import com.doraliz.ecommerce.domain.Equivalencia;
import com.doraliz.ecommerce.repository.EquivalenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Equivalencia.
 */
@Service
@Transactional
public class EquivalenciaServiceImpl implements EquivalenciaService{

    private final Logger log = LoggerFactory.getLogger(EquivalenciaServiceImpl.class);

    private final EquivalenciaRepository equivalenciaRepository;

    public EquivalenciaServiceImpl(EquivalenciaRepository equivalenciaRepository) {
        this.equivalenciaRepository = equivalenciaRepository;
    }

    /**
     * Save a equivalencia.
     *
     * @param equivalencia the entity to save
     * @return the persisted entity
     */
    @Override
    public Equivalencia save(Equivalencia equivalencia) {
        log.debug("Request to save Equivalencia : {}", equivalencia);
        return equivalenciaRepository.save(equivalencia);
    }

    /**
     *  Get all the equivalencias.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Equivalencia> findAll() {
        log.debug("Request to get all Equivalencias");
        return equivalenciaRepository.findAll();
    }

    /**
     *  Get one equivalencia by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Equivalencia findOne(Long id) {
        log.debug("Request to get Equivalencia : {}", id);
        return equivalenciaRepository.findOne(id);
    }

    /**
     *  Delete the  equivalencia by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equivalencia : {}", id);
        equivalenciaRepository.delete(id);
    }
}
