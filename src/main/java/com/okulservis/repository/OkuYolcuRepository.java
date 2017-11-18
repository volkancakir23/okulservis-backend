package com.okulservis.repository;

import com.okulservis.domain.OkuYolcu;
import com.okulservis.domain.User;
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

    List<OkuYolcu> findBySefer_TarihAndSefer_ServisAndSefer_Sofor_User(LocalDate tarih, OkuServis okuServis, User user);

    @Query("select o from OkuYolcu o " +
        "left outer join OkuSefer osf on o.sefer.id=osf.id " +
        "left outer join OkuSofor os on osf.sofor.id=os.id " +
        "left outer join User u on os.user.id=u.id " +
        "where osf.tarih=:tarih and osf.servis=:okuServis and u.id=:userId"
    )
    List<OkuYolcu> findByQuery(@Param("tarih") LocalDate tarih, @Param("okuServis") OkuServis okuServis, @Param("userId") Long userId);


    List<OkuYolcu> findOkuYolcuBySefer_Id(@Param("seferId") Long seferId);

}
