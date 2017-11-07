package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VotarFavoritoB;

import com.mycompany.myapp.repository.VotarFavoritoBRepository;
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
 * REST controller for managing VotarFavoritoB.
 */
@RestController
@RequestMapping("/api")
public class VotarFavoritoBResource {

    private final Logger log = LoggerFactory.getLogger(VotarFavoritoBResource.class);

    private static final String ENTITY_NAME = "votarFavoritoB";

    private final VotarFavoritoBRepository votarFavoritoBRepository;

    public VotarFavoritoBResource(VotarFavoritoBRepository votarFavoritoBRepository) {
        this.votarFavoritoBRepository = votarFavoritoBRepository;
    }

    /**
     * POST  /votar-favorito-bs : Create a new votarFavoritoB.
     *
     * @param votarFavoritoB the votarFavoritoB to create
     * @return the ResponseEntity with status 201 (Created) and with body the new votarFavoritoB, or with status 400 (Bad Request) if the votarFavoritoB has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/votar-favorito-bs")
    @Timed
    public ResponseEntity<VotarFavoritoB> createVotarFavoritoB(@RequestBody VotarFavoritoB votarFavoritoB) throws URISyntaxException {
        log.debug("REST request to save VotarFavoritoB : {}", votarFavoritoB);
        if (votarFavoritoB.getId() != null) {
            throw new BadRequestAlertException("A new votarFavoritoB cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VotarFavoritoB result = votarFavoritoBRepository.save(votarFavoritoB);
        return ResponseEntity.created(new URI("/api/votar-favorito-bs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /votar-favorito-bs : Updates an existing votarFavoritoB.
     *
     * @param votarFavoritoB the votarFavoritoB to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated votarFavoritoB,
     * or with status 400 (Bad Request) if the votarFavoritoB is not valid,
     * or with status 500 (Internal Server Error) if the votarFavoritoB couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/votar-favorito-bs")
    @Timed
    public ResponseEntity<VotarFavoritoB> updateVotarFavoritoB(@RequestBody VotarFavoritoB votarFavoritoB) throws URISyntaxException {
        log.debug("REST request to update VotarFavoritoB : {}", votarFavoritoB);
        if (votarFavoritoB.getId() == null) {
            return createVotarFavoritoB(votarFavoritoB);
        }
        VotarFavoritoB result = votarFavoritoBRepository.save(votarFavoritoB);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votarFavoritoB.getId().toString()))
            .body(result);
    }

    /**
     * GET  /votar-favorito-bs : get all the votarFavoritoBS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of votarFavoritoBS in body
     */
    @GetMapping("/votar-favorito-bs")
    @Timed
    public List<VotarFavoritoB> getAllVotarFavoritoBS() {
        log.debug("REST request to get all VotarFavoritoBS");
        return votarFavoritoBRepository.findAll();
        }

    /**
     * GET  /votar-favorito-bs/:id : get the "id" votarFavoritoB.
     *
     * @param id the id of the votarFavoritoB to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the votarFavoritoB, or with status 404 (Not Found)
     */
    @GetMapping("/votar-favorito-bs/{id}")
    @Timed
    public ResponseEntity<VotarFavoritoB> getVotarFavoritoB(@PathVariable Long id) {
        log.debug("REST request to get VotarFavoritoB : {}", id);
        VotarFavoritoB votarFavoritoB = votarFavoritoBRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votarFavoritoB));
    }

    /**
     * DELETE  /votar-favorito-bs/:id : delete the "id" votarFavoritoB.
     *
     * @param id the id of the votarFavoritoB to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/votar-favorito-bs/{id}")
    @Timed
    public ResponseEntity<Void> deleteVotarFavoritoB(@PathVariable Long id) {
        log.debug("REST request to delete VotarFavoritoB : {}", id);
        votarFavoritoBRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
