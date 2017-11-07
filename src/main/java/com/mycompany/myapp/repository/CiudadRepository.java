package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ciudad;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ciudad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

}
