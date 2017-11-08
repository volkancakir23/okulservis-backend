package com.okulservis.service.impl;

import com.okulservis.service.OkuVeliService;
import com.okulservis.domain.OkuVeli;
import com.okulservis.repository.OkuVeliRepository;
import com.okulservis.repository.search.OkuVeliSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuVeli.
 */
@Service
@Transactional
public class OkuVeliServiceImpl implements OkuVeliService{

    private final Logger log = LoggerFactory.getLogger(OkuVeliServiceImpl.class);

    private final OkuVeliRepository okuVeliRepository;

    private final OkuVeliSearchRepository okuVeliSearchRepository;

    public OkuVeliServiceImpl(OkuVeliRepository okuVeliRepository, OkuVeliSearchRepository okuVeliSearchRepository) {
        this.okuVeliRepository = okuVeliRepository;
        this.okuVeliSearchRepository = okuVeliSearchRepository;
    }

    /**
     * Save a okuVeli.
     *
     * @param okuVeli the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuVeli save(OkuVeli okuVeli) {
        log.debug("Request to save OkuVeli : {}", okuVeli);
        OkuVeli result = okuVeliRepository.save(okuVeli);
        okuVeliSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuVelis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuVeli> findAll(Pageable pageable) {
        log.debug("Request to get all OkuVelis");
        return okuVeliRepository.findAll(pageable);
    }

    /**
     *  Get one okuVeli by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuVeli findOne(Long id) {
        log.debug("Request to get OkuVeli : {}", id);
        return okuVeliRepository.findOne(id);
    }

    /**
     *  Delete the  okuVeli by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuVeli : {}", id);
        okuVeliRepository.delete(id);
        okuVeliSearchRepository.delete(id);
    }

    /**
     * Search for the okuVeli corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuVeli> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuVelis for query {}", query);
        Page<OkuVeli> result = okuVeliSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
