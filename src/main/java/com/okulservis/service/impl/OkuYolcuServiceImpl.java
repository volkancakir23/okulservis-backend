package com.okulservis.service.impl;

import com.okulservis.domain.User;
import com.okulservis.domain.enumeration.OkuServis;
import com.okulservis.service.BaseServiceImpl;
import com.okulservis.service.OkuYolcuService;
import com.okulservis.domain.OkuYolcu;
import com.okulservis.repository.OkuYolcuRepository;
import com.okulservis.repository.search.OkuYolcuSearchRepository;
import com.okulservis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;


/**
 * Service Implementation for managing OkuYolcu.
 */
@Service
@Transactional
public class OkuYolcuServiceImpl extends BaseServiceImpl implements OkuYolcuService{

    private final Logger log = LoggerFactory.getLogger(OkuYolcuServiceImpl.class);

    private final OkuYolcuRepository okuYolcuRepository;

    private final OkuYolcuSearchRepository okuYolcuSearchRepository;

    private final UserService userService;

    public OkuYolcuServiceImpl(OkuYolcuRepository okuYolcuRepository,
                               OkuYolcuSearchRepository okuYolcuSearchRepository, UserService userService) {
        this.okuYolcuRepository = okuYolcuRepository;
        this.okuYolcuSearchRepository = okuYolcuSearchRepository;
        this.userService = userService;
    }

    /**
     * Save a okuYolcu.
     *
     * @param okuYolcu the entity to save
     * @return the persisted entity
     */
    @Override
    public OkuYolcu save(OkuYolcu okuYolcu) {
        log.debug("Request to save OkuYolcu : {}", okuYolcu);
        OkuYolcu result = okuYolcuRepository.save(okuYolcu);
        okuYolcuSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the okuYolcus.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuYolcu> findAll(Pageable pageable) {
        log.debug("Request to get all OkuYolcus");
        return okuYolcuRepository.findAll(pageable);
    }

    /**
     *  Get one okuYolcu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OkuYolcu findOne(Long id) {
        log.debug("Request to get OkuYolcu : {}", id);
        return okuYolcuRepository.findOne(id);
    }

    /**
     *  Delete the  okuYolcu by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OkuYolcu : {}", id);
        okuYolcuRepository.delete(id);
        okuYolcuSearchRepository.delete(id);
    }

    /**
     * Search for the okuYolcu corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OkuYolcu> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OkuYolcus for query {}", query);
        Page<OkuYolcu> result = okuYolcuSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OkuYolcu> findBySefer_Tarih(LocalDate date) {
        return okuYolcuRepository.findBySefer_Tarih(date);
    }

    @Transactional(readOnly = true)
    public List<OkuYolcu> findBySefer_TarihAndSefer_Servis(LocalDate tarih, OkuServis okuServis) {
        User user = userService.getUserWithAuthorities();
        return okuYolcuRepository.findBySefer_TarihAndSefer_Servis(tarih,okuServis);
    }

    @Override
    public List<OkuYolcu> findBySefer_TarihAndSefer_ServisAndSefer_Sofor_User(LocalDate tarih, OkuServis okuServis) {
        User user = userService.getUserWithAuthorities();
        return okuYolcuRepository.findBySefer_TarihAndSefer_ServisAndSefer_Sofor_User(tarih,okuServis,user);
    }

    @Transactional(readOnly = true)
    public List<OkuYolcu> findByQuery(LocalDate tarih, OkuServis okuServis){
        User user = userService.getUserWithAuthorities();
        Long userId = user.getId();
        return okuYolcuRepository.findByQuery(tarih,okuServis,userId);
    }

    public List<OkuYolcu> findOkuYolcuBySefer_Id(Long seferId) {

        return okuYolcuRepository.findOkuYolcuBySefer_Id(seferId);
    }
}
