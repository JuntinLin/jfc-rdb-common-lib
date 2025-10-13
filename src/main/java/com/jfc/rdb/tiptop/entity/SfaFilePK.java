package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SfaFilePK {
	private String sfa01;//	varchar2(20)	工單編號	工單編號 sfb01
    private String sfa03;//	varchar2(40)	料件編號	料件編號 ima01儲存該工單所屬下階料件編號，應為被發放投入生產的料件編號
    private String sfa08;//	varchar2(6)	作業編號	作業編號儲存該工單備料料件，預期投入生產的作業所屬作業編號；可由[工單檔]指定的製程編號及產品結構而得
    private String sfa012;//	varchar2(10)	製程段號
}
