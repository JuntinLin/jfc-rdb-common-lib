package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *工單發料底稿單身檔(sfs_file)
 * @author justin
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SFS_FILE")
public class SfsFile {
	@EmbeddedId
    private SfsFilePK id;
	//private String sfs01;//	varchar2(20)	發料單號	發料單號 sfp01
    //private com.tiptop.db.entity.SFP sfp;
    //private Integer sfs02;//	number(5)	項次	
	@ManyToOne
    @JoinColumn(name = "sfs01", referencedColumnName = "sfp01", 
            insertable = false, updatable = false)
    private SfpFile sfp; //工單發料底稿單頭檔(sfp_file)sfp01 <= sfs01 
    //private String sfs03;//	varchar2(40)	工單單號	工單   sfb01(ASR此欄是料號(asri210,asri220))
    @ManyToOne
    @JoinColumn(name = "sfs03", referencedColumnName = "sfb01", 
            insertable = false, updatable = false)
    private SfbFile sfb;
    private String sfs04;//	varchar2(40)	料號	料號   ima01(sfa03)
    private BigDecimal  sfs05;//	number(15,3)	發料數量	
    private String sfs06;//	varchar2(4)	發料單位	
    private String sfs07;//	varchar2(10)	倉庫	倉庫   imd01(img02)
    private String sfs08;//	varchar2(10)	儲位	儲位   ime01(img03)
    private String sfs09;//	varchar2(24)	批號	批號         img04
    private String sfs10;//	varchar2(6)	作業編號	
    private String sfs21;//	varchar2(255)	備註	

}
