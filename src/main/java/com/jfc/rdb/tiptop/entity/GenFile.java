package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * aooi040 員工資料維護
* 員工姓名資料(gen_file)	
欄位代號	欄寬	欄位名稱	額外說明
gen04	varchar2(80)	職稱	
gen05	varchar2(5)	分機	
gen06	varchar2(80)	E-mail	E-mail #03/02/11 add
	
genuser	varchar2(10)	資料所有者	
gengrup	varchar2(10)	資料所有部門	
genmodu	varchar2(10)	資料修改者	
gendate	date	最近修改日	
genorig	varchar2(10)	資料建立部門	
genoriu	varchar2(10)	資料建立者	
genpos	varchar2(1)	已傳POS否(1.新增未下傳 2.修改未下傳 3.已下傳)	
gen07	varchar2(10)	歸屬營運中心	
gen08	varchar2(20)	行動電話	

*/
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GEN_FILE")
public class GenFile {
	@Id
	@Column(name = "gen01", length = 10)
	private String gen01; //	varchar2(10)	員工代號	
    private String gen02; //	varchar2(40)	員工姓名	
    private String gen03; //	varchar2(10)	所屬部門代號	
    private String gen05; //	varchar2(5)	分機	
    private String gen06; //	varchar2(80)	E-mail	E-mail #03/02/11 add
    private String genacti; //	varchar2(1)	資料有效碼

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gen03", referencedColumnName = "gem01", insertable = false, updatable = false)
    private GemFile department;    // 關聯部門資料
}
