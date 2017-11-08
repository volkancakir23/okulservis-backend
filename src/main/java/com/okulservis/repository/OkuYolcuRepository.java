package com.okulservis.repository;

import com.okulservis.domain.OkuYolcu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OkuYolcu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuYolcuRepository extends JpaRepository<OkuYolcu, Long> {

}
