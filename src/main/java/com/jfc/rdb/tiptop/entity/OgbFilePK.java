package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OgbFilePK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String ogb01; //	varchar2(20)	出貨單號	出貨單號 oga01
    private Integer ogb03; //	number(5)	項次	
}
