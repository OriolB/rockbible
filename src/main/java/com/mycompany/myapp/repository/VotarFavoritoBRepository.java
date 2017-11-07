package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VotarFavoritoB;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VotarFavoritoB entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VotarFavoritoBRepository extends JpaRepository<VotarFavoritoB, Long> {

}
