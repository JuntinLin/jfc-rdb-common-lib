package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *工單發料底稿單頭檔(sfp_file)
 * @author justin
 * 
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SFP_FILE")
public class SfpFile {
	@Id
	private String sfp01;//	varchar2(20)	發料單號	
    private java.util.Date sfp02; //	date	輸入日期	
    private java.util.Date sfp03; //	date	扣帳日期	
    private String sfp04; //	varchar2(1)	扣帳碼	扣帳碼(Y/N)
    private String sfp05; //	varchar2(1)	列印碼	列印碼(Y/N)
    private String sfp06; //	varchar2(1)	異動類別	異動類別  1:成套發料 2:超領     3:補料     4.耗材領  6:成套退料 7:超領退 8:一般退 9.耗材退  A:重複性生產發料    B:重複性生產退料   C:重複性生產領料
    private String sfp07; //	varchar2(10)	製造部門	製造部門  gem01
    private String sfp08; //	varchar2(20)	PBI NO(Picking Batch ID)	料表批號  sfc01
    private String sfp09; //	varchar2(1)	挪料否	挪料否        #NO:6968 紀錄是否為挪料資料產生的發/退料資料
    private String sfp10; //	varchar2(8)	序號	序號          #NO:6968 當此筆發退料單為工單挪料作業自動產生並 扣帳時，此欄位記錄挪料紀錄檔的序號 當此張發退料單做過帳還原時，可以此序號　 串工單挪料紀錄檔(sfm_file,sfn_fil)刪除　 挪料紀錄
    private String sfpuser; //	varchar2(10)	資料所有者	資料所有者    #NO:6968
    private String sfpgrup; //	varchar2(10)	資料所有部門	資料所有部門  #NO:6968
    private String sfpmodu; //	varchar2(10)	資料修改者	資料修改者    #NO:6968
    private java.util.Date sfpdate; //	date	最近修改日	最近修改日    #NO:6968
    private String sfp11; //	varchar2(10)	理由碼	
    private String sfpconf; //	varchar2(1)	確認碼

}
