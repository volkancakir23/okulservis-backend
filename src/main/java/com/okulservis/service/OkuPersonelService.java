package com.okulservis.service;

import com.okulservis.domain.OkuPersonel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OkuPersonel.
 */
public interface OkuPersonelService {

    /**
     * Save a okuPersonel.
     *
     * @param okuPersonel the entity to save
     * @return the persisted entity
     */
    OkuPersonel save(OkuPersonel okuPersonel);

    /**
     *  Get all the okuPersonels.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuPersonel> findAll(Pageable pageable);

    /**
     *  Get the "id" okuPersonel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OkuPersonel findOne(Long id);

    /**
     *  Delete the "id" okuPersonel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the okuPersonel corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuPersonel> search(String query, Pageable pageable);
}
