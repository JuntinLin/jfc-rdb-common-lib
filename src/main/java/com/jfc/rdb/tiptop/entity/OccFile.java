package com.jfc.rdb.tiptop.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OCC_FILE")
@NamedQuery(name = "OccFile.findAll", query = "SELECT o FROM OccFile o")
public class OccFile {
	@Id
	String occ01; //	varchar2(10)	客戶編號	
    String occ02; //	varchar2(40)	客戶簡稱
    String occ03; //	varchar2(10)	客戶分類	
    //String occ04; //	varchar2(10)	負責業務員編號	
    //private com.tiptop.db.entity.GEN salesman;
    @ManyToOne
    @JoinColumn(name = "occ04", referencedColumnName = "gen01")
    private GenFile salesman;
    String occ18; //	varchar2(80)	公司全名(1)	
    String occ21; //	varchar2(10)	國別編號
    String occ38;//	varchar2(2)	客戶月結日
    String occ47;//	varchar2(10)	慣用交運方式	
    Float occud07;//	number(15,3)	績效倍率
    String occud02;//	varchar2(40)	出貨逾期是否扣款
    String occud05;//	varchar2(40)	使用者自訂欄位, 第一碼:寄送逾期催收email

    //
    //private boolean overduePenalty;//出貨逾期扣款
    
    //private com.tiptop.db.entity.OCA industrialType;

    @OneToMany(mappedBy = "customer")
    private List<ImaFile> imaList;
    
 // 添加反向關係（如果需要）
    @OneToMany(mappedBy = "occ", fetch = FetchType.LAZY)
    private java.util.List<OceFile> oceFiles;
}
