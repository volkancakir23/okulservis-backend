package com.okulservis.service;

import com.okulservis.domain.OkuGuzergah;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OkuGuzergah.
 */
public interface OkuGuzergahService {

    /**
     * Save a okuGuzergah.
     *
     * @param okuGuzergah the entity to save
     * @return the persisted entity
     */
    OkuGuzergah save(OkuGuzergah okuGuzergah);

    /**
     *  Get all the okuGuzergahs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuGuzergah> findAll(Pageable pageable);

    /**
     *  Get the "id" okuGuzergah.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OkuGuzergah findOne(Long id);

    /**
     *  Delete the "id" okuGuzergah.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the okuGuzergah corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuGuzergah> search(String query, Pageable pageable);
}
