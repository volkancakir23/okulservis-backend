package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuSehir;
import com.okulservis.service.OkuSehirService;
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
 * REST controller for managing OkuSehir.
 */
@RestController
@RequestMapping("/api")
public class OkuSehirResource {

    private final Logger log = LoggerFactory.getLogger(OkuSehirResource.class);

    private static final String ENTITY_NAME = "okuSehir";

    private final OkuSehirService okuSehirService;

    public OkuSehirResource(OkuSehirService okuSehirService) {
        this.okuSehirService = okuSehirService;
    }

    /**
     * POST  /oku-sehirs : Create a new okuSehir.
     *
     * @param okuSehir the okuSehir to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuSehir, or with status 400 (Bad Request) if the okuSehir has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-sehirs")
    @Timed
    public ResponseEntity<OkuSehir> createOkuSehir(@RequestBody OkuSehir okuSehir) throws URISyntaxException {
        log.debug("REST request to save OkuSehir : {}", okuSehir);
        if (okuSehir.getId() != null) {
            throw new BadRequestAlertException("A new okuSehir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuSehir result = okuSehirService.save(okuSehir);
        return ResponseEntity.created(new URI("/api/oku-sehirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-sehirs : Updates an existing okuSehir.
     *
     * @param okuSehir the okuSehir to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuSehir,
     * or with status 400 (Bad Request) if the okuSehir is not valid,
     * or with status 500 (Internal Server Error) if the okuSehir couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-sehirs")
    @Timed
    public ResponseEntity<OkuSehir> updateOkuSehir(@RequestBody OkuSehir okuSehir) throws URISyntaxException {
        log.debug("REST request to update OkuSehir : {}", okuSehir);
        if (okuSehir.getId() == null) {
            return createOkuSehir(okuSehir);
        }
        OkuSehir result = okuSehirService.save(okuSehir);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuSehir.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-sehirs : get all the okuSehirs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuSehirs in body
     */
    @GetMapping("/oku-sehirs")
    @Timed
    public ResponseEntity<List<OkuSehir>> getAllOkuSehirs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuSehirs");
        Page<OkuSehir> page = okuSehirService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-sehirs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-sehirs/:id : get the "id" okuSehir.
     *
     * @param id the id of the okuSehir to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuSehir, or with status 404 (Not Found)
     */
    @GetMapping("/oku-sehirs/{id}")
    @Timed
    public ResponseEntity<OkuSehir> getOkuSehir(@PathVariable Long id) {
        log.debug("REST request to get OkuSehir : {}", id);
        OkuSehir okuSehir = okuSehirService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuSehir));
    }

    /**
     * DELETE  /oku-sehirs/:id : delete the "id" okuSehir.
     *
     * @param id the id of the okuSehir to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-sehirs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuSehir(@PathVariable Long id) {
        log.debug("REST request to delete OkuSehir : {}", id);
        okuSehirService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-sehirs?query=:query : search for the okuSehir corresponding
     * to the query.
     *
     * @param query the query of the okuSehir search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-sehirs")
    @Timed
    public ResponseEntity<List<OkuSehir>> searchOkuSehirs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuSehirs for query {}", query);
        Page<OkuSehir> page = okuSehirService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-sehirs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
