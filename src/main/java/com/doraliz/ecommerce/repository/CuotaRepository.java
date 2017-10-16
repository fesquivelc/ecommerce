package com.doraliz.ecommerce.repository;

import com.doraliz.ecommerce.domain.Cuota;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cuota entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {

}
