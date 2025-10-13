package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.CmcFile;
import com.jfc.rdb.tiptop.entity.CmcFilePK;

@Repository
public interface CmcRepository extends JpaRepository<CmcFile, CmcFilePK> {
	@Query(value = """
	        SELECT COALESCE(SUM(c.cmc04), 0)
	        FROM Cmc_File c
	        WHERE c.cmc01 = :mano 
	        AND (c.cmc02 - c.cmc03) >= :aging
	        AND c.cmc02 = (
	            SELECT MAX(c2.cmc02) 
	            FROM Cmc_File c2 
	            WHERE c2.cmc01 = :mano
	        )
	        """,
	        nativeQuery = true)
	    BigDecimal findStockAmountOverAging(
	        @Param("aging") int aging, 
	        @Param("mano") String mano
	    );
}
