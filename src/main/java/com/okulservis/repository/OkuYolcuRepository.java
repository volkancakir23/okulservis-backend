package com.okulservis.repository;

import com.okulservis.domain.OkuYolcu;
import com.okulservis.domain.enumeration.OkuServis;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


/**
 * Spring Data JPA repository for the OkuYolcu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuYolcuRepository extends JpaRepository<OkuYolcu, Long> {

    List<OkuYolcu> findBySefer_Tarih(LocalDate tarih);

    List<OkuYolcu> findBySefer_TarihAndSefer_Servis(LocalDate tarih, OkuServis okuServis);
}
