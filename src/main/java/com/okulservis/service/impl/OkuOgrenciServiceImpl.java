package com.okulservis.service.impl;

import com.okulservis.service.OkuOgrenciService;
import com.okulservis.domain.OkuOgrenci;
import com.okulservis.repository.OkuOgrenciRepository;
import com.okulservis.repository.search.OkuOgrenciSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OkuOgrenci.
 */
@Service
@Transactional
public class OkuOgrenciServiceImpl implements OkuOgrenciService{

    private final Logger log = LoggerFactory.getLogger(OkuOgrenciServiceImpl.class);

    private final OkuOgrenciRepository okuOgrenciRepository;

    private final OkuOgrenciSearchRepository okuOgrenciSearchRepository;

    public OkuOgrenciServiceImpl(OkuOgrenciRepository okuOgrenciRepository, OkuOgrenciSearchRepository okuOgrenciSearchRepository) {
        this.okuOgrenciRepository = okuOgrenciRepository;
        this.okuOgrenciSearchRepository = okuOgrenciSearchRepository;
    }

    /**
     * Save a okuOgrenci.
     *
     * @param okuOgrenci the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuOgrenci save(OkuOgrenci okuOgrenci) {
        log.debug("Request to save OkuOgrenci : {}", okuOgrenci);
        OkuOgrenci result = okuOgrenciRepository.save(okuOgrenci);
        okuOgrenciSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuOgrencis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuOgrenci> findAll(Pageable pageable) {
        log.debug("Request to get all OkuOgrencis");
        return okuOgrenciRepository.findAll(pageable);
    }

    /**
     *  Get one okuOgrenci by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuOgrenci findOne(Long id) {
        log.debug("Request to get OkuOgrenci : {}", id);
        return okuOgrenciRepository.findOne(id);
    }

    /**
     *  Delete the  okuOgrenci by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuOgrenci : {}", id);
        okuOgrenciRepository.delete(id);
        okuOgrenciSearchRepository.delete(id);
    }

    /**
     * Search for the okuOgrenci corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuOgrenci> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuOgrencis for query {}", query);
        Page<OkuOgrenci> result = okuOgrenciSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
