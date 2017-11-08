package com.okulservis.service;

import com.okulservis.domain.OkuOgrenci;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OkuOgrenci.
 */
public interface OkuOgrenciService {

    /**
     * Save a okuOgrenci.
     *
     * @param okuOgrenci the entity to save
     * @return the persisted entity
     */
    OkuOgrenci save(OkuOgrenci okuOgrenci);

    /**
     *  Get all the okuOgrencis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuOgrenci> findAll(Pageable pageable);

    /**
     *  Get the "id" okuOgrenci.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OkuOgrenci findOne(Long id);

    /**
     *  Delete the "id" okuOgrenci.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the okuOgrenci corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuOgrenci> search(String query, Pageable pageable);
}
