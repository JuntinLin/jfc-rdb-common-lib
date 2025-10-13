package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//LCM 料件數量入庫異動檔(cmc_file)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CMC_FILE")
public class CmcFile {
	@EmbeddedId
    private CmcFilePK id;
	//cmc01	varchar2(40)	料件編號			*
	private Date cmc02; //	date	計算基準日			
	//cmc03	date	異動日期			*
	private BigDecimal cmc04; 	//number(15,5)	數量	數量   (不含除外倉之入庫量)		
	//cmc021	number(5)	年度			*
	//cmc022	number(5)	期別			*
	//cmc07	varchar2(1)	成本計算類別			*
	//cmc08	varchar2(40)	類別代號(批次號/專案號/利潤中心)			*
	private String cmclegal; //	varchar2(10)	所屬法人			


}
