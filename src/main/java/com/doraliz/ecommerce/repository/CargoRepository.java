package com.doraliz.ecommerce.repository;

import com.doraliz.ecommerce.domain.Cargo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cargo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

}
