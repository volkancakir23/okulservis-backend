package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuOkul;
import com.okulservis.service.OkuOkulService;
import com.okulservis.web.rest.errors.BadRequestAlertException;
import com.okulservis.web.rest.util.HeaderUtil;
import com.okulservis.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing OkuOkul.
 */
@RestController
@RequestMapping("/api")
public class OkuOkulResource {

    private final Logger log = LoggerFactory.getLogger(OkuOkulResource.class);

    private static final String ENTITY_NAME = "okuOkul";

    private final OkuOkulService okuOkulService;

    public OkuOkulResource(OkuOkulService okuOkulService) {
        this.okuOkulService = okuOkulService;
    }

    /**
     * POST  /oku-okuls : Create a new okuOkul.
     *
     * @param okuOkul the okuOkul to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuOkul, or with status 400 (Bad Request) if the okuOkul has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-okuls")
    @Timed
    public ResponseEntity<OkuOkul> createOkuOkul(@RequestBody OkuOkul okuOkul) throws URISyntaxException {
        log.debug("REST request to save OkuOkul : {}", okuOkul);
        if (okuOkul.getId() != null) {
            throw new BadRequestAlertException("A new okuOkul cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuOkul result = okuOkulService.save(okuOkul);
        return ResponseEntity.created(new URI("/api/oku-okuls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-okuls : Updates an existing okuOkul.
     *
     * @param okuOkul the okuOkul to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuOkul,
     * or with status 400 (Bad Request) if the okuOkul is not valid,
     * or with status 500 (Internal Server Error) if the okuOkul couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-okuls")
    @Timed
    public ResponseEntity<OkuOkul> updateOkuOkul(@RequestBody OkuOkul okuOkul) throws URISyntaxException {
        log.debug("REST request to update OkuOkul : {}", okuOkul);
        if (okuOkul.getId() == null) {
            return createOkuOkul(okuOkul);
        }
        OkuOkul result = okuOkulService.save(okuOkul);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuOkul.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-okuls : get all the okuOkuls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuOkuls in body
     */
    @GetMapping("/oku-okuls")
    @Timed
    public ResponseEntity<List<OkuOkul>> getAllOkuOkuls(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuOkuls");
        Page<OkuOkul> page = okuOkulService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-okuls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-okuls/:id : get the "id" okuOkul.
     *
     * @param id the id of the okuOkul to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuOkul, or with status 404 (Not Found)
     */
    @GetMapping("/oku-okuls/{id}")
    @Timed
    public ResponseEntity<OkuOkul> getOkuOkul(@PathVariable Long id) {
        log.debug("REST request to get OkuOkul : {}", id);
        OkuOkul okuOkul = okuOkulService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuOkul));
    }

    /**
     * DELETE  /oku-okuls/:id : delete the "id" okuOkul.
     *
     * @param id the id of the okuOkul to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-okuls/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuOkul(@PathVariable Long id) {
        log.debug("REST request to delete OkuOkul : {}", id);
        okuOkulService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-okuls?query=:query : search for the okuOkul corresponding
     * to the query.
     *
     * @param query the query of the okuOkul search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-okuls")
    @Timed
    public ResponseEntity<List<OkuOkul>> searchOkuOkuls(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuOkuls for query {}", query);
        Page<OkuOkul> page = okuOkulService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-okuls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
