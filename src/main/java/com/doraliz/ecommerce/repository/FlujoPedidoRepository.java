package com.doraliz.ecommerce.repository;

import com.doraliz.ecommerce.domain.FlujoPedido;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FlujoPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlujoPedidoRepository extends JpaRepository<FlujoPedido, Long> {

}
