package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SfsFilePK {
	private String sfs01;//	varchar2(20)	發料單號	發料單號 sfp01
    private Integer sfs02;//	number(5)	項次	
    

}
