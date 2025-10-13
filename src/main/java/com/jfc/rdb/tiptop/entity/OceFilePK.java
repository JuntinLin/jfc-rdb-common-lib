package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OceFilePK {
	String oce01;//	varchar2(	客戶編號	
    String oce03;//	varchar2(	聯絡人	
}
