package com.okulservis.service.impl;

import com.okulservis.service.OkuSehirService;
import com.okulservis.domain.OkuSehir;
import com.okulservis.repository.OkuSehirRepository;
import com.okulservis.repository.search.OkuSehirSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuSehir.
 */
@Service
@Transactional
public class OkuSehirServiceImpl implements OkuSehirService{

    private final Logger log = LoggerFactory.getLogger(OkuSehirServiceImpl.class);

    private final OkuSehirRepository okuSehirRepository;

    private final OkuSehirSearchRepository okuSehirSearchRepository;

    public OkuSehirServiceImpl(OkuSehirRepository okuSehirRepository, OkuSehirSearchRepository okuSehirSearchRepository) {
        this.okuSehirRepository = okuSehirRepository;
        this.okuSehirSearchRepository = okuSehirSearchRepository;
    }

    /**
     * Save a okuSehir.
     *
     * @param okuSehir the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuSehir save(OkuSehir okuSehir) {
        log.debug("Request to save OkuSehir : {}", okuSehir);
        OkuSehir result = okuSehirRepository.save(okuSehir);
        okuSehirSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuSehirs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuSehir> findAll(Pageable pageable) {
        log.debug("Request to get all OkuSehirs");
        return okuSehirRepository.findAll(pageable);
    }

    /**
     *  Get one okuSehir by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuSehir findOne(Long id) {
        log.debug("Request to get OkuSehir : {}", id);
        return okuSehirRepository.findOne(id);
    }

    /**
     *  Delete the  okuSehir by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuSehir : {}", id);
        okuSehirRepository.delete(id);
        okuSehirSearchRepository.delete(id);
    }

    /**
     * Search for the okuSehir corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuSehir> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuSehirs for query {}", query);
        Page<OkuSehir> result = okuSehirSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
