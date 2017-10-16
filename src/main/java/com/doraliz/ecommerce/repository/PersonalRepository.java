package com.doraliz.ecommerce.repository;

import com.doraliz.ecommerce.domain.Personal;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Personal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {

}
