package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.DetallePedidoService;
import com.doraliz.ecommerce.domain.DetallePedido;
import com.doraliz.ecommerce.repository.DetallePedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing DetallePedido.
 */
@Service
@Transactional
public class DetallePedidoServiceImpl implements DetallePedidoService{

    private final Logger log = LoggerFactory.getLogger(DetallePedidoServiceImpl.class);

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    /**
     * Save a detallePedido.
     *
     * @param detallePedido the entity to save
     * @return the persisted entity
     */
    @Override
    public DetallePedido save(DetallePedido detallePedido) {
        log.debug("Request to save DetallePedido : {}", detallePedido);
        return detallePedidoRepository.save(detallePedido);
    }

    /**
     *  Get all the detallePedidos.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DetallePedido> findAll() {
        log.debug("Request to get all DetallePedidos");
        return detallePedidoRepository.findAll();
    }

    /**
     *  Get one detallePedido by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DetallePedido findOne(Long id) {
        log.debug("Request to get DetallePedido : {}", id);
        return detallePedidoRepository.findOne(id);
    }

    /**
     *  Delete the  detallePedido by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetallePedido : {}", id);
        detallePedidoRepository.delete(id);
    }
}
