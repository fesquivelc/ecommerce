package com.doraliz.ecommerce.repository;

import com.doraliz.ecommerce.domain.Ubigeo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ubigeo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UbigeoRepository extends JpaRepository<Ubigeo, Long> {

}
