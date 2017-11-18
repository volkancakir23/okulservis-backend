package com.okulservis.repository;

import com.okulservis.domain.OkuPersonel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the OkuPersonel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuPersonelRepository extends JpaRepository<OkuPersonel, Long> {

    @Query("select oku_personel from OkuPersonel oku_personel where oku_personel.user.login = ?#{principal.username}")
    OkuPersonel findByUserIsCurrentUser();

}
