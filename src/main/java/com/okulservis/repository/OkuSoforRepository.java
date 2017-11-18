package com.okulservis.repository;

import com.okulservis.domain.OkuSofor;
import com.okulservis.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the OkuSofor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuSoforRepository extends JpaRepository<OkuSofor, Long> {

    OkuSofor findOkuSoforByUser(User user);

    @Query("select oku_sofor from OkuSofor oku_sofor where oku_sofor.user.login = ?#{principal.username}")
    OkuSofor findByUserIsCurrentUser();

}
