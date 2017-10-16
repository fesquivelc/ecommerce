package com.doraliz.ecommerce.repository;

import com.doraliz.ecommerce.domain.Equivalencia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Equivalencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquivalenciaRepository extends JpaRepository<Equivalencia, Long> {

}
