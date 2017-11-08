package com.okulservis.service;

import com.okulservis.domain.OkuVeli;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OkuVeli.
 */
public interface OkuVeliService {

    /**
     * Save a okuVeli.
     *
     * @param okuVeli the entity to save
     * @return the persisted entity
     */
    OkuVeli save(OkuVeli okuVeli);

    /**
     *  Get all the okuVelis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuVeli> findAll(Pageable pageable);

    /**
     *  Get the "id" okuVeli.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OkuVeli findOne(Long id);

    /**
     *  Delete the "id" okuVeli.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the okuVeli corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuVeli> search(String query, Pageable pageable);
}
