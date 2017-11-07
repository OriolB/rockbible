package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VotarFavoritoM;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VotarFavoritoM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VotarFavoritoMRepository extends JpaRepository<VotarFavoritoM, Long> {

}
