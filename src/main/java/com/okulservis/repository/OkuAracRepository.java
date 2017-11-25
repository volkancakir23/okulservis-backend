package com.okulservis.repository;

import com.okulservis.domain.OkuArac;
import com.okulservis.domain.enumeration.OkuServis;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the OkuArac entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuAracRepository extends JpaRepository<OkuArac, Long> {

    @Query("select os.id as seferId, os.arac.kod||' '||os.arac.plaka as dsc from OkuSefer os, OkuPersonel op " +
        "where os.tarih=:tarih and os.guzergah.okul.id=op.okul.id and op.user.id=:userId " +
        "and os.servis=:okuServis"
    )
    List<Object> findByPlakaQuery(@Param("okuServis") OkuServis okuServis, @Param("userId") Long userId, @Param("tarih") LocalDate tarih);

}
