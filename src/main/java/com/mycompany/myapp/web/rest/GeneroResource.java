package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Genero;

import com.mycompany.myapp.repository.GeneroRepository;
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
 * REST controller for managing Genero.
 */
@RestController
@RequestMapping("/api")
public class GeneroResource {

    private final Logger log = LoggerFactory.getLogger(GeneroResource.class);

    private static final String ENTITY_NAME = "genero";

    private final GeneroRepository generoRepository;

    public GeneroResource(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    /**
     * POST  /generos : Create a new genero.
     *
     * @param genero the genero to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genero, or with status 400 (Bad Request) if the genero has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/generos")
    @Timed
    public ResponseEntity<Genero> createGenero(@RequestBody Genero genero) throws URISyntaxException {
        log.debug("REST request to save Genero : {}", genero);
        if (genero.getId() != null) {
            throw new BadRequestAlertException("A new genero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Genero result = generoRepository.save(genero);
        return ResponseEntity.created(new URI("/api/generos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generos : Updates an existing genero.
     *
     * @param genero the genero to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genero,
     * or with status 400 (Bad Request) if the genero is not valid,
     * or with status 500 (Internal Server Error) if the genero couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/generos")
    @Timed
    public ResponseEntity<Genero> updateGenero(@RequestBody Genero genero) throws URISyntaxException {
        log.debug("REST request to update Genero : {}", genero);
        if (genero.getId() == null) {
            return createGenero(genero);
        }
        Genero result = generoRepository.save(genero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, genero.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generos : get all the generos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of generos in body
     */
    @GetMapping("/generos")
    @Timed
    public List<Genero> getAllGeneros() {
        log.debug("REST request to get all Generos");
        return generoRepository.findAll();
        }

    /**
     * GET  /generos/:id : get the "id" genero.
     *
     * @param id the id of the genero to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genero, or with status 404 (Not Found)
     */
    @GetMapping("/generos/{id}")
    @Timed
    public ResponseEntity<Genero> getGenero(@PathVariable Long id) {
        log.debug("REST request to get Genero : {}", id);
        Genero genero = generoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(genero));
    }

    /**
     * DELETE  /generos/:id : delete the "id" genero.
     *
     * @param id the id of the genero to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/generos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        log.debug("REST request to delete Genero : {}", id);
        generoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
