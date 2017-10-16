package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.DetallePedido;
import java.util.List;

/**
 * Service Interface for managing DetallePedido.
 */
public interface DetallePedidoService {

    /**
     * Save a detallePedido.
     *
     * @param detallePedido the entity to save
     * @return the persisted entity
     */
    DetallePedido save(DetallePedido detallePedido);

    /**
     *  Get all the detallePedidos.
     *
     *  @return the list of entities
     */
    List<DetallePedido> findAll();

    /**
     *  Get the "id" detallePedido.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DetallePedido findOne(Long id);

    /**
     *  Delete the "id" detallePedido.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
