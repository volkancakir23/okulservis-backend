package com.okulservis.repository;

import com.okulservis.domain.OkuVeli;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the OkuVeli entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuVeliRepository extends JpaRepository<OkuVeli, Long> {

    @Query("select oku_veli from OkuVeli oku_veli where oku_veli.user.login = ?#{principal.username}")
    List<OkuVeli> findByUserIsCurrentUser();

}
