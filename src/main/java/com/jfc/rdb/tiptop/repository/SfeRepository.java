package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.SfeFile;
@Repository
public interface SfeRepository extends JpaRepository<SfeFile, String> {
	//工單料帳歷史檔(sfe_file) 已結案的工單發料
    //sfe04	date	異動日期	"異動日期 儲存該筆異動日期"
    //sfe07	varchar2(40)	異動料件編號	"異動料件編號 儲存該筆異動的料件編號"
    //sfe16	number(15,3)	異動數量	"異動數量 儲存該筆異動的異動數量"
    //2018-06-06 須排除重工工單　sfb02　工單型態　5: 再加工工單
	@Query("""
	        SELECT COALESCE(SUM(s.sfe16), 0) 
	        FROM SfeFile s 
	        WHERE s.sfe07 = :mano 
	        AND s.sfe04 BETWEEN :beginDate AND :endDate
	        """)
	    BigDecimal findSendingAmount(
	        @Param("mano") String mano,
	        @Param("beginDate") Date beginDate,
	        @Param("endDate") Date endDate
	    );
}
