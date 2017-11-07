package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Musico;

import com.mycompany.myapp.repository.MusicoRepository;
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
 * REST controller for managing Musico.
 */
@RestController
@RequestMapping("/api")
public class MusicoResource {

    private final Logger log = LoggerFactory.getLogger(MusicoResource.class);

    private static final String ENTITY_NAME = "musico";

    private final MusicoRepository musicoRepository;

    public MusicoResource(MusicoRepository musicoRepository) {
        this.musicoRepository = musicoRepository;
    }

    /**
     * POST  /musicos : Create a new musico.
     *
     * @param musico the musico to create
     * @return the ResponseEntity with status 201 (Created) and with body the new musico, or with status 400 (Bad Request) if the musico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/musicos")
    @Timed
    public ResponseEntity<Musico> createMusico(@RequestBody Musico musico) throws URISyntaxException {
        log.debug("REST request to save Musico : {}", musico);
        if (musico.getId() != null) {
            throw new BadRequestAlertException("A new musico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Musico result = musicoRepository.save(musico);
        return ResponseEntity.created(new URI("/api/musicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /musicos : Updates an existing musico.
     *
     * @param musico the musico to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated musico,
     * or with status 400 (Bad Request) if the musico is not valid,
     * or with status 500 (Internal Server Error) if the musico couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/musicos")
    @Timed
    public ResponseEntity<Musico> updateMusico(@RequestBody Musico musico) throws URISyntaxException {
        log.debug("REST request to update Musico : {}", musico);
        if (musico.getId() == null) {
            return createMusico(musico);
        }
        Musico result = musicoRepository.save(musico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, musico.getId().toString()))
            .body(result);
    }

    /**
     * GET  /musicos : get all the musicos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of musicos in body
     */
    @GetMapping("/musicos")
    @Timed
    public List<Musico> getAllMusicos() {
        log.debug("REST request to get all Musicos");
        return musicoRepository.findAll();
        }

    /**
     * GET  /musicos/:id : get the "id" musico.
     *
     * @param id the id of the musico to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the musico, or with status 404 (Not Found)
     */
    @GetMapping("/musicos/{id}")
    @Timed
    public ResponseEntity<Musico> getMusico(@PathVariable Long id) {
        log.debug("REST request to get Musico : {}", id);
        Musico musico = musicoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(musico));
    }

    /**
     * DELETE  /musicos/:id : delete the "id" musico.
     *
     * @param id the id of the musico to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/musicos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMusico(@PathVariable Long id) {
        log.debug("REST request to delete Musico : {}", id);
        musicoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
