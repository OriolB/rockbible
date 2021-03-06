package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Banda;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Banda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BandaRepository extends JpaRepository<Banda, Long> {
    @Query("select distinct banda from Banda banda left join fetch banda.generos")
    List<Banda> findAllWithEagerRelationships();

    @Query("select banda from Banda banda left join fetch banda.generos where banda.id =:id")
    Banda findOneWithEagerRelationships(@Param("id") Long id);

    List<Banda> findByNombreContaining(String nombre);

}
