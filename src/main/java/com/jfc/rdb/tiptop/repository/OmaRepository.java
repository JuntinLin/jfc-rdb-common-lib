package com.jfc.rdb.tiptop.repository;

/*應收/待抵帳款單頭檔(oma_file)*/
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OmaFile;
import com.jfc.rdb.tiptop.model.dto.AccountDetailDTO;

@Repository
public interface OmaRepository extends JpaRepository<OmaFile, String> {
	/*
	 * 2025-02-21 馥雅:改為oma61 private BigDecimal oma56t;// number(20,6) 本幣應收含稅金額 /
	 * 待抵含稅 private BigDecimal oma61; // number(20,6) 本幣未沖金額
	 * 
	 * @Query(value = """ SELECT o.oma03 AS customerCode, o.oma032 AS customerName,
	 * SUM(o.oma56t) AS totalALL,
	 * 
	 * o30.s56 AS total30, o60.s56 AS total60, o120.s56 AS total120, o180.s56 AS
	 * total180, o365.s56 AS total365, o545.s56 AS total545, o546.s56 AS
	 * totalOver546
	 * 
	 * FROM oma_file o
	 * 
	 * -- 0~30天 LEFT JOIN ( SELECT oma03, SUM(oma56t) AS s56 FROM oma_file WHERE
	 * omavoid = 'N' AND oma61 > 0 AND oma18 = :accountNumber AND oma11 <
	 * :cutoffDate AND oma11 >= :cutoffDate - 30 GROUP BY oma03 ) o30 ON o30.oma03 =
	 * o.oma03
	 * 
	 * -- 31~60天 LEFT JOIN ( SELECT oma03, SUM(oma56t) AS s56 FROM oma_file WHERE
	 * omavoid = 'N' AND oma61 > 0 AND oma18 = :accountNumber AND oma11 <
	 * :cutoffDate AND oma11 >= :cutoffDate - 60 AND oma11 < :cutoffDate - 30 GROUP
	 * BY oma03 ) o60 ON o60.oma03 = o.oma03
	 * 
	 * -- 61~120天 LEFT JOIN ( SELECT oma03, SUM(oma56t) AS s56 FROM oma_file WHERE
	 * omavoid = 'N' AND oma61 > 0 AND oma18 = :accountNumber AND oma11 <
	 * :cutoffDate AND oma11 >= :cutoffDate - 120 AND oma11 < :cutoffDate - 60 GROUP
	 * BY oma03 ) o120 ON o120.oma03 = o.oma03
	 * 
	 * -- 121~180天 LEFT JOIN ( SELECT oma03, SUM(oma56t) AS s56 FROM oma_file WHERE
	 * omavoid = 'N' AND oma61 > 0 AND oma18 = :accountNumber AND oma11 <
	 * :cutoffDate AND oma11 >= :cutoffDate - 180 AND oma11 < :cutoffDate - 120
	 * GROUP BY oma03 ) o180 ON o180.oma03 = o.oma03
	 * 
	 * -- 181~365天 LEFT JOIN ( SELECT oma03, SUM(oma56t) AS s56 FROM oma_file WHERE
	 * omavoid = 'N' AND oma61 > 0 AND oma18 = :accountNumber AND oma11 <
	 * :cutoffDate AND oma11 >= :cutoffDate - 365 AND oma11 < :cutoffDate - 180
	 * GROUP BY oma03 ) o365 ON o365.oma03 = o.oma03
	 * 
	 * -- 366~545天 LEFT JOIN ( SELECT oma03, SUM(oma56t) AS s56 FROM oma_file WHERE
	 * omavoid = 'N' AND oma61 > 0 AND oma18 = :accountNumber AND oma11 <
	 * :cutoffDate AND oma11 >= :cutoffDate - 545 AND oma11 < :cutoffDate - 365
	 * GROUP BY oma03 ) o545 ON o545.oma03 = o.oma03
	 * 
	 * -- over 546天 LEFT JOIN ( SELECT oma03, SUM(oma56t) AS s56 FROM oma_file WHERE
	 * omavoid = 'N' AND oma61 > 0 AND oma18 = :accountNumber AND oma11 <
	 * :cutoffDate AND oma11 <= :cutoffDate - 546 GROUP BY oma03 ) o546 ON
	 * o546.oma03 = o.oma03
	 * 
	 * WHERE o.omavoid = 'N' AND o.oma61 > 0 AND o.oma18 = :accountNumber AND
	 * o.oma11 < :cutoffDate
	 * 
	 * GROUP BY o.oma03, o.oma032, o30.s56, o60.s56, o120.s56, o180.s56, o365.s56,
	 * o545.s56, o546.s56 ORDER BY o.oma03
	 */
	/*
	 * 2025-02-27 馥雅:取消0~30天, 避免只有逾期1~3天的資料被催收 截止日 cutoffDate 前30天不顯示 o30.s61 AS
	 * total30, change to 0 AS total30,
	 */
	/*
	 * 2025-03-19 增加是否寄送email功能 occud05 第一碼用來決定是否寄送逾期催收email
	 */
	@Query(value = """
			      SELECT
			          o.oma03       AS customerCode,
			          o.oma032      AS customerName,
			          g.gen01 as salesmanCode,
			          g.gen02 as salesmanName,
			          SUM(o.oma61) AS totalALL,
			          0       AS total30,
			          o60.s61       AS total60,
			          o120.s61      AS total120,
			          o180.s61      AS total180,
			          o365.s61      AS total365,
			          o545.s61      AS total545,
			          o546.s61      AS totalOver546,
			          c.occud05     AS sendEmailFlag

			      FROM oma_file o
			LEFT JOIN gen_file g ON o.oma14 = g.gen01
			LEFT JOIN occ_file c ON o.oma03 = c.occ01
			      -- 0~30天
			      LEFT JOIN (
			          SELECT oma03, SUM(oma61) AS s61
			          FROM oma_file
			          WHERE omavoid = 'N'
			            AND oma61 > 0
			            AND oma18 = :accountNumber
			            AND oma11 < :cutoffDate
			            AND oma11 >= :cutoffDate - 30
			          GROUP BY oma03
			      ) o30 ON o30.oma03 = o.oma03

			      -- 31~60天
			      LEFT JOIN (
			          SELECT oma03, SUM(oma61) AS s61
			          FROM oma_file
			          WHERE omavoid = 'N'
			            AND oma61 > 0
			            AND oma18 = :accountNumber
			            AND oma11 < :cutoffDate
			            AND oma11 >= :cutoffDate - 60
			            AND oma11 < :cutoffDate - 30
			          GROUP BY oma03
			      ) o60 ON o60.oma03 = o.oma03

			      -- 61~120天
			      LEFT JOIN (
			          SELECT oma03, SUM(oma61) AS s61
			          FROM oma_file
			          WHERE omavoid = 'N'
			            AND oma61 > 0
			            AND oma18 = :accountNumber
			            AND oma11 < :cutoffDate
			            AND oma11 >= :cutoffDate - 120
			            AND oma11 < :cutoffDate - 60
			          GROUP BY oma03
			      ) o120 ON o120.oma03 = o.oma03

			      -- 121~180天
			      LEFT JOIN (
			          SELECT oma03, SUM(oma61) AS s61
			          FROM oma_file
			          WHERE omavoid = 'N'
			            AND oma61 > 0
			            AND oma18 = :accountNumber
			            AND oma11 < :cutoffDate
			            AND oma11 >= :cutoffDate - 180
			            AND oma11 < :cutoffDate - 120
			          GROUP BY oma03
			      ) o180 ON o180.oma03 = o.oma03

			      -- 181~365天
			      LEFT JOIN (
			          SELECT oma03, SUM(oma61) AS s61
			          FROM oma_file
			          WHERE omavoid = 'N'
			            AND oma61 > 0
			            AND oma18 = :accountNumber
			            AND oma11 < :cutoffDate
			            AND oma11 >= :cutoffDate - 365
			            AND oma11 < :cutoffDate - 180
			          GROUP BY oma03
			      ) o365 ON o365.oma03 = o.oma03

			      -- 366~545天
			      LEFT JOIN (
			          SELECT oma03, SUM(oma61) AS s61
			          FROM oma_file
			          WHERE omavoid = 'N'
			            AND oma61 > 0
			            AND oma18 = :accountNumber
			            AND oma11 < :cutoffDate
			            AND oma11 >= :cutoffDate - 545
			            AND oma11 < :cutoffDate - 365
			          GROUP BY oma03
			      ) o545 ON o545.oma03 = o.oma03

			      -- over 546天
			      LEFT JOIN (
			          SELECT oma03, SUM(oma61) AS s61
			          FROM oma_file
			          WHERE omavoid = 'N'
			            AND oma61 > 0
			            AND oma18 = :accountNumber
			            AND oma11 < :cutoffDate
			            AND oma11 <= :cutoffDate - 546
			          GROUP BY oma03
			      ) o546 ON o546.oma03 = o.oma03

			      WHERE o.omavoid = 'N'
			        AND o.oma61 > 0
			        AND o.oma18 = :accountNumber
			        AND o.oma11 < :cutoffDate - 30

			      GROUP BY
			          o.oma03,
			          o.oma032,
			          g.gen01,
			          g.gen02,
			          o30.s61,
			          o60.s61,
			          o120.s61,
			          o180.s61,
			          o365.s61,
			          o545.s61,
			          o546.s61,
			          c.occud05
			      ORDER BY o.oma03
			      """, nativeQuery = true)
	List<Object[]> findAgingAnalysisNative(@Param("accountNumber") String accountNumber,
			@Param("cutoffDate") LocalDate cutoffDate);

