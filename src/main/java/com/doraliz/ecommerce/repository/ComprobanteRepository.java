package com.doraliz.ecommerce.repository;

import com.doraliz.ecommerce.domain.Comprobante;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Comprobante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {

}
