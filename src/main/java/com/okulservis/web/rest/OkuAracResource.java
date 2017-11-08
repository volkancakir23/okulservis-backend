package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuArac;
import com.okulservis.service.OkuAracService;
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
 * REST controller for managing OkuArac.
 */
@RestController
@RequestMapping("/api")
public class OkuAracResource {

    private final Logger log = LoggerFactory.getLogger(OkuAracResource.class);

    private static final String ENTITY_NAME = "okuArac";

    private final OkuAracService okuAracService;

    public OkuAracResource(OkuAracService okuAracService) {
        this.okuAracService = okuAracService;
    }

    /**
     * POST  /oku-aracs : Create a new okuArac.
     *
     * @param okuArac the okuArac to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuArac, or with status 400 (Bad Request) if the okuArac has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-aracs")
    @Timed
    public ResponseEntity<OkuArac> createOkuArac(@RequestBody OkuArac okuArac) throws URISyntaxException {
        log.debug("REST request to save OkuArac : {}", okuArac);
        if (okuArac.getId() != null) {
            throw new BadRequestAlertException("A new okuArac cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuArac result = okuAracService.save(okuArac);
        return ResponseEntity.created(new URI("/api/oku-aracs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-aracs : Updates an existing okuArac.
     *
     * @param okuArac the okuArac to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuArac,
     * or with status 400 (Bad Request) if the okuArac is not valid,
     * or with status 500 (Internal Server Error) if the okuArac couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-aracs")
    @Timed
    public ResponseEntity<OkuArac> updateOkuArac(@RequestBody OkuArac okuArac) throws URISyntaxException {
        log.debug("REST request to update OkuArac : {}", okuArac);
        if (okuArac.getId() == null) {
            return createOkuArac(okuArac);
        }
        OkuArac result = okuAracService.save(okuArac);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuArac.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-aracs : get all the okuAracs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuAracs in body
     */
    @GetMapping("/oku-aracs")
    @Timed
    public ResponseEntity<List<OkuArac>> getAllOkuAracs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuAracs");
        Page<OkuArac> page = okuAracService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-aracs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-aracs/:id : get the "id" okuArac.
     *
     * @param id the id of the okuArac to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuArac, or with status 404 (Not Found)
     */
    @GetMapping("/oku-aracs/{id}")
    @Timed
    public ResponseEntity<OkuArac> getOkuArac(@PathVariable Long id) {
        log.debug("REST request to get OkuArac : {}", id);
        OkuArac okuArac = okuAracService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuArac));
    }

    /**
     * DELETE  /oku-aracs/:id : delete the "id" okuArac.
     *
     * @param id the id of the okuArac to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-aracs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuArac(@PathVariable Long id) {
        log.debug("REST request to delete OkuArac : {}", id);
        okuAracService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-aracs?query=:query : search for the okuArac corresponding
     * to the query.
     *
     * @param query the query of the okuArac search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-aracs")
    @Timed
    public ResponseEntity<List<OkuArac>> searchOkuAracs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuAracs for query {}", query);
        Page<OkuArac> page = okuAracService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-aracs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