	/*
	 * 2025-02-27 AND oma11 < :cutoffDate 變更為 AND oma11 < :cutoffDate - 30
	 */
	@Query("""
			    SELECT NEW com.jfc.rdb.tiptop.model.dto.AccountDetailDTO(
			        o.oma02,
			        o.oma11,
			        o.oma01,
			        o.oma00,
			        o.oma56t,
			        o.oma61,
			        o.oma10,
			        o.salesman.gen02
			    )
			    FROM OmaFile o
			    WHERE o.oma03 = :customerCode
			    AND o.oma18 = :accountNumber
			    AND o.oma11 <= :adjustedDate
			    AND o.omavoid = 'N'
			    AND o.oma61 > 0
			    ORDER BY o.oma11 DESC
			""")
	List<AccountDetailDTO> findCustomerDetails(@Param("customerCode") String customerCode,
			@Param("accountNumber") String accountNumber, @Param("adjustedDate") Date cutoffDate);

	/**
     * 查詢特定客戶的應收帳款明細
     */
    @Query(nativeQuery = true, value =
        "SELECT " +
        "    oma01 AS documentNumber, " +           // 單據編號
        "    oma02 AS documentDate, " +             // 單據日期
        "    oma11 AS dueDate, " +                  // 到期日 oma11	date	應收款日/應扣抵日
        "    oma23 AS currency, " +                 // 幣別 oma23	varchar2(4)	幣別	幣別   azi01
        "    oma56t AS amount, " +                  // 金額 oma56t	number(20,6)	本幣應收含稅金額 / 待抵含稅
        "    DECODE(OGC15, 'N', '未結清', 'Y', '已結清', OGC15) AS status " + // 狀態
        "FROM OMA_FILE " +
        "WHERE oma18 = :accountNumber " +           // 客戶代號
        " AND omavoid = 'N'" +					//omavoid	varchar2(1)	作廢否	作廢否 (Y/N)
        " AND oma61 > 0 " + 					// 未結清 oma61	number(20,6)	本幣未沖金額
        "ORDER BY oma01 DESC"
    )
    List<Object[]> findReceivablesDetailsByCustomer(@Param("customerCode") String customerCode);
}
