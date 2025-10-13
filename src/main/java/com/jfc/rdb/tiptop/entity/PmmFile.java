package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author justin 採購單單頭(pmm_file) pmm03 number(5) 更動序號 更動序號 採購單更動版本序號
 *         本系統有保留歷史採購單資料的功能，當使用 者與廠商之間的交易有一些變更時可利用更動 序號記錄變更否，如無變更本欄位為空白
 * 
 *         pmm05 varchar2(10) 專案號碼 專案號碼 --> no use (00/04/18)
 *         如該採購單為特定給某一專案使用時可輸入 pmm06 varchar2(10) 預算號碼 預算號碼 pno01 如果有使用採購預算時可輸入
 *         如採用採購預算時, 系統應與應付帳款相連 pmm07 varchar2(10) 單據分類 單據分類 使用者可自行定義將採購單分類
 *         pmm08 varchar2(20) PBI pmm14 varchar2(10) 收貨部門 收貨部門 gem01
 *         輸入此批採購的收貨部門, 可空白 如果有設定請購部門, 則需存在 部門檔 pmm15 varchar2(10) 確認人 確認人
 *         輸入此批採購確認人, 可空白 如果有設定確認人, 則需存在 員工檔 pmm16 varchar2(10) 運送方式 運送方式 ged01
 *         輸入此批採購的確認人, 可空白 pmm17 varchar2(10) 代理商 代理商 pmc01 輸入此批採購的代理商, 可空白
 * 
 *         pmm20 varchar2(10) 付款方式 付款方式 pma01 輸入此批採購的付款方式 pmm21 varchar2(4) 稅別
 *         稅別 gec01 輸入此批採購的稅別, 不可空白 pmm22 varchar2(4) 幣別 幣別 azi01 輸入此批採購的幣別,
 *         不可空白 pmm26 varchar2(10) 理由碼 理由碼 azf01,azf02='2' 狀況變更的理由, 可空白 pmm27
 *         date 狀況異動日期 pmm28 varchar2(10) 會計分類 會計分類 專案系統使用 pmm29 varchar2(24)
 *         會計科目 會計科目 aag01 單據性質為 'EXP'，'SER'，'CAP' 時一定要 輸入會計科目 pmm30 varchar2(1)
 *         收貨單列印否 收貨單列印否 正確值 Y/N Y: 收貨時, 必須列印收貨單 N: 收貨時, 可不列印收貨單 pmm31 number(5)
 *         會計年度 會計年度 該採購單所屬會計年度 pmm32 number(5) 會計期間 會計期間 該採購單所屬會計期間 pmm40
 *         number(20,6) 總金額 總金額 系統維頀 採購單身各項資料的 單價 * 數量 之總和
 * 
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PMM_FILE")
public class PmmFile {
	@Id
	private String pmm01; // 採購單號

	@Column(name = "pmm18")
	private String pmm18; // 確認碼

	@Column(name = "pmm25")
	private String pmm25; // 狀態碼
}
