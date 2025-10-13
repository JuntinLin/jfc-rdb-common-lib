package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.SfaFile;
import com.jfc.rdb.tiptop.entity.SfaFilePK;
@Repository
public interface SfaRepository extends JpaRepository<SfaFile, SfaFilePK> {
	/*備料檔(sfa_file)
    sfa05	number(15,3)	應發數量	應發數量儲存該工單備料料件，使用者經過修改後，決定的備料量；開始時，應與原發數量相同
    sfa06	number(15,3)	已發數量	已發數量儲存該工單備料料件，已經被發料的數量；
    工單檔(sfb_file)
    sfb87	varchar2(1)	確認否	"確認否(Y/N/X)
    sfb04	varchar2(1)	工單狀態	"工單狀態儲存該工單目前處理階段狀況正確值 1/2/3/4/5/6/7/8
            1: 確認生產工單(firm plan)  2: 工單已發放,料表尚未列印  3: 工單已發放,料表已列印    4: 工單已發料
            5: 在製過程中   6: 工單已完工,進入F.Q.C 7: 完工入庫     8: 結案
            
    
    private float findOnJob(String mano){//-工單備料量 - a.sfa062 + a.sfa063
        String sql = "select sum(sfa05), sum(sfa06), sum(sfa05) - sum(sfa06) -sum(sfa062) + sum(sfa063) as onJob\n" +
                        "from sfa_file\n" +
                        "left outer join sfb_file on sfb01 = sfa01\n" +
                        "where sfb87 = 'Y' and sfa03='" + mano + "' and sfb04 not in ('8')";
    */
	@Query("""
	        SELECT COALESCE(SUM(s.sfa05) - SUM(s.sfa06) - SUM(s.sfa062) + SUM(s.sfa063), 0)
	        FROM SfaFile s 
	        LEFT JOIN s.sfb sfb 
	        WHERE sfb.sfb87 = 'Y' 
	        AND s.ima.ima01 = :mano 
	        AND sfb.sfb04 NOT IN ('8')
	        """)
	BigDecimal findOnJobAmount(@Param("mano") String mano);
}
