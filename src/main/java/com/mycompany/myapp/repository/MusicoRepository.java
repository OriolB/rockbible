package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Musico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Musico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MusicoRepository extends JpaRepository<Musico, Long> {

}
