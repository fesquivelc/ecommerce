package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.FlujoPedidoService;
import com.doraliz.ecommerce.domain.FlujoPedido;
import com.doraliz.ecommerce.repository.FlujoPedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing FlujoPedido.
 */
@Service
@Transactional
public class FlujoPedidoServiceImpl implements FlujoPedidoService{

    private final Logger log = LoggerFactory.getLogger(FlujoPedidoServiceImpl.class);

    private final FlujoPedidoRepository flujoPedidoRepository;

    public FlujoPedidoServiceImpl(FlujoPedidoRepository flujoPedidoRepository) {
        this.flujoPedidoRepository = flujoPedidoRepository;
    }

    /**
     * Save a flujoPedido.
     *
     * @param flujoPedido the entity to save
     * @return the persisted entity
     */
    @Override
    public FlujoPedido save(FlujoPedido flujoPedido) {
        log.debug("Request to save FlujoPedido : {}", flujoPedido);
        return flujoPedidoRepository.save(flujoPedido);
    }

    /**
     *  Get all the flujoPedidos.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FlujoPedido> findAll() {
        log.debug("Request to get all FlujoPedidos");
        return flujoPedidoRepository.findAll();
    }

    /**
     *  Get one flujoPedido by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FlujoPedido findOne(Long id) {
        log.debug("Request to get FlujoPedido : {}", id);
        return flujoPedidoRepository.findOne(id);
    }

    /**
     *  Delete the  flujoPedido by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FlujoPedido : {}", id);
        flujoPedidoRepository.delete(id);
    }
}
