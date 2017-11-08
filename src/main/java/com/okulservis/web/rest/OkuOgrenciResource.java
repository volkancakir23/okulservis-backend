package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuOgrenci;
import com.okulservis.service.OkuOgrenciService;
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
 * REST controller for managing OkuOgrenci.
 */
@RestController
@RequestMapping("/api")
public class OkuOgrenciResource {

    private final Logger log = LoggerFactory.getLogger(OkuOgrenciResource.class);

    private static final String ENTITY_NAME = "okuOgrenci";

    private final OkuOgrenciService okuOgrenciService;

    public OkuOgrenciResource(OkuOgrenciService okuOgrenciService) {
        this.okuOgrenciService = okuOgrenciService;
    }

    /**
     * POST  /oku-ogrencis : Create a new okuOgrenci.
     *
     * @param okuOgrenci the okuOgrenci to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuOgrenci, or with status 400 (Bad Request) if the okuOgrenci has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-ogrencis")
    @Timed
    public ResponseEntity<OkuOgrenci> createOkuOgrenci(@RequestBody OkuOgrenci okuOgrenci) throws URISyntaxException {
        log.debug("REST request to save OkuOgrenci : {}", okuOgrenci);
        if (okuOgrenci.getId() != null) {
            throw new BadRequestAlertException("A new okuOgrenci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuOgrenci result = okuOgrenciService.save(okuOgrenci);
        return ResponseEntity.created(new URI("/api/oku-ogrencis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-ogrencis : Updates an existing okuOgrenci.
     *
     * @param okuOgrenci the okuOgrenci to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuOgrenci,
     * or with status 400 (Bad Request) if the okuOgrenci is not valid,
     * or with status 500 (Internal Server Error) if the okuOgrenci couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-ogrencis")
    @Timed
    public ResponseEntity<OkuOgrenci> updateOkuOgrenci(@RequestBody OkuOgrenci okuOgrenci) throws URISyntaxException {
        log.debug("REST request to update OkuOgrenci : {}", okuOgrenci);
        if (okuOgrenci.getId() == null) {
            return createOkuOgrenci(okuOgrenci);
        }
        OkuOgrenci result = okuOgrenciService.save(okuOgrenci);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuOgrenci.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-ogrencis : get all the okuOgrencis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuOgrencis in body
     */
    @GetMapping("/oku-ogrencis")
    @Timed
    public ResponseEntity<List<OkuOgrenci>> getAllOkuOgrencis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuOgrencis");
        Page<OkuOgrenci> page = okuOgrenciService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-ogrencis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-ogrencis/:id : get the "id" okuOgrenci.
     *
     * @param id the id of the okuOgrenci to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuOgrenci, or with status 404 (Not Found)
     */
    @GetMapping("/oku-ogrencis/{id}")
    @Timed
    public ResponseEntity<OkuOgrenci> getOkuOgrenci(@PathVariable Long id) {
        log.debug("REST request to get OkuOgrenci : {}", id);
        OkuOgrenci okuOgrenci = okuOgrenciService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuOgrenci));
    }

    /**
     * DELETE  /oku-ogrencis/:id : delete the "id" okuOgrenci.
     *
     * @param id the id of the okuOgrenci to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-ogrencis/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuOgrenci(@PathVariable Long id) {
        log.debug("REST request to delete OkuOgrenci : {}", id);
        okuOgrenciService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-ogrencis?query=:query : search for the okuOgrenci corresponding
     * to the query.
     *
     * @param query the query of the okuOgrenci search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-ogrencis")
    @Timed
    public ResponseEntity<List<OkuOgrenci>> searchOkuOgrencis(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuOgrencis for query {}", query);
        Page<OkuOgrenci> page = okuOgrenciService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-ogrencis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
