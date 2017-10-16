package com.doraliz.ecommerce.repository;

import com.doraliz.ecommerce.domain.Almacen;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Almacen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {

}
