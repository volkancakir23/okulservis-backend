package com.okulservis.service;

import com.okulservis.domain.OkuYolcu;
import com.okulservis.domain.enumeration.OkuServis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service Interface for managing OkuYolcu.
 */
public interface OkuYolcuService {

    /**
     * Save a okuYolcu.
     *
     * @param okuYolcu the entity to save
     * @return the persisted entity
     */
    OkuYolcu save(OkuYolcu okuYolcu);

    /**
     *  Get all the okuYolcus.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuYolcu> findAll(Pageable pageable);

    /**
     *  Get the "id" okuYolcu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OkuYolcu findOne(Long id);

    /**
     *  Delete the "id" okuYolcu.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the okuYolcu corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OkuYolcu> search(String query, Pageable pageable);

    List<OkuYolcu> findBySefer_Tarih(LocalDate date);
    List<OkuYolcu> findBySefer_TarihAndSefer_Servis(LocalDate tarih, OkuServis okuServis);
    List<OkuYolcu> findBySefer_TarihAndSefer_ServisAndSefer_Sofor_User(LocalDate tarih, OkuServis okuServis);

    List<OkuYolcu> findByQuery(LocalDate now, OkuServis okuServis);

    List<OkuYolcu> findOkuYolcuBySefer_Id(Long SeferId);
}
