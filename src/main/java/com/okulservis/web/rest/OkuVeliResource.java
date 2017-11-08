package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuVeli;
import com.okulservis.service.OkuVeliService;
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
 * REST controller for managing OkuVeli.
 */
@RestController
@RequestMapping("/api")
public class OkuVeliResource {

    private final Logger log = LoggerFactory.getLogger(OkuVeliResource.class);

    private static final String ENTITY_NAME = "okuVeli";

    private final OkuVeliService okuVeliService;

    public OkuVeliResource(OkuVeliService okuVeliService) {
        this.okuVeliService = okuVeliService;
    }

    /**
     * POST  /oku-velis : Create a new okuVeli.
     *
     * @param okuVeli the okuVeli to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuVeli, or with status 400 (Bad Request) if the okuVeli has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-velis")
    @Timed
    public ResponseEntity<OkuVeli> createOkuVeli(@RequestBody OkuVeli okuVeli) throws URISyntaxException {
        log.debug("REST request to save OkuVeli : {}", okuVeli);
        if (okuVeli.getId() != null) {
            throw new BadRequestAlertException("A new okuVeli cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuVeli result = okuVeliService.save(okuVeli);
        return ResponseEntity.created(new URI("/api/oku-velis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-velis : Updates an existing okuVeli.
     *
     * @param okuVeli the okuVeli to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuVeli,
     * or with status 400 (Bad Request) if the okuVeli is not valid,
     * or with status 500 (Internal Server Error) if the okuVeli couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-velis")
    @Timed
    public ResponseEntity<OkuVeli> updateOkuVeli(@RequestBody OkuVeli okuVeli) throws URISyntaxException {
        log.debug("REST request to update OkuVeli : {}", okuVeli);
        if (okuVeli.getId() == null) {
            return createOkuVeli(okuVeli);
        }
        OkuVeli result = okuVeliService.save(okuVeli);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuVeli.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-velis : get all the okuVelis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuVelis in body
     */
    @GetMapping("/oku-velis")
    @Timed
    public ResponseEntity<List<OkuVeli>> getAllOkuVelis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuVelis");
        Page<OkuVeli> page = okuVeliService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-velis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-velis/:id : get the "id" okuVeli.
     *
     * @param id the id of the okuVeli to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuVeli, or with status 404 (Not Found)
     */
    @GetMapping("/oku-velis/{id}")
    @Timed
    public ResponseEntity<OkuVeli> getOkuVeli(@PathVariable Long id) {
        log.debug("REST request to get OkuVeli : {}", id);
        OkuVeli okuVeli = okuVeliService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuVeli));
    }

    /**
     * DELETE  /oku-velis/:id : delete the "id" okuVeli.
     *
     * @param id the id of the okuVeli to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-velis/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuVeli(@PathVariable Long id) {
        log.debug("REST request to delete OkuVeli : {}", id);
        okuVeliService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-velis?query=:query : search for the okuVeli corresponding
     * to the query.
     *
     * @param query the query of the okuVeli search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-velis")
    @Timed
    public ResponseEntity<List<OkuVeli>> searchOkuVelis(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuVelis for query {}", query);
        Page<OkuVeli> page = okuVeliService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-velis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
