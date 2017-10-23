package com.okulservis.service;

import com.okulservis.domain.OkuOkul;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OkuOkul.
 */
public interface OkuOkulService {

    /**
     * Save a okuOkul.
     *
     * @param okuOkul the entity to save
     * @return the persisted entity
     */
    OkuOkul save(OkuOkul okuOkul);

    /**
     *  Get all the okuOkuls.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuOkul> findAll(Pageable pageable);

    /**
     *  Get the "id" okuOkul.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OkuOkul findOne(Long id);

    /**
     *  Delete the "id" okuOkul.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the okuOkul corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuOkul> search(String query, Pageable pageable);
}
