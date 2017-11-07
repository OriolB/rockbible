package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cancion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Cancion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {
    @Query("select distinct cancion from Cancion cancion left join fetch cancion.albums")
    List<Cancion> findAllWithEagerRelationships();

    @Query("select cancion from Cancion cancion left join fetch cancion.albums where cancion.id =:id")
    Cancion findOneWithEagerRelationships(@Param("id") Long id);

}
