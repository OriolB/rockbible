package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VotarFavoritoC;

import com.mycompany.myapp.repository.VotarFavoritoCRepository;
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
 * REST controller for managing VotarFavoritoC.
 */
@RestController
@RequestMapping("/api")
public class VotarFavoritoCResource {

    private final Logger log = LoggerFactory.getLogger(VotarFavoritoCResource.class);

    private static final String ENTITY_NAME = "votarFavoritoC";

    private final VotarFavoritoCRepository votarFavoritoCRepository;

    public VotarFavoritoCResource(VotarFavoritoCRepository votarFavoritoCRepository) {
        this.votarFavoritoCRepository = votarFavoritoCRepository;
    }

    /**
     * POST  /votar-favorito-cs : Create a new votarFavoritoC.
     *
     * @param votarFavoritoC the votarFavoritoC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new votarFavoritoC, or with status 400 (Bad Request) if the votarFavoritoC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/votar-favorito-cs")
    @Timed
    public ResponseEntity<VotarFavoritoC> createVotarFavoritoC(@RequestBody VotarFavoritoC votarFavoritoC) throws URISyntaxException {
        log.debug("REST request to save VotarFavoritoC : {}", votarFavoritoC);
        if (votarFavoritoC.getId() != null) {
            throw new BadRequestAlertException("A new votarFavoritoC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VotarFavoritoC result = votarFavoritoCRepository.save(votarFavoritoC);
        return ResponseEntity.created(new URI("/api/votar-favorito-cs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /votar-favorito-cs : Updates an existing votarFavoritoC.
     *
     * @param votarFavoritoC the votarFavoritoC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated votarFavoritoC,
     * or with status 400 (Bad Request) if the votarFavoritoC is not valid,
     * or with status 500 (Internal Server Error) if the votarFavoritoC couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/votar-favorito-cs")
    @Timed
    public ResponseEntity<VotarFavoritoC> updateVotarFavoritoC(@RequestBody VotarFavoritoC votarFavoritoC) throws URISyntaxException {
        log.debug("REST request to update VotarFavoritoC : {}", votarFavoritoC);
        if (votarFavoritoC.getId() == null) {
            return createVotarFavoritoC(votarFavoritoC);
        }
        VotarFavoritoC result = votarFavoritoCRepository.save(votarFavoritoC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votarFavoritoC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /votar-favorito-cs : get all the votarFavoritoCS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of votarFavoritoCS in body
     */
    @GetMapping("/votar-favorito-cs")
    @Timed
    public List<VotarFavoritoC> getAllVotarFavoritoCS() {
        log.debug("REST request to get all VotarFavoritoCS");
        return votarFavoritoCRepository.findAll();
        }

    /**
     * GET  /votar-favorito-cs/:id : get the "id" votarFavoritoC.
     *
     * @param id the id of the votarFavoritoC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the votarFavoritoC, or with status 404 (Not Found)
     */
    @GetMapping("/votar-favorito-cs/{id}")
    @Timed
    public ResponseEntity<VotarFavoritoC> getVotarFavoritoC(@PathVariable Long id) {
        log.debug("REST request to get VotarFavoritoC : {}", id);
        VotarFavoritoC votarFavoritoC = votarFavoritoCRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votarFavoritoC));
    }

    /**
     * DELETE  /votar-favorito-cs/:id : delete the "id" votarFavoritoC.
     *
     * @param id the id of the votarFavoritoC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/votar-favorito-cs/{id}")
    @Timed
    public ResponseEntity<Void> deleteVotarFavoritoC(@PathVariable Long id) {
        log.debug("REST request to delete VotarFavoritoC : {}", id);
        votarFavoritoCRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
