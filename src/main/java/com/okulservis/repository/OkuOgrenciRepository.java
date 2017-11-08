package com.okulservis.repository;

import com.okulservis.domain.OkuOgrenci;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OkuOgrenci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuOgrenciRepository extends JpaRepository<OkuOgrenci, Long> {

}
