package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuPersonel;
import com.okulservis.service.OkuPersonelService;
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
 * REST controller for managing OkuPersonel.
 */
@RestController
@RequestMapping("/api")
public class OkuPersonelResource {

    private final Logger log = LoggerFactory.getLogger(OkuPersonelResource.class);

    private static final String ENTITY_NAME = "okuPersonel";

    private final OkuPersonelService okuPersonelService;

    public OkuPersonelResource(OkuPersonelService okuPersonelService) {
        this.okuPersonelService = okuPersonelService;
    }

    /**
     * POST  /oku-personels : Create a new okuPersonel.
     *
     * @param okuPersonel the okuPersonel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuPersonel, or with status 400 (Bad Request) if the okuPersonel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-personels")
    @Timed
    public ResponseEntity<OkuPersonel> createOkuPersonel(@RequestBody OkuPersonel okuPersonel) throws URISyntaxException {
        log.debug("REST request to save OkuPersonel : {}", okuPersonel);
        if (okuPersonel.getId() != null) {
            throw new BadRequestAlertException("A new okuPersonel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuPersonel result = okuPersonelService.save(okuPersonel);
        return ResponseEntity.created(new URI("/api/oku-personels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-personels : Updates an existing okuPersonel.
     *
     * @param okuPersonel the okuPersonel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuPersonel,
     * or with status 400 (Bad Request) if the okuPersonel is not valid,
     * or with status 500 (Internal Server Error) if the okuPersonel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-personels")
    @Timed
    public ResponseEntity<OkuPersonel> updateOkuPersonel(@RequestBody OkuPersonel okuPersonel) throws URISyntaxException {
        log.debug("REST request to update OkuPersonel : {}", okuPersonel);
        if (okuPersonel.getId() == null) {
            return createOkuPersonel(okuPersonel);
        }
        OkuPersonel result = okuPersonelService.save(okuPersonel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuPersonel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-personels : get all the okuPersonels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuPersonels in body
     */
    @GetMapping("/oku-personels")
    @Timed
    public ResponseEntity<List<OkuPersonel>> getAllOkuPersonels(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuPersonels");
        Page<OkuPersonel> page = okuPersonelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-personels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-personels/:id : get the "id" okuPersonel.
     *
     * @param id the id of the okuPersonel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuPersonel, or with status 404 (Not Found)
     */
    @GetMapping("/oku-personels/{id}")
    @Timed
    public ResponseEntity<OkuPersonel> getOkuPersonel(@PathVariable Long id) {
        log.debug("REST request to get OkuPersonel : {}", id);
        OkuPersonel okuPersonel = okuPersonelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuPersonel));
    }

    /**
     * DELETE  /oku-personels/:id : delete the "id" okuPersonel.
     *
     * @param id the id of the okuPersonel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-personels/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuPersonel(@PathVariable Long id) {
        log.debug("REST request to delete OkuPersonel : {}", id);
        okuPersonelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-personels?query=:query : search for the okuPersonel corresponding
     * to the query.
     *
     * @param query the query of the okuPersonel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-personels")
    @Timed
    public ResponseEntity<List<OkuPersonel>> searchOkuPersonels(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuPersonels for query {}", query);
        Page<OkuPersonel> page = okuPersonelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-personels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
