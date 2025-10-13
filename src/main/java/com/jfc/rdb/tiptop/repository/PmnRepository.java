package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.PmnFile;
import com.jfc.rdb.tiptop.entity.PmnFilePK;
@Repository
public interface PmnRepository extends JpaRepository<PmnFile, PmnFilePK> {
	/*採購單單頭(pmm_file)
    採購單單身(pmn_file)
    pmm18	varchar2(1)	確認碼	確認碼(Y/N/X) X.作廢         (97/07)
    pmm25	varchar2(1)	狀況碼	狀況碼 正確值 x/0/1/2/6/9/S/R/W X: 計劃訂單轉入 0: 開立(Open) 1: 已核准 2: 發出採購單 6: 結案 9: 取消(Cancel) S: 送簽                  #No.6686 R: 送簽退回 W: 抽單
    pmn16	varchar2(1)	狀況碼	狀況碼 Status Flag X :計劃工單轉入 0 :開立(Open) 1 :已核准 2 :發出採購單 6 :結案-正常 7 :結案-結長 8 :結案-結短 9 :取消(Cancel)/ 作廢
    pmm02	varchar2(10)	採購單性質	採購單性質 正確值 REG/EXP/CAP/SUB REG:REGular          一般性  採購 EXP:EXPensed         消耗性  採購 CAP:CAPital          資材性  採購 SUB:SUBcontracted    廠外加工採購 TRI:Tri-Angle PO     銷售性多角貿易採購 TAP:Tri-Angle PO     採購性多角貿易採購 ICT:Inter-Company Transfer 集團調撥的採購單
    pmn011	varchar2(10)	單據性質	單據性質 正確值 REG/EXP/SER/CAP/BKR/SUB/DAS/IPO  /RTN REG:REGular          一般性  採購 EXP:EXPensed         消耗性  採購 SER:SERvice          服務性  採購 CAP:CAPital          資材性  採購 BKR:BlanKet Regular  無交期性採購 SUB:SUBcontracted    廠外加工採購 DAS:De-assembly      套件    採購 IPO:In
    */
    //2017-03-02 請購單轉採購，送簽中視為有效 pmm18 = N
	@Query("""
			SELECT p.pmn20 - (p.pmn50 - p.pmn55) as purchQty
			FROM PmnFile p
			LEFT JOIN p.pmm pmm
			WHERE pmm.pmm18 IN ('Y', 'N')
			AND pmm.pmm25 NOT IN ('6', '9', 'R', 'W')
			AND p.pmn16 NOT IN ('6', '7', '8', '9')
			AND p.pmn011 != 'SUB'
			AND p.pmn04 = :mano
			""")
	List<BigDecimal> findPurchaseAmounts(@Param("mano") String mano);
}
