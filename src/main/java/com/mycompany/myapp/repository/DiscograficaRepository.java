package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Discografica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Discografica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscograficaRepository extends JpaRepository<Discografica, Long> {
    @Query("select distinct discografica from Discografica discografica left join fetch discografica.bandas")
    List<Discografica> findAllWithEagerRelationships();

    @Query("select discografica from Discografica discografica left join fetch discografica.bandas where discografica.id =:id")
    Discografica findOneWithEagerRelationships(@Param("id") Long id);

}
