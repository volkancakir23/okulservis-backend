package com.okulservis.service;

import com.okulservis.domain.OkuSofor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OkuSofor.
 */
public interface OkuSoforService {

    /**
     * Save a okuSofor.
     *
     * @param okuSofor the entity to save
     * @return the persisted entity
     */
    OkuSofor save(OkuSofor okuSofor);

    /**
     *  Get all the okuSofors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuSofor> findAll(Pageable pageable);

    /**
     *  Get the "id" okuSofor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OkuSofor findOne(Long id);

    /**
     *  Delete the "id" okuSofor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the okuSofor corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuSofor> search(String query, Pageable pageable);
}
