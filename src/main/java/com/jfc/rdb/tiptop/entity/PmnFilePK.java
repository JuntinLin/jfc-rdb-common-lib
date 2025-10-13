package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PmnFilePK {
	private String pmn01; //	varchar2(20)	採購單號	採購單號 pmm01
    private int pmn02;//	number(5)	項次	項次 採購單所含蓋的項目編號
}
