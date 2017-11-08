package com.okulservis.service.impl;

import com.okulservis.service.OkuOkulService;
import com.okulservis.domain.OkuOkul;
import com.okulservis.repository.OkuOkulRepository;
import com.okulservis.repository.search.OkuOkulSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuOkul.
 */
@Service
@Transactional
public class OkuOkulServiceImpl implements OkuOkulService{

    private final Logger log = LoggerFactory.getLogger(OkuOkulServiceImpl.class);

    private final OkuOkulRepository okuOkulRepository;

    private final OkuOkulSearchRepository okuOkulSearchRepository;

    public OkuOkulServiceImpl(OkuOkulRepository okuOkulRepository, OkuOkulSearchRepository okuOkulSearchRepository) {
        this.okuOkulRepository = okuOkulRepository;
        this.okuOkulSearchRepository = okuOkulSearchRepository;
    }

    /**
     * Save a okuOkul.
     *
     * @param okuOkul the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuOkul save(OkuOkul okuOkul) {
        log.debug("Request to save OkuOkul : {}", okuOkul);
        OkuOkul result = okuOkulRepository.save(okuOkul);
        okuOkulSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuOkuls.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuOkul> findAll(Pageable pageable) {
        log.debug("Request to get all OkuOkuls");
        return okuOkulRepository.findAll(pageable);
    }

    /**
     *  Get one okuOkul by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuOkul findOne(Long id) {
        log.debug("Request to get OkuOkul : {}", id);
        return okuOkulRepository.findOne(id);
    }

    /**
     *  Delete the  okuOkul by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuOkul : {}", id);
        okuOkulRepository.delete(id);
        okuOkulSearchRepository.delete(id);
    }

    /**
     * Search for the okuOkul corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuOkul> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuOkuls for query {}", query);
        Page<OkuOkul> result = okuOkulSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
