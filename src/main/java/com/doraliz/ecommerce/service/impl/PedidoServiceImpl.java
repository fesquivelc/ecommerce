package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.PedidoService;
import com.doraliz.ecommerce.domain.Pedido;
import com.doraliz.ecommerce.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Pedido.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService{

    private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

    private final PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Save a pedido.
     *
     * @param pedido the entity to save
     * @return the persisted entity
     */
    @Override
    public Pedido save(Pedido pedido) {
        log.debug("Request to save Pedido : {}", pedido);
        return pedidoRepository.save(pedido);
    }

    /**
     *  Get all the pedidos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pedido> findAll(Pageable pageable) {
        log.debug("Request to get all Pedidos");
        return pedidoRepository.findAll(pageable);
    }

    /**
     *  Get one pedido by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Pedido findOne(Long id) {
        log.debug("Request to get Pedido : {}", id);
        return pedidoRepository.findOne(id);
    }

    /**
     *  Delete the  pedido by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pedido : {}", id);
        pedidoRepository.delete(id);
    }
}
