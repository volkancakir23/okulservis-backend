package com.okulservis.repository;

import com.okulservis.domain.OkuGuzergah;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OkuGuzergah entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuGuzergahRepository extends JpaRepository<OkuGuzergah, Long> {

}
