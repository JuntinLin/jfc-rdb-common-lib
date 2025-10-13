package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.SfsFile;
import com.jfc.rdb.tiptop.entity.SfsFilePK;

@Repository
public interface SfsRepository extends JpaRepository<SfsFile, SfsFilePK> {
    //工單發料底稿單身檔(sfs_file) 未結案的工單發料
    //工單發料底稿單頭檔(sfp_file)
    //sfs03	varchar2(40)	工單單號	工單   sfb01(ASR此欄是料號(asri210,asri220))
    //sfs04	varchar2(40)	料號
    //sfs05	number(15,3)	發料數量
    //sfp03	date	扣帳日期	
    //sfp04	varchar2(1)	扣帳碼	扣帳碼(Y/N)
    //sfpconf	varchar2(1)	確認碼
    //sfb02	number(5)	工單型態	"工單型態儲存該工單所屬類別型態正確值 1/2/5/7/11/12/13/15 1: 一般工單 5: 再加工工單 7: 委外工單 8: 重工委外工單 #Add By Snow 11: 拆件式工單 13: 預測工單 15: 試產工單 "
    
	@Query("""
			SELECT COALESCE(SUM(s.sfs05), 0)
			FROM SfsFile s
			LEFT JOIN s.sfp sfp
			LEFT JOIN s.sfb sfb
			WHERE sfp.sfpconf = 'Y'
			AND s.sfs04 = :mano
			AND sfb.sfb02 != 5
			AND sfp.sfp03 BETWEEN :beginDate AND :endDate
			""")
	BigDecimal findSendingAmount(@Param("mano") String mano, @Param("beginDate") Date beginDate,
			@Param("endDate") Date endDate);
}
