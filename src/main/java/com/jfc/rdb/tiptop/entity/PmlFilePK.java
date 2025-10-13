package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PmlFilePK {
	private String pml01; //	varchar2(20)	請購單號	請購單號  pmk01
    private int pml02; //	number(5)	項次	項次 請購單所含蓋的項目編號
}
