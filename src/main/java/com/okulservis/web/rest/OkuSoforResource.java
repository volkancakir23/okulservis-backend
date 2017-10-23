package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuSofor;
import com.okulservis.service.OkuSoforService;
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
 * REST controller for managing OkuSofor.
 */
@RestController
@RequestMapping("/api")
public class OkuSoforResource {

    private final Logger log = LoggerFactory.getLogger(OkuSoforResource.class);

    private static final String ENTITY_NAME = "okuSofor";

    private final OkuSoforService okuSoforService;

    public OkuSoforResource(OkuSoforService okuSoforService) {
        this.okuSoforService = okuSoforService;
    }

    /**
     * POST  /oku-sofors : Create a new okuSofor.
     *
     * @param okuSofor the okuSofor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuSofor, or with status 400 (Bad Request) if the okuSofor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-sofors")
    @Timed
    public ResponseEntity<OkuSofor> createOkuSofor(@RequestBody OkuSofor okuSofor) throws URISyntaxException {
        log.debug("REST request to save OkuSofor : {}", okuSofor);
        if (okuSofor.getId() != null) {
            throw new BadRequestAlertException("A new okuSofor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuSofor result = okuSoforService.save(okuSofor);
        return ResponseEntity.created(new URI("/api/oku-sofors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-sofors : Updates an existing okuSofor.
     *
     * @param okuSofor the okuSofor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuSofor,
     * or with status 400 (Bad Request) if the okuSofor is not valid,
     * or with status 500 (Internal Server Error) if the okuSofor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-sofors")
    @Timed
    public ResponseEntity<OkuSofor> updateOkuSofor(@RequestBody OkuSofor okuSofor) throws URISyntaxException {
        log.debug("REST request to update OkuSofor : {}", okuSofor);
        if (okuSofor.getId() == null) {
            return createOkuSofor(okuSofor);
        }
        OkuSofor result = okuSoforService.save(okuSofor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuSofor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-sofors : get all the okuSofors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuSofors in body
     */
    @GetMapping("/oku-sofors")
    @Timed
    public ResponseEntity<List<OkuSofor>> getAllOkuSofors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuSofors");
        Page<OkuSofor> page = okuSoforService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-sofors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-sofors/:id : get the "id" okuSofor.
     *
     * @param id the id of the okuSofor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuSofor, or with status 404 (Not Found)
     */
    @GetMapping("/oku-sofors/{id}")
    @Timed
    public ResponseEntity<OkuSofor> getOkuSofor(@PathVariable Long id) {
        log.debug("REST request to get OkuSofor : {}", id);
        OkuSofor okuSofor = okuSoforService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuSofor));
    }

    /**
     * DELETE  /oku-sofors/:id : delete the "id" okuSofor.
     *
     * @param id the id of the okuSofor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-sofors/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuSofor(@PathVariable Long id) {
        log.debug("REST request to delete OkuSofor : {}", id);
        okuSoforService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-sofors?query=:query : search for the okuSofor corresponding
     * to the query.
     *
     * @param query the query of the okuSofor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-sofors")
    @Timed
    public ResponseEntity<List<OkuSofor>> searchOkuSofors(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuSofors for query {}", query);
        Page<OkuSofor> page = okuSoforService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-sofors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
