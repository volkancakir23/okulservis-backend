package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuSefer;
import com.okulservis.service.OkuSeferService;
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
 * REST controller for managing OkuSefer.
 */
@RestController
@RequestMapping("/api")
public class OkuSeferResource {

    private final Logger log = LoggerFactory.getLogger(OkuSeferResource.class);

    private static final String ENTITY_NAME = "okuSefer";

    private final OkuSeferService okuSeferService;

    public OkuSeferResource(OkuSeferService okuSeferService) {
        this.okuSeferService = okuSeferService;
    }

    /**
     * POST  /oku-sefers : Create a new okuSefer.
     *
     * @param okuSefer the okuSefer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuSefer, or with status 400 (Bad Request) if the okuSefer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-sefers")
    @Timed
    public ResponseEntity<OkuSefer> createOkuSefer(@RequestBody OkuSefer okuSefer) throws URISyntaxException {
        log.debug("REST request to save OkuSefer : {}", okuSefer);
        if (okuSefer.getId() != null) {
            throw new BadRequestAlertException("A new okuSefer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuSefer result = okuSeferService.save(okuSefer);
        return ResponseEntity.created(new URI("/api/oku-sefers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-sefers : Updates an existing okuSefer.
     *
     * @param okuSefer the okuSefer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuSefer,
     * or with status 400 (Bad Request) if the okuSefer is not valid,
     * or with status 500 (Internal Server Error) if the okuSefer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-sefers")
    @Timed
    public ResponseEntity<OkuSefer> updateOkuSefer(@RequestBody OkuSefer okuSefer) throws URISyntaxException {
        log.debug("REST request to update OkuSefer : {}", okuSefer);
        if (okuSefer.getId() == null) {
            return createOkuSefer(okuSefer);
        }
        OkuSefer result = okuSeferService.save(okuSefer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuSefer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-sefers : get all the okuSefers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuSefers in body
     */
    @GetMapping("/oku-sefers")
    @Timed
    public ResponseEntity<List<OkuSefer>> getAllOkuSefers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuSefers");
        Page<OkuSefer> page = okuSeferService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-sefers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-sefers/:id : get the "id" okuSefer.
     *
     * @param id the id of the okuSefer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuSefer, or with status 404 (Not Found)
     */
    @GetMapping("/oku-sefers/{id}")
    @Timed
    public ResponseEntity<OkuSefer> getOkuSefer(@PathVariable Long id) {
        log.debug("REST request to get OkuSefer : {}", id);
        OkuSefer okuSefer = okuSeferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuSefer));
    }

    /**
     * DELETE  /oku-sefers/:id : delete the "id" okuSefer.
     *
     * @param id the id of the okuSefer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-sefers/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuSefer(@PathVariable Long id) {
        log.debug("REST request to delete OkuSefer : {}", id);
        okuSeferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-sefers?query=:query : search for the okuSefer corresponding
     * to the query.
     *
     * @param query the query of the okuSefer search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-sefers")
    @Timed
    public ResponseEntity<List<OkuSefer>> searchOkuSefers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuSefers for query {}", query);
        Page<OkuSefer> page = okuSeferService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-sefers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
