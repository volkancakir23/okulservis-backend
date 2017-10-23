package com.okulservis.service.impl;

import com.okulservis.service.OkuSoforService;
import com.okulservis.domain.OkuSofor;
import com.okulservis.repository.OkuSoforRepository;
import com.okulservis.repository.search.OkuSoforSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuSofor.
 */
@Service
@Transactional
public class OkuSoforServiceImpl implements OkuSoforService{

    private final Logger log = LoggerFactory.getLogger(OkuSoforServiceImpl.class);

    private final OkuSoforRepository okuSoforRepository;

    private final OkuSoforSearchRepository okuSoforSearchRepository;

    public OkuSoforServiceImpl(OkuSoforRepository okuSoforRepository, OkuSoforSearchRepository okuSoforSearchRepository) {
        this.okuSoforRepository = okuSoforRepository;
        this.okuSoforSearchRepository = okuSoforSearchRepository;
    }

    /**
     * Save a okuSofor.
     *
     * @param okuSofor the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuSofor save(OkuSofor okuSofor) {
        log.debug("Request to save OkuSofor : {}", okuSofor);
        OkuSofor result = okuSoforRepository.save(okuSofor);
        okuSoforSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuSofors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuSofor> findAll(Pageable pageable) {
        log.debug("Request to get all OkuSofors");
        return okuSoforRepository.findAll(pageable);
    }

    /**
     *  Get one okuSofor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuSofor findOne(Long id) {
        log.debug("Request to get OkuSofor : {}", id);
        return okuSoforRepository.findOne(id);
    }

    /**
     *  Delete the  okuSofor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuSofor : {}", id);
        okuSoforRepository.delete(id);
        okuSoforSearchRepository.delete(id);
    }

    /**
     * Search for the okuSofor corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuSofor> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuSofors for query {}", query);
        Page<OkuSofor> result = okuSoforSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
