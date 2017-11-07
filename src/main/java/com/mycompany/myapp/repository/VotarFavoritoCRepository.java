package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VotarFavoritoC;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VotarFavoritoC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VotarFavoritoCRepository extends JpaRepository<VotarFavoritoC, Long> {

}
