package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.InventarioService;
import com.doraliz.ecommerce.domain.Inventario;
import com.doraliz.ecommerce.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Inventario.
 */
@Service
@Transactional
public class InventarioServiceImpl implements InventarioService{

    private final Logger log = LoggerFactory.getLogger(InventarioServiceImpl.class);

    private final InventarioRepository inventarioRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    /**
     * Save a inventario.
     *
     * @param inventario the entity to save
     * @return the persisted entity
     */
    @Override
    public Inventario save(Inventario inventario) {
        log.debug("Request to save Inventario : {}", inventario);
        return inventarioRepository.save(inventario);
    }

    /**
     *  Get all the inventarios.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inventario> findAll() {
        log.debug("Request to get all Inventarios");
        return inventarioRepository.findAll();
    }

    /**
     *  Get one inventario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Inventario findOne(Long id) {
        log.debug("Request to get Inventario : {}", id);
        return inventarioRepository.findOne(id);
    }

    /**
     *  Delete the  inventario by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inventario : {}", id);
        inventarioRepository.delete(id);
    }
}
