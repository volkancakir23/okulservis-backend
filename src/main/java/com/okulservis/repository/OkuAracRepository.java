package com.okulservis.repository;

import com.okulservis.domain.OkuArac;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OkuArac entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuAracRepository extends JpaRepository<OkuArac, Long> {

}
