package com.okulservis.service.impl;

import com.okulservis.service.OkuAracService;
import com.okulservis.domain.OkuArac;
import com.okulservis.repository.OkuAracRepository;
import com.okulservis.repository.search.OkuAracSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuArac.
 */
@Service
@Transactional
public class OkuAracServiceImpl implements OkuAracService{

    private final Logger log = LoggerFactory.getLogger(OkuAracServiceImpl.class);

    private final OkuAracRepository okuAracRepository;

    private final OkuAracSearchRepository okuAracSearchRepository;

    public OkuAracServiceImpl(OkuAracRepository okuAracRepository, OkuAracSearchRepository okuAracSearchRepository) {
        this.okuAracRepository = okuAracRepository;
        this.okuAracSearchRepository = okuAracSearchRepository;
    }

    /**
     * Save a okuArac.
     *
     * @param okuArac the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuArac save(OkuArac okuArac) {
        log.debug("Request to save OkuArac : {}", okuArac);
        OkuArac result = okuAracRepository.save(okuArac);
        okuAracSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuAracs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuArac> findAll(Pageable pageable) {
        log.debug("Request to get all OkuAracs");
        return okuAracRepository.findAll(pageable);
    }

    /**
     *  Get one okuArac by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuArac findOne(Long id) {
        log.debug("Request to get OkuArac : {}", id);
        return okuAracRepository.findOne(id);
    }

    /**
     *  Delete the  okuArac by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuArac : {}", id);
        okuAracRepository.delete(id);
        okuAracSearchRepository.delete(id);
    }

    /**
     * Search for the okuArac corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuArac> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuAracs for query {}", query);
        Page<OkuArac> result = okuAracSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
