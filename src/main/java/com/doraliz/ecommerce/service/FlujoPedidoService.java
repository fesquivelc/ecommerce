package com.doraliz.ecommerce.service;

import com.doraliz.ecommerce.domain.FlujoPedido;
import java.util.List;

/**
 * Service Interface for managing FlujoPedido.
 */
public interface FlujoPedidoService {

    /**
     * Save a flujoPedido.
     *
     * @param flujoPedido the entity to save
     * @return the persisted entity
     */
    FlujoPedido save(FlujoPedido flujoPedido);

    /**
     *  Get all the flujoPedidos.
     *
     *  @return the list of entities
     */
    List<FlujoPedido> findAll();

    /**
     *  Get the "id" flujoPedido.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FlujoPedido findOne(Long id);

    /**
     *  Delete the "id" flujoPedido.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
