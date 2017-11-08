package com.okulservis.repository;

import com.okulservis.domain.OkuSefer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OkuSefer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OkuSeferRepository extends JpaRepository<OkuSefer, Long> {

    //BudDocumentEntity findByFiscalYear(FiscalYearEntity fiscalYear);

//    @Query("select d from FinDetailEntity d inner join fetch d.item i where d.bsDocument.id = :documentId")
  //  List<FinDetailEntity> findPRDetailList(@Param("documentId")Long documentId);

    //@Modifying
    //@Transactional
    //@Query("update FinDetailEntity t set t.document=null where t.document.id = :finDocId and (resource != 'FIN' and resource != 'ACC')")
    //void updateRefFinInfo(@Param("finDocId") Long id);


}
