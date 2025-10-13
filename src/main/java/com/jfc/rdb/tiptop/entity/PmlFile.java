package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
*
* @author justin
* 請購單單身(pml_file)

pml05	varchar2(32)	APS單據編號	APS單據編號   no.4649 02/03/15(modify)
pml06	varchar2(255)	廠商料號	廠商料號
pml07	varchar2(4)	請購單位	請購單位 gfe01 請購單上請購數量單位
pml08	varchar2(4)	庫存單位	庫存單位 default ima25 料件庫存單位
pml09	number(20,8)	轉換率	轉換率 請購單位/庫存單位的轉換率
pml18	date	MRP 需求日期	MRP 需求日期 該請購料件的MRP/MPS計劃訂單需求日期

*/
@Entity
@Table(name = "PML_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PmlFile {
	@EmbeddedId
    private PmlFilePK id;
	@ManyToOne
    @JoinColumn(name = "pml01", referencedColumnName = "pmk01", 
                insertable = false, updatable = false)
    private PmkFile pmk;
	
	@Column(name = "pml04")
    private String pml04;  // 料號
    
    @Column(name = "pml16")
    private String pml16;  // 狀態碼
    
    @Column(name = "pml20", precision = 15, scale = 3)
    private BigDecimal pml20;  // 請購數量
    
    @Column(name = "pml21", precision = 15, scale = 3)
    private BigDecimal pml21;  // 已轉採購數量
}
