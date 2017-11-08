package com.okulservis.service.impl;

import com.okulservis.service.OkuPersonelService;
import com.okulservis.domain.OkuPersonel;
import com.okulservis.repository.OkuPersonelRepository;
import com.okulservis.repository.search.OkuPersonelSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuPersonel.
 */
@Service
@Transactional
public class OkuPersonelServiceImpl implements OkuPersonelService{

    private final Logger log = LoggerFactory.getLogger(OkuPersonelServiceImpl.class);

    private final OkuPersonelRepository okuPersonelRepository;

    private final OkuPersonelSearchRepository okuPersonelSearchRepository;

    public OkuPersonelServiceImpl(OkuPersonelRepository okuPersonelRepository, OkuPersonelSearchRepository okuPersonelSearchRepository) {
        this.okuPersonelRepository = okuPersonelRepository;
        this.okuPersonelSearchRepository = okuPersonelSearchRepository;
    }

    /**
     * Save a okuPersonel.
     *
     * @param okuPersonel the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuPersonel save(OkuPersonel okuPersonel) {
        log.debug("Request to save OkuPersonel : {}", okuPersonel);
        OkuPersonel result = okuPersonelRepository.save(okuPersonel);
        okuPersonelSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuPersonels.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuPersonel> findAll(Pageable pageable) {
        log.debug("Request to get all OkuPersonels");
        return okuPersonelRepository.findAll(pageable);
    }

    /**
     *  Get one okuPersonel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuPersonel findOne(Long id) {
        log.debug("Request to get OkuPersonel : {}", id);
        return okuPersonelRepository.findOne(id);
    }

    /**
     *  Delete the  okuPersonel by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuPersonel : {}", id);
        okuPersonelRepository.delete(id);
        okuPersonelSearchRepository.delete(id);
    }

    /**
     * Search for the okuPersonel corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuPersonel> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuPersonels for query {}", query);
        Page<OkuPersonel> result = okuPersonelSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
