package com.okulservis.service.impl;

import com.okulservis.service.OkuSeferService;
import com.okulservis.domain.OkuSefer;
import com.okulservis.repository.OkuSeferRepository;
import com.okulservis.repository.search.OkuSeferSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuSefer.
 */
@Service
@Transactional
public class OkuSeferServiceImpl implements OkuSeferService{

    private final Logger log = LoggerFactory.getLogger(OkuSeferServiceImpl.class);

    private final OkuSeferRepository okuSeferRepository;

    private final OkuSeferSearchRepository okuSeferSearchRepository;

    public OkuSeferServiceImpl(OkuSeferRepository okuSeferRepository, OkuSeferSearchRepository okuSeferSearchRepository) {
        this.okuSeferRepository = okuSeferRepository;
        this.okuSeferSearchRepository = okuSeferSearchRepository;
    }

    /**
     * Save a okuSefer.
     *
     * @param okuSefer the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuSefer save(OkuSefer okuSefer) {
        log.debug("Request to save OkuSefer : {}", okuSefer);
        OkuSefer result = okuSeferRepository.save(okuSefer);
        okuSeferSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuSefers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuSefer> findAll(Pageable pageable) {
        log.debug("Request to get all OkuSefers");
        return okuSeferRepository.findAll(pageable);
    }

    /**
     *  Get one okuSefer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuSefer findOne(Long id) {
        log.debug("Request to get OkuSefer : {}", id);
        return okuSeferRepository.findOne(id);
    }

    /**
     *  Delete the  okuSefer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuSefer : {}", id);
        okuSeferRepository.delete(id);
        okuSeferSearchRepository.delete(id);
    }

    /**
     * Search for the okuSefer corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuSefer> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuSefers for query {}", query);
        Page<OkuSefer> result = okuSeferSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
