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
* @author justin
請購單單頭(pmk_file)
pmk03	number(5)	更動序號	更動序號 請購單更動版本序號
pmk05	varchar2(10)	專案號碼	專案號碼--> no use(00/04/15)
pmk06	varchar2(10)	預算號碼	預算號碼 如該有使用採購預算時可輸入 如採用採購預算時, 系統應與應收帳款相連
pmk07	varchar2(10)	請購類別	請購類別 使用者可自行定義分類
pmk08	varchar2(20)	PBI	
pmk09	varchar2(10)	供應廠商	供應廠商pmc01 下單供應廠商
pmk10	varchar2(10)	shipping address  pme01,pme02!='1'
pmk11	varchar2(10)	Bill address  pme01,pme02!='0'

pmk13	varchar2(10)	請購部門	請購部門 gem01
pmk14	varchar2(10)	收貨部門	收貨部門 gem01
pmk15	varchar2(10)	確認人	
pmk16	varchar2(10)	運送方式	運送方式 ged01
pmk17	varchar2(10)	代理商	代理商   pmc01
pmk18	varchar2(1)	確認否	確認否(Y/N) 原'FOB條件'
pmk20	varchar2(10)	付款條件	付款條件 pma01
pmk21	varchar2(4)	稅別	稅別 gec01,gec011='1'
pmk22	varchar2(4)	幣別	幣別 azi01
pmk26	varchar2(10)	理由碼	理由碼 azf01,azf02='2'
*/
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PMK_FILE")
public class PmkFile {
	@Id
    private String pmk01;  // 請購單號
    
    @Column(name = "pmk18")
    private String pmk18;  // 確認碼
}
