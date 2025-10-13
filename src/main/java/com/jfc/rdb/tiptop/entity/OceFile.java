package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OCE_FILE")
public class OceFile {
	@EmbeddedId
	private OceFilePK id;
	//String oce01;//	varchar2(	客戶編號
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("oce01")  // 映射到複合主鍵的 oce01 欄位
    @JoinColumn(name = "oce01", referencedColumnName = "occ01", 
                insertable = false, updatable = false)
    private OccFile occ;
    String oce02;//	varchar2(	職務	
    //String oce03;//	varchar2(	聯絡人	
    String oce04;//	varchar2(	聯絡電話	
    String oce05;//	varchar2(	E-mail address	
    java.util.Date oce06;//	date	No Use	
    String oce07;//	varchar2(	住宅號碼	
    String oce08;//	varchar2(	傳真號碼	
    String oce09;//	varchar2(	身份証地址	
    String oce10;//	varchar2(	身份証號碼
}
