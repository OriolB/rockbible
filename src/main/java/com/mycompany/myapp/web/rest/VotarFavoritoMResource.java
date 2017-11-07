package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VotarFavoritoM;

import com.mycompany.myapp.repository.VotarFavoritoMRepository;
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
 * REST controller for managing VotarFavoritoM.
 */
@RestController
@RequestMapping("/api")
public class VotarFavoritoMResource {

    private final Logger log = LoggerFactory.getLogger(VotarFavoritoMResource.class);

    private static final String ENTITY_NAME = "votarFavoritoM";

    private final VotarFavoritoMRepository votarFavoritoMRepository;

    public VotarFavoritoMResource(VotarFavoritoMRepository votarFavoritoMRepository) {
        this.votarFavoritoMRepository = votarFavoritoMRepository;
    }

    /**
     * POST  /votar-favorito-ms : Create a new votarFavoritoM.
     *
     * @param votarFavoritoM the votarFavoritoM to create
     * @return the ResponseEntity with status 201 (Created) and with body the new votarFavoritoM, or with status 400 (Bad Request) if the votarFavoritoM has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/votar-favorito-ms")
    @Timed
    public ResponseEntity<VotarFavoritoM> createVotarFavoritoM(@RequestBody VotarFavoritoM votarFavoritoM) throws URISyntaxException {
        log.debug("REST request to save VotarFavoritoM : {}", votarFavoritoM);
        if (votarFavoritoM.getId() != null) {
            throw new BadRequestAlertException("A new votarFavoritoM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VotarFavoritoM result = votarFavoritoMRepository.save(votarFavoritoM);
        return ResponseEntity.created(new URI("/api/votar-favorito-ms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /votar-favorito-ms : Updates an existing votarFavoritoM.
     *
     * @param votarFavoritoM the votarFavoritoM to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated votarFavoritoM,
     * or with status 400 (Bad Request) if the votarFavoritoM is not valid,
     * or with status 500 (Internal Server Error) if the votarFavoritoM couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/votar-favorito-ms")
    @Timed
    public ResponseEntity<VotarFavoritoM> updateVotarFavoritoM(@RequestBody VotarFavoritoM votarFavoritoM) throws URISyntaxException {
        log.debug("REST request to update VotarFavoritoM : {}", votarFavoritoM);
        if (votarFavoritoM.getId() == null) {
            return createVotarFavoritoM(votarFavoritoM);
        }
        VotarFavoritoM result = votarFavoritoMRepository.save(votarFavoritoM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votarFavoritoM.getId().toString()))
            .body(result);
    }

    /**
     * GET  /votar-favorito-ms : get all the votarFavoritoMS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of votarFavoritoMS in body
     */
    @GetMapping("/votar-favorito-ms")
    @Timed
    public List<VotarFavoritoM> getAllVotarFavoritoMS() {
        log.debug("REST request to get all VotarFavoritoMS");
        return votarFavoritoMRepository.findAll();
        }

    /**
     * GET  /votar-favorito-ms/:id : get the "id" votarFavoritoM.
     *
     * @param id the id of the votarFavoritoM to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the votarFavoritoM, or with status 404 (Not Found)
     */
    @GetMapping("/votar-favorito-ms/{id}")
    @Timed
    public ResponseEntity<VotarFavoritoM> getVotarFavoritoM(@PathVariable Long id) {
        log.debug("REST request to get VotarFavoritoM : {}", id);
        VotarFavoritoM votarFavoritoM = votarFavoritoMRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votarFavoritoM));
    }

    /**
     * DELETE  /votar-favorito-ms/:id : delete the "id" votarFavoritoM.
     *
     * @param id the id of the votarFavoritoM to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/votar-favorito-ms/{id}")
    @Timed
    public ResponseEntity<Void> deleteVotarFavoritoM(@PathVariable Long id) {
        log.debug("REST request to delete VotarFavoritoM : {}", id);
        votarFavoritoMRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
