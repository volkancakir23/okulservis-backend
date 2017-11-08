package com.okulservis.repository;

import com.okulservis.domain.OkuSofor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the OkuSofor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuSoforRepository extends JpaRepository<OkuSofor, Long> {

    @Query("select oku_sofor from OkuSofor oku_sofor where oku_sofor.user.login = ?#{principal.username}")
    List<OkuSofor> findByUserIsCurrentUser();

}
