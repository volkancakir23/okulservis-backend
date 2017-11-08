package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuGuzergah;
import com.okulservis.service.OkuGuzergahService;
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
 * REST controller for managing OkuGuzergah.
 */
@RestController
@RequestMapping("/api")
public class OkuGuzergahResource {

    private final Logger log = LoggerFactory.getLogger(OkuGuzergahResource.class);

    private static final String ENTITY_NAME = "okuGuzergah";

    private final OkuGuzergahService okuGuzergahService;

    public OkuGuzergahResource(OkuGuzergahService okuGuzergahService) {
        this.okuGuzergahService = okuGuzergahService;
    }

    /**
     * POST  /oku-guzergahs : Create a new okuGuzergah.
     *
     * @param okuGuzergah the okuGuzergah to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuGuzergah, or with status 400 (Bad Request) if the okuGuzergah has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-guzergahs")
    @Timed
    public ResponseEntity<OkuGuzergah> createOkuGuzergah(@RequestBody OkuGuzergah okuGuzergah) throws URISyntaxException {
        log.debug("REST request to save OkuGuzergah : {}", okuGuzergah);
        if (okuGuzergah.getId() != null) {
            throw new BadRequestAlertException("A new okuGuzergah cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuGuzergah result = okuGuzergahService.save(okuGuzergah);
        return ResponseEntity.created(new URI("/api/oku-guzergahs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-guzergahs : Updates an existing okuGuzergah.
     *
     * @param okuGuzergah the okuGuzergah to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuGuzergah,
     * or with status 400 (Bad Request) if the okuGuzergah is not valid,
     * or with status 500 (Internal Server Error) if the okuGuzergah couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-guzergahs")
    @Timed
    public ResponseEntity<OkuGuzergah> updateOkuGuzergah(@RequestBody OkuGuzergah okuGuzergah) throws URISyntaxException {
        log.debug("REST request to update OkuGuzergah : {}", okuGuzergah);
        if (okuGuzergah.getId() == null) {
            return createOkuGuzergah(okuGuzergah);
        }
        OkuGuzergah result = okuGuzergahService.save(okuGuzergah);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuGuzergah.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-guzergahs : get all the okuGuzergahs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuGuzergahs in body
     */
    @GetMapping("/oku-guzergahs")
    @Timed
    public ResponseEntity<List<OkuGuzergah>> getAllOkuGuzergahs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuGuzergahs");
        Page<OkuGuzergah> page = okuGuzergahService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-guzergahs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-guzergahs/:id : get the "id" okuGuzergah.
     *
     * @param id the id of the okuGuzergah to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuGuzergah, or with status 404 (Not Found)
     */
    @GetMapping("/oku-guzergahs/{id}")
    @Timed
    public ResponseEntity<OkuGuzergah> getOkuGuzergah(@PathVariable Long id) {
        log.debug("REST request to get OkuGuzergah : {}", id);
        OkuGuzergah okuGuzergah = okuGuzergahService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuGuzergah));
    }

    /**
     * DELETE  /oku-guzergahs/:id : delete the "id" okuGuzergah.
     *
     * @param id the id of the okuGuzergah to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-guzergahs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuGuzergah(@PathVariable Long id) {
        log.debug("REST request to delete OkuGuzergah : {}", id);
        okuGuzergahService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-guzergahs?query=:query : search for the okuGuzergah corresponding
     * to the query.
     *
     * @param query the query of the okuGuzergah search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-guzergahs")
    @Timed
    public ResponseEntity<List<OkuGuzergah>> searchOkuGuzergahs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuGuzergahs for query {}", query);
        Page<OkuGuzergah> page = okuGuzergahService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-guzergahs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
