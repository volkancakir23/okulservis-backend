package com.okulservis.service.impl;

import com.okulservis.service.OkuGuzergahService;
import com.okulservis.domain.OkuGuzergah;
import com.okulservis.repository.OkuGuzergahRepository;
import com.okulservis.repository.search.OkuGuzergahSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuGuzergah.
 */
@Service
@Transactional
public class OkuGuzergahServiceImpl implements OkuGuzergahService{

    private final Logger log = LoggerFactory.getLogger(OkuGuzergahServiceImpl.class);

    private final OkuGuzergahRepository okuGuzergahRepository;

    private final OkuGuzergahSearchRepository okuGuzergahSearchRepository;

    public OkuGuzergahServiceImpl(OkuGuzergahRepository okuGuzergahRepository, OkuGuzergahSearchRepository okuGuzergahSearchRepository) {
        this.okuGuzergahRepository = okuGuzergahRepository;
        this.okuGuzergahSearchRepository = okuGuzergahSearchRepository;
    }

    /**
     * Save a okuGuzergah.
     *
     * @param okuGuzergah the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuGuzergah save(OkuGuzergah okuGuzergah) {
        log.debug("Request to save OkuGuzergah : {}", okuGuzergah);
        OkuGuzergah result = okuGuzergahRepository.save(okuGuzergah);
        okuGuzergahSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuGuzergahs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuGuzergah> findAll(Pageable pageable) {
        log.debug("Request to get all OkuGuzergahs");
        return okuGuzergahRepository.findAll(pageable);
    }

    /**
     *  Get one okuGuzergah by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuGuzergah findOne(Long id) {
        log.debug("Request to get OkuGuzergah : {}", id);
        return okuGuzergahRepository.findOne(id);
    }

    /**
     *  Delete the  okuGuzergah by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuGuzergah : {}", id);
        okuGuzergahRepository.delete(id);
        okuGuzergahSearchRepository.delete(id);
    }

    /**
     * Search for the okuGuzergah corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuGuzergah> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuGuzergahs for query {}", query);
        Page<OkuGuzergah> result = okuGuzergahSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
