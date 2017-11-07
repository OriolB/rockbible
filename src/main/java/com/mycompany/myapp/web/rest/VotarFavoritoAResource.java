package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VotarFavoritoA;

import com.mycompany.myapp.repository.VotarFavoritoARepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VotarFavoritoA.
 */
@RestController
@RequestMapping("/api")
public class VotarFavoritoAResource {

    private final Logger log = LoggerFactory.getLogger(VotarFavoritoAResource.class);

    private static final String ENTITY_NAME = "votarFavoritoA";

    private final VotarFavoritoARepository votarFavoritoARepository;

    public VotarFavoritoAResource(VotarFavoritoARepository votarFavoritoARepository) {
        this.votarFavoritoARepository = votarFavoritoARepository;
    }

    /**
     * POST  /votar-favorito-as : Create a new votarFavoritoA.
     *
     * @param votarFavoritoA the votarFavoritoA to create
     * @return the ResponseEntity with status 201 (Created) and with body the new votarFavoritoA, or with status 400 (Bad Request) if the votarFavoritoA has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/votar-favorito-as")
    @Timed
    public ResponseEntity<VotarFavoritoA> createVotarFavoritoA(@RequestBody VotarFavoritoA votarFavoritoA) throws URISyntaxException {
        log.debug("REST request to save VotarFavoritoA : {}", votarFavoritoA);
        if (votarFavoritoA.getId() != null) {
            throw new BadRequestAlertException("A new votarFavoritoA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VotarFavoritoA result = votarFavoritoARepository.save(votarFavoritoA);
        return ResponseEntity.created(new URI("/api/votar-favorito-as/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /votar-favorito-as : Updates an existing votarFavoritoA.
     *
     * @param votarFavoritoA the votarFavoritoA to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated votarFavoritoA,
     * or with status 400 (Bad Request) if the votarFavoritoA is not valid,
     * or with status 500 (Internal Server Error) if the votarFavoritoA couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/votar-favorito-as")
    @Timed
    public ResponseEntity<VotarFavoritoA> updateVotarFavoritoA(@RequestBody VotarFavoritoA votarFavoritoA) throws URISyntaxException {
        log.debug("REST request to update VotarFavoritoA : {}", votarFavoritoA);
        if (votarFavoritoA.getId() == null) {
            return createVotarFavoritoA(votarFavoritoA);
        }
        VotarFavoritoA result = votarFavoritoARepository.save(votarFavoritoA);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votarFavoritoA.getId().toString()))
            .body(result);
    }

    /**
     * GET  /votar-favorito-as : get all the votarFavoritoAS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of votarFavoritoAS in body
     */
    @GetMapping("/votar-favorito-as")
    @Timed
    public List<VotarFavoritoA> getAllVotarFavoritoAS() {
        log.debug("REST request to get all VotarFavoritoAS");
        return votarFavoritoARepository.findAll();
        }

    /**
     * GET  /votar-favorito-as/:id : get the "id" votarFavoritoA.
     *
     * @param id the id of the votarFavoritoA to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the votarFavoritoA, or with status 404 (Not Found)
     */
    @GetMapping("/votar-favorito-as/{id}")
    @Timed
    public ResponseEntity<VotarFavoritoA> getVotarFavoritoA(@PathVariable Long id) {
        log.debug("REST request to get VotarFavoritoA : {}", id);
        VotarFavoritoA votarFavoritoA = votarFavoritoARepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votarFavoritoA));
    }

    /**
     * DELETE  /votar-favorito-as/:id : delete the "id" votarFavoritoA.
     *
     * @param id the id of the votarFavoritoA to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/votar-favorito-as/{id}")
    @Timed
    public ResponseEntity<Void> deleteVotarFavoritoA(@PathVariable Long id) {
        log.debug("REST request to delete VotarFavoritoA : {}", id);
        votarFavoritoARepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
