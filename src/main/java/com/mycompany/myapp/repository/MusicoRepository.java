package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Musico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Musico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MusicoRepository extends JpaRepository<Musico, Long> {


    List<Musico> findByNombreContaining(String nombre);
}
