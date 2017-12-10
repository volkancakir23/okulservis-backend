package com.okulservis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.okulservis.domain.OkuYolcu;
import com.okulservis.service.OkuYolcuService;
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
 * REST controller for managing OkuYolcu.
 */
@RestController
@RequestMapping("/api")
public class OkuYolcuResource {

    private final Logger log = LoggerFactory.getLogger(OkuYolcuResource.class);

    private static final String ENTITY_NAME = "okuYolcu";

    private final OkuYolcuService okuYolcuService;

    public OkuYolcuResource(OkuYolcuService okuYolcuService) {
        this.okuYolcuService = okuYolcuService;
    }

    /**
     * POST  /oku-yolcus : Create a new okuYolcu.
     *
     * @param okuYolcu the okuYolcu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new okuYolcu, or with status 400 (Bad Request) if the okuYolcu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oku-yolcus")
    @Timed
    public ResponseEntity<OkuYolcu> createOkuYolcu(@RequestBody OkuYolcu okuYolcu) throws URISyntaxException {
        log.debug("REST request to save OkuYolcu : {}", okuYolcu);
        if (okuYolcu.getId() != null) {
            throw new BadRequestAlertException("A new okuYolcu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OkuYolcu result = okuYolcuService.save(okuYolcu);
        return ResponseEntity.created(new URI("/api/oku-yolcus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oku-yolcus : Updates an existing okuYolcu.
     *
     * @param okuYolcu the okuYolcu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated okuYolcu,
     * or with status 400 (Bad Request) if the okuYolcu is not valid,
     * or with status 500 (Internal Server Error) if the okuYolcu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oku-yolcus")
    @Timed
    public ResponseEntity<OkuYolcu> updateOkuYolcu(@RequestBody OkuYolcu okuYolcu) throws URISyntaxException {
        log.debug("REST request to update OkuYolcu : {}", okuYolcu);
        if (okuYolcu.getId() == null) {
            return createOkuYolcu(okuYolcu);
        }
        OkuYolcu result = okuYolcuService.save(okuYolcu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, okuYolcu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oku-yolcus : get all the okuYolcus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of okuYolcus in body
     */
    @GetMapping("/oku-yolcus")
    @Timed
    public ResponseEntity<List<OkuYolcu>> getAllOkuYolcus(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OkuYolcus");
        Page<OkuYolcu> page = okuYolcuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oku-yolcus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oku-yolcus/:id : get the "id" okuYolcu.
     *
     * @param id the id of the okuYolcu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the okuYolcu, or with status 404 (Not Found)
     */
    @GetMapping("/oku-yolcus/{id}")
    @Timed
    public ResponseEntity<OkuYolcu> getOkuYolcu(@PathVariable Long id) {
        log.debug("REST request to get OkuYolcu : {}", id);
        OkuYolcu okuYolcu = okuYolcuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(okuYolcu));
    }

    /**
     * DELETE  /oku-yolcus/:id : delete the "id" okuYolcu.
     *
     * @param id the id of the okuYolcu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oku-yolcus/{id}")
    @Timed
    public ResponseEntity<Void> deleteOkuYolcu(@PathVariable Long id) {
        log.debug("REST request to delete OkuYolcu : {}", id);
        okuYolcuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/oku-yolcus?query=:query : search for the okuYolcu corresponding
     * to the query.
     *
     * @param query the query of the okuYolcu search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/oku-yolcus")
    @Timed
    public ResponseEntity<List<OkuYolcu>> searchOkuYolcus(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OkuYolcus for query {}", query);
        Page<OkuYolcu> page = okuYolcuService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/oku-yolcus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
