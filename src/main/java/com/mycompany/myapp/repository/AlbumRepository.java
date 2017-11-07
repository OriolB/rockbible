package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Album;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Album entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("select distinct album from Album album left join fetch album.generos")
    List<Album> findAllWithEagerRelationships();

    @Query("select album from Album album left join fetch album.generos where album.id =:id")
    Album findOneWithEagerRelationships(@Param("id") Long id);

}
