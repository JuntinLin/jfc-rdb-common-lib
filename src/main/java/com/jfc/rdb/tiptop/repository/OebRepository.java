package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OebFile;
import com.jfc.rdb.tiptop.entity.OebFilePK;
@Repository
public interface OebRepository extends JpaRepository<OebFile, OebFilePK> {
	//oea49	varchar2(1)	狀況碼	"狀況碼: 0: 開立(Open) 1: 已核准 2: 結案 S: 送簽 #No.6686 R: 送簽退回 W: 抽單 
    //oeaconf	varchar2(1)	確認否	確認否 (Y/N/X)
    //oeb70	varchar2(1)	結案否	結案否 (Y/N)
    //oeb12	number(15,3)	數量
    //oeb24	number(15,3)	已出貨數量
	@Query("""
	        SELECT COALESCE(SUM(o.oeb12) - SUM(o.oeb24), 0) 
	        FROM OebFile o 
	        LEFT JOIN o.oea oea 
	        WHERE oea.oea49 IN ('0', '1', 'S') 
	        AND oea.oeaconf = 'Y' 
	        AND o.oeb70 != 'Y' 
	        AND o.oeb04 = :mano
	        """)
	BigDecimal findOnOrderAmount(@Param("mano") String mano);
}
