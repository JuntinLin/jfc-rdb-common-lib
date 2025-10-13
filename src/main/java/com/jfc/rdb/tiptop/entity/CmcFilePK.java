package com.jfc.rdb.tiptop.entity;

import java.util.Date;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmcFilePK {
	private String cmc01; 	//	varchar2(40)	料件編號			*
	private Date cmc03; 	//	date	異動日期			*
	private Integer cmc021;	//	number(5)	年度			*
	private Integer cmc022; //	number(5)	期別			*
	private String cmc07; //	varchar2(1)	成本計算類別			*
	private String cmc08; //	varchar2(40)	類別代號(批次號/專案號/利潤中心)			*
	
}
