package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OebFilePK {
	String oeb01; //	varchar2(20)	訂單單號	訂單單號 oea01
    int oeb03; //	number(5)	項次	
}
